---
title: 'Dependency management'
weight: 2
draft: false
author: Wouter Groeneveld
---

Lees ook: [Meer uitleg over de Gradle build tool](/dependency-management/gradle/).

> <i class="fa fa-question-circle" aria-hidden="true"></i>
 Wat is de beste manier om afhankelijkheden te beheren?

## Wat is een 'dependency'?

Een dependency, of _afhankelijkheid_, is een externe bibliotheek die wordt gebruikt tijdens de ontwikkeling van een toepassing. Tijdens het vak 'Software ontwerp in Java' zijn reeds de volgende externe libraries gebruikt:

1. [JavaFX](https://openjfx.io)
2. [Google Gson](https://github.com/google/gson)
3. [JUnit](https://junit.org/junit5/)

Het vertrouwen op zo'n library houdt in dat een extern bestand, zoals een `.jar` of `.war` bestand, download en koppelt wordt aan de applicatie. In Java koppelen we externe libraries door middel van het `CLASSPATH`: een folder die de compiler gebruikt om te zoeken naar klassen. 

Serialisatie met behulp van [Gson](https://jar-download.com/artifacts/io.github.palexdev/gson/2.9.0/source-code) kan op deze manier:

<div class="devselect">

```java
public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(1));
    }
}
```


</div>

Bovenstaande `Main.java` compileren zonder meer geeft de volgende fout:

<pre>
PS C:\Users\u0158802\OneDrive - KU Leuven\Desktop\java_example_2> javac  Main.java
Main.java:3: error: cannot find symbol
        Gson gson = new Gson();
        ^
  symbol:   class Gson
  location: class Main
</pre>

De klasse `Gson` is immers iets dat we niet hebben zelfgemaakt, maar wensen te importeren via het `import com.google.gson.*;` statement. Er is een manier nodig om de [gedownloade library](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0) te linken met onze bestaande code: `javac -cp gson-2.9.0.jar Main.java`. Het programma uitvoeren kan met `java -cp "gson-2.9.0.jar;." Main`. Er worden dus 2 zaken aan het classpath meegegeven: de Google jar, en de huidige directory (`.`), om `Main.class` terug te vinden.

{{% notice warning %}}
Java classpath separators zijn [OS-specifiek](https://howtodoinjava.com/java/basics/java-classpath/)! Unix: `:` in plaats van Windows: `;`.
{{% /notice %}}

Dit programma kan schematisch worden voorgesteld als volgt:

{{<mermaid>}}
graph LR;
    A["Main klasse"]
    C["Gson 2.9.0"]
    A -->|dependent on| C
{{< /mermaid >}}


De dependency in bovenstaand voorbeeld is `gson-2.9.0.jar`. Een gemiddelde Java applicatie heeft echter **meer dan 10 dependencies!** Het beheren van deze bestanden en de verschillende versies (major, minor, revision) geeft vaak conflicten die beter beheerd kunnen worden door tools dan door de typische vergeetachtigheid van mensen. Dit kluwen aan afhankelijkheden, dat erg snel onhandelbaar kan worden, noemt men een [Dependency Hell](https://en.wikipedia.org/wiki/Dependency_hell). Er zijn varianten: [DLL Hell](https://en.wikipedia.org/wiki/DLL_Hell) sinds 16-bit Windows versies, RPM Hell voor Redhat Linux distributies, en [JAR Hell](https://en.wikipedia.org/wiki/Java_Classloader#JAR_hell) voor Java projecten.

Zie ook xkcd's [Tech Loops](https://www.explainxkcd.com/wiki/index.php/1579:_Tech_Loops) rommelboeltje:

![](/img/tech_loops.png)

## Wie beheert dependencies?

### De ontwikkelaar (manueel)

De eenvoudigste manier om een library te gebruiken is de volgende procedure te volgen:

1. Navigeer naar de website van de library en download deze in een bepaalde map, zoals `/lib`.
2. Importeer de juiste klasses met het `import` statement.
3. Compileer de code door middel van het `-cp dependency1.jar` argument.

Voor kleine programma's met enkele libraries is dit meer dan voldoende. Het kost echter redelijk veel moeite om de juiste versie te downloaden: stap 1 kost meestal meer dan 5 minuten werk. 

{{% notice note %}}
Merk op dat jar files in een submap steken de syntax van de `-cp` parameter lichtjes wijzigt: bij compileren wordt het `javac -cp lib/* bla.java` en bij uitvoeren wordt het `java -cp "lib/*:." bla`. Zonder de toegevoegde punt (`.`) bij het `java` commando wordt de main methode in `bla` zelf niet gevonden. Wildcards zijn toegestaan. Zie ook [Understanding the Java Classpath](https://dev.to/martingaston/understanding-the-java-classpath-building-a-project-manually-3c3l). In de praktijk worden build tools als Gradle gebruikt om projecten automatisch te builden, inclusief het doorgeven van de juiste parameters/dependencies.
{{% /notice %}}

### De tools (automatisch)

#### Apache Maven

[Maven](https://en.wikipedia.org/wiki/Apache_Maven) is een build tool van de Apache Foundation die zowel de manier waarop de software wordt gecompileerd als zijn afhankelijkheden beheert. Maven is de voorloper van Gradle en bestaat reeds 15 jaar. 

Een Maven project heeft een `pom.xml` bestand (Project Object Model), waarin in XML formaat wordt beschreven hoe de structuur er uit ziet, welke libraries men gebruikt, en zo voort:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0</version>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

Maven is erg populair in de Java wereld, waardoor er verschillende servers zijn die deze `pom` bestanden samen met hun libraries beheren, zoals de [Central Maven Repository](https://mvnrepository.com) en de [Google Maven Repository](https://maven.google.com/web/index.html) mirrors. De syntax van het configuratiebestand is echter erg onoverzichtelijk, en er zijn ondertussen betere alternatieven beschikbaar, zoals Gradle.

#### Gradle

**Belangrijk**: neem dit eerst door - [Meer informatie over Gradle](/dependency-management/gradle/). 

Gradle is net zoals Maven een automatisatie tool voor de Java wereld (en daarbuiten), die verder bouwt op de populariteit van Maven door bijvoorbeeld compatibel te zijn met de Repository servers, maar de grootste pijnpunten wegneemt: een slorig configuratiebestand in XML, en complexe command-line scripts.

De volgende procedure volg je als je Gradle dependencies laat beheren:

1. Zoek op de [Maven Repository](https://mvnrepository.com) website naar de gewenste library. 
2. Voeg één regel toe in je `gradle.build` bestand, in het dependencies stuk:

```
dependencies {
    implementation 'com.google.code.gson:gson:2.9.0'
}
```

Bij het uitvoeren van `gradlew` download Gradle automatisch de juiste opgegeven versie. Gradle bewaart lokale kopies van libraries in een submap van je home folder: `~/.gradle`. Dit kan je controleren door in IntelliJ naar File -> Project Structure te gaan en te klikken op "Libraries":

![](/img/gradlepath.png "Het lokale path naar de auto-cached libraries.")

Voordelen van het gebruik van deze methode:

1. Het zoeken van libraries beperkt zich tot één centrale (Maven Repository) website, waar alle verschillende versie revisies duidelijk worden vermeld.
2. Het downloaden van libraries beperkt zich tot één centrale locatie op je harde schijf: 10 verschillende Java projecten die gebruik maken van Gson vereisen linken naar dezelfde gradle bestanden. 
3. Het beheren van dependencies en versies beperkt zich tot één centraal configuratiebestand: `build.gradle`. Dit is (terecht) een **integraal deel van het project**! 

Lees ook: [Declaring dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies.html) in de Gradle docs. 

#### Custom Repository URLs voorzien

Veelgebruikte libraries zijn eenvoudig te vinden via de [Central Maven Repository](https://mvnrepository.com). Wanneer echter een eigen library werd gecompileerd, die dan in andere projecten worden gebruikt, schiet deze methode tekort: interne libraries zijn uiteraard niet op een publieke server gepubliceerd. 

Gradle voorziet gelukkig genoeg een eenvoudige manier om [repository websites toe te voegen](https://docs.gradle.org/current/userguide/declaring_repositories.html), met de volgende eenvoudige syntax:

<pre>
repositories {
  mavenCentral()
}
</pre>

`mavenCentral()`, `jcenter()`, en `google()` zijn ingebouwde repositories. Eigen Maven folders en URLs toevoegen kan ook, evenals een lokale folder:
<a name="flatdir"></a>

<pre>
repositories {
    maven {
        // dit kan zowel een folder als HTTP(s) URL zijn
        url "C:\\Users\\u0158802\\development\\java\\maven-repo"
    }
    flatDir {
        dirs 'lib'
    }
}
</pre>

#### Transitieve dependencies

Er zijn twee types van dependencies: **directe** (1) en **transitieve** (2). Een directe dependency is een afhankelijkheid die het project nodig heeft, zoals het gebruik van Gson, waarbij dit in de `dependencies {}` config zit. Een _transitieve_ of indirecte dependency is een dependency van een dependency. In de oefening hieronder maken we een project (1) aan, dat een project (2) gebruikt, dat Gson gebruikt. In project 1 is project 2 een directe dependency, en Gson een transitieve. In Project 2 is Gson een directe dependency (en komt project 1 niet voor):

{{<mermaid>}}
graph LR;
    A[Project een]
    B[Project twee]
    C[Gson]
    A --> B
    B --> C
    A -.-> C
{{< /mermaid >}}

Het is **geen goed idee** om bij fouten in uitvoering de zachte link (stippellijn) te veranderen in een harde, door dit als directe dependency toe te voegen. Gradle [biedt hier alternatieven voor](https://docs.gradle.org/current/userguide/dependency_management.html#controlling_transitive_dependencies). Het voor de hand liggende alternatief is van de library ook een Maven module te maken en deze te uploaden naar een (lokale) repository. 

#### Publiceren naar een Maven Repository

Klik op '[View All](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0)' bij de Gson module op de MVN Central Repo om te inspecteren welke bestanden typisch worden aangeleverd in een Maven repository:

1. De library zelf, in een bepaalde versie.
2. Eventueel de javadoc en/of sources als aparte jars.
3. Een `.pom` XML bestand.
4. metadata als `md5` checksums.

Het XML bestand beschrijft welke afhankelijkheden deze module op zich heeft. Zo kan een hele **dependency tree** worden opgebouwd! Het beheren van _alle_ afhankelijkheden is complexer dan op het eerste zicht lijkt, en laat je dus beter over aan deze gespecialiseerde tools. Google heeft voor Gson enkel Junit als test dependency aangeduid:

```xml
<dependencies>
<dependency>
<groupId>junit</groupId>
<artifactId>junit</artifactId>
<scope>test</scope>
</dependency>
</dependencies>
```

Grote projecten kunnen makkelijk afhankelijk zijn van tientallen libraries, die op hun beurt weer afhankelijk zijn van libraries. Een typische grote webapplicatie geschreven in java heeft de volgende dependency tree, die opgevraagd kan worden via Gradle of Maven:

![](/img/teaching/ses/deptree.png)

Gebruik hiervoor de task `dependencies`: `./gradlew dependencies`. Detailinformatie voor specifieke dependencies kunnen worden opgevraagd met de `dependencyInsight` task. Zie ook: [Viewing and debugging dependencies](https://docs.gradle.org/current/userguide/viewing_debugging_dependencies.html) in de Gradle docs. 

Gradle voorziet een plugin genaamd '_maven-publish_' die deze bestanden automatisch aanmaakt. 
<!-- Activeer de plugin en voeg een `publishing` tag toe met de volgende properties: -->

<div class="devselect">

```java
plugins {
    id 'java'
    id 'maven-publish' // toevoegen!
}

publishing {
    publications {
        maven(MavenPublication) {
            groupId = project.group.toString()
            version = version
            artifactId = 'projectnaam'

            from components.java
        }
    }
    repositories {
        maven {
            url = "C:\\Users\\u0158802\\development\\java\\maven-repo"
        }
    }
}
```

</div>

Windows gebruikers dienen in de `url` value te werken met dubbele backslashes (`\\`) in plaats van forward slashes (`/`) om naar het juiste pad te navigeren.

<!-- {{% notice warning %}}
**Opgelet met Kotlin-specifieke build files**: Als je een `build.gradle.kts` bestand gebruikt (Gradle in Kotlin-script formaat), is de syntax sterk gewijzigd (klik dan hierboven op de tab "Kotlin"). Zie ook [de officiele Gradle documentatie](https://docs.gradle.org/current/userguide/publishing_maven.html) over how to publish in Maven.
{{% /notice %}} -->

Deze uitbreiding voegt de target `publish` toe aan Gradle. Dus: `./gradlew publish` publiceert de nodige bestanden in de aangegeven folder. Een Gradle project die daar gebruik van wenst te maken dient enkel een tweede Maven Repository plaats te definiëren:

<div class="devselect">

```java
repositories {
    mavenCentral()
    maven {
        url = "C:\\Users\\u0158802\\development\\java\\maven-repo"
    }
}
```

</div>

## Opgaven

Neem dit eerst door: [Meer informatie over Gradle](/dependency-management/gradle/). 

### Opgave 1

Ontwerp een eenvoudige library genaamd '_scorebord_' die scores kan bijhouden voor bordspelletjes. Deze library kan dan gebruikt worden door toekomstige digitale Java bordspellen. In een Scorebord kan je spelers toevoegen door middel van een naam en een score. Er is een mogelijkheid om de huidige score van een speler op te vragen, en de winnende speler. Deze gegevens worden met behulp van Gson in een `JSON` bestand bewaard, zodat bij het heropstarten van een spel de scores behouden blijven. <br/>De API (publieke methodes) van de library ziet er zo uit:

<div class="devselect">

```java
public class Speler {
    public String getNaam() { }
    public int getScore() { }
}
public class Scorebord {
    public void voegToe(String x, int huidigeScore) { }
    public int getTotaleScore(String x) { }
    public String getWinnaar() { }
}
```

</div>

De klasse `Speler` is een intern hulpmiddel om te serialiseren. <br/>
Extra methodes toevoegen mag altijd. De constructor van het scorebord leest automatisch de score van de vorige keer in, als dat bestand bestaat. Denk bij de implementatie aan een collectie om spelers en hun scores bij te houden. Maak via IntelliJ een nieuw **Gradle - Java project**. Groupid: `be.kuleuven`. Arifactid: `scorebord`. Vergeet niet op 'refresh' te drukken wanneer je een dependency toevoegt (linksboven op onderstaande screenshot):

![](/img/teaching/ses/gradle-refresh.png)

Met het commando `gradlew jar` creëer je het bestand `scorebord-1.0-SNAPSHOT.jar` in de `build/libs` folder. 

{{% notice note %}}
Denk na over het bijhouden van `Speler`s in `Scorebord`. Een simpele `ArrayList` zal volstaan. Gebruik `Gson` in een methode als `save()` om gewoon de lijst (of het object zelf) naar de HDD te serialiseren. Tip: [java.nio.files.write](https://www.logicbig.com/how-to/code-snippets/jcode-java-io-files-write.html).
{{% /notice %}}

Tip: indien de Gralde wrapper een oudere versie aanmaakt (< v6), update met `gradlew wrapper --gradle-version 6.0.1`. Gradle versie `6` of groter is vereist voor JDK `13` of groter. 

### Opgave 2

Maak een nieuw Gradle project aan genaamd '_scorebord-darts_', dat bovenstaand scorebord project als een library gaat gebruiken. Bewaar de jar file lokaal in een 'lib' folder en instrueer Gradle zo dat dit als `flatDir` repository wordt opgenomen ([zie boven](#flatdir)). Het tweede project heeft als Artifactid `scorebord-darts`. De klasse `DartsGame` ziet er zo uit:

<div class="devselect">


```java
public class DartsGame {
    private String player = "jos";
    public void throwDart() {}
}
```

</div>

Als de dependencies goed liggen, kan je een nieuw `Scorebord` aanmaken, en herkent IntelliJ dit met CTRL+Space:

![](/img/teaching/ses/gradle-dependency-used.png)

Maak een `Main` klasse met een `public static void main(String[] args)` methode, waarin een darts spel wordt opgezet, en een aantal keer ter test wordt 'gegooid'. Druk de totale score en de winnaar af, dat opgevraagd kan worden via het spelbord. Krijg je deze klasse opgestart? 

<pre>
> Task :Main.main() FAILED
Exception in thread "main" java.lang.NoClassDefFoundError: com/google/gson/Gson
    at be.kuleuven.scorebord.Scorebord.<init>(Scorebord.java:24)
    at be.kuleuven.DartsGame.<init>(DartsGame.java:11)
    at be.kuleuven.Main.main(Main.java:6)
Caused by: java.lang.ClassNotFoundException: com.google.gson.Gson
    at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:583)
    at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
    at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
    ... 3 more

Caused by: java.lang.ClassNotFoundException: com.google.gson.Gson

Execution failed for task ':Main.main()'.
> Process 'command '/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home/bin/java'' finished with non-zero exit value 1
</pre>

Dit werkt _niet_ omdat we een library gebruiken (ScoreBord), die op zijn beurt een library gebruikt (Gson), die niet in onze huidige Gradle file is gedefiniëerd. Om dit op te lossen dienen we over te schakelen naar een lokale Maven repository, die ook transitieve dependencies automatisch inlaadt. Verwijder de `flatDir` en voeg een lokale maven URL toe. Publiceer in het scorebord project naar diezelfde URL volgens de instructies van de `maven-publish` plugin.

<!-- ### Opgave 3 (extra)

Bovenstaande screenshot geeft aan dat IntelliJ methodes herkent van de `Scorebord` klasse. Er is echter geen javadoc voorzien die uitlegt wat welke parameter doet. Voorzie javadoc bij alle publieke methodes. Dit moet ook mee worden verpakt in het `jar` bestand, zodat het ander project deze kan herkennen. Probeer uit te zoeken wat je hier voor moet doen in het `build.gradle` bestand. -->

<!-- ### Opgave 4 (extra)

Genereer met behulp van Gradle van de [SESsy library](/dependency-management/sessy) een dependency tree en inspecteer welke dpendencies transitief zijn en welke direct.  -->

<!-- ## Denkvragen

- Hoe zou je transitieve dependencies handmatig kunnen beheren? Wat zijn de voor- en nadelen?
- Wat gebeurt er als project1-1.0 afhankelijk is van lib1-1.0 en lib1-2.0, en lib1-1.0 van lib2-1.0 - een oudere versie dus? 
- Heb je altijd test dependencies nodig? Wat gebeurt er met een test dependency, libtest-1.0, van lib1-1.0, als project1-1.0 afhankelijk is van lib1-1.0?
- Als ik publiceer naar een lokale folder, welke bestanden zijn dan absoluut noodzakelijk voor iemand om mijn library te kunnen gebruiken?  -->
