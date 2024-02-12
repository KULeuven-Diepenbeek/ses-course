---
title: "4.1 Records"
toc: true
autonumbering: true
---

## Wat zijn records

Een **record** in Java is een eenvoudige klasse die een waarde voorstelt.
Met een waarde bedoelen we een object waarvoor de identiteit volledig bepaald wordt door de attributen, en het dus niet uitmaakt welke instantie je bekijkt.
De attributen van een waarde mogen ook niet veranderen doorheen de tijd (het object is dus **immutable**).
Twee waarde-objecten met dezelfde attribuut-waarden worden bijgevolg als volledig equivalent beschouwd.

Als voorbeeld definiëren we een coordinaat-klasse als een record, met 2 attributen: een x- en y-coordinaat.

```java
public record Coordinate(double x, double y) {
}
```

Merk het verschil op met de definitie van een gewone klasse: de attributen van de record staan hier vlak na de klassenaam, en er is geen constructor nodig.

Objecten van een record maak je gewoon aan met `new`, zoals elk ander object:

```java
Coordinate coord = new Coordinate(3, 5);
System.out.println(coord);
// => Coordinate[x=3.0, y=5.0]
```

Twee coordinaat-objecten met dezelfde x- en y-coordinaat worden als equivalent beschouwd, ook al zijn het twee verschillende objecten:

```java
var coordinate1 = new Coordinate(3, 5);
var coordinate2 = new Coordinate(3, 5);
assertEquals(coordinate1, coordinate2);
```

## Achter de schermen

Een record is eigenlijk een gewone klasse, waarbij de Java-compiler zelf enkele zaken voorziet:

- een constructor, die de velden initialiseert;
- methodes om de attributen op te vragen;
- een `toString`-methode om een leesbare versie van het record uit te printen; en
- een `equals`- en `hashCode`-methode, die ervoor zorgen dat objecten met dezelfde parameters als gelijk worden beschouwd.

Met deze speciale syntax kan je zo heel wat typwerk (en kans op fouten) besparen.
De coordinaat-definitie van hierboven is equivalent aan volgende klasse-definitie:

```java
public final class Coordinate {
    private final double x;
    private final double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double x() { return this.x; }
    double y()  { return this.y; }

    public boolean equals(Object other) { /* ... */ }
    public int hashCode() { /* ... */ }

    public String toString() { /* ... */ }
}
```

## Methodes toevoegen aan een record

Je kan zelf extra methodes toevoegen aan een record op dezelfde manier als bij een gewone Java klasse:

```java
public record Coordinate(double x, double y) {
  public double distanceTo(Coordinate other) {
    double deltaX = other.x() - this.x();
    double deltaY = other.y() - this.y();
    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }
}
```

## Constructor van een record

Als je geen constructor definieert, krijgt een record een standaard constructor met de attributen.

Maar je kan ook zelf een of meerdere constructoren definiëren voor een record, net zoals bij klassen.
Je moet dan zelf zorgen dat je alle attributen van de record initialiseert.

Er is ook een verkorte notatie, waarbij je de parameters niet meer moet herhalen (die staan immers al achter de naam van het record).
Je hoeft met deze notatie ook de parameters niet toe te kennen aan de velden; dat gebeurt automatisch.
Het belangrijkste nut hiervan is dus om de geldigheid van de waarden te controleren bij het aanmaken van een object:

```java
public record Coordinate(double x, double y) {
  public Coordinate {
    if (x < 0) throw new IllegalArgumentException("x must be non-negative");
    if (y < 0) throw new IllegalArgumentException("y must be non-negative");
  }
}
```

## Records en overerving

Een record is gelijkaardig aan een `final` klasse.
Je kan er dus niet van overerven.

Een record zelf kan ook geen subklasse zijn van een andere klasse of record, maar kan wel interfaces implementeren.

## Immutable

Een record is immutable: de attributen krijgen een waarde wanneer het object geconstrueerd wordt, en kunnen daarna nooit meer wijzigen.
Als je een object wil met andere waarden, moet je dus een nieuw object maken.

Bijvoorbeeld, als we een `translate` methode willen maken voor `Coordinate` hierboven, dan kunnen we de x- en y-coordinaten niet aanpassen.
We moeten een nieuw object maken, en dat teruggeven:

```java
public record Coordinate(double x, double y) {
  public Coordinate translate(double deltaX, double deltaY) {
    // NIET:
    // this.x += deltaX;  <-- kan niet; de x-waarde mag niet meer gewijzigd worden

    // WEL: een nieuw object maken
    return new Coordinate(this.x + deltaX, this.y + deltaY);
  }
}
```

## Pattern matching

switch + records

## Sealed classes

```java
sealed interface Length {
  public double value();

  public record Meter(double value) implements Length {};
  public record Kilometer(double value) implements Length {};
}
```

TODO

- pattern matching?

## Oefeningen

### Email

Definieer (volgens de principes van TDD) een `Email`-record dat een geldig e-mailadres voorstelt.
Het mail-adres wordt voorgesteld door een String.

Controleer de geldigheid van de String bij het aanmaken van een Email-object:

- de String mag niet null zijn
- de String moet exact één @-teken bevatten (de echte regels voor een emailadres zijn uiteraard veel complexer)

### Money

Maak (volgens de principes van TDD) een `Money`-record dat een geldbedrag (bijvoorbeeld 20) en een munteenheid (bijvoorbeeld "EUR") bevat.
Voeg ook methodes toe om twee geldbedragen op te tellen. Dit mag enkel wanneer de munteenheid van beiden gelijk is; zoniet moet er een exception gegooid worden.

### Interval

start, end

validate start <= end

### Rechthoek

Schrijf (volgens de principes van TDD) een klasse die een rechthoek voorstelt.
Een rechthoek wordt gedefinieerd door 2 punten (linksboven en rechtsonder); gebruik een Coordinaat-record om deze punten voor te stellen.

Voeg extra methodes toe:

- om de hoekpunten linksonder en rechtsboven op te vragen
- om na te gaan of een gegeven punt zich binnen de rechthoek bevindt
- om na te gaan of een rechthoek overlapt met een andere rechthoek. (_Hint: bij twee overlappende rechthoeken ligt minstens één hoekpunten van de ene rechthoek binnen de andere_)

### Expression hierarchie

(Arithmetic)

Lit, Var, Sum, Product, Exp < Expr

print operator
eval operator
differentiate operator
