---
title: 'Dependency management'
weight: 2
draft: false
author: Wouter Groeneveld en Arne Duyver
---

Lees ook: [Meer uitleg over de Gradle build tool](/dependency-management/gradle/).

> <i class="fa fa-question-circle" aria-hidden="true"></i>
 Wat is de beste manier om afhankelijkheden te beheren?

## Wat is een 'dependency'?

Een dependency, of _afhankelijkheid_, is een externe bibliotheek die wordt gebruikt tijdens de ontwikkeling van een toepassing. Tijdens het vak 'Software ontwerp in Java' zijn reeds de volgende externe libraries gebruikt:

1. [JavaFX](https://openjfx.io)
2. [Google Gson](https://github.com/google/gson)
3. [JUnit](https://junit.org/junit5/)

Het vertrouwen op zo'n library houdt in dat een extern bestand, zoals een `.jar` of `.war` bestand, gedownload en gekoppeld wordt aan de applicatie. In Java koppelen we externe libraries door middel van het `CLASSPATH`: een folder die de compiler gebruikt om te zoeken naar klassen. 

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

```bash
arne@LT3210121:~/ses/depmanag$ javac Main.java 
Main.java:3: error: cannot find symbol
        Gson gson = new Gson();
        ^
  symbol:   class Gson
  location: class Main
Main.java:3: error: cannot find symbol
        Gson gson = new Gson();
                        ^
  symbol:   class Gson
  location: class Main
2 errors
```

De klasse `Gson` is immers iets dat we niet hebben zelfgemaakt, maar wensen te importeren via het `import com.google.gson.*;` statement. Er is een manier nodig om de [gedownloade library](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.9.0) te linken met onze bestaande code: `javac -cp gson-2.9.0.jar Main.java`. _(Je kan de jar ook rechtstreeks downloaden met behulp van `curl` via `$ curl https://repo1.maven.org/maven2/com/google/code/gson/gson/2.9.0/gson-2.9.0.jar --output gson-2.9.0.jar`. Indien je curl nog niet geïnstalleerd hebt doe dat dan eerst!)_

Het programma uitvoeren kan nu met `java -cp "gson-2.9.0.jar:." Main`. Er worden dus 2 zaken aan het classpath meegegeven: de Google jar, en de huidige directory (`.`), om `Main.class` terug te vinden.

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
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.0</version>
    </dependency>
  </dependencies>
</project>
```

Maven is erg populair in de Java wereld, waardoor er verschillende servers zijn die deze `pom` bestanden samen met hun libraries beheren, zoals de [Central Maven Repository](https://mvnrepository.com) en de [Google Maven Repository](https://maven.google.com/web/index.html) mirrors. De syntax van het configuratiebestand is echter erg onoverzichtelijk, en er zijn ondertussen betere alternatieven beschikbaar, zoals Gradle.

#### Gradle

**Belangrijk**: neem dit eerst door - [Meer informatie over Gradle](/dependency-management/gradle/). 

Gradle is net zoals Maven een automatisatie tool voor de Java wereld (en daarbuiten), die verder bouwt op de populariteit van Maven door bijvoorbeeld compatibel te zijn met de Repository servers, maar de grootste pijnpunten wegneemt: een slordig configuratiebestand in XML, en complexe command-line scripts.

De volgende procedure volg je als je Gradle dependencies laat beheren:

1. Zoek op de [Maven Repository](https://mvnrepository.com) website naar de gewenste library. 
2. Voeg één regel toe in je `gradle.build` bestand, in het dependencies stuk:

```
dependencies {
    implementation 'com.google.code.gson:gson:2.9.0'
}
```

Bij het uitvoeren van `gradlew` download Gradle automatisch de juiste opgegeven versie. Gradle bewaart lokale kopies van libraries in een submap van je home folder: `~/.gradle`. Dit kan je controleren door `ls ~/.gradle/caches/modules-2/files-2.1/`. Waar je nu dus ook de `com.google.code.gson`-directory terug vind. Met `tree ~/.gradle/caches/modules-2/files-2.1/com.google.code.gson` kan je eens inspecteren hoe die directory er juist uitziet. _(Indien je `tree` nog niet geïnstalleerd hebt doe dat dan eerst!)_

```
arne@LT3210121:~/ses/depgradle$ tree ~/.gradle/caches/modules-2/files-2.1/com.google.code.gson
/home/arne/.gradle/caches/modules-2/files-2.1/com.google.code.gson
├── gson
│   └── 2.9.0
│       ├── 8a1167e089096758b49f9b34066ef98b2f4b37aa
│       │   └── gson-2.9.0.jar
│       └── bfedf86dd09fdbb51b11621570b75d0697bf7a2a
│           └── gson-2.9.0.pom
└── gson-parent
    └── 2.9.0
        └── c6a7218f3573c254d33ffac6aa6efe7cb4f8186b
            └── gson-parent-2.9.0.pom

8 directories, 3 files
```

Voordelen van het gebruik van deze methode:

1. Het zoeken van libraries beperkt zich tot één centrale (Maven Repository) website, waar alle verschillende versie revisies duidelijk worden vermeld.
2. Het downloaden van libraries beperkt zich tot één centrale locatie op je harde schijf (`~/.gradle/caches/modules-2/files-2.1/`): 10 verschillende Java projecten die gebruik maken van Gson vereisen linken naar dezelfde gradle bestanden. Je hebt dus geen 10 kopieën nodig van de Gson.jar.
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

`mavenCentral()`, `jcenter()`, en `google()` zijn ingebouwde repositories. [Eigen Maven folders](#publiceren-naar-een-maven-repository) en URLs toevoegen kan ook, evenals een lokale folder:
<a name="flatdir"></a>

```groovy
repositories {
    flatDir {
        dirs 'lib'
    }
}
```

Door nu de nodige `.jar` files toe te voegen aan de folder `./app/lib` kunnen de juiste dependencies ook gevonden worden. Indien de `./app/lib` directory nog niet bestaat, ga je die eerst moeten toevoegen.

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

Gradle voorziet een plugin genaamd '_maven-publish_' die deze bestanden automatisch aanmaakt, zodat je jouw project kan uploaden naar een online repository, zoals Maven central, of een lokale maven repository. op die manier kunnen andere projecten jouw project dan weer als dependency gaan gebruiken. Activeer de plugin en voeg een `publishing` tag toe met de volgende properties:

```groovy
plugins {
    id 'java'
    id 'maven-publish' // toevoegen!
}

group = 'be.ses'
version = '1.0-SNAPSHOT'

sourceCompatibility = 21

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
            url = "/home/arne/local-maven-repo"
        }
    }
}
```
**Indien die directory nog niet bestaat wordt deze aangemaakt!**

Windows gebruikers dienen in de `url` value te werken met dubbele backslashes (`\\`) in plaats van forward slashes (`/`) om naar het juiste pad te navigeren.

Deze uitbreiding voegt de target `publish` toe aan Gradle. Dus: `./gradlew publish` publiceert de nodige bestanden in de aangegeven folder. 

De aangemaakte lokale Maven repository ziet er nu zo uit:

```bash
arne@LT3210121:~/ses/depgradle$ tree /home/arne/local-maven-repo
/home/arne/local-maven-repo
└── be
    └── ses
        └── projectnaam
            ├── 1.0-SNAPSHOT
            │   ├── maven-metadata.xml
            │   ├── maven-metadata.xml.md5
            │   ├── maven-metadata.xml.sha1
            │   ├── maven-metadata.xml.sha256
            │   ├── maven-metadata.xml.sha512
            │   ├── projectnaam-1.0-20250227.143923-1.jar
            │   ├── projectnaam-1.0-20250227.143923-1.jar.md5
            │   ├── projectnaam-1.0-20250227.143923-1.jar.sha1
            │   ├── projectnaam-1.0-20250227.143923-1.jar.sha256
            │   ├── projectnaam-1.0-20250227.143923-1.jar.sha512
            │   ├── projectnaam-1.0-20250227.143923-1.module
            │   ├── projectnaam-1.0-20250227.143923-1.module.md5
            │   ├── projectnaam-1.0-20250227.143923-1.module.sha1
            │   ├── projectnaam-1.0-20250227.143923-1.module.sha256
            │   ├── projectnaam-1.0-20250227.143923-1.module.sha512
            │   ├── projectnaam-1.0-20250227.143923-1.pom
            │   ├── projectnaam-1.0-20250227.143923-1.pom.md5
            │   ├── projectnaam-1.0-20250227.143923-1.pom.sha1
            │   ├── projectnaam-1.0-20250227.143923-1.pom.sha256
            │   └── projectnaam-1.0-20250227.143923-1.pom.sha512
            ├── maven-metadata.xml
            ├── maven-metadata.xml.md5
            ├── maven-metadata.xml.sha1
            ├── maven-metadata.xml.sha256
            └── maven-metadata.xml.sha512

5 directories, 25 files
```

Een Gradle project dat nu gebruik wilt maken van de libraries in die lokale Maven repository dient enkel een tweede Maven Repository plaats te definiëren:

<div class="devselect">

```java
repositories {
    mavenCentral()
    maven {
        url = "/home/arne/local-maven-repo"
    }
}
```

</div>

### shadowJar
Standaard probeert een build tool zoals Gradle zo weinig mogelijk te compilen in een jar file. De dependencies worden namelijk standaard niet bij in jouw jar file gestoken. Dat is logisch want geeft Gradle de voorkeur aan het scheiden van de applicatiecode en de dependencies. Hierdoor blijft de jar file kleiner en wordt het eenvoudiger om dependencies te beheren en bij te werken.

Wil je nu toch dat een gebruiker bijvoorbeeld niet zelf aan dependency management moet doen, dan is het mogelijk om ook alle dependecies te compilen binnenin één grote jar file. Dit wordt een **shadowJar** genoemd. Een gebruiker heeft dan alles om je code uit te voeren zolang hij/zij over de juiste versie van de JVM beschikt. 

Je kan Gradle zo een `shadowJar` laten aanmaken via: 
1. Voeg de Shadow plugin toe aan je `build.gradle` bestand:
```groovy
plugins {
    id 'java'
    id 'com.github.johnrengelman.shadow' version '7.1.2'
}
```
2. Configureer de plugin om een shadow jar te maken:
```groovy
shadowJar {
    archiveClassifier.set('')
    mergeServiceFiles()
}
```
3. Bouw je project met de shadow jar taak: `./gradlew shadowJar`

**Hieronder zie je het verschil tussen compileren tot een shadowJar of een gewone jar:**
```bash
arne@LT3210121:~/ses/depgradle/app/build/libs$ ls
app-1.0-SNAPSHOT-shadow.jar  app-1.0-SNAPSHOT.jar

# Trying to run normal jar via cli manually
arne@LT3210121:~/ses/depgradle/app/build/libs$ java -jar app-1.0-SNAPSHOT.jar 
Exception in thread "main" java.lang.NoClassDefFoundError: com/google/gson/Gson
        at be.ses.depgradle.App.main(App.java:10)
Caused by: java.lang.ClassNotFoundException: com.google.gson.Gson
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:526)
        ... 1 more

# Trying to run shadowJar via cli manually
arne@LT3210121:~/ses/depgradle/app/build/libs$ java -jar app-1.0-SNAPSHOT-shadow.jar 
1
```

_Aangezien we toch meestal gebruik gaan maken van build tools zoals Gradle die steeds zelf de nodige dependecies download en toevoegt is het gebruik van de shadowJar echter beperkt_

### Oefening 1 <!-- TODO: hoger lager -->

Ontwerp een eenvoudige library genaamd '_scorebord_' die scores kan bijhouden voor bordspelletjes. Deze library kan dan gebruikt worden door toekomstige digitale Java bordspellen. In een Scorebord kan je spelers toevoegen door middel van een naam en een score. Er is een mogelijkheid om de huidige score van een speler op te vragen, en de winnende speler. Deze gegevens worden met behulp van Gson in een `JSON` bestand bewaard, zodat bij het heropstarten van een spel de scores behouden blijven. <br/>De API (publieke methodes) van de library ziet er zo uit:

<div class="devselect">

```java
public class Player {
    public String getName() { }
    public int getScore() { }
}
public class Scorebord {
    public void add(String name, int currentScore) { }
    public int getTotalScore(String name) { }
    public String getWinner() { }
    public static void saveToJson(String filename) { }
    public static Scorebord getScorebordFromJson(String filename) { }
}
```

</div>

De klasse `Speler` is een intern hulpmiddel om te serialiseren. <br/>
Extra methodes toevoegen mag altijd. De constructor van het scorebord leest automatisch de score van de vorige keer in, als dat bestand bestaat. Denk bij de implementatie aan een collectie om spelers en hun scores bij te houden. Maak via de CLI een nieuw **Gradle - Java project**. Groupid: `be.ses`. Geef je project de naam: `scorebord`:

Met het commando `gradlew jar` creëer je het bestand `scorebord-1.0-SNAPSHOT.jar` in de `build/libs` folder. 

{{% notice note %}}
Denk na over het bijhouden van `Speler`s in `Scorebord`. Een simpele `ArrayList` zal volstaan. Gebruik `Gson` in een methode als `save()` om gewoon de lijst (of het object zelf) naar de HDD te serialiseren. Tip: [java.nio.files.write](https://www.logicbig.com/how-to/code-snippets/jcode-java-io-files-write.html).
{{% /notice %}}

_Tip: je update best je gradle wrappen naar versie 8.12._

### Opgave 2

Voeg dat bovenstaand scorebord project als een library toe aan [de `higher_lower`-applicatie van vorig hoofdstuk](/4-dependency-management/gradle.md#oefening). Bewaar de jar `scorebord.jar`-jarfile lokaal in een `./app/lib` folder en instrueer Gradle zo dat dit als `flatDir` repository wordt opgenomen ([zie boven](#custom-repository-urls-voorzien)). Update nu ook de Main klasse in de `higher_lower`-applicatie:

<details closed>
<summary><i><b> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

```java

package be.ses.higher_lower;

import java.util.Random;
import java.util.Scanner;
import be.ses.scorebord.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Scorebord scorebord = Scorebord.getScorebordFromJson("highscores.json");

        System.out.println("Welcome to the Higher or Lower game!");
        System.out.println("This is the current leaderboard: \n" + scorebord + "\n\n");
        System.out.println("Guess if the next number will be higher or lower.");

        int currentNumber = random.nextInt(100) + 1;
        boolean playing = true;
        int score = 0;

        while (playing) {
            System.out.println("Current number: " + currentNumber);
            System.out.print("Will the next number be higher or lower? (h/l): ");
            String guess = scanner.next().toLowerCase();

            int nextNumber = random.nextInt(100) + 1;

            if ((guess.equals("h") && nextNumber > currentNumber)
                    || (guess.equals("l") && nextNumber < currentNumber)) {
                System.out.println("Correct! The next number was: " + nextNumber);
                score++;
            } else {
                System.out.println("Wrong! The next number was: " + nextNumber);
                playing = false;
            }

            currentNumber = nextNumber;
        }

        System.out.println("Game over! Your final score: " + score);
        System.out.println("What is your name?");
        String name = scanner.next();
        scorebord.add(name, score);
        System.out.println("This is the new leaderboard: \n" + scorebord + "\n\n");
        Scorebord.saveToJson("highscores.json", scorebord);
        scanner.close();
    }
}

```

</p>
</details>

Als de dependencies goed liggen, kan je een nieuw `Scorebord` aanmaken, en herkent VSCode dit met CTRL+Space. Hieronder een voorbeeld van `Gson`:

<figure style="display: flex; align-items: center; flex-direction: column;">
    <img src="/img/gradlevscodectrlspace.png" style="max-height: 40rem;"/>
    <figcaption ><strong><i>Gradle extention for VSCode <code>Ctrl+space</code></i></strong></figcation>
</figure>

_Het project builden of runnen werkt echter **niet** omdat we een library gebruiken (ScoreBord), die op zijn beurt een library gebruikt (Gson), die niet in onze huidige Gradle file is gedefiniëerd. Om dit op te lossen dienen we over te schakelen naar een lokale Maven repository, die ook transitieve dependencies automatisch inlaadt. Verwijder de `flatDir` en voeg een lokale maven URL toe. Publiceer het scorebord project naar diezelfde URL volgens de instructies van de `maven-publish` plugin._

<!-- EXSOL -->
<!-- <details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen van de `build.gradle`</b></i>🔽</summary>
<p>

```groovy
/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.2/userguide/building_java_projects.html
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    id 'application'
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    // flatDir {
    //     dirs 'lib'
    // }
    maven {
        url = "/home/arne/local-maven-repo"
    }
}

dependencies {
    // Use JUnit test framework.
    testImplementation 'junit:junit:4.13.2'

    // This dependency is used by the application.
    implementation 'com.google.guava:guava:30.1.1-jre'

    // Als je kiest voor flat-dir
    //implementation 'be.ses:scorebord' 
    implementation 'be.ses:scorebord:1.0-SNAPSHOT'
}

application {
    // Define the main class for the application.
    mainClass = 'be.ses.higher_lower.App'
}

run {
    standardInput = System.in
}

jar {
  manifest {
    attributes(
      'Main-Class': application.mainClass
    )
  }
}
```

</p>
</details> -->

<!-- ### Opgave 3 (extra)

Bovenstaande screenshot geeft aan dat IntelliJ methodes herkent van de `Scorebord` klasse. Er is echter geen javadoc voorzien die uitlegt wat welke parameter doet. Voorzie javadoc bij alle publieke methodes. Dit moet ook mee worden verpakt in het `jar` bestand, zodat het ander project deze kan herkennen. Probeer uit te zoeken wat je hier voor moet doen in het `build.gradle` bestand. -->

## Denkvragen

- Hoe zou je transitieve dependencies handmatig kunnen beheren? Wat zijn de voor- en nadelen?
<!-- EXSOL -->
<!-- _**<span style="color: #03C03C;">Solution:</span>**_  Het handmatig beheren van transitieve dependencies houdt in dat je expliciet alle dependencies en hun afhankelijkheden in je buildscript definieert zoals bijvoorbeeld een `makefile`, wat je volledige controle geeft over de gebruikte versies (**voordeel**) en consistentie in je build waarborgt. **Nadelen:** Dit kan echter tijdrovend zijn en minder flexibel, omdat je elke wijziging handmatig moet bijwerken, wat ook de kans op fouten vergroot. Voor de meeste projecten is het efficiënter om Gradle de transitieve dependencies automatisch te laten beheren, tenzij je specifieke controle nodig hebt. -->
- Wat gebeurt er als project1-1.0 afhankelijk is van lib1-1.0 en lib1-2.0, en lib1-1.0 van lib2-1.0 - een oudere versie dus? 
<!-- EXSOL -->
<!-- _**<span style="color: #03C03C;">Solution:</span>**_ In dit geval ontstaat er een afhankelijkheidsconflict, ook wel bekend als een "dependency hell". Dit gebeurt omdat project1-1.0 afhankelijk is van zowel lib1-1.0 als lib1-2.0, terwijl lib1-1.0 op zijn beurt afhankelijk is van lib2-1.0, een oudere versie van lib2. -->
- Als ik publiceer naar een lokale folder, welke bestanden zijn dan absoluut noodzakelijk voor iemand om mijn library te kunnen gebruiken? 
<!-- EXSOL -->
<!-- _**<span style="color: #03C03C;">Solution:</span>**_ Bij het publiceren van een library naar een lokale folder, zijn de essentiële bestanden: de gecompileerde library bestanden (zoals .jar, .dll, of .so), een README met gebruiksinstructies, een licentiebestand (LICENSE), eventuele configuratiebestanden, een lijst of map met benodigde afhankelijkheden en gedetailleerde documentatie over de API en functionaliteiten. Deze zorgen ervoor dat anderen je library correct kunnen gebruiken. -->


<!-- EXTRA -->
<!-- - Heb je altijd test dependencies nodig? Wat gebeurt er met een test dependency, libtest-1.0, van lib1-1.0, als project1-1.0 afhankelijk is van lib1-1.0? -->