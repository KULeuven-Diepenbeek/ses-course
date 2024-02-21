---
title: 'Java Gradle projecten'
weight: 2
draft: false
author: Wouter Groeneveld
---


> Helps teams build, automate and deliver better software, faster.<span>volgens de [gradle.org](https://gradle.org) website.</span>

### Wat is dat, een build tool?

Gradle is een build tool die de automatisatie van releasen, builden, testen, configureren, dependencies en libraries managen, ... eenvoudiger maakt. Kort gezegd: het maakt het leven van een ontwikkelaar eenvoudiger. In een config bestand genaamd `build.gradle` schrijft men met Groovy, een dynamische taal bovenop de JVM, op een descriptieve manier hoe Gradle de applicatie moet beheren. 

Andere bekende build tools:

- `Maven`, `Ant` (in XML) voor Java
- `(C/Q)Make` (custom config) voor C/C++
- `yarn`, `grunt`, `gulp`, (in JS) ... voor JS
- `nuget` (custom config, XML) voor .NET

Naast het beheren van compilaties, verzorgt Gradle ook libraries. Het is dus ook een _dependency management_ systeem, zoals Composer voor PHP of Node Package Manager voor NodeJS. Sommige build tools doen dus meer dan builden en beheren en downloaden ook automatisch je libraries, maar dat is niet altijd het geval!

#### Ontleding van een Gradle config file

Een voorbeeld van een eenvoudige buildfile is hieronder terug te vinden:
<!-- De meest eenvoudige buildfile is terug te vinden in de [singleton oefening](https://github.com/KULeuven-Diepenbeek/ses-patterns-singleton-template/blob/master/build.gradle): -->

<pre>
plugins {
    id 'java'
}

group 'be.kuleuven.ses'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.10

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
    testImplementation group: 'org.hamcrest', name: 'hamcrest-library', version: '2.2'
}

test {
    useJUnitPlatform()
}
</pre>

Hier onderscheiden we de volgende zaken:

1. Het project is een java 10 project (er zijn ook nog andere talen op de JVM; zoals Kotlin---Gradle kan ook Kotlinprogramma's compileren)
2. Het project komt van `be.kuleuven.ses`, versie `1.0-SNAPSHOT`.
3. Dependencies downloaden via de [standaard maven central](https://mvnrepository.com/repos/central) (ingebouwde URL).
    - Hiervan moet Gradle `junit-jupiter-api 5.6.0` downloaden voor de testen
    - Hiervan moet Gradle `junit-jupiter-engine` (zelfde versie) gebruiken om testen te runnen
    - Hiervan moet Gradle `hamcrest-library 2.2` downloaden voor de testen

Dependencies vallen (meestal) in twee categorieën:

1. `implementation` (productie dependencies)
2. `testImplementation` (test dependencies)

Merk op dat een typisch gradle project **geen jars** mee zipt, zoals de oefeningen. Die worden dus automatisch door deze tool gedownload, en in de juiste map geplaatst. 

<!-- Voor het [SESsy Library](/extra/sessy) project wordt ook Gradle gebruikt, en is de config file iets ingewikkelder, door de inclusie van eigen "tasks". (te raadplegen op <i class='fab fa-github'></i> [Github](https://github.com/KULeuven-Diepenbeek/sessylibrary/blob/master/build.gradle)) -->

{{% notice note %}}
Merk op dat bij Gradle 6.x, t.o.v. de vorige versies, de runtime dependencies nu `implementation` in plaats van `compile` heten, en de test dependencies `testImplementation` en `testRuntimeOnly` in plaats van `testCompile`. Zie ook: [Declaring dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies.html) in de Gradle docs. 
{{% /notice %}}


#### Ontleding van een Gradle project mappenstructuur

Als we kijken naar de bestanden- en mappenstructuur van een voorbeeld Gradle project, vinden we dit terug:

<pre>
build/
src/
    main/
        java/
            be/
                package1/
                    ClassMain
    test/
        java/
            be/
                package1/
                    ClassMainTest
resources/
    css/
    js/
gradle/
    wrapper/
        gradle-wrapper.jar
        gradle-wrapper.properties
build.gradle
gradlew.bat
gradlew
settings.gradle
</pre>

Hier onderscheiden we de volgende zaken:

1. Broncode (`.java` bestanden) in `src/main/java` en `src/test/java`, met productie- en testcode gescheiden. 
2. Gecompileerde code (`.class` bestanden) in de `build/` (of ook wel `out`) folder.
2. Eventueel `resources` voor webapps e.d. (images, css, ...)
3. `gradle` map, en executable (`gradlew.bat` win en shell script voor unix)
4. `gradle` settings en build file.

Wat gebeurt er nu precies als je `gradlew.bat` uitvoert? 

1. Download de juiste versie van Gradle zelf (!! dus installatie is **niet nodig**), afhankelijk van de specificaties in de properties file.
2. Download de juiste libraries om het project te kunnen runnen. 

Aan deze wrapper kan je commando's meegeven. Bijvoorbeeld, `gradlew.bat test`:

<pre>
Wouters-Air:singleton wgroeneveld$ ./gradlew test

> Task :test FAILED

ShoppingCartResourceTest > get_fromMultipleResources_shouldNotIncreaseDBHandleVarCount FAILED
    java.lang.AssertionError at ShoppingCartResourceTest.java:25

2 tests completed, 1 failed

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':test'.
> There were failing tests. See the report at: file:///Users/jefklak/development/brainbaking/content/teaching/ses/singleton/build/reports/tests/test/index.html
</pre>

Dit is **exact hetzelfde** als in IntelliJ de testen uitvoeren met de knop 'Run':

![](/img/teaching/ses/intellij_run_test.png)

### Waarom een build tool gebruiken?

De grootste voordelen hiervan zijn onder andere:

- Een kleine voetafdruk van de broncode (repository). Het is niet nodig om jars als libraries in een `lib/` folder zelf bij te houden: Gradle doet dit immers voor jou. 
- Een project _bootstrappen_ in luttele seconden: download code, voer de Gradle wrapper uit, en alles wordt vanzelf klaargezet (de juiste Gradle versie, de juiste library versies, ...)
- Platform-onafhankelijk processen besturen die altijd op dezelfde manier werken: een taak uitvoeren op mijn PC doet exact hetzelfde als bij jou, dankzij de beschrijving van de stappen in de config file. 

Het is bijvoorbeeld bij de oefeningen eenvoudig om een test library als `junit` mee te leveren, zonder de bestanden zelf aan te leveren, dankzij het toevoegen van twee regels in de dependencies block (zie hieronder: "Gradle en JUnit integratie"). 

### Een nieuw Gradle project maken

Dit kan op twee manieren:

1. Via IntelliJ: File -> New Project, kies voor "Java", en kies als build tool "Gradle". Je moet dan nog kiezen voor de Groovy build files. Dit maakt automatisch de juiste bestanden aan (`src/main/java`, `build.gradle(.kts)`, `gradle` wrapper files)
2. Via commandline. 

De tweede manier vereist de installatie van een lokale Gradle tool: zie [Gradle Docs: installing manually](https://gradle.org/install/#manually) (package managers zoals `brew` en `apt` voorzien meestal een `gradle` entry). Zorg er net zoals bij De Java installatie ervoor dat de bin folder in je `$PATH` is toegevoegd (zie documentatie). 


Vanaf dan kan je met het `gradle` commando in een lege map een nieuw project initialiseren. Zie [Gradle Docs: Bootstrapping new projects](https://docs.gradle.org/current/userguide/command_line_interface.html#sec:command_line_bootstrapping_projects)

```
$ gradle init
```

Dit start een interactieve shell waarin je dezelfde keuzes als bij een nieuw IntelliJ project moet ingeven (Kotlin/Gradle, ...). Dit maakt de folders gradle, .gradle, lib aan en de files settings.gradle, gradlew, en gradlew.bat. De broncode van je programma (Java code + `build.gradle`) zit in de `lib/` folder!

Het is ook via die `gradle` commando dat je wrapper bestanden kan aanmaken (`gradle wrapper`) of updaten. 


### Gradle en Maven integratie

Gradle voorziet een plugin genaamd '_maven-publish_' die deze bestanden automatisch aanmaakt. Activeer de plugin en voeg een `publishing` tag toe met de volgende properties:

<pre>
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
</pre>

Deze uitbreiding voegt de target `publish` toe aan Gradle. Dus: `./gradlew publish` publiceert de nodige bestanden in de aangegeven folder. Een Gradle project die daar gebruik van wenst te maken dient enkel een tweede Maven Repository plaats te definiëren:

<pre>
repositories {
    mavenCentral()
    maven {
        url = "C:\\Users\\u0158802\\development\\java\\maven-repo"
    }
}
</pre>

### Gradle en JUnit integratie

JUnit 5 splitst de test library op in een aantal submodules, waarvan er twee belangrijke zijn die we nodig hebben om te testen:

1. `junit-jupiter-api` - nodig om testen te SCHRIJVEN (de API waar `@BeforeEach` e.a. in zitten)
2. `junit-jupiter-engine` - nodig om testen UIT TE VOEREN (cmdline interface)

Aangezien Gradle verschillende test bibliotheken ondersteund, zoals ook TestNG, dient men in de Gradle build file ondersteuning voor elk framework te activeren. Dit is _enkel nodig bij cmdline uitvoeren van de testen_. Als je beslist om enkel binnen IntelliJ testen uit te voeren, verzorgt IntelliJ dit zelf, en is de jupiter-engine ook niet nodig. 

<pre>
test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

dependencies {
    // for WRITING tests, this will suffice:
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    // for RUNNING tests (cmdline, without IntelliJ), this is also needed:
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
}
</pre>

Optionele test libraries zoals Hamcrest en Selenium/WebDriver kunnen daarna ook worden toegevoegd onder de `testImplementation` groep.

{{% notice note %}}
Merk op dat dit betekent dat dependencies in de `testRuntimeOnly` groep eigenlijk _runtime dependencies_ zijn: deze worden niet gebruikt om mee te builden. Denk aan het verschil tussen statisch en dynamisch linken in C. 
{{% /notice %}}


### Welke Task moet ik uitvoeren?

`./gradlew tasks --all` voorziet een overzicht van alle beschikbare taken voor een bepaald Gradle project, opgesplitst per fase (build tasks, build setup tasks, documentation tasks, help tasks, verification tasks). Plugins voorzien vaak extra tasks, zoals bovenstaande maven plugin. 

Belangrijke taken zijn onder andere:

- `test`: voer alle unit testen uit. Een rapport hiervan is beschikbaar op build/reports/tests/test/index.html.
- `clean`: verwijder alle binaries en metadata.
- `build`: compile en test het project.
- `publish`: (maven plugin) publiceert naar een repository.
- `jar`: compile en package in een jar bestand
- `javadoc`: (plugin) genereert HTML javadoc. Een rapport hiervan is beschikbaar op build/docs/javadoc/index.html.

Onderstaande screenshot is een voorbeeld van een Unit Test HTML rapport voor de SESsy library:

![Gradle test report](/img/teaching/ses/gradle-testreports.png)


### Ik wil meer output bij het uitvoeren van mijn tasks!

De standaard output geeft enkel weer of er iets gelukt is of niet:

Meer informatie kan met de volgende parameters:

- `--info`, output LogLevel `INFO`. Veel irrelevante info wordt ook getoond.
- `--warning-mode all`, toont detail informatie van warning messages
- `--stacktrace`, toont de detail stacktrace bij exceptions

### De Gradle (wrapper) Upgraden

Indien de Gralde wrapper een oudere versie aanmaakt (< v6), update met `gradle wrapper --gradle-version 6.0.1`. Gradle versie `6` of groter is vereist voor JDK `13` of groter. 

[Gradle/Java compatibiliteitsmatrix](https://docs.gradle.org/current/userguide/compatibility.html#java): 

- JDK `21`: Gradle `8.5` of nieuwer

Indien je de fout "Could not initialize class `org.codehaus.groovy.reflection.ReflectionCache`" krijgt, betekent dit dat je JDK te nieuw is voor de gradle versie (bijvoorbeeld JDK `17` met Gradle `6.7` in plaats van `7.0` of nieuwer). Controleer de huidige gralde versie met `gradle --info` of kijk in `gradle/wrapper/gradle-wrapper.properties`. 

### Meer links en tutorials:

- Officiële Gradle [docs](https://docs.gradle.org/current/userguide/userguide.html).
- Officiële Gradle [guides: creating a new build](https://guides.gradle.org/creating-new-gradle-builds/)
- [Gradle cheatsheet voorbeeld config file](https://gist.github.com/jahe/59557d507f43574b0d96)
- [Gradle common commands](https://www.polyglotdeveloper.com/cheatsheet/2015-01-08-Gradle-cheatsheet/)

### Opgave

Maak een nieuw JavaFX (gradle) project aan. Maak een kleine darts-applicatie die gebruik maakt van de scorebord-library die je in het vorige deel hebt aangemaakt. De JavaFX applicatie bestaat uit een dartsbord met 3 concentrische cirkels. Je kan je naam ingeven in een tekstveld. Je kan op de cirkels klikken om een score te krijgen. Binnenste cirkel is 3 punten, middenste cirkel is 2 punten, buitenste cirkel is 1 punt. Dit punt wordt direct toegevoegd aan je scorebord. Met een load-knop moet een vorig scorebord ingeladen worden, met een save-knop moet je het huidige scorebord kunnen opslaan. De huidige winnaar moet ook steeds getoond worden in een label.