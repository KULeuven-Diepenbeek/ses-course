---
title: "4.1 Generics"
toc: true
autonumbering: true
---

Vooraleer we de discussie over datastructuren kunnen starten, is het nuttig om eerst te kijken naar **generics**, aangezien generics veelvuldig gebruikt worden in datastructuren.

## Wat en waarom?

Generics zijn een manier om klassen en methodes te voorzien van type-parameters.
Bijvoorbeeld, neem de volgende klasse `ArrayList`[^1]:

[^1]: Deze klasse is geïnspireerd op de ArrayList-klasse die standaard in Java zit.

```java
class ArrayList {
  private Object[] elements;
  public void add(Object element) { /* ... */  }
  public Object get(int index) { /* ... */  }
}
```

Als we deze klasse makkelijk willen kunnen herbruiken, en dus algemeen willen houden, kunnen we nu nog niet zeggen wat het type is van de elementen van de lijst.
Gaan er Student-objecten in de lijst? Of Animal-objecten?
Dat weten we nog niet.
We kiezen daarom voor `Object`, het meest algemene type in Java.

Maar dat betekent ook dat je nu objecten van verschillende, niet-gerelateerde types kan opnemen in dezelfde lijst, hoewel dat niet de bedoeling is.
Stel bijvoorbeeld dat je een lijst van studenten wil bijhouden, dan houdt de compiler je niet tegen om ook andere types van objecten toe te voegen:

```java
ArrayList students = new ArrayList();

Student student = new Student();
students.add(student);

Animal animal = new Animal();
students.add(animal); // <-- compiler vindt dit OK
```

Om dat tegen te gaan, zou je zelf afzonderlijke klassen `AnimalArrayList`, `StudentArrayList`, ... kunnen schrijven waar het bedoelde type van elementen wel duidelijk is, en ook afgedwongen wordt door de compiler.

```java
class AnimalArrayList {
  private Animal[] elements;
  public void add(Animal element) { /* ... */  }
  public Animal get(int index) { /* ... */  }
}

class StudentArrayList {
  private Student[] elements;
  public void add(Student element) { /* ... */  }
  public Student get(int index) { /* ... */  }
}
```

Met deze implementaties is het probleem hierboven opgelost:

```java
StudentArrayList students = new StudentArrayList();
students.add(student); // OK
students.add(animal); // compiler error
```

De prijs is echter veel quasi-identieke implementaties, die enkel verschillen in het type van de elementen.
Dat leidt tot veel onnodige en ongewenste code-duplicatie.

Met generics kan je een _type_ gebruiken als parameter voor een klasse om deze code-duplicatie vermijden.
Generics geven je dus een combinatie van slechts 1 implementatie hebben (zoals `ArrayList`) en ook het specifiëren van een bepaald type (zoals `StudentArrayList`).

De type-parameter staat tussen `<` en `>`, en je kan deze type-parameter vervolgens gebruiken in heel de klasse.
Bijvoorbeeld, volgende klasse is een nieuwe versie van de `ArrayList` klasse van hierboven, maar nu met type-parameter `E`, welke vervolgens gebruikt wordt als type van de elements-array, de parameter van de add-method, en het resultaat-type van de get-method:

```java
class ArrayList<E> {
  private E[] elements;
  public void add(E element) { /* ... */  }
  public E get(int index) { /* ... */  }
}
```

Bij het gebruik van deze klasse moet je dan een type opgeven voor de parameter `E`.
Bijvoorbeeld, als je een lijst met enkel studenten wil, gebruik je `ArrayList<Student>`.
Je kan dan de klasse gebruiken op dezelfde manier als de `StudentArrayList` hierboven.

```java
ArrayList<Student> students = new ArrayList<Student>();
Student someStudent = new Student();
students.add(someStudent);
// students.add(animal); // <-- niet toegelaten (compiler error)
Student firstStudent = students.get(0);
```

Java laat toe om, bij het instantiëren, het type weg te laten met behulp van `<>`:

```java
ArrayList<Student> students = new ArrayList<>(); // <- je hoeft geen tweede keer <Student> te typen
```

Dat type is immers al bepaald door het type van de variabele.

Tenslotte kan een type meerdere type-parameters hebben, bijvoorbeeld een tuple van 3 elementen van mogelijk verschillend type:

```java
class Tuple3<T1, T2, T3> {
  private T1 first;
  private T2 second;
  private T3 third;
  public T1 getFirst() { return first; }
  /* ... */
}
```

### Oefening: Maybe

Schrijf een generische klasse `Maybe` die een object voorstelt dat nul of één element kan bevatten.
Het type van het element wordt bepaald door een generische parameter.
Hieronder zie je hoe de klasse gebruikt kan worden:

```java
public static void print(Maybe<String> maybe) {
  if (maybe.hasValue()) {
    System.out.println("Contains a value: " + maybe.getValue());
  } else {
    System.out.println("No value :(");
  }
}

Maybe<String> maybeAString = new Maybe<>("yes");
Maybe<String> maybeAnotherString = new Maybe<>(null);

print(maybeAString);
print(maybeAnotherString);
```

### Oefening: SuccessOrFail

Schrijf een generische klasse `SuccessOrFail` die een object voorstelt dat één element bevatten.
Dat element heeft 1 van 2 mogelijke types (die types zijn generische parameters).
Het eerste type stelt het type van een succesvol resultaat voor; het tweede type is dat van een fout.
Een voorbeeld van hoe je die klasse kan gebruiken vind je hieronder:

```java
// Resultaat van een operatie die een String teruggeeft bij success,
// en een exception als de operatie gefaald is.
SuccessOrFail<String, Exception> result = new SuccessOrFail<>("The operation was successful", null);
if (result.isSuccess()) {
    System.out.println("Operation was successful: " + result.getResult());
} else {
    System.out.println("Operation failed: " + result.getError());
}

// Resultaat van een operatie die een int (Integer) teruggeeft bij success,
// en een String (foutmelding) als de operatie gefaald is.
SuccessOrFail<Integer, String> result2 = new SuccessOrFail<>(null, "Division by zero");
if (result2.isSuccess()) {
    System.out.println("Operation was successful: " + result2.getResult());
} else {
    System.out.println("Operation failed: " + result2.getError());
}
```

## Behavioral subtyping (substitutie-principe)

Stel we hebben klassen `Animal`, `Mammal`, `Cat`, `Dog`, en `Bird` met een overervingsrelatie:

<div style="display: grid; grid-template-columns: 1fr 1fr; align-items: center;">

```java
class Animal { /* ... */ }
class Mammal extends Animal { /* ... */ }
class Cat extends Mammal { /* ... */ }
class Dog extends Mammal { /* ... */ }
class Bird extends Animal { /* ... */ }
```

```mermaid
graph BT
Cat --> Mammal
Dog --> Mammal
Mammal --> Animal
Bird --> Animal
```

</div>

Het behavioral subtyping-principe (soms ook het Liskov substitutie-principe genoemd) zegt dat **overal waar een object van type `T` verwacht wordt, ook een object van een subtype van `T` toegelaten wordt**.
De Java compiler zal deze regel respecteren.
Bijvoorbeeld, volgende toekenningen maken gebruik van het dit principe, en zijn dus toegelaten:

```java
Animal animal = new Cat();
Mammal mammal = new Dog();
animal = new Bird();
```

maar `mammal = new Bird();` is bijvoorbeeld niet toegelaten.

In onderstaande code is de eerste oproep toegelaten (cat heeft type `Cat`, en dat is een subtype van `Mammal`), maar de tweede niet (cat is geen `Dog`) en de derde ook niet (`Cat` is geen subtype van `Bird`):

```java
static void pet(Mammal mammal) { /* ... */ }
static void bark(Dog dog) { /* ... */ }
static void layEgg(Bird bird) { /* ... */ }

Cat cat = new Cat();
pet(cat); // <- toegelaten (voldoet aan principe)
bark(cat); // <- niet toegelaten (compiler error)
layEgg(cat); // <- niet toegelaten (compiler error)
```

### Toegepast op lijsten

Een lijst in Java is een geordende groep van elementen van hetzelfde type.
`List<E>` is de interface[^2] die aan de basis ligt van alle lijsten.
`ArrayList<E>` is een klasse die een lijst implementeert met behulp van een array.
`ArrayList<E>` is een subtype van `List<E>`; volgens het principe kan dus, overal waar een lijst-object verwacht wordt, een lijst gebruikt worden die geïmplementeerd is met arrays.
Er bestaat ook een interface `Collection<E>`, wat een willekeurige groep van elementen voorstelt (niet enkel een lijst, maar bijvoorbeeld ook verzamelingen (`Set`) of wachtrijen (`Queue`)).
`List<E>` is een subtype van `Collection<E>`. Bijgevolg is ook `ArrayList<E>` een subtype van `Collection<E>`.

In code ziet deze situatie er als volgt uit:

<div style="display: grid; grid-template-columns: 1fr 1fr; align-items: center;">

```java
interface Collection<E> {
  public void add(E element);
  public int size();
  /* ... */
}
interface List<E> extends Collection<E> {
  public E get(int index);
  /* ... */
}
class ArrayList<E> implements List<E> {
  private E[] elements;
  /* ... */
}
interface Set<E> extends Collection<E> { /* ... */ }
interface Queue<E> extends Collection<E> { /* ... */ }
```

```mermaid
graph BT
Y1["Set#lt;E>"] --> Z0
X0["ArrayList#lt;E>"] --> Y0["List#lt;E>"] --> Z0["Collection#lt;E>"]
Y2["Queue#lt;E>"] --> Z0
style Y1 fill:#eee,stroke:#aaa,color:#888
style Y2 fill:#eee,stroke:#aaa,color:#888
```

</div>

[^2]: Een interface kan je zien als een abstracte klasse waarvan alle methodes abstract zijn. Het defineert alle methodes die geïmplementeerd moeten worden, maar bevat zelf geen implementatie.

<div style="display: grid; grid-template-columns: 1fr 1fr; align-items: center; gap: 2rem">

<div>
In deze situatie is hetvolgende geldig:

```java
List<Cat> cats = new ArrayList<Cat>();
Collection<Dog> dogs = new ArrayList<Dog>();
List<Animal> animals = new ArrayList<Animal>();
```

maar hetvolgende kan uiteraard niet:

```java
Collection<Dog> dogs = new ArrayList<Cat>(); // compileert niet
```

</div>

```mermaid
graph BT
X1["ArrayList#lt;Cat>"] --> Y1["List#lt;Cat>"] --> Z1["Collection#lt;Cat>"]
X2["ArrayList#lt;Dog>"] --> Y2["List#lt;Dog>"] --> Z2["Collection#lt;Dog>"]
X3["ArrayList#lt;Animal>"] --> Y3["List#lt;Animal>"] --> Z3["Collection#lt;Animal>"]
```

</div>

</div>

Het lijkt vervolgens misschien logisch dat `ArrayList<Cat>` ook een subtype moet zijn van `ArrayList<Animal>`.
Een lijst van katten lijkt tenslotte toch een speciaal geval te zijn van een lijst van dieren?
Maar dat is niet het geval.

<div style="display: grid; grid-template-columns: 1fr 1fr; align-items: center;">

```java
ArrayList<Animal> = new ArrayList<Cat>(); // compileert niet
```

```mermaid
graph BT
id1(Niet correct)
style id1 fill:#f99,stroke:#600,stroke-width:4px
X0["ArrayList#lt;Cat>"] -- #times; --> Y0["ArrayList#lt;Animal>"]
```

</div>

Waarom niet?
Stel dat `ArrayList<Cat>` toch een subtype zou zijn van `ArrayList<Animal>`. Dan zou volgende code ook geldig zijn:

```java
ArrayList<Cat> cats = new ArrayList<Cat>();
ArrayList<Animal> animals = cats; // <- dit zou geldig zijn (maar is het niet!)
Dog dog = new Dog();
animals.add(dog); // <- OOPS: er zit nu een hond in de lijst van katten
```

Je zou dus honden kunnen toevoegen aan je lijst van katten zonder dat de compiler je waarschuwt, en dat is niet gewenst.
Om die reden beschouwt Java beide klassen dus niet als subtype van elkaar.

### Oefening: behavioral subtyping

Vetrek van volgende klasse-hiërarchie en zeg van elk van volgende lijnen code of ze toegelaten worden door de Java compiler:

```mermaid
graph BT
Bike --> Vehicle
Motorized --> Vehicle
Car --> Motorized
Plane --> Motorized
```

```java
/* 1 */  Motorized myCar = new Car();
/* 2 */  Vehicle yourPlane = new Plane();
/* 3 */  Collection<Vehicle> vehicles = new ArrayList<Vehicle>();
/* 4 */  vehicles.add(myCar);
/* 5 */  List<Car> cars = new ArrayList<Car>();
/* 6 */  List<Vehicle> carsAsVehicles = cars;
```

## Covariantie en contravariantie

We zagen hierboven dat `List<Cat>` en `List<Animal>` niets met elkaar te maken hebben, ondanks dat `Cat` een subtype is van `Animal`.
In sommige situaties willen we wel zo'n relatie kunnen leggen.

### Covariantie: ? extends T

Wat als we een methode willen schrijven die de dieren uit een gegeven lijst toevoegt aan een andere lijst van dieren? Bijvoorbeeld:

```java
public static void copyFromTo(ArrayList<Animal> source, ArrayList<Animal> target) {
  for (Animal a : source) { target.add(a); }
}

ArrayList<Animal> target = new ArrayList<>();
ArrayList<Cat> cats = /* ... */
ArrayList<Dog> dogs = /* ... */
/* ... */
copyFromTo(dogs, target); // niet toegelaten
copyFromTo(cats, target); // niet toegelaten
```

Volgens de regels die we hierboven gezien hebben, kunnen we deze methode nu niet gebruiken om de dieren uit een lijst van honden of katten te kopiëren naar een lijst van dieren.
Maar dat lijkt wel een zinnige operatie.
Een oplossing kan zijn om verschillende versies van de methode te schrijven:

```java
public static void copyFromCatsTo(ArrayList<Cat> source, ArrayList<Animal> target) {
  for (Cat cat : source) { target.add(cat); }
}
public static void copyFromDogsTo(ArrayList<Dog> source, ArrayList<Animal> target) {
  for (Dog dog : source) { target.add(dog); }
}
public static void copyFromBirdsTo(ArrayList<Bird> source, ArrayList<Animal> target) {
  for (Bird bird : source) { target.add(bird); }
}
```

{{% notice note %}}
Merk op dat de oproep `target.add(cat)`, alsook die met `dog` en `bird`, toegelaten is, omdat `Cat`, `Dog` en `Bird` subtypes zijn van `Animal`.
{{% /notice %}}

Maar dan lopen we opnieuw tegen het probleem van gedupliceerde code aan.
Java voorziet een oplossing, doormiddel van het **wildcard-type `<? extends T>`**.
We kunnen bovenstaande methode dus schrijven als

```java
public static void copyFromTo_wildcard(ArrayList<? extends Animal> source, ArrayList<Animal> target) {
  for (Animal a : source) { target.add(a); }
}
```

Het type `ArrayList<? extends Animal>` staat dus voor _"elke ArrayList waar het element-type een subtype is van `Animal`"_.
Volgende code is nu toegelaten:

```java
copyFromTo_wildcard(dogs, target); // OK!
copyFromTo_wildcard(cats, target); // OK!
```

Dit heet **covariantie**: omdat `Cat` een subtype is van `Animal`, is `ArrayList<Cat>` een subtype van `ArrayList<? extends Animal>`.
De 'co' in covariantie wijst erop dat de overervingsrelatie tussen `Cat` en `Animal` in dezelfde richting loopt als die tussen `ArrayList<Cat>` en `ArrayList<? extends Animal>` (in tegenstelling tot contravariantie, wat zodadelijk aan bod komt).

```mermaid
graph BT
ALCat["ArrayList#lt;Cat>"]
ALextendsAnimal["ArrayList#lt;? extends Animal>"]
ALAnimal["ArrayList#lt;Animal>"]
ALAnimal --> ALextendsAnimal
ALCat --> ALextendsAnimal

Cat --> Animal
```

TODO: relatie List-ArrayList bespreken
Het plaatje wordt nu heel wat complexer:

```mermaid
graph BT
ALCat["ArrayList#lt;Cat>"]
ALextendsAnimal["ArrayList#lt;? extends Animal>"]
LCat["List#lt;Cat>"]
LextendsAnimal["List#lt;? extends Animal>"]
LAnimal["List#lt;Animal>"]
ALAnimal["ArrayList#lt;Animal>"]
ALCat --> LCat
LAnimal --> LextendsAnimal
ALAnimal --> LAnimal
ALAnimal --> ALextendsAnimal
ALextendsAnimal --> LextendsAnimal
LCat --> LextendsAnimal
ALCat --> ALextendsAnimal

Cat --> Animal
```

#### Oefening: covariantie

Breid het schema hierboven uit met de wildcard `? extends Cat`.

```mermaid
graph BT
ALCat["ArrayList#lt;Cat>"]
ALextendsAnimal["ArrayList#lt;? extends Animal>"]
ALextendsCat["ArrayList#lt;? extends Cat>"]
LCat["List#lt;Cat>"]
LextendsCat["List#lt;? extends Cat>"]
LextendsAnimal["List#lt;? extends Animal>"]
LAnimal["List#lt;Animal>"]
ALAnimal["ArrayList#lt;Animal>"]
ALCat --> ALextendsCat
ALCat --> LCat
LCat --> LextendsCat
LextendsCat --> LextendsAnimal
LAnimal --> LextendsAnimal
ALAnimal --> LAnimal
ALAnimal --> ALextendsAnimal
ALextendsAnimal --> LextendsAnimal
ALextendsCat --> ALextendsAnimal
ALextendsCat --> LextendsCat
```

### Contravariantie: ? super T

Wat als we het omgekeerde willen van hierboven: een methode die katten uit een gegeven lijst toevoegt aan een andere lijst waarin katten kunnen voorkomen (maar eventueel ook andere objecten)? Bijvoorbeeld:

```java
public static void copyFromCatsTo(ArrayList<Cat> source, ArrayList<Cat> target) {
  for (Cat cat : source) { target.add(a); }
}

ArrayList<Cat> cats = /* ... */

ArrayList<Cat> otherCats = new ArrayList<>();
ArrayList<Animal> animals = new ArrayList<>();
ArrayList<Object> objects = new ArrayList<>();

copyFromTo(cats, otherCats); // OK
copyFromTo(cats, animals); // niet toegelaten
copyFromTo(cats, objects); // niet toegelaten
```

De laatste twee regels zijn niet toegelaten, maar zouden opnieuw erg nuttig kunnen zijn.
Aparte methodes schrijven leidt opnieuw tot code-duplicatie:

```java
public static void copyFromCatsToCats(ArrayList<Cat> source, ArrayList<Cat> target) {
  for (Cat cat : source) { target.add(a); }
}
public static void copyFromCatsToAnimals(ArrayList<Cat> source, ArrayList<Animal> target) {
  for (Cat cat : source) { target.add(a); }
}
public static void copyFromCatsToObjects(ArrayList<Cat> source, ArrayList<Objects> target) {
  for (Cat cat : source) { target.add(a); }
}
```

De oplossing in dit geval is gebruik maken van het **wildcard-type `<? super T>`**.
Het type `ArrayList<? super Cat>` staat dus voor _"elke ArrayList waar het element-type een supertype is van `Cat`"_ (of het type `Cat` zelf).
We kunnen dus schrijven:

```java
public static void copyFromCatsTo_wildcard(ArrayList<Cat> source, ArrayList<? super Cat> target) {
  for (Cat cat : source) { target.add(a); }
}
```

en kunnen nu hetvolgende uitvoeren:

```java
copyFromCatsTo_wildcard(cats, otherCats); // OK
copyFromCatsTo_wildcard(cats, animals); // OK
copyFromCatsTo_wildcard(cats, objects); // OK
```

Dit heet **contravariantie**: hoewel `Cat` een subtype is van `Animal`, is `ArrayList<Animal>` een subtype van `ArrayList<? super Cat>`.
De 'contra' in contravariantie wijst erop dat de overervingsrelatie tussen `Cat` en `Animal` in de omgekeerde richting loopt als die tussen `ArrayList<? super Cat>` en `ArrayList<Animal>`.

```mermaid
graph BT
ALsuperCat["ArrayList#lt;? super Cat>"]
ALAnimal["ArrayList#lt;Animal>"]

ALAnimal --> ALsuperCat

Cat --> Animal
```

Als we ook `ArrayList<Object>` en `ArrayList<? super Animal>` toevoegen aan het plaatje, ziet dat er als volgt uit:

```mermaid
graph BT
ALCat["ArrayList#lt;Cat>"]
ALsuperCat["ArrayList#lt;? super Cat>"]
ALsuperAnimal["ArrayList#lt;? super Animal>"]
ALAnimal["ArrayList#lt;Animal>"]
ALObject["ArrayList#lt;Object>"]
ALCat --> ALsuperCat
ALAnimal --> ALsuperAnimal
ALObject --> ALsuperAnimal
ALsuperAnimal --> ALsuperCat

Cat --> Animal
Animal --> Object
```

### PECS

Als we covariantie en contravariantie combineren, krijgen we volgend beeld:

```mermaid
graph BT
ALAnimal["ArrayList#lt;Animal>"]
ALCat["ArrayList#lt;Cat>"]
ALsuperCat["ArrayList#lt;? super Cat>"]
ALsuperAnimal["ArrayList#lt;? super Animal>"]
ALextendsAnimal["ArrayList#lt;? extends Animal>"]

ALAnimal --> ALextendsAnimal
ALCat --> ALextendsAnimal
ALAnimal --> ALsuperAnimal
ALCat --> ALsuperCat
ALsuperAnimal --> ALsuperCat

Cat --> Animal
Animal --> Object
```

Wanneer gebruik je wat?
PECS: Producer Extends, Consumer Super

### Arrays en type erasure

In tegenstelling tot ArrayLists (en andere generische types), beschouwt Java arrays wel als co-variant.
Dat betekent dat `Cat[]` een subtype is van `Animal[]`.
Volgende code compileert dus (maar gooit een uitzondering bij het uitvoeren):

```java
Animal[] cats = new Cat[2];
cats[0] = new Dog(); // compileert, maar faalt tijdens het uitvoeren
```

De reden hiervoor is, in het kort, dat informatie over generics gewist wordt bij het compileren.
Dit heet **type erasure**.
In de gecompileerde code is een `ArrayList<Animal>` en `ArrayList<Cat>` dus exact hetzelfde.
Er kan dus, tijdens de uitvoering, niet gecontroleerd worden of je steeds het juiste type gebruikt.

Bij arrays wordt er _wel_ type-informatie bijgehouden na het compileren, en kan dus gecontroleerd worden dat je geen ongeldig getypeerde elementen toevoegt.

## Labo-oefeningen

TODO

## Denkvragen

TODO

## Extra leermateriaal

Er is erg veel informatie te vinden op het internet over generics in Java.
