---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
draft: false
math: true
---


## Klasse of record?

Geef enkele voorbeelden van types die volgens jou best als record gecodeerd worden, en ook enkele types die best als klasse gecodeerd worden.

Kan je, voor een van je voorbeelden, een situatie bedenken waarin je van record naar klasse zou gaan, en omgekeerd?

## Sealed interface

Kan je een voorbeeld bedenken van een sealed interface?

## Email

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

## Money

Maak (volgens de principes van TDD) een `Money`-record dat een geldbedrag (bijvoorbeeld 20) en een munteenheid (bijvoorbeeld "EUR") bevat.
Voeg ook methodes toe om twee geldbedragen op te tellen. Dit mag enkel wanneer de munteenheid van beiden gelijk is; zoniet moet er een exception gegooid worden.

## Interval

Maak (volgens de principes van TDD) een `Interval`-record dat een periode tussen twee tijdstippen voorstelt, bijvoorbeeld voor een vergadering. Elk tijdstip wordt voorgesteld door een niet-negatieve long-waarde.
Het eind-tijdstip mag niet voor het start-tijdstip liggen.

Voeg een methode toe om na te kijken of een interval overlapt met een ander interval.
Intervallen worden beschouwd als half-open: twee aansluitende intervallen overlappen niet, bijvoorbeeld [15, 16) en [16, 17).

## Rechthoek

Schrijf (volgens de principes van TDD) een record die een rechthoek voorstelt.
Een rechthoek wordt gedefinieerd door 2 punten (linksboven en rechtsonder).
Gebruik een Coordinaat-record om deze hoekpunten voor te stellen.
Zorg ervoor dat enkel geldige rechthoeken aangemaakt kunnen worden (dus: het hoekpunt linksboven ligt zowel links als boven het hoekpunt rechtsonder).

Voeg extra methodes toe:

- om de twee andere hoekpunten (linksonder en rechtsboven) op te vragen
- om na te gaan of een gegeven punt zich binnen de rechthoek bevindt
- om na te gaan of een rechthoek overlapt met een andere rechthoek. (_Hint: bij twee overlappende rechthoeken ligt minstens één hoekpunt van de ene rechthoek binnen de andere_)

## Expressie-hierarchie

Maak een set van records om een wiskundige uitdrukking voor te stellen.
Alle records moeten een interface `Expression` implementeren.

De mogelijke expressies zijn:

- een `Literal`: een constante getal-waarde (een double)
- een `Variable`: een naam (een String), bijvoorbeeld "x"
- een `Sum`: bevat twee expressies, een linker en een rechter
- een `Product`: gelijkaardig aan Som, maar stelt een product voor
- een `Power`: een expressie tot een (constante) macht

De veelterm \( 3x^2+5 \) kan dus voorgesteld worden als:

```java
var poly = new Sum(
  new Product(
    new Literal(3),
    new Power(
      new Variable("x"),
      new Literal(2))),
  new Literal(5));
```

Maak vervolgens, gebruik makend van **pattern matching** (en TDD), ook een methodes `String prettyPrint(Expression expr)` die de expressie omzet in een string, bijvoorbeeld `prettyPrint(poly)` geeft `((3 * x^2) + 5)`.

Uitbreidingen (gebruik steeds TDD):

- zorg ervoor dat er geen onnodige haakjes verschijnen in prettyPrint, door rekening te houden met de volgorde van de bewerkingen. (_Hint: geef elke expressie een numerieke prioriteit_)
- schrijf een methode om een expressie te vereenvoudigen (bv. vervangen van \(3 + 7\) door \(10\), vervangen van \(x+0\), \(x*1\), en \(x^1\) door \(x\); vervangen van \(x * 0\) door \(0\),  ...).
- schrijf een methode om een expressie te evalueren voor een bepaalde waarde van een variabele (bv. \( 3x^2+5 \) met \( x=7 \) geeft \(152\)).
- schrijf een methode om de afgeleide van de expressie in een variabele te berekenen (bv. \( \frac{d}{dx} 3x^2+5 = 6x \)).
