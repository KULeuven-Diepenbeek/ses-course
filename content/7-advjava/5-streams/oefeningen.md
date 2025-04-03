---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
author: "Koen Yskout"
draft: true
---

Alle oefeningen moeten opgelost worden **zonder for- of while-lussen**.

## Personen

We gaan werken met [een dataset van personen](https://github.com/KULeuven-Diepenbeek/ses-demos-exercises-student/tree/main/streams/src/main/java/streams/exercises/exercise1), onderverdeeld in volwassenen en kinderen:

```java
interface Person { String firstName(); String lastName(); int age(); String zipCode(); }
record Adult(String firstName, String lastName, int age, String zipCode, List<Child> children) implements Person {}
record Child(String firstName, String lastName, int age, String zipCode) implements Person {}
```

De dataset vind je in het bestand `Data.java`, en ziet er als volgt uit:

```java
public static final List<Adult> DATASET = List.of(
        new Adult("John", "Doe", 30, "12345", List.of(
                new Child("Alice", "Doe", 5, "12345"),
                new Child("Liam", "Doe", 7, "12345"))),
  ...
);
```

Los volgende oefeningen op met streams.

1. Print alle namen van de volwassenen in de dataset.
2. Ga na dat alle volwassenen in de dataset inderdaad ouder dan 18 zijn.
3. Ga na of er minstens één volwassene in de dataset zit waarvan de voornaam "Joseph" is.
4. Geef enkele statistieken (minimum, maximum, gemiddelde) van de leeftijd van alle volwassenen in de dataset die ten minste 30 jaar oud zijn.
5. Zoek de volwassene met de langste achternaam.
6. Groepeer alle volwassenen in een Map volgens hun postcode.
7. Geef een String met de 5 oudste volwassenen terug, in het formaat "voornaam achternaam leeftijd", gesorteerd volgens voornaam, 1 persoon per lijn.
8. Tel het totaal aantal kinderen in de dataset.
9. Tel het aantal volwassenen met kinderen in de dataset.
10. Geef het minimum en maximum aantal kinderen dat een volwassene heeft.
11. Zoek alle personen die precies 6 keer zo oud zijn als een van hun kinderen.
12. Maak een lijst van alle kind-objecten in de dataset.
13. Maak een gesorteerde lijst met alle unieke voornamen van alle kinderen in de dataset.
14. Bereken de gemiddelde leeftijd van alle kinderen in de dataset die een ouder hebben die ouder is dan 40.
15. Maak een lijst van alle personen (volwassenen + kinderen) in de dataset, gesorteerd volgens voornaam.
16. Bereken de gemiddelde leeftijd van alle personen (volwassenen + kinderen) in de dataset.

## Extra: mapValues

Schrijf, gebruik makend van streams, een generische methode `mapAllValues(map, function)` die je kan gebruiken om een nieuwe Map te maken waarbij alle waarden vervangen zijn door de gegeven functie erop toe te passen.
Bijvoorbeeld, in onderstaande code gebruiken we deze functie om alle String-values in de map te vervangen door hun lengte:

```java
Map<String, String> myMap = Map.of("first", "333", "second", "55555"); // => {first="333", second="55555"}
Map<String, Integer> result = mapAllValues(myMap, String::length);
System.out.println(result); // => {first=3, second=5}
```

_Hint: gebruik [`Collectors.toMap(...)`](<https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Collectors.html#toMap(java.util.function.Function,java.util.function.Function)>)_

Denk ook na over geschikte grenzen voor je generische parameters (PECS).
Ook hetvolgende zou moeten werken:

```java
Map<String, String> myMap = Map.of("first", "333", "second", "55555");
Function<Object, Integer> fn = (x) -> x.toString().length();
Map<Object, Object> result = mapAllValues(myMap, fn);
```
