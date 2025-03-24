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

Kan je een voorbeeld bedenken van een nuttig gebruik van een sealed interface?

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
Alle records moeten een **sealed** interface `Expression` implementeren.

De mogelijke expressies zijn:

- een `Literal`: een constante getal-waarde (een double)
- een `Variable`: een naam (een String), bijvoorbeeld "x"
- een `Sum`: bevat twee expressies, een linker en een rechter
- een `Product`: gelijkaardig aan Som, maar stelt een product voor
- een `Power`: een expressie tot een _constante_ macht

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

Maak nu een klasse `ExpressionUtils` met volgende statische methodes (de beschrijving volgt hieronder).
```java
class ExpressionUtils {
  public static String prettyPrint(Expression expr) { ... }
  public static Expression simplify(Expression expr) { ... }
  public static double evaluate(Expression expr, ArrayList<Assignment> variableValues) { ... }
  public static Expression differentiate(Expression expr, Variable var) { ... }
}
```

Gebruik **pattern matching** (en TDD) voor elk van volgende opdrachten:
1. Schrijf de methode `prettyPrint` die de gegeven expressie omzet in een string, bijvoorbeeld `prettyPrint(poly)` geeft `(3.0) * ((x)^2.0) + 5.0`.
2. zorg ervoor dat er geen onnodige haakjes verschijnen in het resultaat van `prettyPrint`, door rekening te houden met de volgorde van de bewerkingen. (_Hint: geef elke expressie een numerieke prioriteit_)
3. de methode `simplify` moet de gegeven expressie te vereenvoudigen door enkele vereenvoudigingsregels toe te passen. Bijvoorbeeld, het vervangen van \(3 + 7\) door \(10\), vervangen van \(x+0\), \(x*1\), en \(x^1\) door \(x\); vervangen van \(x * 0\) door \(0\),  ...
4. de methode `evaluate` moet de gegeven expressie evalueren voor de gegeven waarden van de variabelen. Bijvoorbeeld, \( 3x^2+5 \) evalueren met \( x=7 \) geeft \(152\). De parameter `variableValues` bevat een lijst van toekenningen van een waarde aan een variabele. Je moet de klasse `Assignment` eerst zelf nog maken (_Hint: gebruik hiervoor ook een record_).
5. de methode `differentiate` moet de afgeleide berekenen van de gegeven expressie in de gegeven variabele (bv. \( \frac{d}{dx} 3x^2+5x = 6x+5 \)).

{{% notice tip Denkvraag %}}
Wat is het voor- en nadeel van het gebruik van pattern matching tegenover het gebruik van overerving en dynamische binding?
Met andere woorden, wat is het verschil met bijvoorbeeld de methodes `simplify()`, `evaluate()`, ... in de interface Expression zelf te definiëren, en ze te implementeren in elke subklasse?
{{% /notice %}}