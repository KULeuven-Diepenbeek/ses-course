---
title: "5.1 Records"
toc: true
autonumbering: true
draft: false
---

> [!todo]
> data-oriented programming (niet value classes): opt out of internal state/encapsulation (value = opt out of identity)


## Wat zijn records

Een **record** in Java is een eenvoudige klasse die een waarde voorstelt.
Met een waarde bedoelen we een object waarvoor de identiteit volledig bepaald wordt door de attribuut-waarden, en het dus niet uitmaakt welke instantie je bekijkt.
Welgekende voorbeelden zijn een coordinaat (bestaande uit een x- en y-attribuut), een geldbedrag (een bedrag en een munteenheid), een adres (straat, huisnummer, postcode, gemeente), etc.

Een record-object dient dus als data-drager, waarbij verschillende objecten met dezelfde attribuut-waarden volledig inwisselbaar (equivalent) zijn.
De attributen van een record-object mogen daarom niet veranderen doorheen de tijd (het object is dus **immutable**).

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

## Wanneer gebruik je een record

Er zijn meerdere situaties waarin het aangeraden is om een record te gebruiken, bijvoorbeeld:

1. wanneer je meerdere waarden (die logisch bij elkaar horen) wil bundelen:

```java
record Address(String street, int number, int zipcode, String city, String country) {}
```

2. wanneer je een methode meerdere waarden wil laten teruggeven

```java
record ElementFound(int index, int value) {}

public ElementFound findMaximum(ArrayList<Integer> values) { ... }
```

3. wanneer je een type wil definiëren dat overeenkomt met een ander, reeds bestaand datatype, maar met beperkingen.

```java
record PositiveNumber(int number) {
  public PositiveNumber {
    if (number <= 0) throw new IllegalArgumentException("Number must be larger than 0");
  }
}
```

4. wanneer je een (immutable) datatype wil maken dat zonder probleem door meerdere threads gebruikt kan worden; dit komt later nog aan bod in het onderwerp _Multithreading en concurrency_.

Merk op dat bij records in de eerste plaats gaat over het creëren van een nieuw datatype, door (primitievere) data te bundelen of te beperken qua mogelijke waarden.
Je maakt dus als het ware een nieuw primitief datatype, zoals int, double, of String.
Dit in tegenstelling tot klassen, waar gedrag om het object aan te passen (mutatie-methodes) en identiteit ook essentieel zijn.

## Achter de schermen

Een record is eigenlijk een gewone klasse, waarbij de Java-compiler zelf enkele zaken voorziet:

- een constructor, die de velden initialiseert;
- methodes om de attributen op te vragen;
- een `toString`-methode om een leesbare versie van het record uit te printen; en
- een `equals`- en `hashCode`-methode, die ervoor zorgen dat objecten met dezelfde parameters als gelijk worden beschouwd.

De klasse is ook `final`, zodat er geen subklassen van gemaakt kunnen worden.

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

Met de speciale syntax voor records kan je jezelf dus heel wat typwerk (en kans op fouten) besparen.

## Methodes toevoegen aan een record

Je kan zelf extra methodes toevoegen aan een record op dezelfde manier als bij een gewone Java-klasse:

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

Als je geen constructor definieert, krijgt een record een standaard constructor met de opgegeven attributen als parameters (in dezelfde volgorde).

Maar je kan ook zelf een of meerdere constructoren definiëren voor een record, net zoals bij klassen.
Je moet dan zelf zorgen dat je alle attributen van de record initialiseert.

```java
public record Coordinate(double x, double y) {
  public Coordinate(double x, double y) {
    this.x = x;
    this.y = y;
  }
  public Coordinate(double x) { // constructor for points on the x-axis
    this(x, 0);
  }
}
```

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

Zoals eerder al vermeldt komt een record overeen met een `final` klasse.
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

{{% notice note %}}
**Let op!** Als een van de attributen van het object zelf gewijzigd kan worden, kan dat attribuut nog steeds aangepast worden.
Vermijd deze situatie!
{{% /notice %}}

Bijvoorbeeld:

```java
public record Song(String title, String artist) {}
public record Playlist(ArrayList<Song> songs) {}

var songs = new ArrayList<>(List.of(new Song("Hello", "Adele")));
var playlist1 = new Playlist(songs);
var playlist2 = new Playlist(new ArrayList<>(songs));
System.out.println(playlist1.equals(playlist2)); // => true: beide playlists bevatten dezelfde liedjes
songs.add(new Song("Bye bye bye", "NSYNC"));
System.out.println(playlist1.equals(playlist2)); // => false
```

Hier zijn twee record-objecten eerst gelijk, maar later niet meer.
Dat schendt het principe dat de identiteit van het object niet uitmaakt.
Overal waar `playlist1` gebruikt wordt, zou ook `playlist2` gebruikt moeten kunnen worden en vice versa.
Twee record-objecten die gelijk zijn, moeten altijd gelijk blijven, onafhankelijk van wat er later nog gebeurt.
Gebruik dus steeds immutable data types in een record.

## Pattern matching

Je kan records ook gebruiken in switch statements.
Dit heet **pattern matching**, en is vooral nuttig wanneer je meerdere record-types hebt die eenzelfde interface implementeren.
Bijvoorbeeld:

```java
interface Shape {}
record Square(double side) implements Shape {}
record Circle(double radius) implements Shape {}
record Rectangle(Coordinate topLeft, Coordinate bottomRight) implements Shape {}
```

Je kan dan een switch expressie gebruiken, bijvoorbeeld om een methode te implementeren die de oppervlakte van een vorm berekent:

```java
public double area(Shape shape) {
  return switch(shape) {
    case Square s -> s.side() * s.side();
    case Circle(double radius) -> Math.PI * radius * radius;
    case Rectangle(Coordinate(double topLeftX, double topLeftY), Coordinate bottomRight) ->
         (bottomRight.x() - topLeftX) * (bottomRight.y() - topLeftY);
    default -> throw new IllegalArgumentException("Unknown shape");
  };
}
```

Merk op dat je zowel kan matchen op het object als geheel (`Square s` in het voorbeeld hierboven), individuele argumenten (`Circle(double radius)` in het voorbeeld), en zelfs geneste patronen (`Rectangle(Coordinate(double topLeftX, double topLeftY), Coordinate bottomRight)`).

De switch-expressie hierboven is verschillend van het (oudere) switch-statement in Java:

- er wordt `->` gebruikt in plaats van `:`
- er is geen `break` nodig op het einde van elke case
- de switch-expressie geeft een waarde terug die kan toegekend worden aan een variabele, of gebruikt kan worden in een `return`-statement (zoals in het voorbeeld hierboven).

Tenslotte is er in een switch-expressie de mogelijkheid om een conditie toe te voegen door middel van een `when`-clausule:

```java
public double area(Shape shape) {
  return switch(shape) {
    case Square s -> s.side() * s.side();
    case Circle(double radius) -> Math.PI * radius * radius;
    case Rectangle(Coordinate(double topLeftX, double topLeftY), Coordinate bottomRight)
         when topLeftX <= bottomRight.x() && topLeftY <= bottomRight.y() -> // <= when-clausule
         (bottomRight.x() - topLeftX) * (bottomRight.y() - topLeftY);
    default -> throw new IllegalArgumentException("Unknown or invalid shape");
  };
}
```

## Sealed interfaces

Wanneer je alle klassen kent die een bepaalde interface zullen implementeren (of van een abstracte klasse zullen overerven), kan je van deze interface (of klasse) een **sealed** interface (of klasse) maken.
Met een `permits` clausule kan je aangeven welke klassen de interface mogen implementeren:

```java
public sealed interface Shape permits Square, Circle, Rectangle {}
record Square(double side) implements Shape {}
record Circle(double radius) implements Shape {}
record Rectangle(double length, double width) implements Shape {}
```

Indien je geen permits-clausule opgeeft, zijn enkel de klassen die in hetzelfde bestand toegestaan.

Voor een sealed klasse of interface zoals `Shape` zal de compiler niet toelaten dat je er later een andere klasse van laat overerven:

```java
record Triangle(double base, double height) implements Shape {} // <- compiler error
```

Omdat de compiler kan nagaan wat alle mogelijkheden zijn, kan je bij pattern matching op een sealed klasse in een switch statement ook de default case weglaten:

```java
public double area(Shape shape) {
  return switch(shape) {
    case Square s -> s.side() * s.side();
    case Circle(double radius) -> Math.PI * radius * radius;
    case Rectangle(double width, double height) -> width * height;
  };
}
```

Omgekeerd zal de compiler je ook waarschuwen wanneer er een geval ontbreekt.

```java
public double area(Shape shape) {
  return switch(shape) {
    case Square s -> s.side() * s.side();
    case Circle(double radius) -> Math.PI * radius * radius;
    // <= compiler error: ontbrekende case voor 'Rectangle'
  };
}
```

## Oefeningen

### Klasse of record?

Geef enkele voorbeelden van types die volgens jou best als record gecodeerd worden, en ook enkele types die best als klasse gecodeerd worden.

Kan je, voor een van je voorbeelden, een situatie bedenken waarin je van record naar klasse zou gaan, en omgekeerd?

### Sealed interface

Kan je een voorbeeld bedenken van een sealed interface?

### Email

Definieer (volgens de principes van TDD) een `Email`-record dat een geldig e-mailadres voorstelt.
Het mail-adres wordt voorgesteld door een String.

Controleer de geldigheid van de String bij het aanmaken van een Email-object:

- de String mag niet null zijn
- de String moet exact één @-teken bevatten
- de String moet eindigen op ".com" of ".be"

{{% notice note %}}
De echte regels voor een geldig emailadres zijn uiteraard _veel_ complexer.
Zie bijvoorbeeld de [voorbeelden van geldige e-mailadressen op deze Wikipedia-pagina](https://en.wikipedia.org/wiki/Email_address#Valid_email_addresses).
{{% /notice %}}

### Money

Maak (volgens de principes van TDD) een `Money`-record dat een geldbedrag (bijvoorbeeld 20) en een munteenheid (bijvoorbeeld "EUR") bevat.
Voeg ook methodes toe om twee geldbedragen op te tellen. Dit mag enkel wanneer de munteenheid van beiden gelijk is; zoniet moet er een exception gegooid worden.

### Interval

Maak (volgens de principes van TDD) een `Interval`-record dat een periode tussen twee tijdstippen voorstelt, bijvoorbeeld voor een vergadering. Elk tijdstip wordt voorgesteld door een niet-negatieve long-waarde.
Het eind-tijdstip mag niet voor het start-tijdstip liggen.

Voeg een methode toe om na te kijken of een interval overlapt met een ander interval.
Intervallen worden beschouwd als half-open: twee aansluitende intervallen overlappen niet, bijvoorbeeld [15, 16) en [16, 17).

### Rechthoek

Schrijf (volgens de principes van TDD) een record die een rechthoek voorstelt.
Een rechthoek wordt gedefinieerd door 2 punten (linksboven en rechtsonder).
Gebruik een Coordinaat-record om deze hoekpunten voor te stellen.
Zorg ervoor dat enkel geldige rechthoeken aangemaakt kunnen worden (dus: het hoekpunt linksboven ligt zowel links als boven het hoekpunt rechtsonder).

Voeg extra methodes toe:

- om de twee andere hoekpunten (linksonder en rechtsboven) op te vragen
- om na te gaan of een gegeven punt zich binnen de rechthoek bevindt
- om na te gaan of een rechthoek overlapt met een andere rechthoek. (_Hint: bij twee overlappende rechthoeken ligt minstens één hoekpunt van de ene rechthoek binnen de andere_)

### Expressie-hierarchie

Maak een set van records om een wiskundige uitdrukking voor te stellen.
Alle records moeten een interface `Expression` implementeren.

De mogelijke expressies zijn:

- een `Literal`: een constante getal-waarde (een double)
- een `Variable`: een naam (een String), bijvoorbeeld "x"
- een `Sum`: bevat twee expressies, een linker en een rechter
- een `Product`: gelijkaardig aan Som, maar stelt een product voor
- een `Power`: een expressie tot een (constante) macht

De veelterm `3x^2 + 5` kan dus voorgesteld worden als:

```java
var poly = new Sum(new Product(new Literal(3), new Power(new Variable("x"), new Literal(2))), new Literal(5))
```

Maak vervolgens, gebruik makend van pattern matching, ook een methodes `String prettyPrint(Expression expr)` die de expressie omzet in een string, bijvoorbeeld `prettyPrint(poly)` geeft `((3 * x^2) + 5)`.

Uitbreidingen (optioneel):

- zorg ervoor dat er geen onnodige haakjes verschijnen, door rekening te houden met de volgorde van de bewerkingen.
- schrijf een methode om een expressie te vereenvoudigen.
- schrijf een methode om een expressie te evalueren voor een bepaalde waarde van een variabele.
- schrijf een methode om de afgeleide van de expressie in een variabele te berekenen.
