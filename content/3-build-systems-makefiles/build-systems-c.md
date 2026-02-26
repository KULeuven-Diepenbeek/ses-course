---
title: 'Cmdline C Compiling'
weight: 1
draft: false
author: Arne Duyver
---

## C programma's compilen

### Source code en header files
De ontwikkeling begint met het schrijven van de broncode in `.c`-bestanden en het definiëren van functies en variabelen in header files (`.h`-bestanden). Header files bevatten vaak declaraties van functies en macro's die in meerdere bronbestanden worden gebruikt. 
- Bijvoorbeeld `main.c`:
```c
#include "header.h"
#include <stdio.h>

int main() {
    helloWorld();
    printf("Dag %s\n", naam);
    return 0;
}
```

- Bijvoorbeeld `header.c`:
```c
#include "header.h"
#include <stdio.h>

const char* naam = "Jan";

void helloWorld() {
    printf("Hello, World!\n");
}
```

### Preprocessor
De preprocessor voert tekstvervangingen uit voordat de daadwerkelijke compilatie begint. Dit omvat het verwerken van `#include`-directives, het vervangen van macro's en het uitvoeren van voorwaardelijke compilatie.

- Bijvoorbeeld `header.h`:
```c
#ifndef HEADER_H
#define HEADER_H

extern const char* naam;

void helloWorld();

#endif // HEADER_H
```

{{% notice warning %}}
De `#ifndef`-directive staat voor "if not defined" en wordt gebruikt om te voorkomen dat een header file meerdere keren wordt ingeladen, wat kan leiden tot dubbele declaraties en andere problemen. Dit wordt ook wel een **include guard** genoemd.
{{% /notice %}}

### Compiler
De compiler vertaalt de preprocessed broncode naar assembly code. Dit is een laag-niveau representatie van de code die **specifiek is voor de CPU-architectuur**.

```bash
gcc -c main.c header.c
```

### Assembler
De assembler vertaalt de assembly code naar objectbestanden (`.o`-bestanden). Deze objectbestanden bevatten machinecode die door de processor kan worden uitgevoerd, maar zijn nog niet zelfstandig uitvoerbaar.

### Linker

De linker neemt de objectbestanden en eventuele bibliotheken en combineert deze tot een enkel uitvoerbaar bestand. De linker lost symbolen op (zoals functie- en variabelenamen) en zorgt ervoor dat alle verwijzingen correct zijn. Bijvoorbeeld:

```bash
gcc -o output.bin main.o header.o
```

### Libraries
Libraries bevatten vooraf gecompileerde code die kan worden hergebruikt in verschillende programma's. Er zijn statische bibliotheken (`.a`-bestanden) en dynamische bibliotheken (`.so`-bestanden). _Hier komen we later bij dependency management nog op terug._

### De Binary
Het eindresultaat is een binary executable die direct door het besturingssysteem kan worden uitgevoerd. Dit bestand bevat de machinecode, evenals alle benodigde symbolen en verwijzingen naar bibliotheken. Je kan je programma runnen met volgende commando: 
```bash
./output.bin
```

**Door die stappen te doorlopen, wordt de broncode omgezet in een uitvoerbaar programma dat op de doelmachine kan draaien.**

<figure>
    <img src="/img/c-compilation-proces.jpg" alt="c-compilation-proces" style="max-height: 23em;"/>
    <figcaption style="text-align: center; font-weight: bold;">C compilation proces</figcaption>
</figure>

## De GNU Compiler Collection: `gcc`
GCC is een krachtige en veelzijdige compiler die wordt gebruikt voor het compileren van verschillende programmeertalen zoals C, C++, Objective-C, Fortran, Ada en meer. GCC is een essentieel onderdeel van de GNU-toolchain en wordt veel gebruikt in de open-source gemeenschap vanwege zijn flexibiliteit en robuustheid. Het biedt uitgebreide optimalisatiemogelijkheden, foutdetectie en ondersteuning voor verschillende architecturen.

- **compileren naar `.o` files**: `gcc -c ./src/main.c -o ./build/main.o`
- **linken van de `.o` files**: `gcc -o program.bin ./build/*.o`

{{% notice info %}}
Hier enkele veelgebruikte flags voor `gcc`:
- `-Wall`: Deze flag schakelt de meeste waarschuwingen in, zodat je tijdens het compileren meldingen krijgt over mogelijke problemen in je code. Dit helpt om fouten en onbedoelde gedragingen op te sporen.
- `-Wextra`:Met deze flag worden extra waarschuwingen ingeschakeld die niet door -Wall worden gedekt. Hierdoor krijg je nog meer informatie over potentiële problemen of verbeterpunten in je code.
- `-std=c11`:Deze flag geeft aan dat de compiler de C-standaard uit 2011 (C11) moet gebruiken. Dit zorgt ervoor dat je code voldoet aan de specificaties en functionaliteiten die in deze standaard zijn gedefinieerd.
{{% /notice %}}

## Automatiseren met een shell script
Het handmatig compileren van bronbestanden kan tijdrovend en foutgevoelig zijn, vooral bij grotere projecten met veel bestanden. Daarom geven we de voorkeur aan het automatiseren van dit proces. Dit kan je eventueel doen met behulp van een Shell script. 

Door een script te gebruiken, kunnen we ervoor zorgen dat alle stappen consistent en correct worden uitgevoerd, zonder dat we elke keer dezelfde commando's hoeven in te typen. Dit vermindert de kans op menselijke fouten, zoals het vergeten van een bestand of het verkeerd typen van een commando. Bovendien maakt automatisering het eenvoudiger om het compilatieproces te herhalen, wat handig is bij het ontwikkelen en testen van software. Het gebruik van scripts verhoogt de efficiëntie en betrouwbaarheid van het ontwikkelproces, waardoor ontwikkelaars zich kunnen concentreren op het schrijven van code in plaats van op het compileren ervan.

### Oefening
Extract alle files in [dit zip bestand](/files/monstergame-c.zip) naar een directory naar keuze  OF clone [de repository](https://github.com/ArneDuyver/ses-monstergame-c-start). Schrijf een shell script met de naam `make.sh` dat de volgende dingen kan doen en plaats het in de root van je directory:
- `compile`: Compileert de bronbestanden naar de `/build`-directory en maakt de binary `game.bin` in de root directory
- `clean`: Verwijdert de binary en de object files in de build directory
- `run` : Voert de binary uit (bouwt eerst als die nog niet bestaat) en geeft eventuele flags door (`bv --hp 12`)

<!-- EXSOL -->
<details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

```bash
#!/bin/sh
# Dit script ondersteunt drie commando's:
#   compile: Compileert de bronbestanden en maakt de binary
#   run:   Voert de binary uit (bouwt eerst als die nog niet bestaat)
#   clean: Verwijdert de binary en de build directory

# Variabelen (hardcoded voor eenvoud)
SRC_DIR="src"
BUILD_DIR="build"
TARGET="game.bin"
CFLAGS="-Wall -Wextra -std=c11"

# Zorg dat er minstens één argument is meegegeven
if [ "$#" -lt 1 ]; then
    echo "Gebruik: $0 {compile|run|clean} [--hp <waarde>]"
    exit 1
fi

COMMAND=$1
# Shift the parameters so the second becomes the first etc.
shift 

if [ "$COMMAND" = "compile" ]; then
    echo "Bouwen van het project..."

    # Maak de build-directory als deze nog niet bestaat
    if [ ! -d "$BUILD_DIR" ]; then
        mkdir -p "$BUILD_DIR"
    fi

    # Compileer main.c en game.c naar objectbestanden in build/
    echo "Compileren van $SRC_DIR/main.c..."
    gcc $CFLAGS -c "$SRC_DIR/main.c" -o "$BUILD_DIR/main.o"
    echo "Compileren van $SRC_DIR/game.c..."
    gcc $CFLAGS -c "$SRC_DIR/game.c" -o "$BUILD_DIR/game.o"

    # Link de objectbestanden naar de uiteindelijke binary in de root
    echo "Linken naar $TARGET..."
    gcc $CFLAGS -o "$TARGET" "$BUILD_DIR/main.o" "$BUILD_DIR/game.o"
    echo "Build succesvol: $TARGET is aangemaakt."

elif [ "$COMMAND" = "run" ]; then
    # Bouw de binary als deze niet bestaat
    if [ ! -f "$TARGET" ]; then
        echo "Binary niet gevonden, eerst bouwen..."
        sh "$0" build "$@"
    fi
    echo "Uitvoeren van $TARGET..."
    ./"$TARGET" "$@"

elif [ "$COMMAND" = "clean" ]; then
    echo "Opruimen..."
    # Verwijder de binary en de build-directory
    rm -rf "$BUILD_DIR/*"
    rm -f "$TARGET"
    echo "Opruimen voltooid."

else
    echo "Onbekend commando: $COMMAND"
    echo "Gebruik: $0 {compile|run|clean} [--hp <waarde>]"
    exit 1
fi
```

</p>
</details>

### Moeilijkheden

Bij het handmatig compileren van projecten kunnen er verschillende tekortkomingen optreden:
- Een van de grootste uitdagingen is het **beheren van afhankelijkheden tussen bestanden**. Wanneer een bronbestand wordt gewijzigd, moeten alle gerelateerde bestanden opnieuw worden gecompileerd, wat moeilijk bij te houden is zonder een gestructureerd systeem. 
- Daarnaast kan het handmatig invoeren van compilatie- en linkcommando's voor elk bestand tijdrovend en foutgevoelig zijn. 
- De if-else syntax voor de verschillende opties is ook niet zo een gracieuze oplossing.

## Beter, een build system: Makefiles

Makefiles proberen een antwoord te bieden op de tekortkomingen van shell scripts door e**en gestructureerde en efficiënte manier te bieden om afhankelijkheden en compilatiestappen te beheren**. Ze maken gebruik van **regels** en **doelen** om automatisch te bepalen welke bestanden opnieuw moeten worden gecompileerd wanneer een bronbestand wordt gewijzigd. Dit voorkomt onnodige hercompilatie en bespaart tijd. Bovendien kunnen Makefiles complexe build-processen eenvoudig beheren door verschillende taken zoals compileren, linken, testen en opruimen te automatiseren. Ze bieden ook flexibiliteit door het gebruik van variabelen en conditionele statements, waardoor dezelfde Makefile kan worden gebruikt voor verschillende configuraties en platformen.

### Hoe zijn makefiles opgebouwd?

Makefiles zijn opgebouwd uit een reeks regels die beschrijven hoe verschillende bestanden in een project moeten worden gecompileerd en gelinkt. Elke regel in een makefile bestaat uit drie hoofdonderdelen: **doelen**, **afhankelijkheden** en **commando's**.

1. _Doelen (Targets)_: Dit zijn de bestanden die je wilt genereren, zoals objectbestanden of een uitvoerbaar bestand. Een doel kan ook een alias zijn voor een groep commando's, zoals all of clean.
2. _Afhankelijkheden (Dependencies)_: Dit zijn de bestanden waarvan het doel afhankelijk is. Als een van deze bestanden wordt gewijzigd, weet make dat het doel opnieuw moet worden gegenereerd.
3. _Commando's (Commands)_: Dit zijn de shell-commando's die worden uitgevoerd om het doel te genereren. Ze MOETEN beginnen met een tab en worden uitgevoerd in de volgorde waarin ze zijn geschreven.

Voorbeeld:
```makefile
# Doel: objectbestand 'main.o' en 'header.o'
compile: main.c header.c
    gcc -c main.c -o main.o
    gcc -c main.c -o header.o

# Doel: uitvoerbaar bestand 'program.bin'
program: main.o header.o
    gcc -o program.bin main.o header.o

# Doel: opruimen van gecompileerde bestanden
clean:
    rm -f program.bin main.o header.o
```
- Het doel `program` hangt af van `main.o` en `header.o`. Als een van deze objectbestanden wordt gewijzigd, wordt `program` opnieuw gegenereerd.
- De regel `compile` specificeert hoe de nodige `.c`-bestanden moeten worden gecompileerd naar de respectievelijke `.o`-bestanden. 
- Het doel clean verwijdert de gecompileerde bestanden, wat handig is voor een schone hercompilatie.

### Syntax en Flow
Een 'naïve' make file zou er kunnen uitzien zoals hieronder, met wat leuke syntax zoals variabelen:
```makefile
# Declareer variabelen
SRCDIR = ./src
BUILDDIR = ./build

TARGET = program.bin

# Je kan zoals in de commando's simpelweg wildcards gebruiken
compile: 
	gcc -c $(SRCDIR)/header.c -o ./build/header.o
	gcc -c ./src/main.c -o ./build/main.o
	gcc -o $(TARGET) ./build/*.o

clean:
	rm -rf program.bin $(BUILDDIR)/*

# @ suppresses outputting the command to the terminal
run: 
	@echo "Running program ..." 
	./program.bin
```

{{% notice info %}}
In Makefiles, you can use certain symbols as prefixes to control the behavior of commands:
- `@`: Suppresses the command echo, so the command itself won't be printed to the terminal.
- `-`: Ignores errors from the command, allowing the Makefile to continue even if the command fails.
- `+`: Forces the command to be executed even if make is run with options that normally prevent command execution (like -n, -t, or -q).
{{% /notice %}}

Hiermee bereiken we echter niets meer mee dan een gewoon shell script daarom gaan we van de `rule` en `dependecy` met wat syntactische suiker om de kracht van Makefiles te unlocken:

```makefile
# Declareer variabelen kan met `=`, `:=` of `::=`
CC = gcc
CFLAGS = -Wall -Wextra -std=c11
SRCDIR = ./src
BUILDDIR = ./build

# declareer alle .c files
CFILES = $(SRCDIR)/main.c $(SRCDIR)/header.c
# declareer de corresponderende .o files
OBJECTS = $(BUILDDIR)/main.o $(BUILDDIR)/header.o

TARGET = program.bin

# Het is een good practice om altijd een `all` rule te implementeren
# In dit geval is de `all` afhankelijk van onze TARGET
all: $(TARGET)

# Maar waar is onze TARGET afhankelijk van ...
# van alle object files, want enkel dan kunnen we linken tot een binary
$(TARGET): $(OBJECTS)
	$(CC) -o $@ $^
# Hierboven verwijzen we met $@ naar alles links van de `:` en met $^ alle elementen er rechts van

# Maar onze OBJECTS zijn op hun beurt weer afhankelijk van hun corresponderende .c files ...
# we gebruiken hier regular expressions waardoor % een wildcard is
$(BUILDDIR)/%.o: $(SRCDIR)/%.c
	$(CC) $(CFLAGS) -c -o $@ $<
# Hierboven verwijzen we met $@ naar alles links van de `:` en met $< (het corresponderende element) er rechts van

compile: $(TARGET)

clean: 
	rm -rf $(TARGET) $(OBJECTS)

run: $(TARGET)
	./$(TARGET)
```

We declareren als eerst de `all`-rule omdat wanneer je standaard geen command meegeeft aan make, de eerste rule uitgevoerd zal worden.

_Met deze structuur zal er wanneer je `make run` ingeeft enkel gecompileerd worden wat gewijzigt is! Voor mog meer info kan je [hier](https://www.youtube.com/watch?v=DtGrdB8wQ_8) terecht_


### Maar we kunnen nog beter
Nu moeten we nog manueel alle source-files gaan benoemen, maar hier bestaat echter ook wat makefile 'magic' voor om dit te automatiseren: zie voorbeeld hieronder:

```makefile
CC = gcc
CFLAGS = -Wall -Wextra -std=c11
SRCDIR = ./src
BUILDDIR = ./build

# declareer alle .c files en gebruik de * wildcard om simpelweg alle .c bestanden te selecteren in de SRCDIR
CFILES = $(wildcard $(SRCDIR)/*.c)
# declareer de corresponderende .o files via subsititutie en renaming
OBJECTS = $(patsubst $(SRCDIR)/%.c,$(BUILDDIR)/%.o,$(CFILES))

TARGET = program.bin

all: $(TARGET)

$(TARGET): $(OBJECTS)
	$(CC) -o $@ $^

$(BUILDDIR)/%.o: $(SRCDIR)/%.c
	$(CC) $(CFLAGS) -c -o $@ $<

compile: $(TARGET)

clean: 
	rm -rf $(TARGET) $(OBJECTS)

run: $(TARGET)
	./$(TARGET)
```

### Nog steeds niet perfect maar buiten scope van deze cursus
Makefiles blijven een aantal beperkingen hebben waaronder dat wanneer je een CONSTANT in een header file aanpast, make niet noodzakelijk doorheeft dat je de file die gebruik maakt van de constant moet updaten. Dit valt op te lossen met wat 'elbow grease' aan de make-syntax maar dat valt buiten de scope van deze cursus. Later gaan we toch ook gebruik maken van meer advanced build systems.

## Oefening
1. Maak nu een Makefile voor de game van hierboven die dezelfde functionaliteit biedt als je gemaakte shell script.
<!-- EXSOL -->
<!-- <details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

```makefile
# Directories
SRC_DIR = src
BUILD_DIR = build

# Doel binary
TARGET = game.bin

# Compiler en flags
CC = gcc
CFLAGS = -Wall -Wextra -std=c11

# Alle bronbestanden en objectbestanden
SRCS = $(wildcard $(SRC_DIR)/*.c)
OBJS = $(patsubst $(SRC_DIR)/%.c,$(BUILD_DIR)/%.o,$(SRCS))

# Standaard doel: bouw de binary
all: $(TARGET)

# Link de objectbestanden tot de uiteindelijke binary
$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)

# Compileer .c naar .o en plaats deze in de build map
$(BUILD_DIR)/%.o: $(SRC_DIR)/%.c | $(BUILD_DIR)
	$(CC) $(CFLAGS) -c $< -o $@

# Zorg dat de build map bestaat
$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

# Geef ook de optie om 'compile' als een commando mee te geven
compile: $(TARGET)

# Voer de binary uit. Eventuele argumenten meegegeven met ARGS worden doorgegeven.
run: $(TARGET)
	./$(TARGET) $(ARGS)
# run as `make run ARGS="--your-flags"`

# Verwijder de binary en de build directory
clean:
	rm -f $(TARGET)
	rm -rf $(BUILD_DIR)
```

</p>
</details> -->

2. Breid de functionaliteit van je spel verder uit, door nieuwe Monsters te spawnen wanneer je een monster verslaat. Hou dan ook bij hoeveel monsters je verslagen hebt. Dat is je uitendelijke score wanneer je sterft.
    - Zoek op hoe je de library `cJSON` downloaden, toevoegen aan je applicatie en kan gebruiken in de `main.c` file.
    - Laat wanneer je sterft de applicatie de naam van de speler vragen en een JSON object aanmaken van de speler met naam en score en dit toevoegen aan een `/resources/highscore.json` bestand.
    - Voeg aan het begin van de game toe dat de huidige highscores van de json file geladen worden en getoond worden aan de speler.
    - _(Geniet hierbij van het feit dat je een gemakkelijke makefile hebt om snel wijzigingen aan de code te testen.)_
    - _De `cJSON`-library is een voorbeeld van een dependency, we gaan hier nog dieper over in in het deel rond 'Dependency management'_

<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen van een voorbeeld <code>main.c</code> programma dat gebruik maakt van cJSON</b></i>🔽</summary>
<p>

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cJSON.h"

// Function to read the JSON file
char *read_file(const char *filename)
{
  FILE *file = fopen(filename, "rb");
  if (!file)
  {
    return NULL;
  }
  fseek(file, 0, SEEK_END);
  long length = ftell(file);
  fseek(file, 0, SEEK_SET);
  char *data = (char *)malloc(length + 1);
  fread(data, 1, length, file);
  data[length] = '\0';
  fclose(file);
  return data;
}

// Function to write the JSON file
void write_file(const char *filename, const char *data)
{
  FILE *file = fopen(filename, "wb");
  if (!file)
  {
    perror("File opening failed");
    return;
  }
  fwrite(data, 1, strlen(data), file);
  fclose(file);
}

int main()
{
  const char *filename = "scores.json";

  // Read the JSON file
  char *json_data = read_file(filename);
  cJSON *root;

  if (!json_data)
  {
    // File does not exist, create a new JSON object
    root = cJSON_CreateArray();
  }
  else
  {
    // Parse the existing JSON data
    root = cJSON_Parse(json_data);
    if (!root)
    {
      printf("Error parsing JSON data\n");
      free(json_data);
      return 1;
    }
    free(json_data);
  }

  // Create a new JSON object to add
  cJSON *new_entry = cJSON_CreateObject();
  cJSON_AddStringToObject(new_entry, "name", "Jane Doe");
  cJSON_AddNumberToObject(new_entry, "score", 95);

  // Add the new entry to the JSON array
  cJSON_AddItemToArray(root, new_entry);

  // Convert JSON object to string
  char *updated_json_data = cJSON_Print(root);

  // Write the updated JSON data back to the file
  write_file(filename, updated_json_data);

  // Clean up
  cJSON_Delete(root);
  free(updated_json_data);

  return 0;
}
```

</p>
</details>

<!-- EXSOL -->
<!-- <details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

</p>
</details> -->


## Interessante bronnen
- [Understanding C program Compilation Process](https://www.youtube.com/watch?v=VDslRumKvRA&t=21s)

