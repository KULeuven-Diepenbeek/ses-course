---
title: 'VSCode'
weight: 3
draft: false
author: Arne Duyver
---

## Heb je speciale tools nodig als software engineer?
Je kan in principe code schrijven puur met nano en andere command line tools, maar dat is niet altijd even handig. Hoewel nano en vergelijkbare tools lichtgewicht en eenvoudig te gebruiken zijn, missen ze veel van de geavanceerde functies die moderne ontwikkelaars nodig hebben om efficient aan software engineering te doen. Denk hierbij aan **syntax highlighting**, **code completion**, **debugging**, en **geïntegreerde versiebeheer**. Deze functies kunnen het programmeren aanzienlijk efficiënter en minder foutgevoelig maken.

Daarom bestaan er **Integrated Development Environments (IDE's)** die het programmeren vergemakkelijken op verschillende manieren. IDE's bieden een uitgebreide set tools en functies binnen één enkele applicatie. Ze ondersteunen vaak meerdere programmeertalen, bieden geavanceerde debugging-mogelijkheden en hebben ingebouwde ondersteuning voor versiebeheer zoals [Git](/2-versiebeheer/lokaal%20versiebeheer%20met%20Git.md). Bovendien bieden ze vaak een visuele interface (GUI) voor het **beheren van projecten en dependencies**, wat het ontwikkelproces stroomlijnt.

De nadelen van IDE's zijn echter dat ze vaak zwaar en traag kunnen zijn, vooral op oudere of minder krachtige hardware. Ze kunnen ook een **steile leercurve** hebben vanwege de vele functies en configuratiemogelijkheden. Dit kan overweldigend zijn voor beginners of voor ontwikkelaars die snel aan de slag willen zonder veel tijd te besteden aan het leren van een nieuwe tool.

Als je dit vergelijkt met een lichtgewicht code/text editor zoals Notepad++ of Visual Studio Code (VSCode), zie je dat deze editors een goede balans bieden tussen functionaliteit en prestaties. Ze zijn sneller en minder resource-intensief dan volledige IDE's, maar bieden toch veel van de functies die ontwikkelaars nodig hebben.

VSCode biedt een mooie middenweg omdat je via **extensies** het gedrag van de editor kunt aanpassen aan je eigen wensen. Met duizenden beschikbare extensies kun je functies toevoegen zoals linting, debugging, versiebeheer, en ondersteuning voor vrijwel elke programmeertaal. Dit maakt VSCode zeer flexibel en aanpasbaar, waardoor het een populaire keuze is onder ontwikkelaars van alle niveaus.

## VSCode installeren
Om Visual Studio Code (VSCode) op Windows te installeren, volg je deze stappen:

1. Ga naar de [downloadpagina van Visual Studio Code](https://code.visualstudio.com/download) en download VSCode voor Windows.
2. Vink tijdens de installatie de optie aan om VSCode aan je PATH toe te voegen tijdens de installatie en om directories te openen met VSCode.
3. Na de installatie kun je VSCode starten vanuit het Startmenu of door `code` in de command line te typen.

_Inloggen met je Microsoft- of GitHub-account in VSCode biedt verschillende voordelen. Door in te loggen, kun je je instellingen, thema's en extensies synchroniseren over meerdere apparaten. Dit betekent dat je dezelfde ontwikkelomgeving hebt, ongeacht waar je werkt. Bovendien kun je eenvoudig samenwerken met anderen via GitHub, waarbij je toegang hebt tot je repositories en pull requests direct vanuit VSCode. Het is echter niet verplicht._

## Ingebouwde menus
VSCode komt out-of-the-box met een breed scala aan functionaliteiten die het programmeren en ontwikkelen aanzienlijk vergemakkelijken:
- **Bestandenviewer**: De ingebouwde bestandenviewer biedt een overzichtelijke manier om door je projectbestanden en mappen te navigeren. Je kunt eenvoudig bestanden openen, verplaatsen, hernoemen en verwijderen zonder de editor te verlaten.
- **Zoekfunctie**: De krachtige zoekfunctie in VSCode stelt je in staat om snel door je codebase te zoeken naar specifieke termen of patronen. Je kunt zoeken binnen een enkel bestand of door je hele project, en zelfs gebruik maken van reguliere expressies (regex) voor geavanceerde zoekopdrachten.
- **Ingebouwd versiebeheer**: VSCode heeft ingebouwde ondersteuning voor versiebeheer, zoals Git. Je kunt je code rechtstreeks vanuit de editor beheren, inclusief het maken van commits, het bekijken van de geschiedenis, en het oplossen van conflicten. Dit maakt het samenwerken met anderen en het bijhouden van wijzigingen in je code veel eenvoudiger.
- **Debugger**: De ingebouwde debugger in VSCode ondersteunt verschillende programmeertalen en biedt functies zoals breakpoints, stap-na-stap uitvoering en het inspecteren van variabelen. 
- **Extensies**: Daarnaast kun je zoals al eerder vermeld met extensies de functionaliteit van VSCode verder uitbreiden. Of je nu ondersteuning nodig hebt voor een specifieke programmeertaal, linting, formattering, of integratie met andere tools, er is vrijwel altijd een extensie beschikbaar die aan je behoeften voldoet.
- **Shortcuts**: Verder kan je ook zeer gepersonaliseerde shortcuts aanmaken die bij jouw specifieke workflow passen.

## Instellingen
Je kunt op verschillende niveaus instellingen aanpassen in Visual Studio Code (VSCode), wat je de flexibiliteit geeft om de editor precies naar jouw wensen te configureren. Hier zijn de belangrijkste niveaus waarop je instellingen kunt aanpassen:

- **Gebruikersniveau:** Instellingen die op gebruikersniveau worden aangepast, gelden voor alle projecten en werkruimten die je opent in VSCode. Deze instellingen worden opgeslagen in een JSON-bestand dat je kunt openen en bewerken via de Command Palette door te zoeken naar "Preferences: Open Settings (JSON)".

- **Workspace niveau:** Instellingen op workspace niveau gelden alleen voor de specifieke werkruimte of het project dat je hebt geopend. Dit is handig als je verschillende configuraties nodig hebt voor verschillende projecten. Workspace-instellingen worden opgeslagen in een `.vscode`-map binnen je projectdirectory.

- **Directory niveau:** Binnen een werkruimte kun je ook instellingen aanpassen voor specifieke mappen. Dit kan nuttig zijn als je een project hebt met meerdere mappen die elk hun eigen configuratie vereisen.

_Je kan extenties ook op die verschillende niveaus enablen/disablen._

## De command pallette
De **Command Palette** in VSCode is een krachtige tool die je toegang geeft tot vrijwel alle functies en instellingen van de editor via een eenvoudige interface. Je kunt de Command Palette openen door `Ctrl+Shift+p` (Windows/Linux) of `Cmd+Shift+p` (Mac) te gebruiken. In de Command Palette kun je commando's invoeren om taken uit te voeren zoals het openen van bestanden, het wijzigen van instellingen, het installeren van extensies, en nog veel meer. Het biedt een snelle manier om acties uit te voeren zonder door menu's te hoeven navigeren, wat je workflow aanzienlijk kan versnellen. De Command Palette ondersteunt ook fuzzy search, waardoor je snel kunt vinden wat je zoekt, zelfs als je de exacte naam van het commando niet weet.

## Connectie maken met WSL
Om connectie te maken met je WSL in VSCode heb je de extensie WSL nodig. Daarna kan je onderaan links op de blauwe of groene knop met `><` pijlen klikken om connectie te maken met je WSL, op die manier kan je (de meeste) van je extensies behouden wanneer je 

## Enkele nuttige algemene extensies
- Prettier: Coder formatter
- _TODO Tree_: Hiervan gaan we in de lessen gebruik maken om oefeningen aan te geven binnen in bronbestanden.

## Een ontwikkelomgeving opstellen voor de gewenste programmeertaal/het gewenste framework

**Pad naar interpreter/compiler instellen:**<br>
In de instellingen kan je voor specifieke programmeertalen het pad naar de correcte interpreter of compiler instellen. Op die manier kan je ook met de play-knop code uitvoeren in VSCode

**Nuttige extenties voor elk framework/elke programmeertaal:**
- _Syntax highlighter_: Het zorgt ervoor dat verschillende elementen van de code, zoals sleutelwoorden, variabelen, strings en opmerkingen, verschillende kleuren krijgen. Dit helpt ontwikkelaars om de structuur en betekenis van de code sneller te begrijpen.
- _Code suggestions/completion_: ook bekend als IntelliSense, biedt intelligente code-aanvullingen terwijl je typt.
- _Debugger_: De ingebouwde debugger in VSCode helpt ontwikkelaars om hun code te testen en fouten op te sporen. Het biedt functies zoals breakpoints, stap-voor-stap uitvoering, en het inspecteren van variabelen. Dit versnelt de cyclus van bewerken, compileren en debuggen, waardoor ontwikkelaars efficiënter kunnen werken.
- _Linter_: analyseert de code op semantische en stilistische problemen. Het helpt bij het identificeren en corrigeren van subtiele programmeerfouten en coding practices die tot fouten kunnen leiden.
- _Formatter_: maakt de broncode gemakkelijker leesbaar door mensen door bepaalde regels en conventies af te dwingen, zoals lijnspatiëring, inspringing en spatiëring rond operators.
- _Code navigation shortcuts_: VSCode biedt verschillende sneltoetsen om efficiënt door je code te navigeren.
- _(code templates)_

_Deze zijn meestal programmeertaal specifiek en moet je dus voor elke taal apart instellen. Soms kan je ook pakketten van extensies downloaden_


## Enkele development environments:

### C development environment
Om broncode in `C` te runnen op je WSL ga je een aantal prerequisites nodig hebben: 
- `gcc`: Install via `sudo apt install gcc -y`. De **GNU Compiler Collection** is een verzameling van compilers voor verschillende programmeertalen zoals C, C++, Objective-C, Fortran, Ada, en meer.
- `make`: Install via `sudo apt install make -y`. Dit is een tool die de bouw van softwareprojecten automatiseert door gebruik te maken van een **Makefile** om rules en dependencies te definiëren.

Verder kunnen volgende VSCode extensies handig zijn om je development proces te optimaliseren:
- **C C++ Extension Pack**: dit bevat volgende extensies
  - **C/C++**: _The C/C++ extension adds language support for C/C++ to Visual Studio Code, including editing (IntelliSense) and debugging features._
  - **C/C++ Themes**
  - **CMake Tools**: _CMake Tools provides the native developer a full-featured, convenient, and powerful workflow for CMake-based projects in Visual Studio Code._

### Java development environment
Om `Java`code te runnen en te compileren op je WSL ga je een aantal prerequisites nodig hebben:
- `java`: Install via `sudo apt install default-jre -y`. De **Java Runtime Environment (JRE)** is een softwarelaag die nodig is om Java-applicaties uit te voeren. Het bevat de Java Virtual Machine (JVM), kernbibliotheken en andere componenten die nodig zijn om Java-programma's te draaien.
- `javac`: Install via `sudo apt install default-jdk -y`. De **Java Development Kit (JDK)** is een softwaredeveloperskit die de tools en bibliotheken bevat die nodig zijn om Java-applicaties te ontwikkelen en te compileren. Het omvat de Java Runtime Environment, een compiler (`javac`), en andere hulpmiddelen zoals een debugger en documentatiegenerator.
- `gradle`: Install via `sudo snap install gradle --classic`. **Gradle** is een open-source build automation tool die wordt gebruikt voor het ontwikkelen van softwareprojecten. Het ondersteunt het bouwen, testen, en implementeren van applicaties en is vooral populair in Java- en Android-ontwikkeling vanwege zijn flexibiliteit en krachtige configuratiemogelijkheden.
  - create gradle project in directory: `gradle init`
  - update gradle version per project: `gradle wrapper --gradle-version x.x.x`.

<!-- TODO: voeg to hoe je met cmdline ook packagenaam meegeeft aan gradle init. Mss zelf alle opties in 1 command `echo 'no' | gradle init --type java-application --no-split-project --dsl groovy --java-version 21  --test-framework junit --package be.ses --project-name gradletest`-->
<!-- TODO: Duidelijke vermelding maken dat je per javaproject BEST een apart vscode window opent anders registreert die niet dat het om een gradle project gaat en geeft hij ook errors bij package names die wel kloppen bijvoorbeeld OOK iets zeggen over de 3 veschillende manieren om te runnen 1. boven static void main als die exact zoals het hoort geschreven is 2. play knop in vscode 3. gradle run 4. ./gradlew run -->

Verder kunnen volgende VSCode extensies handig zijn om je development proces te optimaliseren:
- **Extension Pack for Java**: dit bevat volgende extensies
  - **Language Support for Java(TM) by Red Hat**: Java Linting, Intellisense, formatting, refactoring, Maven/Gradle support and more... Java Linting, Intellisense, formatting, refactoring, Maven/Gradle support and more... Provides Java ™ language support via Eclipse ™ JDT Language Server, which utilizes Eclipse ™ JDT, M2Eclipse and Buildship.
  - **Debugger for Java**:  A lightweight Java Debugger based on Java Debug Server which extends the Language Support for Java by Red Hat. It allows users to debug Java code using Visual Studio Code (VS Code).
  - **Test Runner for Java**: A lightweight extension to run and debug Java test cases in Visual Studio Code.
  - **Maven for Java**: Maven extension for VS Code. It provides a project explorer and shortcuts to execute Maven commands, improving user experience for Java developers who use Maven.
  - **Gradle for Java**: This VS Code extension provides a visual interface for your Gradle build. You can use this interface to view Gradle Tasks and Project dependencies, or run Gradle Tasks as VS Code Task. The extension also offers better Gradle file (e.g. build.gradle) authoring experience including syntax highlighting, error reporting and auto completion.
  - **Project Manager for Java**: A lightweight extension to provide additional Java project explorer features. It works with Language Support for Java by Red Hat.
  - **IntelliCode**: The Visual Studio IntelliCode extension provides AI-assisted development features for Python, TypeScript/JavaScript and Java developers in Visual Studio Code, with insights based on understanding your code context combined with machine learning.


### Python development environment
Om `Python`code te runnen (en te compileren) op je WSL ga je een aantal prerequisites nodig hebben:
- `python3`: Install via `sudo apt install python3 -y`. Het package **python3** heb je nodig om Python 3 scripts en programma's uit te voeren. Het bevat de **interpreter** en de standard libraries die essentieel zijn voor het draaien van Python 3 code.
- `pip`: Install via `sudo apt-get install python3-pip`. **Pip** is een package manager voor Python die wordt gebruikt om Python-packages te installeren en te beheren.
  - update pip via `pip install --upgrade pip`
- `pyinstaller`: Install via `pip install pyinstaller`. **PyInstaller** is een tool dat Python-scripts bundelt tot stand-alone executables voor Windows, macOS en Linux. Het maakt het mogelijk om Python-applicaties te distribueren zonder dat gebruikers een Python-omgeving hoeven te installeren.

Verder kunnen volgende VSCode extensies handig zijn om je development proces te optimaliseren:
- **Python**: A Visual Studio Code extension with rich support for the Python language (for all actively supported Python versions), providing access points for extensions to seamlessly integrate and offer support for IntelliSense (Pylance), debugging (Python Debugger), formatting, linting, code navigation, refactoring, variable explorer, test explorer, and more!
- **Python Debugger**: A Visual Studio Code extension that supports Python debugging with debugpy. Python Debugger provides a seamless debugging experience by allowing you to set breakpoints, step through code, inspect variables, and perform other essential debugging tasks. The debugpy extension offers debugging support for various types of Python applications including scripts, web applications, remote processes, and multi-threaded processes.
- **Pylance**: Pylance is an extension that works alongside Python in Visual Studio Code to provide performant language support. Under the hood, Pylance is powered by Pyright, Microsoft's static type checking tool. Using Pyright, Pylance has the ability to supercharge your Python IntelliSense experience with rich type information, helping you write better code faster.