---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
author: "Koen Yskout"
draft: false
---

Alle oefeningen moeten opgelost worden **zonder for- of while-lussen**.
Gebruik waar mogelijk methode-referenties.

## Personen

We werken met een dataset van personen, onderverdeeld in volwassenen en kinderen:

```java
interface Person {
    String firstName();
    String lastName();
    int age();
    String zipCode();
}
record Adult(String firstName,
             String lastName,
             int age,
             String zipCode,
             List<Child> children) implements Person {}
record Child(String firstName,
             String lastName,
             int age,
             String zipCode) implements Person {}
```

De dataset vind je in `Data.java`.

{{% notice type=code title="Data.java" expanded=false %}}
```java
package streams.exercises.exercise1;

import java.util.Collections;
import java.util.List;

public class Data {
    public interface Person { String firstName(); String lastName(); int age(); String zipCode(); }
    public record Adult(String firstName, String lastName, int age, String zipCode, List<Child> children) implements Person {}
    public record Child(String firstName, String lastName, int age, String zipCode) implements Person {}

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
            new Adult("Steven", "Adams", 36, "56789", Collections.emptyList()),
            new Adult("Olivia", "Brown", 41, "13579", List.of(
                    new Child("Noah", "Brown", 12, "13579"),
                    new Child("Nina", "Brown", 12, "13579"))),
            new Adult("Ethan", "Moore", 38, "24680", List.of(
                    new Child("Mila", "Moore", 4, "24680"))),
            new Adult("Emma", "Baker", 33, "13579", Collections.emptyList()),
            new Adult("Lucas", "Carter", 47, "97531", List.of(
                    new Child("Sam", "Carter", 8, "97531"),
                    new Child("Iris", "Carter", 15, "97531"))),
            new Adult("Ava", "Turner", 27, "86420", List.of(
                    new Child("Leo", "Turner", 1, "86420"))),
            new Adult("Noah", "Phillips", 52, "24680", Collections.emptyList()),
            new Adult("Mia", "Campbell", 31, "97531", List.of(
                    new Child("Ben", "Campbell", 6, "97531"),
                    new Child("Lena", "Campbell", 6, "97531"))),
            new Adult("Liam", "Parker", 44, "11111", List.of(
                    new Child("Owen", "Parker", 7, "11111"),
                    new Child("Luca", "Parker", 9, "11111"),
                    new Child("Emma", "Parker", 11, "11111"))),
            new Adult("Chloe", "Evans", 23, "22222", Collections.emptyList()),
            new Adult("Mason", "Edwards", 39, "33333", List.of(
                    new Child("Aya", "Edwards", 13, "33333"))),
            new Adult("Ella", "Collins", 55, "44444", List.of(
                    new Child("Finn", "Collins", 17, "44444"))),
            new Adult("Aria", "Stewart", 36, "55555", List.of(
                    new Child("Eli", "Stewart", 5, "55555"),
                    new Child("Ivy", "Stewart", 8, "55555"))),
            new Adult("Finn", "Sanchez", 28, "66666", Collections.emptyList()),
            new Adult("Nora", "Morris", 42, "77777", List.of(
                    new Child("Mia", "Morris", 7, "77777"),
                    new Child("Max", "Morris", 14, "77777"))),
            new Adult("Owen", "Rogers", 34, "88888", List.of(
                    new Child("Lia", "Rogers", 3, "88888")))
    );
}
```
{{% /notice %}}

In `Main.java` vind je een skeleton voor alle oefeningen.

{{% notice type=code title="Main.java" expanded=false %}}
```java
package streams.exercises.exercise1;

import java.util.*;
import java.util.function.Function;
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
        exercise17();
        exercise18();
        exercise19();
        exercise20();
        exercise21();
        exercise22();
        exercise23();
        exercise24();
        exercise25();
        exercise26();
        exercise27();
    }

    private static void exercise01() { /* TODO */ }
    private static void exercise02() { /* TODO */ }
    private static void exercise03() { /* TODO */ }
    private static void exercise04() { /* TODO */ }
    private static void exercise05() { /* TODO */ }
    private static void exercise06() { /* TODO */ }

    private static void exercise07() { /* TODO */ }
    private static void exercise08() { /* TODO */ }
    private static void exercise09() { /* TODO */ }
    private static void exercise10() { /* TODO */ }
    private static void exercise11() { /* TODO */ }
    private static void exercise12() { /* TODO */ }
    private static void exercise13() { /* TODO */ }

    private static void exercise14() { /* TODO */ }
    private static void exercise15() { /* TODO */ }
    private static void exercise16() { /* TODO */ }
    private static void exercise17() { /* TODO */ }
    private static void exercise18() { /* TODO */ }
    private static void exercise19() { /* TODO */ }
    private static void exercise20() { /* TODO */ }

    private static void exercise21() { /* TODO */ }
    private static void exercise22() { /* TODO */ }
    private static void exercise23() { /* TODO */ }
    private static void exercise24() { /* TODO */ }
    private static void exercise25() { /* TODO */ }
    private static void exercise26() { /* TODO */ }
    private static void exercise27() { /* TODO */ }

    public static <K, V, R> Map<K, R> mapAllValues(Map<? extends K, ? extends V> map, Function<? super V, ? extends R> function) {
        // TODO
        return Map.of();
    }

    // Hulpfunctie om het resultaat van een oefening te printen
    private static void print(Object value) {
        var s = Arrays.stream(Thread.currentThread().getStackTrace())
                      .dropWhile(m -> !m.getMethodName().startsWith("ex"))
                      .findFirst().get();
        if (value instanceof Optional<?> o) {
            value = o.orElse("Nothing found.");
        } else if (value instanceof OptionalDouble o) {
            value = o.isPresent() ? o.getAsDouble() : "Nothing found.";
        }
        System.out.println("\n- " + s.getMethodName() + ":\n" +
            value.toString().lines().map(l -> "  " + l).collect(Collectors.joining("\n")));
    }
}
```
{{% /notice %}}

### Basis

- **1.** Print alle voor- en achternamen van de volwassenen in de dataset.
  
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<String> exercise01(List<Adult> dataset) {
      return dataset.stream().map(a -> a.firstName() + " " + a.lastName()).toList();
  }
  ```

  {{% /notice %}}

- **2.** Ga na dat alle volwassenen in de dataset inderdaad ouder dan 18 zijn.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static boolean exercise02(List<Adult> dataset) {
      return dataset.stream().allMatch(a -> a.age() >= 18);
  }
  ```
  
  {{% /notice %}}

- **3.** Ga na of er minstens één volwassene in de dataset zit waarvan de voornaam `"Joseph"` is.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static boolean exercise03(List<Adult> dataset) {
      return dataset.stream().anyMatch(a -> a.firstName().equals("Joseph"));
  }
  ```
  
  {{% /notice %}}

- **4.** Geef enkele statistieken (minimum, maximum, gemiddelde) van de leeftijd van alle volwassenen in de dataset die ten minste 30 jaar oud zijn.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static IntSummaryStatistics exercise04(List<Adult> dataset) {
      return dataset.stream().mapToInt(Adult::age).filter(age -> age >= 30).summaryStatistics();
  }
  ```
  
  {{% /notice %}}

- **5.** Zoek de volwassene met de langste achternaam.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Optional<Adult> exercise05(List<Adult> dataset) {
      return dataset.stream().max(Comparator.comparingInt(a -> a.lastName().length()));
  }
  ```
  
  {{% /notice %}}

- **6.** Groepeer alle volwassenen in een `Map` volgens hun postcode.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, List<Adult>> exercise06(List<Adult> dataset) {
      return dataset.stream().collect(Collectors.groupingBy(Adult::zipCode));
  }
  ```
  
  {{% /notice %}}

### Eenvoudig

- **7.** Geef een String met de 5 oudste volwassenen terug, in het formaat `"voornaam achternaam leeftijd"`, gesorteerd volgens voornaam, 1 persoon per lijn.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static String exercise07(List<Adult> dataset) {
      return dataset.stream()
              .sorted(Comparator.comparingInt(Adult::age).reversed())
              .limit(5)
              .sorted(Comparator.comparing(Adult::firstName))
              .map(a -> a.firstName() + " " + a.lastName() + " " + a.age())
              .collect(Collectors.joining("\n"));
  }
  ```
  
  {{% /notice %}}

- **8.** Tel het totaal aantal kinderen in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static long exercise08(List<Adult> dataset) {
      return dataset.stream().mapToLong(a -> a.children().size()).sum();
  }
  ```
  
  {{% /notice %}}

- **9.** Tel het aantal volwassenen met kinderen in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static long exercise09(List<Adult> dataset) {
      return dataset.stream().filter(a -> !a.children().isEmpty()).count();
  }
  ```
  
  {{% /notice %}}

- **10.** Geef het minimum en maximum aantal kinderen dat een volwassene heeft.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static IntSummaryStatistics exercise10(List<Adult> dataset) {
      return dataset.stream().mapToInt(a -> a.children().size()).summaryStatistics();
  }
  ```
  
  {{% /notice %}}

- **11.** Zoek alle volwassenen die precies 6 keer zo oud zijn als een van hun kinderen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Adult> exercise11(List<Adult> dataset) {
      return dataset.stream()
              .filter(a -> a.children().stream().anyMatch(c -> a.age() == 6 * c.age()))
              .toList();
  }
  ```
  
  {{% /notice %}}

- **12.** Maak een lijst van alle kind-objecten in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Child> exercise12(List<Adult> dataset) {
      return dataset.stream().flatMap(a -> a.children().stream()).toList();
  }
  ```
  
  {{% /notice %}}

- **13.** Maak een gesorteerde lijst met alle unieke voornamen van alle kinderen in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<String> exercise13(List<Adult> dataset) {
      return dataset.stream()
              .flatMap(a -> a.children().stream())
              .map(Child::firstName)
              .distinct()
              .sorted()
              .toList();
  }
  ```
  
  {{% /notice %}}

### Moeilijk

- **14.** Bereken de gemiddelde leeftijd van alle kinderen in de dataset die een ouder hebben die ouder is dan 40.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static OptionalDouble exercise14(List<Adult> dataset) {
      return dataset.stream().filter(a -> a.age() > 40).flatMap(a -> a.children().stream()).mapToInt(Child::age).average();
  }
  ```
  
  {{% /notice %}}

- **15.** Maak een lijst van alle personen (volwassenen + kinderen) in de dataset, gesorteerd volgens voornaam en dan achternaam.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Person> exercise15(List<Adult> dataset) {
      return Stream.concat(
              dataset.stream().map(a -> (Person) a),
              dataset.stream().flatMap(a -> a.children().stream().map(c -> (Person) c))
      ).sorted(Comparator.comparing(Person::firstName).thenComparing(Person::lastName)).toList();
  }
  ```
  
  {{% /notice %}}

- **16.** Bereken de gemiddelde leeftijd van alle personen (volwassenen + kinderen) in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static OptionalDouble exercise16(List<Adult> dataset) {
      return Stream.concat(
              dataset.stream().map(a -> (Person) a),
              dataset.stream().flatMap(a -> a.children().stream().map(c -> (Person) c))
      ).mapToInt(Person::age).average();
  }
  ```
  
  {{% /notice %}}

- **17.** Bepaal het aantal unieke postcodes van alle volwassenen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static long exercise17(List<Adult> dataset) {
      return dataset.stream().map(Adult::zipCode).distinct().count();
  }
  ```
  
  {{% /notice %}}

- **18.** Maak een `Map<String, Long>` die voor elke postcode het aantal volwassenen bevat.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, Long> exercise18(List<Adult> dataset) {
      return dataset.stream().collect(Collectors.groupingBy(Adult::zipCode, Collectors.counting()));
  }
  ```
  
  {{% /notice %}}

- **19.** Geef een alfabetisch gesorteerde lijst van alle achternamen die minstens 2 keer voorkomen in de volwassenenlijst.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<String> exercise19(List<Adult> dataset) {
      return dataset.stream()
              .collect(Collectors.groupingBy(Adult::lastName, Collectors.counting()))
              .entrySet().stream()
              .filter(e -> e.getValue() >= 2)
              .map(Map.Entry::getKey)
              .sorted()
              .toList();
  }
  ```
  
  {{% /notice %}}

- **20.** Zoek de jongste volwassene zonder kinderen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Optional<Adult> exercise20(List<Adult> dataset) {
      return dataset.stream().filter(a -> a.children().isEmpty()).min(Comparator.comparingInt(Adult::age));
  }
  ```
  
  {{% /notice %}}

### Extra uitdagend

- **21.** Geef per volwassene het aantal kinderen terug in een `Map<Adult, Integer>`.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<Adult, Integer> exercise21(List<Adult> dataset) {
      return dataset.stream().collect(Collectors.toMap(Function.identity(), a -> a.children().size()));
  }
  ```
  
  {{% /notice %}}

- **22.** Maak een `Map<String, Long>` met de frequentie van kind-voornamen (case-insensitive), bijvoorbeeld `"alice"` en `"Alice"` samen tellen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, Long> exercise22(List<Adult> dataset) {
      return dataset.stream()
              .flatMap(a -> a.children().stream())
              .map(c -> c.firstName().toLowerCase(Locale.ROOT))
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }
  ```
  
  {{% /notice %}}

- **23.** Bepaal de top 3 postcodes met het grootste totaal aantal kinderen, gesorteerd van hoog naar laag.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Map.Entry<String, Integer>> exercise23(List<Adult> dataset) {
      return dataset.stream()
              .collect(Collectors.groupingBy(Adult::zipCode, Collectors.summingInt(a -> a.children().size())))
              .entrySet().stream()
              .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
              .limit(3)
              .toList();
  }
  ```
  
  {{% /notice %}}

- **24.** Zoek alle gezinnen waarin minstens twee kinderen dezelfde leeftijd hebben.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Adult> exercise24(List<Adult> dataset) {
      return dataset.stream()
              .filter(a -> a.children().stream()
                      .collect(Collectors.groupingBy(Child::age, Collectors.counting()))
                      .values().stream().anyMatch(count -> count >= 2))
              .toList();
  }
  ```
  
  {{% /notice %}}

- **25.** Maak een lijst van strings in de vorm `"ZIP -> gemiddelde leeftijd kinderen"` voor alle postcodes waarvoor minstens één kind bestaat, gesorteerd op postcode.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<String> exercise25(List<Adult> dataset) {
      Map<String, Double> avgByZip = dataset.stream()
              .filter(a -> !a.children().isEmpty())
              .collect(Collectors.groupingBy(Adult::zipCode,
                      Collectors.flatMapping(a -> a.children().stream(), Collectors.averagingInt(Child::age))));

      return avgByZip.entrySet().stream()
              .sorted(Map.Entry.comparingByKey())
              .map(e -> String.format(Locale.ROOT, "%s -> %.2f", e.getKey(), e.getValue()))
              .toList();
  }
  ```
  
  {{% /notice %}}

- **26.** Schrijf een methode `oldestChildPerZipCode(...)` die voor elke postcode het oudste kind teruggeeft als `Map<String, Child>`.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, Child> exercise26(List<Adult> dataset) {
      return dataset.stream()
              .flatMap(a -> a.children().stream().map(c -> Map.entry(a.zipCode(), c)))
              .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (c1, c2) -> c1.age() >= c2.age() ? c1 : c2
              ));
  }
  ```
  
  {{% /notice %}}

- **27.** Schrijf, gebruik makend van streams, een generische methode `mapAllValues(map, function)` die een nieuwe Map maakt waarbij alle values vervangen zijn door de gegeven functie erop toe te passen. Denk na over geschikte grenzen voor je generische parameters (PECS).
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static <K, V, R> Map<K, R> mapAllValues(Map<? extends K, ? extends V> map,
                                                  Function<? super V, ? extends R> function) {
      return map.entrySet().stream()
              .collect(Collectors.toMap(Map.Entry::getKey, e -> function.apply(e.getValue())));
  }
  ```
  
  {{% /notice %}}

## Tests (AssertJ)

{{% notice type=code title="StreamsExercisesTest.java" expanded=false %}}
```java
package streams.exercises.exercise1;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static streams.exercises.exercise1.Data.*;

public class StreamsExercisesTest {

    private final List<Adult> dataset = Data.DATASET;

    @Test
    void exercise01_printAdultNames() {
        var result = Solutions.exercise01(dataset);
        assertThat(result).hasSize(dataset.size()).contains("John Doe");
    }

    @Test
    void exercise02_allAdultsOlderThan18() {
        assertThat(Solutions.exercise02(dataset)).isTrue();
    }

    @Test
    void exercise03_containsJoseph() {
        assertThat(Solutions.exercise03(dataset)).isTrue();
    }

    @Test
    void exercise04_statsForAdultsAtLeast30() {
        var stats = Solutions.exercise04(dataset);
        assertThat(stats.getCount()).isGreaterThan(0);
        assertThat(stats.getMin()).isGreaterThanOrEqualTo(30);
        assertThat(stats.getMax()).isGreaterThanOrEqualTo(stats.getMin());
    }

    @Test
    void exercise05_longestLastName() {
        var result = Solutions.exercise05(dataset);
        int expectedMaxLength = dataset.stream().mapToInt(a -> a.lastName().length()).max().orElseThrow();
        assertThat(result).isPresent();
        assertThat(result.orElseThrow().lastName().length()).isEqualTo(expectedMaxLength);
    }

    @Test
    void exercise06_groupByZip() {
        var result = Solutions.exercise06(dataset);
        assertThat(result.values().stream().flatMap(List::stream).toList())
                .containsExactlyInAnyOrderElementsOf(dataset);
    }

    @Test
    void exercise07_top5OldestFormatted() {
        var result = Solutions.exercise07(dataset);
        assertThat(result.lines().count()).isEqualTo(5);
    }

    @Test
    void exercise08_totalChildren() {
        var result = Solutions.exercise08(dataset);
        var expected = dataset.stream().mapToLong(a -> a.children().size()).sum();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void exercise09_countAdultsWithChildren() {
        var result = Solutions.exercise09(dataset);
        var expected = dataset.stream().filter(a -> !a.children().isEmpty()).count();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void exercise10_minMaxChildrenPerAdult() {
        var stats = Solutions.exercise10(dataset);
        assertThat(stats.getMin()).isGreaterThanOrEqualTo(0);
        assertThat(stats.getMax()).isGreaterThanOrEqualTo(stats.getMin());
    }

    @Test
    void exercise11_adultsSixTimesOlderThanChild() {
        var result = Solutions.exercise11(dataset);
        assertThat(result.stream().allMatch(a -> a.children().stream().anyMatch(c -> a.age() == 6 * c.age()))).isTrue();
    }

    @Test
    void exercise12_allChildrenList() {
        var result = Solutions.exercise12(dataset);
        long expectedSize = dataset.stream().mapToLong(a -> a.children().size()).sum();
        assertThat(result).hasSize((int) expectedSize);
    }

    @Test
    void exercise13_uniqueSortedChildFirstNames() {
        var result = Solutions.exercise13(dataset);
        assertThat(result).isSorted();
        assertThat(new HashSet<>(result)).hasSize(result.size());
    }

    @Test
    void exercise14_avgAgeChildrenOfParentsOlderThan40() {
        var result = Solutions.exercise14(dataset);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.orElseThrow()).isBetween(0.0, 18.0);
    }

    @Test
    void exercise15_allPersonsSorted() {
        var result = Solutions.exercise15(dataset);
        long expectedSize = dataset.size() + dataset.stream().mapToLong(a -> a.children().size()).sum();
        assertThat(result).hasSize((int) expectedSize);
        assertThat(result).isSortedAccordingTo(Comparator
                .comparing(Person::firstName)
                .thenComparing(Person::lastName));
    }

    @Test
    void exercise16_avgAgeAllPersons() {
        var result = Solutions.exercise16(dataset);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.orElseThrow()).isGreaterThan(0);
    }

    @Test
    void exercise17_uniqueZipCount() {
        var result = Solutions.exercise17(dataset);
        long expected = dataset.stream().map(Adult::zipCode).distinct().count();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void exercise18_zipToAdultCount() {
        var result = Solutions.exercise18(dataset);
        assertThat(result.values().stream().mapToLong(Long::longValue).sum()).isEqualTo(dataset.size());
    }

    @Test
    void exercise19_lastNamesAppearingTwiceOrMore() {
        var result = Solutions.exercise19(dataset);
        assertThat(result).isSorted();
        assertThat(result.stream().allMatch(name ->
                dataset.stream().filter(a -> a.lastName().equals(name)).count() >= 2)).isTrue();
    }

    @Test
    void exercise20_youngestAdultWithoutChildren() {
        var result = Solutions.exercise20(dataset);
        int expected = dataset.stream().filter(a -> a.children().isEmpty()).mapToInt(Adult::age).min().orElseThrow();
        assertThat(result).isPresent();
        assertThat(result.orElseThrow().children()).isEmpty();
        assertThat(result.orElseThrow().age()).isEqualTo(expected);
    }

    @Test
    void exercise21_mapAdultToNbChildren() {
        var result = Solutions.exercise21(dataset);
        assertThat(result).hasSize(dataset.size());
        assertThat(result.entrySet().stream().allMatch(e -> e.getValue() == e.getKey().children().size())).isTrue();
    }

    @Test
    void exercise22_childFirstNameFrequencyCaseInsensitive() {
        var result = Solutions.exercise22(dataset);
        long expectedTotal = dataset.stream().mapToLong(a -> a.children().size()).sum();
        assertThat(result.keySet().stream().allMatch(k -> k.equals(k.toLowerCase(Locale.ROOT)))).isTrue();
        assertThat(result.values().stream().mapToLong(Long::longValue).sum()).isEqualTo(expectedTotal);
    }

    @Test
    void exercise23_top3ZipCodesByTotalChildren() {
        var result = Solutions.exercise23(dataset);
        assertThat(result).hasSizeLessThanOrEqualTo(3);
        assertThat(result).isSortedAccordingTo(Map.Entry.<String, Integer>comparingByValue().reversed());
    }

    @Test
    void exercise24_familiesWithSameAgeChildren() {
        var result = Solutions.exercise24(dataset);
        assertThat(result.stream().allMatch(a ->
                a.children().stream()
                        .collect(java.util.stream.Collectors.groupingBy(Child::age, java.util.stream.Collectors.counting()))
                        .values().stream().anyMatch(c -> c >= 2))).isTrue();
    }

    @Test
    void exercise25_zipToAverageChildAgeStrings() {
        var result = Solutions.exercise25(dataset);
        assertThat(result).allMatch(s -> s.matches("\\d{5} -> \\d+\\.\\d{2}"));
        assertThat(result).isSorted();
    }

    @Test
    void exercise26_oldestChildPerZipCode() {
        var result = Solutions.exercise26(dataset);
        assertThat(result).isNotEmpty();
        assertThat(result.entrySet().stream().allMatch(e ->
                e.getValue().age() == dataset.stream()
                        .filter(a -> a.zipCode().equals(e.getKey()))
                        .flatMap(a -> a.children().stream())
                        .mapToInt(Child::age)
                        .max()
                        .orElseThrow())).isTrue();
    }

    @Test
    void exercise27_mapAllValuesWithPecs() {
        Map<String, String> input = Map.of("first", "333", "second", "55555");
        Map<String, Integer> result = Solutions.mapAllValues(input, String::length);
        assertThat(result).containsEntry("first", 3).containsEntry("second", 5);

        Function<Object, Integer> fn = x -> x.toString().length();
        Map<Object, Integer> result2 = Solutions.mapAllValues(input, fn);
        assertThat(result2).containsEntry("first", 3).containsEntry("second", 5);
    }
}
```
{{% /notice %}}
