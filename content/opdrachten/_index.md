---
title: Opdrachten
weight: 210
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

### Pushen van branches en tags naar de remote repository
Standaard worden lokale branches en tags niet altijd gepushed naar een remote repository, gebruik daarom volgende flags bij het `push`-command: `$ git push --all --tags`

### Deadlines

Er is 1 deadline voor alle opdrachten van het eerste deel (SES+EES): **vrijdag 23/03/2026 23u59**.

<!-- De deadline voor alle opdrachten van het tweede deel (enkel SES) is **zondag 25/05/2025 23u59**. -->

## Opdrachten deel 1 SES/EES

Hier vind je de opdrachten voor de permanente evaluatie van SES+EES. De opdrachten voor **deel 1** worden gemaakt door alle studenten (SES+EES).

### Github classroom link
Klik [hier](https://classroom.github.com/a/bitFVHgR) voor de invite van de assignment "SES en EES Opdrachten deel 1".

### Opdrachten rond Versiebeheer

#### Opdracht 1:
1. Download volgende file [`Candy_Crush_spelregels.txt`](https://kuleuven-diepenbeek.github.io/ses-course/files/Candy_Crush_spelregels.txt) via rechtermuisklik, '`opslaan als`' en sla de file op in volgende directory/folder `opdracht1-4_Versiebeheer`. **Behoud de juiste naam!**
2. Doe nu op de correcte manier een commit en tag de commit met `v1`.

#### Opdracht 2:
1. Los alle TODO's in de file `Candy_Crush_spelregels.txt` op.
    - Verander `<voornaam>` en `<naam>` nog **NIET**, dit doe je in opdracht 3.
    - Los de `TODO` op rond hoofdletters en kleine letters.
    - Los de `TODO` op rond speciale snoepjes, verzin dus zelf vier namen en verwissel `<naamSnoepje>` steeds met jouw verzonnen naam.
2. Doe nu op de correcte manier een commit en tag de commit met `v2`.

#### Opdracht 3:
1. Maak een branch aan met als naam "`opdracht_branch`".
2. Switch naar die branch.
3. In de file `Candy_Crush_spelregels.txt` verander je nu `<voornaam>` en `<achternaam>` naar jouw voor- en achternaam.
4. Commit je aanpassingen in deze `opdracht_branch` branch.
5. Switch terug naar de `main` branch en vul daar andere waarden in voor de auteur gegevens (Dit is bijvoorbeeld een bug in je software). 
6. Commit nu je aanpassingen in deze `main` branch.
7. Merge de `opdracht_branch` nu met de `main` branch. Hiervoor zal je dus ook het mergeconflict moeten oplossen.
8. Doe nu op de correcte manier een commit en tag de commit met `v3`.

#### Laatste belangrijke stap!

Push nu je aanpassingen naar Github met behulp van `git push --all --tags`

Dankzij `--all` wordt je branch mee gepushed en dankzij `--tags` worden je tags mee gepushed.

### Opdrachten rond Build Systems

#### Opdracht 4
1. In de subfolder `c/` binnenin de folder `opdracht4_BuildSystems`: 
    - Je moet de makefile aanpassen zodat het C-programma kan gecompiled+gelinked worden met `make compile`, 
    - dat de binaries en object files gedeletet worden met `make clean` 
    - en dat je project gerund kan worden met `make run` (je moet hier ook flags kunnen meegeven). 
    - De uiteindelijk binary moet in de root van je c-project (dus subfolder `c` in de folder `opdracht4_BuildSystems`) directory staan met als naam `friendshiptester.bin`. Er mag geen andere binary file in staan!

2. In de subfolder `java/` binnenin de folder `opdracht4_BuildSystems`: 
    - Schrijf een simpele makefile dat de volgende dingen kan doen:
        - `compile`: Compileert de bronbestanden naar de `/build`-directory
        - `jar`: packaged alle klassen naar een jar genaamd 'app.jar' in de 'build'-directory met entrypoint de 'App'-klasse.
        - `run`: Voert de jar file uit
        - `clean`: Verwijdert de '.class'-bestanden en het '.jar'-bestand uit de 'build'-directory

3. In de subfolder `python/` binnenin de folder `opdracht4_BuildSystems`: 
    - Schrijf een simpele makefile dat de volgende dingen kan doen:
        - `compile`: Compileert de bronbestanden naar de single 'friendshiptester.bin' file in de "/dist"-directory
        - `test`: Voert de 'app.py' uit
        - `run`: Voert de 'friendshiptester.bin' uit
        - `clean`: Verwijdert het '.bin'-bestand

4. Doe nu op de correcte manier een commit en tag de commit met `v4` en push naar Github.

### Opdrachten rond Dependency Management

#### Opdracht 5
1. In de folder `opdracht5-6_DependencyManagement` maak je een javaproject met **gradle** aan in een subdirectory genaamd "checkneighbours" met package "be.ses".
   - _Je kan de package name meegeven met het 'gradle init' commando op de volgende manier: `gradle init --package be.ses`_
2. In je `checkneighbours` gradle project, moet je nu een klasse 'CheckNeighboursInGrid' maken en onderstaande static method in implementeren, los ook de **TODO** op:
{{% notice style="code" title="CheckNeighboursInGrid.java (klik om te verbergen/tonen)" expanded=true %}}
```java
/**
CheckNeighboursInGrid.java

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

4. Genereer een `checkneighbours.jar` file van deze Javaklasse in de `build/libs` directory van je gradle project. Kopiëer de jar-file naar de folder ".../opdracht5-6_DependencyManagement/" (dus `.../opdracht5-6_DependencyManagement/checkneighbours.jar`).

5. Doe nu op de correcte manier een commit en tag de commit met `v5` en push naar Github.


#### Opdracht 6
1. In het gradle project `.../opdracht5-6_DependencyManagement/checkneighbours_example`moet je alle TODO's oplossen. _(Je kan met de TODO tree extensie in VSCode gemakkelijk zien of je alle TODO's gedaan hebt)_
    - kopieer je `checkneighbours.jar` (die in de directory `.../opdracht5-6_DependencyManagement/` zou moeten staan van **opdracht 5**) naar de `app/lib` directory van het gradle project 'checkneighbours_example'.
    - Pas de `build.gradle` in dat project ook aan zodat de `main`-method in de `App.java` correct gebuild en gerund kan worden.

2. Doe nu op de correcte manier een commit en tag de commit met `v6` en push naar Github.

### Opdrachten rond Test Driven Development

#### Opdracht 7
1. Voeg minstens 6 unittesten toe aan je gradle project `checkneighbours` (in de directory `.../opdracht5-6_DependencyManagement/checkneighbours/`) waarbij de unittesten je oplossing voor de `getSameNeighboursIds`-methode testen van de `CheckNeighboursInGrid`-klasse. De testen moeten aan volgende vereisten voldoen:
    - Gebruik de correcte naamgeving voor de testen
    - Gebruik het correcte Arrange, Act, Assert principe
    - Maak minstens 1 test die een `Exception` test
    - Probeer de randgevallen te testen en elke test moet een ander scenario testen. (bv. 1 test waarbij je een element aan de linker rand test mag maar 1 keer voorkomen. Eentje in een hoek testen kan dan wel al een ander scenario zijn.)

2. Doe nu op de correcte manier een commit en tag de commit met `v7` en push naar Github.

#### Opdracht 8
1. Het python project in de subdirectory `.../opdracht7-8_TestDrivenDevelopment/tdd_python` bevat:
   - een `checkneighbours.py`-bestand met een implementatie van de functie `get_same_neighbours_ids()`
   - een file `checkneighbours_test.py` met het geraamte van het unittest framework al ingevuld. 

2. Schrijf nu dezelfde testen als in **Opdracht 7** maar dan voor het python programma.

3. Doe nu op de correcte manier een commit en tag de commit met `v8` en push naar Github.

## Opdrachten rond Continuous Integration and Continuous Deployment

#### Opdracht 9
1. Gebruik Github Actions om een CI-pipeline aan te maken die steeds je testen van je 'checkneighbours' uit opdracht 7 uitvoert wanneer je naar de `main` branch pusht. Voorzie ook een badge in een README.md in de root folder van deze git directory zodat je in één opslag kan zien wanneer de tests falen.

2. **Let op!** Je wil nu dus de testen runnen van een specifiek gradle project dat in een subdirectory staat. Meer info [hier](https://stackoverflow.com/questions/57806624/github-actions-how-to-build-project-in-sub-directory)

3. Doe nu op de correcte manier een commit en tag de commit met `v9` en push naar Github.