---
title: "9.1 Specificatie versus implementatie"
autonumbering: true
weight: 10
toc: true
draft: false
math: true
---

## Wat is een specificatie?

Een **specificatie** beschrijft het gewenste gedrag van software.
Ze zegt wat correct gedrag is, zonder vast te leggen hoe je dat gedrag intern programmeert.

Dat klinkt eenvoudig, maar in de praktijk is dit een van de belangrijkste denkoefeningen in softwareontwikkeling.
Veel bugs ontstaan niet door een typfout in code, maar doordat de bedoeling onduidelijk of impliciet was.
Een goede specificatie maakt die bedoeling expliciet.

Je kan een specificatie zien als een contract tussen verschillende rollen:
- de gebruiker of opdrachtgever die gedrag verwacht,
- de ontwikkelaar die dat gedrag implementeert,
- de tester die controleert of het gedrag klopt,
- de toekomstige maintainer die code moet aanpassen zonder gedrag te breken.

Een specificatie kan op verschillende niveaus bestaan:

1. **Functioneel gedrag**
   Wat moet de software doen? Bijvoorbeeld: "zoek de index van een element".
2. **Randvoorwaarden**
   Voor welke inputs geldt het gedrag? Bijvoorbeeld: "de lijst is gesorteerd".
3. **Resultaatvoorwaarden**
   Wat moet na uitvoering waar zijn? Bijvoorbeeld: "het resultaat is een geldige index of -1".
4. **Niet-functionele verwachtingen**
   Soms ook betrouwbaarheid, foutafhandeling, veiligheid, enzovoort.

In dit hoofdstuk focussen we vooral op functionele specificaties en contracten.
We bouwen stapsgewijs op:
- eerst wat een specificatie precies is,
- dan het onderscheid tussen wat en hoe,
- daarna contracten en invarianten,
- en ten slotte waarom tests en formele redenering elkaar aanvullen.

### Intuïtief voorbeeld

Stel dat je een parkeerapp bouwt.
Een mogelijke specificatie van de functie `berekenParkeerkost(duurInMinuten)` is:
- de kost is nooit negatief,
- tot en met 30 minuten is de kost 0 euro,
- boven 30 minuten betaal je per begonnen half uur.

Die specificatie zegt niets over hoe je dit berekent.
Je kan intern werken met `if`-blokken, een tabel, of een formule: allemaal prima, zolang het externe gedrag klopt.
Dit is exact dezelfde denkwijze die we straks op Java-methodes toepassen.

### Pre- en postcondities

Een **preconditie** is een voorwaarde die waar moet zijn **vóór** de methode start.
Een **postconditie** is een voorwaarde die waar moet zijn **na** de methode, als de preconditie geldig was.

Belangrijk is de taakverdeling:
- de **aanroeper** is verantwoordelijk om de preconditie te respecteren,
- de **methode** is verantwoordelijk om de postconditie te garanderen.

Kort voorbeeld:

```java
//@ requires b != 0;
//@ ensures a == b * \result + (a % b);
public static int divide(int a, int b) {
    return a / b;
}
```

Interpretatie:
- je mag `divide` alleen oproepen met `b != 0`,
- daarna garandeert de methode de standaard eigenschap van gehele deling in Java.

Zonder pre/postcondities blijft vaak onduidelijk wie welke fout moet voorkomen.
Met pre/postcondities is die afspraak expliciet en toetsbaar.

{{% notice note Notatie %}}
Om zulke afspraken precies te maken, gebruiken we in dit hoofdstuk **OpenJML** (JML-annotaties in Java-comments).

Belangrijkste bouwstenen:
- `requires ...;` voor precondities,
- `ensures ...;` voor postcondities,
- `\result` voor de returnwaarde,
- `\old(expr)` voor de waarde vóór uitvoering,
- `invariant ...;` voor klasse-invarianten,
- `loop_invariant ...;` voor loop-invarianten.

In code ziet dat er meestal zo uit:

```java
//@ requires ...;
//@ ensures ...;
```

Concreet voorbeeld:

```java
//@ requires true;
//@ ensures \result >= a && \result >= b;
//@ ensures \result == a || \result == b;
public static int max(int a, int b) {
    return (a >= b) ? a : b;
}
```
{{% /notice %}}

## Volledigheid van specificaties

Bij specificaties gaat het niet alleen over juistheid, maar ook over **volledigheid**.
Een te zwakke specificatie laat implementaties toe die formeel “kloppen”, maar inhoudelijk fout zijn.

Stel dat je voor `max(a, b)` alleen dit zou eisen:

```java
//@ requires true;
//@ ensures \result >= a && \result >= b;
```

Dan is deze implementatie toegelaten:

```java
public static int max(int a, int b) {
    return Integer.MAX_VALUE;
}
```

Die is duidelijk fout als "maximum van twee getallen", maar voldoet wel aan de te zwakke postconditie.
Daarom is de extra regel

```java
//@ ensures \result == a || \result == b;
```

essentieel.

Algemene les:
een bruikbare specificatie moet niet alleen correcte implementaties toelaten,
maar ook **ongewenste implementaties uitsluiten**.

Hetzelfde zie je bij `berekenParkeerkost`.
Als je alleen specificeert `//@ ensures \result >= 0;`, dan is dit ook “correct”:

```java
public static int berekenParkeerkost(int duurInMinuten) {
    return 0;
}
```

Maar dat schendt duidelijk de bedoeling voor langdurig parkeren.
Dus ook daar moet de specificatie extra regels bevatten over de drempel van 30 minuten en het gedrag erboven.

## Specificatie en implementatie

In het vorige stuk zagen we al dat een specificatie gedrag beschrijft zonder codekeuzes.
Hier maken we dat onderscheid expliciet.

- **specificatie**: wat moet waar zijn vanuit het standpunt van de gebruiker,
- **implementatie**: hoe je dat gedrag technisch realiseert.

Voor dezelfde OpenJML-specificatie van `max` zijn bijvoorbeeld meerdere implementaties mogelijk:

```java
public static int maxIf(int a, int b) {
    if (a >= b) return a;
    return b;
}
```

```java
public static int maxMath(int a, int b) {
    return Math.max(a, b);
}
```

Zolang beide methodes aan hetzelfde contract voldoen, zijn ze extern equivalent.

Hetzelfde principe geldt voor de parkeerkost-functie.
Twee verschillende implementaties kunnen dezelfde specificatie realiseren:

```java
public static int berekenParkeerkostIf(int duurInMinuten) {
    if (duurInMinuten <= 30) return 0;
    int extra = duurInMinuten - 30;
    int halveUren = (extra + 29) / 30; // afronden naar boven
    return halveUren * 2; // euro
}
```

```java
public static int berekenParkeerkostTabel(int duurInMinuten) {
    List<Integer> grenzen = List.of(30, 60, 90, 120);
    List<Integer> prijzen = List.of(0, 2, 4, 6);
    for (int i = 0; i < grenzen.size(); i++) {
        if (duurInMinuten <= grenzen.get(i)) return prijzen.get(i);
    }
    int extra = duurInMinuten - 120;
    int halveUren = (extra + 29) / 30;
    return 6 + halveUren * 2;
}
```

De code ziet er anders uit, maar wat gebruikers zien is hetzelfde contract.

### Waarom dit belangrijk is

1. **Refactoren wordt veiliger**
   Je mag intern herwerken zolang het contract gelijk blijft.
2. **Samenwerken wordt duidelijker**
   Teamleden discussiëren over gedrag, niet over codevoorkeuren.
3. **Testen krijgt richting**
   Tests worden afgeleid uit contracten.
4. **Vervangbaarheid**
   Een implementatie kan vervangen worden zonder callers aan te passen.

Een praktische vuistregel:
als een zin de woorden "for-loop", "hashmap", "recursie", "if" bevat,
dan beschrijf je vaak implementatie in plaats van specificatie.

## Meerdere implementaties

Nu volgt de belangrijkste consequentie:
als specificatie en implementatie los staan, dan moeten meerdere implementaties mogelijk zijn onder één specificatie.

We werken dit uit met `indexOf` op `List<Integer>`.

### Specificatie

We willen:
- als `x` voorkomt, geef een geldige index terug,
- anders geef `-1` terug.

Voor zulke specificaties is het handig om eerst pure hulpfuncties te definiëren:

```java
//@ pure
private static boolean contains(List<Integer> list, int x) {
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i) == x) return true;
    }
    return false;
}

//@ pure
private static boolean isSorted(List<Integer> list) {
    for (int i = 0; i + 1 < list.size(); i++) {
        if (list.get(i) > list.get(i + 1)) return false;
    }
    return true;
}
```

### Implementatie 1: lineair zoeken

```java
//@ requires list != null;
//@ ensures (!contains(list, x)) ==> (\result == -1);
//@ ensures (\result != -1) ==> (0 <= \result && \result < list.size());
//@ ensures (\result != -1) ==> (list.get(\result) == x);
public static int indexOfLinear(List<Integer> list, int x) {
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i) == x) return i;
    }
    return -1;
}
```

### Implementatie 2: binair zoeken

Voor binair zoeken komt er een extra preconditie bij:

```java
//@ requires list != null;
//@ requires isSorted(list);
//@ ensures (!contains(list, x)) ==> (\result == -1);
//@ ensures (\result != -1) ==> (0 <= \result && \result < list.size());
//@ ensures (\result != -1) ==> (list.get(\result) == x);
public static int indexOfBinary(List<Integer> list, int x) {
    int lo = 0;
    int hi = list.size() - 1;

    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        int value = list.get(mid);

        if (value == x) return mid;
        if (value < x) lo = mid + 1;
        else hi = mid - 1;
    }
    return -1;
}
```

Als de preconditie `isSorted(list)` ontbreekt, is de tweede implementatie niet betrouwbaar.

### Interface versus implementatie

Als je parameters typt als `List<T>` in plaats van `ArrayList<T>`,
beschrijf je gedrag op interface-niveau.
Dat maakt het eenvoudiger om later de interne datastructuur te wijzigen,
zonder het contract voor gebruikers te veranderen.

## Contracten

Tot nu toe beschreven we specificaties in woorden.
Met OpenJML maak je ze exact en machineleesbaar.

- **preconditie**: `requires`
- **postconditie**: `ensures`
- **gewijzigde toestand**: `assignable`

### Voorbeeld 1: `max`

```java
//@ requires true;
//@ ensures \result >= a && \result >= b;
//@ ensures \result == a || \result == b;
public static int max(int a, int b) {
    return (a >= b) ? a : b;
}
```

### Voorbeeld 2: `withdraw`

```java
class BankAccount {
    //@ spec_public
    private int balanceInCents;

    //@ public invariant balanceInCents >= 0;

    //@ requires initialBalanceInCents >= 0;
    //@ ensures balanceInCents == initialBalanceInCents;
    //@ assignable balanceInCents;
    public BankAccount(int initialBalanceInCents) {
        this.balanceInCents = initialBalanceInCents;
    }

    //@ requires amountInCents > 0 && amountInCents <= balanceInCents;
    //@ ensures balanceInCents == \old(balanceInCents) - amountInCents;
    //@ assignable balanceInCents;
    public void withdraw(int amountInCents) {
        if (amountInCents <= 0 || amountInCents > balanceInCents) {
            throw new IllegalArgumentException("invalid amount");
        }
        balanceInCents -= amountInCents;
    }

    public int balanceInCents() {
        return balanceInCents;
    }
}
```

`\old(balanceInCents)` verwijst naar de waarde vóór de methode-uitvoering.

### Voorbeeld 3: `berekenParkeerkost`

Ook voor het intuïtieve voorbeeld kan je een contract precies maken:

```java
//@ requires duurInMinuten >= 0;
//@ ensures \result >= 0;
//@ ensures duurInMinuten <= 30 ==> \result == 0;
//@ ensures duurInMinuten > 30 ==> \result > 0;
//@ assignable \nothing;
public static int berekenParkeerkost(int duurInMinuten) {
    if (duurInMinuten <= 30) return 0;
    int extra = duurInMinuten - 30;
    int halveUren = (extra + 29) / 30;
    return halveUren * 2;
}
```

Merk op: dit contract legt het gedrag al stevig vast, maar je kan het nog uitbreiden
als je ook exact het tarief per begonnen half uur formeel wil vastleggen.

### Schending van precondities

Als een preconditie niet geldt, moet dat ook duidelijk gespecificeerd zijn.
Een veelgebruikte praktijk is dan een exception gooien.
Je kan dit in OpenJML expliciteren met `exceptional_behavior`, maar voor dit hoofdstuk volstaat het om die afspraak consequent te documenteren.

## Invarianten

Contracten per methode zijn sterk, maar bij toestandrijke objecten wil je ook eigenschappen die altijd behouden blijven.
Dat zijn invarianten.

- klasse-invariant: eigenschap over objecttoestand,
- loop-invariant: eigenschap die waar blijft tijdens een lus.

### Klasse-invariant

Voor `BankAccount` gebruikten we:

```java
//@ public invariant balanceInCents >= 0;
```

Die moet gelden na constructor en na elke publieke methode.

### Loop-invariant

Bij een som over een lijst:

```java
//@ pure
private static int prefixSum(List<Integer> values, int endExclusive) {
    int s = 0;
    for (int k = 0; k < endExclusive; k++) s += values.get(k);
    return s;
}

public static int sum(List<Integer> values) {
    int s = 0;
    int i = 0;

    //@ loop_invariant 0 <= i && i <= values.size();
    //@ loop_invariant s == prefixSum(values, i);
    //@ decreases values.size() - i;
    while (i < values.size()) {
        s = s + values.get(i);
        i = i + 1;
    }
    return s;
}
```

Deze annotaties maken expliciet waarom de lus correct is.

## Tests versus specificaties

Nu we contracten hebben, kunnen we het verschil scherp maken:

- een specificatie zegt wat voor **alle** geldige inputs moet gelden,
- een test controleert slechts enkele concrete voorbeelden.

Tests en specificaties vullen elkaar aan.
Contracten geven richting aan testontwerp.
Tests geven snelle feedback tijdens implementatie.

### Tegenvoorbeeld

Foute implementatie:

```java
public static int max(int a, int b) {
    return a; // fout: b wordt genegeerd
}
```

Beperkte tests:

```java
@Test
void maxOfEqualNumbers() {
    assertEquals(5, max(5, 5));
}

@Test
void maxWhenFirstIsBigger() {
    assertEquals(9, max(9, 2));
}
```

Deze tests zijn groen, maar `max(2, 9)` blijft fout.

Met OpenJML-contracten kan je hier extra zekerheid opbouwen:
als de contracten sterk genoeg zijn, kan de verifier zulke implementaties afkeuren.

Een gelijkaardig risico bestaat bij `berekenParkeerkost`.
Met alleen deze tests:

```java
@Test
void kostTot30MinutenIsNul() {
    assertEquals(0, berekenParkeerkost(15));
}

@Test
void kostOp30MinutenIsNul() {
    assertEquals(0, berekenParkeerkost(30));
}
```

kan een foute implementatie die altijd `0` teruggeeft nog steeds groen zijn.
Daarom heb je ook tests nodig boven 30 minuten, en liefst rechtstreeks afgeleid uit je contract.

### Conclusies

1. Zonder expliciete specificatie missen tests snel belangrijke gevallen.
2. "Groen" betekent alleen dat je huidige testset slaagt.
3. OpenJML-contracten en tests samen geven een veel sterker kwaliteitsnet.

## Hoare-logica

Hoare-logica beschrijft correctheid met triples:

\[
\{P\}\ C\ \{Q\}
\]

Interpretatie:
als preconditie `P` geldt vóór commando `C`, dan geldt postconditie `Q` erna.

OpenJML kan je zien als een praktische, code-nabije manier om diezelfde redenering toe te passen op Java-methodes:
- `P` komt overeen met `requires`,
- `Q` met `ensures`.

Ook voor `berekenParkeerkost` kan je zo redeneren:
\[
\{\texttt{duurInMinuten >= 0}\}\ \texttt{berekenParkeerkost(duurInMinuten)}\ \{\texttt{resultaat >= 0}\}
\]

Dat is precies dezelfde gedachte als het OpenJML-contract van die methode.

### Achterwaarts redeneren

Voor:

```java
x = x + 1;
```

en postconditie `x > 10` heb je preconditie `x > 9` nodig:

\[
\{x > 9\}\ x = x + 1\ \{x > 10\}
\]

Voor:

```java
y = x + 2;
```

met doel `y > 5` heb je `x > 3` nodig:

\[
\{x > 3\}\ y = x + 2\ \{y > 5\}
\]

### Sequentie van statements

```java
x = x + 1;
y = x + 2;
```

Doel: `y > 10`.

Dan volstaat preconditie `x > 7`:

\[
\{x > 7\}\ x = x + 1;\ y = x + 2\ \{y > 10\}
\]

### Lussen en invarianten

Bij lussen combineer je:
- een loop-invariant (blijft waar),
- een terminatie-argument (`decreases`).

Dat is precies dezelfde structuur als in OpenJML-lusannotaties.

## Samenvatting en checklist

Gebruik deze checklist bij ontwerp, review en implementatie:

1. Staat het gewenste gedrag expliciet beschreven?
2. Zijn precondities (`requires`) duidelijk?
3. Zijn postcondities (`ensures`) volledig genoeg?
4. Sluiten de specificaties ongewenste implementaties uit?
5. Zijn invarianten (klasse/lus) expliciet?
6. Is duidelijk welke toestand gewijzigd mag worden (`assignable`)?
7. Dekt de testset de belangrijkste inputklassen?
8. Ondersteunen contracten en tests elkaar?
9. Kan je de kernredenering samenvatten als `{P} C {Q}`?

Wie op deze manier werkt, schrijft code met voorspelbaar gedrag en minder regressies.
