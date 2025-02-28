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

### Naamgeving bij indienen (tags)
Om de verschillende (sub)opdrachten te identificeren wordt er gebruik gemaakt van "tags" bij het committen van je finale resultaat. (In de eerste lessen worden deze onderwerpen klassikaal uitgelegd zodat alle studenten de juiste werkwijze gebruiken).

De naamgeving: voor het committen van je resultaat van "Opdracht 1" gebruik je de tag `v1`, voor "Opdracht 2" gebruik je de tag `v2`, enzoverder.

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

<!-- 
#### TODO candycrush tester Opdracht 5: dependency management

-->
