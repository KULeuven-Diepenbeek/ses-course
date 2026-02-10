---
title: "7.1 Java basics"
weight: 10
autonumbering: true
draft: true
---


Als je Java-kennis wat roestig is (of wanneer je meer ervaring hebt in een andere programmeertaal), kan je je Java-kennis even opfrissen aan de hand van [deze pagina](../../bijlagen/bijlageB_Java%20in%20een%20notendop.md).

## IntelliJ

We maken voor deze lessen geen gebruik van VSCode zoals in deel 1, maar schakelen over naar [Jetbrains IntelliJ IDEA](https://www.jetbrains.com/idea/), een van de vaakst gebruikte professionele Java IDE's.
De gratis Community Edition volstaat voor dit vak, maar je kan als student ook een gratis licentie voor de [Ultimate Edition aanvragen](https://www.jetbrains.com/community/education/#students).

Download en installeer IntelliJ op je machine.

In IntelliJ organiseer je je code in **projecten**.
Elk IntelliJ scherm heeft op elk moment één geopend (actief) project.

Binnen een project heb je één of meer **modules**.
Een module is een onderdeel van een software-project.
Elke module kan in een verschillende programmeertaal geschreven zijn, en/of met zijn eigen (specifieke) instellingen gecompileerd worden.
Elke module is dus onafhankelijk.

In deze cursus zullen we **voor elk onderwerp (~elke les) een afzonderlijk project** maken, en voor **elke oefening een aparte module** binnen dat project maken.
Dat zorgt ervoor dat je elke oefening onafhankelijk kan oplossen.
In combinatie met git zullen we **één repository per project** gebruiken.

Je opdracht maak je ook in een apart project (en aparte git repository); zie de instructies bij de opdrachten.

## Oefening 1: Hello world

Maak in IntelliJ, in een folder voor het vak naar keuze, een leeg project (`Empty project`) aan (dus *geen* Java-project!).
Gebruik een naam naar keuze (bijvoorbeeld `sessie01-basics`).
Creeer meteen ook een git-repository.

Maak binnen dat project een Java-module `oef01-helloworld`.
Zet het build system voorlopig op IntelliJ (later zullen we overschakelen naar Gradle).

Maak in de `src`-folder van deze module een nieuwe klasse `HelloWorld`, en kopieer volgend programma.
Voer het programma uit via de play-knop.

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
```