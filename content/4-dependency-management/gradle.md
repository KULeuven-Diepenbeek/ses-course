---
title: 'Java Gradle projecten'
weight: 1
draft: false
author: Wouter Groeneveld en Arne Duyver
---


> Helps teams build, automate and deliver better software, faster.<span>volgens de [gradle.org](https://gradle.org) website.</span>

### Gradle: Meer dan enkel een glorified Makefile

Gradle is een **build tool** die de automatisatie van releasen, builden, testen, configureren, dependencies en libraries managen, ... eenvoudiger maakt. Kort gezegd: het maakt het leven van een ontwikkelaar eenvoudiger. In een config bestand genaamd `build.gradle` schrijft men met **Groovy**, een dynamische taal bovenop de JVM (Java Virtual Machine), op een descriptieve manier hoe Gradle de applicatie moet beheren. 

Andere bekende build tools:

- `Maven`, `Ant` (in XML) voor Java
- `(C/Q)Make` (custom config) voor C/C++
- `yarn`, `grunt`, `gulp`, (in JS) ... voor JS
- `nuget` (custom config, XML) voor .NET

Naast het beheren van compilaties, verzorgt Gradle ook libraries. Het is dus ook een _dependency management_ systeem, zoals Composer voor PHP of Node Package Manager voor NodeJS. Sommige build tools doen dus meer dan builden en beheren en downloaden ook automatisch je libraries, maar dat is niet altijd het geval!

#### [Installatie Gradle en een project opstarten](/1-wsl-vscode/vscode.md#java-development-environment)

Je kan Gradle op je WSL installeren met het commando `sudo snap install gradle --classic`. (Zie ook [Gradle Docs: installing manually](https://gradle.org/install/#manually)) Dit installeert echter niet altijd de nieuwste versie van Gradle, in dit geval `v7.2`, maar dat is geen probleem (hier komen we zo dadelijk op terug). Je kan nu een directory initialiseren als een Gradle project met `gradle init`. (Zie ook [Gradle Docs: Bootstrapping new projects](https://docs.gradle.org/current/userguide/command_line_interface.html#sec:command_line_bootstrapping_projects)) Volg onderstaande stappen:
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

# Nu vraagt Gradle te kiezen tussen programmeertalen voor ons build script (de taal waarmee we onze buil.gradle file gaan programmeren), we kiezen voor 2. Groovy
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

##### Gradle options
Gradle vraagt ons tijdens de init een aantal opties te kiezen. Alhoewel we in deze lessen 90% van de tijd de opties kiezen zoals hierboven getoond in het voorbeeld, kan je hieronder toch een overzicht terugvinden met enkele opties:
- **Select type of build to generate**: opties - Application - Library (gaan we niet verder op in) - Gradle Plugin (gaan we niet verder op in) - Basic
    - **Application**: Dit type is geconfigureerd om een volwaardige uitvoerbare applicatie te bouwen. Het bevat extra configuraties en plugins, zoals de application plugin, die helpt bij het definiëren van de hoofdmethode en het maken van uitvoerbare JAR-bestanden. Geschikt voor het ontwikkelen van volledige applicaties die je kunt uitvoeren en distribueren. _(99% van de tijd gaan we dit type gebruiken)_
    - **Basic**: Dit is een eenvoudig project zonder specifieke plugins of configuraties. Het bevat alleen de minimale structuur en bestanden die nodig zijn om een Gradle-project te starten.  Je kan dan enkel nog de projectnaam kiezen en de programmeertaal voor Gradle.
- **Select implementation language**: Je ziet dat Gradle dus ook voor andere programmeertalen gebruikt kan worden. Wij kiezen hier echter voor Java.
- **Select application structure**: opties - 1. only one application project - application and library projects
    - **Only one application project**: Dit type project is gericht op het bouwen van één enkele applicatie. De projectstructuur is eenvoudig en bevat meestal alleen de bronbestanden en configuratiebestanden die nodig zijn om de applicatie te bouwen en uit te voeren. Geschikt voor kleinere projecten of wanneer er geen behoefte is aan herbruikbare componenten.
    - **Application and library project**: Dit type project is opgesplitst in meerdere modules, waaronder een applicatiemodule en een of meer bibliotheekmodules. De bibliotheekmodules bevatten herbruikbare code die door de applicatiemodule kan worden gebruikt. Deze structuur bevordert modulariteit en hergebruik van code, wat vooral nuttig is voor grotere projecten of wanneer je van plan bent om delen van je code in andere projecten te gebruiken. 

_**Bij het aanmaken van je project maakt Gradle een aantal folders en bestanden aan. We overlopen hieronder de functionaliteit van de belangrijkste onderdelen.**_ 

#### Ontleding van een Gradle config file `build.gradle`

De belangrijkste file voor Gradle is de `build.gradle` file die zich in de `./app` directory bevindt. Die ziet er als volgt:

```groovy
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

Met de Groovy syntax definiëren we verschillende taken met `taaknaam { ... }`. We onderscheiden we de volgende taken:

1. **Plugins**: hier kan je plugins voor Gradle toevoegen, je voegt ze toe op basis van `id`
    - `id 'application'`: Voegt taken toe zoals `build` en `test` voor je applicatie.
2. **Repositories**: hiermee specificeer je waar de dependencies in de dependencies functie gezocht en gedownload moeten worden, default via [standaard maven central](https://mvnrepository.com/repos/central) (ingebouwde URL)
3. **Dependencies**: hiermee specificeer je de dependencies die je project nodig heeft. Dependencies vallen (meestal) in twee categorieën:
    - `implementation` (productie dependencies): deze dependencies worden zo goed als altijd toegepast.
    - `testImplementation` (test dependencies): deze dependencies worden enkel toegepast bij testing.
4. **Application**: met `mainClass = 'be.ses.my_application.App'` geef je aan welke `main`-methode van welke klasse moet gerund worden wanneer je je applicatie wil runnen. (Dit is dus de entrypoint van je applicatie)

Merk op dat een typisch gradle project **geen jars** mee zipt. Die worden dus automatisch door deze tool gedownload, en in de juiste map geplaatst. _(zie dependency management)_

{{% notice note %}}
Merk op dat bij Gradle 6.x, t.o.v. de vorige versies, de runtime dependencies nu `implementation` in plaats van `compile` heten, en de test dependencies `testImplementation` en `testRuntimeOnly` in plaats van `testCompile`. Zie ook: [Declaring dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies.html) in de Gradle docs. 
{{% /notice %}}

We kunnen zelf nog wat meer info over ons project gaan toevoegen door enkel **project properties** in te stellen via onderstaande Groovy code_(het maakt niet heel veel uit waar deze code juist geplaatst wordt, maar dit mag niet binnenin een andere functie zijn EN MOET na de `plugins {id 'application'}` functie komen omdat het hier een onderdeel van is)_:
```groovy
group = 'be.ses'
version = '1.0-SNAPSHOT'

sourceCompatibility = 21
```

- Het project komt van `be.ses`, versie `1.0-SNAPSHOT`.
- Het project is een java 21 project _(Deprecated en veroorzaakt een warning dus gaan we niet meer gebruiken)_

{{% notice info %}}
We zouden nu ook zelf een kleine taks kunnen coderen in Groovy die simpelweg de 'group' van ons project uit print:
```groovy
task example {
    println project.group
}
// Gebruik via `./gradlew example`
```
{{% /notice %}}

#### Ontleding van een Gradle project mappenstructuur

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

1. Broncode (`.java` bestanden) in `src/main/java` en `src/test/java`, met productie- en testcode gescheiden. 
2. Gecompileerde code (`.class` bestanden) in de `build/` (of ook wel `out`) folder.
2. Eventueel `resources` voor webapps e.d. (images, css, ...)
3. `gradle` map, en executable (`gradlew.bat` win en `gradlew`-shell script voor unix)
4. `gradle` settings en build file.
    - in de `settings.gradle`-file kan je de projectnaam aanpassen: `rootProject.name = 'new_name'`

#### Gradlew
Wanneer je een Gradle project aanmaakt creëert Gradle vanzelf ook een soort lokale executable (**wrapper**) van zichzelf in de vorm van een `./gradlew`-executable. Dit heeft enkele voordelen: 
- **Consistentie**: Het zorgt ervoor dat iedereen die aan het project werkt dezelfde versie van Gradle gebruikt, ongeacht de versie die lokaal is geïnstalleerd. (Door steeds de `gradlew` in de projectdirectory te gebruiken)
- **Gemak**: Gebruikers hoeven Gradle niet apart te installeren, omdat de wrapper automatisch de juiste versie downloadt en gebruikt (enkel om initieel een project aan te maken heb je een lokale versie van Gradle nodig).
- **Automatisering**: Het maakt het eenvoudiger om build-scripts en CI/CD-pijplijnen te configureren, omdat de wrapper de benodigde Gradle-versie beheert.

Nu moeten we dus onze lokale `gradlew` nog zetten op de juiste versie die overeenkomt met de versie van Java die we in ons project gaan gebruiken. [Hier](https://docs.gradle.org/current/userguide/compatibility.html#:~:text=Gradle%20runs%20on%20the%20Java,an%20error%20in%20Gradle%209.0.) vind je een overzicht van welke Gradle versies compatibel zijn met welke versies van Java (JVM provided by JRE or JDK). Nu je weet welke versie van Gradle je wil gebruiken upgrade je de lokale `gradlew` met het volgende commando: `gradle wrapper --gradle-version x.x.x` (bijvoorbeeld `8.12` voor Java versies 23 of ouder). **Gradle laat je soms zelf ook wel weten naar welke versie je moet updaten aan de hand van je gebruikte Java code**

**Wat gebeurt er nu precies als je `gradlew` uitvoert?**

1. Download de juiste versie van Gradle zelf (!! dus installatie is **niet nodig**), afhankelijk van de specificaties in de properties file.
2. Download de juiste libraries om het project te kunnen runnen. 

Aan deze wrapper kan je commando's meegeven. Bijvoorbeeld, `./gradlew run`:

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

### Waarom een build tool gebruiken?

De grootste voordelen hiervan zijn onder andere:

- Een kleine voetafdruk van de broncode (repository). Het is niet nodig om jars als libraries in een `lib/` folder zelf bij te houden: Gradle doet dit immers voor jou. 
- Een project _bootstrappen_ in luttele seconden: download code, voer de Gradle wrapper uit, en alles wordt vanzelf klaargezet (de juiste Gradle versie, de juiste library versies, ...)
    - _Een project bootstrappen betekent het opzetten of initialiseren van een project vanaf het begin, vaak met behulp van een tool of framework dat de basisstructuur en configuratie voor je genereert. Dit proces helpt je snel aan de slag te gaan zonder dat je alles handmatig hoeft in te stellen._
- Platform-onafhankelijk processen besturen die altijd op dezelfde manier werken: een taak uitvoeren op mijn PC doet exact hetzelfde als bij jou, dankzij de beschrijving van de stappen in de config file. 

Het is bijvoorbeeld bij de oefeningen eenvoudig om een test library als `junit` mee te leveren, zonder de bestanden zelf aan te leveren, dankzij het toevoegen van twee regels in de dependencies block. 

### Welke Task moet ik uitvoeren?

`./gradlew tasks --all` voorziet een overzicht van alle beschikbare taken voor een bepaald Gradle project, opgesplitst per fase (build tasks, build setup tasks, documentation tasks, help tasks, verification tasks). Plugins voorzien vaak extra tasks, zoals bovenstaande maven plugin. 

Belangrijke taken zijn onder andere:

- `test`: voer alle unit testen uit. Een rapport hiervan is beschikbaar op build/reports/tests/test/index.html. (komen we zeker nog op terug in het deel TDD)
- `clean`: verwijder alle binaries en metadata.
- `build`: compile en test het project.
- `publish`: (maven plugin) publiceert naar een repository.
- `jar`: compile en package in een jar bestand
- `javadoc`: (plugin) genereert HTML javadoc. Een rapport hiervan is beschikbaar op build/docs/javadoc/index.html.

Onderstaande screenshot is een voorbeeld van een Unit Test HTML rapport:

![Gradle test report](/img/teaching/ses/gradle-testreports.png)


### Ik wil meer output bij het uitvoeren van mijn tasks!

De standaard output geeft enkel weer of er iets gelukt is of niet:

Meer informatie kan met de volgende parameters:

- `--info`, output LogLevel `INFO`. Veel irrelevante info wordt ook getoond.
- `--warning-mode all`, toont detail informatie van warning messages
- `--stacktrace`, toont de detail stacktrace bij exceptions

### De Gradle (wrapper) Upgraden

Indien je de fout "Could not initialize class `org.codehaus.groovy.reflection.ReflectionCache`" krijgt, betekent dit dat je JDK te nieuw is voor de gradle versie (bijvoorbeeld JDK `17` met Gradle `6.7` in plaats van `7.0` of nieuwer). Controleer de huidige Gradle versie met `gradle --info` of kijk in `gradle/wrapper/gradle-wrapper.properties`. 

### Oefening
1. Maak een nieuw Gradle project aan met de naam: `higher_lower` en gebruik de packagenaam `be.ses.higher_lower`.
2. Delete de test in `app/src/test/java/App.java`. Anders zal je je project niet kunnen runnen.
3. Aangezien we input aan de gebruiker gaan vragen moeten we een extra optie instellen in de `app/build.gradle`:
```groovy
run {
    standardInput = System.in
}
```
4. Kopieer onderstaande code voor de klasse `app/src/main/java/App.java`:

<details closed>
<summary><i><b> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

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
            
            if ((guess.equals("h") && nextNumber > currentNumber) || (guess.equals("l") && nextNumber < currentNumber)) {
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

</p>
</details>

5. Test je programma: Aangezien we input vragen via de scanner moeten we ook op een speciale manier ons project runnen: gebruik `./gradlew --console plain run` of `gradlew -q --console plain run` om helemaal geen output van gradle tasks ertussen te zien.

### Compile to JAR
Zoals we in het hoofdstuk rond [build systems in Java](/3-build-systems-makefiles/build-systems-java.md) gezien hebben kan je alle bestanden groeperen in een `.jar` bestand om er een executable van te maken dat gerund kan worden met de JVM. Hiervoor gebruik je het commando: `./gradlew jar`. Er wordt dan een `main_classname.jar` aangemaakt in je `/app/build/libs` directory.
<!-- TODO no main class in manifest file => update build.gradle -->

#### TODO Shadowjar


### Oefening
6. Ga verder op de oefening `higher_lower` en build een .jar file.
7. Copy de `app/build/libs/app.jar`-file naar een andere directory en hernoem naar `higherLower.jar`.
8. Run het jar programma via de CLI.
9. Doe hetzelfde maar gebruik een shadow jar.