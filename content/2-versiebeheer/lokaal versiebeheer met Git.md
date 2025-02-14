---
title: "Lokaal versiebeheer: Git"
weight: 1
draft: false
author: Wouter Groeneveld en Arne Duyver
---

## Git installeren

Het versiebeheer systeem dat we in dit opleidingsonderdeel zullen gebruiken is [Git](https://git-scm.com/). 'Git' staat voor **Global Information Tracker** of met andere woorden Git wordt gebruikt om informatie over bestanden te volgen.
Via volgende link kan je alle up-to-date informatie terugvinden over hoe je Git op je besturingssysteem kan istalleren: [Installatie procedure Git](https://git-scm.com/book/nl/v2/Aan-de-slag-Git-installeren)


Je kan checken of Git al geïnstalleerd is op je WSL door de versie ervan op te vragen via de CLI met `git --version`. Indien je geen error krijgt, is Git klaar voor gebruik. Als Git nog niet geïnstalleerd zou zijn kan je dit eenvoudig installeren met `sudo apt install git`. 

### Git gui en Git bash
Het is ook mogelijk een GUI voor Git te installeren, maar deze gaan we niet gebruiken. Dit is overigens ook nog niet mogelijk aangezien we enkel een CLI interface hebben voor onze WSL.

De git-gui app is een beginner vriendelijke omgeving om gemakkelijk met versiebeheer aan de slag te gaan. In de SES-lessen willen we je echter de 'correcte' manier aanleren om als software ontwikkelaar overweg te kunnen met verschillende handige tools zoals Git. Ook daarom gaan we de grafische interface van Git niet gebruiken.

{{% notice info %}}
We gaan gebruik maken van de Git commands in een CLI om aan versiebeheer te doen. Windows gebruikers kunnen hiervoor best gebruik maken van det Git Bash applicatie die gelijktijdig met git geïnstalleerd werd. Mac OS en Linux gebruikers kunnen gebruik maken van hun eigen terminal applicatie.
{{% /notice %}}

## De git workflow

{{% notice note %}}
SVN, RCS, MS SourceSafe, CVS, ... zijn allemaal **version control systemen** (VCS). Merk op dat Git géén klassieke "version control" is maar eerder een collaboratieve tool om met meerdere personen tegelijkertijd aan verschillende versies van een project te werken. Er is geen revisienummer dat op elkaar volgt zoals in CVS of SVN (v1, v2, v3, v'), en er is geen logische timestamp. (Zie [git is not revision control](https://blog.feld.me/posts/2018/01/git-is-not-revision-control/)).
Ook, in tegenstelling tot bovenstaande tools, kan je Git ook compleet lokaal gebruiken, zonder ooit te pushen naar een upstream server. Het is dus "self-sufficient": er is geen "server" nodig: dat is je PC zelf.
{{% /notice %}}

![The lifecycle of the status of your file](/img/versiebeheer/git_flowchart_staging.png "from Pro Git handbook")
([Image Src](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository))

In Git doorloopt een bestand verschillende statussen tijdens zijn levenscyclus. Wanneer een bestand voor het eerst wordt aangemaakt, is het **untracked**, wat betekent dat Git het nog niet volgt. Zodra je het bestand toevoegt aan de repository met `git add`, wordt het **staged**. Dit betekent dat het klaar is om gecommit te worden. Als je het bestand daarna bewerkt, wordt het **modified**. Om deze wijzigingen op te nemen in de volgende commit, moet je het bestand opnieuw stagen met `git add`. Wanneer je tevreden bent met de wijzigingen, gebruik je `git commit` om de wijzigingen definitief vast te leggen (**commit**) in de repository. Als je een bestand niet langer nodig hebt, kun je het verwijderen met `git rm`, waardoor het bestand uit de repository wordt verwijderd en de status van het bestand verandert. Gedurende deze cyclus kan een bestand ook de status **unmodified** hebben, wat betekent dat er geen wijzigingen zijn aangebracht sinds de laatste commit.

### Git correct configureren

Om van start te kunnen gaan, moeten we Git eerst nog correct configureren. Dit doen we via de volgende commando's:

```bash
$ git config --global user.email "jouwemail@voorbeeld.com"
$ git config --global user.name "VoornaamAchternaam"
```

We gaan in de volgende les een account aanmaken op een cloudplatform waarmee we ons versiebeheer gaan kunnen uitbreiden met cloud opslag en de mogelijkheid om samen te werken aan hetzelfde project. Hiervoor gaan we een gratis account moeten aanmaken met een emailadres en een username. _**Dit moeten dezelfde worden als de gegevens die je zojuist hebt geconfigureerd.**_ Je kan deze gegevens later nog aanpassen.

Soms ga je een text editor moeten gebruiken om bijvoorbeeld een boodschap mee te geven bij elke nieuwe 'versie' die we gaan opslaan. Standaard wordt hier Vim voor gebruikt, maar aangezien dit niet zo beginnersvriendelijk is, veranderen we de default liever naar iets anders zoals Nano. Dit doe je via het volgende commando:

```bash
$ git config --global core.editor nano
```

### Een directory initialiseren als een Git directory

Om van een directory een git directory te maken en alle veranderingen beginnen tracken gebruiken we het commando:

```bash
$ git init
```

Er verschijnt een verborgen folder in je directory genaamd `.git`. In die folder zal git alles bewaren om de veranderingen te tracken en een geschiendenis van alle versies bij te houden.

### Checking, staging and committing changes

Met het commando **`$ git status`** kan je controleren in welke staat alle files/directories zich bevinden. Een file/folder kan zich in één van drie toestanden bevinden:

- **Modified**: de file is aangepast maar nog niet gestaged.
- **Staged**: de file is gestaged en klaar om gecommit te worden.
- **Committed**: de file is sinds zijn laatste commit niet meer aangepast.
- (**Untracked**: dit geldt enkel voor files/folders die juist aangemaakt zijn en nog nooit gecommit werden)

Een voorbeeld output ziet er als volgt uit:

<pre>
arne@LT3210121:~/gittest$ git status
On branch master

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
        new file:   eenfile.txt

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
        modified:   eenfile.txt

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        filetwee.txt
</pre>

Met het commando **`$ git diff`** kan je de verschillen zien tussen de huidige staat van je files/folders versus de staat van de files/folders in de laatste commit.

Met het commando **`$ git add <filenaam>`** kan je afzonderlijk modified files naar de staging area 'verplaatsen'. Volgens een goede version control strategy, is dit de manier om wijzigingen te stagen. Op die manier kan je gerichte commits maken die over een bepaald onderwerp gaan. In de praktijk wordt er soms echter voor snelheid gekozen. Zo kan je met **`$ git add .`** alle wijzigingen en nieuwe files in een keer stagen.

Met het commando **`$ git commit`** kan je alle files committen die gestaged zijn. Je teksteditor opent en je wordt gevraagd een message mee te geven met de commit. Probeer steeds een concrete boodschap mee te geven met elke commit bv. "Bug opgelost waarbij alles vetgedrukt stond". Probeer elke commit message zo zinvol mogelijk te maken. Dit maakt het later makkelijk om terug te keren naar een belangrijke 'versie' van je broncode. Elke commit krijgt ook een specifieke **hash** zodat je gemakkelijk naar een specifieke 'versie' (commit) kan refereren.

- Alle modified files stagen met 1 commando: **`$ git add .`**
- Onmiddellijk een commit message meegeven binnenin het commit commando: **`$ git commit -m "mijn commit message"`**
- combinatie van de twee bovenstaande commando's: **`$ git commit -a -m "mijn commit message"`**
  - **Opgelet** Met `$ git commit -a` worden nieuwe files niet toegevoegd. Gebruik hier dus nog `$ git add` voor.

#### Undo changes

`git status` geeft je ook informatie over hoe je files/folders kan unstagen en zelfs de veranderingen van files kan terugbrengen naar hoe ze eruit zagen tijdens de laatste commit.
Gebruik: `git rm --cached <file>...` om te unstagen en `git restore <file>...` om wijzigingen terug te draaien (te discarden).

### Bekijk de version tree (log)

Het commando **`$ git log`** wordt gebruikt om een lijst te zien van all je commits in chronologische volgorde, startend bij de laatste commit. Dat commando geeft veel informatie mee voor elke commit. Wil je liever een korter overzicht? Gebruik dan **`$ git log --oneline`**. (Met de `--graph` flag kan je ook de boomstructuur weergeven waar we zo dadelijk op terugkomen)

### Teruggaan naar vorige 'versies'

Er bestaan commando's die we kunnen gebruiken om naar een vroegere commit terug te keren. Ze werken echter op een verschillende manier en het is belangrijk dat je deze verschillen goed kent.

- **`$ git reset <hash van de commit>`**: hiermee ga je terug naar een vorige commit en worden alle commits die erna gebeurden verwijderd.
- **`$ git checkout <hash van de commit>`**: hiermee kan je tijdelijk terugkeren naar een vorige commit. Je zit hier dan in een soort virtuele omgeving waar je ook commits kan aanbrengen. Om dan terug te keren naar de **HEAD** van de versiegeschiedenis gebruik je:
  - **`$ git switch -c <branchname>`** : om je nieuwe virtuele commits op te slaan in een echte nieuwe branch.
  - **`$ git switch -`** : om terug te keren zonder je nieuwe virtuele commits bij te houden.

### Snel een extra wijziging aanbrengen aan een vorige commit
Soms ga je snel te werk en heb je een commit gedaan, maar is er nog een kleine wijziging die eigenlijk ook nog best tot die commit hoort. In de plaats van een volledig nieuwe commit te doen kan je via `git commit --amend` je nieuwe _gestagede_ files/folders toevoegen aan de laatste commit en de commit message eventueel wijzigen.

Dit is ook een handige manier om incrementeel en regelmatig een commit te doen, ook al denk je dat je werk nog geen volwaardige commit vereist. Je blijft gewoon stap na stap delen toevoegen tot je tevreden bent.

{{% notice warning %}}
Het `git commit --amend` commando kan je remote repository in de war sturen, wanneer een commit bijvoorbeeld gepushed is naar de remote en daarna lokaal nog geamend wordt. 
{{% /notice %}}

### Branching

Het fijne aan Git is dat je parallel aan verschillende versies van je project kan werken door gebruik te maken van **branching**. Je start letterlijk een nieuwe tak waar je eigen commits kan aan toevoegen. Zo kan elk teamlid bijvoorbeeld zijn eigen branch aanmaken. Of je kan een brach aanmaken om aan een nieuwe feature te werken zonder dat je schrik moet hebben om problemen te creëren op de main branch.

Je maakt een nieuwe branch aan met het commando: **`$ git branch <branchnaam>`**
Je kan de verschillende branches oplijsten met: **`$ git branch`**

- Het `*` symbool duidt de actieve branch aan.
  Je kan naar een bepaalde branch gaan met: **`$ git checkout <branchnaam>`**
  Je kan een branch deleten met: **`$ git branch -d <branchnaam>`**

_Om een grafische voorstelling van de braches te laten printen bij het log commando gebruik je de `--graph` flag._

#### Merging

Je kan de commits van een zijtak nu terug toevoegen aan de historie van een andere tak. Dit doe je met het commando **`$ git merge <branchname>`**. Wil je bijvoorbeeld de wijzigingen van de `new_feature` branch mergen met de `main` branch, dan moet je eerst zien **dat je je in de `main` branch bevindt** en dan gebruik je het volgende commando: `$ git merge new_feature`

#### Merge conflicts

Wanneer je op twee verschillende branches echter commits heb die verschillende aanpassingen maken **aan hetzelfde stukje code** in je project dan ontstaat er een merge conflict. Je zal dan beide versies voorgeschoteld krijgen en jij moet dan de versie verwijderen die je niet wilt behouden. Dan pas kan de 'merge' succesvol afgerond worden.

### .gitignore

Soms wil je dat een file niet wordt bijgehouden door git. Hier komen we in het gedeelte over [remote repositories](/versiebeheer/remote-repository-met-github) op terug. De files/folders die niet getracked mogen worden, moeten we in het bestand `.gitignore` toevoegen.

<details open>
<summary><i><b>Klik hier om een voorbeeld <code>.gitignore</code> file te zien/verbergen</b></i>🔽</summary>
<p>

```.gitignore
# Negeert het bestand secret.txt in de root directory.
/secret.txt 

# Negeert alle bestanden in de node_modules directory.
node_modules/

# Negeert de gehele directory build en alle subdirectories.
/build/

# Negeert alle bestanden met de extensie .log.
*.log 

# Negeert alle bestanden in de temp directory, maar niet de subdirectories.
/temp/*

# Negeert alle .tmp bestanden in alle directories en subdirectories.
**/*.tmp: 

# Negeert alle bestanden in de logs directory.
/logs/*
# Negeert NIET het bestand debug.log in de logs directory.
!/logs/debug.log: 

# Negeert alle bestanden die beginnen met een tilde (~).
~* 
```

</p>
</details>

### Tagging

Om bepaalde belangrijke commits snel terug te vinden, kan je gebruik maken van tags.

- Je kan de bestaande tags oplijsten met: **`$ git tag`**
- Je kan de huidige commit taggen met bijvoorbeeld: **`$ git tag vX.X`**
- Je kan een specifieke commit taggen met bijvoorbeeld: **`$ git tag vX.X <hashcode commit>`**
- Je kan tags deleten met bijvoorbeeld: **`$ git tag -d vX.X`**

**Eens een commit een tag heeft gekregen kan je er ook naar refereren via de tag.

_Indien je een bestaande tag wil verplaatsen, moet je ze eerst deleten en daarna toevoegen aan de nieuwe commit._

<!-- {{% notice warning %}}
In de lessen gaan we **tags** gebruiken om finale versie van een opdracht terug te kunnen vinden.
{{% /notice %}} -->

## Belangrijk

We hebben hier enkel de basis commando's aangehaald met een paar van de meest gebruikte manieren waarop ze gebruikt worden. Voor meer diepgang en achtergrond van alle git commando's verwijs ik naar het [Pro Git](https://git-scm.com/book/en/v2) handboek. Vooral hoofdstuk 1 t.e.m. 3 zijn voor jullie interessant.

## Oefeningen

1. Maak een git directory aan
2. Voeg één of meerdere tekstbestanden toe aan deze directory. Maak eventueel subfolders ...
3. Gebruik `$ git status` om te zien wat er allemaal gecommit kan worden.
4. Commit deze veranderingen.
5. Maak aanpassingen aan de tekstbestanden en commit.
6. Keer terug naar de eerste commit via `$ git reset`
7. Bestudeer de output van `$ git log`
8. Maak een nieuwe branch en chechout naar die nieuwe branch
9. Voeg in deze branch nieuwe textbestanden toe en commit de verandering in die nieuwe branch.
10. Open File Explorer en doe een checkout terug naar de main branch. Zie je de nieuwe files verdwijnen/verschijnen wanneer je tussen de twee branches wisselt?
11. Merge je nieuwe branch met de main branch.
12. Maak veranderingen in de main branch en commit.
13. Switch terug naar je andere branch en maak op dezelfde plaats veranderingen en commit. Zo kan ga je een mergeconflict opwekken.
14. Probeer nu weer je nieuwe branch te mergen met je main branch. Je zal eerst het merge conflict moeten oplossen
15. Tag je eerste commit met `v1.0` en tag je laatste commit met `v2.0`.
16. Doe een git checkout naar je eerste commit door gebruik te maken van je nieuwe tags.
17. Probeer van alle bovenstaande stappen een schets te maken om zo na te gaan of je alle commando's goed begrijpt.

---

_Indien niet afgewerkt in het applicatiecollege, gelieve thuis af te werken tegen volgende les._

## Extra

### Wildcards en commando's aaneenschakelen

**Wildcards** kunnen gebruikt worden om naar meerdere files tegelijk te verwijzen. Zo kan je de wildcard '<b>_</b>' gebruiken om naar alle files die eindigen op '.txt' te verwijzen.
</br> Bijvoorbeeld: \*\*`$ git add _.txt`\*\*

Je kan commando's ook **aaneenschakelen** met '<b>;</b>' zodat meerdere commando's onmiddellijk na elkaar uitgevoerd worden
</br> Bijvoorbeeld: **`$ git add . ; git commit -m "initial commit"`**

### Extra bronnen
- [Pro Git](https://git-scm.com/book/en/v2) handboek, vooral hoofdstuk 1 tot en met 3.
- [git - the simple guide](http://rogerdudler.github.io/git-guide/)
- [Code Forest: Git VS SVN](https://www.codeforest.net/git-vs-svn)
- [the Harvard.edu guide to Git](https://cs61.seas.harvard.edu/site/ref/git/)

- [Beginner vriendelijke Git en Github tutorials](https://www.youtube.com/playlist?list=PLRqwX-V7Uu6ZF9C0YMKuns9sLDzK6zoiV)
- [Tools & Concepts for Mastering Version Control with Git](https://www.youtube.com/watch?v=Uszj_k0DGsg)
- Wil je meer inzicht in hoe git achter de schermen precies werkt? Lees dan zeker [dit artikel](https://get-git.readthedocs.io/en/latest/internal.html#internal) eens! Die geeft je op een toegankelijke manier uitleg over hoe git wijzigingen opslaat, wat de staging area precies is, etc. (Opmerking: het artikel begint met een vergelijking met SVN; dat is een ander/ouder versiecontrolesysteem.)