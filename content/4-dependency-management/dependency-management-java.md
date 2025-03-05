---
title: 'In Java (met Gradle)'
weight: 1
draft: false
author: Wouter Groeneveld, Arne Duyver, Koen Yskout
toc: true
autonumbering: true
---

## Een voorbeeld

Een dependency, of _afhankelijkheid_, is een externe bibliotheek die wordt gebruikt tijdens de ontwikkeling van een toepassing. Tijdens het vak 'Software ontwerp in Java' zijn reeds de volgende externe libraries gebruikt:

1. [JavaFX](https://openjfx.io)
2. [Google Gson](https://github.com/google/gson)
3. [JUnit](https://junit.org/junit5/)

Het vertrouwen op zo'n library houdt in dat een extern bestand, zoals een `.jar` of `.war` bestand, gedownload en gekoppeld wordt aan de applicatie. In Java koppelen we externe libraries door middel van het `CLASSPATH`: dat is een lijst van folders en jars die de compiler (bij het compilen) en de Java runtime (bij het uitvoeren) gebruikt om te zoeken naar de implementatie van de gebruikte klassen.

Laten we bijvoorbeeld eens kijken naar het gebruik van de Gson library om Json te genereren vanuit Java.
Serialisatie met behulp van Gson kan op deze manier:

```java
import com.google.gson.Gson;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
      Gson gson = new Gson();
      System.out.println(gson.toJson(Arrays.asList(1, 2, 3)));
  }
}
```

Als we bovenstaande `Main.java` compileren zonder meer, krijgen we echter de volgende fout:

```bash
arne@LT3210121:~/ses/depmanag$ javac Main.java 
Main.java:1: error: package com.google.gson does not exist
import com.google.gson.Gson;
                      ^
Main.java:6: error: cannot find symbol
      Gson gson = new Gson();
      ^
  symbol:   class Gson
  location: class Main
Main.java:6: error: cannot find symbol
      Gson gson = new Gson();
                      ^
  symbol:   class Gson
  location: class Main
3 errors
```

De klasse Arrays die we importeren is deel van de standaard Java-omgeving. Die zorgt dus niet voor problemen.
Maar de klasse `com.google.gson.Gson` hebben we niet zelf gemaakt, en willen we importeren uit een library. 
We moeten de library daarom eerst [downloaden](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.12.1).
_(Je kan de jar ook rechtstreeks downloaden met behulp van `curl` via `$ curl https://repo1.maven.org/maven2/com/google/code/gson/gson/2.12.1/gson-2.12.1.jar --output gson-2.12.1.jar`. Indien je curl nog niet geïnstalleerd hebt doe dat dan eerst!)_

Het programma compileren kan nu met `javac -cp gson-2.12.1.jar Main.java`. We geven de gedownloade jar mee aan het classpath (via de `-cp` optie). Dat zorgt ervoor dat de compiler de geïmporteerde klasse Gson nu wel kan terugvinden, en het compileren slaagt.

Om het programma uit te voeren, gebruiken we `java -cp "gson-2.12.1.jar:." Main`. Merk op dat er nu 2 zaken aan het classpath worden meegegeven: de Google jar, maar ook de huidige directory (`.`) om `Main.class` terug te vinden.

{{% notice warning "Opgelet!" %}}
Java classpath separators zijn [OS-specifiek](https://howtodoinjava.com/java/basics/java-classpath/)! Unix: `:` in plaats van Windows: `;`.
{{% /notice %}}

Dit programma kan schematisch worden voorgesteld als volgt:

{{<mermaid>}}
graph LR;
    A["Main klasse"]
    C["Gson 2.12.1"]
    A -->|depends on| C
{{< /mermaid >}}


De dependency in bovenstaand voorbeeld is `gson-2.12.1.jar`. Een gemiddelde Java applicatie heeft echter **meer dan 10 dependencies!** Het beheren van deze bestanden en de verschillende versies (major, minor, revision) geeft vaak conflicten die beter beheerd kunnen worden door tools dan door de typische vergeetachtigheid van mensen. Dit kluwen aan afhankelijkheden, dat erg snel onhandelbaar kan worden, noemt men een [Dependency Hell](https://en.wikipedia.org/wiki/Dependency_hell). Er zijn varianten: [DLL Hell](https://en.wikipedia.org/wiki/DLL_Hell) sinds 16-bit Windows versies, RPM Hell voor Redhat Linux distributies, en [JAR Hell](https://en.wikipedia.org/wiki/Java_Classloader#JAR_hell) voor Java projecten.

Zie ook xkcd's [Tech Loops](https://www.explainxkcd.com/wiki/index.php/1579:_Tech_Loops) rommelboeltje:

![](/img/tech_loops.png)

## Manueel Java dependencies beheren

De eenvoudigste manier om een library te gebruiken is de volgende procedure te volgen:

1. Navigeer naar de website van de library en download de jar in een bepaalde map, zoals `/lib`.
2. Importeer de juiste klasses met het `import` statement.
3. Compileer de code door middel van het `-cp lib/dependency1.jar` argument.

Voor kleine programma's met slechts enkele libraries is dit meer dan voldoende. Het kost echter redelijk veel moeite om de juiste versie te downloaden: stap 1 kost meestal meer dan 5 minuten werk. 

{{% notice note %}}
Merk op dat jar files in een submap steken de syntax van de `-cp` parameter lichtjes wijzigt: bij compileren wordt het `javac -cp "lib/*" Main.java` en bij uitvoeren wordt het `java -cp "lib/*:." Main`. Zonder de toegevoegde punt (`.`) bij het `java` commando wordt de main methode in `Main` zelf niet gevonden. Wildcards zijn toegestaan. Zie ook [Understanding the Java Classpath](https://dev.to/martingaston/understanding-the-java-classpath-building-a-project-manually-3c3l). In de praktijk worden build tools als Gradle gebruikt om projecten automatisch te builden, inclusief het doorgeven van de juiste parameters/dependencies.
{{% /notice %}}

Als er een nieuwe versie van de library verschijnt die je wil gebruiken (bv. met een nieuwe feature of een bugfix), moet je dat eerst en vooral zelf nagaan, de jar van de nieuwe versie downloaden, je classpath aanpassen, en alles opnieuw compileren.
Ook bij de uitvoering moet je zorgen dat je de nieuwe versie gebruikt.

## Apache Maven

[Maven](https://en.wikipedia.org/wiki/Apache_Maven) is een build tool van de Apache Foundation om de software te compileren en afhankelijkheden te beheren. Maven is de voorloper van Gradle en bestaat reeds 15 jaar. 

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
      <version>2.12.1</version>
    </dependency>
  </dependencies>
</project>
```

Maven is erg populair in de Java wereld, waardoor er verschillende servers zijn die deze `pom` bestanden samen met hun libraries beheren, zoals de [Central Maven Repository](https://mvnrepository.com) en de [Google Maven Repository](https://maven.google.com/web/index.html) mirrors. De syntax van het configuratiebestand is echter erg onoverzichtelijk, en er zijn ondertussen betere alternatieven beschikbaar, zoals Gradle.

We gaan in deze cursus dus geen gebruik maken van Maven, maar je zal wel verwijzingen naar de Maven repositories tegenkomen bij het gebruik van Gradle.

## Gradle

Gradle is, net zoals Maven, een **build tool** voor de Java wereld (en daarbuiten) die de automatisatie van releasen, builden, testen, configureren, dependencies en libraries managen, ... eenvoudiger maakt. Kort gezegd: het maakt het leven van een ontwikkelaar eenvoudiger. 

Gradle bouwt verder op de populariteit van Maven door bijvoorbeeld compatibel te zijn met de Repository servers, maar de grootste pijnpunten wegneemt: een moeilijk leesbaar configuratiebestand in XML en complexe command-line scripts.

In een config bestand genaamd `build.gradle` schrijft men met **Groovy**, een dynamische taal bovenop de JVM (Java Virtual Machine), op een descriptieve manier hoe Gradle de applicatie moet beheren.
Je build-file is dus een uitvoerbaar (Groovy) script dat gebruik maakt van Gradle-specifieke functies.

{{% notice info %}}
Gradle configuratiebestanden kunnen ook in **Kotlin** geschreven worden (`build.gradle.kts`) in plaats van Groovy.
Deze optie wint aan populariteit, en is sinds Gradle 8.2 (2023) de default, maar we gebruiken in deze cursus nog de Groovy syntax. De verschillen zijn klein.
{{% /notice %}}

### Voordelen van Gradle

De grootste voordelen van een build en dependency management tool zoals Gradle zijn onder andere:

- Een kleine voetafdruk van de broncode (repository). Het is niet nodig om zelf alle jars van libraries te downloaden en bij te houden in een `lib/` folder: Gradle doet dit immers voor jou. 
- Een project _bootstrappen_ in luttele seconden: download code, voer de Gradle wrapper uit, en alles wordt vanzelf klaargezet (de juiste Gradle versie, de juiste library versies, ...)
    - _Een project bootstrappen betekent het opzetten of initialiseren van een project vanaf het begin, vaak met behulp van een tool of framework dat de basisstructuur en configuratie voor je genereert. Dit proces helpt je snel aan de slag te gaan zonder dat je alles handmatig hoeft in te stellen._
- Platform- en machine-onafhankelijk projecten bouwen en uitvoeren: een taak uitvoeren via een build tool op mijn PC doet exact hetzelfde als bij jou, dankzij de beschrijving van de stappen in de config file. 

Om een library als `Gson` te kunnen gebruiken, moet je dus niet zelf de jar-bestanden aanleveren; het volstaat simpelweg om twee regels in de gradle-configuratie toe te voegen.

### Gradle installeren

Je kan Gradle op je WSL installeren met het commando `sudo snap install gradle --classic`. (Zie ook [Gradle Docs: installing manually](https://gradle.org/install/#manually)) Dit installeert echter niet altijd de nieuwste versie van Gradle, in dit geval `v7.2`, maar dat is geen probleem (hier komen we zo dadelijk op terug).

Er zijn ook andere manieren om Gradle te installeren, bijvoorbeeld met [SDKMAN!](https://sdkman.io/). Dit is een tool om allerhande (Java-gebaseerde) Software Development Kits (SDKs) op je systeem te installeren en beheren, en eenvoudig te wisselen tussen versies. Eens SDKMAN! geïnstalleerd is, kan je eenvoudig de laatste versie van gradle installeren met `sdk install gradle`.

#### Gradle in VSCode
Je kan ondersteuning toevoegen voor Gradle in VSCode met de juiste extensie, zie [Java development environment in VSCode](/1-wsl-vscode/vscode.md#java-development-environment).
In het bijzonder voor Gradle is de **Gradle for Java** plugin van Microsoft aangewezen.
Deze is deel van het **Extension Pack for Java**, waarmee je ineens ook andere nuttige extensies voor Java-ontwikkeling installeert.

Deze extensie geeft je extra ondersteuning voor het editeren van je `gradle.build` bestand (syntax highlighting, code completion).
Bovendien kan je voor je projecten ook de verschillende Gradle-taken bekijken en uitvoeren, alsook nagaan welke dependencies er in welke configuratie (compile, runtime, test) geactiveerd zijn --- wat dat precies inhoudt, komt zodadelijk aan bod.

<figure style="display: flex; align-items: center; flex-direction: column;">
    <img src="/img/gradlevscode.png" style="max-height: 40rem;"/>
    <figcaption ><strong><i>Gradle extention for VSCode</i></strong></figcation>
</figure>



### Een nieuw Gradle project creëren
Je kan nu een directory initialiseren als een Gradle project met `gradle init`. (Zie ook [Gradle Docs: Bootstrapping new projects](https://docs.gradle.org/current/userguide/command_line_interface.html#sec:command_line_bootstrapping_projects)) Volg onderstaande stappen:
```bash
# Gradle vraagt eerst welk soort project je wil aanmaken, we kiezen voor 2. application
Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

# Nu vraagt Gradle welke programmeertaal we willen gebruiken, we kiezen voor 3. Java
Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 3

# Nu vraagt Gradle te kiezen tussen een het soort project, we kiezen voor 1. only one application project
Split functionality across multiple subprojects?:
  1: no - only one application project
  2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 1

# Nu vraagt Gradle te kiezen tussen programmeertalen voor ons build script (de taal waarmee we onze build.gradle file gaan programmeren), we kiezen voor 1. Groovy
Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1

# Nu vraagt Gradle te kiezen tussen een testframework, we kiezen voor 1. JUnit 4
Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit Jupiter) [1..4] 4

# Nu vraagt Gradle een projectnaam, default is dit de directorynaam. Vul niets in en druk op enter om de default te gebruiken.
Project name (default: gradletest):
# Als laatste vraagt Gradle je de naam van de source package te kiezen. Gelijkaardig als in INF 1 kiezen we voor be.ses.<app_name>
Source package (default: gradletest): be.ses.my_application
```

Gradle vraagt ons tijdens de init een aantal opties te kiezen. Alhoewel we in deze lessen 90% van de tijd de opties kiezen zoals hierboven getoond in het voorbeeld, kan je hieronder toch een overzicht terugvinden met enkele andere opties:
- **Select type of build to generate**: opties - Application - Library (gaan we niet verder op in) - Gradle Plugin (gaan we niet verder op in) - Basic
    - **Application**: Dit type is geconfigureerd om een volwaardige uitvoerbare applicatie te bouwen. Het bevat extra configuraties en plugins, zoals de application plugin, die helpt bij het definiëren van de hoofdmethode en het maken van uitvoerbare JAR-bestanden. Geschikt voor het ontwikkelen van volledige applicaties die je kunt uitvoeren en distribueren. _(99% van de tijd gaan we dit type gebruiken)_
    - **Basic**: Dit is een eenvoudig project zonder specifieke plugins of configuraties. Het bevat alleen de minimale structuur en bestanden die nodig zijn om een Gradle-project te starten.  Je kan dan enkel nog de projectnaam kiezen en de programmeertaal voor Gradle.
- **Select implementation language**: Je ziet dat Gradle dus ook voor andere programmeertalen gebruikt kan worden. Wij kiezen hier echter voor Java.
- **Select application structure**: opties - 1. only one application project - application and library projects
    - **Only one application project**: Dit type project is gericht op het bouwen van één enkele applicatie. De projectstructuur is eenvoudig en bevat meestal alleen de bronbestanden en configuratiebestanden die nodig zijn om de applicatie te bouwen en uit te voeren. Geschikt voor kleinere projecten of wanneer er geen behoefte is aan herbruikbare componenten.
    - **Application and library project**: Dit type project is opgesplitst in meerdere modules, waaronder een applicatiemodule en een of meer bibliotheekmodules. De bibliotheekmodules bevatten herbruikbare code die door de applicatiemodule kan worden gebruikt. Deze structuur bevordert modulariteit en hergebruik van code, wat vooral nuttig is voor grotere projecten of wanneer je van plan bent om delen van je code in andere projecten te gebruiken. 

_**Bij het aanmaken van je project maakt Gradle een aantal folders en bestanden aan. We overlopen hieronder de functionaliteit van de belangrijkste onderdelen.**_ 

### Ontleding van een Gradle project mappenstructuur

Als we kijken naar de bestanden- en mappenstructuur van een voorbeeld Gradle project, vinden we dit terug:

```bash
.
├── app
│   ├── build.gradle
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── be
│       │   │       └── ses
│       │   │           └── my_application
│       │   │               └── App.java
│       │   └── resources
│       └── test
│           ├── java
│           │   └── be
│           │       └── ses
│           │           └── my_application
│           │               └── AppTest.java
│           └── resources
├── build
│   
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

Hier onderscheiden we de volgende zaken:

1. Broncode (`.java` bestanden) van het `app` subproject in `src/main/java` en `src/test/java`, met productie- en testcode gescheiden. 
2. Eventueel `resources` (bv. afbeeldingen, html en css voor webapplicaties ...)
3. Gecompileerde code (`.class` bestanden) in de `build/` (of ook wel `out`) folder.
4. Een `gradle` map, met daarin de Gradle-wrapper en bijhorende configuratie (hier komen we zodadelijk op terug). Deze folder hoor je toe te voegen aan je versiebeheersysteem (git).
5. Twee executables (`gradlew.bat` voor Windows en een `gradlew`-shell script voor Linux/Unix). Ook deze twee executables voeg je toe aan git.
6. Twee Gradle configuratie-bestanden:
    - `settings.gradle` voor het project als geheel. Hierin kan je de projectnaam aanpassen: `rootProject.name = 'new_name'`. Je lijst hier ook de verschillende subprojecten op: `include('app')`.
    - `build.gradle`: eentje per subproject (hier enkel _app_). Hierin specifieer je de dependencies en andere instellingen om dat specifieke subproject te bouwen.
7. Later kan er ook een verborgen `.gradle` map verschijnen. De inhoud hiervan hoort **niet** thuis in je versiebeheersysteem!

### Gradle wrapper (gradlew)

Wanneer je een Gradle project aanmaakt, creëert Gradle vanzelf ook een **wrapper**. Dat is een soort lokale executable in de vorm van een `./gradlew`-executable (of `gradlew.bat` voor op Windows).
Dit wrapper-script zal, wanneer het uitgevoerd wordt, een specifieke versie van Gradle downloaden in de `gradle`-map.

Dit heeft enkele voordelen: 
- **Consistentie**: Het zorgt ervoor dat iedereen die aan het project werkt dezelfde versie van Gradle gebruikt, ongeacht de versie die lokaal is geïnstalleerd. (Door steeds de `gradlew` in de projectdirectory te gebruiken)
- **Gemak**: Gebruikers hoeven Gradle niet apart te installeren, omdat de wrapper automatisch de juiste versie downloadt en gebruikt. Enkel om initieel een project aan te maken heb je een lokale versie van Gradle nodig.
- **Automatisering**: Het maakt het eenvoudiger om build-scripts en CI/CD-pijplijnen te configureren (zie een later hoofdstuk), omdat de wrapper de benodigde Gradle-versie beheert.

De specifieke versie van Gradle die je gebruikt hangt (onder andere) af van de Java-versie die je gebruikt in je project.
[Hier](https://docs.gradle.org/current/userguide/compatibility.html) vind je een overzicht van de minimum-versies van Gradle die compatibel zijn met welke Java-versies (JVM provided by JRE or JDK).

Eens je weet welke versie van Gradle je wil gebruiken, kan je de lokale `gradlew` updaten met het volgende commando: `./gradlew wrapper --gradle-version x.x` (bijvoorbeeld `8.13` voor Java versies 23 of ouder). 

Kijk na of je de gewenste versie gebruikt met `./gradlew --version`.

{{% notice tip %}}
Soms is je Java-versie te nieuw om een oude Gradle-wrapper uit te voeren en de Gradle-versie te updaten (bijvoorbeeld JDK `17` met Gradle `6.7` in plaats van `7.0` of nieuwer). Je krijgt dan een foutmelding zoals `General error during conversion: Unsupported class file major version 65` of ``Could not initialize class `org.codehaus.groovy.reflection.ReflectionCache` ``.

Om dit op te lossen, kan je de te gebruiken versie van Gradle ook rechtstreeks specifiëren in `gradle/wrapper/gradle-wrapper.properties`, door het versienummer in de `distributionUrl` aan te passen.

Bijvoorbeeld:
```
distributionUrl=https\://services.gradle.org/distributions/gradle-8.13-bin.zip
```
{{% /notice %}}

**Gradle laat je soms zelf ook wel weten naar welke versie je moet updaten aan de hand van je gebruikte Java code.**


Bij het uitvoeren van `gradlew` gebeurt hetvolgende:
1. De wrapper downloadt de juiste versie van Gradle zelf (dus installatie van Gradle via snap/SDKMAN!/... is **niet nodig** voor een bestaand Gradle-project), afhankelijk van de specificaties in de properties file.
2. Vervolgens downloadt Gradle de juiste libraries om het project te kunnen compilen en uitvoeren. 

Aan deze wrapper kan je commando's meegeven (gradle **tasks**, zie later). Bijvoorbeeld, `./gradlew run` om je programma (te compileren en) uit te voeren:

```bash
arne@LT3210121:~/ses/gradletest$ ./gradlew run

> Configure project :app
be.ses

> Task :app:run
Hello World!

BUILD SUCCESSFUL in 1s
2 actionable tasks: 2 executed
```

Dit is **exact hetzelfde** als in een IDE zoals IntelliJ het project runnen met de knop 'Run' (play-knop):

### Ontleding van build.gradle

De belangrijkste file voor Gradle is de `build.gradle` file die zich in de `./app` directory bevindt. Die ziet er als volgt:

```groovy
/* This file was generated by the Gradle 'init' task.
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
}

dependencies {
    // Use JUnit test framework.
    testImplementation 'junit:junit:4.13.2'

    // This dependency is used by the application.
    implementation 'com.google.guava:guava:30.1.1-jre'
}

application {
    // Define the main class for the application.
    mainClass = 'be.ses.my_application.App'
}
```

Met de Groovy syntax definiëren we verschillende configuratie-blokken als `bloknaam { ... }`. We onderscheiden volgende blokken:

1. **plugins**: hier kan je plugins voor Gradle toevoegen, je voegt ze toe op basis van `id`
    - `id 'application'`: de plugin voor Java (stand-alone) applicaties. Dit voegt taken toe zoals `build` en `test` voor je applicatie.
2. **repositories**: hiermee specificeer je waar de dependencies in de dependencies functie gezocht en gedownload moeten worden. Meestal gebruik je hier de default, namelijk de [standaard maven central repository](https://mvnrepository.com/repos/central) (ingebouwde URL).
3. **dependencies**: hiermee specificeer je de dependencies voor je project. Dependencies worden toegevoegd met een zogenaamde [**dependency configuration**](https://docs.gradle.org/current/userguide/java_plugin.html#sec:java_plugin_and_dependency_management), die aangeeft wanneer ze nodig zijn. De meest voorkomende zijn:
    - `implementation` (productie dependencies): deze dependencies zijn beschikbaar bij het compileren en uitvoeren van **alle code**.
    - `testImplementation` (test dependencies): deze dependencies zijn enkel beschikbaar bij het compileren en uitvoeren van **test-code**.
    - `runtimeOnly`: deze dependencies worden enkel gebruikt bij het **uitvoeren** van de applicatie.

    Als argument moet je de dependency opgeven in de vorm `groep:naam:versie`. Je vindt deze meestal makkelijk terug op de gebruikte [repository](https://mvnrepository.com/repos/central).

4. **application**: met `mainClass = 'be.ses.my_application.App'` geef je aan welke `main`-methode van welke klasse moet gerund worden wanneer je je applicatie wil runnen. (Dit is dus het entrypoint van je applicatie)


### Dependencies toevoegen
In het kort volg je volgende procedure als je Gradle je dependencies laat beheren:

1. Zoek op de [Maven Repository](https://mvnrepository.com) website naar de gewenste library. 
2. Voeg één regel toe in je `gradle.build` bestand, in het dependencies stuk:

```
dependencies {
    implementation 'com.google.code.gson:gson:2.12.1'
}
```

Bij het uitvoeren van gradle downloadt Gradle automatisch de juiste opgegeven versie van de Gson library en gebruikt die om je applicatie te compileren en uit te voeren.

De download komt niet terecht in je project maar in een gedeelde cache-folder, zodat elke versie van een library slechts éénmaal gedownload wordt op je systeem.
Die cache-folder staan in een submap van je home folder: `~/.gradle`. Dit kan je controleren door `ls ~/.gradle/caches/modules-2/files-2.1/`, waar je nu dus ook de `com.google.code.gson`-directory terug vindt. Met `tree ~/.gradle/caches/modules-2/files-2.1/com.google.code.gson` kan je eens inspecteren hoe die directory er juist uitziet. _(Indien je `tree` nog niet geïnstalleerd hebt doe dat dan eerst!)_

```
arne@LT3210121:~/ses/depgradle$ tree ~/.gradle/caches/modules-2/files-2.1/com.google.code.gson
/home/arne/.gradle/caches/modules-2/files-2.1/com.google.code.gson
├── gson
│   └── 2.12.1
│       ├── 4e773a317740b83b43cfc3d652962856041697cb
│       │   └── gson-2.12.1.jar
│       └── d2c3993ff96e5da39a57e5e0b695eda560949b57
│           └── gson-2.12.1.pom
└── gson-parent
    └── 2.12.1
        └── 660107b2e76095ef86bbd15c503afe11e5260bfb
            └── gson-parent-2.12.1.pom

8 directories, 3 files
```

Deze cache-folder kan groeien naarmate je meer met Gradle werkt en meer versies van libraries downloadt. Je mag deze cache-folder gerust verwijderen van je systeem; de volgende keer zullen de nodige dependencies gewoon opnieuw gedownload worden.

De voordelen van het gebruik van Gradle voor dependencies zijn dus:

1. Het zoeken van libraries beperkt zich tot één centrale (Maven Repository) website, waar alle verschillende versies duidelijk worden vermeld.
2. Het downloaden van libraries beperkt zich tot één centrale locatie op je harde schijf (`~/.gradle/caches/modules-2/files-2.1/`): 10 verschillende Java projecten die gebruik maken van Gson vereisen linken naar dezelfde gradle bestanden. Je hebt dus geen 10 kopieën nodig van de Gson.jar.
3. Het beheren van dependencies en versies beperkt zich tot één centraal configuratiebestand: `build.gradle`. Dit is (terecht) een **integraal deel van het project**!

Lees ook: [Declaring dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies.html) in de Gradle docs. 

#### Transitieve dependencies

Er bestaan twee types van dependencies: **directe** (soms ook harde dependency genoemd) en **transitieve** (zachte dependency). 

Een directe dependency is een afhankelijkheid die het project nodig heeft, zoals het gebruik van Gson, waarbij dit in de `dependencies {}` config zit.

Een _transitieve_ of indirecte dependency is een dependency van een dependency. In een van de oefeningen hieronder maken we een project (1) aan, dat een project (2) gebruikt, dat Gson gebruikt. In project 1 is project 2 een directe dependency, en Gson een transitieve. In Project 2 is Gson een directe dependency (en komt project 1 niet voor):

{{<mermaid>}}
graph LR;
    A[Project 1]
    B[Project 2]
    C[Gson]
    A --> B
    B --> C
    A -.-> C
{{< /mermaid >}}

Deze transitieve dependencies worden afgehandeld door Groovy. Het is **niet nodig** om ze toe te voegen aan je `dependencies {}` configuratie.
Meer zelfs, als je fouten krijgt gerelateerd aan dependencies, is het **geen goed idee** om de zachte (transitieve) dependency (stippellijn) te veranderen in een harde, door die als directe dependency toe te voegen in de configuratie. Gradle [biedt hier betere alternatieven voor](https://docs.gradle.org/current/userguide/dependency_constraints.html#sec:adding-constraints-transitive-deps).

### Gradle tasks


`./gradlew tasks --all` voorziet een overzicht van alle beschikbare taken voor een bepaald Gradle project, opgesplitst per fase (build tasks, build setup tasks, documentation tasks, help tasks, verification tasks). Gradle plugins voorzien vaak extra tasks, zoals bijvoorbeeld de maven plugin om te publiceren naar repositories. 

Belangrijke taken zijn onder andere:

- `test`: voer alle unit testen uit. Een rapport hiervan is beschikbaar op build/reports/tests/test/index.html. (Hier komen we zeker nog op terug in het hoofdstuk rond test-driven development)
- `clean`: verwijder alle binaries en metadata.
- `build`: compile en test het project.
- `publish`: (maven plugin) publiceer de gebouwde versie naar een repository.
- `jar`: compile en package in een jar bestand
- `javadoc`: (plugin) genereert HTML javadoc. Een rapport hiervan is beschikbaar op build/docs/javadoc/index.html.

Onderstaande screenshot is een voorbeeld van een Unit Test HTML rapport dat gegenereerd wordt elke keer de `test` task uitgevoerd wordt:

![Gradle test report](/img/teaching/ses/gradle-testreports.png)

#### Compile to JAR
Zoals we in het hoofdstuk rond [build systems in Java](/3-build-systems-makefiles/build-systems-java.md) gezien hebben kan je alle bestanden groeperen in een `.jar` bestand om er een 'executable' van te maken die door iemand anders uitgevoerd kan worden op een JVM. Om zo'n jar te maken gebruik je het commando `./gradlew jar`. Er wordt dan een `main_classname.jar` aangemaakt in je `/app/build/libs` directory.

Zonder extra opties in je `build.gradle` wordt er echter geen `'Main-Class'`-attribuut toegevoegd aan de MANIFEST file van je jar. Daarom kan je volgende optie instellen in je `build.gradle` waarbij `application.mainClass` een referentie is naar de value die je daar hebt ingesteld:
```groovy
jar {
  manifest {
    attributes(
      'Main-Class': application.mainClass
    )
  }
  archiveBaseName = 'myJarName'
}
```

{{% notice info Weetje %}}
We kunnen ook zelf een kleine task coderen in Groovy die simpelweg de 'group' van ons project uitprint:
```groovy
task example {
    println project.group
}
```
We gebruiken deze dan via `./gradlew example`.
{{% /notice %}}


#### Fat jars (shadow jars)
Merk op dat een typisch project dat gebouwd of uitgevoerd wordt via Gradle **geen jars** van de dependencies bevat. Die worden immers automatisch door Gradle gedownload en in de juiste map (de Gradle cache) geplaatst.

Bij het maken van een jar van je applicatie zal Gradle standaard de dependencies dus niet mee in jouw jar file steken. Dat is logisch: Gradle geeft de voorkeur aan het scheiden van de applicatiecode en de dependencies. Hierdoor blijft de jar file kleiner en wordt het eenvoudiger om dependencies te beheren en bij te werken.

Wil je nu toch dat een (eind)gebruiker bijvoorbeeld niet zelf aan dependency management via Gradle moet doen, dan is het mogelijk om ook toch de applicatie en _alle_ dependencies te bundelen in één grote jar file. Dit wordt een **uber jar**, **fat jar**, of **shadow jar** genoemd. De gebruiker heeft dan alles om je code uit te voeren zolang hij/zij over de juiste versie van de JVM beschikt. 

Je kan Gradle zo een `shadowJar` laten aanmaken op deze manier: 
1. Voeg de Shadow plugin toe aan je `build.gradle` bestand:
```groovy
plugins {
    id 'java'
    id 'com.gradleup.shadow' version '8.3.0'
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

_Aangezien we toch meestal gebruik gaan maken van build tools zoals Gradle die steeds zelf de nodige dependecies download en toevoegt is het gebruik van de shadowJar in deze cursus echter beperkt_


#### Meer output

De standaard output geeft enkel weer of er iets gelukt is of niet:

Je kan meer informatie krijgen met de volgende parameters:

- `--info`, output LogLevel `INFO`. Veel irrelevante info wordt ook getoond.
- `--warning-mode all`, toont detail informatie van warning messages
- `--stacktrace`, toont de detail stacktrace bij exceptions

### Repositories

Zoals eerder vermeld gebruikt Gradle Maven-repositories om de bestanden (denk: de `.jar`) van alle opgelijste dependencies op te halen.
We bekijken hier hoe je extra repositories kan toevoegen, wat in zo'n repository zit, en hoe je zelf kan publiceren naar een repository.

#### Extra repositories toevoegen

Veelgebruikte libraries zijn eenvoudig te vinden via de [Central Maven Repository](https://mvnrepository.com). Wanneer echter een eigen library werd gecompileerd, die dan in andere projecten worden gebruikt, schiet deze methode tekort: interne libraries zijn uiteraard niet op een publieke server gepubliceerd. 

Gradle voorziet gelukkig genoeg een eenvoudige manier om [repository websites toe te voegen](https://docs.gradle.org/current/userguide/declaring_repositories.html), met de volgende eenvoudige syntax:

<pre>
repositories {
  mavenCentral()
}
</pre>

`mavenCentral()`, `jcenter()`, en `google()` zijn ingebouwde repositories. [Eigen Maven folders](#publiceren-naar-een-maven-repository) en URLs toevoegen kan ook.
Tenslotte kan je ook een lokale folder toevoegen:
<a name="flatdir"></a>

```groovy
repositories {
    flatDir {
        dirs 'lib'
    }
}
```
Door nu de nodige `.jar` files toe te voegen aan de folder `./app/lib` kunnen de juiste dependencies ook gevonden worden. Indien de `./app/lib` directory nog niet bestaat, ga je die eerst moeten toevoegen.
Deze laatste manier is vooral handig wanneer je een library (`.jar`) wil gebruiken die niet via een repository beschikbaar zijn. Zo kan je deze dependencies toch nog via Gradle beheren.

#### Wat zit er in een Maven Repository

Klik op '[View All](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.12.1)' bij de Gson module op de MVN Central Repo om te inspecteren welke bestanden typisch worden aangeleverd in een Maven repository:

1. De library zelf, in een bepaalde versie (`gson-2.12.1.jar`).
2. Eventueel de javadoc en/of sources als aparte jars (`gson-2.12.1-javadoc.jar`, `gson-2.12.1-sources.jar`).
3. Een `.pom` XML bestand (`gson-2.12.1.pom`).
4. metadata over het build-proces(`gson-2.12.1.buildinfo`)
5. checksums (`md5` en `sha1`) en digitale handtekeningen (`asc`) op alle vorige bestanden

Het `.pom` XML bestand beschrijft welke afhankelijkheden deze module op zich heeft. Zo kan een hele **dependency tree** worden opgebouwd! Het beheren van _alle_ afhankelijkheden is complexer dan op het eerste zicht lijkt, en laat je dus beter over aan deze gespecialiseerde tools. Google heeft voor Gson enkel Junit als test dependency aangeduid:

```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Grote projecten kunnen makkelijk afhankelijk zijn van tientallen libraries, die op hun beurt weer afhankelijk zijn van meerdere andere libraries. 
Hieronder een voorbeeld van een dependency tree voor een typische grote webapplicatie geschreven in Java:

![](/img/teaching/ses/deptree.png)

Je kan deze opvragen via Gradle met de de task `dependencies`: `./gradlew dependencies`. Detailinformatie voor specifieke dependencies kunnen worden opgevraagd met de `dependencyInsight` task. Zie ook: [Viewing and debugging dependencies](https://docs.gradle.org/current/userguide/viewing_debugging_dependencies.html) in de Gradle docs. 

#### Publiceren naar een Maven Repository

Gradle voorziet een plugin genaamd '_maven-publish_' die bovenvermelde bestanden automatisch aanmaakt, zodat je jouw project kan uploaden naar een online repository, zoals Maven central, of een lokale maven repository. Op die manier kunnen andere projecten jouw project dan weer als dependency gaan gebruiken. Activeer de plugin en voeg een `publishing` block toe met de volgende eigenschappen:

```groovy
plugins {
    id 'java'
    id 'maven-publish' // toevoegen!
}

group = 'be.ses'
version = '1.0-SNAPSHOT'

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
            url = "/home/arne/local-maven-repo" // gebruik je eigen home-folder!
        }
    }
}
```
**Indien die directory nog niet bestaat wordt deze aangemaakt!**

Windows-gebruikers dienen in de `url` value te werken met dubbele backslashes (`\\`) in plaats van forward slashes (`/`) om naar het juiste pad te navigeren.

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

Een Gradle project dat nu gebruik wilt maken van de libraries in die lokale Maven repository dient enkel een tweede Maven repository plaats te definiëren:

```groovy
repositories {
    mavenCentral()
    maven {
        url = "/home/arne/local-maven-repo" // gebruik je eigen home-folder!
    }
}
```

In de praktijk ga je natuurlijk eerder een gedeelde (al dan niet interne) locatie gebruiken als repository, en geen map op je eigen harde schijf.
Dat laatste doet immers veel van de voordelen van het gebruik van Gradle teniet.


## Oefeningen

### Oefening 1: Hoger lager
1. Maak een nieuw Gradle project aan met de naam: `higher_lower` en gebruik de packagenaam `be.ses.higher_lower`.
2. Delete de test in `app/src/test/java/be/ses/higher_lower/App.java`. Anders zal je je project niet kunnen runnen.
3. Aangezien we input aan de gebruiker gaan vragen moeten we een extra optie instellen in de `app/build.gradle`:
```groovy
run {
    standardInput = System.in
}
```
4. Kopieer onderstaande code voor de klasse `app/src/main/java/be/ses/higher_lower/App.java`:

{{% notice style="code" title="App.java (klik om te tonen)" expanded=false %}}
```java
package be.ses.higher_lower;

import java.util.Random;
import java.util.Scanner;

public class App {
        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("Welcome to the Higher or Lower game!");
        System.out.println("Guess if the next number will be higher or lower.");
        
        int currentNumber = random.nextInt(100) + 1;
        boolean playing = true;
        int score = 0;
        
        while (playing) {
            System.out.println("Current number: " + currentNumber);
            System.out.print("Will the next number be higher or lower? (h/l): ");
            String guess = scanner.next().toLowerCase();
            
            int nextNumber = random.nextInt(100) + 1;
            
            if ((guess.equals("h") && nextNumber > currentNumber) || 
                (guess.equals("l") && nextNumber < currentNumber)) {
                System.out.println("Correct! The next number was: " + nextNumber);
                score++;
            } else {
                System.out.println("Wrong! The next number was: " + nextNumber);
                playing = false;
            }
            
            currentNumber = nextNumber;
        }
        
        System.out.println("Game over! Your final score: " + score);
        scanner.close();
    }
}
```
{{% /notice %}}

5. Test je programma: Aangezien we input vragen via de scanner moeten we ook op een speciale manier ons project runnen: gebruik `./gradlew --console plain run` (of `./gradlew -q --console plain run` om helemaal geen output van gradle tasks ertussen te zien.)

6. Maak een `jar` via Gradle.

7. Copy de `app/build/libs/app.jar`-file naar een andere directory en hernoem naar `higherLower.jar`.

8. Run de jar via de terminal.

### Oefening 2: Scorebord-library

Ontwerp een eenvoudige library genaamd '_scorebord_' die scores kan bijhouden voor bordspelletjes. Deze library kan dan gebruikt worden door toekomstige digitale Java bordspellen (en zal gebruikt worden door onze hoger-lager app).

In een Scorebord kan je spelers toevoegen door middel van een naam en een score. Er is een mogelijkheid om de huidige score van een speler op te vragen, en de winnende speler. Deze gegevens worden met behulp van Gson in een `json` bestand bewaard, zodat bij het heropstarten van een spel de scores behouden blijven.

De code van de library ziet er zo uit:

{{% notice style="code" title="SpelerScore.java (klik om te tonen)" expanded=false %}}
```java
package be.ses.scorebord;

class SpelerScore {
    private String name;
    private int score;

    public SpelerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public String getName() {
        return name;
     }
    public int getScore() {
        return score;
    }
}
```
{{% /notice %}}

{{% notice style="code" title="Scorebord.java (klik om te tonen)" expanded=false %}}
```java
package be.ses.scorebord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.gson.Gson;

public class Scorebord {
    private List<SpelerScore> spelerScores = new ArrayList<>();

    public void setScore(String name, int currentScore) {
        spelerScores.add(new SpelerScore(name, currentScore));
        spelerScores.sort(Comparator.comparing(ss -> ss.getScore()));
    }
    public int getScoreOf(String name) {
        for (var s : spelerScores) {
            if (s.getName().equals(name)) {
                return s.getScore();
            }
        }
        throw new NoSuchElementException("No player with that name");
    }

    public String getWinner() {
        SpelerScore winner = null;
        for (var ss : spelerScores) {
            if (winner == null || ss.getScore() > winner.getScore()) {
                winner = ss;
            }
        }
        return winner.getName();
    }

    @Override
    public String toString() {
        String result = "";
        for (var ss : spelerScores) {
            result += ss.getName() + ": " + ss.getScore() + "\n";
        }
        return result;
    }

    public void saveToJson(String filename) throws IOException {
        var gson = new Gson();
        var json = gson.toJson(this);
        Files.write(Path.of("scorebord.json"), json.getBytes());
    }

    public static Scorebord getScorebordFromJson(String filename) throws IOException {
        if (!Files.exists(Path.of(filename))) {
            return new Scorebord();
        }
        var gson = new Gson();
        var json = Files.readString(Path.of("scorebord.json"));
        var scorebord = gson.fromJson(json, Scorebord.class);
        return scorebord;
    }
}
```
{{% /notice %}}

De klasse `SpelerScore` is een intern hulpmiddel om te serialiseren van/naar JSON. Deze klasse wordt gebruikt in de implementatie van Scorebord.
Maak via de CLI een nieuw **Gradle - Java project**. Groupid: `be.ses`. Geef je project de naam: `scorebord`.

Configureer Gradle zodat het commando `gradlew jar` een bestand `scorebord-1.0.jar` creëert in de `build/libs` folder. 

_Tip: je update best je gradle wrappen naar versie 8.13 of hoger._

### Oefening 3: Hoger lager met scorebord

Voeg dat bovenstaand scorebord project als een library toe aan `higher_lower`-applicatie uit de eerste oefening. Kopieer de `scorebord-1.0.jar`-jarfile lokaal in een `./app/lib` folder in je higher_lower project en instrueer Gradle zo dat dit als `flatDir` repository wordt opgenomen ([zie boven](#extra-repositories-toevoegen)). Update nu ook de App klasse in de `higher_lower`-applicatie:

{{% notice style="code" title="Nieuwe App.java" expanded=false %}}
```java
package be.ses.higher_lower;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import be.ses.scorebord.Scorebord;

public class App {
    public static void main(String[] args) throws IOException {
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
        scorebord.setScore(name, score);
        System.out.println("This is the new leaderboard: \n" + scorebord + "\n\n");
        scorebord.saveToJson("highscores.json");
        scanner.close();
    }
}
```
{{% /notice %}}

Als de dependencies goed liggen, kan je een nieuw `Scorebord` aanmaken, en herkent VSCode dit met CTRL+Space. Hieronder een voorbeeld van `Gson`:

<figure style="display: flex; align-items: center; flex-direction: column;">
    <img src="/img/gradlevscodectrlspace-higherlower.png" style="max-height: 40rem;"/>
    <figcaption ><strong><i>Gradle extention for VSCode <code>Ctrl+space</code></i></strong></figcation>
</figure>

Als we het project uitvoeren, werkt dit echter **niet**: we krijgen een foutmelding bij het opslaan.
Dat komt omdat we een library gebruiken (scorebord), die op zijn beurt een library gebruikt (Gson).
Maar de Gson dependency is niet in onze huidige Gradle file gedefinieerd.

Om dit op te lossen, hebben we 3 opties:
1. zelf een dependency naar Gson toevoegen. Dat is echter ten zeerste af te raden, omdat we zo het automatische dependencybeheer via Gradle omzeilen.
2. een [shadow jar/fat jar](#fat-jars-shadow-jars) maken van scorebord. Dat is echter ook niet de meest aangewezen manier, opnieuw omdat we ingaan tegen de bedoeling van Gradle.
3. overschakelen naar een lokale Maven repository waarnaar we onze scorebord-library publiceren (zie [eerder](#publiceren-naar-een-maven-repository)). Dan worden ook transitieve dependencies automatisch ingeladen. Verwijder de `flatDir` en voeg een lokale maven URL toe. Publiceer het scorebord project naar diezelfde URL volgens de instructies van de `maven-publish` plugin.

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