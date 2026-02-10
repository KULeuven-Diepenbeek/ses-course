---
title: Opdrachten
weight: 9
author: Arne Duyver, koen Yskout
draft: true
toc: true
---

# Permanente evaluatie 2025

## Algemene informatie

Om de permanente evaluatie opdrachten in te dienen voor het vak SES en het eerste deel van EES maken we gebruik van Github (Classroom). In de lessen zal je onder begeleiding een gratis account aanmaken op dit platform. Voor elke opdracht zal je een repository gebruiken voor het indienen van je opdrachten. 

Om de verschillende (sub)opdrachten te identificeren wordt er gebruik gemaakt van "tags" bij het committen van je finale resultaat. (In de eerste lessen worden deze onderwerpen klassikaal uitgelegd zodat alle studenten de juiste werkwijze gebruiken).  
De naamgeving: voor het committen van je resultaat van "Opdracht 1" gebruik je de tag `v1`. (Je kan altijd je tag veranderen naar een andere commit indien nodig)

### Deadlines

Er is 1 deadline voor alle opdrachten van het eerste deel van SES en EES: vrijdag 21/03/2024 23u59.

<!-- De deadline voor alle opdrachten van het tweede deel is zondag 19/05 23u59. -->

## Opdrachten deel 1

### Versiebeheer

#### Opdracht 1:

1. Maak een nieuwe directory aan op je computer voor deze opdracht.
2. Initialiseer die directory als een git directory.
3. Download de file [`Candy_Crush_spelregels.txt`](/files/Candy_Crush_spelregels.txt) en plaats de file in je git directory.
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

#### Opdracht 4:

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

### Dependency management

#### Opdracht 5:

- Voeg in je directory een java-project met gradle toe. (gebruik hiervoor IntelliJ)
- Kopiëer de .ignore file die gradle aangemaakt heeft naar de root folder van je git directory (overschrijf je eigen .gitignore)

- Maak een java klasse "CheckNeighboursInGrid" aan in je Gradle project. Maak in de klasse minstens 1 public static method:

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
  //write you code below
  return result;
}
```

Voorbeeld: voor het volgende grid:

```
[ 0, 0, 1, 0,
  1, 1, 0, 2,
  2, 0, 1, 3,
  0, 1, 1, 1 ]
```

-> getSameNeighboursIds(grid, 4, 4, 5) returns [2,4,10]

Genereer een .jar file van deze Javaklasse in de libs directory van je gradle project. Kopiëer de jar-file naar de root folder van je git repository.

Je gitrepo heeft nu dus volgende structuur:

```
/mijnGitDirectory
|
+--- .git/
|
+--- .gitignore
|
+--- ...
|
+--- mijnJarFile.jar
|
+--- mijnIntelliJProject/
     |
     +--- gradle/
     |
     +--- src/
     |
     + ...

```
<!--  TODO: orden lijst, noem specifieke naar voor jar en voeg toe aan gitignore -->

Commit nu de veranderingen in je directory en tag als `v5.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

_Krijg je je `.jar` file niet te zien in Github, dan kan het zijn dat je deze nog forcably moet toevoegen aan git via het commando `$ add -f *.jar`_

### Test Driven Development

#### Opdracht 6: (Aanzet wordt in de les gegeven)

Maak een JavaFX (gebruik Gradle) project aan met als projectnaam candycrush.
(Je kan [hier](https://github.com/KULeuven-Diepenbeek/ses-demos-exercises-student/tree/main/opdrachten/CandycrushJavaFXstartProject) een start 'candycrush' javaFX project terugvinden.)

Je gitrepo heeft nu dus volgende structuur:

```
/mijnGitDirectory
|
+--- .git/
|
+--- .gitignore
|
+--- ...
|
+--- mijnJarFile.jar
|
+--- mijnJarFileProject/
|
+--- CandyCrushIntelliJProject/
     |
     +--- gradle/
     |
     +--- src/
     |
     ...
     |
     +--- libs/
          |
          +--- mijnJarFile.jar
```

Het project moet gebruik maken van Model - View - Controller. Wanneer je applicatie start moet je een startscherm krijgen waarmee je inlogt in het spel. Dan moet je naar de spelpagina gebracht worden. Het spel moet minstens een grid bevatten waar nummers instaan (de verschillende snoepjes, 1 tot en met 5). Je moet op een snoepje kunnen klikken en wanneer dat snoepje 3 of meer reschtstreekse buren van hetzelfde type heeft, verwijder je die snoepjes en moeten die vakjes met willekeurige getallen terug gevuld worden. Elke snoepje dat je verwijderd is een punt waard. Voorzie ook een reset knop een nieuw spel te kunnen starten.

Voor het model moeten er minstens 10 unit testen geschreven worden. Maak ook gebruik van de debugger om problemen op te lossen in plaats van print out statements te gebruiken.

Commit nu de veranderingen in je directory en tag als `v6.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

### Continuous Integration and Continuous Deployment

#### Opdracht 7:

Gebruik Github Actions om een CI-pipeline aan te maken die steeds je testen van je JavaFX candycrush applicatie uitvoert wanneer je naar de `main` branch pusht. Voorzie ook een badge in een README.md in de root folder van je git directory zodat je in één opslag kan zien wanneer de tests falen. Bescherm ten slotte je `main` branch tegen pull request, zodat eerst de CI-pipeline succesvol moet zijn voordat mensen een pull-request kunnen indienen.

Commit nu de veranderingen in je directory en tag als `v7.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

## Opdrachten deel 2

### Advanced Java

#### Opdracht 8: Records

1. Maak een record (genaamd `BoardSize`) dat de grootte van een candycrush speelveld voorstelt (aantal rijen en aantal kolommen).
   Het aantal rijen en kolommen moeten beiden groter zijn dan 0.

2. Maak ook een tweede record (genaamd `Position`) dat een _geldige_ positie van een cel op een candycrush-speelveld voorstelt (rij- en kolomnummer).
   Bij het aanmaken van een Position-object moet een rij- en kolomnummer en een `BoardSize` meegegeven worden aan de constructor.
   Indien de positie niet geldig is gegeven de grootte van het speelveld, moet je een exception gooien.

3. Voeg in `Position` de volgende methodes toe, samen met zinvolle tests voor elke methode:

   - een methode `int toIndex()` die de positie omzet in een index. Voor veld met 2 rijen en 4 kolommen lopen de indices als volgt:

     ```
     0 1 2 3
     4 5 6 7
     ```

   - een **statische** methode `Position fromIndex(int index, BoardSize size)` die de positie teruggeeft die overeenkomt met de gegeven index.
     Deze methode moet een exception gooien indien de index ongeldig is.
   - een methode `Iterable<Position> neighborPositions()` die alle posities van (bestaande) directe buren in het speelveld teruggeeft.
   - een methode `boolean isLastColumn()` die aangeeft of de positie de laatste is in een rij.

4. Voeg in `BoardSize` de volgende methodes toe, samen met zinvolle tests:

   - een methode `Iterable<Position> positions()` die alle posities op het bord teruggeeft volgens oplopende index.

5. Voeg een sealed interface `Candy` toe, met subklassen (records) voor

   - `NormalCandy`, met een attribuut `color` (een int met mogelijke waarden 0, 1, 2, of 3)
   - Elk van de vier speciale snoepjes uit `Candy_Crush_Spelregels.txt`

6. Pas je code uit deel 1 (model en view) aan zodat die gebruik maakt maakt van bovenstaande records. Dus:

   - op elke plaats waar voorheen width en/of height gebruikt of teruggegeven werden, moet nu `BoardSize` gebruikt worden
   - op elke plaats waar voorheen een index (of rij- en kolomnummer) gebruikt of teruggegeven werd, moet nu een `Position` object gebruikt worden
   - op elke plaats waar voorheen een int gebruikt of teruggegeven werd om een snoepje aan te duiden, moet nu een `Candy` object gebruikt worden.
     - Je zal hiervoor (in je model) een methode moeten maken die een willekeurig Candy-object aan kan maken.
     - Maak (in je view) een methode `Node makeCandyShape(Position position, Candy candy)` die een switch-expressie gebruikt om een JavaFX Node te maken voor de gegeven candy op de gegeven positie.
       - Voor `NormalCandy` geef je een `Circle` terug met een (zelf te kiezen) kleur die overeenkomt met waarden 0, 1, 2, of 3.
       - Voor de vier speciale snoepjes geef je een vierkant (`Rectangle`) terug, elk met een eigen kleur.

   **Verwijder** ook alle methodes, variabelen, ... uit je code die **overbodig** geworden zijn door bovenstaande wijzigingen.

7. Voeg, naar analogie van `getSameNeighboursIds`, een methode `Iterable<Position> getSameNeighbourPositions(Position position)` toe aan je model, gebruik makend van de records en methodes die je geschreven hebt. Denk eraan dat je records moet vergelijken met `equals`, en niet met `==`.

8. Verwijder de jar-file en bijhorend IntelliJ-project van je bibliotheek waarin `getSameNeighboursIds` gedefinieerd werd (geen nood, als je deze gecommit hebt blijven ze bewaard in de vorige versies van je repository).
   Op dit moment moet je repository 'clean' zijn: enkel jouw candycrush broncode, de spelregels, en git en gradle files horen nog thuis in je repository.

Tag het resultaat als `v8.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

#### Opdracht 9: Generics

1. Een rechthoekig spelbord met cellen kan ook voor andere spellen dan Candycrush gebruikt worden; denk bijvoorbeeld aan schaken, dammen, zeeslag, go, ... .
   Maak daarom een generische `Board`-klasse, met een generische parameter die het type van elke cel weergeeft.
   Deze klasse moet gebruik maken van `BoardSize` en `Position` uit opdracht 8.

2. Voeg methodes toe:

   - `getCellAt(position)` om de cel op een gegeven positie van het bord op te vragen.
   - `replaceCellAt(position, newCell)` om de cel op een gegeven positie te vervangen door een meegegeven object.
   - `fill(cellCreator)` om het hele bord te vullen. De `fill`-functie heeft als parameter een `Function`-object (`cellCreator`) die, gegeven een Positie-object, een nieuw cel-object teruggeeft.
   - een methode `copyTo(otherBoard)` die alle cellen van het huidige bord kopieert naar het meegegeven bord. Als het meegegeven bord niet dezelfde afmetingen heeft, gooi je een exception.

   Zorg dat deze laatste twee methodes zo algemeen mogelijk zijn qua type, en schrijf telkens een test waarin je hier gebruik van maakt.

3. Gebruik de Board-klasse in je model van Candycrush, waarbij de cellen `Candy`-objecten zijn.

Tag het resultaat als `v9.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

#### Opdracht 10: Collections

1. Hou in je klasse `Board` de elementen (cellen) bij via een `Map`, met `Position` als de sleutel.
2. Hou ook een omgekeerde `Map` bij, van een element (cel) naar alle posities waarop dat element voorkomt. Deze structuur moet altijd overeenkomen met de andere map.
   Zorg er dus voor dat andere klassen die de klasse Board gebruiken beide maps enkel via gepaste methodes kunnen aanpassen.
3. Voorzie een methode `getPositionsOfElement` die alle posities teruggeeft waarop het gegeven element (cel) voorkomt.
   De teruggegeven collectie moet immutable zijn (de ontvanger mag ze niet kunnen aanpassen).

Tag het resultaat als `v10.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

#### Opdracht 11: _(bonus)_ Concurrency

{{% notice info %}}
Deze opdracht is **niet verplicht**. Als je ze wel maakt en indient kan je hiermee een bonuspunt verdienen op het deel van je taken.
{{% /notice %}}

1. Maak je `Board`-klasse (en alle methodes ervan) thread-safe. Als een programmeur die je klasse wil gebruiken zelf nog iets moet doen om de klasse thread-safe te gebruiken, moet je dat expliciet documenteren.
2. Schrijf een client-programma (`MultithreadingClient` met een main-methode) waarin een bord-object gemaakt wordt, en daarna twee threads tegelijk en continu `replaceCellAt` gebruiken om op willekeurige plaatsen een willekeurig snoepje te zetten. Dit client-programma moet geen GUI starten en/of JavaFX gebruiken.

Tag het resultaat als `v11.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

#### Opdracht 12: Streams

**Elke methode** die hieronder vermeld wordt moet **volledig geïmplementeerd worden met streams**.
In het bijzonder mogen er _geen_ for- of while-lussen gebruikt worden.
_(Als een methode je echt niet lukt met streams, mag je wel lussen gebruiken --- dat zal je wel een deel van de punten op deze opdracht kosten.)_

1. Verander in `BoardSize` het return type van `positions()` van `Iterable` naar `Collection`, zodat je straks makkelijk een stream van alle posities kan maken. Dit zou slechts een beperkt effect op de rest van je code mogen hebben, aangezien Collection een sub-interface is van Iterable.

2. Maak in klasse `Position` vier methodes.

   - `public Stream<Position> walkLeft()`
   - `public Stream<Position> walkRight()`
   - `public Stream<Position> walkUp()`
   - `public Stream<Position> walkDown()`

   Elk van die methodes geeft een stream terug met alle (geldige) posities die links, rechts, boven, of onder de this-positie liggen.
   De streams starten met de positie zelf, dan de positie die vlak naast de this-positie ligt, dan die daarnaast, enzovoort.

3. Maak (bijvoorbeeld in je model-klasse) een methode `Set<List<Position>> findAllMatches()` die een Set teruggeeft met daarin alle matches van het bord.
   Een match is de langste opeenvolging van 3 of meer dezelfde snoepjes naast elkaar (allemaal horizontaal, of allemaal vertikaal).
   Een match wordt voorgesteld als een lijst van posities (namelijk alle posities die deel uitmaken van de match en waarop hetzelfde soort snoepje staat, in volgorde van links naar rechts of van boven naar onder).

   Om deze `findAllMatches`-methode te implementeren, zijn volgende hulpfuncties handig (zet deze ook in je model-klasse, of waar je `findAllMatches` gaat implementeren):

   - `boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions)`. Deze methode geeft terug of op de eerste twee posities in de gegeven stream het gegeven soort snoepje staat (als de stream minder dan 2 posities bevat, geef je `false` terug).
   - `Stream<Position> horizontalStartingPositions()` en `Stream<Position> verticalStartingPositions()`. Deze methodes geven een stream terug van alle posities op het bord waar _links_ respectievelijk _boven_ die positie een _ander_ soort snoepje staat. Deze posities zijn dus de mogelijke startposities van een horizontale respectievelijk vertikale match. (Die match, als die bestaat, loopt dus naar rechts respectievelijk onder van elke positie in de stream). _Hint: Maak gebruik van `firstTwoHaveSameCandy` en de walk-methodes._
   - `List<Position> longestMatchToRight(Position pos)` en `List<Position> longestMatchDown(Position pos)`. Deze methodes geven de langste match terug als lijst van posities, vertrekkend vanop de gegeven positie en in de richting aangegeven door de methodenaam. (_Hint: gebruik de walk-methodes van Position_).

   Eens je beschikt over deze hulpmethodes werken, kan je `findAllMatches` implementeren. (_Hint: je moet mogelijk twee streams concateneren._)

Tag het resultaat als `v12.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

### Opdracht 13: Recursie

**Elke methode** die hieronder vermeld wordt moet **recursief geïmplementeerd worden**. Dat wil zeggen dat ze minstens 1 nuttige recursieve oproep moeten bevatten. Je mag daarnaast ook for- en while-lussen gebruiken, en ook extra parameters en/of hulpmethodes toevoegen indien nodig.
_(Als een methode je echt niet lukt met recursie, mag je ze ook op een andere manier implementeren --- dat zal je wel een deel van de punten op deze opdracht kosten.)_

1. Maak een recursieve methode `clearMatch(List<Position> match)` die alle snoepjes die deel uitmaken van de gegeven match (= één van de matches gevonden door `findAllMatches` uit opdracht 11) van het speelbord verwijdert. De plaatsen waarop een verwijderd snoepje stond, blijven leeg.

2. Maak een recursieve methode `fallDownTo(Position pos)` die alle snoepjes die boven positie `pos` staan zoveel mogelijk naar beneden laat vallen, tot ze op een ander snoepje of positie `pos` terecht komen. Bijvoorbeeld, in onderstaande situatie (met `pos` aangegeven door het pijltje) verandert de methode de situatie aan de linkerkant in die van de rechterkant.

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

3. Maak een recursieve methode `boolean updateBoard()` die alle matches zoekt, verwijdert, en de overblijvende snoepjes naar beneden laat vallen (gebruik hiervoor de methodes `findAllMatches`, `clearMatch`, en `fallDownTo`). Als er hierdoor nieuwe matches ontstaan, moeten die ook weer verwijderd worden en moeten de snoepjes weer naar beneden vallen etc., totdat er geen matches meer zijn. De methode moet `true` teruggeven indien er minstens één match verwijderd werd, en `false` indien dat niet zo is.

Merk dus op dat we het bord _niet_ terug opvullen met nieuwe snoepjes: in onze variant van CandyCrush is het de bedoeling om het bord zo leeg mogelijk te maken. Om alles zo eenvoudig mogelijk te houden, negeren we ook de effecten van de speciale snoepjes.

Tag het resultaat als `v13.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

### Opdracht 14: Backtracking

De enige operatie die de speler kan uitvoeren is twee naburige snoepjes van plaats wisselen, om zo nieuwe matches te creëren, die vervolgens verdwijnen. Daardoor verandert het bord en kan er opnieuw een wissel gedaan worden. Het doel van het spel is om het bord zo leeg mogelijk te krijgen. Omdat het bord verandert na elke wissel, is de volgorde van die wissels belangrijk.

Schrijf (gebruik makend van een backtracking-algoritme) een methode `maximizeScore` die de beste sequentie van wissels zoekt voor een gegeven spel. De beste sequentie is deze die de hoogste score oplevert.
De spelregels nog eens samengevat:

- De speler mag enkel snoepjes van plaats wisselen die vlak naast elkaar liggen.
- Twee snoepjes van plaats proberen te wisselen zonder dat dat onmiddellijk leidt tot een match is niet toegelaten; beide snoepjes blijven dan op hun oorspronkelijke plaats.
- De speler mag geen snoepje wisselen met een lege plaats, enkel met een ander snoepje.
- De score verhoogt telkens er snoepjes verwijderd worden, en dat met het aantal snoepjes dat verwijderd wordt. Dus 3 snoepjes verwijderen verhoogt de score met 3.

Als er meerdere sequenties zijn die tot dezelfde score leiden, geef je de kortste terug. Zijn er meerdere met dezelfde score en lengte, dan maakt het niet uit welke je daarvan teruggeeft.

_Hints:_

- Een wissel kan je voorstellen als de 2 posities van het bord waarop de snoepjes gewisseld worden.
- Maak zeker gebruik van de `updateBoard()`-methode uit opdracht 13. Je kan die uitbreiden om de score bij te houden.
- Het kan ook handig zijn om een nieuwe methode `boolean matchAfterSwitch(...)` te voorzien, die nagaat of er matches zijn na het uitvoeren van een bepaalde wissel (zonder die match te verwijderen en/of snoepjes te laten vallen). Om dat te doen kan je bijvoorbeeld de wissel uitvoeren, `findAllMatches()` gebruiken, en daarna de wissel terug ongedaan maken.
- Besteed niet te veel aandacht aan efficiëntie; kies liever voor duidelijkheid.
- Om je methode te testen kan het handig zijn om snel een bord te kunnen maken vanuit een ASCII-voorstelling. Volgende methodes kunnen als startpunt dienen. (Java laat multi-line strings toe als je `"""` gebruikt)

```java
CandycrushModel model1 = createBoardFromString("""
   @@o#
   o*#o
   @@**
   *#@@""");

CandycrushModel model2 = createBoardFromString("""
   #oo##
   #@o@@
   *##o@
   @@*@o
   **#*o""");

CandycrushModel model3 = createBoardFromString("""
   #@#oo@
   @**@**
   o##@#o
   @#oo#@
   @*@**@
   *#@##*""");


public static CandycrushModel createBoardFromString(String configuration) {
   var lines = configuration.toLowerCase().lines().toList();
   BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
   var model = createNewModel(size); // deze moet je zelf voorzien
   for (int row = 0; row < lines.size(); row++) {
      var line = lines.get(row);
      for (int col = 0; col < line.length(); col++) {
            model.setCandyAt(new Position(row, col, size), characterToCandy(line.charAt(col)));
      }
   }
   return model;
}

private static Candy characterToCandy(char c) {
   return switch(c) {
      case '.' -> null;
      case 'o' -> new NormalCandy(0);
      case '*' -> new NormalCandy(1);
      case '#' -> new NormalCandy(2);
      case '@' -> new NormalCandy(3);
      default -> throw new IllegalArgumentException("Unexpected value: " + c);
   };
}
```

De voorbeelden hierboven hebben volgende oplossing:

(**Update 13/5/2024**) Sommige maximumscores, wissels en sequenties hieronder waren fout; deze zijn nu verbeterd.
Deze gaan ervan uit dat een snoepje in een L-vorm _niet_ dubbel geteld wordt bij het verwijderen.

- `model1`: maximumscore 16 na 4 wissels
- `model2`: maximumscore 23 na 7 wissels (of score 24 als je een snoepje in een L-vorm dubbel telt)
- `model3`: maximumscore 33 na 9 wissels (dit kan een tijdje duren).

Ter info: Voor model2 zijn er 4 verschillende sequenties van 7 wissels die tot een eindscore van 23 leiden:

- (r2,c0) ⇄ (r2,c1) ; (r3,c3) ⇄ (r3,c4) ; (r1,c2) ⇄ (r1,c3) ; (r2,c2) ⇄ (r3,c2) ; (r2,c1) ⇄ (r2,c2) ; (r4,c2) ⇄ (r4,c3) ; (r4,c3) ⇄ (r4,c4)
- (r3,c3) ⇄ (r3,c4) ; (r2,c0) ⇄ (r2,c1) ; (r1,c2) ⇄ (r1,c3) ; (r2,c2) ⇄ (r3,c2) ; (r2,c1) ⇄ (r2,c2) ; (r4,c2) ⇄ (r4,c3) ; (r4,c3) ⇄ (r4,c4)
- (r3,c3) ⇄ (r3,c4) ; (r1,c2) ⇄ (r1,c3) ; (r2,c2) ⇄ (r3,c2) ; (r2,c0) ⇄ (r2,c1) ; (r2,c1) ⇄ (r2,c2) ; (r4,c2) ⇄ (r4,c3) ; (r4,c3) ⇄ (r4,c4)
- (r3,c3) ⇄ (r3,c4) ; (r1,c2) ⇄ (r1,c3) ; (r2,c0) ⇄ (r2,c1) ; (r2,c2) ⇄ (r3,c2) ; (r2,c1) ⇄ (r2,c2) ; (r4,c2) ⇄ (r4,c3) ; (r4,c3) ⇄ (r4,c4)

Voor model3 (score 33 na 9 wissels) zijn er volgende 7 sequenties:

- (r1,c0) ⇄ (r2,c0) ; (r2,c3) ⇄ (r2,c4) ; (r4,c5) ⇄ (r5,c5) ; (r2,c2) ⇄ (r2,c3) ; (r2,c5) ⇄ (r3,c5) ; (r5,c1) ⇄ (r5,c2) ; (r2,c2) ⇄ (r3,c2) ; (r5,c0) ⇄ (r5,c1) ; (r4,c4) ⇄ (r5,c4)
- (r2,c3) ⇄ (r2,c4) ; (r4,c5) ⇄ (r5,c5) ; (r2,c2) ⇄ (r2,c3) ; (r2,c5) ⇄ (r3,c5) ; (r5,c1) ⇄ (r5,c2) ; (r1,c0) ⇄ (r2,c0) ; (r2,c2) ⇄ (r3,c2) ; (r5,c0) ⇄ (r5,c1) ; (r4,c4) ⇄ (r5,c4)
- (r2,c3) ⇄ (r2,c4) ; (r4,c5) ⇄ (r5,c5) ; (r2,c2) ⇄ (r2,c3) ; (r2,c5) ⇄ (r3,c5) ; (r5,c1) ⇄ (r5,c2) ; (r5,c0) ⇄ (r5,c1) ; (r2,c2) ⇄ (r3,c2) ; (r4,c0) ⇄ (r5,c0) ; (r4,c4) ⇄ (r5,c4)
- (r2,c3) ⇄ (r2,c4) ; (r4,c5) ⇄ (r5,c5) ; (r2,c2) ⇄ (r2,c3) ; (r2,c5) ⇄ (r3,c5) ; (r1,c0) ⇄ (r2,c0) ; (r5,c1) ⇄ (r5,c2) ; (r2,c2) ⇄ (r3,c2) ; (r5,c0) ⇄ (r5,c1) ; (r4,c4) ⇄ (r5,c4)
- (r2,c3) ⇄ (r2,c4) ; (r4,c5) ⇄ (r5,c5) ; (r2,c2) ⇄ (r2,c3) ; (r1,c0) ⇄ (r2,c0) ; (r2,c5) ⇄ (r3,c5) ; (r5,c1) ⇄ (r5,c2) ; (r2,c2) ⇄ (r3,c2) ; (r5,c0) ⇄ (r5,c1) ; (r4,c4) ⇄ (r5,c4)
- (r2,c3) ⇄ (r2,c4) ; (r4,c5) ⇄ (r5,c5) ; (r1,c0) ⇄ (r2,c0) ; (r2,c2) ⇄ (r2,c3) ; (r2,c5) ⇄ (r3,c5) ; (r5,c1) ⇄ (r5,c2) ; (r2,c2) ⇄ (r3,c2) ; (r5,c0) ⇄ (r5,c1) ; (r4,c4) ⇄ (r5,c4)
- (r2,c3) ⇄ (r2,c4) ; (r1,c0) ⇄ (r2,c0) ; (r4,c5) ⇄ (r5,c5) ; (r2,c2) ⇄ (r2,c3) ; (r2,c5) ⇄ (r3,c5) ; (r5,c1) ⇄ (r5,c2) ; (r2,c2) ⇄ (r3,c2) ; (r5,c0) ⇄ (r5,c1) ; (r4,c4) ⇄ (r5,c4)

Tag het resultaat als `v14.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github.

_Dit was de laatste opdracht voor het vak._
