---
title: "7.3 Records"
toc: true
weight: 30
autonumbering: true
draft: false
---

{{% notice info "In andere programmeertalen" %}}
De concepten in andere programmeertalen die het dichtst aanleunen bij Java records, pattern matching en sealed interfaces zijn
- structs in C en C++ (pattern matching in C++ is nog niet beschikbaar, maar er wordt gewerkt aan dit toe te voegen aan de taal)
- @dataclass en structured pattern matching in Python
- (sealed) record types en pattern matching in C#
{{% /notice %}}

## Wat zijn records

In een object-georiënteerd software-ontwerp brengen we data en gedrag samen binnen één klasse.
We gebruiken dan gewoonlijk *encapsulatie*: we maken de velden van een klasse privaat, zodat ze worden afgeschermd van andere klassen. Op die manier kunnen we de interne representatie (de velden en hun types) makkelijk aanpassen: zolang de publieke methodes (het gedrag) hetzelfde blijven, heeft zo'n aanpassing aan de interne voorstellingswijze geen effect op de rest van het systeem.

Maar soms is encapsulatie niet echt nodig: sommige klassen zijn niet meer dan een bundeling van verschillende waarden, zonder bijhorend complex gedrag.
Welgekende voorbeelden zijn een coordinaat (bestaande uit een x- en y-attribuut), een geldbedrag (een bedrag en een munteenheid), een adres (straat, huisnummer, postcode, gemeente), etc.
Objecten van deze klassen hoeven ook niet aanpasbaar te zijn; je kan makkelijk een nieuw object maken met andere waarden.
Met andere woorden, de _identiteit_ van het object is van ondergeschikt belang.
We noemen dit **data-oriented programming**: een ontwerpstrategie waar data een _first class citizen_ is, en niet gekoppeld hoeft te worden aan gedrag.
Voor dergelijke klassen heeft doorgedreven encapsulatie weinig zin.

Een **record** in Java is een eenvoudige klasse die gebruikt kan worden voor data-oriented programming.
Een record-object dient voornamelijk als data-drager, waarbij verschillende objecten met dezelfde attribuut-waarden gewoonlijk volledig inwisselbaar (equivalent) zijn.
De attributen van een record-object mogen daarom niet veranderen doorheen de tijd (het object is dus **immutable**).

Als voorbeeld definiëren we een coördinaat-klasse als een record, met 2 attributen: een x- en y-coördinaat.

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

Twee coördinaat-objecten met dezelfde x- en y-coordinaat worden als equivalent beschouwd, ook al zijn het twee verschillende objecten:

```java
var coordinate1 = new Coordinate(3, 5);
var coordinate2 = new Coordinate(3, 5);
coordinate1.equals(coordinate2); // => true
```

## Wanneer gebruik je een record

Er zijn meerdere situaties waarin het aangeraden is om een record te gebruiken, bijvoorbeeld:

1. wanneer je meerdere waarden (die logisch bij elkaar horen) wil bundelen:

```java
record Address(String street,
               int houseNumber,
               int zipCode,
               String city,
               String country) {}
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

4. wanneer je een (immutable) datatype wil maken dat zonder probleem door meerdere threads gebruikt kan worden. We gaan in dit vak niet dieper in op het onderwerp _Multithreading en concurrency_, maar onthoud dat het gebruik van immutable objecten zeer sterk aangeraden wordt in deze context!

Merk op dat bij records in de eerste plaats gaat over het creëren van een nieuw datatype, door (primitievere) data of andere records te bundelen, of beperkingen op te leggen aan mogelijke waarden.
Je maakt dus als het ware een nieuw 'primitief' datatype, zoals int, double, of String.
Dit in tegenstelling tot gewone klassen, waar encapsulatie en mogelijkheden om de toestand van een object aan te passen (mutatie-methodes) ook essentieel zijn.

{{% notice tip Onthoud %}}
Gebruik een record wanneer je puur data modelleert, zonder bijhorend gedrag dat de toestand van het object kan veranderen.
Gebruik **geen** record als je een entiteit modelleert waarvan de toestand kan evolueren doorheen de tijd (met andere woorden, wanneer de identiteit van het object belangrijk is).
{{% /notice %}}

## Achter de schermen

Een record is eigenlijk een gewone klasse, waarbij de Java-compiler zelf enkele zaken voorziet:

- een constructor, die de velden initialiseert;
- methodes om de attributen op te vragen;
- een `toString`-methode om een leesbare versie van het record uit te printen; en
- een `equals`- en `hashCode`-methode, die ervoor zorgen dat objecten met dezelfde parameters als gelijk worden beschouwd.

De klasse is ook `final`, zodat er geen subklassen van gemaakt kunnen worden.

De coördinaat-record van hierboven is equivalent aan volgende klasse-definitie:

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

Merk wel op dat, omdat de klasse immutable is, je in een methode geen nieuwe waarde kan toekennen aan de velden. Code als
```java
  this.x = 5;
```
in een methode van een record is dus ongeldig, en leidt tot een foutmelding van de compiler.

## Constructor van een record

Als je geen enkele constructor definieert, krijgt een record een standaard constructor met de opgegeven attributen als parameters (in dezelfde volgorde).

Maar je kan ook zelf een of meerdere constructoren definiëren voor een record, net zoals bij klassen (je krijgt dan geen default-constructor meer).
Je moet in die constructoren zorgen dat alle attributen van het record geïnitialiseerd worden.

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
Het belangrijkste nut hiervan is om de geldigheid van de waarden te controleren bij het aanmaken van een object:

```java
public record Coordinate(double x, double y) {
  public Coordinate {
    if (x < 0) throw new IllegalArgumentException("x must be non-negative");
    if (y < 0) throw new IllegalArgumentException("y must be non-negative");
  }
}
```

## Records en overerving

Zoals eerder al vermeld werd, komt een record overeen met een `final` klasse.
Je kan er dus niet van overerven.

Een record zelf kan ook geen subklasse zijn van een andere klasse of record, maar kan wel interfaces implementeren.

## Immutable

Een record is **immutable** (onveranderbaar): de attributen krijgen een waarde wanneer het object geconstrueerd wordt, en kunnen daarna nooit meer wijzigen.
Als je een object wil met andere waarden, moet je dus een nieuw object maken.

Bijvoorbeeld, als we een `translate` methode willen maken voor `Coordinate`, dan kunnen we de x- en y-coordinaten niet aanpassen.
We moeten een nieuw Coordinate-object maken, en dat teruggeven:

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

**Let op!** Als een van de velden van het record een object is dat zelf wél gewijzigd kan worden (bijvoorbeeld een array of ArrayList), dan kan je de data die geassocieerd wordt met het record dus wel nog wijzigen.
Vermijd deze situatie!
Bijvoorbeeld:

```java
public record Song(String title, String artist) {}
public record Playlist(ArrayList<Song> songs) {}

var songs = new ArrayList<>(List.of(new Song("Hello", "Adele")));
var playlist1 = new Playlist(songs);
var playlist2 = new Playlist(new ArrayList<>(songs)); // kopie
System.out.println(playlist1.equals(playlist2)); // => true: beide playlists bevatten dezelfde liedjes
songs.add(new Song("Bye bye bye", "NSYNC"));
System.out.println(playlist1.equals(playlist2)); // => false
```

Hier zijn twee record-objecten eerst gelijk, maar later niet meer.
Dat schendt het principe dat, voor data-objecten, de identiteit van het object niet zou mogen uitmaken.
De objecten zijn immers niet meer dan de aggregatie van de data die ze bevatten.
Overal waar `playlist1` gebruikt wordt, zou ook `playlist2` gebruikt moeten kunnen worden en vice versa.
Twee record-objecten die gelijk zijn, moeten altijd gelijk blijven, onafhankelijk van wat er later nog gebeurt.
Gebruik dus bij voorkeur immutable data types in een record.

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
    case Square s -> 
        s.side() * s.side();
    case Circle(double radius) -> 
        Math.PI * radius * radius;
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
    case Square s ->
        s.side() * s.side();
    case Circle(double radius) ->
        Math.PI * radius * radius;
    case Rectangle(Coordinate(double topLeftX, double topLeftY), Coordinate bottomRight)
        when topLeftX <= bottomRight.x() && topLeftY <= bottomRight.y() -> // <= when-clausule
        (bottomRight.x() - topLeftX) * (bottomRight.y() - topLeftY);
    default -> throw new IllegalArgumentException("Unknown or invalid shape");
  };
}
```

Op die manier kan je extra voorwaarden toevoegen om een case te laten matchen, bovenop het type van het element.

## Sealed interfaces

Wanneer je alle klassen kent die een bepaalde interface zullen implementeren (of van een abstracte klasse zullen overerven), kan je van deze interface (of klasse) een **sealed** interface (of klasse) maken.
Met een `permits` clausule kan je aangeven welke klassen de interface mogen implementeren:

```java
public sealed interface Shape permits Square, Circle, Rectangle {}
record Square(double side) implements Shape {}
record Circle(double radius) implements Shape {}
record Rectangle(double length, double width) implements Shape {}
```

Indien je geen permits-clausule opgeeft, zijn enkel de klassen die in hetzelfde bestand staan toegestaan.

Omdat elk Java-bestand slechts 1 publieke top-level klasse (of interface/record) mag hebben, zal je vaak ook zien dat de records _in_ de interface-definitie geplaatst worden:
```java
public sealed interface Shape {
  record Square(double side) implements Shape {}
  record Circle(double radius) implements Shape {}
  record Rectangle(double length, double width) implements Shape {}
}
```
Zo staan ze allemaal samen in 1 bestand, en kan je ze overal in je code gebruiken via bv. `Shape.Square`.

Voor een sealed klasse of interface zoals `Shape` zal de compiler niet toelaten dat je er ergens anders een andere klasse van laat overerven:

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

Zie [Oefeningen](oefeningen)