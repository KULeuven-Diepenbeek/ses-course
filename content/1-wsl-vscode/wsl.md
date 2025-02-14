---
title: 'Windows Subsystem for Linux (WSL)'
weight: 1
draft: false
author: Arne Duyver
---

## Besturingssystemen: introductie

Voor we met de echte materie kunnen starten moeten we eerst wat meer kennis vergaren over hoe computers juist werken. Je zal dit nog met meer diepgang bespreken in het vak [*'Besturingssystemen en C'*](https://studiegidswww.uhasselt.be/opleidingsonderdeel.aspx?i=4082), maar we geven toch al een korte intro zodat je weet waar we mee bezig zijn.

### Wat is een besturingssysteem? (Besturingssysteem = Operating System = OS)

Een besturingssysteem (OS) is een essentieel onderdeel van de computerarchitectuur dat fungeert als een brug tussen de hardware van een computer en de gebruiker. Het beheert de hardwarebronnen van de computer en biedt een omgeving waarin applicaties kunnen draaien. Zonder een besturingssysteem zou een computer niet functioneel zijn voor de meeste gebruikers.

Het besturingssysteem voert verschillende cruciale taken uit:
- hardwarecomponenten (CPU, geheugen ...) besturen;
- een platform voorzien waar software/applicaties op kunnen draaien;
- een gebruikersinterface voorzien;
- beheren van processen;
- input en output apparaten beheren;
- applicaties beheren;
- veiligheid beheren.

Dit betekent dat het OS bijvoorbeeld bepaalt welke processen toegang hebben tot welke bronnen en wanneer. Dit beheer is essentieel om ervoor te zorgen dat de computer efficiënt en effectief werkt. We gaan vooral even dieper in hoe het OS applicaties beheert en kan starten/stoppen.

Daarnaast biedt het besturingssysteem een gebruikersinterface, die kan variëren van een **command-line interface (CLI)** tot een **grafische gebruikersinterface (GUI)**. Deze interface stelt gebruikers in staat om met de computer te interageren. Je bent waarschijnlijk al zeer bekend met de GUI. Tijdens de lessen SES gaan we ons echter focussen op het interageren met de computer via de CLI.

Nog ander belangrijk aspect van een besturingssysteem is het **beheer van bestanden**. Het OS organiseert en bewaart bestanden op een manier die gemakkelijk toegankelijk en veilig is. Dit omvat het beheren van lees- en schrijfrechten, het organiseren van bestanden in mappen en het zorgen voor gegevensintegriteit. Dit komt later nog aan bod wanneer we het hebben over het beheer van applicaties en het commando `chmod`.

Zoals hierboven al vermeld, biedt het besturingssysteem een platform voor het uitvoeren van applicaties. Het zorgt ervoor dat applicaties de benodigde bronnen krijgen en dat ze geïsoleerd zijn van elkaar om conflicten en beveiligingsproblemen te voorkomen. Dit maakt het mogelijk om meerdere applicaties tegelijkertijd te draaien zonder dat ze elkaar storen.

In samenvatting, een besturingssysteem is een complex maar essentieel onderdeel dat de functionaliteit en bruikbaarheid van computers mogelijk maakt.

**Voorbeelden van besturingssystemen:**
- Windows
- Linux
- Mac OS
- FreeRTOS

Van de voorbeelden hierboven kunnen Linux en Mac OS nog gegroepeerd worden tot de zogenaamde UNIX-besturingssystemen omdat ze beide gebaseerd zijn op de principes en architectuur van het oorspronkelijke UNIX-systeem. De CLI werkt bij zowel Linux als Mac OS gelijkaardig, en dit is grotendeels te danken aan hun gemeenschappelijke UNIX-basis. 

{{% notice info %}}
Er is een groot [verschil tussen UNIX-gebaseerde besturingssystemen (zoals Linux en Mac OS) en Windows](https://clickup.com/nl/blog/218063/mac-vs-linux-vs-windows-voor-programmeren), daarom dat we in deze cursus de keuze maken om met Linux te werken. (Wel niet rechtstreeks maar via het Windows Subsystem for Linux, waarover hieronder meer.)
{{% /notice %}}


### De Command-Line Interface (CLI)
De CLI is een tekstgebaseerde interface waarmee gebruikers commando's kunnen invoeren om taken uit te voeren op een computer. In tegenstelling tot GUI's, waarbij gebruikers met muis en vensters werken, vereist de CLI dat gebruikers tekstcommando's typen. 
Hoewel GUI's tegenwoordig veel gebruiksvriendelijker zijn voor de meeste gebruikers, blijft de CLI een krachtig hulpmiddel voor specifieke taken zoals taken die efficiëntie, automatisering en flexibiliteit vereisen.

{{% notice info %}}
Daarenboven bestonden in de beginjaren van de computertechnologie nog geen GUI's. Computers werden voornamelijk bediend via de CLI. Dit was de standaard manier om met computers te communiceren, omdat de hardware en software van die tijd niet krachtig genoeg waren om grafische interfaces te ondersteunen. Dus zeker in low-end devices blijft de CLI een essentiële tool.
{{% /notice %}}

Volgende termen houden verband met de CLI en worden soms (incorrect) door elkaar gebruikt:
- **Terminal**: Een terminal is een apparaat of softwaretoepassing die communicatie tussen de gebruiker en het besturingssysteem mogelijk maakt via de CLI. In de context van moderne computers is een terminal meestal een softwaretoepassing die een CLI-omgeving biedt.
- **Terminal Emulator**: Een terminal emulator is een softwaretoepassing die de functionaliteit van een traditionele hardwareterminal nabootst. Het stelt gebruikers in staat om een terminalsessie te openen binnen een grafische omgeving en zorgt voor een omgeving waarin een **shell** kan draaien. Voorbeelden van terminal emulators zijn GNOME Terminal, Konsole en Terminator. 
- **Shell**: Een shell is een programma dat de CLI biedt en de interpretatie van de ingevoerde commando's verzorgt. Het fungeert als een interface tussen de gebruiker en het besturingssysteem. De shell ontvangt commando's van de gebruiker, voert ze uit en geeft de output terug.
    - **Bash**: Bash (Bourne Again Shell) is een veelgebruikte UNIX-shell die vaak standaard wordt geleverd met Linux-distributies. Bash biedt krachtige scriptingmogelijkheden en een breed scala aan ingebouwde commando's, waardoor het een populaire keuze is voor zowel systeembeheer als ontwikkeling.
    - **Zsh**: Zsh (Z Shell) is een andere UNIX-shell die bekend staat om zijn uitgebreide functies en configuratiemogelijkheden. Het biedt verbeterde autocompletion, betere scriptingmogelijkheden en een meer aanpasbare omgeving in vergelijking met Bash. Veel gebruikers kiezen voor Zsh vanwege de extra functionaliteit en flexibiliteit.
    - **PowerShell**: PowerShell is een shell ontwikkeld door Microsoft voor Windows. Het combineert de traditionele CLI met een krachtige scriptingtaal gebaseerd op .NET. PowerShell is ontworpen voor systeembeheer en automatisering, en biedt uitgebreide mogelijkheden voor het beheren van Windows-systemen.

- De verschillende shells gebruiken vaak ook verschillende commando's voor een gelijkaardige functionaliteit. Zo kan je met het commando `pwd` de inhoud van een folder/directory weergeven in *Bash*, terwijl in vroegere versies van *PowerShell* het commando `dir` voor gebruikt werd.
- Verschillende terminal emulators hebben bijvoorbeeld verschillende manieren om tekst te copy en pasten in de terminal.

### Software programma's / applicaties

Enkele belangrijke termen:
- **Hardware**: Hardware is de fysieke machine.
- **Software**: Software is een programma dat op hardware draait.
- **Programma**: Een programma is een reeks instructies (in alle vormen en maten). Een *software programma* is dus een reeks computer instructies.
- **Proces**: Een proces is een programma dat in het geheugen is geladen van het computersysteem en wordt beheert door het besturingssysteem.
- **Applicatie**: Een programma dat is ontworpen voor de eindgebruiker voor een specifiek doel. Sommige programma's zijn algemeen van aard, zoals een besturingssysteem, anderen hebben een specifiek doel zoals tekstverwerking (Word) of draaien niet voor de eindgebruiker maar op de achtergrond (zoals een applicatie dat de hardware monitort).

In het dagelijkse leven is een gebruiker dus vooral aan het interageren met verschillende applicaties op de computer. Je kan applicaties installeren, updaten of deïnstalleren. 
Als ingenieur of software ontwikkelaar zal je zelf *programma's* schrijven of zelfs volledige *applicaties* en kan het soms nodig zijn om dieper in te gaan in de processen die bezig zijn op je computer.

#### Hoe een applicatie installeren?
Ten eerste zal je voor de meeste applicaties administrator rechten nodig hebben om een applicatie te installeren. Via een GUI krijg je dan vaak een pop-up, met behulp van de CLI kan je in Windows bijvoorbeeld simpelweg PowerShell met administrator rechten starten. In Linux kan je een commando uitvoeren als "super user" met het commando `sudo` wat staat voor "super user do".

**1. Executables downloaden**

De meeste onder jullie hebben ooit al wel een applicatie gedownload en geïnstalleerd op je computer. Hiervoor heb je waarschijnlijk een `.exe` file gedownload van het internet op Windows. Dubbelklikken op die file start een process dat alle nodige bestanden installeert met het belangrijkste bestand dat op zichzelf weer een `.exe` file is. EXE staat dan ook voor "executable" of "iets dat uitgevoerd kan worden". Door op die laatste file te klikken start je dan meestal je applicatie. Menu iconen en Desktop iconen zijn dan meestal gewoon een link naar die specifieke `.exe` file die op een speciale plaats op je computer is opgeslagen.

**2. Een package manager gebruiken**

In een CLI omgeving gaan we echter nergens op kunnen klikken. We gaan hier dan vaak gebruik maken van een **package manager** die in een online **repository** op zoek gaat naar de gewilde applicatie en hier dan automatisch de correcte bestanden van gaat downloaden op de juiste plaats. Een package manager is een softwaretool die helpt bij het installeren, updaten, configureren en verwijderen van softwarepakketten op een computer. Voordelen van een package manager zijn:
- Efficiëntie: Bespaart tijd en moeite bij het installeren en beheren van software.
- Betrouwbaarheid: Zorgt ervoor dat alle benodigde afhankelijkheden correct worden geïnstalleerd. 
- Beveiliging: Helpt bij het up-to-date houden van software, wat belangrijk is voor beveiligingspatches en bugfixes.
- Gemak: Maakt het eenvoudig om software te vinden, installeren en verwijderen met eenvoudige commando's. Je kan namelijk met één commando alle applicaties op je computer updaten naar de nieuwste versie.

Populaire package managers zijn:
- Apt voor Ubuntu (Linux)
- Homebrew voor Mac OS
- Chocolatey voor Windows

**3. Een fully contained package gebruiken**

Er bestaan ook volledig voorverpakte software bestanden. In dat geval zit alles wat nodig is om de applicatie te runnen in één bestand. Dit worden ook wel mobiele applicaties genoemd omdat je ze makkelijk op een usb stick kan zetten en op verschillende computers kan gebruiken zonder dat een volledige installatie nodig is. Een voorbeeld hiervan is "Balena Etcher". Snap packages en flatpacks zijn hiervan voorbeelden voor Linux.

**4. Een applicatie bouwen vanuit de bronbestanden**

Je kan ook de bronbestanden/broncode van sommige software downloaden en dan de applicatie zelf bouwen. Hiervoor heb je echter de juiste build tools voor nodig, de juiste dependecies, moet je de bestanden op de juiste plaats zetten ... Dit kost veel moeite voor de gebruiker en leidt vaak tot errors waardoor we deze manier liefst niet gebruiken. Soms is er echter geen andere mogelijkheid.

#### Waar worden de nodige bestanden voor de applicatie bewaard?
Een applicatie bestaat vaak uit meerdere onderdelen/bestanden die tijdens het installeren op je computer gezet worden (behalve in fully contained packages, want daar zitten juist alle nodige bestanden in één bestand), maar waar worden deze dan gezet zodat je deze later kan gebruiken?

In **Windows** heb je waarschijnlijk al eens gekeken in `C:\\Program Files`, wat meestal de default locatie is voor applicaties. In deze directory wordt er per applicatie meestal een map aangemaakt waar de nodige bestanden inkomen.<br>
In **Linux** worden de meeste executables geplaatst in `/usr/bin` of `/usr/local/bin`.<br>
In **Mac OS** worden applicaties meestal geïnstalleerd in de `Applications`-map, die zich in de root van het bestandssysteem bevindt.

#### Hoe een applicatie starten?
Je kan natuurlijk gewoon dubbelklikken op het icoontje of de executable file zoals je waarschijnlijk altijd al gedaan hebt in een GUI. Dit kan wel weer niet in een CLI omgeving. Hiervoor gebruiken we de naam van de executable file. Als ik in mijn huidige directory een executable heb staan met de naam `myprogram`, dan kan ik deze applicatie/dit commando simpelweg uitvoeren door de naam in te typen in de CLI. Ik kan echter ook applicaties starten met hun commandonaam die niet in deze specifieke folder zitten. Het OS heeft namelijk een lijst met alle folders waar die moet gaan zoeken naar commando's. Die lijst staat opgeslagen in de PATH-variabele. 

## Virtual Machines en WSL
Een virtual machine (VM) is een **software-emulatie** van een fysieke computer die een besturingssysteem en applicaties kan draaien alsof het een echte machine is. VM's maken gebruik van hypervisors om de hardwarebronnen van een fysieke hostmachine te verdelen en te isoleren, waardoor meerdere virtuele machines tegelijkertijd op dezelfde fysieke hardware kunnen draaien.

<figure>
    <img src="/img/Physical-Servers-vs-VMs.png" alt="drawing" style="max-height: 23em;"/>
    <figcaption style="text-align: center; font-weight: bold;">Normal computer (left) VS VM's on host (right)</figcaption>
</figure>

Het principe van virtual machines bestaat al lange tijd en kan soms ingewikkeld zijn om op te zetten, vooral op Windows. Daarom heeft Windows onlangs een ingebouwde oplossing geïntroduceerd genaamd het **Windows Subsystem voor Linux (WSL)**. WSL biedt een Linux-omgeving binnen Windows, waarbij WSL 2 gebruikmaakt van een lichte virtuele machine met een volledige Linux-kernel, wat zorgt voor betere prestaties en integratie dan traditionele VM's.

Wij gaan de Linux distributie **Ubuntu versie 24.04** gebruiken in deze cursus. Een **Linux distributie** is een complete verzameling van software die een Linux-kernel bevat, samen met een reeks tools, bibliotheken en applicaties die nodig zijn om een volledig functioneel besturingssysteem te vormen. Elke distributie is samengesteld en geoptimaliseerd voor verschillende doeleinden en gebruikersgroepen. Linux distributies verschillen van elkaar op gebied van verschillende aspecten, waaronder: package manager, desktop omgeving, vooraf geïnstalleerde software en tools ... <br>
[Populaire Linux distributies](https://upload.wikimedia.org/wikipedia/commons/1/1b/Linux_Distribution_Timeline.svg) zijn: Ubuntu, Debian, Pop OS, Fedora, Debian, Linux Mint, Arch Linux ...

Wij kiezen voor Ubuntu voor de gebruiksvriendelijkheid, community en ondersteuning, softwarebeschikbaarheid, regelmatige update.

### [WSL installeren op Windows 11](https://learn.microsoft.com/en-us/windows/wsl/install)
Voor Windows 10 version 2004 en hoger is het commando `wsl` normaal al geïnstalleerd. Je kan dit testen door Terminal of PowerShell te openen en het commando `wsl --version` in te geven. Krijg je een antwoord terug zonder error dan werk WSL. Je kan nu het volgende commando gebruiken om Ubuntu 24.04 te installeren: `wsl --install -d Ubuntu-24.04`. Na de installatie wordt je meteen in de VM gegooid en moet je een `username` en `password` meegeven. Zorg ervoor dat je deze onthoudt! Je kan via WSL meerdere WMs tegelijkertijd installeren op je computer, daarom zetten we Ubuntu even als de default via het commando `wsl --set-default Ubuntu-24.04`. Wanneer je nu de Windows applicatie WSL opent zal je rechtstreeks in de CLI omgeving van Ubuntu terecht komen. Hier gaan we voorlopig het grootste deel van onze tijd doorbrengen.

<details open>
<summary><i>Klik hier om voorbeeld output te zien/verbergen</i>🔽</summary>
<p>

```bash
PS C:\Users\u0158802> wsl --install -d Ubuntu-24.04
Installing: Ubuntu 24.04 LTS
Failed to install Ubuntu-24.04 from the Microsoft Store: The Windows Subsystem for Linux instance has terminated.
Attempting web download...
Downloading: Ubuntu 24.04 LTS
Installing: Ubuntu 24.04 LTS
Ubuntu 24.04 LTS has been installed.
Launching Ubuntu 24.04 LTS...
Installing, this may take a few minutes...
Please create a default UNIX user account. The username does not need to match your Windows username.
For more information visit: https://aka.ms/wslusers
Enter new UNIX username: arne
New password:
Retype new password:
passwd: password updated successfully
Installation successful!
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.

Welcome to Ubuntu 24.04 LTS (GNU/Linux 5.15.167.4-microsoft-standard-WSL2 x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/pro

 System information as of Mon Feb 10 17:20:37 CET 2025

  System load:  0.62                Processes:             53
  Usage of /:   0.1% of 1006.85GB   Users logged in:       0
  Memory usage: 12%                 IPv4 address for eth0: 172.29.161.86
  Swap usage:   0%


This message is shown once a day. To disable it please create the
/home/arne/.hushlogin file.
arne@LT3210121:~$
```

</p>
</details>

### WSL starten, stoppen
Je kan WSL ook starten vanuit een Windows CLI tool zoals Terminal of Powershell met het commando `wsl`. Om dan uit je WSL te raken en terug in de host versie van de CLI gebruik je het commando `exit`.

*Overzicht commando's:*
```powershell
> wsl --version
> wsl --install -d [distributie naam]
> wsl --set-default [distributie naam]
# binnenin WSL gebruik je `exit` om terug naar de host te keren
$ exit
```

### Connectie tussen host en VM  
Eén van de handige functies van het WSL is de naadloze integratie tussen de WSL-omgeving en de Windows-host. Dit betekent dat je eenvoudig bestanden kunt bekijken en bewerken die zich op je Windows-bestandssysteem bevinden vanuit de WSL-omgeving, en omgekeerd.

#### filesystem Windows vs Linux 
Er is nog een vrij groot verschil tussen het bestandssysteem van Windows en Linux en kan invloed hebben op hoe je met bestanden en mappen werkt. 
- De root/hoofd directory in Windows is meestal een specifieke schijf, zoals `C:\`. Elke schijf of partitie heeft zijn eigen root, bijvoorbeeld `D:\` voor een tweede schijf.In Linux is er één enkele root directory aangeduid met `/`. 
- Windows gebruikt backslashes '\' om directories te scheiden, bijvoorbeeld `C:\Users\Gebruiker\Documents`. Linux gebruikt forward slashes '/' voor hetzelfde doel, bijvoorbeeld `/home/gebruiker/documents`.
- In Windows zijn bestandsnamen niet hoofdlettergevoelig. `Document.txt` en `document.txt` worden als hetzelfde bestand beschouwd. Linux is wel hoofdlettergevoelig.
- In Windows worden schijven en partities aangeduid met letters zoals `C:\`, `D:\`. In Linux worden schijven en partities gekoppeld aan directories binnen het filesystem, zoals `/mnt/c` voor de C-schijf.

{{% notice info %}}
Een **directory** is een locatie op een filesystem waar bestanden (= **files**) en andere directories (**subdirectories**) kunnen worden opgeslagen. Het fungeert als een container die helpt bij het organiseren en structureren van bestanden op een computer. 

Hoewel er kleine verschillen kunnen zijn tussen de volgende termen, in de context waarin deze termen worden gebruikt, verwijzen ze allemaal naar hetzelfde concept: een locatie op een bestandssysteem waar bestanden en andere mappen kunnen worden georganiseerd: directory, folder, map. 
{{% /notice %}}

#### files van VM op host en files van de host op de VM
Binnenin File Explorer (Verkenner) in Windows kan je de files van de WSL terugvinden op de locatie `\\wsl$\Ubuntu-24.04` zoals op onderstaande afbeelding te zien is.

<figure>
    <img src="/img/wsl-ubuntu-in-explorer.png" alt="drawing" style="max-height: 23em;"/>
    <figcaption style="text-align: center; font-weight: bold;">WSL bestandslocatie in File Explorer</figcaption>
</figure>

In WSL vind je de bestanden van de Windows host in de directory `/mnt/c`.

## De CLI gebruiken: essentiële UNIX commands
Dan kunnen we nu eindelijk aan het praktische deel beginnen. Dingen doen binnen in je WSL Ubuntu met behulp can CLI-commando's. We gaan een reeks van veelgebruikte commando's overlopen en bekijken. We gaan echter niet alle commando's zien omdat er gewoon teveel zijn en ook niet voor elk commando elke optie die mogelijk is. Maar met alles wat we gaan overlopen zou je het grootste deel van je taken als een ingenieur in de CLI moeten kunnen uitvoeren.

We gebruiken een bash shell. 

**pwd** - 'print working directory': Met dit commando output je je huidige directory in de terminal. 
```bash
$ pwd 
# Voorbeeld
arne@LT3210121:~$ pwd
/home/arne
```
Je huidige directory is ook steeds terug te vinden tussen de `:` en de `$` in de terminal. In het voorbeeld hierboven zie je dat de directory `~` is aangegeven. Dit is een synoniem voor de `/home/$USER` directory. (Voor de `@` staat de ingelogde usernaam en tussen de `@` en de `:` staat de naam van het apparaat)

**echo**: Met dit commando toon je de opgegeven tekst in de terminal.
```bash
$ echo [tekst] 
# Voorbeeld
arne@LT3210121:~$ echo "Hello world!"
Hello world!
```

**clear**: Met dit commando maak je het terminalvenster leeg.
```bash
$ clear
```

**mkdir** - 'make directory': Met dit commando maak je een nieuwe directory aan.
```bash
$ mkdir [directory]
# Voorbeeld
arne@LT3210121:~$ mkdir test 
```

**cd** - 'change directory': Met dit commando verander je naar de opgegeven directory.
```bash
$ cd [directory]
# Voorbeeld
arne@LT3210121:~$ cd test  
arne@LT3210121:~/test$
```
Je kan op twee manieren een directory opgeven, namelijk met een relatief pad of een absoluut pad:
- Een **absoluut pad** geeft de volledige locatie van een directory of bestand vanaf de root directory van het bestandssysteem. Het begint altijd met een `/` en specificeert de exacte locatie, ongeacht de huidige werkdirectory. (bijvoorbeeld: `/home/arne/test`)
- Een **relatief pad** geeft de locatie van een directory of bestand ten opzichte van de huidige werkdirectory. Het begint niet met een /, maar met de naam van de directory of bestand. Relatieve paden zijn handig voor het navigeren binnen de huidige directorystructuur zonder de volledige padnaam te hoeven opgeven. (bijvoorbeeld: `arne/test` als je je in de `/home` directory bevindt)<br>
Bovendien zijn er nog twee speciale symbolen die je in padnamen kan gebruiken `.` en `..`:
- `.` verwijst naar de huidige directory. (bijvoorbeeld: `./arne/test` als je je in de `/home` directory bevindt)
- `..` verwijst naar de bovenliggende directory (= **parent directory**). (bijvoorbeeld: `./arne/../arne/test` als je je in de `/home` directory bevindt, waarbij je met `..` terug naar de `/home` verwijst, de parent directory van `/home/arne`)

Voor de meeste commando's kan je de **`tab`-toets** gebruiken om automatisch je text of commando's te laten vervolledigen. Bijvoorbeeld voor directory of file namen ... Als er meerdere mogelijkheden voor autocomplete zijn moet je tweemaal op de `tab`-toets drukken en dan krijg je een lijst te zien met alle mogelijkheden.

**ls** - 'list files': Met dit commando lijst je de bestanden en directories in de huidige directory op.
```bash
$ ls
# Voorbeeld
arne@LT3210121:~$ ls
test
```
Je kan meestal je commando meer specificeren met behulp van **options** en **flags** om extra functionaliteit toe te voegen of de uitvoer aan te passen. Bijvoorbeeld, het `ls`-commando in Linux wordt gebruikt om de inhoud van een directory weer te geven. Door opties en flags toe te voegen, kun je de uitvoer van ls aanpassen aan je behoeften. Zo geeft `ls -l` een gedetailleerde lijst weer met extra informatie zoals *bestandsrechten, eigenaar en grootte*, terwijl `ls -a` ook *verborgen bestanden* toont die normaal gesproken niet zichtbaar zijn. Je kunt ook meerdere opties combineren, zoals `ls -la`, om zowel een gedetailleerde lijst als verborgen bestanden weer te geven. Deze flexibiliteit maakt het mogelijk om commando's nauwkeurig af te stemmen op specifieke taken en vereisten. Je hebt verder ook de optie om aan het `ls` commando een specifiek directory mee te geven waardoor het de inhoud van de meegegeven directory toont.

<details open>
<summary><i>Klik hier om voorbeeld output te zien/verbergen</i>🔽</summary>
<p>

```bash
# Voorbeeld
arne@LT3210121:~$ ls -l
total 4
drwxr-xr-x 2 arne arne 4096 Feb 10 23:01 test
# Voorbeeld
arne@LT3210121:~$ ls -a
.   .aws    .bash_history  .bashrc  .docker      .profile                  .sudo_as_admin_successful
..  .azure  .bash_logout   .cache   .motd_shown  .skip-cloud-init-warning  test
# Voorbeeld
arne@LT3210121:~$ ls -la
total 36
drwxr-x--- 5 arne arne 4096 Feb 11 14:26 .
drwxr-xr-x 4 root root 4096 Feb 10 17:21 ..
lrwxrwxrwx 1 arne arne   26 Feb 11 14:26 .aws -> /mnt/c/Users/u0158802/.aws
lrwxrwxrwx 1 arne arne   28 Feb 11 14:26 .azure -> /mnt/c/Users/u0158802/.azure
-rw------- 1 arne arne    9 Feb 10 17:27 .bash_history
-rw-r--r-- 1 arne arne  220 Feb 10 17:21 .bash_logout
-rw-r--r-- 1 arne arne 3771 Feb 10 17:21 .bashrc
drwx------ 2 arne arne 4096 Feb 10 17:21 .cache
drwxr-xr-x 5 arne arne 4096 Feb 11 14:26 .docker
-rw-r--r-- 1 arne arne    0 Feb 11 14:26 .motd_shown
-rw-r--r-- 1 arne arne  807 Feb 10 17:21 .profile
-rw-r--r-- 1 arne arne    0 Feb 11 14:26 .skip-cloud-init-warning
-rw-r--r-- 1 arne arne    0 Feb 10 22:20 .sudo_as_admin_successful
drwxr-xr-x 2 arne arne 4096 Feb 10 23:01 test
# Voorbeeld
arne@LT3210121:~$ ls /mnt
c  wsl  wslg
```

</p>
</details>

In de output bovenaan van het commando `ls -l` krijg je meer info te zien over het bestandstype (`d` = directory, `-` = file ...) en de eigenaarsrechten (`r` = can read, `w` = can write, `x` = can execute). De output bevat verschillende kolommen met belangrijke informatie.
- De eerste kolom toont de bestandsrechten, die aangeven wie lees-, schrijf- en uitvoerrechten heeft voor het bestand (bijvoorbeeld `-rw-r--r--`). De rechten komen in 3 groeperingen van 3 waarvan de meest linkse de rechten van de eigenaar zijn, de middelste groepering de rechten van alle users in dezelfde groep als de eigenaar en de meest rechtse groepering de rechten van alle gebruikers op het systeem. 
- De tweede kolom geeft het aantal harde links naar het bestand weer.
- De derde en vierde kolommen tonen respectievelijk **de eigenaar** en **de groep** waartoe het bestand behoort.
- De vijfde kolom geeft de bestandsgrootte in bytes weer. De zesde kolom toont de datum en tijd van de laatste wijziging.
- De laatste kolom geeft de naam van het bestand of de directory weer. 

**man** - 'manual': Dit commando is een krachtig hulpmiddel in Linux om gedetailleerde informatie over andere commando's en hun opties te vinden.<br>
```bash
$ man [commando]
# Voorbeeld
arne@LT3210121:~$ man ls
LS(1)                                               User Commands                                               LS(1)

NAME
       ls - list directory contents

SYNOPSIS
       ls [OPTION]... [FILE]...

DESCRIPTION
       List  information  about the FILEs (the current directory by default).  Sort entries alphabetically if none of
       -cftuvSUX nor --sort is specified.

       Mandatory arguments to long options are mandatory for short options too.

       -a, --all
              do not ignore entries starting with .

       -A, --almost-all
              do not list implied . and ..

       --author
              with -l, print the author of each file

       -b, --escape
              print C-style escapes for nongraphic characters

       --block-size=SIZE
              with -l, scale sizes by SIZE when printing them; e.g., '--block-size=M'; see SIZE format below

 Manual page ls(1) line 1 (press h for help or q to quit)
```
Door man te gebruiken gevolgd door de naam van een commando, zoals `man ls`, krijg je toegang tot de handleidingspagina voor dat commando **_(gebruik de pijltjes toetsen om te navigeren en 'q' om dit menu te sluiten)_**. Deze pagina bevat uitgebreide informatie over wat het commando doet, welke opties en flags beschikbaar zijn, en hoe je ze kunt gebruiken. Bijvoorbeeld, door `man ls` in te voeren, zie je een lijst van alle beschikbare opties voor het `ls`-commando, zoals `-l` voor een gedetailleerde lijstweergave en `-a` om verborgen bestanden te tonen. Dit maakt het `man`-commando een onmisbaar hulpmiddel voor zowel beginners als gevorderde gebruikers om de volledige functionaliteit van Linux-commando's te ontdekken en effectief te benutten.

**touch**: Met dit commando maak je een nieuw, leeg bestand aan of update je de timestamp van een bestaand bestand.
```bash
$ touch [bestandsnaam]
# Voorbeeld
arne@LT3210121:~$ touch test.txt
arne@LT3210121:~$ ls
test  test.txt
```

**rm** - 'rm [bestand]': Met dit commando verwijder je het opgegeven bestand. Gebruik de -R (recursive) flag to delete entire directories.
```bash
$ rm [bestandsnaam]
$ rm -R [directorynaam]
# Voorbeeld
arne@LT3210121:~$ ls
test  test.txt
arne@LT3210121:~$ rm test.txt
arne@LT3210121:~$ ls
test
arne@LT3210121:~$ rm -R test
arne@LT3210121:~$ ls
arne@LT3210121:~$
```

**nano**: Met dit commando open je het opgegeven bestand in de nano-teksteditor. Aangezien nano een CLI teksteditor is, kan je niet gewoon klikken om de cursor te verplaatsen, op te slaan of te exitten. _Gebruik hiervoor de pijltjestoetsen, Ctrl+o (= save, write out), Ctrl+x (= exit)._ Indien het bestand bestaat kan je het aanpassen en indien het bestand niet bestaat wordt het on save aangemaakt.
```bash
$ nano [bestandsnaam]
# Voorbeeld
arne@LT3210121:~$ nano test.txt
...
```

**vim**: Met dit commando open je het opgegeven bestand in de vim-teksteditor. Dit is een zeer speciale editor. Het belangrijkste dat je moet weten is dat je kan exiten met `:q!`+enter. Soms moet je eerst tweemaal op `esc` drukken. Meer info vind je [hier](https://www.freecodecamp.org/news/vim-beginners-guide/).
```bash
$ vim [bestandsnaam]
# Voorbeeld
arne@LT3210121:~$ vim test.txt
...
```

**cat** - 'cat [bestand]': Met dit commando toon je de inhoud van het opgegeven bestand in de terminal.
```bash
$ cat [bestandsnaam]
# Voorbeeld
arne@LT3210121:~$ cat test.txt
Dit is een test file.
```
**copy en paste**: Dit zal voor verschillende terminal emulators anders zijn. In Windows terminal kan je simpelweg `Ctrl+c` en `Ctrl+v` gebruiken, in WSL kan je `Ctrl+c` gebruiken voor copy en `rechtermuisklik` voor paste en een andere veel gebruikte toetsencombinatie is `Ctrl+Shift+c` en `Ctrl+Shift+v`.

_**[Maak nu de oefeningen van oefenreeks 1 van de volgende pagina](/1-wsl-vscode/wsl_exercises.md/#oefeningenreeks-1)**_

**chown** - 'change ownership': Met dit commando verander je de eigenaar van het opgegeven bestand.
```bash
$ sudo chown [gebruiker]:[groep] [bestandsnaam]
# Voorbeeld
arne@LT3210121:~$ chown root:root test.txt
chown: changing ownership of 'test.txt': Operation not permitted
arne@LT3210121:~$ sudo chown root:root test.txt
[sudo] password for arne:
arne@LT3210121:~$ ls -l
total 8
drwxr-xr-x 2 arne arne 4096 Feb 11 14:55 test
-rw-r--r-- 1 root root   22 Feb 11 14:52 test.txt
```
{{% notice important %}}
**sudo** - 'sudo [commando]': Met dit commando voer je een ander commando uit met verhoogde (superuser) rechten.
{{% /notice %}}

**export**: Met dit commando kun je omgevingsvariabelen instellen die beschikbaar zijn voor de huidige shell-sessie en alle sub-processen die vanuit deze shell worden gestart.
```bash
$ export [VARIABELENAAM]=[waarde]
# Voorbeeld
arne@LT3210121:~$ export TEST=testwaarde
arne@LT3210121:~$ echo $TEST
testwaarde
# Er zijn ook al voorgedefinieerde variabelen zoals de huidige user ($USER)
arne@LT3210121:~$ echo $USER
arne
```

**chmod** - 'change mode': Met dit commando verander je de bestandsrechten van het opgegeven bestand. (`+r`,`+w`,`+x`,`-r`,`-w`,`-x`)
```bash
$ chmod [rechten] [bestand]
# Voorbeeld
arne@LT3210121:~$ ls -l
total 8
drwxr-xr-x 2 arne arne 4096 Feb 11 14:55 test
-r--r--r-- 1 arne arne   22 Feb 11 14:52 test.txt

arne@LT3210121:~$ chmod -w test.txt
arne@LT3210121:~$ ls -l
total 8
drwxr-xr-x 2 arne arne 4096 Feb 11 14:55 test
-r--r--r-- 1 arne arne   22 Feb 11 14:52 test.txt

arne@LT3210121:~$ chmod +x test.txt
arne@LT3210121:~$ ls -l
total 8
drwxr-xr-x 2 arne arne 4096 Feb 11 14:55 test
-r-xr-xr-x 1 arne arne   22 Feb 11 14:52 test.txt
```

**mv** - 'move': Met dit commando verplaats of hernoem je een bestand of directory.
```bash
$ mv [bron] [doel]
# Voorbeeld
arne@LT3210121:~$ mv ./test.txt ./test/test.txt
arne@LT3210121:~$ ls -l ./test
total 4
-rw-r--r-- 1 arne arne 22 Feb 11 14:52 test.txt
```

**cp** - 'cp [bron] [doel]': Met dit commando kopieer je een bestand of directory naar een nieuwe locatie.
```bash
$ mv [bron] [doel]
# Voorbeeld
arne@LT3210121:~$ cp ./test/test.txt ./test.txt
arne@LT3210121:~$ ls -l ./test
total 4
-rw-r--r-- 1 arne arne 22 Feb 11 14:52 test.txt

arne@LT3210121:~$ ls -l
total 8
drwxr-xr-x 2 arne arne 4096 Feb 11 16:27 test
-rw-r--r-- 1 arne arne   22 Feb 11 16:29 test.txt
```

**sudo apt update** - APT staat voor Advanced Packaging Tool: Met dit commando vernieuw je de lijst van beschikbare applicatie packages en hun versies, maar installeert of verwijdert geen packages.<br>
**sudo apt upgrade**: Met dit commando installeer je de nieuwste versies van alle geïnstalleerde applicatie packages die kunnen worden bijgewerkt, zonder nieuwe packages te verwijderen.<br>
**apt install**: Met dit commando installeer je het opgegeven pakket op een Debian-gebaseerd systeem.<br>
```bash
$ sudo apt install [packagenaam]
# Voorbeeld
arne@LT3210121:~$ sudo apt install curl
[sudo] password for arne:
Reading package lists... Done
Building dependency tree... Done
Reading state information... Done
curl is already the newest version (8.5.0-2ubuntu10.6).
curl set to manually installed.
The following package was automatically installed and is no longer required:
  libllvm17t64
Use 'sudo apt autoremove' to remove it.
0 upgraded, 0 newly installed, 0 to remove and 2 not upgraded.
```
**apt search** - 'apt search [pakket]': Met dit commando zoek je naar een pakket in de pakketbronnen.
```bash
$ sudo apt search [zoekterm]
# Voorbeeld
arne@LT3210121:~$ sudo apt search curl
Sorting... Done
Full Text Search... Done
ario/noble 1.6-1.2build4 amd64
  GTK+ client for the Music Player Daemon (MPD)

ario-common/noble 1.6-1.2build4 all
  GTK+ client for the Music Player Daemon (MPD) (Common files)

cht.sh/noble 0.0~git20220418.571377f-2 all
  Cht is the only cheat sheet you need

cl-curry-compose-reader-macros/noble 20171227-1.1 all
...
```
**apt remove**: Met dit commando verwijder je het opgegeven pakket van het systeem.
```bash
$ sudo apt remove [packagenaam]
# Voorbeeld
arne@LT3210121:~$ sudo apt remove curl
Reading package lists... Done
Building dependency tree... Done
Reading state information... Done
The following packages were automatically installed and are no longer required:
  libcurl4t64 libllvm17t64
Use 'sudo apt autoremove' to remove them.
The following packages will be REMOVED:
  curl ubuntu-wsl
0 upgraded, 0 newly installed, 2 to remove and 2 not upgraded.
After this operation, 551 kB disk space will be freed.
Do you want to continue? [Y/n] y
(Reading database ... 40775 files and directories currently installed.)
Removing ubuntu-wsl (1.539.2) ...
Removing curl (8.5.0-2ubuntu10.6) ...
Processing triggers for man-db (2.12.0-4build2) ...
```

**sleep**: Met dit commando pauzeer je de uitvoering van commando's voor het opgegeven aantal seconden.
```bash
$ sleep [seconden]
# Voorbeeld
arne@LT3210121:~$ sleep 5
```

**ctrl+c** - 'ctrl+c': Met deze toetsencombinatie beëindig je het huidige proces in de terminal.
```bash
# Voorbeeld
arne@LT3210121:~$ sleep 5
^C
arne@LT3210121:~$
```

**commando's aaneenschakelen** met `;` of `&&`: je kan ze gebruiken om meerdere commando's aan elkaar te schakelen, maar ze werken op verschillende manieren. De `;` operator voert de commando's sequentieel uit, ongeacht of het vorige commando succesvol was of niet. De `&&` voert operator het tweede commando alleen uit als het eerste commando succesvol is (d.w.z. een exitstatus van 0 heeft). Dit maakt `&&` nuttig voor het uitvoeren van afhankelijkheden, waar het tweede commando alleen zinvol is als het eerste commando slaagt.
```bash
# Voorbeeld ;
arne@LT3210121:~$ cat test.txt; echo "two"
Dit is een test file.
two

arne@LT3210121:~$ cat onbestaand.txt; echo "two"
cat: onbestaand.txt: No such file or directory
two
# Voorbeeld &&
arne@LT3210121:~$ cat test.txt && echo "two"
Dit is een test file.
two

arne@LT3210121:~$ cat onbestaand.txt && echo "two"
cat: onbestaand.txt: No such file or directory
```

**>>**: Met deze operator voeg je de output van een commando toe aan het einde van een bestand.
```bash
$ [commando met output] >> [bestand]
# Voorbeeld
arne@LT3210121:~$ cat test.txt
Dit is een test file.

arne@LT3210121:~$ echo "Hello" >> test.txt
arne@LT3210121:~$ cat test.txt
Dit is een test file.
Hello
```

**>**: Met deze operator operator kun je de output van een commando naar een bestand sturen, waarbij de inhoud van het bestand wordt overschreven als het bestand al bestaat.
```bash
$ [commando met output] >> [bestand]
# Voorbeeld
arne@LT3210121:~$ cat test.txt
Dit is een test file.

arne@LT3210121:~$ echo "Hello" > test.txt
arne@LT3210121:~$ cat test.txt
Hello
```

**Wildcards (*)**: Met de wildcard operator kun je patronen specificeren die overeenkomen met meerdere bestanden of directories in één keer. Dit is vooral handig bij het uitvoeren van bewerkingen op groepen bestanden zonder dat je elk bestand afzonderlijk hoeft te specificeren. Bijvoorbeeld kopieer alle bestanden met een bepaalde extensie naar een map: `cp *.txt doelmap/`

_**[Maak nu de oefeningen van oefenreeks 2 en 3 van de volgende pagina](/1-wsl-vscode/wsl_exercises.md/#oefeningenreeks-2)**_

**EXTRA**: hier nog een lijst van nuttige commando's en principes die we voorlopig niet verder in diepgang gaan bespreken, maar wel handig kunnen zijn in je ingenieurs carrière:
- `grep`
- het principe van piping `|`
- `ssh`
- het principe van background processes met `&` of `Ctrl+z`
- `fg`
- `curl`
- `ping`
- het principe van de `bashrc`-file 
- `source`
- fancy printouts
- het principe van signal interrupts
- regular expressions

## Shell scripts
Een shell script is een tekstbestand dat een reeks commando's bevat die door een Unix-shell worden uitgevoerd. Shell scripts worden vaak gebruikt om taken te automatiseren, zoals systeembeheer, batchverwerking en het uitvoeren van complexe commando's. Een belangrijk onderdeel van een shell script is de shebang (`#!`), die aangeeft welke interpreter moet worden gebruikt om het script uit te voeren. Voor een Bash-script wordt vaak de volgende shebang gebruikt: `#!/bin/bash`. Dit vertelt het systeem dat het script moet worden uitgevoerd met de Bash-shell.

**Voorbeeld van een eenvoudig shell script: `test.sh`**
```bash
#!/bin/bash
# Dit is een eenvoudig shell script dat een begroeting weergeeft en een lijst van bestanden in de huidige directory toont.

echo "Hallo, wereld!"
echo "Hier is een lijst van bestanden in de huidige directory:"
ls -l
```

Om een shell script uitvoerbaar te maken, moet je het bestand de juiste permissies geven met het `chmod`-commando. Dit doe je door de uitvoerbare permissie toe te voegen met `chmod +x`. Dan kan je het shell script uitvoeren met de naam van het `.sh`-bestand.

### Variabelen in een Shell Script
In een shell script kun je variabelen gebruiken om gegevens op te slaan en te manipuleren. Variabelen worden zonder spaties gedefinieerd en kunnen later in het script worden opgeroepen door een $-teken voor de variabelenaam te plaatsen.

**Voorbeeld van een shell script met variabelen:**
```bash
#!/bin/bash
# Dit script gebruikt variabelen om een begroeting weer te geven.

NAAM="Alice"
echo "Hallo, $NAAM! Welkom bij het shell scripting."
```

### Opties meegeven en/of input uitlezen
Je kunt een shell script zo schrijven dat het invoer van de gebruiker accepteert via de commandline of tijdens de uitvoering van het script. Hier is een voorbeeld van beide methoden:

**Voorbeeld: Invoer via de commandline**
```bash
#!/bin/bash
# Dit script accepteert een naam als argument en geeft een begroeting weer.

NAAM=$1
echo "Hallo, $NAAM! Welkom bij het shell scripting."
```
Om dit script uit te voeren, geef je de naam op als argument: 
```bash
./script.sh Alice
```
_Merk op dat `$0` het commando zelf is!_

**Voorbeeld: Invoer tijdens de uitvoering**
```bash
#!/bin/bash
# Dit script vraagt de gebruiker om een naam in te voeren en geeft een begroeting weer.

echo "Voer je naam in:"
read NAAM
echo "Hallo, $NAAM! Welkom bij het shell scripting."
```

**Voorbeeld: conditional statements**
```bash
#!/bin/bash
# Dit script controleert de waarde van een variabele en geeft een bericht weer op basis van de waarde.

echo "Voer een getal in:"
read getal

if [ $getal -lt 10 ]; then
  echo "Het getal is kleiner dan 10."
elif [ $getal -eq 10 ]; then
  echo "Het getal is precies 10."
else
  echo "Het getal is groter dan 10."
fi
```
_Merk op dat je eindigt met `fi`_

[Lijst met conditions](https://www.geeksforgeeks.org/basic-operators-in-shell-scripting/):
- `&&`: logische AND
- `||`: logische OR
- `!`: logische NOT
- `-eq` of `==`: equals
- `-ne` of `!=`: not equals
- `-lt` of `<`: less than
- `-le` of `<=`: less than or equals
- `-gt` of `>`: greater than
- `-le` of `>=`: greater than or equals

**Voorbeeld: for-loop**
```bash
#!/bin/bash
# Dit script gebruikt een for-loop om een reeks bestanden te maken met oplopende getallen in de bestandsnamen.

# Beginwaarde van het getal
start=1

# Eindwaarde van het getal
eind=5

# Gebruik een for-loop om door de reeks getallen te itereren
for ((i=start; i<=eind; i++)); do
  bestandsnaam="bestand_$i.txt"
  touch "$bestandsnaam"
  echo "Bestand aangemaakt: $bestandsnaam"
done
```
_Merk op dat je eindigt met `done`_

**Voorbeeld: while-loop**
```bash
#!/bin/bash
# Dit script gebruikt een while-loop om een getal te verhogen en weer te geven totdat het een bepaalde waarde bereikt.

# Beginwaarde van het getal
getal=1

# Eindwaarde van het getal
eind=5

# Gebruik een while-loop om het getal te verhogen en weer te geven
while [ $getal -le $eind ]; do
  echo "Huidig getal: $getal"
  getal=$((getal + 1))
done
```
_Merk op dat je eindigt met `done`_

_**[Maak nu de oefeningen van oefenreeks 4 van de volgende pagina](/1-wsl-vscode/wsl_exercises.md/#oefeningenreeks-4)**_


## Wat moet ik hier nu allemaal van kennen/kunnen?
Het doel van deze informatie is dat je vlot je weg kan vinden in een OS met behulp van enkel een CLI. Je hoeft geen theorie te kennen, maar je moet wel simpele commando's kunnen gebruiken en kennen zoals: *Met welk commando navigeer je naar de directory `/home/arne/test` door gebruik te maken van een relatief pad wanneer je je in de directory `/home/arne` bevindt? OPLOSSING: `$ cd test` of `$ cd ./test`.*

Heb je nog meer vragen over hoe de commando's werken, gebruik dan het `man`-commando of zoek de documentatie op op het internet.