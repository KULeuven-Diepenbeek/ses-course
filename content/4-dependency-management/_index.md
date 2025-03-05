---
title: '4. Dependency Management'
weight: 40
draft: false
author: Arne Duyver
---

## Wat is Dependency Management?

Dependency management is een essentieel onderdeel van softwareontwikkeling. Het verwijst naar het beheren van alle externe libraries, modules, externe broncode ... die een project nodig heeft om correct te functioneren. Deze dependencies (_= afhankelijkheden_) kunnen variëren van kleine hulpprogramma's tot grote frameworks die een groot deel van de functionaliteit van een applicatie leveren.

Er zijn verschillende uitdagingen verbonden aan dependency management:
- **Versieconflicten**: Wanneer verschillende dependencies verschillende versies van dezelfde bibliotheek vereisen, kunnen er conflicten ontstaan. Dit kan leiden tot bugs of zelfs het failen van de build.
- **Transitive dependencies**: Dit zijn dependencies van dependencies. Het kan moeilijk zijn om bij te houden welke bibliotheken indirect worden geïmporteerd en of ze compatibel zijn met de rest van het project.
- **Beveiligingsrisico's**: Het gebruik van externe bibliotheken kan beveiligingsrisico's met zich meebrengen, vooral als deze bibliotheken niet regelmatig worden bijgewerkt.
- **Compatibiliteit**: Het kan een uitdaging zijn om ervoor te zorgen dat alle dependencies compatibel zijn met elkaar en met de gebruikte programmeertaal of runtime.

Tools zoals **Gradle** helpen bij het automatiseren en vereenvoudigen van dependency management. Hier zijn enkele redenen waarom we deze tools gebruiken:
- **Automatisering**: tools zoals Gradle kunnen het proces van het downloaden en beheren van dependencies automatiseren. Dit betekent dat ontwikkelaars zich kunnen concentreren op het schrijven van code in plaats van het handmatig beheren van bibliotheken.
- **Versiebeheer**: tools zoals Gradle bieden vaak geavanceerde mogelijkheden voor versiebeheer, waardoor het eenvoudiger wordt om versieconflicten op te lossen en ervoor te zorgen dat de juiste versies van bibliotheken worden gebruikt.
- **Transitive dependency management**: vaak worden door deze tools transitieve dependencies automatisch beheerd, wat betekent dat het alle benodigde bibliotheken en hun dependencies downloadt en configureert.
- **Configuratiebeheer**: het stelt ontwikkelaars ook vaak in staat eenvoudig verschillende configuraties in te stellen voor verschillende omgevingen (bijvoorbeeld ontwikkel-, test- en productieomgevingen) en/of verschillende architecturen en operating systems.
- **Reproduceerbaarheid**: door een tool zoals Gradle wordt je software gebouwd met steeds dezelfde versies van bibliotheken, onafhankelijk van wat (en welke versies) op de machines van de ontwikkelaars geïnstalleerd werd. Dat maakt het makkelijker om bestaande software opnieuw te bouwen op een willekeurige machine.

## Voorbeeld
Stel je voor dat je een project hebt dat afhankelijk is van een `logging`-library en een `JSON`-parser. De `logging`-library heeft op zijn beurt een specifieke versie van een `utility`-library nodig.

Met een dependency management tool (bv. Gradle) hoef je alleen maar de `logging`-library en de `JSON`-parser te specificeren in je **buildscript**, en de tool zorgt ervoor dat alle benodigde dependencies, inclusief de transitieve dependency (de `utility`-library) worden gedownload en correct worden geconfigureerd.

Door zo'n tools te gebruiken, kunnen we de complexiteit van dependency management dus aanzienlijk verminderen en ervoor zorgen dat onze projecten betrouwbaar en veilig blijven.

## Tools voor dependency management

Enkele bekende tools voor dependency management zijn:

- `Gradle`, `Maven`, `Ant` voor Java
- `(C/Q)Make` (custom config) voor C/C++
- `npm`, `yarn`, `grunt`, `gulp`, (in JS) ... voor JS (nodejs)
- `pip` voor Python
- `nuget` (custom config, XML) voor .NET

_**In de volgende delen gaan we dieper in op deze concepten en er ook praktisch mee aan de slag.**_ We bestuderen meer bepaald Gradle (Java), CMake (C) en pip (Python).