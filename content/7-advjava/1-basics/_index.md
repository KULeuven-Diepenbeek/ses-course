---
title: "7.1 Java basics"
weight: 10
autonumbering: true
draft: true
---


Als je Java-kennis wat roestig is (of wanneer je meer ervaring hebt in een andere programmeertaal), kan je je Java-kennis even opfrissen aan de hand van [deze pagina](../../bijlagen/bijlageB_Java%20in%20een%20notendop.md).

## IntelliJ

We maken voor de lessen in dit deel geen gebruik van VSCode, maar schakelen over naar [Jetbrains IntelliJ IDEA](https://www.jetbrains.com/idea/), een van de vaakst gebruikte professionele Java IDE's.
De gratis Community Edition volstaat voor dit vak, maar je kan als student ook een gratis licentie voor de [Ultimate Edition aanvragen](https://www.jetbrains.com/community/education/#students).

Download en installeer IntelliJ op je machine.

In IntelliJ organiseer je je code in **projecten**.
Elk IntelliJ scherm heeft op elk moment één geopend (actief) project.

Binnen een project heb je één of meer **modules**.
Een module is een onderdeel van een software-project.
Elke module kan in een verschillende programmeertaal geschreven zijn, en/of met zijn eigen (specifieke) instellingen gecompileerd worden.
Elke module is dus onafhankelijk.

In deel 2 van dit vak zullen we **voor elk onderwerp (~elke les) een afzonderlijk project** maken, en voor **elke oefening een aparte module** binnen dat project maken.
Dat zorgt ervoor dat je elke oefening onafhankelijk kan oplossen.
In combinatie met git zullen we **één repository per project** (en dus per onderwerp) gebruiken.

Je opdracht voor dit deel maak je ook in een apart project (en aparte git repository); zie de instructies bij de opdrachten.

## Oefening 1: Hello world

1. Maak in IntelliJ een leeg project (`Empty project`) aan (dus ***geen* Java-project**!) met volgende instellingen:

    - Type (*links in het scherm*): Empty Project
    - Name: naar keuze (bijvoorbeeld `sessie01-basics`)
    - Location: ergens op je Linux/WSL2 installatie (bijvoorbeeld een map `\\wsl.localhost\Ubuntu\home\youruser\ses-intellij`)
    - Create git repository: **aan**

    Klik op *Create*.

2. IntelliJ opent nu je project. Je kan de projectstructuur openen en sluiten door links op het map-icontje te drukken.

3. Maak in de root van het project eerst een nieuw leeg bestand `.noai`. Dit zet de AI-ondersteuning uit.

4. Voeg nu een nieuwe Java-module toe aan het project:

    - Type (*links in het scherm*): Java
    - Name: naar keuze (bijvoorbeeld `oef01-helloworld`)
    - Location: zou standaard goed moeten staan (locatie van het project)
    - Build system: IntelliJ (later zullen we overschakelen naar Gradle)
    - JDK: een recente versie (**25** of hoger)
    - Add sample code: **uit**

    Klik op *Create*.

    {{% notice info "Geen Java 25?" %}}

Als je WSL2-installatie geen Java 25 SDK bevat, kan je deze via een terminal installeren met

```bash
sudo apt update
sudo apt install openjdk-25-jdk
```

{{% /notice %}}

5. Je ziet de module nu verschijnen als subfolder van je project.

6. Maak in de `src`-folder van deze module een nieuwe klasse `HelloWorld`, en kopieer volgend programma.

    ```java
    public class HelloWorld {
        public static void main(String[] args) {
            IO.println("Hello world");
        }
    }
    ```

7. Voer het programma uit via de play-knop.

## Oefening 2: Basis Java

Met deze oefening fris je je geheugen over het gebruik van `if` en `for` nog eens op.

Maak een nieuwe Java-module `oef02-basis`.
Maak in die module een klasse `Opteller` die een getal `n` vraagt aan de gebruiker, 
vervolgens de som berekent van alle oneven getallen van 1 tot en met `n`, en tenslotte het resultaat afdrukt.

Bijvoorbeeld:

```bash
Geef een getal: 25
De som van de oneven getallen van 1 tot en met 25 is 169
```

Hint: gebruik `IO.readln()` en `IO.println()` om te lezen van en schrijven naar de console. Deze methodes zijn nieuw sinds Java 25.
Je kan een String omzetten naar een getal via `Integer.parseInt`.

{{% notice style=tip title="Oplossing" expanded=false %}}

```java
class Opteller {
    public static void main(String[] args) {
        int n = Integer.parseInt(IO.readln("Geef een getal: "));
        long som = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                som += i;
            }
        }
        IO.println("De som van de oneven getallen van 1 tot en met " + n + " is " + som);
    }
}
```

{{% /notice %}}

## Oefening 3: ArrayList

Met deze oefening fris je je geheugen over het gebruik van een ArrayList nog eens op.

Maak een nieuwe Java-module `oef03-arraylist`.
Maak in die module een klasse `Persoon` met een naam en leeftijd.
Maak ook een klasse `Programma` die aan de gebruiker steeds achtereenvolgens een naam en leeftijd vraagt, een object van klasse Persoon aanmaakt, en deze objecten bijhoudt in een ArrayList.
De invoer stopt wanneer de ingegeven naam leeg is.

Vervolgens moeten de gegevens van alle ingegeven personen uitgeprint worden.
Bijvoorbeeld:

```bash
Geef de naam van de volgende persoon: Jan
Geef de leeftijd van Jan: 25

Geef de naam van de volgende persoon: Marie
Geef de leeftijd van Marie: 28

Geef de naam van de volgende persoon: 
De ingegeven personen zijn:
- Jan (25 jaar)
- Marie (28 jaar)
```

{{% notice style=tip title="Oplossing" expanded=false %}}

```java
// Persoon.java
public class Persoon {
    private String naam;
    private int leeftijd;

    public Persoon(String naam, int leeftijd) {
        this.naam = naam;
        this.leeftijd = leeftijd;
    }

    @Override
    public String toString() {
        return naam + " (" + leeftijd + " jaar)";
    }
}
```

```java
// Programma.java
import java.util.ArrayList;

public class Programma {
    public static void main() {
        ArrayList<Persoon> personen = new ArrayList<>();
        String naam = IO.readln("Geef de naam van de volgende persoon: ");
        while (!naam.isBlank()) {
            int leeftijd = Integer.parseInt(IO.readln("Geef de leeftijd van " + naam + ": "));
            Persoon p = new Persoon(naam, leeftijd);
            personen.add(p);
            IO.println();
            naam = IO.readln("Geef de naam van de volgende persoon: ");
        }
        IO.println("De ingegeven personen zijn:");
        for (var p : personen) {
            IO.println("- " + p);
        }
    }
}
```

{{% /notice %}}

We gaan nu verder met [Java collecties](./collectionapi.md).