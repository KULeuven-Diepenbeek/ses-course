---
title: "Specificatie: oefeningen"
autonumbering: true
weight: 20
toc: true
draft: false
math: true
---

## Oefening 1: specificatie of implementatie?

Geef voor elke uitspraak aan of die vooral bij de **specificatie** hoort, of bij de **implementatie**.
Motiveer telkens kort in één zin.

1. "De methode `indexOf` geeft `-1` terug als het element niet voorkomt."
2. "De methode `indexOf` gebruikt een `for`-lus en vergelijkt elk element."
3. "Voor `binarySearch` moet de inputlijst oplopend gesorteerd zijn."
4. "De methode gebruikt twee indexen `lo` en `hi`."
5. "Na `withdraw(amount)` is het saldo exact `oudSaldo - amount`."
6. "De implementatie gebruikt intern een `LinkedList` in plaats van een `ArrayList`."

## Oefening 2: schrijf pre- en postcondities

Schrijf voor elke methode een zinvolle preconditie en postconditie.
Gebruik duidelijke, controleerbare formuleringen.

```java
public static int divide(int a, int b) {
    return a / b;
}

public static <T> T elementAt(List<T> values, int i) {
    return values.get(i);
}

public static void transfer(BankAccount from, BankAccount to, int amountInCents) {
    from.withdraw(amountInCents);
    to.deposit(amountInCents);
}
```

Vervolgvragen:
1. Welke preconditie ontbreekt er om `transfer` veilig te maken?
2. Welke postconditie zou je toevoegen om "geld verdwijnt niet" expliciet te maken?

## Oefening 3: invarianten herkennen en herstellen

Beschouw deze klasse:

```java
class TemperatureLog {
    private final List<Double> values = new ArrayList<>();

    public void add(double t) {
        values.add(t);
    }

    public double average() {
        double sum = 0.0;
        for (double v : values) sum += v;
        return sum / values.size();
    }
}
```

1. Formuleer minstens één klasse-invariant.
2. Waar kan de invariant momenteel geschonden worden?
3. Pas het ontwerp conceptueel aan zodat de invariant altijd behouden blijft.
4. Formuleer een mogelijke loop-invariant voor de lus in `average`.
5. Geef één testcase die een schending van jouw invariant zichtbaar maakt.

## Oefening 4: meerdere implementaties, één specificatie

Je wil een methode `contains(List<Integer> values, int x)`.

1. Schrijf een specificatie die zowel een lineaire als een binaire zoekimplementatie toelaat.
2. Voeg de extra preconditie toe die nodig is voor de binaire variant.
3. Leg uit waarom beide implementaties toch onder dezelfde functionele bedoeling vallen.
4. Leg uit welke gedragskeuzes je expliciet moet vastleggen in de specificatie (bv. eerste index bij duplicaten).
5. Geef een voorbeeldinput waarvoor de binaire variant fout gedrag kan geven als de preconditie niet geldt.

## Oefening 5: tests zijn groen, maar specificatie fout

Je krijgt volgende implementatie en tests:

```java
public static int max(int a, int b) {
    return a;
}
```

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

1. Geef een input waarvoor de implementatie fout is, ondanks groene tests.
2. Formuleer de ontbrekende specificatie-eis expliciet.
3. Voeg minstens drie tests toe die die eis wel afdekken.
4. Duid per test aan welke eigenschap van de specificatie ze controleert.

## Oefening 6: Hoare-logica invullen

Vul de ontbrekende voorwaarden in:

\[
\{\ ?\ \}\ y = x + 4\ \{\ y > 10\ \}
\]

\[
\{\ x \ge 0\ \}\ x = x + 1\ \{\ ?\ \}
\]

\[
\{\ ?\ \}\ z = 2*z\ \{\ z > 20\ \}
\]

Werk telkens als volgt:
1. schrijf de gewenste postconditie,
2. redeneer achterwaarts naar een voldoende preconditie,
3. controleer met één concreet getal.

## Oefening 7: mini-redeneerketen met invariant

Beschouw:

```java
public static int countPositive(List<Integer> values) {
    int i = 0;
    int c = 0;
    while (i < values.size()) {
        if (values.get(i) > 0) c++;
        i++;
    }
    return c;
}
```

1. Stel een loop-invariant op voor `i` en `c`.
2. Toon kort aan waarom die invariant initieel waar is.
3. Toon kort aan waarom die invariant behouden blijft in één iteratie.
4. Leid op basis van invariant + stopvoorwaarde de postconditie af.

## Suggestie voor zelfcontrole

Los elke oefening eerst informeel op in woorden, en herschrijf nadien in precieze voorwaarden.
Als je twijfelt, zoek een tegenvoorbeeld: één invoer die je formulering onderuit haalt.
