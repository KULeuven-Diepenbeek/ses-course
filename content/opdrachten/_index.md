---
title: Opdrachten
weight: 210
author: Arne Duyver, koen Yskout
draft: true
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

De deadline voor alle opdrachten van het tweede deel (enkel SES) is **zondag 25/05/2025 23u59**.

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

Volg onderstaande stappen nauwgezet om je project juist te configureren.

1. Maak [hier (via GitHub Classroom)](https://classroom.github.com/a/OhUGgJxZ) je repository aan voor de assignment "Opdrachten deel 2". Deze repository is nog leeg.
2. Clone jouw (lege) repository naar een folder op je eigen machine.
3. Voeg vervolgens een **tweede remote** aan die repository op je machine toe, namelijk `git@github.com:KULeuven-Diepenbeek/ses-startcode-deel2-2425.git` onder de naam `startcode`. Dat kan je met volgend commando:
   ```bash
   git remote add startcode git@github.com:KULeuven-Diepenbeek/ses-startcode-deel2-2425.git
   ```

   Je lokale repository heeft nu dus **twee** remote repositories (kijk dit na met `git remote -v`):
   - **origin**: je eigen GitHub repository
   - **startcode**: de GitHub repository met de startcode die door ons aangeleverd wordt

4. Haal de laatste versie van de startcode op en merge die in je repository via `git pull startcode main`. Doe dit minstens voor elke nieuwe opdracht, en eventueel ook tussendoor (als er wijzigingen/bugfixes aan onze startcode gebeurd zijn).

5. Open de folder `CandyCrush` uit de repository als project in IntelliJ. **Opgelet!** zorg dat je de `CandyCrush` folder als project opent in IntelliJ, en _niet_ de bovenliggende folder van je repository (`ses-opdrachten-deel2-...`)!

Als alles goed gegaan is, wordt je project herkend en geïmporteerd als een Gradle-Java-project. Het is normaal dat de testen nog fouten geven bij het compileren, aangezien die code verwachten die je nog moet schrijven als deel van de opdracht.
Je kan normaalgezien wel al de `main`-methode in `CandyCrushMain` uitvoeren.

![IntelliJ bij het importeren van het project.](/img/opdracht2/opdracht2-intellij.png)

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
   - een methode `Collection<Position> neighbors()` die alle posities van (geldige) directe buren (horizontaal en verticaal) in het speelveld teruggeeft.
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
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode 02-generics` uit te voeren in de `main`-branch van jouw lokale repository.
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
   - `void fill(cellCreatorFunction)` om het hele bord te vullen met objecten die teruggegeven worden door de `cellCreatorFunction`. De `cellCreatorFunction` is een `java.util.function.Function`-object dat, gegeven een Position-object als argument, het cel-object teruggeeft wat op die positie geplaatst moet worden.
   - een methode `copyTo(otherBoard)` die alle cellen van het huidige bord kopieert naar het meegegeven bord. Als het meegegeven bord niet dezelfde afmetingen heeft, gooi je een `IllegalArgumentException`.

   Zorg dat deze laatste twee methodes zo algemeen mogelijk zijn qua type, en schrijf telkens een test waarin je hier gebruik van maakt.

3. Gebruik de Board-klasse nu zoveel mogelijk in `CandyCrushGame`, waarbij de cellen `Candy`-objecten zijn.

Tag het resultaat als `v2` en push dit naar je remote repository op GitHub.

### Opdracht 3: Collections

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode 03-collections` uit te voeren in de `main`-branch van jouw lokale repository.
{{% /notice %}}


1. Hou in je klasse `Board` de elementen (cellen) bij via een `Map`, met `Position` als de sleutel.
2. Hou ook een omgekeerde `Map` bij, dus van een element (cel) naar alle posities (in een `Set`) waarop dat element voorkomt.
   - Dus: (in de context van CandyCrush) kan je uit deze `Map` een `Set` halen met alle posities waarop een bepaalde Candy voorkomt.
   - Deze structuur moet altijd overeenkomen met de andere map (van positie naar cel).
     Zorg er dus voor dat andere klassen die de klasse Board gebruiken beide maps enkel via gepaste methodes kunnen aanpassen.
3. Voorzie een methode `getPositionsOfElement` die alle posities teruggeeft waarop het gegeven element (cel) voorkomt, gebruik makend van de `Map` van hierboven.
   De teruggegeven collectie mag niet aanpasbaar zijn (dus: de ontvanger mag ze niet kunnen aanpassen).
4. Als je klasse Board goed geëncapsuleerd was, hoef je geen andere code (buiten de klasse Board) aan te passen na bovenstaande wijzigingen.

Tag het resultaat als `v3` en push dit naar je remote repository op Github.

### Opdracht 4: Concurrency

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode 04-concurrency` uit te voeren in de `main`-branch van jouw lokale repository.
{{% /notice %}}

In deze opdracht gaan we uit van een situatie dat er meerdere spelers (threads) tegelijk met één Board-object werken.

1. Maak de `fill`-methode in de `Board`-klasse thread-safe. Wanneer meerdere threads fill oproepen, moet het resultaat overeenkomen met dan van één van de threads (met andere woorden, het uiteindelijke bord mag niet deels opgevuld zijn door één thread, en deels door een andere).
2. Schrijf een test (met jcstress) die het correcte gedrag van `fill` nagaat. 
   > In de startcode is jcstress al toegevoegd als dependency, en staat er een klasse `ses.candycrush.board.Assignment04_Concurrency_Tests` klaar in de map `src/jcstress/java` die je kan aanpassen. Vergeet niet eerst je gradle configuratie te herladen in IntelliJ.
3. Schrijf in package `ses.candycrush.experiment` een klasse `MultithreadedBoardClient` met een `main`-methode.
   In die methode maak je een `Board<Integer>` aan (met grootte 10x10 en 0 als initiële waarde voor elke cel).
   Gebruik vervolgens een Executor om 10 threads te maken die elk de waarde van elke cel verhogen met 1.
   Als dat correct gebeurt, zodat alle cellen van het bord uiteindelijk waarde 10 moeten hebben (ga dat na).
   _(Dit client-programma moet geen GUI starten en/of JavaFX gebruiken.)_

Tag het resultaat als `v4` en push dit naar je remote repository op Github.

### Opdracht 5: Streams

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode 05-streams` uit te voeren in de `main`-branch van jouw lokale repository.
{{% /notice %}}


> **Elke methode** die hieronder vermeld wordt moet **volledig geïmplementeerd worden met streams**.
> In het bijzonder mogen er **_geen_ if-statement, for- of while-lussen** gebruikt worden.
> Je mag ook de **`forEach()`-methode niet gebruiken**.
> _(Als een methode je echt niet lukt met streams, mag je wel if-statements of lussen gebruiken --- dat zal je wel een deel van de punten op deze opdracht kosten.)_

1. Maak in klasse `Position` vier methodes (uitsluitend met behulp van streams).

   - `public Stream<Position> walkLeft()`
   - `public Stream<Position> walkRight()`
   - `public Stream<Position> walkUp()`
   - `public Stream<Position> walkDown()`

   Elk van die methodes geeft een stream terug met alle (geldige) posities die links, rechts, boven, of onder een positie liggen (_rij 0 is de bovenste rij_).
   De streams **starten met de positie zelf**, dan de positie die vlak naast de this-positie ligt, dan die daarnaast, enzovoort.
   Kijk naar de meegeleverde testen voor het verwachte gedrag.

   _Hint: de makkelijkste manier is om te vertrekken van een `IntStream`._


2. {{% notice note %}}
   De methodes in Match zijn optioneel; het niet maken ervan (of ze oplossen met lussen) zal geen negatieve invloed hebben op je score (je kan er wel bonuspunten mee verdienen).
   {{% /notice %}}
   De startcode bevat een klasse `ses.candycrush.model.Match`. Een Match-object stelt een opeenvolging van posities voor (bijgehouden in een lijst).
   Deze posities mogen niet `null` zijn, en moeten steeds naast elkaar liggen, ofwel allemaal horizontaal (geordend van links naar rechts), ofwel allemaal verticaal (geordend van boven naar onder).
   
   Implementeer in deze klasse (uitsluitend met behulp van streams) de statische methodes 
   - `containsNull`
   - `areAdjacentFromLeftToRight`
   - `areAdjacentFromTopToBottom`
   
   die gebruikt worden om deze voorwaarden te controleren.

   _Hint: de makkelijkste manier voor de `areAdjacent`-methodes is om te vertrekken van een `IntStream`._
   
3. Voorzie in klasse `CandyCrushGame` een methode `Set<Match> findAllMatches()` die alle matches met een **minimale lengte van 3** op het bord teruggeeft.
   
   Bijvoorbeeld: voor het bord hieronder moet de methode een `Set` met daarin twee Match-objecten teruggeven, namelijk de rode match (de posities van de 3 rode snoepjes, van links naar rechts) en de groene match (de posities van 4 groene snoepjes, van boven naar onder).
   <img src="/img/opdracht2/all-matches.png" width="300px"></img>

   Om deze `findAllMatches`-methode te implementeren, zijn volgende hulpfuncties handig. Zet deze (publiek) in je CandyCrushGame-klasse, en implementeer ze volledig via streams.
   Het idee van deze hulpmethodes is om eerst de mogelijke startposities van een match te zoeken (het meest linkse of bovenste snoepje van de match), en van daaruit (naar rechts of naar onder) de langste reeks van dezelfde snoepjes te zoeken.

   - `static <T> boolean firstTwoEqual(T value, Stream<T> stream)`. Deze methode geeft terug of de eerste twee elementen in de gegeven stream hetzelfde zijn als de gegeven waarde. Als de stream minder dan 2 elementen bevat, geef je `false` terug.
   
   - `Stream<Position> horizontalStartingPositions()`. Deze methode geeft een stream terug van alle posities op het bord waar een snoepje staat, en _links_ van die positie een _ander_ soort snoepje (of geen snoepje) staat. Deze posities zijn dus de mogelijke startposities van een horizontale match. (Die match loopt dus naar rechts). We kijken hier nog niet naar de lengte van de match. _Hint: Maak gebruik van `firstTwoEqual` en de `walkLeft`-methode._

      Maak ook de (gelijkaardige) methode `Stream<Position> verticalStartingPositions()` die de mogelijke startposities van een verticale match teruggeeft.
   
      In de figuren hieronder wordt met een kruisje aangeduid welke posities een mogelijke startpositie zijn, en teruggegeven moeten worden.
      <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 2rem">
         <figure><img src="/img/opdracht2/startpos-hor.png" width="250px" /><figcaption style="text-align: center">Mogelijke horizontale startposities</figcaption></figure>
         <figure><img src="/img/opdracht2/startpos-vert.png" width="250px" /><figcaption  style="text-align: center">Mogelijke verticale startposities</figcaption></figure>
      </div>

   - `List<Position> longestMatchToRight(Position pos)` en `List<Position> longestMatchDown(Position pos)`. Deze methodes geven de langste match terug als een lijst van posities. De teruggegeven posities vertrekken op de gegeven positie en lopen in de richting aangegeven door de methodenaam. Deze methode geeft ook matches van lengte 1 of 2 terug. (_Hint: gebruik de walk-methodes van Position_).

      Voor het voorbeeld hierboven zijn dit de matches die teruggegeven worden door `longestMatchDown` voor elk van de verticale startposities:
       <img src="/img/opdracht2/longest-match-vert.png" width="250px" />

   Eens je beschikt over deze hulpmethodes, kan je `findAllMatches` implementeren (via streams). (_Hint: [`Stream.concat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html#concat(java.util.stream.Stream,java.util.stream.Stream))_)

4. Vervang de bestaande implementatie van de methode `getPotentialSwitchesOf` door een implementatie die **volledig werkt via streams**.
   Deze mag enkel Switch-objecten teruggeven die voldoen aan volgende voorwaarden:
   - de posities zijn twee naburige posities;
   - er staat een snoepje (geen NoCandy) op beide posities;
   - beide snoepjes zijn verschillend;
   - na het uitvoeren van de wissel zou er minstens één match ontstaan (dus: `findAllMatches` geeft een niet-lege Set terug). 
     _Let op: de uitvoering van `getPotentialSwitchesOf` moet het bord in de oorspronkelijke toestand achterlaten!_

   Volgende methodes zijn hierbij handig; je mag deze dan ook kopiëren in je `CandyCrushGame`-klasse:
   ```java
   // kopieer naar CandyCrushGame.java
   private boolean leadsToMatch(Switch sw) {
      doSwitch(sw);
      var result = !findAllMatches().isEmpty();
      doSwitch(sw);
      return result;
   }

   private void doSwitch(Switch sw) {
      var firstCandy = getCandyAt(sw.first());
      var secondCandy = getCandyAt(sw.second());
      setCandyAt(sw.first(), secondCandy);
      setCandyAt(sw.second(), firstCandy);
   }
   ```

   De test van `getPotentialSwitchesOf` in `Assignment01_Records_Tests` zal niet meer slagen. Deze werd daarom verwijderd uit de startcode. In `Assignment05_Streams_Tests` vind je een nieuwe test.


Tag het resultaat als `v5` en push dit naar je remote repository op Github.

> Vergeet niet om de tag zelf ook expliciet te pushen: `git push origin v5`. Dit gebeurt namelijk niet automatisch bij een `git push`.
> Je kan ook alle tags in 1 keer pushen met `git push --tags`.
> Controleer op je GitHub-repository of je de tags kan zien.

### Opdracht 6: Recursie

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode 06-recursie` uit te voeren in de `main`-branch van jouw lokale repository.

**Opgelet**: de startcode voegt een regel toe aan je `build.gradle` (namelijk `testImplementation('org.ow2.asm:asm-tree:9.8')`). Herlaad de Gradle-configuratie in IntelliJ als er klassen niet gevonden worden bij het uitvoeren van de test.
{{% /notice %}}

**Elke methode** die hieronder vermeld wordt moet **recursief geïmplementeerd worden**. Dat wil zeggen dat ze minstens 1 nuttige recursieve oproep moeten bevatten. Je mag daarnaast ook for- en while-lussen gebruiken, en ook extra parameters en/of hulpmethodes toevoegen indien nodig.
_(Als een methode je echt niet lukt met recursie, mag je ze ook op een andere manier implementeren --- dat zal je wel een deel van de punten op deze opdracht kosten.)_

1. Maak (in klasse `CandyCrushGame`) een recursieve methode `void clearMatch(List<Position> match)` die alle snoepjes die deel uitmaken van de gegeven match (= de posities uit één van de matches gevonden door `findAllMatches` uit de vorige opdracht) van het speelbord verwijdert. De plaatsen waarop een verwijderd snoepje stond, blijven leeg (en krijgen dus een `NoCandy` in de plaats).

2. Maak (in klasse `CandyCrushGame`) een recursieve methode `fallDownTo(Position pos)` die alle snoepjes die boven positie `pos` staan zoveel mogelijk naar beneden laat vallen, tot ze op een ander snoepje of positie `pos` terecht komen. Bijvoorbeeld, in onderstaande situatie (met `pos` aangegeven door het pijltje) verandert de methode de situatie aan de linkerkant in die van de rechterkant.

   <div style="width: 400px; display: grid; grid: auto-flow / repeat(8, 50px); gap: 30px; align-items: center;">

   ```goat
      *
      o
      *
      *
   -> o
      o
      *
   ```

   wordt

   ```goat
      o
      o
      *
      *
   -> *
      o
      *
   ```

   <div style="grid-column: span 2; justify-self: center;">en</div>

   ```goat
      o
      *
      o
      *
   -> *
      o
      *
   ```

   wordt

   ```goat
      o
      o
      *
      *
   -> *
      o
      *
   ```

   </div>

3. Maak (in klasse `CandyCrushGame`) een recursieve methode `boolean updateBoard()` die alle matches zoekt, verwijdert, en de overblijvende snoepjes naar beneden laat vallen (gebruik hiervoor de methodes `findAllMatches`, `clearMatch`, en `fallDownTo`). Als er hierdoor nieuwe matches ontstaan, moeten die ook weer verwijderd worden en moeten de snoepjes weer naar beneden vallen etc., totdat er geen matches meer zijn. De methode moet `true` teruggeven indien er minstens één match verwijderd werd, en `false` indien dat niet zo is.

   Merk op dat we (in tegenstelling tot de 'gewone' CandyCrush) het bord _niet_ terug opvullen met nieuwe snoepjes: in onze variant is het de bedoeling om het bord zo leeg mogelijk te maken. Om alles zo eenvoudig mogelijk te houden, negeren we ook de effecten van de speciale snoepjes.

4. Vervang de code van de methode `selectCandy` in `CandyCrushGame.java` door hetvolgende:
   ```java
   public void selectCandy(Position position) {
      if (!hasCandyAt(position)) {
            previousSelected = null;
            return;
      }
      if (!hasAnySelected()) {
            previousSelected = position;
      } else if (position.isNeighborOf(previousSelected)) {
            var sw = new Switch(position, previousSelected);
            doSwitch(sw);
            if (!updateBoard()) {
               doSwitch(sw);
               previousSelected = position;
            } else {
               previousSelected = null;
            }
      } else {
            previousSelected = position;
      }
   }
   ```

   Met deze wijzigingen, en correcte implementaties tot nu toe, krijg je een speelbare versie van CandyCrush.

Tag het resultaat als `v6` en push dit naar je remote repository op Github.

> Vergeet niet om de tag zelf ook expliciet te pushen: `git push origin v6`. Dit gebeurt namelijk niet automatisch bij een `git push`.
> Je kan ook alle tags in 1 keer pushen met `git push --tags`.
> Controleer op je GitHub-repository of je de tags kan zien.

### Opdracht 7: Backtracking

{{% notice task Startcode %}}
Merge eerst de laatste versie van de startcode in je repository door `git pull startcode 07-backtracking` uit te voeren in de `main`-branch van jouw lokale repository.
{{% /notice %}}

De spelregels van CandyCrush nog eens samengevat:

- De speler mag enkel snoepjes van plaats wisselen die vlak naast elkaar liggen.
- Twee snoepjes van plaats proberen te wisselen zonder dat dat onmiddellijk leidt tot een match is niet toegelaten; beide snoepjes blijven dan op hun oorspronkelijke plaats.
- De speler mag geen snoepje wisselen met een lege plaats, enkel met een ander snoepje.
- Wanneer er een match is (3 of meer dezelfde snoepjes naast elkaar, horizontaal of verticaal), verdwijnen die snoepjes en vallen de andere snoepjes naar beneden. Dit herhaalt zich tot er geen matches meer zijn.

We voegen nu ook een score toe:
- De score verhoogt telkens er snoepjes verwijderd worden, en dat met het aantal snoepjes dat verwijderd wordt. Dus 3 snoepjes verwijderen verhoogt de score met 3.
- Elk verwijderd snoepje telt slechts één keer voor de score, ook als het tegelijkertijd deel is van zowel een horizontale als een verticale match.

De enige operatie die de speler dus kan uitvoeren is twee naburige snoepjes van plaats wisselen, om zo nieuwe matches te creëren, die vervolgens verdwijnen. Daardoor verandert het bord en kan er opnieuw een wissel gedaan worden. Het doel van het spel is om het bord zo leeg mogelijk te krijgen. Omdat het bord verandert na elke wissel, is de volgorde van die wissels erg belangrijk.

Schrijf in klasse `CandyCrushGame` een methode `public List<Switch> maximizeScore()` met **een backtracking-algoritme** dat de **beste sequentie** van geldige wissels zoekt voor het huidige spel. De beste sequentie is deze die de **hoogste score** oplevert.
Als er meerdere sequenties zijn die tot dezelfde score leiden, geef je de **kortste** terug. Zijn er meerdere met dezelfde score en lengte, dan maakt het niet uit welke je daarvan teruggeeft.

Na afloop van deze methode moet de toestand van het bord **onveranderd** zijn: de methode berekent enkel de optimale lijst van switches, zonder het bord te wijzigen.

_Hints:_

- Je mag, naast de `maximizeScore`-methode, uiteraard ook nog hulpmethodes toevoegen. Een nuttige hulpmethode is eentje om alle mogelijke switches op het bord te zoeken (gebruik makend van `getPotentialSwitchesOf`).
- Maak zeker gebruik van de `updateBoard()`-methode de vorige opdracht. Je kan die uitbreiden om de score aan te passen.
- Besteed niet te veel aandacht aan efficiëntie; kies liever voor duidelijkheid.
- Je kan werken met een kopie van het bord om aanpassingen uit te voeren zonder de oorspronkelijke toestand te veranderen.

In de bijgeleverde tests worden de volgende drie spellen gebruikt:
- Spel 1
   <img src="/img/opdracht2/model1.png" width="100px"></img>
   ```java
   CandyCrushGame model1 = Util.createBoardFromString("""
            @@o#
            o*#o
            @@**
            *#@@""");
   ```
   **Oplossing**: De maximumscore is **16** na **4 wissels**.
   {{% notice style=info title="Meer detail" expanded=false %}}
   Er zijn 3 sequenties van 4 wissels die tot een eindscore van 16 leiden, namelijk:

   - (r2,c1) ⇄ (r3,c1), (r2,c1) ⇄ (r3,c1), (r2,c0) ⇄ (r2,c1), (r2,c3) ⇄ (r3,c3)
   - (r2,c1) ⇄ (r3,c1), (r1,c0) ⇄ (r1,c1), (r2,c1) ⇄ (r3,c1), (r2,c3) ⇄ (r3,c3)
   - (r2,c1) ⇄ (r3,c1), (r1,c0) ⇄ (r1,c1), (r1,c3) ⇄ (r2,c3), (r2,c1) ⇄ (r3,c1)

   _(rA,cB) ⇄ (rC,cD) betekent een wissel van het snoepje op rij A, kolom B met dat op rij C, kolom D._

   De eerste sequentie ziet er als volgt uit:
   {{% multicolumn min=100px %}}
   <img src="/img/opdracht2/model1-1.png" width="100px"></img>

   <img src="/img/opdracht2/model1-2.png" width="100px"></img>

   <img src="/img/opdracht2/model1-3.png" width="100px"></img>

   <img src="/img/opdracht2/model1-4.png" width="100px"></img>

   <img src="/img/opdracht2/model1-5.png" width="100px"></img>

   {{% /multicolumn %}}

  {{% /notice %}}
   

- Spel 2
   <img src="/img/opdracht2/model2.png" width="130px"></img>

   ```java
   CandyCrushGame model2 = Util.createBoardFromString("""
            #oo##
            #@o@@
            *##o@
            @@*@o
            **#*o""");
   ```
   **Oplossing**: De maximumscore is **23** na **7 wissels**.
   {{% notice style=info title="Meer detail" expanded=false %}}
   Er zijn 4 verschillende sequenties van 7 wissels die tot een eindscore van 23 leiden:
   - (r2,c0) ⇄ (r2,c1), (r3,c3) ⇄ (r3,c4), (r1,c2) ⇄ (r1,c3), (r2,c2) ⇄ (r3,c2), (r2,c1) ⇄ (r2,c2), (r4,c2) ⇄ (r4,c3), (r4,c3) ⇄ (r4,c4)
   - (r3,c3) ⇄ (r3,c4), (r2,c0) ⇄ (r2,c1), (r1,c2) ⇄ (r1,c3), (r2,c2) ⇄ (r3,c2), (r2,c1) ⇄ (r2,c2), (r4,c2) ⇄ (r4,c3), (r4,c3) ⇄ (r4,c4)
   - (r3,c3) ⇄ (r3,c4), (r1,c2) ⇄ (r1,c3), (r2,c2) ⇄ (r3,c2), (r2,c0) ⇄ (r2,c1), (r2,c1) ⇄ (r2,c2), (r4,c2) ⇄ (r4,c3), (r4,c3) ⇄ (r4,c4)
   - (r3,c3) ⇄ (r3,c4), (r1,c2) ⇄ (r1,c3), (r2,c0) ⇄ (r2,c1), (r2,c2) ⇄ (r3,c2), (r2,c1) ⇄ (r2,c2), (r4,c2) ⇄ (r4,c3), (r4,c3) ⇄ (r4,c4)
  {{% /notice %}}
- Spel 3
   <img src="/img/opdracht2/model3.png" width="160px"></img>

   ```java
   CandyCrushGame model3 = Util.createBoardFromString("""
            #@#oo@
            @**@**
            o##@#o
            @#oo#@
            @*@**@
            *#@##*""");
   ```
   **Oplossing**: De maximumscore is **33** na **9 wissels** (dit berekenen kan een tijdje duren).
   {{% notice style=info title="Meer detail" expanded=false %}}
   Voor model3 (score 33 na 9 wissels) zijn er volgende 7 sequenties:

   - (r1,c0) ⇄ (r2,c0), (r2,c3) ⇄ (r2,c4), (r4,c5) ⇄ (r5,c5), (r2,c2) ⇄ (r2,c3), (r2,c5) ⇄ (r3,c5), (r5,c1) ⇄ (r5,c2), (r2,c2) ⇄ (r3,c2), (r5,c0) ⇄ (r5,c1), (r4,c4) ⇄ (r5,c4)
   - (r2,c3) ⇄ (r2,c4), (r1,c0) ⇄ (r2,c0), (r4,c5) ⇄ (r5,c5), (r2,c2) ⇄ (r2,c3), (r2,c5) ⇄ (r3,c5), (r5,c1) ⇄ (r5,c2), (r2,c2) ⇄ (r3,c2), (r5,c0) ⇄ (r5,c1), (r4,c4) ⇄ (r5,c4)
   - (r2,c3) ⇄ (r2,c4), (r4,c5) ⇄ (r5,c5), (r1,c0) ⇄ (r2,c0), (r2,c2) ⇄ (r2,c3), (r2,c5) ⇄ (r3,c5), (r5,c1) ⇄ (r5,c2), (r2,c2) ⇄ (r3,c2), (r5,c0) ⇄ (r5,c1), (r4,c4) ⇄ (r5,c4)
   - (r2,c3) ⇄ (r2,c4), (r4,c5) ⇄ (r5,c5), (r2,c2) ⇄ (r2,c3), (r2,c5) ⇄ (r3,c5), (r1,c0) ⇄ (r2,c0), (r5,c1) ⇄ (r5,c2), (r2,c2) ⇄ (r3,c2), (r5,c0) ⇄ (r5,c1), (r4,c4) ⇄ (r5,c4)
   - (r2,c3) ⇄ (r2,c4), (r4,c5) ⇄ (r5,c5), (r2,c2) ⇄ (r2,c3), (r2,c5) ⇄ (r3,c5), (r5,c1) ⇄ (r5,c2), (r1,c0) ⇄ (r2,c0), (r2,c2) ⇄ (r3,c2), (r5,c0) ⇄ (r5,c1), (r4,c4) ⇄ (r5,c4)
   - (r2,c3) ⇄ (r2,c4), (r4,c5) ⇄ (r5,c5), (r2,c2) ⇄ (r2,c3), (r2,c5) ⇄ (r3,c5), (r5,c1) ⇄ (r5,c2), (r5,c0) ⇄ (r5,c1), (r2,c2) ⇄ (r3,c2), (r4,c0) ⇄ (r5,c0), (r4,c4) ⇄ (r5,c4)
   - (r2,c3) ⇄ (r2,c4), (r4,c5) ⇄ (r5,c5), (r2,c2) ⇄ (r2,c3), (r1,c0) ⇄ (r2,c0), (r2,c5) ⇄ (r3,c5), (r5,c1) ⇄ (r5,c2), (r2,c2) ⇄ (r3,c2), (r5,c0) ⇄ (r5,c1), (r4,c4) ⇄ (r5,c4)
   {{% /notice %}}


Tag het resultaat als `v7` en push dit naar je remote repository op Github.

> Vergeet niet om de tag zelf ook expliciet te pushen: `git push origin v7`. Dit gebeurt namelijk niet automatisch bij een `git push`.
> Je kan ook alle tags in 1 keer pushen met `git push --tags`.
> Controleer op je GitHub-repository of je de tags kan zien.

_Dit was de laatste opdracht voor het vak._
