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

### Voorbeeld: `max(a, b)`

Voor de methode `max(a, b)` kan je bijvoorbeeld deze specificatie formuleren:
- het resultaat is groter dan of gelijk aan `a`,
- het resultaat is groter dan of gelijk aan `b`,
- het resultaat is gelijk aan `a` of gelijk aan `b`.

### Volledigheid van specificaties

Bij specificaties gaat het niet alleen over juistheid, maar ook over **volledigheid**.
Een te zwakke specificatie laat implementaties toe die formeel “kloppen”, maar inhoudelijk fout zijn.

Stel dat je voor `max(a, b)` alleen dit noteert:
- resultaat is groter dan of gelijk aan `a`,
- resultaat is groter dan of gelijk aan `b`.

Dan is deze implementatie volgens die (te zwakke) specificatie perfect toegelaten:

```java
public static int max(int a, int b) {
    return Integer.MAX_VALUE;
}
```

Voor alle `int`-waarden geldt immers dat `Integer.MAX_VALUE >= a` en `>= b`.
Toch is dit duidelijk niet wat we bedoelen met “maximum van twee getallen”.

Daarom staat in de volledige specificatie nog een extra voorwaarde:
- resultaat is gelijk aan `a` of gelijk aan `b`.

Die derde lijn sluit de foute implementatie meteen uit.
Dit is een goede illustratie van een algemene regel:
een bruikbare specificatie moet niet enkel gewenste gevallen toelaten,
maar ook **ongewenste implementaties actief blokkeren**.

Merk op wat deze specificatie **niet** zegt:
- niet welke `if`-structuur je gebruikt,
- niet of je `Math.max` oproept,
- niet hoe de compiler de code optimaliseert.

Die zaken horen bij de implementatie.

## Specificatie en implementatie

In het vorige stuk zagen we al dat een specificatie gedrag beschrijft zonder codekeuzes.
Hier maken we dat onderscheid expliciet en systematisch.

Het onderscheid tussen **wat** en **hoe** is de kern van dit hoofdstuk:
- **specificatie**: _wat_ moet waar zijn vanuit het standpunt van de gebruiker van de methode,
- **implementatie**: _hoe_ je dat gedrag technisch realiseert in code.

Neem opnieuw `max(a, b)`. Volgende implementaties zijn verschillend, maar gedragsequivalent:

```java
public static int max(int a, int b) {
    if (a >= b) return a;
    return b;
}
```

```java
public static int max(int a, int b) {
    return Math.max(a, b);
}
```

Beide voldoen aan dezelfde specificatie.

### Waarom dit belangrijk is

1. **Refactoren wordt veiliger**
   Je mag intern herwerken zolang de specificatie identiek blijft.
2. **Samenwerken wordt duidelijker**
   Teamleden discussiëren over gedrag, niet over persoonlijke codevoorkeuren.
3. **Testen krijgt richting**
   Tests moeten specificatie valideren, niet toevallige implementatiedetails.
4. **Vervangbaarheid**
   Je kan implementaties uitwisselen zonder de rest van het systeem te breken.

Een praktische vuistregel:
als een zin de woorden "for-loop", "hashmap", "recursie", "if", "pointer" bevat,
dan is het vaak implementatie, geen specificatie.

## Meerdere implementaties

Nu komt de belangrijkste consequentie van dat onderscheid:
als specificatie en implementatie echt los staan, dan moeten meerdere implementaties mogelijk zijn onder dezelfde specificatie.

Een krachtige gedachte in softwareontwerp:
**één specificatie kan meerdere correcte implementaties hebben**.

We werken dit uit met `indexOf` op een `List<Integer>`.

### Specificatie

Voor methode `indexOf(list, x)`:
- als `x` in `list` voorkomt, geef een geldige index `i` terug zodat `list.get(i) == x`,
- als `x` niet voorkomt, geef `-1` terug.

Je kan deze specificatie nog strikter maken met extra keuzes, bijvoorbeeld:
- "geef de **eerste** index terug als `x` meerdere keren voorkomt".

Zonder die extra zin zijn meerdere antwoorden soms correct.

### Implementatie 1: lineair zoeken

```java
public static int indexOfLinear(List<Integer> list, int x) {
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i) == x) return i;
    }
    return -1;
}
```

Eigenschap:
- werkt op elke `List<Integer>`,
- geen extra preconditie nodig.

### Implementatie 2: binair zoeken

```java
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

Eigenschap:
- alleen correct als de lijst oplopend gesorteerd is.

Die voorwaarde hoort expliciet in de specificatie, bijvoorbeeld als preconditie:

\[
\forall i,j: 0 \le i < j < n \Rightarrow \texttt{list.get(i)} \le \texttt{list.get(j)}
\]

waarbij \( n = \texttt{list.size()} \).

Als je die preconditie niet communiceert, krijg je schijnbaar "random" fouten bij niet-gesorteerde input.

### Interface versus implementatie

Als je als parameter type `List<T>` gebruikt, laat je ook meerdere **datastructuur-implementaties** toe, zoals:
- `ArrayList<T>`
- `LinkedList<T>`

Ook dat is exact dezelfde ontwerpgedachte:
- specificatie op interface-niveau,
- meerdere implementaties op intern niveau.

Met andere woorden: het is geen detail, maar een kernprincipe van goed ontwerp.
Je legt vast wat gebruikers mogen verwachten, en laat ruimte om het hoe later te verbeteren.

## Contracten

Tot nu toe beschreven we specificaties vooral in gewone taal.
Dat is een goede start, maar voor implementatie en testen wil je meestal preciezere afspraken.
Daar komen **contracten** in beeld.

Een methodecontract maakt specificaties heel concreet.

- **preconditie**: wat de aanroeper moet garanderen vóór de oproep.
- **postconditie**: wat de methode garandeert na afloop, op voorwaarde dat de preconditie gold.

De verantwoordelijkheden zijn dus verdeeld.
Dat voorkomt discussies zoals "maar ik dacht dat negatieve bedragen ook mochten".

### Voorbeeld 1: `max`

```java
/**
 * @pre  true
 * @post result >= a && result >= b
 * @post result == a || result == b
 */
public static int max(int a, int b) {
    return (a >= b) ? a : b;
}
```

Hier is de preconditie triviaal (`true`).
Bij andere methodes is de preconditie vaak essentieel.

### Voorbeeld 2: `withdraw`

```java
class BankAccount {
    private int balanceInCents;

    public BankAccount(int initialBalanceInCents) {
        if (initialBalanceInCents < 0) {
            throw new IllegalArgumentException("initial balance must be >= 0");
        }
        this.balanceInCents = initialBalanceInCents;
    }

    /**
     * @pre  amountInCents > 0 && amountInCents <= balanceInCents
     * @post balanceInCents == old(balanceInCents) - amountInCents
     */
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

`old(balanceInCents)` betekent: de waarde vóór de uitvoering.

Belangrijk inzicht:
postcondities gaan vaak over relaties tussen "voor" en "na".
Voor toestandrijke objecten is dat cruciaal.

### Schending van precondities

Daar zijn verschillende strategieën voor:
- exception gooien,
- foutcode teruggeven,
- defensief normaliseren.

Welke strategie je kiest is een ontwerpbeslissing, maar moet zelf ook deel uitmaken van de specificatie.

## Invarianten

Contracten per methode zijn krachtig, maar vaak nog niet voldoende.
Bij objecten met toestand wil je ook eigenschappen die over **alle** methodes heen blijven gelden.
Dat zijn invarianten.

Een **invariant** is een eigenschap die altijd behouden moet blijven tijdens een bepaalde levensduur.

- bij objecten: klasse-invariant,
- bij lussen: loop-invariant.

Invarianten zijn bijzonder nuttig omdat ze stabiliteit brengen:
je hoeft niet telkens alles opnieuw te bewijzen, je vertrekt van iets dat altijd waar blijft.

### Klasse-invariant

Voor `BankAccount` is een natuurlijke invariant:

\[
\texttt{balanceInCents} \ge 0
\]

Die moet gelden:
- na constructor,
- voor en na elke publieke methode,
- na elke combinatie van method calls.

Bij een klasse met collecties kan een invariant er zo uitzien:
- de lijst bevat geen `null`-elementen,
- de lijst is steeds oplopend gesorteerd,
- de lijst bevat enkel unieke sleutels.

Voorbeeldidee:

```java
class GuestList {
    private final List<String> names = new ArrayList<>();

    // mogelijke invariant:
    // - geen nulls
    // - geen lege strings
    // - geen duplicaten
}
```

### Loop-invariant

We berekenen de som van een lijst:

```java
public static int sum(List<Integer> values) {
    int s = 0;
    int i = 0;
    while (i < values.size()) {
        s = s + values.get(i);
        i = i + 1;
    }
    return s;
}
```

Mogelijke loop-invariant:

\[
s = \sum_{k=0}^{i-1} \texttt{values.get(k)}
\]

Interpretatie:
- vóór de lus: `i = 0`, dus som over een leeg bereik is 0,
- tijdens de lus: `s` blijft de som van exact de reeds verwerkte prefix,
- na de lus: `i = values.size()`, dus `s` is de som van alle elementen.

Dat is precies de brug van lokale stapjes naar globale correctheid.

## Tests versus specificaties

Nu we precondities, postcondities en invarianten hebben, kunnen we hun relatie met testen helder maken.

Tests en specificaties vullen elkaar aan, maar ze zijn fundamenteel verschillend.

- specificatie: universele uitspraak over gedrag,
- test: concrete observatie op één testcase.

Een testset kan nooit letterlijk alle inputs uitproberen.
Daarom is een formele of semiformele specificatie onmisbaar als referentie.

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

Deze tests slagen, maar `max(2, 9)` geeft fout resultaat.

### Conclusies

1. Tests zonder duidelijke specificatie missen snel belangrijke gevallen.
2. "Groen" betekent enkel: alle geschreven tests slagen.
3. Niet-geteste delen van het invoerdomein blijven risico.

Praktisch patroon:
- stap 1: schrijf contract (pre/post/invarianten),
- stap 2: leid tests af uit dat contract,
- stap 3: voeg extra tests toe voor grensgevallen en tegenvoorbeelden.

Voor `max` zijn bijvoorbeeld nuttige categorieën:
- `a < b`
- `a == b`
- `a > b`
- negatieve waarden
- combinaties met 0.

## Hoare-logica

Tests geven dus steekproeven.
Soms wil je sterker redeneren: niet “dit geval werkt”, maar “elke toestand die aan de preconditie voldoet leidt tot de postconditie”.
Dat is precies de intuïtie achter Hoare-logica.

Hoare-logica beschrijft correctheid met triples:

\[
\{P\}\ C\ \{Q\}
\]

Lees dit als:
"Als `P` waar is vóór commando `C`, dan is `Q` waar na uitvoering van `C`."

Belangrijk: dit gaat over **partiële correctheid**.
Dat betekent: als het programma terminaal eindigt, dan geldt de postconditie.
Terminatie op zich vraagt vaak een extra argument (bijvoorbeeld een variant die afneemt).

### Achterwaarts redeneren

Voor:

```java
x = x + 1;
```

En gewenste postconditie `x > 10`:
- na uitvoering wil je `x > 10`,
- dus vóór uitvoering moet oude `x > 9` zijn.

\[
\{x > 9\}\ x = x + 1\ \{x > 10\}
\]

Nog een:

```java
y = x + 2;
```

Gewenste postconditie `y > 5` geeft preconditie `x > 3`.

\[
\{x > 3\}\ y = x + 2\ \{y > 5\}
\]

### Sequentie van statements

```java
x = x + 1;
y = x + 2;
```

Doel: `y > 10` na beide statements.

Redenering:
1. vóór tweede statement heb je `x > 8` nodig (want `x + 2 > 10`).
2. vóór eerste statement heb je dus `x > 7` nodig.

Dus een geldige triple is:

\[
\{x > 7\}\ x = x + 1;\ y = x + 2\ \{y > 10\}
\]

Dit is de intuïtie van formeel bewijzen: je maakt tussenstappen expliciet.

### Lussen en invarianten

Bij lussen combineer je twee elementen:
- loop-invariant (blijft waar),
- terminatie-idee (er is vooruitgang).

Voorbeeld in woorden:
- invariant zegt dat `s` de som van de reeds verwerkte prefix is,
- `i` stijgt elke iteratie,
- `i < values.size()` wordt uiteindelijk false,
- bij exit combineer je invariant + lusconditie om de postconditie af te leiden.

Daarmee wordt duidelijk waarom invarianten geen "extra theorie" zijn, maar een praktisch hulpmiddel om lussen te begrijpen en te vertrouwen.

## Samenvatting en checklist

Gebruik deze checklist telkens je een methode ontwerpt, reviewt of refactort:

1. Staat expliciet beschreven **wat** de methode doet?
2. Is het verschil tussen gedrag en implementatiedetails helder?
3. Zijn precondities en postcondities concreet en toetsbaar?
4. Zijn relevante invarianten benoemd?
5. Is duidelijk of meerdere implementaties toegelaten zijn?
6. Dekt de testset alle belangrijke inputklassen uit de specificatie?
7. Kan je de kernredenering eventueel samenvatten met `{P} C {Q}`?
8. Is de specificatie volledig genoeg om “technisch geldige maar inhoudelijk foute” implementaties uit te sluiten?

Wie op deze manier werkt, schrijft code die voorspelbaar gedrag heeft,
robuuster is tegen regressies en veel makkelijker onderhoudbaar blijft over meerdere semesters of projectjaren heen.
