---
title: "Oefeningen: alles"
toc: true
weight: 60
autonumbering: true
draft: false
---

## Covariantie

Maak een schema met de overervingsrelaties tussen

- `List<Cat>`
- `List<? extends Cat>`
- `ArrayList<Cat>`
- `ArrayList<? extends Cat>`
- `List<Animal>`
- `List<? extends Animal>`
- `ArrayList<Animal>`
- `ArrayList<? extends Animal>`

> [!tip]- Antwoord
> - `ArrayList<Cat>` is een subtype van `List<Cat>` en van `ArrayList<? extends Cat>`.
> - `List<Cat>` is een subtype van `List<? extends Cat>`
> - `ArrayList<? extends Cat>` is een subtype van `List<? extends Cat>` en van `ArrayList<? extends Animal>`
> - `ArrayList<Animal>` is een subtype van `ArrayList<? extends Animal>` en `List<Animal>`
> - `List<? extends Cat>`, `ArrayList<? extends Animal>` en `List<Animal>` zijn alledrie subtypes van `List<? extends Animal>`

## Shop

Maak een klasse `Shop` die een winkel voorstelt die items (subklasse van `StockItem`) aankoopt.
Een Shop-object wordt geparametriseerd met het type items dat aangekocht kan worden. We beschouwen hier `Fruit` en `Electronics`.

`Shop` heeft twee methodes:

- `buy`, die een lijst van items toevoegt aan de stock;
- `addStockToInventory`, die de lijst van items in stock toevoegt aan de meegegeven inventaris-lijst.

Voor het fruit maak je een abstracte klasse `Fruit`, en subklassen `Apple` en `Orange`.
Maak daarnaast nog een abstracte klasse `Electronics`, met als subklasse `Smartphone`.

Zorg dat onderstaande code (ongewijzigd) compileert en dat de test slaagt:

```java
@Test
public void testGenerics() {
  Shop<Fruit> fruitShop = new Shop<>();
  Shop<Electronics> electronicsShop = new Shop<>();

  List<Apple> apples = List.of(new Apple(), new Apple());
  List<Fruit> oranges = List.of(new Orange(), new Orange(), new Orange());

  List<Smartphone> phones = List.of(new Smartphone(), new Smartphone());

  fruitShop.buy(apples);
  fruitShop.buy(oranges);

  electronicsShop.buy(phones);

  List<StockItem> inventory = new ArrayList<>();
  fruitShop.addStockToInventory(inventory);
  Assertions.assertThat(inventory).hasSize(5);

  electronicsShop.addStockToInventory(inventory);

  Assertions.assertThat(inventory).hasSize(7);
}
```

## Functie compositie

Schrijf een generische functie `compose` die twee functies als parameters heeft, en een nieuwe functie teruggeeft die de compositie voorstelt: eerst wordt de eerste functie uitgevoerd, en dan wordt de tweede functie uitgevoerd op het resultaat van de eerste. Voor types van functies kan je opnieuw `java.util.function.Function<T, R>` gebruiken.
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

## Oud-examenvraag

{{% notice todo TODO %}}
Examenoefening
{{% /notice %}}

## Animal food

**Dit is een uitdagende oefening, voor als je je kennis over generics echt wil testen.**

Voeg generics (met grenzen/bounds) toe aan de code hieronder, zodat de code (behalve de laatste regel) compileert,
en de compiler enkel katteneten toelaat voor katten, en hondeneten voor honden:

```java
class Animal {
  public void eat(Food food) { }
}
class Cat extends Animal {}
class Dog extends Animal {}
class Food {}

class Main {
  public static void main(String[] args) {
    Food catFood = new Food();
    Food dogFood = new Food();

    Cat cat = new Cat();
    Dog dog = new Dog();

    cat.eat(catFood); // OK 👍
    dog.eat(dogFood); // OK 👍

    cat.eat(dogFood); // <- moet een compiler error geven! ❌
  }
}
```

(Hint: Begin met het type `Food` te parametriseren met een generische parameter die het `Animal`-type voorstelt dat dit voedsel eet.)

## Self-type

**Dit is een uitdagende oefening, voor als je je kennis over generics echt wil testen.**

Heb je je al eens afgevraagd hoe `assertThat(obj)` uit AssertJ werkt?
Afhankelijk van het type van `obj` dat je meegeeft, worden er andere assertions beschikbaar die door de compiler aanvaard worden:

```java
// een List<String>
List<String> someListOfStrings = List.of("hello", "there", "how", "are", "you");
assertThat(someListOfStrings).isNotNull().hasSize(5).containsItem("hello");

// een String
String someString = "hello";
assertThat(someString).isNotNull().isEqualToIgnoringCase("hello");

// een Integer
int someInteger = 4;
assertThat(someInteger).isNotNull().isGreaterThan(4);

assertThat(someInteger).isNotNull().isEqualToIgnoringCase("hello"); // <= compileert niet ❌ 
```

Sommige assertions (zoals `isNotNull`) zijn echter generiek, en wil je slechts op 1 plaats implementeren.
Probeer zelf een assertThat-methode te schrijven die werkt zoals bovenstaande, maar waar `isNotNull` slechts op 1 plaats geïmplementeerd is.

Hint 1: maak verschillende klassen, bijvoorbeeld `ListAssertion`, `StringAssertion`, `IntegerAssertion` die de type-specifieke methodes bevatten. Begin met `isNotNull` toe te voegen aan elk van die klassen (dus door de implementatie te kopiëren).

Hint 2: in een zogenaamde 'fluent interface' geeft elke operatie zoals `isNotNull` en `hasSize` het this-object op het einde terug (`return this`), zodat je oproepen na elkaar kan doen. Bijvoorbeeld `.isNotNull().hasSize(5)`.

Hint 3: maak nu een abstracte klasse `GenericAssertion` die `isNotNull` bevat, en waarvan de andere assertions overerven. Verwijder de andere implementaties van `isNotNull`.

Hint 4: In `isNotNull` is geen informatie beschikbaar over het type dat gebruikt moet worden als terugkeertype van `isNotNull`. `assertThat(someString).isNotNull()` moet bijvoorbeeld opnieuw een `StringAssertion` teruggeven. Dat kan je oplossen met generics, en een abstracte methode die het juiste object teruggeeft.

Hint 5: Je zal een zogenaamd 'self-type' moeten gebruiken. Dat is een generische parameter die wijst naar de (sub)klasse zelf.

Hint 6: op [deze pagina](http://web.archive.org/web/20130721224442/http:/passion.forco.de/content/emulating-self-types-using-java-generics-simplify-fluent-api-implementation) wordt uitgelegd hoe AssertJ dit doet. Probeer eerst zelf, zonder dit te lezen!
