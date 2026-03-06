---
title: "7.5 Lambdas"
toc: true
weight: 50
autonumbering: true
author: "Koen Yskout"
draft: false
math: true
---

{{% notice info "In andere programmeertalen" %}}
De concepten in andere programmeertalen die het dichtst aanleunen bij Java lambda's zijn
- in Python: lambda's (met het keyword `lambda`) en callables
- in C++: lambda expressies, function pointers
- in C#: lambda expressies
Daarenboven zijn lambda's en methode-referenties alomtegenwoordig in zogenaamde *pure functionele programmeertalen* (bv. Haskell).
{{% /notice %}}

## Wat en waarom?

Met lambda-functies kun je in Java kort en bondig een functie schrijven zonder de functie een naam te geven.
Dat is handig als je de functie maar op één plaats zal gebruiken.

Bijvoorbeeld, stel dat we een record `Person` hebben:

```java
record Person(String firstName, String lastName, int age) {
  public boolean isAdult() {
    return this.age() >= 18;
  }
}
```

en we willen die sorteren volgens `"Voornaam Achternaam"`, maar ons Person-record heeft geen methode `fullName()`.

In plaats van die functie te schrijven, enkel voor het sorteren, kunnen we een lambda-functie gebruiken.
Een lambda-functie die de voor- en achternaam van een persoon aan elkaar plakt met een spatie ziet er als volgt uit:

```java
(Person p) -> p.firstName() + " " + p.lastName()
```

De `->` wijst op een lambda-functie. Links ervan staan de parameters, rechts de _body_ van de methode.

Je kan met zo'n lambda-functie dan een Comparator maken die gebruikt wordt om een lijst van personen sorteert volgens hun voor- en achternaam:
```java
var people = new java.util.ArrayList<>(List.of(
                new Person("Jane", "Doe", 17),
                new Person("John", "Doe", 18),
                new Person("Alex", "Jones", 20)));

var comparator = Comparator.comparing((Person p) -> p.firstName() + " " + p.lastName());
//                                    ^---- lambda-functie --------------------------^
people.sort(comparator);
System.out.println(people);
```

Je hoeft niet expliciet te zeggen welk type parameter `p` heeft; Java kan dat vaak zelf afleiden. Het volgende kan dus ook:

```java
Comparator.comparing(p -> p.firstName() + " " + p.lastName());
```

## Syntax van lambda-expressies

Een lambda-expressie heeft in het algemeen deze vorm:

```java
(parameters) -> expression
```

of

```java
(parameters) -> {
    // meerdere statements
    return waarde;
}
```

Merk op dat een lambda zonder `{ }` geen `;` bevat.

Enkele belangrijke varianten:

- Geen parameters:

  ```java
  () -> 42
  ```

- Exact 1 parameter (haakjes mogen weg als je geen expliciet type schrijft):

  ```java
  x -> x * x
  ```

- Meerdere parameters:

  ```java
  (a, b) -> a + b
  ```

- Body met meerdere statements:

  ```java
  (a, b) -> {
      int som = a + b;
      return som * 2;
  }
  ```

{{% notice note %}}
Gebruik je een blok-body met `{ ... }`, dan moet je `return` expliciet schrijven (behalve bij `void`-lambdas).
Bij een expression-body (`x -> x + 1`) gebeurt de return impliciet.
{{% /notice %}}


## Methode-referenties

Methode-referenties zijn een manier om direct te verwijzen naar een reeds bestaande methode, in plaats van er een lambda voor te schrijven.
Waar je een lambda van de vorm `(Person p) -> p.age()` zou gebruiken, kun je ook gewoon `Person::age` schrijven.
De `::` wijst op een methode-referentie. Links ervan staat de naam van de klasse, rechts de naam van de (bestaande) methode.
Merk op dat er _geen haakjes ()_ staan (dus **niet** `Person::age()`). Het is enkel een verwijzing naar de methode; de methode zelf wordt niet meteen opgeroepen.

Een methode-referentie is vooral handig als de lambda precies één methode-aanroep doet.
Achter de schermen gebeurt hetzelfde, maar de code is wat compacter.

Om bijvoorbeeld de lijst `people` te sorteren volgens hun leeftijd, kan je een methode-referentie gebruiken als volgt:
```java
people.sort(Comparator.comparing(Person::age));
```

Java kent vier standaardvormen van methode-referenties:

- **Statische methode**: `ClassName::staticMethod`
  ```java
  Function<String, Integer> toInt = Integer::parseInt; // s -> Integer.parseInt(s)
  ```
- **Instantiemethode op specifiek object**: `instanceRef::method`
  ```java
  Consumer<Object> print = System.out::println; // s -> System.out.println(s)
  ```
- **Instantiemethode op willekeurig object van een type**: `ClassName::instanceMethod`
  ```java
  Function<String, String> lower = String::toLowerCase; // s -> s.toLowerCase()
  ```
- **Constructor**: `ClassName::new`
  ```java
  Supplier<ArrayList<String>> emptyList = ArrayList::new; // () -> new ArrayList<>()
  ```

Merk op dat alle variabelen hierboven (`toInt`, `print`, `lower`, `emptyList`) verwijzen naar een **methode** (of functie), en niet naar een **waarde** (int, String, ...) zoals je gewoon bent.
Deze variabelen hebben type `Function<String, Integer>`, `Consumer<Object>`, etc.
We gaan zodadelijk dieper in op deze types.

Het is erg belangrijk om te begrijpen dat in het voorbeeld hierboven geen van de methodes (`parseInt`, `println`, `toLowerCase`, of de constructor van `ArrayList`) opgeroepen of uitgevoerd wordt.
We maken enkel de verwijzing naar deze methode, en kennen die toe aan een variabele.
We kunnen deze variabelen nadien wel gebruiken om de methodes effectief uit te voeren, bijvoorbeeld:

```java
ArrayList<String> newList = emptyList.get(); // roept constructor van ArrayList op
String element = lower.apply("HeLLo"); // zet "HeLLo" om in "hello"
newList.add(element);
print.accept(newList.get(0)); // print "hello" uit
int x = toInt.apply("123"); // zet "123" om naar 123
print.accept(x); // print 123 uit
```

## Types van lambda's en methode-referenties

Omdat Java een sterk getypeerde taal is, moeten lambda-functies en methode-referenties ook een type hebben.
Dat gebeurt door een interface te definiëren.

### Functional interface

Elke **interface met daarin precies één methode** kan automatisch gebruikt worden als type voor lambda-functies en methode-referenties, tenminste als de types van parameters en het resultaat overeen komen.
Als het expliciet de bedoeling is om de interface op deze manier te gebruiken, kan je die interface ook met `@FunctionalInterface` annoteren; de compiler komt dan klagen als je later een extra methode zou proberen toe te voegen aan die interface.
Bijvoorbeeld:

```java
@FunctionalInterface
interface PersonPredicate {
  boolean test(Person person); // slechts één methode (van Person naar boolean)
}
```

In de code hierboven zie je de `PersonPredicate`-interface, geannoteerd met `@FunctionalInterface`.
Deze definieert één methode die true of false teruggeeft voor een persoon.

De methode `selectPeople` hieronder gebruikt de `PersonPredicate` interface om alle personen te selecteren die voldoen aan de meegegeven voorwaarde.

```java
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

We overlopen nu vier manieren om de `selectPeople` methode te gebruiken.

1. Een eerste manier is een **klasse maken** (bv. `IsAdult`) die de interface implementeert, en die nagaat of de persoon meerderjarig is.
Dat werkt, maar is nogal omslachtig, zeker als we dit slechts op 1 plaats nodig hebben:

    ```java
    /* [1] */
    class IsAdult implements PersonPredicate {
      @Override
      public boolean test(Person person) {
        return person.isAdult();
      }
    }
    System.out.println(selectPeople(people, new IsAdult()));
    // => [Person[firstName=John, lastName=Doe, age=18], Person[firstName=Alex, lastName=Jones, age=20]]
    ```

2. Een tweede optie is om een **anonieme klasse** te gebruiken. In het voorbeeld gaat de anonieme klasse na of de achternaam van de persoon begint met "Do".
  Ook dat blijft omslachtig, het bespaart ons enkel de moeite om een naam voor een klasse te verzinnen:

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

3. Met **lambda-functies** kan je dergelijke code veel eenvoudiger schrijven.

    In codefragment 3 en 4 hieronder zie je hoe je een lambda-functie kan gebruiken die hetzelfde doet als de vorige voorbeelden, maar dan zonder een klasse te schrijven.
    Merk op dat het toegelaten is om de lambda-functies te gebruiken waar een `PersonPredicate` verwacht wordt.
    De twee lambda-functies hieronder zijn inderdaad functies die een Person-object als argument hebben, en een boolean teruggeven, en komen dus qua type overeen met de `test`-methode in `PersonPredicate`.

    ```java
    /* [3] */
    System.out.println(selectPeople(people, p -> p.isAdult()));
    // => [Person[firstName=John, lastName=Doe, age=18], Person[firstName=Alex, lastName=Jones, age=20]]

    /* [4] */
    System.out.println(selectPeople(people, p -> p.lastName().startsWith("Do")));
    // => [Person[firstName=Jane, lastName=Doe, age=17], Person[firstName=John, lastName=Doe, age=18]]
    ```

4. Tenslotte kunnen we ook een **methode-referentie** gebruiken:

    ```java
    /* [5] */
    System.out.println(selectPeople(people, Person::isAdult));
    ```

    Dat is vooral nuttig als deze methode al bestaat en je er gebruik van wil maken.

### Voorgedefinieerde types voor functies

In plaats van zelf een interface zoals `PersonPredicate` te schrijven, kan je vaak ook beroep doen op een voorgedefinieerde functie-interface.
Je vindt de lijst daarvan [in de documentatie](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/package-summary.html).
We lijsten hier de belangrijkste reeds bestaande functionele interfaces op die gebruikt worden:

- [`Function<T, R>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/Function.html): stelt een functie met 1 parameter voor die een `T` omzet in een `R`:

  ```java
  @FunctionalInterface
  interface Function<T, R> {
    R apply(T t);
  }
  ```

  Er zijn ook varianten voor primitieve resultaat-types, zoals [`ToIntFunction<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/ToIntFunction.html), die een `T` omzet in een int. Bijvoorbeeld:

  ```java
  Function<Person, String> getLowercaseName = person -> (person.firstName() + " " + person.lastName()).toLowerCase();
  ToIntFunction<Person> getAge = Person::age;
  ```

- [`Predicate<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/Predicate.html): stelt een functie voor met 1 parameter van type `T`, die `true` of `false` teruggeeft:
  
  ```java
  @FunctionalInterface
  interface Predicate<T> {
    boolean test(T t);
  }
  ```

  Ook hier bestaan varianten voor primitieve types, bijvoorbeeld [`IntPredicate`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/IntPredicate.html). Bijvoorbeeld:

  ```java
  Predicate<Person> isAdult = person -> person.age() >= 18;
  IntPredicate isNonNegative = i -> i >= 0;
  ```

- [`BiFunction<T, U, R>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BiFunction.html): stelt een functie met 2 parameters voor, die een `T` en een `U` omzet in een `R`:

  ```java
  @FunctionalInterface
  interface BiFunction<T, U, R> {
    R apply(T t, U u);
  }
  ```

  Bijvoorbeeld:

  ```java
  BiFunction<Person, Integer, String> f =
      (person, nbPets) -> person.firstName() + " has " + nbPets + " pets";
  ```

- [`UnaryOperator<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/UnaryOperator.html): een operator met 1 parameter van type `T`, en een resultaat van type `T`:

  ```java
  @FunctionalInterface
  interface UnaryOperator<T> {
    T apply(T t);
  }
  ```

  Dit is dus een speciaal geval (en ook een subtype) van `Function`, namelijk een `Function<T, T>`. Bijvoorbeeld:

  ```java
  UnaryOperator<String> indentOnce = s -> "  " + s;
  ```

- [`BinaryOperator<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BinaryOperator.html): stelt een functie met 2 parameters voor, beide van type `T`, die een `T` teruggeeft:

  ```java
  @FunctionalInterface
  interface BinaryOperator<T> {
    T apply(T t1, T t2);
  }
  ```

  Dit is dus een speciaal geval van een `BiFunction`, namelijk een `BiFunction<T, T, T>`. Bijvoorbeeld:

    ```java
    BinaryOperator<String> joinWithSpace = (s1, s2) -> s1 + " " + s2;
    ```

- [`Supplier<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/Supplier.html): stelt een operatie zonder parameters voor die een `T` teruggeeft:

  ```java
  @FunctionalInterface
  interface Supplier<T> {
    T get();
  }
  ```

  Een invocatie van de supplier mag telkens hetzelfde object teruggeven, maar ook elke keer een ander object en alles daartussenin. Een supplier kan dus beschouwd worden als generator. Bijvoorbeeld:

  ```java
  Supplier<String> constant = () -> "Hello";
  Random rnd = new Random();
  Supplier<Integer> randomInt = rnd::nextInt;
  ```

- [`Consumer<T>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/Consumer.html): een operatie met 1 parameter van type `T`, met een void return type:

  ```java
  @FunctionalInterface
  interface Consumer<T> {
    void accept(T t);
  }
  ```

  De consumer 'verbruikt' het meegegeven object, zonder een resultaat terug te geven. Bijvoorbeeld:

  ```java
  Consumer<Person> printPerson =
      person -> System.out.println(person.firstName() + " " + person.lastName() + " (age " + person.age() + ")");
  ```

- [`BiConsumer<T, U>`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/BiConsumer.html): een consumer met 2 argumenten van type `T` en `U`, die niets teruggeeft:

  ```java
  @FunctionalInterface
  interface BiConsumer<T, U> {
    void accept(T t, U u);
  }
  ```

  Bijvoorbeeld:

  ```java
  BiConsumer<Person, Integer> printNTimes = (person, n) -> {
    for (int i = 0; i < n; i++)
      System.out.println(person.firstName() + " " + person.lastName() + " (age " + person.age() + ")");
  };
  ```

Op zich maakt het niet uit welke interface er gebruikt wordt, zolang de types van de enige methode erin maar overeenkomen met die van de lambda-expressie.
Bijvoorbeeld, de lambda

```java
(Person p) -> p.age() >= 18
```

kan dus overal gebruikt worden waar onze zelfgedefinieerde `PersonPredicate`-interface verwacht wordt, maar ook overal waar een `Predicate<Person>` of een `Function<Person, Boolean>` verwacht wordt.
De compiler gaat enkel na of de types van parameters en resultaat overeenkomen met die van de enige methode in de interface; de naam van de interface en de naam van de methode in deze interface doen niet terzake.


## Capturing en 'effectively final'

Een lambda mag variabelen uit de omliggende scope gebruiken.
Dat heet **capturing**.

```java
int minAge = 18;
Predicate<Person> isOldEnough = p -> p.age() >= minAge; // minAge wordt "gecaptured"
```

Zo'n variabele (`minAge` hierboven) moet wel **effectively final** zijn: je mag ze na initialisatie niet meer aanpassen.
Met andere woorden: je zou de variabele `final` moeten kunnen maken zonder dat dat problemen geeft.

De Java compiler zal zelf uitzoeken of de variabelen die gebruikt worden in een lambda inderdaad effectively final zijn, en een fout geven als dat niet zo is.
Dit voorkomt verwarrende situaties rond toestand en lifetime van variabelen.
Bijvoorbeeld:

```java
int minAge = 18;
Predicate<Person> isOldEnough = p -> p.age() >= minAge;  // compileerfout
minAge = 21;

var personOf19y = new Person("A", "B", 19);
IO.println(isOldEnough.test(personOf19y)); // true of false?
```

Als de code hierboven wel zou compileren, is het niet duidelijk welke leeftijd gebruikt zou moeten worden: 18 of 21?

De makkelijkste manier om hier geen last van te hebben is door geen variabelen te gebruiken die buiten de lambda gedefinieerd worden, en dat is dan ook ten zeerste aanbevolen.

## Oefeningen

Gebruik in deze oefeningen waar mogelijk zowel lambda's als methode-referenties.
De startcode vind je op [https://github.com/KULeuven-Diepenbeek/ses-deel2-oefeningen-04-lambdas](https://github.com/KULeuven-Diepenbeek/ses-deel2-oefeningen-04-lambdas).

### Basislambda's

Gegeven

```java
record Person(String firstName, String lastName, int age) {}
```

schrijf lambda-expressies voor volgende types:

- `Function<Person, String> fullname`: geef `"firstName lastName"` terug
- `Predicate<Person> isSenior`: true als de persoon minstens 65 jaar is
- `BiFunction<Person, Integer, Boolean> isAtLeast`: true als `person.age()` groter of gelijk is aan de tweede parameter
- `Supplier<Person> newPerson`: maak telkens een nieuwe `Person("Jane", "Doe", 25)`

Gebruik daarna ook elk van deze methodes.

{{% notice style=tip title=Oplossing expanded=false %}}

- ```java
  Function<Person, String> fullname = (Person p) -> p.firstName() + " " + p.lastName();
  ```

- ```java
  Predicate<Person> isSenior = (Person p) -> p.age() >= 65;
  ```

- ```java
  BiFunction<Person, Integer, Boolean> isAtLeast = (Person p, Integer minAge) -> p.age() >= minAge;
  ```

- ```java
  Supplier<Person> newPerson = () -> new Person("Jane", "Doe", 25);
  ```

Gebruik:

```java
var p = newPerson.get();
IO.println(fullname.apply(p));
IO.println(isSenior.test(p));
IO.println(isAtLeast.apply(p, 18));
```

{{% /notice %}}

### Van anonieme klasse naar lambda

Herschrijf onderstaande code met een lambda:

```java
people.removeIf(new Predicate<Person>() {
    @Override
    public boolean test(Person person) {
        return person.lastName().startsWith("D");
    }
});
```

{{% notice style=tip title=Oplossing expanded=false %}}

```java
people.removeIf(p -> p.lastName().startsWith("D"));
```

{{% /notice %}}

### Van lambda naar methode-referentie

Vervang elke lambda door een equivalente methode-referentie:

```java
Function<Person, Integer> f1 = p -> p.age();
Predicate<Person> f2 = p -> p.isAdult();
Consumer<Person> f3 = p -> System.out.println(p);
Function<String, Integer> f4 = s -> Integer.parseInt(s);
Supplier<List<Person>> f5 = () -> new ArrayList<>();
```

{{% notice style=tip title=Oplossing expanded=false %}}

```java
Function<Person, Integer> f1 = Person::age;
Predicate<Person> f2 = Person::isAdult;
Consumer<Person> f3 = System.out::println;
Function<String, Integer> f4 = Integer::parseInt;
Supplier<List<Person>> f5 = ArrayList::new;
```

{{% /notice %}}

### Functie-compositie

> Deze oefening is ook een extra oefening op generics.

Schrijf een generische methode `compose` die twee functies als parameters heeft, en als resultaat een nieuwe functie teruggeeft die de compositie voorstelt: eerst wordt de eerste functie uitgevoerd, en dan wordt de tweede functie uitgevoerd op het resultaat van de eerste.

Dus: voor functies

```java
Function<A, B> f1 = ...
Function<B, C> f2 = ...
```

moet `compose(f1, f2)` een `Function<A, C>` teruggeven, die als resultaat `f2.apply(f1.apply(a))` teruggeeft.

Pas de PECS-regel toe om ook functies te kunnen samenstellen die niet exact overeenkomen qua type.
Bijvoorbeeld, volgende code moet compileren en de test moet slagen:

```java
interface Ingredient {}
record Fruit() implements Ingredient {}
record PeeledFruit(Fruit fruit) implements Ingredient {}
record Chopped(Ingredient food) implements Ingredient {}

@Test
public void testCompose() {
    Function<Fruit, PeeledFruit> peelFruit = (var fruit) -> new PeeledFruit(fruit);
    Function<Ingredient, Chopped> chopIngredient = (var food) -> new Chopped(food);

    var makeFruitSalad = compose(peelFruit, chopIngredient);

    assertThat(makeFruitSalad.apply(new Fruit())).isEqualTo(new Chopped(new PeeledFruit(new Fruit())));
}
```

{{% notice style=tip title=Oplossing expanded=false %}}

```java
public static <A, B, C> Function<A, C> compose(
      Function<? super A, ? extends B> f1,
      Function<? super B, ? extends C> f2) {
  return a -> f2.apply(f1.apply(a));
}
```

Producer Extends, Consumer Super:

`f1` consumeert een `A` en produceert een `B`, vandaar `? super A` en `? extends B`.

`f2` consumeert een `B` en produceert een `C`, vandaar `? super B` en `? extends C`.
{{% /notice %}}

### Comparator

Gegeven onderstaande code:

```java
record Person(String firstName, String lastName, int age) {}

static void main() {
    List<Person> people = new ArrayList<>(List.of(
            new Person("Adam", "Doe", 20),
            new Person("John", "Doe", 28),
            new Person("Xavier", "Doe", 30),
            new Person("Mary", "Adams", 64),
            new Person("Mary", "Adams", 12)
    ));

    Comparator<Person> comparator = null; // TODO

    people.sort(comparator);

    people.forEach(IO::println);
}
```

Maak een comparator-object dat de lijst sorteert

- eerst op `lastName`
- dan op `firstName`
- en bij gelijke naam op dalende `age`

Gebruik `Comparator.comparing`, `thenComparing` en methode-referenties.

{{% notice style=tip title=Oplossing expanded=false %}}

```java
Comparator<Person> comparator = Comparator
                .comparing(Person::lastName)
                .thenComparing(Person::firstName)
                .thenComparing(Person::age);
```

{{% /notice %}}

### TriFunction

Java heeft geen interface voor een functie met 3 parameters.

1. Definieer zelf een (generische) functionele interface `TriFunction` die een functie voorstelt met 3 parameters (van een verschillend type).
2. Definieer een functie `zip3` die 3 lijsten als parameters heeft, samen met een `TriFunction`.
   De 3 lijsten moeten even lang zijn.
   Deze functie geeft een lijst terug waarvan het _i-de_ element gevormd wordt door de meegegeven tri-functie toe te passen op het _i-de_ element van elk van de drie parameter-lijst.

Een voorbeeld van het gebruik van `zip3` om een lijst van personen te maken op basis van een lijst van voornamen, achternamen, en leeftijden:

```java
var people = zip3(List.of("John", "Mary"),
                  List.of("Doe", "Adams"),
                  List.of(18, 23),
                  (first, last, age) -> new Person(first, last, age));
IO.println(people);
// Person[firstName=John, lastName=Doe, age=18]
// Person[firstName=Mary, lastName=Adams, age=23]
```

{{% notice style=tip title=Oplossing expanded=false %}}

```java
@FunctionalInterface
interface TriFunction<U, V, W, R> {
    R apply(U u, V v, W w);
}

static <U, V, W, R> List<R> zip3(List<U> us, List<V> vs, List<W> ws, TriFunction<U, V, W, R> f) {
    List<R> result = new ArrayList<>();
    for (int i = 0; i < us.size(); i++) {
        result.add(f.apply(us.get(i), vs.get(i), ws.get(i)));
    }
    return result;
}
```

{{% /notice %}}

### Mini-event dispatcher

Schrijf een generische klasse `Dispatcher<E>` waar klassen zich kunnen inschrijven om op de hoogte gebracht te worden van een event.
Het event heeft generisch type `E`.

Voorzie twee methodes:

1. Een methode `subscribe` waar je een methode kan registreren die opgeroepen moet worden telkens een event `E` gepubliceerd wordt.
2. Een methode `publish(E event)` die alle geregistreerde methodes oproept met het meegegeven event.

Een voorbeeld van het gebruik van de Dispatcher-klasse (met `String` als event-type):

```java
static void handler1(String s) {
    IO.println("Handler 1 got " + s);
}

static void main() {
    Dispatcher<String> dispatcher = new Dispatcher<>();

    dispatcher.subscribe(Oef07::handler1);
    dispatcher.subscribe(s -> IO.println("Handler 2 got " + s));

    dispatcher.publish("hello");
    // Handler 1 got hello
    // Handler 2 got hello

    dispatcher.publish("bye");
    // Handler 1 got bye
    // Handler 2 got bye
}
```

{{% notice style=tip title=Oplossing expanded=false %}}

```java
class Dispatcher<E> {
    private List<Consumer<E>> handlers = new ArrayList<>();
    public void publish(E event) {
        for (var h : handlers) {
            h.accept(event);
        }
    }
    public void subscribe(Consumer<E> handler) {
        handlers.add(handler);
    }
}
```

{{% /notice %}}
