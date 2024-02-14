---
title: 'Lokaal versiebeheer: Git'
weight: 2
draft: false
author: Wouter Groeneveld en Arne Duyver
---

## De git workflow

{{% notice note %}}
SVN, RCS, MS SourceSafe, CVS, ... zijn allemaal **version control systemen** (VCS). Merk op dat Git géén klassieke "version control" is maar eerder een collaboratieve tool om met meerdere personen tegelijkertijd aan verschillende versies van een project te werken. Er is geen revisienummer dat op elkaar volgt zoals in CVS of SVN (v1, v2, v3, v'), en er is geen logische timestamp. (Zie [git is not revision control](https://blog.feld.me/posts/2018/01/git-is-not-revision-control/)).
Ook, in tegenstelling tot bovenstaande tools, kan je Git ook compleet lokaal gebruiken, zonder ooit te pushen naar een upstream server. Het is dus "self-sufficient": er is geen "server" nodig: dat is je PC zelf. 
{{% /notice %}}

![The lifecycle of the status of your file](/img/versiebeheer/git_flowchart_staging.png "from Pro Git handbook")
([Image Src](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository))

### Git correct configureren

Om van start te kunnen gaan, moeten we Git eerst nog correct configureren. Dit doen we via de volgende commando's:
```bash
$ git config --global user.email "jouwemail@voorbeeld.com"
$ git config --global user.name "VoornaamAchternaam"
```
We gaan in de volgende les een account aanmaken op een cloudplatform waarmee we ons versiebeheer gaan kunnen uitbreiden met cloud opslag en de mogelijkheid om samen te werken aan hetzelfde project. Hiervoor gaan we een gratis account moeten aanmaken met een emailadres en een username. Dit moeten dezelfde worden als de gegevens die je zojuist hebt geconfigureerd. Je kan deze gegevens later nog aanpassen.

Soms ga een texteditor moeten gebruiken om bijvoorbeeld een boodschap mee te geven bij elke nieuwe 'versie' die we gaan opslaan. Standaard wordt hier Vim voor gebruikt. Dat is een CLI-texteditor, maar aangezien dit niet zo beginnersvriendelijk is, verandere we de default liever naar iets anders zoals Notepad. Dit doe je via het volgende commando:
```bash
$ git config --global core.editor notepad
```

### Een directory intialiseren als een Git directory

Om van een directory een git directory te maken en alle veranderingen beginnen tracken gebruiken we het commando: 
```bash
$ git init
```
Er verschijnt een vorborgen folder in je directory genaamd `.git`. In die folder zal git alles bewaren om de veranderingen te tracken en een geschiendenis van alle versies bij te houden.

### Checking, staging and committing changes

Met het commando **`$ git status`** kan je controleren in welke staat alle files/directories zich bevinden. Een file/folder kan zich in één van drie toestanden bevinden:
- **Modified**: de file is aangepast maar nog niet gestaged.
- **Staged**: de file is gestaged en klaar om gecommit te worden.
- **Committed**: de file is sinds zijn laatste commit niet meer aangepast.
- (**Untracked**: dit geldt enkel voor files/folders die juist aangemaakt zijn en nog nooit gecommit werden)

Een voorbeeld output ziet er als volgt uit:
<pre>
Work@ALFHEIM MINGW64 ~/Desktop/ses_git (master)
$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
        modified:   voorbeeld.txt

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        haha.txt

no changes added to commit (use "git add" and/or "git commit -a")
</pre>

Met het commando **`$ git diff`** kan je de verschillen zien tussen de huidige staat van je files/folders versus de staat van de files/folders in de laatste commit.

Met het commando **`$ git add <filenaam>`** kan je afzonderlijk modified files naar de staging area 'verplaatsen'.

Met het commando **`$ git commit`** kan je alle files committen die gestaged zijn. Je teksteditor opent en je wordt gevraagd een message mee te geven met de commit. Probeer steeds een concrete boodschap mee te geven met elke commit bv. "Bug opgelost waarbij alles vetgedrukt stond". Probeer elke commit message zo zinvol mogelijk te maken. Dit maakt het later makkelijk om terug te keren naar een belangrijke 'versie' van je broncode. Elke commit krijgt ook een specifieke **hash** zodat je gemakkelijk naar een specifieke 'versie' (commit) kan refereren.

- Alle modified files stagen met 1 commando: **`$ git add .`**
- Onmiddellijk een commit message meegeven binnenin het commit commando: **`$ git commit -m "mijn commit message"`**
- combinatie van de twee bovenstaande commando's: **`$ git commit -a -m "mijn commit message"`**

#### Undo changes
`git status` geeft je ook informatie over hoe je files/folders kan unstagen en zelfs de veranderingen van files kan terugbrengen naar hoe ze eruit zagen tijdens de laatste commit.

### Bekijk de version tree (log)
Het commando **`$ git log`** wordt gebruikt om een lijst te zien van all je commits in chronologische volgorde, startend bij de laatste commit. Dat commando geeft veel informatie mee voor elke commit. Wil je liever een korter overzicht? Gebruik dan **`$ git log --oneline`**.

### Teruggaan naar vorige 'versies'

Er bestaan commando's die we kunnen gebruiken om naar een vroegere commit terug te keren. Ze werken echter op een verschillende manier en het is belangrijk dat je deze verschillen goed kent. 
- **`$ git reset <hash van de commit>`**: hiermee ga je terug naar een vorige commit en worden alle commits die erna gebeurden verwijderd.
- **`$ git checkout <hash van de commit>`**: hiermee kan je tijdelijk terugkeren naar een vorige commit. Je zit hier dan in een soort viruele omgeving waar je ook commits kan aanbrengen. Om dan terug te keren naar de HEAD van de versiegeschiedenis gebruik je:
  - **`$ git switch -c <branchname>`** : om je nieuwe virtuele commits op te slaan in een echte nieuwe branch.
  - **`$ git switch -`** : om terug te keren zonder je nieuwe virtuele commits bij te houden.

### Branching

Het fijne aan Git is dat je parallel aan verschillende versies van je project kan werken door gebruik te maken van **branching**. Je start letterlijk een nieuwe tak waar je eigen commits kan aan toevoegen. Zo kan elk teamlid bijvoorbeeld zijn eigen branch aanmaken. Of je kan een brach aanmaken om aan een nieuwe feature te werken zonder dat je schrik moet hebben om problemen te creëren op de main branch.

Je maakt een nieuwe branch aan met het commando: **`$ git branch <branchnaam>`**
Je kan de verschillende branches oplijsten met: **`$ git branch`**
- Het `*` symbool duidt de actieve branch aan.
Je kan naar een bepaalde branch gaan met: **`$ git checkout <branchnaam>`**
Je kan een branch deleten mert: **`$ git branch -d <branchnaam>`**

#### Merging

Je kan de commits van een zijtak nu terug toevoegen aan de historie van een andere tak. Dit doe je met het commando **`$ git merge <branchname>`**. Wil je bijvoorbeeld de wijzingen van de `new_feature` branch mergen met de `main` branch, dan moet je eerst zien dat je je in de `main` branch bevindt en dan gebruik je het volgende commando: `$ git merge new_feature`

#### Merge conflicts

Wanneer je op twee verschillende branches echter commits heb die verschillende aanpassingen maken aan hetzelde stukje code in je project dan onstaat er een mergeconflict. Je zal dan beide versies voorgeschoteld krijgen en jij moet dan de versie verwijderen die je niet wilt behouden. Dan pas kan de 'merge' succesvol afgerond worden.

### .gitignore

Soms wil je dat een file niet wordt bijghouden door git. Hier komen we in het gedeelte over [remote repositories](/versiebeheer/remote-repository-met-github) op terug. De files/folders die niet getracked mogen worden, moeten we in het bestand `.gitignore` toevoegen.

### Tagging

Om bepaalde belangrijke commits snel terug te vinden, kan je gebruik maken van tags. 
- Je kan de bestaande tags oplijsten met: **`$ git tag`**
- Je kan de huidige commit taggen met: **`$ git tag -a vX.X`**
- Je kan een specifieke commit taggen met: **`$ git tag -a vX.X <hashcode commit>`**
- Je kan tags deleten met: **`$ git tag -d vX.X`**

{{% notice warning %}}
In de lessen gaan we **tags** gebruiken om finale versie van een opdracht terug te kunnen vinden.
{{% /notice %}}

## Belangrijk
We hebben hier enkel de basis commando's aangehaald met een paar van de meest gebruikte manieren waarop ze gebruikt worden. Voor meer diepgang en achtergrond van alle git commando's verwijs ik naar het [Pro Git](https://git-scm.com/book/en/v2) handboek. Vooral hoofdstuk 1 t.e.m. 3 zijn voor jullie interessant.

## Oefeningen 
1. Maak een git directory aan
3. Voeg één of meerdere tekstbestanden toe aan deze directory. Maak eventueel subfolders ...
4. Gebruik `$ git status` om te zien wat er allemaal gecommit kan worden.
5. Commit deze veranderingen.
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
16. Doe een git revert naar je eerste commit door gebruik te maken van je nieuwe tags.
17. Probeer van alle bovenstaande stappen een schets te maken om zo na te gaan of je alle commando's goed begrijpt.
---
*Indien niet afgewerkt in het applicatiecollege, gelieve thuis af te werken tegen volgende les.*


## Extra

### Wildcards en commando's aaneenschakelen

**Wildcards** kunnen gebruikt worden om naar meerdere files tegelijk te verwijzen. Zo kan je de wildcard '<b>*</b>' gebruiken om naar alle files die eindigen op '.txt' te verwijzen. 
</br> Bijvoorbeeld: **`$ git add *.txt`**

Je kan commando's ook **aaneenschakelen** met '<b>;</b>' zodat meerdere commando's onmiddellijk na elkaar uitgevoerd worden 
</br> Bijvoorbeeld: **`$ git add . ; git commit -m "initial commit"`**