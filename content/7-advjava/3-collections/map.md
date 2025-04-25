---
title: "7.3.6 Maps"
toc: true
weight: 60
autonumbering: true
author: "Koen Yskout"
draft: false
math: true
---

## Map (Dictionary)

De collecties hierboven stellen allemaal een groep elementen voor, en erven over van de `Collection`-interface.
Een [`Map`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html) is iets anders.
Hier worden **sleutels** bijgehouden, en bij elke sleutel hoort een **waarde** (een object).
Denk aan een telefoonboek, waar bij elke naam (de sleutel) een telefoonnummer (de waarde) hoort, of een woordenboek waar bij elk woord (de sleutel) een definitie hoort (de waarde).
Een andere naam voor een map is dan ook een **dictionary**.
Sleutels mogen slechts één keer voorkomen; eenzelfde waarde mag wel onder meerdere sleutels opgeslagen worden.

De interface `Map<K, V>` heeft twee generische parameters: een (`K`) voor het type van de sleutels, een een (`V`) voor het type van de waarden.
Elementen toevoegen aan een `Map<K, V>` gaat via de `put(K key, V value)`-methode.
De waarde opvragen kan via de methode `V get(K key)`.
Verder zijn er methodes om na te gaan of een map een bepaalde sleutel of waarde bevat.
Een Map is vaak geoptimaliseerd voor deze operaties; deze hebben vaak tijdscomplexiteit \\(\mathcal{O}(1)\\) (hashtable-gebaseerde implementaties) of \\(\mathcal{O}(\log n)\\) (boom-gebaseerde implementaties).

Er zijn verder ook drie manieren om een `Map<K, V>` als `Collection` te beschouwen:

- de `keySet`: de verzameling sleutels (een `Set<K>`)
- de `values`: de collectie waarden (een `Collection<V>`, want dubbels zijn mogelijk)
- de `entrySet`: een verzameling (`Set<Entry<K, V>>`) van sleutel-waarde paren (de _entries_).

Belangrijk om te onthouden is dat een Map geoptimaliseerd is om waardes op te vragen aan de hand van hun sleutels.

## HashMap

Net zoals bij Set kunnen we de Map-interface implementeren met een hashtable.
Dat gebeurt in de `HashMap` klasse.
Entries in een hashmap worden in een niet-gespecifieerde volgorde bijgehouden.

De werking van een hashmap is zeer gelijkaardig aan wat we besproken hebben bij HashSet hierboven.
Meer zelfs, de implementatie van HashSet in Java maakt gebruik van een HashMap.
Het belangrijkste verschil met de HashSet is dat we in een HashMap, naast de waarde, ook de sleutel moeten bewaren.

## SortedMap en TreeMap

Een [`SortedMap`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/SortedMap.html) is een map waarbij de **sleutels** (dus niet de waarden) gesorteerd worden bijgehouden (zoals bij een SortedSet).

De [TreeMap](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/TreeMap.html) klasse implementeert een SortedMap aan de hand van een boomstructuur.

## Voorbeeld

Een voorbeeld van het gebruik van `Map` als een telefoonbook:

```java
record Person(String name) {}
record PhoneNumber(int countryCode, String prefix, String number) {}

Person mary = new Person("Mary");
Person john = new Person("John");

Map<Person, PhoneNumber> phoneBook = new HashMap<>();
phoneBook.put(john, "0470123456");
phoneBook.put(mary, "0480999999");

Person somePerson = ...

String numberOrInfo;
if (phoneBook.containsKey(somePerson)) {
  // geeft `null` terug indien persoon niet gevonden
  numberOrInfo = phoneBook.get(somePerson);
} else {
  numberOrInfo = "1207"
}

// alternatief voor bovenstaande code:
var numberOrInfo = phoneBook.getOrDefault(somePerson, "1207");
```

Als we personen alfabetisch kunnen sorteren, kunnen we ook een `SortedMap` gebruiken. 
Als implementatie gebruiken we dan een `TreeMap` in plaats van `HashMap`, en moeten we een comparator meegeven die bepaalt hoe de elementen gesorteerd worden.

```java
SortedMap<Person, String> phoneBook = new TreeMap<>(Comparator.comparing(Person::name));
phoneBook.put(john, "0470123456");
phoneBook.put(mary, "0480999999");
```
We zijn nu zeker dat we, bij het itereren over alle items, de personen in alfabetische volgorde terugkrijgen:
```java
for (Map.Entry<Person, String> entry : phoneBook.entrySet()) {
    System.out.println(entry.getKey() + " " + entry.getValue());
}
// => Person[name=John] 0470123456
//    Person[name=Mary] 0480999999
``` 