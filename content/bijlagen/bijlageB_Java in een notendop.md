---
title: Bijlage B - Java in een notendop
weight: 2
author: Koen Yskout
draft: false
toc: true
autonumbering: true
---

Dit document biedt een overzicht van enkele Java-specifieke concepten en syntax voor studenten die wel vertrouwd zijn met een object-georiënteerde programmeertaal, maar niet met Java.

## Algemeen

Java is een sterk getypeerde taal: alle variabelen hebben types, en de compiler dwingt af dat de types overeenkomen.

Het type wordt steeds vóór de variabele geschreven: `int x`.

Java is ook object-georiënteerd: elke variabele is ofwel een referentie naar een object, ofwel een primitieve waarde (int, boolean, ...). De speciale waarde `null` wordt gebruikt voor referentie-variabelen die naar geen enkel object verwijzen.

Een methode oproepen op een object gebeurt via een punt: `ontvanger.methode(parameter1, parameter2)`

## Primitieve datatypes

De primitieve datatypes in Java zijn

- `int` (32-bit integer)
- `long` (64-bit integer)
- `short` (16-bit integer)
- `boolean`
- `float` (single-precision)
- `double` (double-precision)
- `char` (16-bit unicode character)
- `byte` (signed! -128..127)

Daarnaast is ook `String` een ingebouwd datatype (maar strikt genomen geen primitief type).
Strings zijn immutable: eens aangemaakt, kan geen karakters wijzigen/toevoegen/....

Tenslotte bestaan er ook zogenaamde 'boxed' types: `Integer`, `Long`, `Short`, `Boolean`, `Float`, `Double`, `Char`, `Byte`. Dit zijn objecten die exact één primitieve waarde van het overeenkomstige type bevatten.
De Java compiler zorgt zelf voor boxing en unboxing:

```java
int a = 2;
Integer x = a;
Double y = 3.2;
double z = x * y;
```

Je kan ze dus bijna overal door elkaar gebruiken, maar het is aangeraden om, waar mogelijk de primitieve types te gebruiken.
Het voornaamste gebruik van de boxed types is als type voor ArrayLists (zie hieronder), bijvoorbeeld `ArrayList<Integer>` om een lijst van gehele getallen voor te stellen.

## Main method

De uitvoering van een Java-programma start steeds in een methode `public static void main(String[] args)`, die in een klasse moet staan.

```java
class Example {
  public static void main(String[] args) {
    System.out.println("Hello world");
  }
}
```

## Arrays

Java heeft arrays; deze hebben een vaste lengte die bepaald wordt bij het aanmaken en daarna niet meer gewijzigd kan worden.
Het type van een array van type T is `T[]`; bij het aanmaken geef je de lengte op: `new T[lengte]`.
De lengte kan je opvragen via het `.length` attribuut.
Individuele elementen vraag je op en verander je via `[index]`; het eerste element heeft index 0:

```java
int[] fiveNumbers = new int[5];
int first = fiveNumbers[0];
int last = fiveNumbers[fiveNumbers.length - 1];

fiveNumbers[2] = 10;
```

## ArrayList

Een ArrayList is een collectie zoals een array, maar die groter en kleiner kan worden.
Je voegt elementen toe met `add(element)`, en vraagt ze op via `get(index)`.
Je moet ArrayList importeren uit het `java.util` package om het te kunnen gebruiken.

```java
import java.util.ArrayList;

ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(10);
System.out.println(numbers.get(0)); // toont '10';
```

## Statements

### If-statements

Een if-statement in java bevat steeds een conditie (haakjes verplicht!).
Er kunnen ook meerdere `else if` blokken aan toegevoegd worden,
en/of één `else`-blok.

```java
if (<conditie>) {
  // ...
} else if (<conditie>) {
  // ...
} else {
  // ...
}
```

Een if-statement is geen expressie; het geeft dus geen waarde terug.
Er is ook een conditionele expressie waarbij dat wel kan:

```java
int numberOfCats = ...
String plural = (numberOfCats == 1 ? "cat" : "cats");
```

### While-statements

```java
while (<condition>) {
  // ...
}
```

Er is ook een do-while statement, wat minstens 1 keer uitgevoegd wordt:

```java
do {
  // ...
} while (<condition>)
```

### For-statements

```java
for (<initializer>; <condition>; <increment>) {
  // body
}
```

Bijvoorbeeld:

```java
int sum = 0;
for (int number = 0; number < 10; number++) {
  sum += number;
}
```

Een for-loop is bijna volledig equivalent aan

```java
<initializer>
while (<condition>) {
  // body
  <increment>
}
```

behalve dat je, bij de for-loop, de variabelen die in de initializer gedeclareerd worden (`number` in het voorbeeld hierboven) niet meer kan gebruiken na de lus (die zijn _out of scope_).

### Enhanced for-loop (foreach)

Een enhanced for-loop ('foreach') kan je gebruiken om over de elementen van een array of collectie (bijvoorbeeld een `ArrayList`) te itereren (meer in detail: alle klassen die de `Iterable` interface implementeren).

```java
Person[] peopleArray = ...
for (Person person : peopleArray) {
  // ...
}


ArrayList<Person> peopleList = new ArrayList<Person>();
for (Person person : peopleList) {
  // ...
}
```

### Switch statements

```java
switch (<expression>) {
  case <constant 1>:
    // ...
    break;
  case <constant 2>:
    // ...
    break;
  default:
    // ...
}
```

`<expression>` kan bijvoorbeeld een `String`, `int` of `enum` zijn.

`break` is noodzakelijk in elke case om 'fall-through' te vermijden.

Recentere versies van Java hebben ook een switch-expressie, met een iets andere syntax.
Hier zijn geen break-statements meer nodig:

```java
String result = switch (<expression>) {
  case <constant 1> -> "case one";
  case <constant 2> -> "case two";
  default -> "default";
}
```

## Klassen

Een klasse heeft

- een of meerdere constructoren, die opgeroepen worden wanneer een object aangemaakt wordt. Indien je geen constructor schrijft, wordt een default constructor voorzien (zonder parameters).
- velden/attributen: de eigenschappen van een object, telkens met een type
- methodes: de acties die uitgevoerd kunnen worden door een object. Elke methode heeft
  - precies 1 terugkeertype, of `void` indien de methode geen resultaat teruggeeft. Je geeft een waarde terug via een `return`-statement.
  - 0 of meer parameters

Een voorbeeld:

```java
package example.person;

public class Person {
  // velden
  private String name;
  private Person marriedTo = null; // default null

  // constructor
  public Person(String name) {
    this.name = name;
  }

  // getter en setter voor naam
  public String getName() {
    return name; // zelfde als 'return this.name;'
  }
  public void setName(String newName) {
    this.name = newName;
  }

  public boolean isMarried() {
    return marriedTo != null;
  }

  public void marryTo(Person other) {
    if (isMarried()) {
      throw new IllegalStateException("Already married");
    }
    if (other.isMarried()) {
      throw new IllegalArgumentException("Other person is already married");
    }
    this.marriedTo = other;
    other.marriedTo = this; // OK ondanks `private` modifier (want zelfde klasse)
  }

}
```

Elke publieke klasse moet in een bestand staan met dezelfde naam als de klasse, bijvoorbeeld `Person.java`.
Als de klasse in een package zit, moet het pad overeenkomen met dat package, bijvoorbeeld `example/person/Person.java`.

### Objecten aanmaken

Een object van een klasse wordt aangemaakt via `new`. Daarbij wordt de constructor opgeroepen.

```java
Person john = new Person("John");
Person mary = new Person("Mary");
```

Tenzij een variabele een primitief type heeft, is het altijd een referentie naar het object in het geheugen (equivalent aan pointers in C/C++):

```java
Person john = new Person("John");
Person mary = john;
mary.setName("Mary");
System.out.println(john.getName()); // => John heet nu ook "Mary"
```

Java heeft automatische garbage collection: objecten die niet meer gebruikt worden, worden automatisch uit het geheugen verwijderd.

## Modifiers

Java heeft 4 **visibility modifiers** voor klassen, velden, en methodes:

- `public`: het element is overal toegankelijk
- `private`: het element is enkel toegankelijk binnen het bestand zelf (de `compilation unit`)
- `protected`: het element is toegankelijk voor 1) klassen binnen hetzelfde package, en 2) subklassen
- (package): het element is toegankelijk voor klassen binnen hetzelfde package. Er is geen specifieke modifier hiervoor (dit is de standaard als je niets schrijft).

Daarnaast zijn er nog volgende modifiers:

- `static`: het element is statisch, dit wil zeggen dat het geen deel uitmaakt van individuele objecten, maar van de klasse als geheel (het bestaat dus slechts 1 keer, en wordt gedeeld)
- `final`:
  - voor een klasse: er kan niet van overgeërfd worden
  - voor een methode: de methode kan niet overridden worden in een subklasse
  - voor een veld/attribuut en variabele: de referentie (of waarde voor een primitief type) van het veld kan niet meer gewijzigd worden (opgelet: het object waarnaar verwezen wordt, kan zelf wel nog gewijzigd worden! Enkel de referentie is onwijzigbaar).
- `abstract`: de klasse of de methode is abstract; zie verder.

## Enums

Java heeft ondersteuning voor enums:

```java
public enum Day {
  Monday,
  Tuesday,
  Wednesday,
  Thursday,
  Friday,
  Saturday,
  Sunday
}
```

## Overerving

Java heeft enkelvoudige overerving: elke klasse erft over van exact 1 superklasse (indien er geen opgegeven wordt, is dat `Object`). Overerving van een klasse wordt aangegeven door middel van `extends` na de klasse-naam:

```java
class Person {
  // ...
}

class Student extends Person {
  // ...
}
```

### Abstracte klassen

Een klasse die zelf niet rechtstreeks geïnstantieerd kan worden, is `abstract`.
Een abstracte klasse kan abstracte methodes hebben, die door (niet-abstracte) subklassen geïmplementeerd moeten worden.
Enkel niet-abstracte subklassen kunnen aangemaakt worden via `new`.

```java
abstract class Person {
  // ...
  public abstract void doSomething();
}

class Student extends Person {
  public void doSomething() {
    // study
  }
}
```

### Interfaces

Een klasse kan ook meerdere interfaces implementeren.
Een interface kan je zien als een abstracte klasse met enkel abstracte methodes (geen velden).

```java
interface CanEat {
  void eat(Food food); // altijd public
}

interface CanSleep {
  void sleep();
}

class Person implements CanEat, CanSleep {
  public void eat(Food food) { ... }
  public void sleep() { ... }
}
```

Je kan een interface gebruiken als type, op dezelfde manier als een klasse:

```java
ArrayList<CanEat> hungryPeople = new ArrayList<>();
hungryPeople.add(new Person());
for (CanEat hungry : hungryPeople) {
  hungry.eat(new Pizza());
}
```

### instanceof

Je kan nakijken of een object een instantie is van een bepaalde klasse of interface via instanceof:

```java
Person p = new Student();
System.out.println(p instanceof Person);  // => true
System.out.println(p instanceof Student); // => true
System.out.println(p instanceof Object);  // => true
System.out.println(p instanceof CanEat);  // => true
System.out.println(p instanceof Plant);   // => false
```

Over het algemeen is het gebruik van `instanceof` een 'code smell', en kijk je beter of je gebruik kan maken van polymorfisme.

### Polymorfisme en dynamic dispatch

In Java kan je methodes overloaden (zelfde naam maar verschillende parameter-types binnen eenzelfde klasse) en overriden (zelfde naam en parameters in super- en subklasse). Bij het overriden van een methode kan voor een object dezelfde methode dus bestaan in de klasse en in een of meerdere van de superklassen.
Daarom is er een mechanisme nodig om te bepalen welke methode uiteindelijk uitgevoerd zal worden. Dat wordt in Java bepaald door

- het gedeclareerde (statisch) type van de argumenten (at compile time)
- het effectieve (dynamische) type van het object (at runtime)

Bijvoorbeeld, neem volgende klassen en methodes:

```java
class Food {}
class Pizza extends Food {}

class Person {
  public void eat(Food food) { System.out.println("Person eating food"); }
  public void eat(Pizza pizza) { System.out.println("Person eating pizza"); }
}

class Student extends Person {
  public void eat(Food food) { System.out.println("Student eating food"); }
  public void eat(Pizza pizza) { System.out.println("Student eating pizza"); }
}
```

Dan krijgen we hetvolgende:

```java
Person person = new Student();
Food food = new Pizza();
person.eat(food); // => prints "Student eating food"
```

Het statisch type van `food` (at compile time) is `Food`.
Het dynamisch type van `person` (at runtime) is `Student`.
Dus wordt de methode `eat(Food food)` uit de klasse `Student` uitgevoerd.

## Exceptions

Wanneer zich een onvoorziene situatie voordoet, kan je dat in Java aangeven door een exception-object te gooien via `throw`.

Bijvoorbeeld:

```java
class Breuk {
  private final int teller, noemer;
  public Breuk(int teller, int noemer) {
    if (noemer == 0) throw new IllegalArgumentException("Noemer mag niet 0 zijn");
    this.teller = teller;
    this.noemer = noemer;
  }
}
```

De uitvoering breekt dan onmiddellijk af, en er wordt gezocht naar code die de uitzondering kan opvangen.
Exceptions opvangen doe je via een `try .. catch` block:

```java
try {
  // probeer code uit te voeren
  Breuk breuk = new Breuk(3, 0);
  // gelukt, ga verder met breuk
} catch (IllegalArgumentException e) {
  // wordt (enkel) uitgevoerd wanneer de code in het try-block een IllegalArgumentException gooide
  System.out.println("Oeps");
}
```

Er wordt eerst gekeken naar een try-catch blok in de methode die de methode opgeroepen heeft waarin een uitzondering gegooid werd. Is daar geen try-catch block aanwezig, wordt gekeken in de methode die die methode opgeroepen heeft, etc, tot in de `main`-methode.
Als er geen enkele try-catch block gevonden wordt die de uitzondering kan afhandelen, stopt het programma.

Hierboven gebruikten we **unchecked exceptions**; alle unchecked exceptions erven over van de klasse `RuntimeException`. Java heeft ook **checked exceptions**; deze erven over van de klasse `Exception`. Checked exceptions moeten vermeld worden in de methode-hoofding, en code die de methode oproept moet ze opvangen of ze opnieuw vermelden in de methode-hoofding.

```java
class NoemerIsNul extends Exception {}

class Breuk {
  private final int teller, noemer;
  public Breuk(int teller, int noemer) throws NoemerIsNul { // <- exception moet vermeld worden in hoofding
    if (noemer == 0) throw new NoemerIsNul();
    this.teller = teller;
    this.noemer = noemer;
  }
}

try {
  Breuk breuk = new Breuk(3, 0); // compiler geeft een fout als NoemerIsNul niet opgevangen wordt
} catch (NoemerIsNul e) {
  System.out.println("Oeps");
}
```

Checked exceptions maken het vaak lastig om iets te programmeren, en de voorkeur wordt tegenwoordig vaak gegeven aan unchecked exceptions.
