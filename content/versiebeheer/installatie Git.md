---
title: 'Installatie Git en Git tools'
weight: 1
draft: false
author: Arne Duyver
---

## Git installeren

Het versiebeheer systeem dat we in dit opleidingsonderdeel zullen gebruiken is [Git](https://git-scm.com/). 'Git' staat voor 'Global Information Tracker' of met andere woorden Git wordt gebruikt om informatie over bestanden te volgen.
Via volgende link kan je alle up-to-date informatie terugvinden over hoe je Git op je besturingssysteem kan istalleren: [Installatie procedure Git](https://git-scm.com/book/nl/v2/Aan-de-slag-Git-installeren)

### Git gui en Git bash
Met de installatie van de versiebeheer software werden gelijktijdig een GUI (Graphical User Interface) app en een CLI https://git-scm.com/, enkel voor Windows  (Command Line Interface) geïstalleerd.

De git-gui app is een beginnervriendelijke omgeving om gemakkellijk met versiebeheer aan de slag te gaan. In de SES-lessen willen we je echter de 'correcte' manier aanleren om als software-ontwikkelaar overweg te kunnen met verschillende handige tools zoals Git. Daarom gaan we de grafische interface van Git niet gebruiken.

We gaan gebruik maken van de Git commands in een CLI om aan versiebeheer te doen. Windows gebruikers kunnen hiervoor best gebruik maken van det Git Bash applicatie die gelijktijdig met git geïnstalleerd werd. Mac OS en Linux gebruikers kunnen gebruik maken van hun eigen terminal applicatie.

## Basis CLI commands die we nodig hebben voor dit vak

### Wat is een CLI
Zoals de naam zelf al verraadt, gebruiken we in een CLI commando's om ons besturingssyteem dingen te laten doen. Zo kan je met een commando een '.txt'-file openen in de Kladblok applicatie in plaats van te dubbelklikken op de file. 

Hieronder vind je een voorbeeld met Git Bash in Windows 10:
<img src="/img/versiebeheer/cli_voorbeeld_notepad.png" alt="drawing" style="max-height: 15em;"/>

Op de afbeelding is bovenaan (File Explorer applicatie) te zien dat in mijn mappenstructuur, de file `voorbeeld.txt` zich bevindt in de directory `C:\\Users\Work\Desktop\ses_git`. Onderaan de afbeelding is te zien dat in Git Bash in dezelfde directory heb geopend `~\Desktop\ses_git`. Door het commando `$ notepad voorbeeld.txt` te gebruiken wordt de file geopend in de Notepad applicatie in Windows. 

De structuur van een commando steeds hetzelfde:
1. je typt de naam van het programma/cli-tool dat je wil gebruiken bv. `notepad`
2. nadat je aangegeven hebt welk programma je wil gebruiken moet je de instructies volgen van die applicatie op na te gaan wat je moet typen om het gewenste resultaat te bekomen. In dit geval moet ik de naam van de file geven die ik wil openen in Notepad: ` voorbeeld.txt`
3. soms kan je extra dingen toevoegen om het programma iets specifiek te laten uitvoeren. Het volgende commando `notepad -p voorbeeld.txt` open de file niet in Notepad, maar gebruikt Notepad om de file te printen (`-p`). De `-p` instructie wordt een flag genoemd.

Aangezien we de Git command line tools gaan gebruiken in deze lessen, zal je nog veel kunnen oefenen op het gebruik van de CLI.

#### Enkele belangrijke en handige commando's
- Om Git Bash te openen in een bepaalde directory kan je in File Explorer rechtermuisklik doen op een folder en de optie `Git Bash Here` aanklikken.
- Om in git bash van directory te veranderen gebruiken we het commando `cd` (change directory) gevolgd door het pad van de gewenste directory. ([relatief of absoluut pad](https://www.geeksforgeeks.org/absolute-relative-pathnames-unix/))
    - relatief pad vb. `$ cd ./subfolder` ga naar de folder met naam 'subfolder' binenin de directory waar je **nu** in bent (`./`)
    -  relatief pad vb. `$ cd ../subfolder` ga naar de folder met naam 'subfolder' binenin de **parent directory** waar je nu in bent (`../`). Dit wil zeggen een folder hoger.
    - absoluut pad vb. `$ cd C:\\Users\Work\Desktop\ses_git` ga naar de folder op **deze specifieke locatie**.
- Om in de CLI te zien welke files of subdirectories zich in deze directory bevinden gebruiken we het commando `ls` (list).