---
title: 'Oefeningen op de CLI'
weight: 2
draft: false
author: Arne Duyver
---

<!-- EXSOL -->
_De gegeven oplossingen zijn EEN mogelijke oplossing, soms zijn meerdere mogelijkheden juist. Is het gewenste gedrag bereikt, dan is je oplossing correct!_

## Oefeningenreeks 1

- Toon het pad van de huidige werkdirectory.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ pwd``` 
- Maak een nieuw leeg bestand genaamd `nieuwbestand.txt`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ touch nieuwbestand.txt``` 
- Maak een nieuwe directory genaamd `testmap`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ mkdir testmap``` 
- Verwijder een bestand genaamd `nieuwbestand.txt`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ rm nieuwbestand.txt``` 
- Voeg de tekst "Hallo, wereld!" toe aan de terminaloutput.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ echo "Hallo, wereld!"``` 
- Navigeer naar je `home` directory.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ cd ~``` 
- Wis de output van je terminal.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_ ```$ clear``` 
- Bekijk de handleiding voor het commando dat bestanden en directories weergeeft.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ man cd``` 
- Toon de inhoud van de huidige directory.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ ls``` 
- Open het bestand `nieuwbestand.txt` in een teksteditor en voeg de tekst "Dit is een test." toe. Sla het bestand op en sluit de editor.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ nano nieuwbestand.txt
# save met Ctrl+o en Enter. Exit met Ctrl+x
``` 
- Toon de inhoud van `nieuwbestand.txt` in de terminal.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ cat nieuwbestand.txt``` 

<br>

- Maak een nieuw directory genaamd `project`, navigeer naar deze directory, en maak een nieuw bestand genaamd `README.md`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ mkdir project
$ cd ./project
$ touch ./README.md
```  
- Maak een nieuw bestand genaamd `info.txt`, voeg de tekst "Dit is een informatief bestand." toe, en toon de inhoud van het bestand.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ nano info.txt
$ cat info.txt
```
- Maak een nieuw directory genaamd `backup`, kopieer het bestand `info.txt` naar de backup-directory, en verwijder vervolgens het originele `info.txt`-bestand.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ mkdir backup
$ cp ./info.txt ./backup/info.txt
$ rm ./info.txt
```

## Oefeningenreeks 2
- Wijzig de eigenaar van een bestand genaamd `nieuwbestand.txt` naar de gebruiker jezelf en de groep je eigen groep.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ sudo chown $USER:$USER nieuwbestand.txt``` 
- Verplaats een bestand genaamd `nieuwbestand.txt` naar een nieuwe locatie met de naam `nieuw_bestand.txt`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ mv nieuwbestand.txt ./some_dir/nieuw_bestand.txt``` 
- Kopieer een bestand genaamd `nieuw_bestand.txt` naar een nieuwe locatie met de naam `doel.txt`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ cp nieuw_bestand.txt ./some_dir/doel.txt``` 
- Zoek naar een softwarepakket met de naam 'neofetch'.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ sudo apt search neofetch``` 
- Installeer een softwarepakket genaamd 'neofetch'.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ sudo apt install neofetch``` 
- Verwijder een geïnstalleerd softwarepakket genaamd 'neofetch'.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ $ sudo apt remove neofetch``` 
- Wijzig de permissies van een bestand genaamd `nieuwbestand.txt` zodat de eigenaar lees-, schrijf- en uitvoerrechten heeft, en de groep en anderen alleen lees- en uitvoerrechten hebben.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ sudo chmod 755 nieuwbestand.txt``` 
- Voer twee commando's na elkaar uit, ongeacht of het eerste commando succesvol is.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ cat nieuwbestand.txt; echo "De file bestaat of niet"``` 
- Voer een tweede commando alleen uit als het eerste commando succesvol is.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ cat nieuwbestand.txt && echo "De file bestaat"``` 
- Schrijf de uitvoer van een commando naar een bestand genaamd `output.txt`, waarbij de bestaande inhoud van het bestand wordt overschreven.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ ls > output.txt``` 
- Voeg de uitvoer van een commando toe aan het einde van een bestand genaamd `output.txt`, zonder de bestaande inhoud te verwijderen.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ echo "Einde bestand" >> output.txt``` 
- Zoek naar een softwarepakket genaamd `curl`, installeer het pakket.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ sudo apt search curl
$ sudo apt install curl
``` 
- Verwijder alle bestanden in je map met de extensie `.txt`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  ```$ rm *.txt``` 

<br>

- Maak een bestand genaamd `config.txt` en voeg wat tekst toe. Maak een kopie van een bestand genaamd `config.txt` naar een nieuwe locatie met de naam `backup_config.txt`, wijzig de eigenaar van backup_config.txt naar de gebruiker `root`, en voeg de tekst "Backup voltooid" toe aan een logbestand genaamd `log.txt`.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ nano config.txt
$ cp config.txt ./backups/backup_config.txt
$ sudo chown root:root ./backups/backup_config.txt
$ touch log.txt && echo "Backup voltooid" > log.txt
``` 

## Oefeningenreeks 3

**Oefening 1:**
- Maak een directorystructuur aan met de volgende paden: `project/src`, `project/bin`, en `project/docs`.
- Navigeer naar de `src`-directory.
- Maak een nieuw bestand genaamd `main.c` in de `src`-directory.
- Kopieer het bestand `main.c` naar de `bin`-directory.
- Toon de inhoud van de `bin`-directory.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ mkdir -p project/src project/bin project/docs
$ cd project/src
$ touch main.c
$ cp main.c ../bin/
$ ls ../bin/
``` 

**Oefening 2:**
- Maak een nieuwe directory genaamd `backup` in je thuismap.
- Maak een `subdirectory` genaamd `2025` in de `backup`-directory.
- Maak een nieuw bestand genaamd `data.txt` in de `2025-directory`.
- Voeg de tekst "Backup data voor 2025" toe aan `data.txt`.
- Toon de inhoud van `data.txt` in de terminal.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ mkdir ~/backup
$ mkdir ~/backup/2025
$ touch  ~/backup/2025/data.txt
$ echo "Backup data voor 2025" > ~/backup/2025/data.txt
$ cat ~/backup/2025/data.txt
``` 

**Oefening 3:**
- Zoek naar een softwarepakket genaamd `htop`.
- Installeer het `htop`-pakket.
- Maak een directorystructuur aan met de volgende paden: `tools/monitoring`.
- Start het programma `htop` via het absolute pad naar de htop executable file.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
# vergeet voor het installeren van software packages geen update te doen...
$ sudo apt-get update
$ sudo apt-get install -y htop
$ mkdir -p tools/monitoring
# Een kleine zoektocht toont met dat de `htop` executable file zich bevindt in de `/bin` folder
$ /bin/htop
``` 

**Oefening 4:**
- Maak een directorystructuur aan met de volgende paden: `website/css`, `website/js`, en `website/images`.
- Navigeer naar de `css`-directory.
- Maak een nieuw bestand genaamd `styles.css` in de css-directory.
- Voeg de tekst "body { background-color: #f0f0f0; }" toe aan `styles.css`.
- Toon de inhoud van `styles.css` in de terminal.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ mkdir -p website/css website/js website/images
$ cd website/css
$ echo "body { background-color: #f0f0f0; }" > styles.css
$ cat styles.css
``` 

**Oefening 5:**
- Maak een nieuwe directory genaamd scripts in je thuismap.
- Maak een subdirectory genaamd utilities in de scripts-directory.
- Maak een nieuw shell script genaamd cleanup.sh in de utilities-directory.
- Voeg de volgende inhoud toe aan cleanup.sh:
```bash
#!/bin/bash
echo "Opruimen van tijdelijke bestanden..."
rm -rf /tmp/*
echo "Opruimen voltooid."
```
- Maak het script uitvoerbaar en voer het uit.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
$ mkdir -p ~/scripts/utilities
$ nano ~/scripts/utilities/cleanup.sh
$ chmod +x ~/scripts/utilities/cleanup.sh
$ ~/scripts/utilities/cleanup.sh
``` 

## Oefenreeks 4

**Oefening 1:** Maak een shell script dat aan de gebruiker een absoluut pad van een directory vraagt en het aantal `.txt` bestanden in die directory teruggeeft.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
#!/bin/bash

# Vraag de gebruiker om een absoluut pad van een directory
read -p "Voer het absolute pad van de directory in: " DIR_PATH

# Controleer of de directory bestaat
if [ -d "$DIR_PATH" ]; then
  COUNT=0
  # Gebruik wildcards om alle .txt files in de directory op te vragen
  for FILE in "$DIR_PATH"/*.txt; do
    # Controleer of de file bestaat
    if [ -f "$FILE" ]; then
      COUNT=$((COUNT + 1))
    # vergeet de `fi` niet
    fi
  # vergeet de `done` niet voor de for loop
  done
  echo "Aantal .txt bestanden in $DIR_PATH: $COUNT"
else
  echo "De directory $DIR_PATH bestaat niet."
fi
``` 

**Oefening 2:** Maak een shell script dat het ls commando nadoet met de opties `-l` en `-a` in de huidige directory. Je kan enkel de opties apart meegeven of als combinatie `-la`. Je hebt dus maximum 1 flag die je meegeeft aan je shell script waaruit je afleidt hoe je het `ls` commando moet uitvoeren. 
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
#!/bin/bash

if [ "$1" == "-l" ]; then
  ls -l
elif [ "$1" == "-a" ]; then
  ls -a
elif [ "$1" == "-la" ]; then
  ls -la
else
  ls
fi
``` 

**Oefening 3:** Maak een shell script genaamd `make.sh` dat 4 mogelijke opties kan meekrijgen:
- Als je de optie `start` meegeeft vraagt het script de gebruiker naar een projectnaam en maakt dan volgende directories aan: `./projectnaam/src` en `./projectnaam/build`.
- Als je de optie `build` meegeeft worden alle bestanden in de `./projectnaam/src` directory gekopieerd naar de `./projectnaam/build` directory.
- Als je de optie `clean` meegeeft worden alle bestanden in de `./projectnaam/build` directory gewist.
- Als je de optie `run` meegeeft worden alle bestanden in `./projectnaam/build` van alle `.txt` bestanden een na een getoond.
<!-- EXSOL -->
_**<span style="color: #03C03C;">Solution:</span>**_  
```bash
#!/bin/bash

if [ "$1" == "start" ]; then
  echo "creating project directory ..."
  read -p "Geef een naam voor je project: " PROJECT_NAME
  mkdir -p ./$PROJECT_NAME/src ./$PROJECT_NAME/build
elif [ "$1" == "build" ]; then
  read -p "Geef je projectnaam: " PROJECT_NAME
  echo "building files to build directory ..."
  cp -r ./$PROJECT_NAME/src/* ./$PROJECT_NAME/build
elif [ "$1" == "clean" ]; then
  read -p "Geef je projectnaam: " PROJECT_NAME
  echo "cleaning build directory ..."
  rm -R ./$PROJECT_NAME/build/*
elif [ "$1" == "run" ]; then
  read -p "Geef je projectnaam: " PROJECT_NAME
  echo "running program ..."
  for FILE in ./$PROJECT_NAME/build/*.txt ; do
    if [ -f "$FILE" ]; then
      cat $FILE
    fi
  done
else
  echo "Wrong command, choose: 'start', 'build', 'clean', or 'run'."
fi
echo "Done"
``` 