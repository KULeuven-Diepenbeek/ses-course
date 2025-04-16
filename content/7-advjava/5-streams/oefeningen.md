---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
author: "Koen Yskout"
draft: false
---

Alle oefeningen moeten opgelost worden **zonder for- of while-lussen**.

## Personen

We gaan werken met een dataset van personen, onderverdeeld in volwassenen en kinderen:

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

De dataset is dus een lijst van Adult-objecten, waar elk object (naast naam, leeftijd, postcode) ook een lijst van kinderen bevat.

{{% notice type=code title="Data.java" expanded=false %}}
```java
package streams.exercises.exercise1;

import java.util.Collections;
import java.util.List;

public class Data {
    public interface Person { String firstName(); String lastName(); int age(); String zipCode(); }
    public record Adult(String firstName, String lastName, int age, String zipCode, List<Child> children) implements Person {}
    public record Child(String firstName, String lastName, int age, String zipCode) implements Person {}

    public static void main(String[] args) {
        System.out.println(DATASET.stream().mapToInt(Person::age).summaryStatistics());
        System.out.println(DATASET.stream().flatMap(p -> p.children().stream()).mapToInt(Person::age).summaryStatistics());
    }

    public static final List<Adult> DATASET = List.of(
            new Adult("John", "Doe", 30, "12345", List.of(
                    new Child("Alice", "Doe", 5, "12345"),
                    new Child("Liam", "Doe", 7, "12345"))),
            new Adult("Jane", "Smith", 28, "23456", List.of(
                    new Child("Bob", "Smith", 3, "23456"),
                    new Child("Charlie", "Smith", 6, "23456"),
                    new Child("Daisy", "Smith", 8, "23456"))),
            new Adult("Michael", "Johnson", 35, "34567", List.of(
                    new Child("Daniel", "Johnson", 7, "34567"),
                    new Child("Sophia", "Johnson", 4, "34567"))),
            new Adult("Emily", "Davis", 32, "45678", List.of(
                    new Child("Emma", "Davis", 2, "45678"))),
            new Adult("David", "Wilson", 29, "56789", List.of(
                    new Child("Nora", "Wilson", 5, "56789"),
                    new Child("Evan", "Wilson", 10, "56789"))),
            new Adult("Sarah", "Martinez", 24, "67890", List.of(
                    new Child("Mia", "Martinez", 3, "67890"))),
            new Adult("Daniel", "Anderson", 31, "78901", List.of(
                    new Child("Olivia", "Anderson", 6, "78901"))),
            new Adult("Laura", "Taylor", 27, "89012", Collections.emptyList()),
            new Adult("James", "Thomas", 45, "90123", List.of(
                    new Child("Isaac", "Thomas", 9, "90123"),
                    new Child("Zoe", "Thomas", 13, "90123"),
                    new Child("Ava", "Thomas", 11, "90123"))),
            new Adult("Susan", "Jackson", 26, "01234", Collections.emptyList()),
            new Adult("Robert", "White", 50, "12345", List.of(
                    new Child("Tyler", "White", 14, "12345"))),
            new Adult("Linda", "Harris", 48, "23456", List.of(
                    new Child("Grace", "Harris", 8, "23456"))),
            new Adult("William", "Clark", 51, "34567", Collections.emptyList()),
            new Adult("Barbara", "Lewis", 34, "45678", List.of(
                    new Child("Jasper", "Lewis", 6, "45678"),
                    new Child("Lucy", "Lewis", 10, "45678"))),
            new Adult("Richard", "Robinson", 58, "56789", Collections.emptyList()),
            new Adult("Mary", "Walker", 60, "67890", List.of(
                    new Child("Ella", "Walker", 5, "67890"),
                    new Child("Ruby", "Walker", 7, "67890"))),
            new Adult("Joseph", "Perez", 29, "78901", Collections.emptyList()),
            new Adult("Karen", "Hall", 43, "89012", List.of(
                    new Child("Gavin", "Hall", 13, "89012"))),
            new Adult("Paul", "Young", 22, "90123", Collections.emptyList()),
            new Adult("Lisa", "Allen", 37, "01234", List.of(
                    new Child("Harper", "Allen", 9, "01234"))),
            new Adult("Kevin", "King", 33, "12345", Collections.emptyList()),
            new Adult("Nancy", "Wright", 29, "23456", List.of(
                    new Child("Mason", "Wright", 4, "23456"))),
            new Adult("George", "Scott", 40, "34567", Collections.emptyList()),
            new Adult("Betty", "Green", 30, "45678", Collections.emptyList()),
            new Adult("Steven", "Adams", 36, "56789", Collections.emptyList()));
}
```
{{% /notice %}}

In `Main.java` vind je al wat code voor de verschillende oefeningen.
Ook de dataset is al geïmporteerd.

{{% notice type=code title="Main.java" expanded=false %}}
```java
package streams.exercises.exercise1;

import java.util.*;
import java.util.stream.Collectors;
import static streams.exercises.exercise1.Data.DATASET;

public class Main {
    public static void main(String[] args) {
        exercise01();
        exercise02();
        exercise03();
        exercise04();
        exercise05();
        exercise06();
        exercise07();
        exercise08();
        exercise09();
        exercise10();
        exercise11();
        exercise12();
        exercise13();
        exercise14();
        exercise15();
        exercise16();
    }

    private static void exercise01() {
        // TODO
        // je kan print gebruiken om het resultaat af te drukken, bv.
        print(DATASET.stream().findFirst());
    }

    private static void exercise02() {
        // TODO
    }

    private static void exercise03() {
        // TODO
    }

    private static void exercise04() {
        // TODO
    }

    private static void exercise05() {
        // TODO
    }

    private static void exercise06() {
        // TODO
    }

    private static void exercise07() {
        // TODO
    }

    private static void exercise08() {
        // TODO
    }

    private static void exercise09() {
        // TODO
    }

    private static void exercise10() {
        // TODO
    }

    private static void exercise11() {
        // TODO
    }

    private static void exercise12() {
        // TODO
    }

    private static void exercise13() {
        // TODO
    }

    private static void exercise14() {
        // TODO
    }

    private static void exercise15() {
        // TODO
    }

    private static void exercise16() {
        // TODO
    }

    // Hulpfunctie om het resultaat van een oefening te printen
    // Voegt ook de oefening (naam van de methode) toe.
    private static void print(Object value) {
        var s = Arrays.stream(Thread.currentThread().getStackTrace()).dropWhile(m -> !m.getMethodName().startsWith("ex")).findFirst().get();
        if (value instanceof Optional<?> o) {
            if (o.isPresent())
                value = o.get();
            else
                value = "Nothing found.";
        } else if (value instanceof OptionalDouble o) {
            if (o.isPresent()) {
                value = o.getAsDouble();
            } else {
                value = "Nothing found.";
            }
        }
        System.out.println("\n- " + s.getMethodName() + ":\n" + value.toString().lines().map(l -> "  " + l).collect(Collectors.joining("\n")));
    }
}
```
{{% /notice %}}

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

## mapValues

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
