---
title: "Taak 1: Open Source Contributie"
---

De evaluatie van het vak verloopt op twee manieren: 50% examen op papier, 50% praktische taken, waarvan dit één is. Andere taken zullen ook tijdig via Toledo en/of in de lessen worden aangekondigd. Zie de ECTS fiche voor meer informatie betreffrende de puntenverdeling. 

Voor deze taak, een **open source software (OSS) contributie**, leer je de verschillende aspecten die aan bod komen in de hoofdstukken (versiebeheer, continuous integration, dependencies, unit testing, design patterns, ...) _toepassen_ in een **real-life omgeving** in plaats van een artifiële en netjes afgebakende oefening. Dit wil zeggen, jouw code gaat---alles alles goed verloopt---permanent deel uitmaken van een stuk software dat effectief gebruikt wordt, potentiëel door duizenden mensen.

Hoe gaat dit in zijn werk? Deze taak bestaat uit verschillende delen:

1. Zoek een geschikt OSS project dat voldoet aan onze criteria;
2. Draag bij aan het project door bepaalde issues helpen op te lossen;
3. Schrijf een verslag over het verloop.

Hieronder worden deze delen verder toegelicht.

{{% notice note %}}
**Lees eerst** ➡️ https://opensource.guide/how-to-contribute/ ⬅️ voor een gedetailleerd overzicht die je stap-voor-stap doorheen het contribution process gidst.
{{% /notice %}}


## 1. Zoek een geschikt OSS project

Bijdragen aan OSS klinkt misschien erg beangstigend: er zijn immers veel erg grote projecten waar de standaarden hoog liggen en ervaring vereist is. Daarom is het belangrijk om, in het kader van dit vak, een project te kiezen die bekende technologie gebruikt, en die niet te hoog maar ook niet te laag gegrepen is op vlak van code complexiteit.

Concreet moet een potentiëel project aan de volgende eisen voldoen:

1. Het project moet een **Java of Kotlin** project zijn. Merk op dat Android-projecten hier ook aan voldoen.
1. De gebruikte build tool moet **[Gradle](/dependency-management/gradle)** zijn (zoek `build.gradle` in de root).
2. Het project moet **recent onderhouden** zijn: de laatste commit (op de development branch) mag niet ouder dan een jaar zijn.
3. Er moet een `CONTRIBUTING.md` document aanwezig zijn waarin wordt uitgelegd hoe jij als toekomstige contributor kan meewerken aan het project.
4. Er moet een `LICENSE` bestand in de root van het project aanwezig zijn.
4. Er moeten reeds **andere contributors** aan het project hebben meegewerkt: op die manier weet je dat er een kans is dat je pull request wordt aanvaard.

Beperk je zoektocht tot projecten gehost op `github.com`: die omgeving en UI ken je ondertussen al. Via de volgende sites kan je leuke projecten ontdekken en sorteren/filteren op topic/programmeertaal en stars/last modified/etc. Het soort project maakt niet uit:

- https://gauger.io/contrib/#/language/java
- https://ovio.org/projects?skills=Java
- https://www.codetriage.com/?language=Kotlin
- https://24pullrequests.com/projects
- https://github.com/topics/java

Bekijk de bestaande **issues** lijst en lees de `CONTRIBUTING.md` documentatie. Spreekt het je aan en is het haalbaar?

{{% notice info %}}
Wees niet té ambitieus. Bedenk dat je het project lokaal moet kunnen builden om er aan te werken: Android-projecten vereisen extra tooling (zie de [app devevelopment cursus](https://kuleuven-diepenbeek.github.io/appdev-course/)) waar je veel tijd mee kan verliezen. Een project hoeft geen 200 stars te hebben om een nuttige bijdrage aan te kunnen leveren.<br/>Deze stap is mogelijks **de belangrijkste** dus geef het genoeg nadenktijd!
{{% /notice %}}

Wanneer je **twee geschikte projecten** hebt gekozen stuur je je keuzes (inclusief idee van wat je wil bijdragen) per mail door naar het onderwijsteam. Wij helpen je de beslissing te maken en behoeden je van het selecteren van een te moeilijke (of te makkelijke) opdracht.

## 2. Draag je code/steentje bij


Stap 1: **fork** het project op GitHub, en probeer het lokaal te compileren met Gradle.

Stap 2: Welke bijdrage ga je leveren aan het project? En vooral, hoe? Herlees `CONTRIBUTING.md`. Welke stappen moet je ondernemen volgens de maintainer van het project? Met de volgende dingen moet je zeker rekening houden:

- Bekijk de openstaande **issues**. Bekijk de afgesloten issues---indien van toepassing. Hoe zijn die opgelost? Is het nuttig om eventueel zelf een nieuwe issue aan te maken?
- Maak jezelf **wegwijs in de code**. Hoe zit alles in elkaar? Heb je een algemeen overzicht van de structuur? Welke libraries komen er bij kijken? Wat zou je waar moeten wijzigen? Probeer dit met zo weinig mogelijk impact op bestaande code te doen, eventueel gebruikmakend van de nodige design patterns.
- Moeten wijzigingen op een **branch** gebeuren?
- Zijn er **unit testen** voorzien? De kans is groot dat dit ook wordt verwacht bij contributies. 
- Zijn er bepaalde **code styles** waar je je aan moet houden? (hoofdletters, naamgevingen, package locaties, ...)
- Denk na over je **commit message**: `fix haha lol` is onvoldoende. Bedenk dat je aan een publiek beschikbaar project meewerkt!

Stap 3: na de push naar je eigen fork open je (via de GitHub UI) een **pull request** dat jouw wijzigingen moet mergen met de bestaande codebase. Hier kan je een issue aan linken en extra uitleg schrijven voor de personen die het project onderhouden. 

Stap 4: wacht op feedback! Misschien wordt je request positief onthaald maar zijn er nog wat wijzigingen nodig voordat het kan worden gemerged met de main repository---dit is perfect normaal en komt vaak voor. Om te slagen voor deze taak is het _NIET_ nodig dat de maintainer je pull request effectief merged: dit kan soms erg lang duren, en soms ligt de lat gewoon te hoog. 

## 3. Stel een verslag op

Schrijf een verslag van **minstens 4 pagina's** (single-column, lettergrootte 12pt) met de volgende inhoud:

- Een korte beschrijving van het project waar je aan werkte;
- Een nauwkeurige beschrijving van je wijzigingen (de inclusie van een schema ter visualisatie of code snippets wordt aangemoedigd);
- Op welke verschillende manieren je je wijzigingen getest hebt en/of hebt laten testen;
- De uitdagingen en moeilijkheden die je had tijdens de realisatie ervan;
- De eventuele feedback die je ontving na de pull request;
- Een conclusie met samenvatting van wat je geleerd hebt en hoe je dit in de toekomst zou aanakken.

Dit mag in het Nederlands of Engels. Indienen in `.PDF` formaat op Toledo.

