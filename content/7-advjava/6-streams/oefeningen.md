---
title: "Oefeningen"
toc: false
weight: 20
autonumbering: true
author: "Koen Yskout"
draft: false
---

Alle oefeningen moeten opgelost worden **zonder if-statements**, **zonder for- of while-lussen**, en **zonder extra variabelen**.
Elke methode bevat slechts één return-statement met daarachter een streams-pipeline.
Gebruik waar mogelijk methode-referenties.

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

Een IntelliJ project met de dataset (`Data.java`), het skelet voor de oefeningen (`Main.java`) en de tests vind je [op GitHub](https://github.com/KULeuven-Diepenbeek/ses-deel2-oefeningen-05-streams).

- **1.** ★ Geef een lijst terug met alle volledige namen (voor- en achternaam) van de volwassenen in de dataset.
  
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<String> exercise01(List<Adult> dataset) {
      return dataset.stream()
        .map(a -> a.firstName() + " " + a.lastName())
        .toList();
  }
  ```

  {{% /notice %}}

- **2.** ★ Ga na dat alle volwassenen in de dataset inderdaad ouder dan 18 zijn.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static boolean exercise02(List<Adult> dataset) {
      return dataset.stream()
        .allMatch(a -> a.age() >= 18);
  }
  ```
  
  {{% /notice %}}

- **3.** ★ Ga na of er minstens één volwassene in de dataset zit waarvan de voornaam `"Joseph"` is.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static boolean exercise03(List<Adult> dataset) {
      return dataset.stream()
        .anyMatch(a -> a.firstName().equals("Joseph"));
  }
  ```
  
  {{% /notice %}}

- **4.** ★ Geef enkele statistieken (minimum, maximum, gemiddelde) van de leeftijd van alle volwassenen in de dataset die ten minste 30 jaar oud zijn.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static IntSummaryStatistics exercise04(List<Adult> dataset) {
      return dataset.stream().mapToInt(Adult::age).filter(age -> age >= 30).summaryStatistics();
  }
  ```
  
  {{% /notice %}}

- **5.** ★★ Zoek de volwassene met de langste achternaam.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Optional<Adult> exercise05(List<Adult> dataset) {
      return dataset.stream().max(Comparator.comparingInt(a -> a.lastName().length()));
  }
  ```
  
  {{% /notice %}}

- **6.** ★ Groepeer alle volwassenen in een `Map` volgens hun postcode.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, List<Adult>> exercise06(List<Adult> dataset) {
      return dataset.stream().collect(Collectors.groupingBy(Adult::zipCode));
  }
  ```
  
  {{% /notice %}}

- **7.** ★★★ Geef een String met de 5 oudste volwassenen terug, in het formaat `"voornaam achternaam leeftijd"`, gesorteerd volgens voornaam, 1 persoon per lijn.
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

- **8.** ★ Tel het totaal aantal kinderen in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static long exercise08(List<Adult> dataset) {
      return dataset.stream().mapToLong(a -> a.children().size()).sum();
  }
  ```
  
  {{% /notice %}}

- **9.** ★ Tel het aantal volwassenen met kinderen in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static long exercise09(List<Adult> dataset) {
      return dataset.stream().filter(a -> !a.children().isEmpty()).count();
  }
  ```
  
  {{% /notice %}}

- **10.** ★ Geef het minimum en maximum aantal kinderen van alle volwassenen in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static IntSummaryStatistics exercise10(List<Adult> dataset) {
      return dataset.stream().mapToInt(a -> a.children().size()).summaryStatistics();
  }
  ```
  
  {{% /notice %}}

- **11.** ★★ Zoek alle volwassenen die precies 6 keer zo oud zijn als een van hun kinderen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Adult> exercise11(List<Adult> dataset) {
      return dataset.stream()
              .filter(a -> a.children().stream().anyMatch(c -> a.age() == 6 * c.age()))
              .toList();
  }
  ```
  
  {{% /notice %}}

- **12.** ★★ Maak een lijst van alle kind-objecten in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static List<Child> exercise12(List<Adult> dataset) {
      return dataset.stream().flatMap(a -> a.children().stream()).toList();
  }
  ```
  
  {{% /notice %}}

- **13.** ★★ Maak een gesorteerde lijst met alle unieke voornamen van alle kinderen in de dataset.

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

- **14.** ★★ Bereken de gemiddelde leeftijd van alle kinderen in de dataset die een ouder hebben die ouder is dan 40.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static OptionalDouble exercise14(List<Adult> dataset) {
      return dataset.stream()
                .filter(a -> a.age() > 40)
                .flatMap(a -> a.children().stream())
                .mapToInt(Child::age)
                .average();
  }
  ```
  
  {{% /notice %}}

- **15.** ★★★ Maak een lijst van alle personen (volwassenen + kinderen) in de dataset, gesorteerd volgens voornaam en dan achternaam.

    _Hint: `Stream.concat`_
    
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
    public static List<Data.Person> exercise15(List<Data.Adult> dataset) {
        return Stream.<Data.Person>concat(
                dataset.stream(),
                dataset.stream().flatMap(a -> a.children().stream()))
                .sorted(Comparator.comparing(Data.Person::firstName)
                                  .thenComparing(Data.Person::lastName))
                .toList();
    }
  ```

  Merk op dat we voor `concat` de generische parameter `<Data.Person>` moeten opgeven om het generisch type duidelijk te maken.
  
  {{% /notice %}}

- **16.** ★★ Bereken de gemiddelde leeftijd van alle personen (volwassenen + kinderen) in de dataset.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
    public static OptionalDouble exercise16(List<Data.Adult> dataset) {
        return Stream.concat(
                dataset.stream(),
                dataset.stream().flatMap(a -> a.children().stream()))
                .mapToInt(Data.Person::age)
                .average();
    }
  ```
  
  {{% /notice %}}

- **17.** ★ Bepaal het aantal unieke postcodes van alle volwassenen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static long exercise17(List<Adult> dataset) {
      return dataset.stream()
                .map(Adult::zipCode)
                .distinct()
                .count();
  }
  ```
  
  {{% /notice %}}

- **18.** ★★ Maak een `Map<String, Long>` die voor elke postcode het aantal volwassenen met die postcode bevat.
    _Hint: gebruik `Collectors.counting()` in combinatie met `groupingBy`_

  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, Long> exercise18(List<Adult> dataset) {
      return dataset.stream()
                .collect(Collectors.groupingBy(Adult::zipCode, Collectors.counting()));
  }
  ```
  
  {{% /notice %}}

- **19.** ★★★ Geef een alfabetisch gesorteerde lijst van alle achternamen van volwassenen die minstens 2 keer voorkomen in de lijst van volwassenen.
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

  Merk op dat `.collect(...).entrySet().stream()` inhoudt dat een tussentijdse datastructuur (een `Map`) gecreëerd wordt. Dit is dus niet één stream pipeline, maar zijn er twee na elkaar.
  
  {{% /notice %}}

- **20.** ★★ Zoek de jongste volwassene zonder kinderen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Optional<Adult> exercise20(List<Adult> dataset) {
      return dataset.stream()
                .filter(a -> a.children().isEmpty())
                .min(Comparator.comparingInt(Adult::age));
  }
  ```
  
  {{% /notice %}}

- **21.** ★★ Geef per volwassene het aantal kinderen terug in een `Map<Adult, Integer>`.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<Adult, Integer> exercise21(List<Adult> dataset) {
      return dataset.stream()
                .collect(Collectors.toMap(Function.identity(), a -> a.children().size()));
  }
  ```
  
  {{% /notice %}}

- **22.** ★★ Maak een `Map<String, Long>` met de frequentie van kind-voornamen (case-insensitive), bijvoorbeeld `"alice"` en `"Alice"` samen tellen.
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static Map<String, Long> exercise22(List<Adult> dataset) {
      return dataset.stream()
              .flatMap(a -> a.children().stream())
              .map(c -> c.firstName().toLowerCase())
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }
  ```
  
  {{% /notice %}}

- **23.**  ★★★★ Bepaal de twee postcodes met het grootste totaal aantal kinderen, gesorteerd (op postcode) van hoog naar laag.

  _Hint 1: gebruik `Collectors.summingInt(...)` in combinatie met `groupingBy`._

  _Hint 2: je kan de entries van een Map opnieuw als stream gebruiken via `.entrySet().stream()`._

  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
    public static List<String> exercise23(List<Data.Adult> dataset) {
        return dataset.stream()
                .collect(Collectors.groupingBy(Data.Adult::zipCode, Collectors.summingInt(a -> a.children().size())))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .toList();
    }
  ```

  Merk op dat `.collect(...).entrySet().stream()` inhoudt dat een tussentijdse datastructuur (een `Map`) gecreëerd wordt. Dit is dus niet één stream pipeline, maar zijn er twee na elkaar.
  {{% /notice %}}

- **24.** ★★★★ Zoek alle volwassenen met een twee- of meerling (twee of meer van hun kinderen hebben dezelfde leeftijd).
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
  
  Merk op dat `.collect(...).values().stream()` inhoudt dat een tussentijdse datastructuur (een `Map`) gecreëerd wordt. Dit is dus niet één stream pipeline, maar zijn er twee na elkaar.

  {{% /notice %}}


- **25.** ★★★★★ Maak een lijst van `AvgAgeZip`-objecten voor alle postcodes waar een volwassene met minstens één kind woont, gesorteerd volgens postcode.
  
  ```java
  public record AvgAgeZip(String zipCode, double averageAge) {}
  ```
  
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
    public static List<AvgAgeZip> exercise25(List<Data.Adult> dataset) {
        return dataset.stream()
                .filter(a -> !a.children().isEmpty())
                .collect(Collectors.groupingBy(Data.Adult::zipCode, Collectors.flatMapping(a -> a.children().stream(), Collectors.averagingInt(Data.Child::age))))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new AvgAgeZip(e.getKey(), e.getValue()))
                .toList();
    }
  ```

  Merk op dat `.collect(...).entrySet().stream()` inhoudt dat een tussentijdse datastructuur (een `Map`) gecreëerd wordt. Dit is dus niet één stream pipeline, maar zijn er twee na elkaar.
  
  {{% /notice %}}

- **26.** ★★★ Schrijf, gebruik makend van streams, een generische methode `mapAllValues(map, function)` die op basis van de gegeven `Map` een nieuwe `Map` maakt waarbij alle values vervangen zijn door de gegeven functie erop toe te passen. Denk na over geschikte grenzen voor je generische parameters (PECS).
  {{% notice style=tip title="Modeloplossing" expanded=false %}}

  ```java
  public static <K, V, R> Map<K, R> mapAllValues(
            Map<? extends K, ? extends V> map,
            Function<? super V, ? extends R> function) {
      return map.entrySet().stream()
              .collect(Collectors.toMap(Map.Entry::getKey, e -> function.apply(e.getValue())));
  }
  ```

  Toepassing van PECS:
  - `map` dient als producent voor `K`'s en `V`'s, dus beiden **extends**
  - `function` consumeert een `V` (**super**) en produceert een `R` (**extends**)
  
  {{% /notice %}}


