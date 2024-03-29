---
title: Opdrachten
weight: 9
author: Arne Duyver, koen Yskout
draft: false
toc: true
---

# Permanente evaluatie 2024

## Algemene informatie

Om de permanente evaluatie opdrachten in te dienen voor het vak Software Engineering Skills maken we gebruik van Github. In de lessen zal je onder begeleiding een gratis account aanmaken op dit platform. Voor elke opdracht zal je een repository gebruiken voor het indienen van je opdrachten. Elke student is verplicht een link door te geven naar die repository voor het evalueren van de opdrachten.
De link naar je repository vul je aan in de [online Excel file](https://kuleuven-my.sharepoint.com/:x:/g/personal/koen_yskout_kuleuven_be/EcY4jplKXppFlj2tKbjM7UYBQYTlTcv6HnnEU1H8G6aEGg?e=tNfkXd). Je kan bewerken met het wachtwoord `SES_Github`.

Om de verschillende (sub)opdrachten te identificeren wordt er gebruik gemaakt van "tags" bij het committen van je finale resultaat. (In de eerste lessen worden deze onderwerpen klassikaal uitgelegd zodat alle studenten de juiste werkwijze gebruiken).  
De naamgeving: voor het committen van je resultaat van "Opdracht 1" gebruik je de tag `v1.0`. Het laatste cijfer kan je verhogen indien je een andere versie van je opdracht wil indienen. Bijvoorbeeld: indien je voor opdracht 1 twee versies indient getagged als `v1.0` en `v1.1`. Dan wordt enkel met `v1.1` rekening gehouden in de evaluatie aangezien dit de laatste versie van opdracht 1 moet zijn.

### Deadlines

Er is 1 deadline voor alle opdrachten van het eerste deel van SES: vrijdag 15/03/2024 23u59.

De deadline voor alle opdrachten van het tweede deel is vrijdag 15/05 23u59.

## Opdrachten deel 1

### Versiebeheer

#### Opdracht 1:

1. Maak een nieuwe directory aan op je computer voor deze opdracht.
2. Initialiseer die directory als een git directory.
3. Download de file [`Candy_Crush_spelregels.txt`](/files/Candy_Crush_spelregels.txt) van Toledo en plaats de file in je git directory.
4. Commit nu de veranderingen in je directory en tag als `v1.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie).

#### Opdracht 2:

1. Los alle TODO's in de file `Candy_Crush_spelregels.txt` op en commit de veranderingen in je directory en tag als `v2.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie)

#### Opdracht 3:

1. Maak een branch aan met als naam "opdracht_branch".
2. Switch naar de nieuwe branch.
3. In de `Candy_Crush_spelregels.txt` vul de gegevens voor auteur aan met je eigen voornaam en achternaam. Commit nu je aanpassingen in deze branch.
4. Switch terug naar de main branch en vul daar andere waarden in voor de auteur gegevens (Dit is bijvoorbeeld een bug in je software). Commit nu je aanpassingen in deze branch.
5. Merge de "opdracht_brach" met je main branch. Hiervoor zal je dus ook het mergeconflict moeten oplossen.
6. Commit nu de veranderingen in je directory en tag als `v3.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie).

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
