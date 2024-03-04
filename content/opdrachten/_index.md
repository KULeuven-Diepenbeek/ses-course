---
title: Opdrachten
weight: 9
author: Arne Duyver
draft: false
---
# Permanente evaluatie 2024
## Algemene informatie
Om de permanente evaluatie opdrachten in te dienen voor het vak Software Engineering Skills maken we gebruik van Github. In de lessen zal je onder begeleiding een gratis account aanmaken op dit platform. Voor elke opdracht zal je een repository gebruiken voor het indienen van je opdrachten. Elke student is verplicht een link door te geven naar die repository voor het evalueren van de opdrachten. 
De link naar je repository vul je aan in de [online Excel file](https://kuleuven-my.sharepoint.com/:x:/g/personal/koen_yskout_kuleuven_be/EcY4jplKXppFlj2tKbjM7UYBQYTlTcv6HnnEU1H8G6aEGg?e=tNfkXd). Je kan bewerken met het wachtwoord `SES_Github`. 

Om de verschillende (sub)opdrachten te identificeren wordt er gebruik gemaakt van "tags" bij het committen van je finale resultaat. (In de eerste lessen worden deze onderwerpen klassikaal uitgelegd zodat alle studenten de juiste werkwijze gebruiken).  
De naamgeving: voor het committen van je resultaat van "Opdracht 1" gebruik je de tag `v1.0`. Het laatste cijfer kan je verhogen indien je een andere versie van je opdracht wil indienen. Bijvoorbeeld: indien je voor opdracht 1 twee versies indient getagged als `v1.0` en `v1.1`. Dan wordt enkel met `v1.1` rekening gehouden in de evaluatie aangezien dit de laatste versie van opdracht 1 moet zijn.  

### Deadline 
Er is 1 deadline voor alle opdrachten van het eerste deel van SES: vrijdag 15/03/2024 23u59 

## Opdrachten

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
3. In de `Candy_Crush_spelregels.txt` vul de gegevens voor auteur aan met je eigen voornaam en achternaam. Commit nu je je aanpassingen in deze branch.
4. Switch terug naar de main branch en vul daar andere waarden in voor de auteur gegevens (Dit is bijvoorbeeld een bug in je software). Commit nu je je aanpassingen in deze branch.
5. Merge de "opdracht_brach" met je main branch. Hiervoor zal je dus ook het mergeconflict moeten oplossen.
6. Commit nu de veranderingen in je directory en tag als `v3.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie). 
#### Opdracht 4:
1. Maak op Github een lege public repository aan en push (--all) je git directory naar de nieuwe remote repository. 
2. Maak op Github een nieuwe branch aan voor je repository genaamd “pull-requests”.
3. Fork de repository van een medestudent en onderaan zijn/haar "Candy_Crush_spelregels.txt" het volgende toe:<br/> 
`Nagekeken door \<je naam\>`<br/>
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

[ 0, 0, 1, 0, <br>
  1, 1, 0, 2, <br>
  2, 0, 1, 3, <br>
  0, 1, 1, 1 ] 

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

### Test Driven Development
#### Opdracht 6: (Aanzet wordt in de les gegeven)

Maak een JavaFX (gebruik Gradle) project aan met als projectnaam candycrush.

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

<!-- ### Continuous Integration and Continuous Deployment
#### Opdracht 7:

Commit nu de veranderingen in je directory en tag als `v7.x` ('x' start bij 0 en verhoogt met 1 bij elke nieuwe ingestuurde versie) en push dit naar je remote repository op Github. -->
