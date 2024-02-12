---
title: Opdrachten
weight: 9
author: Arne Duyver
draft: false
---
# Permanente evaluatie 2024
## Algemene informatie
Om de permanente evaluatie opdrachten in te dienen voor het vak Software Engineering Skills maken we gebruik van Github. In de lessen zal je onder begeleiding een gratis account aanmaken op dit platform. Voor elke opdracht zal je een repository gebruiken voor het indienen van je opdrachten. Elke student is verplicht een link door te geven naar die repository voor het evalueren van de opdrachten. 
De link naar je repository vul je aan in de [online Excel file](link_naar_file) die beschikbaar wordt gesteld via Toledo. 

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
