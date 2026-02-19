---
title: "7.5 Lambdas"
toc: true
weight: 50
autonumbering: true
author: "Koen Yskout"
draft: true
math: true
---

{{% notice info "In andere programmeertalen" %}}
De concepten in andere programmeertalen die het dichtst aanleunen bij Java lambda's zijn
- in Python: lamdba's (met het keyword `lambda`) en methode-referenties/callables
- in C++: lambda expressies, function pointers
- in C#: lambda expressies
Daarenboven zijn lambda's en methode-referenties alomtegenwoordig in zogenaamde *pure functionele programmeertalen* (bv. Haskell).
{{% /notice %}}

## Wat en waarom?

Stel dat we een record `Person` hebben:
```java
record Person(String firstName, String lastName, int age) {
  public boolean isAdult() {
    return this.age() >= 18;
  }
}
```

Met lambda-functies kun je in Java kort en bondig een functie schrijven.
Die functie heeft geen naam te geven; je zal ze typisch dus maar één keer gebruiken.
Een lambda-functie in Java, die de voor- en achternaam van een persoon aan elkaar plakt met een spatie, ziet er bijvoorbeeld als volgt uit:

```java
(Person p) -> p.firstName() + " " + p.lastName()
```
De `->` wijst op een lambda-functie. Links ervan staan de parameters, rechts de _body_ van de methode.


Je kan zo'n lambda-functie bijvoorbeeld gebruiken om een Comparator te maken die een lijst van personen sorteert volgens hun voor- en achternaam:
```java
var people = new java.util.ArrayList<>(List.of(
                new Person("Jane", "Doe", 17),
                new Person("John", "Doe", 18),
                new Person("Alex", "Jones", 20)));
        people.sort(Comparator.comparing((Person p) -> p.firstName() + " " + p.lastName()));
        System.out.println(people);
```

Je hoeft niet expliciet te zeggen welk type `p` heeft; Java kan dat vaak zelf afleiden. Hetvolgende kan dus ook:

```java
people.sort(Comparator.comparing(p -> p.firstName() + " " + p.lastName()));
```

Methode-referenties zijn een manier om direct te verwijzen naar een reeds bestaande methode, in plaats van er een lambda voor te schrijven.
Waar je een lambda van de vorm `(Person p) -> p.age()` zou gebruiken, kun je ook gewoon `Person::age` schrijven.
De `::` wijst op een methode-referentie. Links ervan staat de naam van de klasse, rechts de naam van de (bestaande) methode.
Merk op dat er _geen haakjes_ staan (dus niet `Person::age()`). Het is enkel een verwijzing naar de methode; de methode zelf wordt niet meteen opgeroepen.

Een methode-referentie is vooral handig als de lambda precies één methode-aanroep doet.
Achter de schermen gebeurt hetzelfde, maar de code is nog compacter.
Om de lijst van people te sorteren volgens leeftijd, kan je dus ook een methode-referentie gebruiken als volgt:
```java
people.sort(Comparator.comparing(Person::age));
```

### Types van lambda's en methode-referenties
Omdat Java een sterk getypeerde taal is, moeten lambda-functies en methode-referenties ook een type hebben.
Dat gebeurt door een interface te definiëren.
Elke interface met daarin precies één methode kan automatisch gebruikt worden als type voor lambda-functies en methode-referenties (als de types overeen komen).
Als het expliciet de bedoeling is om de interface daarvoor te gebruiken, kan je die interface ook met `@FunctionalInterface` annoteren; de compiler komt dan klagen als je later een extra methode zou proberen toe te voegen.
Bijvoorbeeld:

```java
@FunctionalInterface
interface PersonPredicate {
  boolean test(Person person);
}

public List<Person> selectPeople(List<Person> people, PersonPredicate predicate) {
    List<Person> result = new ArrayList<>();
    for (Person p : people) {
      if (predicate.test(p)) {
        result.add(p);
      }
    }
    return result;
}

var people = List.of(
      new Person("Jane", "Doe", 17),
      new Person("John", "Doe", 18),
      new Person("Alex", "Jones", 20));
```

In de code hierboven zie je de `PersonPredicate`-interface, geannoteerd met `@FunctionalInterface`.
Deze definieert één methode die true of false teruggeeft voor een persoon.
De methode `selectPeople` gebruikt deze interface om alle personen te selecteren die voldoen aan de meegegeven voorwaarde.

{{% notice note %}}
De implementatie van `selectPeople` voldoet aan het patroon van code waarvoor streams een oplossing kunnen bieden.
Inderdaad, deze operatie komt overeen met de `filter`-methode op streams.
{{% /notice %}}

We overlopen nu 5 manieren om de `selectPeople` methode te gebruiken.
Een eerste manier is een klasse maken (bv. `IsAdult`) die de interface implementeert, en die nagaat of de persoon meerderjarig is.
Dat werkt, maar is nogal omslachtig:

```java
/* [1] */
class IsAdult implements PersonPredicate {
  @Override
  public boolean test(Person person) {
    return person.isAdult();
  }
}
System.out.println(selectPeople(people, new IsAdult()));
// => [Person[firstName=Alex, lastName=Jones, age=20], Person[firstName=John, lastName=Doe, age=18]]
```

Een tweede optie is om een anonieme klasse te gebruiken. In het voorbeeld hieronder is dat een anonieme klasse die nagaat of de achternaam van de persoon begint met "Do".
Ook dat blijft omslachtig:

```java
/* [2] */
System.out.println(selectPeople(people, new PersonPredicate() {
    @Override
    public boolean test(Person person) {
        return person.lastName().startsWith("Do");
    }
}));
// => [Person[firstName=Jane, lastName=Doe, age=17], Person[firstName=John, lastName=Doe, age=18]]
```

Met lambda-functies kan je dergelijke code veel eenvoudiger schrijven.
In \[3] en \[4] hieronder zie je hoe je een lambda-functie kan gebruiken die hetzelfde doet als de vorige voorbeelden, maar dan zonder een klasse te schrijven.
Merk op dat het toegelaten is om de lambda-functies te gebruiken waar een `PersonPredicate` verwacht wordt.
De lambda-functies zijn inderdaad functies die een Person-object als argument hebben, en een boolean teruggeven, en komen dus qua type overeen met de `test`-methode in `PersonPredicate`.

```java
/* [3] */
System.out.println(selectPeople(people, p -> p.isAdult()));
// => [Person[firstName=Alex, lastName=Jones, age=20], Person[firstName=John, lastName=Doe, age=18]]

/* [4] */
System.out.println(selectPeople(people, p -> p.lastName().startsWith("Do")));
// => [Person[firstName=Jane, lastName=Doe, age=17], Person[firstName=John, lastName=Doe, age=18]]
```

Tenslotte kunnen we ook een methode-referentie gebruiken:

```java
/* [5] */
System.out.println(selectPeople(people, Person::isAdult));
```

Dat is vooral nuttig als er al een methode bestaat, of als de implementatie van de methode te omslachtig is om als lambda te schrijven.

### Voorgedefinieerde types voor functies

In plaats van zelf een interface zoals `PersonPredicate` te schrijven, kan je vaak beroep doen op een voorgedefinieerde functie-interface.
Je vindt de lijst daarvan [in de documentatie](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/package-summary.html).
We lijsten hier de belangrijkste functionele interfaces op die gebruikt worden in de context van streams:

- [`Function<T, R>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Function.html): een functie met 1 argument, die een `T` omzet in een `R`. Er zijn ook varianten voor primitieve resultaat-types, zoals [`ToIntFunction<T>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/ToIntFunction.html), die een `T` omzet in een int. Bijvoorbeeld:
  ```java
  Function<Person, String> getLowercaseName = person -> person.name().toLowerCase();
  ToIntFunction<Person> getAge = Person::age;
  ```
- [`Predicate<T>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Predicate.html): een functie met 1 argument van type `T`, die `true` of `false` teruggeeft. Ook hier bestaan varianten voor primitieve types, bijvoorbeeld [`IntPredicate`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/IntPredicate.html). Bijvoorbeeld:

  ```java
  Predicate<Person> isAdult = person -> person.age() >= 18;
  IntPredicate isNonNegative = i -> i >= 0;
  ```

- [`BiFunction<T, U, R>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/BiFunction.html): een functie met 2 argumenten, die een `T` en een `U` omzet in een `R`. Bijvoorbeeld:
  ```java
  BiFunction<Person, Integer, String> f = (person, nbPets) -> "Person " + person.name() + " has " + nbPets + " pets";
  ```
- [`UnaryOperator<T>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/UnaryOperator.html): een operator met 1 argument van type `T`, en een resultaat van type `T`. Dit is dus een speciaal geval van een `Function`, namelijk een `Function<T, T>`. Bijvoorbeeld:
  ```java
  UnaryOperator<String> indentOnce = s -> "  " + s;
  ```
- [`BinaryOperator<T>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/BinaryOperator.html): een functie met 2 argumenten, beide van type `T`, die een `T` teruggeeft. Dit is dus een speciaal geval van een `BiFunction`, namelijk een `BiFunction<T, T, T>`. Bijvoorbeeld:
  ```java
  BinaryOperator<String> joinWithSpace = (s1, s2) -> s1 + " " + s2;
  ```
- [`Supplier<T>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Supplier.html): een operatie zonder argumenten, die een `T` teruggeeft. Een invocatie van de supplier mag telkens hetzelfde of een ander object teruggeven. Een supplier kan dus gebruikt worden als generator. Bijvoorbeeld:
  ```java
  Supplier<String> constant = () -> "Hello";
  Random rnd = new Random();
  Supplier<Integer> randomInt = rnd::nextInt();
  ```
- [`Consumer<T>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Consumer.html): een operatie met 1 argument van type `T`, met een void return type. De consumer 'verbruikt' het meegegeven object, zonder een resultaat terug te geven. Bijvoorbeeld:
  ```java
  Consumer<Person> printPerson = (person) -> System.out.println(person.name() + " (age " + person.age() + ")");
  ```
- [`BiConsumer<T, U>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/BiConsumer.html): een consumer met 2 argumenten van type `T` en `U`, die niets teruggeeft. Bijvoorbeeld:
  ```java
  BiConsumer<Person, Integer> printNTimes = (person, n) -> {
    for (int i = 0; i < n; i++)
      System.out.println(person.name() + " (age " + person.age() + ")");
  };
  ```
