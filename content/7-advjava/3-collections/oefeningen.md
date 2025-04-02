---
title: "Oefeningen"
toc: true
weight: 100
autonumbering: true
author: "Koen Yskout"
draft: false
math: true
---

## De Collections API

### IntRange

1. Schrijf eerst een klasse `IntRangeIterator` die `Iterator<Integer>` implementeert, en alle getallen teruggeeft tussen twee grensgetallen `lowest` en `highest` (beiden inclusief) die je meegeeft aan de constructor. Je houdt hiervoor enkel de onder- en bovengrens bij, alsook het volgende terug te geven getal.
2. Schrijf nu ook een record `IntRange` die `Iterable<Integer>` implementeert, en die een `IntRangeIterator`-object aanmaakt en teruggeeft.
   Je moet deze klasse nu als volgt kunnen gebruiken:

```java
IntRange range = new IntRange(3, 6);
for (int x : range) {
  System.out.println(x);
}
// Uitvoer:
// 3
// 4
// 5
// 6

```

{{% notice note %}}
Java laat niet toe om primitieve types als generische parameters te gebruiken.
Voor elk primitief type bestaat er een wrapper-klasse, bijvoorbeeld `Integer` voor `int`.
Daarom gebruiken we hierboven bijvoorbeeld `Iterator<Integer>` in plaats van `Iterator<int>`.
Achter de schermen worden `int`-waarden automatisch omgezet in `Integer`-objecten en omgekeerd.
Dat heet [auto-boxing en -unboxing](https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html).
Je kan beide types in je code grotendeels door elkaar gebruiken zonder problemen.
{{% /notice %}}

## Lijsten

### MyArrayList

Schrijf zelf een simpele klasse `MyArrayList<E>` die werkt zoals de ArrayList uit Java.
Voorzie in je lijst een initiële capaciteit van 4, maar zonder elementen.
Implementeer volgende operaties:

- `int size()` die de grootte (het huidig aantal elementen in de lijst) teruggeeft
- `int capacity()` die de huidige capaciteit (het aantal plaatsen in de array) van de lijst teruggeeft
- `E get(int index)` om het element op positie `index` op te vragen (of een `IndexOutOfBoundsException` indien de index ongeldig is)
- `void add(E element)` om een element achteraan toe te voegen (en de onderliggende array dubbel zo groot te maken indien nodig)
- `void remove(int index)` om het element op plaats index te verwijderen (of een `IndexOutOfBoundsException` indien de index ongeldig is). De capaciteit moet niet terug dalen als er veel elementen verwijderd werden (dat gebeurt in Java ook niet).
- `E last()` om het laatste element terug te krijgen (of een `NoSuchElementException` indien de lijst leeg is)

Hier vind je een test die een deel van dit gedrag controleert:

{{% notice style=code title="Testcode" expanded=false %}}
```java
@Test
public void test_my_arraylist() {
    MyArrayList<String> lst = new MyArrayList<>();
    // initial capacity and size
    assertThat(lst.capacity()).isEqualTo(4);
    assertThat(lst.size()).isEqualTo(0);

    // adding elements
    for (int i = 0; i < 4; i++) {
        lst.add("item" + i);
    }
    assertThat(lst.size()).isEqualTo(4);
    assertThat(lst.capacity()).isEqualTo(4);
    assertThat(lst.last()).isEqualTo("item3");

    // adding more elements
    for (int i = 4; i < 10; i++) {
        lst.add("item" + i);
    }
    assertThat(lst.size()).isEqualTo(10);
    assertThat(lst.capacity()).isEqualTo(16);
    assertThat(lst.last()).isEqualTo("item9");

    // remove an element
    lst.remove(3);
    assertThat(lst.size()).isEqualTo(9);
    assertThat(lst.capacity()).isEqualTo(16);
    assertThat(lst.get(3)).isEqualTo("item4");
    assertThatThrownBy(() -> lst.get(10)).isInstanceOf(IndexOutOfBoundsException.class);
}
```
{{% /notice %}}

### Linked list

Schrijf zelf een klasse `MyLinkedList<E>` om een **dubbel** gelinkte lijst voor te stellen. Voorzie volgende operaties:

- `int size()` om het aantal elementen terug te geven
- `void add(E element)` om het gegeven element achteraan toe te voegen
- `E get(int index)` om het element op positie `index` op te vragen
- `void remove(int index)` om het element op positie `index` te verwijderen

Hieronder vind je enkele tests voor je klasse. Je zal misschien merken dat je implementatie helemaal juist krijgen niet zo eenvoudig is als het op het eerste zicht lijkt, zeker bij de `remove`-methode.
Gebruik de [visuele voorstelling van eerder](lijsten.md#linkedlist), en ga na wat je moet doen om elk van de getekende knopen te verwijderen.

{{% notice style=code title="Testcode" expanded=false %}}
```java
@Test
public void add_first_element() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    lst.add("element0");
    assertThat(lst.size()).isEqualTo(1);
    assertThat(lst.get(0)).isEqualTo("element0");
}

@Test
public void add_more_elements() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    lst.add("element0");
    lst.add("element1");
    assertThat(lst.size()).isEqualTo(2);
    assertThat(lst.get(0)).isEqualTo("element0");
    assertThat(lst.get(1)).isEqualTo("element1");

    lst.add("element2");
    assertThat(lst.size()).isEqualTo(3);
    assertThat(lst.get(0)).isEqualTo("element0");
    assertThat(lst.get(1)).isEqualTo("element1");
    assertThat(lst.get(2)).isEqualTo("element2");
}

@Test
public void remove_elements() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    lst.add("element0");
    lst.add("element1");
    lst.add("element2");

    lst.remove(1);
    assertThat(lst.size()).isEqualTo(2);
    assertThat(lst.get(0)).isEqualTo("element0");
    assertThat(lst.get(1)).isEqualTo("element2");

    lst.remove(1);
    assertThat(lst.size()).isEqualTo(1);
    assertThat(lst.get(0)).isEqualTo("element0");
}

@Test
public void get_from_empty() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    assertThatIndexOutOfBoundsException().isThrownBy(() -> lst.get(0));
}

@Test
public void remove_from_empty() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    assertThatIndexOutOfBoundsException().isThrownBy(() -> lst.remove(0));
}

@Test
public void get_from_single() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    lst.add("element0");
    assertThat(lst.get(0)).isEqualTo("element0");
}

@Test
public void remove_from_single() {
    MyLinkedList<String> lst = new MyLinkedList<>();
    lst.add("element0");
    lst.remove(0);
    assertThat(lst.size()).isEqualTo(0);
}
```
{{% /notice %}}

## Wachtrijen

### MyFIFO

Implementeer zelf een klasse `MyFIFO<E>` die een FIFO queue voorstelt met beperkte capaciteit, en die gebruik maakt van een array om de elementen te bewaren.
De capaciteit wordt opgegeven bij het aanmaken.
Invoegen en terug verwijderen van elementen moet zeer efficiënt zijn (\\(\mathcal{O}(1)\\)).
Implementeer volgende operaties:

- `size()`: het aantal elementen in je queue
- `E poll()`: het hoofd van je FIFO queue opvragen en verwijderen; geeft null terug indien de queue leeg is
- `boolean add(E)`: een element (niet-null) toevoegen aan je queue; geeft false terug indien er niet voldoende capaciteit is

Hieronder vind je enkele tests.

{{% notice style=code title="Testcode" expanded=false %}}
```java
@Test
public void empty_fifo_size_0() {
    MyFIFO<String> fifo = new MyFIFO<>(5);
    assertThat(fifo.size()).isEqualTo(0);
}

@Test
public void fifo_one_element() {
    MyFIFO<String> fifo = new MyFIFO<>(5);
    fifo.add("first");
    assertThat(fifo.size()).isEqualTo(1);
    assertThat(fifo.poll()).isEqualTo("first");
}

@Test
public void fifo_maximum_capacity() {
    MyFIFO<String> fifo = new MyFIFO<>(5);
    for (var e : List.of("first", "second", "third", "fourth", "fifth")) {
        assertThat(fifo.add(e)).isTrue();
    }
    assertThat(fifo.size()).isEqualTo(5);

    assertThat(fifo.add("sixth")).isFalse();
    assertThat(fifo.size()).isEqualTo(5);
}

@Test
public void fifo_poll_correct_order() {
    MyFIFO<String> fifo = new MyFIFO<>(5);
    for (var e : List.of("first", "second", "third", "fourth", "fifth")) {
        assertThat(fifo.add(e)).isTrue();
    }
    assertThat(fifo.poll()).isEqualTo("first");
    assertThat(fifo.poll()).isEqualTo("second");
    assertThat(fifo.poll()).isEqualTo("third");
    assertThat(fifo.size()).isEqualTo(2);
    assertThat(fifo.poll()).isEqualTo("fourth");
    assertThat(fifo.poll()).isEqualTo("fifth");
    assertThat(fifo.size()).isEqualTo(0);
}
```
{{% /notice %}}

{{% notice info Denkvraag %}}
Wat zou je moeten doen om je MyFIFO-klasse dynamisch te laten groeien als er meer elementen aan toegevoegd worden?
{{% /notice %}}

### Priority boarding

Hieronder is een record die een vliegtuigpassagier voorstelt.

```java
record Passenger(String name, int priority) { }
```

Maak een klasse `PriorityBoarding` waar je een PriorityQueue gebruikt om passagiers te laten boarden.
Passagiers mogen het vliegtuig betreden volgens afnemende prioriteit (prioriteit 3 mag voor 2), en bij gelijke prioriteit, in alfabetische volgorde (A mag voor B).
Maak daarvoor twee operaties in je klasse:

- `checkin(Passenger p)` om een passagier toe te voegen aan de lijst van passagiers die kunnen instappen
- `Passenger nextPassenger()` die de volgende passagier teruggeeft die mag instappen, of `null` indien er geen passagiers meer zijn.

Hieronder vind je een test.

_Hint_: schrijf een Comparator. Je kan daarbij gebruik maken van de statische methodes uit de Comparator-interface.

{{% notice style=code title="Testcode" expanded=false %}}
```java
@Test
public void priority_example() {
    var p1 = new Passenger("Bob", 1);
    var p2 = new Passenger("Alex", 2);
    var p3 = new Passenger("Charlie", 3);
    var p4 = new Passenger("Donald", 3);

    var prio = new PriorityBoarding();
    prio.checkin(p4);
    prio.checkin(p1);
    prio.checkin(p3);
    prio.checkin(p2);

    assertThat(prio.nextPassenger()).isEqualTo(p1);
    assertThat(prio.nextPassenger()).isEqualTo(p2);
    assertThat(prio.nextPassenger()).isEqualTo(p3);
    assertThat(prio.nextPassenger()).isEqualTo(p4);
}
```
{{% /notice %}}

## Sets

### Unieke hashcode
Test uit hoe belangrijk het is dat de hashcodes van verschillende objecten in een HashSet goed verdeeld zijn aan de hand van de code hieronder.
Deze code meet hoelang het duurt om een HashSet te vullen met 50000 objecten; de eerste keer met goed verspreide hashcodes, en de tweede keer een keer met steeds dezelfde hashcode. Voer uit; merk je een verschil?

```java
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Timing {

    record DefaultHashcode(int i) {}
    record CustomHashcode(int i) {
        @Override
        public int hashCode() {
            return 4;
        }
    }

    public static void main(String[] args) {
        System.out.print("With default hashcode: ");
        test(DefaultHashcode::new);
        System.gc();
        System.out.print("With identical hashcode: ");
        test(CustomHashcode::new);
    }

    private static <T> void test(Function<Integer, T> ctor) {
        var set = new HashSet<T>();
        var start = System.nanoTime();
        for (int i = 0; i < 50_000; i++) {
            set.add(ctor.apply(i));
        }
        var end = System.nanoTime();
        System.out.println(set.size() + " elements added in " + TimeUnit.NANOSECONDS.toMillis(end - start) + "ms");
    }
}

```

### Veranderende hashcode

Is het nodig dat de hashCode van een object hetzelfde blijft doorheen de levensduur van het object, of mag deze veranderen?
Verklaar je antwoord.

{{% notice style=tip title=Antwoord expanded=false %}}
Nee, deze mag niet veranderen. Mocht die wel veranderen, kan het zijn dat je een object niet meer terugvindt in een set, omdat er (door de veranderde hashcode) in een andere bucket gezocht wordt dan waar het object zich bevindt.

{{% notice style=code title=Voorbeeldcode expanded=false %}}
```java
import java.util.HashSet;
import java.util.Set;

public class Demo {

    static class MyObject {
        public int hashCode = 1;

        @Override
        public int hashCode() {
            return hashCode;
        }
    }

    public static void main(String[] args) {
        Set<MyObject> set = new HashSet<>();

        MyObject obj = new MyObject();
        set.add(obj);
        System.out.println("Before changing hashcode");
        System.out.println("Contains: " + set.contains(obj));

        obj.hashCode++;

        System.out.println("After changing hashcode");
        System.out.println("Contains: " + set.contains(obj));
    }
}
```
{{% /notice %}}
{{% /notice %}}

### Boomstructuur

```mermaid
flowchart TB
subgraph s0 ["d=0"]
direction LR
R
end
subgraph s1 ["d=1"]
direction LR
A
X10[" "]
X11[" "]
B
end
subgraph s2 ["d=2"]
direction LR
C
D
X20[" "]
X21[" "]
X22[" "]
end
subgraph s3 ["d=3"]
direction LR
X30[" "]
X31[" "]
E
X32[" "]
X33[" "]
X34[" "]
end
R --> A
R --> B
A --> C
A --> D
D --> E

classDef empty fill:none,stroke:none
classDef depth stroke:none
class X10,X11,X20,X21,X22,X30,X31,X32,X33,X34 empty
class s0,s1,s2,s3 depth
```

Stel dat je een binaire boom hebt: een boomstructuur hebt waar elke knoop in de boom maximaal 2 kinderen heeft, zoals het voorbeeld hierboven.
- De **diepte** van een knoop is de afstand van die knoop tot de wortelknoop (bv. in de boom hierboven heeft knoop A diepte 1, knoop E diepte 3, en knoop R diepte 0).
- De **hoogte** van de boom is de maximale diepte van alle knopen in de boom (of dus: de diepte van de knoop die het verst van de wortel ligt). In het voorbeeld ligt knoop E het verst (met diepte 3), dus de hoogte van deze boom is 3.

1. wat is de **maximale** hoogte van een binaire boom met \\(n\\) knopen?
2. wat is het maximaal aantal elementen met diepte gelijk aan \\(d\\) in een binaire boom?
3. wanneer heeft een binaire boom met \\(n\\) knopen een **minimale** hoogte? Wat is die hoogte?

{{% notice style=tip title=Antwoord expanded=false %}}
1. Om de maximale hoogte te bekomen, geven we elke knoop slechts 1 kind. We krijgen dan gewoon een lineaire ketting van \(n\) knopen, en de hoogte daarvan is \(n-1\).
2. Op diepte \(d\) kunnen zich in totaal maximaal \(2^d\) knopen bevinden.
3. De minimale hoogte bekomen we wanneer alle knopen precies 2 kinderen hebben (behalve de knopen op het diepste niveau).
   Veronderstellen we dus dat de boom \(n\) elementen bevat en elke laag volledig opgevuld is, dan bevat die
   $$n = 2^0 + 2^1 + ... + 2^h = 2^{h+1} - 1 $$ knopen, met \(h\) de hoogte van de boom.
   Daaruit leiden we af dat \( h = \log_2(n+1) - 1 \in \mathcal{O}(\log_2 n) \).
   Met andere woorden, de hoogte van de boom groeit _logaritmisch_ in functie van het aantal elementen.

{{% /notice %}}

### Scheduler

Maak een record `Job` met attributen time (je kan gewoon een double gebruiken) en een description (String).
Maak ook een klasse `Scheduler` die een TreeSet gebruikt om volgende methodes te implementeren:

- `schedule(Job job)` om de gegeven job toe te voegen aan de scheduler
- `List<Job> allJobs()` om alle jobs (in volgorde van hun tijd) terug te krijgen
- `Job nextJob(double after)` om de eerstvolgende job op of na het gegeven tijdstip terug te vinden. (_Hint_: bekijk eerst of er bruikbare methodes zijn in de klasse [`TreeSet`](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/util/TreeSet.html).)

Hieronder vind je een test die deze methodes gebruikt.
```java
@Test
public void test_scheduler() {
    var scheduler = new Scheduler();
    var job1 = new Job(1, "First job");
    var job2 = new Job(2, "Second job");
    var job3 = new Job(3, "Third job");

    scheduler.schedule(job3);
    scheduler.schedule(job1);
    scheduler.schedule(job2);

    assertThat(scheduler.allJobs()).containsExactly(job1, job2, job3);
    assertThat(scheduler.nextJob(2)).isEqualTo(job2);
    assertThat(scheduler.nextJob(2.5)).isEqualTo(job3);
}
```

## Maps

### Set implementeren met Map

Leg uit hoe je een HashSet zou kunnen implementeren gebruik makend van een HashMap.
(Dit is ook wat Java (en Python) doen in de praktijk.)

{{% notice style=tip title=Antwoord expanded=false %}}
Je gebruikt de elementen die je in de set wil opslaan als sleutel (key), en als waarde (value) neem je een willekeurig object.
Als het element in de HashMap een geassocieerde waarde heeft, zit het in de set; anders niet.
{{% /notice %}}

### Parking

Maak een klasse `Parking` die gebruikt wordt voor betalend parkeren.
Kies een of meerdere datastructuren om volgende methodes te implementeren:

- `enterParking(String licensePlate)`: een auto rijdt de parking binnen
- `double amountToPay(String licensePlate)`: bereken het te betalen bedrag voor de gegeven auto (nummerplaat). De parking kost 2 euro per begonnen uur.
- `pay(String licensePlate)`: markeer dat de auto met de gegeven nummerplaat betaald heeft
- `boolean leaveParking(String licensePlate)`: geef terug of de gegeven auto de parking mag verlaten (betaald heeft), en verwijder de auto uit het systeem indien betaald werd.

Om te werken met huidige tijd en intervallen tussen twee tijdstippen, kan je gebruik maken van [`java.time.Instant`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/Instant.html).

```java
@Test
public void test_parking() throws InterruptedException {
    Parking parking = new Parking();
    parking.enterParking("ABC123");
    Thread.sleep(Duration.ofSeconds(1));
    var amount = parking.amountToPay("ABC123");
    assertThat(amount).isEqualTo(2.0);
    assertThat(parking.leaveParking("ABC123")).isFalse();
    parking.pay("ABC123");
    assertThat(parking.leaveParking("ABC123")).isTrue();
}
```

### MultiMap

Schrijf een klasse `MultiMap` die een map voorstelt, maar waar bij elke key een _verzameling_ (Set) van waarden hoort in plaats van slechts één waarde.
Gebruik een `Map` in je implementatie.

Hieronder vind je enkele tests, die ook aangeven welke methodes je moet voorzien.

```java
public class MultiMapTest {

    @Test
    public void empty_multimap() {
        var mm = new MultiMap<Integer, String>();
        assertThat(mm.size()).isEqualTo(0);
    }

    @Test
    public void add_one_element() {
        var mm = new MultiMap<Integer, String>();
        mm.put(1, "first");
        assertThat(mm.size()).isEqualTo(1);
        assertThat(mm.get(1)).containsExactly("first");
    }

    @Test
    public void add_multiple_with_same_key() {
        var mm = new MultiMap<Integer, String>();
        mm.put(1, "first");
        mm.put(1, "second");
        mm.put(1, "third");
        mm.put(2, "fourth");
        mm.put(2, "fifth");
        assertThat(mm.size()).isEqualTo(2);
        assertThat(mm.get(1)).containsExactlyInAnyOrder("first", "second", "third");
        assertThat(mm.get(2)).containsExactlyInAnyOrder("fourth", "fifth");
    }

    @Test
    public void remove_one_value() {
        var mm = new MultiMap<Integer, String>();
        mm.put(1, "first");
        mm.put(1, "second");
        mm.put(1, "third");
        assertThat(mm.size()).isEqualTo(1);
        mm.remove(1, "second");
        assertThat(mm.size()).isEqualTo(1);
        assertThat(mm.get(1)).containsExactlyInAnyOrder("first", "third");
    }
}
```