---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
draft: false
math: true
---

Je vindt een IntelliJ project met de projectstructuur en tests voor alle oefeningen in volgende GitHub repository:

```bash
https://github.com/KULeuven-Diepenbeek/ses-deel2-oefeningen-02-records
```

## Klasse of record?

Geef enkele voorbeelden van types die volgens jou best als record gecodeerd worden, en ook enkele types die best als klasse gecodeerd worden.

Kan je, voor een van je voorbeelden, een situatie bedenken waarin je van record naar klasse zou gaan, en omgekeerd?

{{% notice style=tip title="Antwoord" expanded=false %}}
Records zijn vooral geschikt voor het bijhouden van _stateless_ informatie (objecten zonder gedrag).
Bijvoorbeeld: `Money`, `ISBN`, `BookInfo`, `ProductDetails`, ...

Klassen zijn geschikt als de identiteit van het object van belang is en constant blijft, maar de state (data) doorheen de tijd kan wijzigen.
Bijvoorbeeld: `BankAccount`, `ShoppingCart`, `GameCharacter`, `OrderProcessor`, ...

Overgaan van de ene naar de andere vorm kan wanneer er gedrag toegevoegd of verwijderd wordt.
Bijvoorbeeld, `BookInfo` zou een klasse kunnen worden indien we er (in de context van een bibliotheek) ook informatie over ontleningen in willen bijhouden. Omgekeerd kan `BankAccount` van klasse naar object gaan indien het enkel een voorstelling wordt van rekeninginformatie (rekeningnummer en naam van de houder bijvoorbeeld), en de balans en transacties naar een ander object (bv. `TransactionHistory`) verplaatst worden.
{{% /notice %}}

## Sealed interface

Kan je een voorbeeld bedenken van een nuttig gebruik van een sealed interface?

{{% notice style=tip title="Antwoord" expanded=false %}}
Sealed interfaces zijn vooral nuttig om een uitgebreidere vorm van enum's te maken, waar elke optie ook extra informatie met zich kan meedragen.

Bijvoorbeeld:
- sealed interface `PaymentMethod` om een manier van betalen voor te stellen, met subtypes (records) `CreditCard(cardName, cardNumber, expirationDate)`, `PayPal(email)`, `BankTransfer(iban)`, ...
- sealed interface `Command` wat een commando voorstelt dat uitgevoerd kan worden, met subtypes (records) `CreateUser(name, email)`, `DeleteUser(uuid)`, `UpdateUser(uuid, newEmail)`, ...
{{% /notice %}}
## Email

Definieer een `Email`-record dat een geldig e-mailadres voorstelt.
Het mail-adres wordt voorgesteld door een String.

Controleer de geldigheid van de String bij het aanmaken van een Email-object:

- de String mag niet null zijn (anders `NullPointerException`)
- de String moet exact één @-teken bevatten (anders `IllegalArgumentException`)
- de String moet eindigen op ".com" of ".be" (anders `IllegalArgumentException`)

{{% notice note %}}
De echte regels voor een geldig emailadres zijn uiteraard _veel_ complexer.
Zie bijvoorbeeld de [voorbeelden van geldige e-mailadressen op deze Wikipedia-pagina](https://en.wikipedia.org/wiki/Email_address#Valid_email_addresses).
{{% /notice %}}

## Rechthoek

Schrijf een record die een rechthoek voorstelt.
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
  public static double evaluate(Expression expr, Map<Variable, Double> variableValues) { ... }
  public static String prettyPrint(Expression expr) { ... }
  public static Expression simplify(Expression expr) { ... }
  public static Expression differentiate(Expression expr, Variable var) { ... }
}
```

Gebruik **pattern matching** (en TDD) voor elk van volgende opdrachten:
1. de methode `evaluate` moet de gegeven expressie evalueren voor de gegeven waarden van de variabelen. Bijvoorbeeld, \( 3x^2+5 \) evalueren met \( x=7 \) geeft \(152\). De parameter `variableValues` bevat een mapping van variabelen naar hun toegekende waarde.
2. Schrijf de methode `prettyPrint` die de gegeven expressie omzet in een String, bijvoorbeeld `prettyPrint(poly)` geeft `(3.0) * ((x)^2.0) + 5.0`.
   Maak je op dit moment nog geen zorgen over onnodige haakjes.
   _Hint: voor het pretty-printen van een som, pretty-print je eerst de linker- en rechterterm afzonderlijk._
3. Zorg er nu voor dat er geen onnodige haakjes verschijnen in het resultaat van `prettyPrint`, door rekening te houden met de volgorde van de bewerkingen. (_Hint: geef elke expressie een numerieke prioriteit_)
4. _(uitdagend)_ De methode `simplify` moet de gegeven expressie te vereenvoudigen door enkele vereenvoudigingsregels toe te passen. Bijvoorbeeld, het vervangen van \(3 + 7\) door \(10\), vervangen van \(x+0\), \(x*1\), en \(x^1\) door \(x\); vervangen van \(x * 0\) door \(0\),  ...
5. _(uitdagend)_ de methode `differentiate` moet de afgeleide berekenen van de gegeven expressie in de gegeven variabele (bv. \( \frac{d}{dx} 3x^2+5x = 6x+5 \)).
   Geef het resultaat zo eenvoudig mogelijk terug (_Hint: gebruik `simplify`_).

{{% notice tip Denkvraag %}}
Wat is het voor- en nadeel van het gebruik van pattern matching tegenover het gebruik van overerving en dynamische binding?
Met andere woorden, wat is het verschil met bijvoorbeeld de methodes `simplify()`, `evaluate()`, ... in de interface Expression zelf te definiëren, en ze te implementeren in elke subklasse?
{{% /notice %}}

## Extra oefeningen

### Money

Maak een `Money`-record dat een geldbedrag (bijvoorbeeld 20) en een munteenheid (bijvoorbeeld "EUR") bevat.
Voeg ook methodes toe om twee geldbedragen op te tellen. Dit mag enkel wanneer de munteenheid van beiden gelijk is; zoniet moet er een exception gegooid worden.

### Interval

Maak een `Interval`-record dat een periode tussen twee tijdstippen voorstelt, bijvoorbeeld voor een vergadering. Elk tijdstip wordt voorgesteld door een niet-negatieve long-waarde.
Het eind-tijdstip mag niet voor het start-tijdstip liggen.

Voeg een methode toe om na te kijken of een interval overlapt met een ander interval.
Intervallen worden beschouwd als half-open: twee aansluitende intervallen overlappen niet, bijvoorbeeld [15, 16) en [16, 17).

### Programmeertaal

Breid de expressies uit de oefening hierboven uit tot je eigen mini-programmeertaal met interpreter.
Voorzie daarvoor een **sealed interface Statement** met volgende klassen en betekenis:

- `Assign(name, expr)`: evalueer `expr` en sla het resultaat op in de variabele `name`
- `Print(expr)`: evalueer `expr` en print de waarde uit
- `If(cond, thenBranch, elseBranch)`: evalueer expressie `cond`; indien dit 0 is, voer statement `thenBranch` uit, anders statement `elseBranch`
- `While(cond, body)`: voer statement `body` uit zolang expressie `cond` naar 0 evalueert
- `Sequence(stmts)`: voer een lijst van statements `stmts` (een 'blok') na elkaar uit

Voeg dan een klasse `Interpreter` toe met een methode `execute(Statement st)` die het meegegeven statement (programma) uitvoert.
In je Interpreter maak je best gebruik van een klasse die de huidige toestand van het programma bijhoudt, met onderstaande interface:

```java
package program;

public interface ExecutionState {
    default double getCurrentValue(Variable var) {
        return variableValues().get(var);
    }
    void setVariable(Variable var, double newValue);

    Map<Variable, Double> variableValues();

    void print(double value);
}
```

Een voorbeeld van het gebruik van je interpreter:

```java
/*

x := 5
while x != 0:
  print x
  x := x - 1

*/
var xvar = new Variable("x");
var program = new Sequence(List.of(
        new Assign(xvar, new Literal(5)),
        new While(xvar,
                new Sequence(List.of(
                        new Print(xvar),
                        new Assign(xvar, new Sum(xvar, new Literal(-1)))
                ))
        )
));
new Interpreter().execute(program);
// 5.0
// 4.0
// 3.0
// 2.0
// 1.0
```