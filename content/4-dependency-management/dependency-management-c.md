---
title: 'In C (met CMake)'
weight: 3
draft: true
author: Arne Duyver
---

## CMake
CMake is een open-source tool dat wordt gebruikt voor het beheren van de build processen van softwareprojecten in C en C++. Het biedt een platformonafhankelijke manier om build configuraties te genereren, wat betekent dat het kan worden gebruikt op verschillende besturingssystemen zoals Windows, macOS en Linux. CMake maakt gebruik van eenvoudige configuratiebestanden, genaamd **CMakeLists.txt** (soms meerdere van deze files in je project, je kan namelijk in meerdere directories zo een `CMakeLists.txt` file aanmaken), waarin de projectstructuur en de dependencies worden gedefinieerd. Deze configuratiebestanden worden vervolgens gebruikt om platformspecifieke buildsystemen te genereren, zoals **Makefiles op Unix-achtige systemen** (of Visual Studio-projectbestanden op Windows).

Een van de belangrijkste voordelen van CMake ten opzichte van het gebruik van alleen Makefiles is de **verbeterde ondersteuning voor dependency management**. Met Makefiles moet je handmatig de afhankelijkheden tussen bestanden en bibliotheken beheren, wat foutgevoelig en tijdrovend kan zijn. CMake automatiseert dit proces door dependencies automatisch te detecteren en te beheren. Dit zorgt ervoor dat alleen de noodzakelijke onderdelen opnieuw worden opgebouwd wanneer er wijzigingen worden aangebracht, wat de build tijd aanzienlijk kan verkorten.

Daarnaast biedt CMake een aantal geavanceerde functies die het beheren van dependencies verder vereenvoudigen. Zo ondersteunt CMake het gebruik van externe packages en libraries via de `find_package`-functie, waarmee je eenvoudig externe afhankelijkheden kunt integreren in je project. Ook biedt CMake ondersteuning voor moderne `C++`-functionaliteiten zoals target-gebaseerde builds, waarbij je specifieke build opties en dependencies kunt toewijzen aan individuele targets in plaats van aan het hele project. Hierdoor wordt het beheer van complexe projecten met meerdere componenten een stuk eenvoudiger en overzichtelijker. Deze functionaliteiten vallen echter buiten de scope van deze cursus.

Kortom, CMake biedt een krachtig en flexibel alternatief voor traditionele Makefiles, met name op het gebied van dependency management. Het automatiseert en vereenvoudigt het beheer van dependencies.

Je kan CMake simpel installeren op je WSL met behulp van volgend commando: `sudo apt install cmake -y`.

### 'CMakeLists.txt' en project structure
CMake werkt op basis van een `CMakeLists.txt`. Daarin specificeer je bepaalde instellingen over de structuur van je project en hoe je project juist gebuild en gelinkt moet worden. Verder kan je net als in `Makefiles` ook gebruik maken van variabelen om het proces nog meer te automatiseren. Je maakt volgens conventie ook een `./build` directory aan waarin dan alle build-files terecht zullen komen. Een van die build-files in een door CMake gegenereerde make file waarmee je dan simpelweg via het commando `make` je project effectief kan builden naar een binary die je kan uitvoeren. Hieronder bekijken we hoe zo een 'CMakeLists.txt' eruit ziet in de root van je project en welke functionaliteiten je hebt.

We starten met een zeer simpel project met een `main.c`-file, een `helloworld.c`-file en een `helloworld.h`-file:
<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor het kleine voorbeeld</b></i>🔽</summary>
<p>

```c
// 'main.c' file

#include "helloworld.h"
#include <stdio.h>

int main() {
    helloWorld();
    return 0;
}
```
```c
// 'helloworld.h' file

#ifndef HELLOWORLD_H
#define HELLOWORLD_H

void helloWorld();

#endif // HELLOWORLD_H
```
```c
// 'helloworld.c' file

#include "helloworld.h"
#include <stdio.h>

void helloWorld() {
    printf("Hello, World!\n");
}
```

</p>
</details>

De structuur van onze projectfolder ziet er dan als volgt uit waarbij `build` voorlopig een empty directory is:
```bash
cmake_example/
├── CMakeLists.txt
├── build
├── helloworld.c
├── helloworld.h
└── main.c
```

Een van de eenvoudigste `CMakeLists.txt` ziet er dan als volgt uit:
```bash
# Specify the minimum required version of CMake you need to use with this project. (Simply use your own version of CMake)
cmake_minimum_required(VERSION 3.28)

# Specify the project name
project(myproject)

# Specify: the name of the binary to build, the source files needed to build the binary
add_executable(myproject.bin main.c helloworld.c)
```

Nu voeren we volgens conventie het cmake commando uit in de `build`-directory zodat alle nodige build files, waaronder de `Makefile` aangemaakt worden in die directory: `cd ./build && cmake ..`

Dat resulteert in volgende files en folders in de `build`-directory waarvan we eigenlijk enkel geinteresserd zijn in de **Makefile**:
```bash
./build
├── CMakeCache.txt
├── CMakeFiles
│   ├── 3.28.3
│   │   ├── CMakeCCompiler.cmake
│   │   ├── CMakeCXXCompiler.cmake
│   │   ├── CMakeDetermineCompilerABI_C.bin
│   │   ├── CMakeDetermineCompilerABI_CXX.bin
│   │   ├── CMakeSystem.cmake
│   │   ├── CompilerIdC
│   │   │   ├── CMakeCCompilerId.c
│   │   │   ├── a.out
│   │   │   └── tmp
│   │   └── CompilerIdCXX
│   │       ├── CMakeCXXCompilerId.cpp
│   │       ├── a.out
│   │       └── tmp
│   ├── CMakeConfigureLog.yaml
│   ├── CMakeDirectoryInformation.cmake
│   ├── CMakeScratch
│   ├── Makefile.cmake
│   ├── Makefile2
│   ├── TargetDirectories.txt
│   ├── cmake.check_cache
│   ├── myproject.bin.dir
│   │   ├── DependInfo.cmake
│   │   ├── build.make
│   │   ├── cmake_clean.cmake
│   │   ├── compiler_depend.make
│   │   ├── compiler_depend.ts
│   │   ├── depend.make
│   │   ├── flags.make
│   │   ├── link.txt
│   │   └── progress.make
│   ├── pkgRedirects
│   └── progress.marks
├── Makefile
└── cmake_install.cmake
```

<details closed>
<summary><i>Klik hier om de code te zien/verbergen voor de contents in de <code>Makefile</code>. Het fijne is nu dat je niet perfect moet weten wat er gebeurt omdat we die verantwoordelijkheid nu bij CMake gelegd hebben</i>🔽</summary>
<p>

```makefile
# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.28

# Default target executed when no arguments are given to make.
default_target: all
.PHONY : default_target

# Allow only one "make -f Makefile2" at a time, but pass parallelism.
.NOTPARALLEL:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/arne/ses/cmake_example

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/arne/ses/cmake_example/build

#=============================================================================
# Targets provided globally by CMake.

# Special rule for the target edit_cache
edit_cache:
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --cyan "No interactive CMake dialog available..."
	/usr/bin/cmake -E echo No\ interactive\ CMake\ dialog\ available.
.PHONY : edit_cache

# Special rule for the target edit_cache
edit_cache/fast: edit_cache
.PHONY : edit_cache/fast

# Special rule for the target rebuild_cache
rebuild_cache:
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --cyan "Running CMake to regenerate build system..."
	/usr/bin/cmake --regenerate-during-build -S$(CMAKE_SOURCE_DIR) -B$(CMAKE_BINARY_DIR)
.PHONY : rebuild_cache

# Special rule for the target rebuild_cache
rebuild_cache/fast: rebuild_cache
.PHONY : rebuild_cache/fast

# The main all target
all: cmake_check_build_system
	$(CMAKE_COMMAND) -E cmake_progress_start /home/arne/ses/cmake_example/build/CMakeFiles /home/arne/ses/cmake_example/build//CMakeFiles/progress.marks
	$(MAKE) $(MAKESILENT) -f CMakeFiles/Makefile2 all
	$(CMAKE_COMMAND) -E cmake_progress_start /home/arne/ses/cmake_example/build/CMakeFiles 0
.PHONY : all

# The main clean target
clean:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/Makefile2 clean
.PHONY : clean

# The main clean target
clean/fast: clean
.PHONY : clean/fast

# Prepare targets for installation.
preinstall: all
	$(MAKE) $(MAKESILENT) -f CMakeFiles/Makefile2 preinstall
.PHONY : preinstall

# Prepare targets for installation.
preinstall/fast:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/Makefile2 preinstall
.PHONY : preinstall/fast

# clear depends
depend:
	$(CMAKE_COMMAND) -S$(CMAKE_SOURCE_DIR) -B$(CMAKE_BINARY_DIR) --check-build-system CMakeFiles/Makefile.cmake 1
.PHONY : depend

#=============================================================================
# Target rules for targets named myproject.bin

# Build rule for target.
myproject.bin: cmake_check_build_system
	$(MAKE) $(MAKESILENT) -f CMakeFiles/Makefile2 myproject.bin
.PHONY : myproject.bin

# fast build rule for target.
myproject.bin/fast:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/build
.PHONY : myproject.bin/fast

helloworld.o: helloworld.c.o
.PHONY : helloworld.o

# target to build an object file
helloworld.c.o:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/helloworld.c.o
.PHONY : helloworld.c.o

helloworld.i: helloworld.c.i
.PHONY : helloworld.i

# target to preprocess a source file
helloworld.c.i:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/helloworld.c.i
.PHONY : helloworld.c.i

helloworld.s: helloworld.c.s
.PHONY : helloworld.s

# target to generate assembly for a file
helloworld.c.s:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/helloworld.c.s
.PHONY : helloworld.c.s

main.o: main.c.o
.PHONY : main.o

# target to build an object file
main.c.o:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/main.c.o
.PHONY : main.c.o

main.i: main.c.i
.PHONY : main.i

# target to preprocess a source file
main.c.i:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/main.c.i
.PHONY : main.c.i

main.s: main.c.s
.PHONY : main.s

# target to generate assembly for a file
main.c.s:
	$(MAKE) $(MAKESILENT) -f CMakeFiles/myproject.bin.dir/build.make CMakeFiles/myproject.bin.dir/main.c.s
.PHONY : main.c.s

# Help Target
help:
	@echo "The following are some of the valid targets for this Makefile:"
	@echo "... all (the default if no target is provided)"
	@echo "... clean"
	@echo "... depend"
	@echo "... edit_cache"
	@echo "... rebuild_cache"
	@echo "... myproject.bin"
	@echo "... helloworld.o"
	@echo "... helloworld.i"
	@echo "... helloworld.s"
	@echo "... main.o"
	@echo "... main.i"
	@echo "... main.s"
.PHONY : help



#=============================================================================
# Special targets to cleanup operation of make.

# Special rule to run CMake to check the build system integrity.
# No rule that depends on this can have commands that come from listfiles
# because they might be regenerated.
cmake_check_build_system:
	$(CMAKE_COMMAND) -S$(CMAKE_SOURCE_DIR) -B$(CMAKE_BINARY_DIR) --check-build-system CMakeFiles/Makefile.cmake 0
.PHONY : cmake_check_build_system
```

</p>
</details>

**Nu gaan we van die `Makefile` gebruik maken om met het `make`-commando de binary effectief te builden: `make`. (zorg er natuurlijk wel voor dat je in de `./build`-directory zit!)**

Er is nu een binary verschenen in de `./build`-directory met de naam zoals gespecificeerd in de `CMakeLists.txt`-file, die je onmiddellijk kan uitvoeren.

### Dependency management met CMake en variables

We kunnen nu de CMake file aanpassen zodat de binary automatisch de naam van het project gebruikt door standaard variabelen te gebruiken:
```bash
add_executable(${CMAKE_PROJECT_NAME}.bin main.c helloworld.c)
```
Je kan CMake ook wat zelf wat extra info text laten printen met 
```c
message("Hallo vanuit CMake je CMAKE_PROJECT_NAME is '${CMAKE_PROJECT_NAME}'")
```

Verder kunnen we de `helloworld.c` en `helloworld.h` files beschouwen als een kleine **internal dependency** en kunnen we deze dus best in een soort `./libs/helloworld`-directory steken. Dan moeten we ook wel onze `CMakeLists.txt`-file aanpassen en BELANGRIJK in die `./libs/helloworld`-directory moeten we ook een eigen `CMakeLists.txt`-file aanmaken waarin je definieert hoe die dependency juist gebuild en gelinkt moet worden. Onze project folder ziet er nu dan als volgt uit:
```bash
cmake_example/
├── CMakeLists.txt
├── build
├── libs
│   └── helloworld
│       ├── CMakeLists.txt
│       ├── helloworld.c
│       └── helloworld.h
└── main.c
```
#### ./CMakeLists.txt
<details open>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./CMakeLists.txt</code></b></i>🔽</summary>
<p>

```bash
# Specify the minimum required version of CMake you need to use with this project. (Simply use your own version of CMake)
cmake_minimum_required(VERSION 3.28)

# Specify the project name
project(myproject)

# Add the helloworld directory to the project
add_subdirectory(libs/helloworld)

# Specify: the name of the binary to build, the source files needed to build the binary
# You don't need to specify the helloworld.c file here anymore, see below
add_executable(${CMAKE_PROJECT_NAME}.bin main.c)
message("Hallo vanuit CMake je CMAKE_PROJECT_NAME is '${CMAKE_PROJECT_NAME}'")

# Link internal library 'helloworld' to the main binary
target_link_libraries(${CMAKE_PROJECT_NAME}.bin PRIVATE helloworld)
```

</p>
</details>

**Belangrijk**: Merk op dat we de directory naar de library moeten toevoegen via `add_subdirectory()`, we niet langer de `helloworld.c` moeten vermelden bij de `add_executable()`-functie omdat we dit nu aangeven met de functie `target_link_libraries()`:
  - `target_link_libraries`: Dit is een CMake-commando dat wordt gebruikt om libraries te koppelen aan een specifieke target. Een target kan een executable of een library zijn die je wilt builden.
  - `${CMAKE_PROJECT_NAME}.bin`: Dit is de naam van de target waaraan je de library wilt koppelen. `${CMAKE_PROJECT_NAME}` is een variabele die de naam van het project bevat, en `.bin` is een extension die aangeeft dat het om een executable/binary bestand gaat.
  - `PRIVATE`: Dit is een eigenschap die aangeeft hoe de gekoppelde library zichtbaar is voor andere targets. In dit geval betekent PRIVATE dat de library alleen zichtbaar is voor de target `${CMAKE_PROJECT_NAME}.bin` en niet voor andere targets die afhankelijk zijn van deze target. Dit helpt om de dependencies te beperken en de build configuratie overzichtelijk te houden. Andere opties buiten 'PRIVATE' zijn: 'PUBLIC' en 'INTERFACE'.
    - `PUBLIC`: Wanneer je een library met de PUBLIC-eigenschap koppelt, betekent dit dat de library zichtbaar is voor zowel de target zelf als voor alle targets die afhankelijk zijn van deze target. Dit is handig wanneer je wilt dat de dependency doorgegeven wordt aan andere targets die deze target gebruiken.
    - `INTERFACE`: Deze eigenschap geeft aan dat de library alleen zichtbaar is voor targets die afhankelijk zijn van de target, maar niet voor de target zelf. Dit is nuttig voor het definiëren van interface-afhankelijkheden die alleen relevant zijn voor afhankelijke targets.
    - `PRIVATE`: Alleen zichtbaar voor de target zelf.
  - `helloworld`: Dit is de naam van de library die je wilt koppelen aan de target. In dit geval is helloworld waarschijnlijk een library die je hebt gedefinieerd of geïmporteerd in je project.

#### ./libs/helloworld/CMakeLists.txt
<details open>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./libs/helloworld/CMakeLists.txt</code></b></i>🔽</summary>
<p>

```bash
# Specify the minimum required version of CMake you need to use with this project. (Simply use your own version of CMake)
cmake_minimum_required(VERSION 3.28)

# Create a sort of temp project that compiles so it can be linked to the main project AND specify it is a C project
project(Helloworld C)

# Now add the compiled project binary as a library
add_library(helloworld helloworld.c)

# Include this directory's binary as a binary that can be used by the main project to link with
target_include_directories(helloworld PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
```

</p>
</details>

**Belangrijk**: Deze CMakeLists.txt file bevat essentiële instructies die samen zorgen dat de interne dependency correct wordt opgebouwd en bruikbaar is voor het main project. Allereerst definieert de `project(Helloworld C)` regel een nieuw project met de naam "Helloworld" en specificeert dat het in de programmeertaal C is geschreven, waardoor CMake de juiste compiler en instellingen selecteert. Met `add_library(helloworld helloworld.c)` wordt een statische of dynamische bibliotheek aangemaakt op basis van de broncode in "helloworld.c", zodat deze gecompileerde code later in het hoofdproject gelinkt kan worden. Tenslotte zorgt `target_include_directories(helloworld PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})` ervoor dat de directory waarin deze CMakeLists.txt en de headers staan, toegevoegd wordt aan de include-paden van alle targets die de "helloworld" library gebruiken. Hierdoor weet het main project waar de benodigde headers te vinden zijn, wat essentieel is voor een correcte compilatie en koppeling tussen de modules.

### Voorbeeld met externe library
Voor dit hoofdstuk is er geen oefening voorzien, maar wel een voorbeeld analoog aan [de oefening rond dependency management in Java](/4-dependency-management/dependency-management.md#opgave-2). In dit geval hebben we een "Higher Lower C" project waarbij we de volgende structuur hebben. De entrypoint van onze applicatie is de `main.c`-file, de `scorebord.c` en `scorebord.h` kunnen gezien worden als een interne library en `cJSON` is een externe library/dependency. Hieronder vind je dan een mogelijke structuur voor ons project terug. Je kan dit project en bijhorende files ook downloaden met volgende [github link](https://github.com/ArneDuyver/ses-dependency-management-c). 

```bash
higher_lower_c
├── CMakeLists.txt
├── README.md
├── build
├── internal
│   └── scorebord
│       ├── CMakeLists.txt
│       ├── player.h
│       ├── scorebord.c
│       └── scorebord.h
└── main.c
```

### Source files
_Wil je de content van de source files bekijken, kan je dat hieronder. Het is in dit hoofdstuk vooral belangrijk dat je begrijpt wat de regels in de verschillende `CMakeLists.txt` betekenen en wat het voordeel is om het op deze manier te doen._

#### source code: files in root
<details open>
  <summary><i><b>Klik hier om de code te zien/verbergen voor <code>CMakeLists.txt</code></b></i>🔽</summary>
  <p>

  ```bash
  cmake_minimum_required(VERSION 3.28)
  project(HogerLagerC)

  # Gebruik de C99-standaard
  set(CMAKE_C_STANDARD 99)
  set(CMAKE_C_STANDARD_REQUIRED ON)


  add_subdirectory(external/cJSON)

  # Voeg de scorebord library toe
  add_subdirectory(internal/scorebord)

  # Maak de executable van het spel
  add_executable(hoger_lager main.c)

  # Link de externe JSON-library
  target_link_libraries(hoger_lager PRIVATE scorebord cjson)
  ```

  </p>
</details>

<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>main.c</code></b></i>🔽</summary>
<p>

```c
/* main.c - Hoger Lager spel */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "scorebord.h"

int main(void)
{
  srand((unsigned)time(NULL));
  Scorebord *scorebord = load_scorebord("highscores.json");

  printf("Welkom bij het Hoger Lager spel!\n");
  printf("Dit is de huidige ranglijst:\n");
  print_scorebord(scorebord);
  printf("\nRaad of het volgende getal hoger of lager zal zijn.\n");

  int currentNumber = rand() % 100 + 1;
  int score = 0;
  char choice;

  while (1)
  {
    printf("Huidig getal: %d\n", currentNumber);
    printf("Zal het volgende hoger (h) of lager (l) zijn? ");
    scanf(" %c", &choice);

    int nextNumber = rand() % 100 + 1;
    if ((choice == 'h' && nextNumber > currentNumber) ||
        (choice == 'l' && nextNumber < currentNumber))
    {
      printf("Correct! Het volgende getal was: %d\n", nextNumber);
      score++;
    }
    else
    {
      printf("Fout! Het volgende getal was: %d\n", nextNumber);
      break;
    }
    currentNumber = nextNumber;
  }

  printf("Game over! Jouw eindscore: %d\n", score);
  printf("Wat is je naam? ");
  char name[50];
  scanf("%49s", name);

  add_score(scorebord, name, score);
  save_scorebord("highscores.json", scorebord);
  free_scorebord(scorebord);

  return 0;
}
```

</p>
</details>

#### source code: files in ./internal/scorebord

<details open>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./internal/scorebord/CMakeLists.txt</code></b></i>🔽</summary>
<p>

```bash
cmake_minimum_required(VERSION 3.28)
project(Scorebord C)

include(FetchContent)
FetchContent_Declare(
  cjson
  GIT_REPOSITORY https://github.com/DaveGamble/cJSON.git
  GIT_TAG v1.7.14 # Specify the version you want to use
)
FetchContent_MakeAvailable(cjson)

add_library(scorebord scorebord.c)

# Ensure cJSON include directories are correctly set
target_include_directories(scorebord PUBLIC ${CMAKE_CURRENT_SOURCE_DIR} ${cjson_SOURCE_DIR} ${cjson_BINARY_DIR})

# Link cJSON library to scorebord
target_link_libraries(scorebord PRIVATE cjson)
```

</p>
</details>

<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./internal/scorebord/scorebord.h</code></b></i>🔽</summary>
<p>

```c
#ifndef SCOREBORD_H
#define SCOREBORD_H

#include "player.h"

typedef struct {
    Player* players;
    int count;
    int capacity;
} Scorebord;

// Laadt de scorebordgegevens vanuit een JSON-bestand.
Scorebord* load_scorebord(const char* filename);

// Slaat de scorebordgegevens op in een JSON-bestand.
void save_scorebord(const char* filename, Scorebord* sb);

// Voegt een nieuwe score toe aan het scorebord.
void add_score(Scorebord* sb, const char* name, int score);

// Print het scorebord naar de standaard output.
void print_scorebord(Scorebord* sb);

// Geeft het geheugen van het scorebord vrij.
void free_scorebord(Scorebord* sb);

#endif // SCOREBORD_H
```

</p>
</details>

<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./internal/scorebord/scorebord.c</code></b></i>🔽</summary>
<p>

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "scorebord.h"
#include "cJSON.h" // Externe JSON-library: cJSON

// Hulpfunctie: maakt een nieuw Scorebord aan
static Scorebord *create_scorebord()
{
  Scorebord *sb = malloc(sizeof(Scorebord));
  if (sb)
  {
    sb->count = 0;
    sb->capacity = 10; // begin met ruimte voor 10 spelers
    sb->players = malloc(sb->capacity * sizeof(Player));
  }
  return sb;
}

Scorebord *load_scorebord(const char *filename)
{
  FILE *fp = fopen(filename, "r");
  if (!fp)
  {
    // Bestaat niet? Geef een nieuw scorebord terug.
    return create_scorebord();
  }

  // Lees het hele bestand in een buffer
  fseek(fp, 0, SEEK_END);
  long fsize = ftell(fp);
  fseek(fp, 0, SEEK_SET);

  char *data = malloc(fsize + 1);
  fread(data, 1, fsize, fp);
  fclose(fp);
  data[fsize] = '\0';

  cJSON *json = cJSON_Parse(data);
  free(data);

  Scorebord *sb = create_scorebord();
  if (json)
  {
    int arraySize = cJSON_GetArraySize(json);
    for (int i = 0; i < arraySize; i++)
    {
      cJSON *item = cJSON_GetArrayItem(json, i);
      cJSON *name = cJSON_GetObjectItem(item, "name");
      cJSON *score = cJSON_GetObjectItem(item, "score");
      if (cJSON_IsString(name) && cJSON_IsNumber(score))
      {
        add_score(sb, name->valuestring, score->valueint);
      }
    }
    cJSON_Delete(json);
  }

  return sb;
}

void save_scorebord(const char *filename, Scorebord *sb)
{
  cJSON *json = cJSON_CreateArray();
  for (int i = 0; i < sb->count; i++)
  {
    cJSON *item = cJSON_CreateObject();
    cJSON_AddStringToObject(item, "name", sb->players[i].name);
    cJSON_AddNumberToObject(item, "score", sb->players[i].score);
    cJSON_AddItemToArray(json, item);
  }

  char *rendered = cJSON_Print(json);
  FILE *fp = fopen(filename, "w");
  if (fp)
  {
    fputs(rendered, fp);
    fclose(fp);
  }
  free(rendered);
  cJSON_Delete(json);
}

void add_score(Scorebord *sb, const char *name, int score)
{
  if (sb->count >= sb->capacity)
  {
    sb->capacity *= 2;
    sb->players = realloc(sb->players, sb->capacity * sizeof(Player));
  }
  // Kopieer de naam (maximaal 49 karakters) en voeg de score toe
  strncpy(sb->players[sb->count].name, name, sizeof(sb->players[sb->count].name) - 1);
  sb->players[sb->count].name[sizeof(sb->players[sb->count].name) - 1] = '\0';
  sb->players[sb->count].score = score;
  sb->count++;
}

void print_scorebord(Scorebord *sb)
{
  // Eenvoudige weergave; je kunt hier sorteren op score toevoegen indien gewenst.
  for (int i = 0; i < sb->count; i++)
  {
    printf("%s: %d\n", sb->players[i].name, sb->players[i].score);
  }
}

void free_scorebord(Scorebord *sb)
{
  if (sb)
  {
    free(sb->players);
    free(sb);
  }
}
```

</p>
</details>

<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./internal/scorebord/player.h</code></b></i>🔽</summary>
<p>

```c
#ifndef PLAYER_H
#define PLAYER_H

typedef struct {
    char name[50];
    int score;
} Player;

#endif // PLAYER_H
```

</p>
</details>
