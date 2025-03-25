---
title: Opdrachten
weight: 120
author: Arne Duyver, koen Yskout
draft: false
toc: true
---

## Algemene informatie

Hier vind je de opdrachten voor de permanente evaluatie van SES+EES.
De opdrachten voor **deel 1** worden gemaakt door alle studenten (SES+EES).
De opdrachten voor **deel 2** worden enkel gemaakt door de studenten SES.

### GitHub repository linken

Om de permanente evaluatie opdrachten in te dienen voor het vak Software Engineering Skills maken we gebruik van GitHub. In de lessen zal je onder begeleiding een gratis account aanmaken op dit platform. 
Je maakt de opdrachten in een repository die je aanmaakt via GitHub Classroom. Daarvoor moet je je GitHub gebruikersnaam linken aan je naam, zodat wij weten bij welke student de repository van 'FluffyBunny' hoort :). Zie de instructies bij de eerste opdracht voor meer details.

### Extra start files van andere start repositories downloaden in je eigen repo
Om jullie het leven makkelijk te maken, hebben we voor veel van de opdrachten al startcode geschreven in de vorm van een github repository. Die nieuwe repositories simpelweg clonen in een subdirectory van je eigen repository zorgt echter voor een aantal problemen. Die nieuwe directories met startcode worden dan namelijk als een **submodule** toegevoegd aan je main repo. Die repo wordt dus als een soort link beschouwd en aangezien die nieuwe repo niet van jullie zelf is ga je dan ook geen veranderingen aan die code kunnen pushen. <br />
In de plaats moeten we die start-code-repositories als een **subtree** toevoegen. Dat heeft als voordeel dat je zelf gewoon wijzigingen kan aanbrengen, committen en pushen naar je eigen hoofd repository. Bovendien blijft er ook een zachte link met de originele start-code-repository zodat als hier iets aan wijzigt je die online wijzigingen nog wel kan fetchen. (Bijvoorbeeld als we merken dat er nog een foutje in de startcode stond!).

Zo een subtree aanmaken doe je dan op de volgende manier:
1. Voeg de remote-start-code-repository toe aan je eigen repo met volgende commando:
```bash
git subtree add --prefix=<subdirectory_waarin_je_de_files_wil_clonen> <remote-start-code-repository-link> <branch_name> --squash
```
   - met `--prefix=` geef je aan in welke subdirectory de inhoud van de externe repo moet komen.
   - je kan een specifieke branch meegeven (meestal `main`)
   - met `--squash` condenseer je de git history van de externe repo tot één enkele commit. (Je kan deze optie weglaten als je toch de hele git history wil bekijken)
2. Zodra de files in jouw repo staan, kan je ze bewerken zoals normaal. De changes worden dan onderdeel van de history van JOUW repository, onafhankelijk van de oorspronkelijke externe repository.
3. Moesten er later toch wijzigingen aangebracht worden in de remote-start-code-repository die je wil fetchen dan kan je volgend commando gebruiken:
```bash
git subtree pull --prefix=<subdirectory_waarin_je_de_files_wil_pullen> <remote-start-code-repository-link> <branch_name> --squash
```

{{% notice warning %}}
Heb je toch al zo een repo gecloned en is dit nu een een submodule waarvan je de changes niet kan committen dan kan je best je wijzigingen even kopieren naar een andere locatie buiten je repo. De subdirectory verwijderen en nu als subtree toevoegen en dan je oplossingen terug erin zetten.
{{% /notice %}}

### Naamgeving bij indienen (tags)
Om de verschillende (sub)opdrachten te identificeren wordt er gebruik gemaakt van "tags" bij het committen van je finale resultaat. (In de eerste lessen worden deze onderwerpen klassikaal uitgelegd zodat alle studenten de juiste werkwijze gebruiken).

De naamgeving: voor het committen van je resultaat van "Opdracht 1" gebruik je de tag `v1`, voor "Opdracht 2" gebruik je de tag `v2`, enzoverder.

**Tags pushen naar github**: `git push --tags`
**Tags op github verwijderen**: `git push --delete origin <tagname>`. Dit moet je doen als je bijvoorbeeld lokaal een tag verplaatst hebt.
**Lokaal een tag deleten**: `git tag --delete <tagname>`

### Deadlines

Er is 1 deadline voor alle opdrachten van het eerste deel (SES+EES): **vrijdag 21/03/2025 23u59**.

De deadline voor alle opdrachten van het tweede deel (enkel SES) is **vrijdag 23/05/2025 23u59**.

## Opdrachten deel 1

Deze opdrachten worden door **alle** studenten (SES+EES) gemaakt en ingediend via Github Classroom. Klik [hier](https://classroom.github.com/a/ucHZpfam) voor de invite van de assignment "Opdrachten deel 1".

### Versiebeheer

#### Opdracht 1:

1. Maak een nieuwe directory aan op je computer voor deze opdracht.
2. Initialiseer die directory als een git directory.
3. Download de file [`Candy_Crush_spelregels.txt`](/files/Candy_Crush_spelregels.txt) van Toledo en plaats de file in je git directory.
4. Commit nu de veranderingen in je directory en tag als `v1`.

#### Opdracht 2:

1. Los alle TODO's in de file `Candy_Crush_spelregels.txt` op en commit de veranderingen in je directory en tag als `v2`.

#### Opdracht 3:

1. Maak een branch aan met als naam "opdracht_branch".
2. Switch naar de nieuwe branch.
3. In de `Candy_Crush_spelregels.txt` vul de gegevens voor auteur aan met je eigen voornaam en achternaam. Commit nu je aanpassingen in deze branch.
4. Switch terug naar de main branch en vul daar andere waarden in voor de auteur gegevens (Dit is bijvoorbeeld een bug in je software). Commit nu je aanpassingen in deze branch.
5. Merge de "opdracht_brach" met je main branch. Hiervoor zal je dus ook het mergeconflict moeten oplossen.
6. Commit nu de veranderingen in je directory en tag als `v3`.

<!-- #### Opdracht ?:
Maak eerst een GitHub account aan (indien je er nog geen hebt), en log in.
Klik vervolgens op [deze link](https://classroom.github.com/a/ucHZpfam) om je GitHub account aan je naam te linken in GitHub Classroom en een persoonlijke repository aan te maken waarlangs je je opdrachten indient.

1. Maak op Github een lege public repository aan en push (--all) je git directory naar de nieuwe remote repository.
2. Maak op Github een nieuwe branch aan voor je repository genaamd “pull-requests”.
3. Fork de repository van een medestudent en onderaan zijn/haar "Candy_Crush_spelregels.txt" het volgende toe:<br/>
   `Nagekeken door <je naam>`<br/>
4. Doe een pull request naar de originele student zijn/haar repository (gebruik de pull-request branch)
5. Kijk in je eigen repository de pull request na en merge pull in de pull-request branch
6. Merge in je eigen repository de pull-request branch in je main branch. (Lokaal of via Github) Tag die merge commit met de tag `v4.x`.

{{% notice warning %}}
Vergeet je **tags** ook niet te pushen naar je remote repository op Github.
{{% /notice %}}
-->

<!-- TODO: plaats alles wat je voorlopig hebt in een folder genaamd 'git-github' -->

### Build systems

#### Opdracht 4: 
1. Maak een folder in de root directory van deze git repository aan genaamd 'build-systems'. Maak hierin 3 subdirectories: 'c', 'java' en 'python'.
2. Extract alle files in [dit zip bestand](/files/build_system_c-start.zip) naar de `./build-systems/c` directory en los alle TODO's in de documenten op. _(Je kan met de TODO tree extensie in VSCode gemakkelijk zien of je alle TODO's gedaan hebt)_ OF clone [de repository](https://github.com/ArneDuyver/ses-friendshiptester-c-start) van het startproject into `./build-systems/c`.
   - Je moet de makefile aanpassen zodat het C-programma kan gecompiled+gelinked worden met `make compile`, dat de binaries en object files gedeletet worden met `make clean` en dat je project gerund kan worden met `make run` (je moet hier ook flags kunnen meegeven). De uiteindelijk binary moet in de root van je c-project directory staan met als naam `friendshiptester.bin`

3. In de `./build-systems/java`-directory:
Extract alle files in [dit zip bestand](/files/ses-friendshiptester-java-start.zip) OF clone [de repository](https://github.com/ArneDuyver/ses-friendshiptester-java-start). Schrijf een simpele makefile dat de volgende dingen kan doen en plaats het in de root van je directory:
- `compile`: Compileert de bronbestanden naar de `/build`-directory
- `jar` : packaged alle klassen naar een jar genaamd 'app.jar' in de 'build'-directory met entrypoint de 'App'-klasse.
- `run` : Voert de jar file uit
- `clean`: Verwijdert de '.class'-bestanden en het '.jar'-bestand uit de 'build'-directory 

4. in de `./build-systems/python`-directory:
Extract alle files in [dit zip bestand](/files/ses-friendshiptester-python-start.zip) OF clone [de repository](https://github.com/ArneDuyver/ses-friendshiptester-python-start). Schrijf een simpele makefile dat de volgende dingen kan doen en plaats het in de root van je directory:
- `compile`: Compileert de bronbestanden naar de single 'friendshiptester.bin' file in de "/dist"-directory
- `clean`: Verwijdert het '.bin'-bestand
- `run` : Voert de 'friendshiptester.bin' uit
- `test` : Voert de 'app.py' uit

5. Commit nu de veranderingen en tag als `v4`.

### Dependency management

#### Opdracht 5: 
1. Maak een folder in de root directory van je git repository aan genaamd 'dependency-management'. Maak een javaproject met gradle aan in een subdirectory genaamd "checkneighbours" met package "be.ses".
   - _Je kan de package name meegeven met het 'gradle init' commando op de volgende manier: `gradle init --package be.ses`_
2. Voeg het volgende toe aan de `.gitignore`-file in de root van je repository.
```.gitignore
./dependency-management/checkneighbours/.gradle/
./dependency-management/checkneighbours/build/
```
3. In je `checkneigbours` gradle project, moet je nu een klasse 'CheckNeighboursInGrid' maken en onderstaande static method in implementeren, los ook de **TODO** op:
{{% notice style="code" title="CheckNeighboursInGrid.java (klik om te verbergen/tonen)" expanded=true %}}
```java
/**
* This method takes a 1D Iterable and an element in the array and gives back an iterable containing the indexes of all neighbours with the same value as the specified element
*@return - Returns a 1D Iterable of ints, the Integers represent the indexes of all neighbours with the same value as the specified element on index 'indexToCheck'.
*@param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
*@param width - Specifies the width of the grid.
*@param height - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
*@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
*/
public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid,int width, int height, int indexToCheck){
   // TODO write your code below so you return the correct result
   Iterable<Integer> result = null;
   return result;
}
```
{{% /notice %}}
Voorbeeld: voor het volgende grid:
```
[ 0, 0, 1, 0,
  1, 1, 0, 2,
  2, 0, 1, 3,
  0, 1, 1, 1 ]
```
-> `getSameNeighboursIds(grid, 4, 4, 5)` returns `[2,4,10]`

4. Genereer een `checkneigbours.jar` file van deze Javaklasse in de `build/libs` directory van je gradle project. Kopiëer de jar-file naar de folder "./dependency-management/" (dus `./dependency-management/checkneigbours.jar`).

5. Commit nu de veranderingen en tag als `v5`.

#### Opdracht 6:
1. Maak een folder in de "./dependency-management/" directory van deze git repository genaamd 'checkneighbours_example'.

2. Extract het volgende gradle project in [dit zip bestand](/files/dependency_management_java-start.zip) naar die `./dependency-management/checkneighbours_example` directory en los alle TODO's in de documenten op. _(Je kan met de TODO tree extensie in VSCode gemakkelijk zien of je alle TODO's gedaan hebt)_ OF clone via subtree [de repository](https://github.com/ArneDuyver/ses-dependency_management_java-start) van het startproject into `./dependency-management/checkneighbours_example`.
   - kopieer je `checkneigbours.jar` naar de `app/lib` directory van het gradle project 
   - Pas de `build.gradle` aan zodat de `main`-method in de `App.java` correct gebuild en gerund kan worden.

3. Voeg ook het volgende toe aan de `.gitignore`-file in de root van je repository.
```.gitignore
./dependency-management/checkneighbours_example/.gradle/
./dependency-management/checkneighbours_example/build/
```

4. Commit nu de veranderingen en tag als `v6`.

{{% notice warning %}}
Krijg je een type error in de `main` method van `App.java` in het 'checkneighbours_example' project. Verwissel de code ``
{{% /notice %}}

### Test Driven Development

#### Opdracht 7:

1. Voeg minstens 6 unittesten toe aan je gradle project `checkneighbours` waarbij de unittesten je oplossing voor de `getSameNeighboursIds`-methode testen van de `CheckNeighboursInGrid`-klasse. De testen moeten aan volgende vereisten voldoen:
- Gebruik de correcte naamgeving voor de testen
- Gebruik het correcte Arrange, Act, Assert principe
- Maak minstens 1 test die een `Exception` test
- Probeer de randgevallen te testen en elke test moet een ander scenario testen. (bv. 1 test waarbij je een element aan de linker rand test mag maar 1 keer voorkomen. Eentje in een hoek testen kan dan wel al een ander scenario zijn.)

2. Commit nu de veranderingen en tag als `v7` en push.

#### Opdracht 8: getSameNeighboursIds functie in python testen

1. Maak een folder in de root van je repository aan genaamd "./tdd/".
2. Extract het volgende python project in [dit zip bestand](/files/tdd_python_opdracht-start.zip) naar die `./tdd` directory OF clone via subtree [de repository](https://github.com/ArneDuyver/tdd_python_opdracht-start)  het startproject into `./tdd`.
   - Het project bevat een `checkneighbours.py`-bestand met een implementatie van de functie `get_same_neighbours_ids()`
   - Het project bevat ook een file `checkneighbours_test.py` met het geraamte van het unittest framework al ingevuld. 

3. Commit nu de veranderingen en tag als `v8` en push.

### Continuous Integration and Continuous Deployment

#### Opdracht 9:

1. Gebruik Github Actions om een CI-pipeline aan te maken die steeds je testen van je 'checkneighbours' uit opdracht 7 uitvoert wanneer je naar de `main` branch pusht. Voorzie ook een badge in een README.md in de root folder van je git directory zodat je in één opslag kan zien wanneer de tests falen.

2. **Let op!** Je wil nu dus de testen runnen van een specifiek gradle project dat in een subdirectory staat. Meer info [hier](https://stackoverflow.com/questions/57806624/github-actions-how-to-build-project-in-sub-directory)

3. Commit nu de veranderingen en tag als `v9` en push.


## Opdrachten deel 2

Deze opdrachten worden **enkel door de studenten van SES**  gemaakt en ingediend via Github Classroom.

### Gebruik van GenAI en samenwerking

GenAI (ChatGPT, Copilot, Claude, ...) maakt een razendsnelle opgang in het leven van iedereen, en dus ook van informatici. Deze tools zijn dan ook zeer onderlegd in het schrijven van (beperkte hoeveelheden) code.

Omdat dit deel van het vak gericht is op het **zelf leren programmeren**, en de opdrachten gequoteerd worden, is het **niet toegestaan** om dergelijke tools in te schakelen om de opdrachten op te lossen. Met andere woorden, we verwachten dus dat je **alle ingediende code zelf geschreven hebt**. Ook code delen met mede-studenten, of buitenstaanders om hulp vragen, is om dezelfde reden niet toegestaan.

Op het examen zal een examenvraag toegevoegd worden die nagaat in welke mate je deze opdracht effectief onder de knie hebt. De score op die vraag zal ook invloed hebben op de punten van je werk doorheen het semester.

Tenslotte is het absoluut geen probleem om GenAI tools te gebruiken bij het studeren en verwerken van de leerstof. Begrijp je een concept niet goed, en wil je meer uitleg of voorbeelden? Wil je hulp bij het oplossen van een oefening? Krijg je een compiler error niet opgelost? Gebruik deze tools dan gerust (hou er wel rekening mee dat er ook fouten in hun antwoord kunnen zitten)! Maar gebruik ze dus niet voor de opdracht.

### Setup

Maak [hier (via GitHub Classroom)](https://classroom.github.com/a/OhUGgJxZ) je repository aan voor de assignment "Opdrachten deel 2". Deze repository is nog leeg.
Clone jouw (lege) repository naar je eigen machine, en voeg vervolgens een **tweede remote** repository toe, namelijk `git@github.com:KULeuven-Diepenbeek/ses-startcode-deel2-2425.git` onder de naam `startcode`. Dat kan je met volgend commando:
```bash
git remote add startcode git@github.com:KULeuven-Diepenbeek/ses-startcode-deel2-2425.git
```

Je lokale repository heeft nu dus **twee** remote repositories (kijk dit na met `git remote -v`):
- **origin**: je eigen GitHub repository
- **startcode**: de GitHub repository met de startcode die door ons aangeleverd wordt

Haal de laatste versie van de startcode op en merge die in je repository via `git pull startcode main`. Doe dit minstens voor elke nieuwe opdracht, en eventueel ook tussendoor (als er wijzigingen/bugfixes aan onze startcode gebeurd zijn).

### Startcode

De startcode bevat een JavaFX-applicatie voor het spel CandyCrush.
Het is een Gradle-project voor IntelliJ, en maakt gebruik van Java 21.
Je kan de folder openen als project in IntelliJ.
De applicatie is gestructureerd volgens het Model-View-Controller (MVC) patroon.

Er zijn ook reeds enkele testen voorgedefinieerd met AssertJ, maar de set van testen is **niet volledig**. De voorgedefinieerde testen dienen voornamelijk om na te gaan of je oplossing automatisch getest kan worden.

{{% notice warning "Belangrijk!" %}}
Omdat je inzendingen (deels) automatisch verbeterd zullen worden, is het noodzakelijk dat **alle** gegeven testen compileren **zonder enige aanpassingen**.
Je mag uiteraard wel extra testen toevoegen.
Ook is het geen groot probleem indien een bepaalde test niet slaagt --- zolang hij maar uitgevoerd kan worden.
{{% /notice %}}

### Opdracht 1: Records

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode main` uit te voeren in jouw lokale repository.
{{% /notice %}}


1. Maak, in package `ses.candycrush.board` een record genaamd `BoardSize` dat de grootte van een candycrush speelveld voorstelt als een aantal rijen (`rows`) en aantal kolommen (`columns`).
   - Het aantal rijen en kolommen moeten beiden groter zijn dan 0, zoniet gooi je een `IllegalArgumentException`.

2. Maak, in hetzelfde package, ook een tweede record genaamd `Position` dat een _geldige_ positie van een cel op een candycrush-speelveld voorstelt (`row` en `column`).
   - Rijen en kolommen worden genummerd vanaf 0.
   - Aan de constructor van een Position-object moeten een rij- en kolomnummer alsook een `BoardSize` meegegeven worden.
   - Indien de positie ongeldig is voor de grootte van het speelveld, moet je een `IllegalArgumentException` gooien.

3. Voeg in `Position` volgende methodes toe, samen met zinvolle tests voor elke methode:

   - een methode `int toIndex()` die de positie omzet in een index. Voor veld met 2 rijen en 4 kolommen lopen de indices als volgt:

     ```
     0 1 2 3
     4 5 6 7
     ```
   - een **statische** methode `Position fromIndex(int index, BoardSize size)` die de positie teruggeeft die overeenkomt met de gegeven index.
     Deze methode moet een `IllegalArgumentException` gooien indien de index ongeldig is.
   - methodes `boolean isFirstRow()`, `boolean isFirstColumm()`, `boolean isLastRow()`, en `boolean isLastColumn()` die aangeven of de positie zich in de eerste/laatste rij/kolom van het bord bevindt.
   - een methode `Collection<Position> neighbors()` die alle posities van (geldige) directe buren (horizontaal en vertikaal) in het speelveld teruggeeft.
   - een methode `boolean isNeighborOf(Position other)` die nagaat of de gegeven positie een directe buur is van de huidige positie. Gooit een `IllegalArgumentException` als de gegeven positie bij een andere bordgrootte hoort.

4. Voeg in `BoardSize` de volgende methodes toe, samen met zinvolle tests:

   - een methode `Collection<Position> positions()` die een collectie (bv. een ArrayList) met daarin alle posities op het bord teruggeeft.

5. Voeg, in package `ses.candycrush.model`, een **sealed interface** `Candy` toe, met subklassen (telkens een **record**, die je in de Candy-interface plaatst) voor

   - `NoCandy`, wat staat voor het ontbreken van een snoepje.
   - `NormalCandy`, met een attribuut `color` (een int met mogelijke waarden 0, 1, 2, of 3); je gooit een `IllegalArgumentException` indien een ongeldige kleur opgegeven wordt.
   - Elk van de volgende speciale soorten snoepjes:
      * een `RowSnapper`
      * een `MultiCandy`
      * een `RareCandy`
      * een `TurnMaster`

6. Voeg, in het package `ses.candycrush.model`, een record `Switch` toe. Een Switch-object stelt een mogelijke wissel voor tussen twee posities `first` en `second`.
   - Beide posities moeten buren zijn van elkaar; je constructor moet een `IllegalArgumentException` gooien indien dat niet het geval is.
   - Zorg ervoor dat het niet uitmaakt in welke volgorde de twee posities meegegeven worden aan de constructor; maar het veld `first` moet uiteindelijk de positie bevatten met de kleinste index (zoals gedefinieerd bij `toIndex()`).

7. Voeg aan dat Switch-record een operatie `other(Position pos)` toe die de andere positie teruggeeft dan de gegeven positie (dus als je first meegeeft, krijg je second terug, en omgekeerd).
   - Indien de gegeven positie geen deel uitmaakt van het Switch-object, gooi je een `IllegalArgumentException`.

8. Pas nu je code (`CandyCrushGame`, `CandyCrushBoardUI`, en `Controller`) aan zodat die op zoveel mogelijk plaatsen gebruik maakt van bovenstaande records in plaats van int's (Switch moet je nog niet gebruiken). Dus:
   - op elke plaats waar voorheen een int voor width en/of height gebruikt of teruggegeven werd, moet nu `BoardSize` gebruikt worden
   - op elke plaats waar voorheen een rij- en/of kolomnummer gebruikt of teruggegeven werd, moet nu een `Position` object gebruikt worden
   - op elke plaats waar voorheen een int gebruikt of teruggegeven werd om een snoepje aan te duiden, moet nu een `Candy` object gebruikt worden.
   - In de klasse CandyCrushBoardUI moet je **pattern matching** gebruiken om een JavaFX Node aan te maken voor de gegeven candy op de gegeven positie.

   Laat de compiler je helpen met het vinden van de nog aan te passen code, door bv. eerst de type van een veld te veranderen.

9. Maak in de model-klasse `CandyCrushGame` een publieke methode `Collection<Switch> getPotentialSwitchesOf(Position pos)` die alle mogelijke wissels teruggeeft (bv. in een ArrayList) van positie `pos`. Positie `pos` kan wisselen met een andere positie indien (1) ze buren zijn; (2) geen van beiden een NoCandy zijn; én (3) de snoepjes op beide posities verschillend zijn (qua soort of kleur). (_Deze methode zullen we in een latere opdracht verfijnen, maar voorlopig volstaat dit_).

10. In de `update()`-methode van `CandyCrushBoardUI` kan je nu code uit commentaar halen, gerelateerd aan het tonen van hints (zie de TODO daarin).

Als je dit alles correct gedaan hebt, zou alle code moeten compileren, zou de applicatie moeten uitvoeren (`./gradlew run`), en zouden de testen moeten slagen.

Tag het resultaat als `v1` en push dit naar jouw remote repository (origin) op GitHub: `git push origin`.

### Opdracht 2: Generics

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode main` uit te voeren in jouw lokale repository.
{{% /notice %}}


Een rechthoekig spelbord met cellen (vakjes) kan ook voor andere spellen dan Candycrush gebruikt worden; denk bijvoorbeeld aan schaken, dammen, zeeslag, go, ... . In deze opdracht ga je daarom een algemene klasse ontwikkelen voor een rechthoekig spelbord.

1. Maak, in package `ses.candycrush.board`, een generische `Board`-klasse, met een generische parameter die het type van elke cel weergeeft.
   Deze klasse moet maximaal gebruik maken van de `BoardSize` en `Position` records uit de vorige opdracht. De inhoud van de cellen kan je bijhouden als een array of ArrayList.

2. De constructor van `Board` vereist enkel een `BoardSize`. Alle cellen zijn initieel `null`.

2. Voeg volgende publieke methodes toe en implementeer ze:

   - `BoardSize getSize()` die de grootte van het bord teruggeeft (als BoardSize-object)
   - `boolean isValidPosition(position)` die nagaat of de gegeven positie geldig is voor dit bord (dus of ze in het bord ligt).
   - `getCellAt(position)` om de cel op een gegeven positie van het bord op te vragen. Als de positie ongeldig is, gooi je een `IllegalArgumentException`.
   - `void replaceCellAt(position, newCell)` om de cel op een gegeven positie te vervangen door een meegegeven object. Als de positie ongeldig is, gooi je een `IllegalArgumentException`.
   - `void fill(cellCreatorFunction)` om het hele bord te vullen met objecten die teruggegeven worden door de `cellCreatorFunction`. De `cellCreatorFunction` is een `java.util.function.Function`-object die, gegeven een Position-object als argument, een nieuw cel-object teruggeeft als resultaat.
   - een methode `copyTo(otherBoard)` die alle cellen van het huidige bord kopieert naar het meegegeven bord. Als het meegegeven bord niet dezelfde afmetingen heeft, gooi je een `IllegalArgumentException`.

   Zorg dat deze laatste twee methodes zo algemeen mogelijk zijn qua type, en schrijf telkens een test waarin je hier gebruik van maakt.

3. Gebruik de Board-klasse nu zoveel mogelijk in je model van Candycrush, waarbij de cellen `Candy`-objecten zijn.

Tag het resultaat als `v2` en push dit naar je remote repository op Github.
