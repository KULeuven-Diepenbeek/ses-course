---
title: "9. Specificaties"
weight: 130
draft: false
toc: true
autonumbering: true
math: true
---

Wanneer is software correct? Hoe weet je dat een implementatie doet wat het moet doen?
Zeker in een wereld waar meer en meer code door AI-tools gegenereerd wordt, is het belangrijk om een goed begrip te hebben van wat correct gedrag is, en hoe je dat kan specificeren en controleren, zonder blind te vertrouwen op de gegenereerde code.

Om dat te onderzoeken, bekijken we in dit hoofdstuk hoe je softwaregedrag precies kan beschrijven, los van de concrete code die dat gedrag realiseert.
We focussen op specificaties, contracten, invarianten, en op hoe je met tests en formele redeneringen (zoals Hoare-logica) kan nagaan of een implementatie correct is.

Het domein van specificatie en correctheid is enorm breed, met veel theorie en tools.
We beperken ons hier daarom tot een eerste kennismaking met de kernconcepten.


## Specificaties

Een **specificatie** beschrijft het gewenste gedrag van software.
Ze zegt wat correct gedrag is, zonder vast te leggen hoe je dat gedrag intern programmeert.
Of nog: ze legt vast wat de (gewenste) relatie is tussen invoer en uitvoer (of begin- en eindtoestand), zonder te zeggen welke algoritmen, datastructuren of codeconstructies je moet gebruiken.

Dat klinkt eenvoudig, maar in de praktijk is dit een van de belangrijkste denkoefeningen in softwareontwikkeling.
Veel bugs ontstaan niet door een typfout in code, maar doordat de bedoeling onduidelijk of impliciet was.
Een goede specificatie maakt die bedoeling expliciet.

Zeker voor grote softwaresystemen, met meerdere modules en teams, is het essentieel dat iedereen dezelfde verwachtingen heeft over wat een software-component moet doen.
Ook voor onderhoud en toekomstige uitbreidingen is het belangrijk dat de oorspronkelijke bedoelingen duidelijk blijven, zelfs als de code verandert.
Tenslotte spelen specificaties ook een belangrijke rol bij het gebruik van GenAI om code te genereren: hoe duidelijker en preciezer de specificatie (prompt), hoe beter de gegenereerde code zal aansluiten bij de bedoeling.

Je kan een specificatie zien als een **contract** tussen verschillende rollen:
- de gebruiker of opdrachtgever die gedrag verwacht,
- de ontwikkelaar (of AI-model) die dat gedrag implementeert,
- de tester die controleert of de implementatie voldoet aan de verwachtingen,
- de toekomstige maintainer die code moet aanpassen of uitbreiden zonder het gewenste gedrag te breken.

### Intuïtief voorbeeld

Stel dat je een parkeerapp bouwt.
Een mogelijke specificatie van de methode `int berekenParkeerkost(int duurInMinuten)` is:

- het resultaat (de kost) is nooit negatief,
- tot en met 30 minuten is de kost 0 euro,
- boven 30 minuten betaal je een gekend bedrag (1 euro) per begonnen half uur.
- vanaf 6 uur (360 minuten) is er een vast dagtarief van 12 euro.

Die specificatie zegt niets over hoe je dit implementeert.
Je kan intern werken met `if`-statements, lussen, een tabel, of een formule: allemaal prima, zolang het externe gedrag overeenkomt met de specificatie.

Deze specificatie kan je ook onmiddellijk omzetten in een aantal tests:

```java
@Test
void kostTot30MinutenIsNul() {
    assertThat(berekenParkeerkost(15)).isEqualTo(0);
}

@Test
void kostOp30MinutenIsNul() {
    assertThat(berekenParkeerkost(30)).isEqualTo(0);
}

@Test
void kostNetBoven30MinutenIsEersteTarief() {
    assertThat(berekenParkeerkost(31)).isEqualTo(1);
}

@Test
void kostOp60MinutenIsTweedeTarief() {
    assertThat(berekenParkeerkost(60)).isEqualTo(2);
}   

@Test
void kostOp360MinutenIsVastTarief() {
    assertThat(berekenParkeerkost(360)).isEqualTo(12);
}

@Test
void kostBoven360MinutenIsVastTarief() {
    assertThat(berekenParkeerkost(400)).isEqualTo(12);
}
```

Merk op hoe je aan de hand van bovenstaande specificatie zinvolle tests kan ontwerpen zonder te hoeven weten hoe de implementatie eruit ziet (of zal zien, als je volgens TDD eerst de tests schrijft en pas daarna de implementatie).


### Specificatie en implementatie

Een specificatie beschrijft dus gedrag zonder codekeuzes. De implementatie wordt gevormd door een keuze van algoritmen, datastructuren, en codeconstructies die dat gewenste gedrag moeten realiseren. Dus:

- **specificatie** (of **contract**): wat moet waar zijn vanuit het standpunt van de gebruiker,
- **implementatie**: hoe je dat gedrag technisch realiseert.

Voor eenzelfde specificatie zijn gewoonlijk meerdere implementaties mogelijk.
Bijvoorbeeld, gegeven de specificatie van een `max`-functie die het grootste van 2 getallen teruggeeft, zijn volgende implementaties allemaal correct:

```java
public static int max(int a, int b) {
    if (a >= b) return a;
    return b;
}
```

```java
public static int max(int a, int b) {
    return (a > b) ? a : b;
}
```

```java
public static int max(int a, int b) {
    return Math.max(a, b);
}
```

Zolang al deze methodes aan hetzelfde contract voldoen, zijn ze extern equivalent.

Het splitsen van specificatie en implementatie heeft verschillende voordelen:

1. **Refactoren wordt veiliger**
   Je mag intern herwerken zolang de specificatie (het contract) gelijk blijft.
2. **Samenwerken wordt duidelijker**
   De grenzen waarbinnen code veranderd mag worden zonder impact te hebben op een ander team worden expliciet gemaakt.
3. **Testen krijgt richting**
   Tests worden afgeleid uit contracten, en zijn niet sterk gekoppeld aan de implementatie.

### Pre- en postcondities van methodes

Om het concept van specificaties concreter te maken, introduceren we de begrippen **precondities** en **postcondities**.

Een **preconditie** is een voorwaarde die waar moet zijn **vóór** de methode start.
Meer bepaald is het een voorwaarde op de invoer en/of de begintoestand.
De preconditie beschrijft dus de aannames die een methode maakt over zijn omgeving.

Een **postconditie** is een voorwaarde die waar moet zijn **na** de methode, tenminste als de preconditie geldig was.
Het is dus een voorwaarde op de uitvoer en/of de eindtoestand.
De postconditie beschrijft dus het gewenste resultaat van de methode (voor alle geldige invoer).

Belangrijk hierbij is de taakverdeling:

- de **aanroeper** van de methode is verantwoordelijk om de preconditie te respecteren,
- de **methode** zelf (of de programmeur ervan) is verantwoordelijk om de postconditie waar te maken.

Een methode is **correct geïmplementeerd** met betrekking tot een specificatie (pre- en postconditie) als, telkens de preconditie geldt vóór de methode-uitvoering, ook de postconditie geldt na de uitvoering (en de uitvoering blijft niet oneindig doorlopen en gooit geen exception).
Met andere woorden: in elke toestand die aan de preconditie voldoet, zal de uitvoering van de methode gegarandeerd leiden tot een toestand die aan de postconditie voldoet.
Dit is schematisch weergegeven in volgende afbeelding:

<img src="/img/prepost.png" alt="Pre- en postcondities" style="max-width: 450px;">

We schrijven dit ook als een zogenaamd **Hoare-triple** $ \{P\}\ C\ \{Q\} $ waarbij `P` de preconditie is, `C` het commando (de methode) en `Q` de postconditie.
Dat betekent exact wat we hierboven beschreven hebben: als `P` waar is vóór het uitvoeren van `C`, dan is `Q` waar na het uitvoeren ervan.

Merk hierbij op dat niet voldoen aan de preconditie niet noodzakelijk leidt tot een fout: het betekent alleen dat er geen garanties zijn over het gevolg. Het is best mogelijk dat de methode eindigt in een toestand die nog steeds voldoet aan de postconditie, maar dat is niet gegarandeerd.

{{% notice note Notatie %}}
Om pre- en postcondities precies te specificeren voor Java-code, halen we in dit hoofdstuk inspiratie uit [**OpenJML**](https://www.openjml.org/), die JML (Java Modeling Language)-annotaties toevoegt in Java-comments.

De belangrijkste annotaties zijn:

- `requires ...;` voor precondities,
- `ensures ...;` voor postcondities,
- `\result` voor de returnwaarde,
- `\old(expr)` voor de waarde vóór uitvoering,
- `invariant ...;` voor klasse-invarianten,

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


### Voorbeeld: deling

Hieronder zie je een voorbeeld van een preconditie (weergegeven met `requires`) en postconditie (weergegeven met `ensures`) voor een methode `divide` die een gehele deling uitvoert:

```java
//@ requires a >= 0;
//@ requires b > 0;
//@ ensures a == b * \result + (a % b);
public static int divide(int a, int b) {
    return a / b;
}
```

Dit betekent:

- de correctheid van `divide` is enkel gegarandeerd als `a` niet negatief is én `b` strikt positief is (preconditie),
- onder die voorwaarde garandeert de methode de standaard eigenschap van gehele deling in Java, namelijk dat `a` gelijk is aan `b` maal het resultaat van de methode plus het restgetal (postconditie).

We kunnen dit ook schrijven als een Hoare-triple:
\[\{a \geq 0 \wedge b > 0\}\ \texttt{divide(a, b)}\ \{a == b * \texttt{\result} + (a \% b)\}\]

Merk bij de postconditie op dat we niet rechtstreeks zeggen *hoe* `\result` berekend moet worden. 
We leggen enkel een **relatie** op tussen `a`, `b` en `\result`.
Dat is het mooie van specificaties: ze leggen vast wat er moet gebeuren, zonder te zeggen hoe je dat moet doen.

Merk ook op dat deze implementatie van `divide` ook werkt als `a` en/of `b` negatief zijn.
Maar omdat de preconditie dat niet toestaat, is er geen garantie dat de postconditie nog steeds geldt in die gevallen.
Als programmeur van `divide` mag je de implementatie later dus wijzigen in een andere implementatie die misschien niet correct werkt voor negatieve getallen, zolang je maar de preconditie respecteert en de postconditie waarmaakt voor geldige invoer.

Een andere implementatie van `divide` die aan dezelfde specificatie voldoet, maar gebruik maakt van een lus in plaats van de delingsoperator, is bijvoorbeeld:

```java
//@ requires a >= 0;
//@ requires b > 0;
//@ ensures a == b * \result + (a % b);
public static int divide(int a, int b) {
    int quotient = 0;
    int remainder = a;
    while (remainder >= b) {
        remainder = remainder - b;
        quotient = quotient + 1;
    }
    return quotient;
}
```

Deze implementatie is misschien minder efficiënt, maar voldoet nog steeds aan dezelfde pre- en postcondities.
Ze werkt echter niet meer correct als `a` of `b` negatief zijn (maar dat is geen probleem, want de preconditie sluit die gevallen uit).


## Sterke en zwakke pre- en postcondities

Pre- en postcondities kunnen sterker of zwakker zijn.

- Een **sterke preconditie** stelt **meer eisen** aan de invoer, waardoor er minder situaties zijn waarin de methode correct kan worden opgeroepen.
- Een **zwakke preconditie** stelt **minder eisen**, waardoor er meer situaties zijn waarin de methode correct kan worden opgeroepen. Dit is meestal de gewenste situatie, omdat het de methode flexibeler maakt.

In termen van verzamelingen van toestanden betekent dit dat een sterke preconditie een kleinere verzameling van geldige invoer toestaat, terwijl een zwakke preconditie een grotere verzameling toestaat.

De zwakst mogelijke preconditie is `requires true;`, omdat die geen enkele beperking oplegt aan de invoer.
De sterkst mogelijke preconditie is `requires false;`. Dit laat echter geen enkele invoer toe. Met andere woorden, een methode met `requires false;` geeft in geen enkele situatie enige garantie over het resultaat.

Voor postcondities:

- Een **sterke postconditie** stelt **meer eisen** aan het resultaat, waardoor er minder mogelijke implementaties zijn die de postconditie waar maken. Vaak willen we dus een zo sterk mogelijke postconditie, omdat die meer garanties geeft over het gedrag van de methode.
- Een **zwakke postconditie** stelt **minder eisen** aan het resultaat, waardoor er meer mogelijke implementaties zijn die de postconditie waar maken.

Een sterke postconditie betekent dus dat er minder mogelijke eindtoestanden zijn die aan de postconditie voldoen, terwijl een zwakke postconditie meer mogelijke eindtoestanden toestaat.

De zwakst mogelijke postconditie is `ensures true;`, omdat die geen enkele uitspraak doet over het resultaat. De sterkst mogelijke postconditie is `ensures false;`, omdat die geen enkele eindtoestand toestaat. Met andere woorden, een methode met `ensures false;` kan nooit correct worden geïmplementeerd[^1], omdat er geen enkele uitvoer of eindtoestand is die aan de postconditie voldoet.

[^1]: In de praktijk zou een methode met `ensures false;` kunnen worden geïmplementeerd als een methode die altijd een exception gooit of oneindig blijft lopen, omdat er dan strikt genomen geen eindtoestand is, maar dat is meestal niet de bedoeling.

Hieronder zie je, als voorbeeld, een methode `max` met een zwakke preconditie én een zwakke postconditie:

```java
//@ requires true;
//@ ensures \result >= a && \result >= b;
```

De postconditie is zwak omdat het toestaat dat `\result` een waarde is die groter is dan zowel `a` en `b`, zoals `Integer.MAX_VALUE`.
Met andere woorden, volgende implementatie voldoet aan de zwakke postconditie, maar is duidelijk fout als "maximum van twee getallen":

```java
//@ requires true;
//@ ensures \result >= a && \result >= b;
public static int max(int a, int b) {
    return Integer.MAX_VALUE;
}
```

Een sterkere (betere) postconditie voor het voorbeeld is:

```java
//@ requires true;
//@ ensures \result >= a && \result >= b;
//@ ensures \result == a || \result == b;
```
Die is sterker omdat het nu vereist dat `\result` precies gelijk is aan `a` of `b`, en niet een willekeurige waarde die groter is dan een van beide.

Hetzelfde zie je bij `berekenParkeerkost`.
Als je als enige postconditie specificeert `//@ ensures \result >= 0;`, dan is volgende implementatie ook “correct”:

```java
public static int berekenParkeerkost(int duurInMinuten) {
    return 0;
}
```

Maar dat schendt duidelijk de bedoeling.
Ook hier zou de specificatie extra regels moeten bevatten, bijvoorbeeld over de drempel van 30 minuten.
In JML zou dat er als volgt kunnen uitzien:

```java
//@ requires duurInMinuten >= 0;
//@ ensures \result >= 0;
//@ ensures duurInMinuten <= 30 ==> \result == 0;
//@ ensures duurInMinuten > 30 && duurInMinuten <= 360 ==>
//@     30 * (\result - 1) < duurInMinuten - 30 
//@     && duurInMinuten - 30 <= 30 * \result;
//@ ensures duurInMinuten > 360 ==> \result == 12;
public static int berekenParkeerkost(int duurInMinuten) {
    if (duurInMinuten <= 30) return 0;
    int extra = duurInMinuten - 30;
    int halveUren = (extra + 29) / 30; // afronden naar boven
    return halveUren * 1; // euro
}
```

Merk op dat deze specificatie precies vastlegt hoe de parkeerkost berekend wordt.
Dat houdt in dat latere wijzigingen aan de parkeerregels (zoals een ander tarief of een andere drempel) ook een wijziging van de specificatie vereisen.
In sommige gevallen kan dat wenselijk zijn, maar in andere gevallen is deze postconditie te sterk en wil je een meer abstracte specificatie die de exacte berekening niet vastlegt.
Je zou bijvoorbeeld kunnen kiezen voor een specificatie die enkel zegt dat de kost nooit negatief is, en dat de kost niet kan dalen als de parkeerduur langer wordt, zonder de grenzen of het precieze bedrag vast te leggen:

```java
//@ requires duurInMinuten >= 0;
//@ ensures \result >= 0;
//
//@ ensures (\forall int d;
//@     d >= 0 && d <= duurInMinuten
//@     ==> berekenParkeerkost(d) <= \result);
public static int berekenParkeerkost(int duurInMinuten) {
    ...
}
```

We maken hier gebruik van `\forall` om te zeggen dat voor alle `d` tussen `0` en `duurInMinuten`, de parkeerkost voor `d` minuten nooit groter mag zijn dan de parkeerkost van `duurInMinuten`.
Deze specificatie is zwakker dan de vorige versie omdat het niet de exacte drempels of bedragen vastlegt.
Deze specificatie laat ook mogelijk ongewenste implementaties toestaat, zoals een implementatie die altijd `0` teruggeeft.
Ze legt wel nog steeds een belangrijke relatie vast tussen de duur en de kost.
De beste specificatie hangt dus af van de context (bv. hoe vaak de parkeerregels aangepast moeten kunnen worden).

{{% notice tip Onthoud %}}
We zijn vaak geïnteresseerd in

- een voldoende **zwakke preconditie** (zodat de methode in zoveel mogelijk situaties kan worden opgeroepen), en
- een voldoende **sterke postconditie** (zodat we heel precieze garanties krijgen over wat de methode precies doet).

Een bruikbare specificatie moet voldoende **flexibel** zijn, **correcte implementaties toelaten**, en ook **ongewenste implementaties uitsluiten**.
{{% /notice %}}

## Specificaties en wijzigen van toestand

Tot nu toe beschreven we specificaties van individuele methodes, maar we kunnen ook specificaties maken over toestandsveranderingen binnen een object of tussen methodes.

Bijvoorbeeld, voor een `BankAccount`-klasse kunnen we specificeren dat het saldo nooit negatief mag zijn, en dat een `withdraw`-methode het saldo met een bepaald bedrag vermindert.

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

We zien hier enkele opvallendheden:

- `invariant` geeft aan dat `balanceInCents` altijd groter of gelijk aan 0 moet zijn, ongeacht welke methode wordt aangeroepen. Het concept van invarianten komt dadelijk verder aan bod.
- `spec_public` geeft aan dat een *private* veld toch gebruikt mag worden in specificaties, zodat we het kunnen gebruiken om pre- en postcondities mee te formuleren.
- `assignable balanceInCents;` geeft aan dat deze methode alleen `balanceInCents` mag wijzigen, en geen andere toestand van het object (of andere objecten).
- `\old(balanceInCents)` verwijst naar de waarde van `balanceInCents` vóór de methode-uitvoering.

### Klasse-invarianten

Contracten per methode zijn sterk, maar bij toestandrijke objecten wil je ook eigenschappen die altijd behouden blijven.
Dat zijn **(klasse-)invarianten**.

Voor `BankAccount` gebruikten we:

```java
//@ public invariant balanceInCents >= 0;
```

Die invariant moet gelden
- nadat de constructor uitgevoerd is; en
- voor en na elke uitvoering van een publieke methode.

Dat zijn de zogenaamde _zichtbare toestanden_ van het object. In deze toestanden moet de invariant altijd gelden.
Met andere woorden, je kan erop vertrouwen dat `balanceInCents` nooit negatief is, ongeacht welke methodes er worden aangeroepen, zolang je maar de precondities respecteert.

Je kan een invariant ook zien als een soort "globale pre- en postconditie" die voor en na elke publieke methode-uitvoering moet gelden.
In plaats van die bij elke methode apart te herhalen, definieer je die als een invariant die altijd behouden moet blijven.

### Voorbeelden van nuttige klasse-invarianten

- In een `User`-klasse kan een invariant zijn dat het e-mailadres altijd een geldig formaat heeft, of dat de leeftijd van de gebruiker altijd groter is dan 0.
- In een `Rectangle`-klasse kan een invariant zijn dat de breedte en hoogte altijd positief zijn, en dat de coördinaten van de hoeken consistent zijn (bijvoorbeeld dat de rechterbovenhoek altijd rechts en boven de linksonderhoek ligt).
- In een `Date`-klasse kan een invariant zijn dat de dag, maand en jaar altijd binnen geldige grenzen liggen (bijvoorbeeld dat de dag tussen 1 en 31 ligt, afhankelijk van de maand, en dat de maand tussen 1 en 12 ligt).
- In een `Employee`-klasse kan een invariant zijn dat het salaris altijd groter is dan 0, en dat de werknemer een geldige ID heeft.

## Hoare-logica

Hoare-logica is een logica die correctheid beschrijft met Hoare-triples die we eerder gezien hebben:

\[
\{P\}\ C\ \{Q\}
\]

De interpretatie daarvan was:
als preconditie `P` geldt vóór commando `C`, dan geldt postconditie `Q` erna.

OpenJML kan je zien als een praktische, code-nabije manier om dit soort specificatie toe te voegen aan Java-methodes:

- `P` komt overeen met `requires`,
- `Q` met `ensures`.
- `C` is de code van de methode zelf.

In Hoare-logica bestaan er wiskundige regels over het toekennen van Hoare-triples gebruikt voor eenvoudige statements, zoals een toewijzing aan een variabele.
Er zijn verder ook regels om te redeneren over samengestelde commando's, zoals sequenties van statements, conditionele statements, en lussen.
Hiermee kan je bewijzen dat een hele methode correct is, door de pre- en postcondities van individuele statements te combineren.

We gaan hier niet verder op in, maar het is goed om te weten dat er een formele theorie bestaat die precies beschrijft hoe je vanuit de pre- en postcondities van individuele statements de pre- en postconditie van een hele methode correct kan bewijzen.
JML en OpenJML zijn praktische tools die deze theorie toepassen in de context van Java-code.

## Oefeningen


### Specificatie of implementatie?

Geef voor elke uitspraak aan of die vooral bij de **specificatie** hoort, of bij de **implementatie**.
Motiveer telkens kort in één zin.

1. "De methode `indexOf` geeft `-1` terug als het element niet voorkomt."
2. "De methode `indexOf` gebruikt een `for`-lus en vergelijkt elk element."
3. "Voor `binarySearch` moet de inputlijst oplopend gesorteerd zijn."
4. "De methode gebruikt twee indexen `lo` en `hi`."
5. "Na `withdraw(amount)` is het saldo exact `oudSaldo - amount`."
6. "De implementatie gebruikt intern een `LinkedList` in plaats van een `ArrayList`."

{{% notice style="tip" title="Antwoorden"  expanded=false %}}

1. **Specificatie**: dit beschrijft het gewenste gedrag van de methode zonder te zeggen hoe het geïmplementeerd is.
2. **Implementatie**: dit beschrijft hoe de methode intern werkt, wat een specifieke keuze van algoritme en datastructuur impliceert.
3. **Specificatie**: dit is een vereiste voor het correct functioneren van `binarySearch`, maar zegt niets over hoe die vereiste wordt geïmplementeerd.
4. **Implementatie**: dit is een specifieke keuze in de code die aangeeft hoe de methode werkt, en is niet noodzakelijk voor het gewenste gedrag.
5. **Specificatie**: dit beschrijft het gewenste resultaat van de `withdraw`-methode, en is een garantie die de methode moet waarmaken, ongeacht hoe het intern is geïmplementeerd.
6. **Implementatie**: dit is een specifieke keuze van datastructuur die de interne werking van de methode beïnvloedt, maar heeft geen invloed op het contract dat de methode moet naleven.
{{% /notice %}}

### Specificatie van `indexOf`

Schrijf een specificatie (pre- en postcondities) voor een methode `indexOf` die een element `x` zoekt in een lijst van gehele getallen, en de index teruggeeft als `x` voorkomt, of `-1` als `x` niet voorkomt.

{{% notice style="tip" title="Antwoord"  expanded=false %}}
```java
//@ requires list != null;
//@ ensures (!contains(list, x)) ==> (\result == -1);
//@ ensures (\result != -1) ==> (0 <= \result && \result < list.size());
//@ ensures (\result != -1) ==> (list.get(\result) == x);
public static int indexOf(List<Integer> list, int x) {
    ...
}
```

Volgende elementen moeten aanwezig zijn in de specificatie:

- de preconditie dat de lijst niet null is,
- de postconditie dat als `x` niet voorkomt, het resultaat `-1` is,
- de postconditie dat als het resultaat niet `-1` is, het een geldige index is binnen de lijst,
- de postconditie dat als het resultaat niet `-1` is, het element op die index gelijk is aan `x`.

{{% /notice %}} 

### indexOf bij gesorteerde lijsten

Stel dat we een efficiëntere versie van `indexOf` uit de vorige oefening willen schrijven, gebruik makend van _binary search_ (de details zijn niet belangrijk).
Dat vereist dat de inputlijst gesorteerd is.
Wat is het effect op de specificatie van `indexOf` als we deze eis toevoegen?

{{% notice style="tip" title="Antwoord"  expanded=false %}}
We moeten een extra preconditie toevoegen die vereist dat de inputlijst gesorteerd is. Bijvoorbeeld:

```java
//@ requires list != null;
//@ requires isSorted(list);
//@ ensures (!contains(list, x)) ==> (\result == -1);
//@ ensures (\result != -1) ==> (0 <= \result && \result < list.size());
//@ ensures (\result != -1) ==> (list.get(\result) == x);
public static int indexOf(List<Integer> list, int x) {
    ...
}

public static boolean isSorted(List<Integer> list) {
    for (int i = 0; i + 1 < list.size(); i++) {
        if (list.get(i) > list.get(i + 1)) return false;
    }
    return true;
}
```

Deze extra preconditie `requires isSorted(list);` geeft aan dat de methode alleen correct werkt als de lijst gesorteerd is, wat een belangrijke beperking is voor het gebruik van deze specifieke implementatie van `indexOf`.

{{% /notice %}}

### Transfer

Stel dat we volgende transfer-methode hebben tussen twee bankrekeningen:

```java
public static void transfer(BankAccount from, BankAccount to, int amountInCents) {
    from.withdraw(amountInCents);
    to.deposit(amountInCents);
}
```

Welke pre- en postcondities zou je toevoegen om deze methode correct te specificeren?

{{% notice style="tip" title="Antwoord"  expanded=false %}}
```java
//@ requires from != null;
//@ requires to != null;
//@ requires from != to;
//@ requires amountInCents > 0;
//@ requires from.balanceInCents() >= amountInCents;
//@ ensures from.balanceInCents() == \old(from.balanceInCents()) - amountInCents;
//@ ensures to.balanceInCents() == \old(to.balanceInCents()) + amountInCents;
public static void transfer(BankAccount from, BankAccount to, int amountInCents) {
    from.withdraw(amountInCents);
    to.deposit(amountInCents);
}
```
{{% /notice %}}


Stel dat we enkel geïnteresseerd zijn in het feit dat geld niet verdwijnt tijdens de transfer, maar we willen niet vastleggen hoe het precies verdeeld wordt tussen `from` en `to`.
Hoe zou je een postconditie kunnen formuleren die dit expliciet maakt?

{{% notice style="tip" title="Antwoord"  expanded=false %}}
Om expliciet te maken dat geld niet verdwijnt tijdens de transfer, kunnen we een `ensures`-clausule toevoegen die stelt dat de totale hoeveelheid geld in beide rekeningen samen gelijk blijft. Bijvoorbeeld:

```java
//@ ensures from.balanceInCents() + to.balanceInCents() == \old(from.balanceInCents()) + \old(to.balanceInCents());
```
{{% /notice %}}


### Klasse-invarianten

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

1. Wat kan er fout gaan in deze klasse?
2. Formuleer een klasse-invariant die deze fout voorkomt.
3. Pas het ontwerp van de klasse aan zodat de invariant altijd behouden blijft.
4. Voeg een `removeLast`-methode toe die de laatste temperatuur verwijdert. Geef de pre- en postcondities van deze methode, en zorg ervoor dat de invariant behouden blijft.
5. Is er een andere (ongewenste) implementatie van removeLast die ook voldoet aan jouw specificatie? Zo ja, hoe zou je die kunnen uitsluiten?

{{% notice style="tip" title="Antwoord"  expanded=false %}}

1. De fout in deze klasse is dat de `average`-methode een deling door nul kan veroorzaken als er geen temperaturen zijn toegevoegd aan de `values`-lijst. Dit gebeurt wanneer `values.size()` gelijk is aan 0.
2. Een nuttige klasse-invariant is dat de lijst `values` nooit leeg mag zijn. Dus we kunnen de invariant formuleren als: `//@ invariant values.size() > 0;`.
3. Om het ontwerp aan te passen zodat de invariant altijd behouden blijft, kunnen we ervoor zorgen dat er altijd minstens één temperatuur in de lijst staat bij het aanmaken van een `TemperatureLog`. Bijvoorbeeld:

```java
class TemperatureLog {
    private final List<Double> values = new ArrayList<>();

    public TemperatureLog(double initialTemperature) {
        values.add(initialTemperature);
    }

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

In dit aangepaste ontwerp is er een constructor die een initiële temperatuur vereist, waardoor de invariant `values.size() > 0` altijd behouden blijft. Hierdoor kunnen we veilig de `average`-methode aanroepen zonder het risico op een deling door nul.

4. De `removeLast`-methode kan als volgt worden toegevoegd:

```java
//@ requires values.size() > 1;
//@ ensures values.size() == \old(values.size()) - 1;
public void removeLast() {
    if (values.size() <= 1) {
        throw new IllegalStateException("Cannot remove last temperature, invariant would be violated.");
    }
    values.remove(values.size() - 1);
}
``` 
In deze `removeLast`-methode hebben we een preconditie toegevoegd die vereist dat er meer dan één temperatuur in de lijst moet zijn voordat we er één kunnen verwijderen. Dit zorgt ervoor dat de invariant `values.size() > 0` behouden blijft, omdat we nooit de laatste temperatuur zullen verwijderen als er maar één is. De postconditie geeft aan dat de grootte van de lijst met precies één is verminderd na het uitvoeren van deze methode.


5. Een ongewenste implementatie van `removeLast` zou kunnen zijn dat we de hele lijst leegmaken en terug opvullen met nullen, in plaats van slechts één element te verwijderen, bijvoorbeeld:

```java
public void removeLast() {
    int oldSize = values.size();
    values.clear();
    for (int i = 0; i < oldSize - 1; i++) {
        values.add(0.0); // voeg nullen toe in plaats van de laatste temperatuur te verwijderen
    }
}
```

Deze implementatie voldoet ook aan de preconditie en postconditie van hierboven, maar is duidelijk niet wat we bedoelen met "removeLast". Om dit soort ongewenste implementaties uit te sluiten, kunnen we de postconditie versterken door expliciet te eisen dat alleen het laatste element wordt verwijderd, en dat alle andere elementen behouden blijven. Bijvoorbeeld:

```java
//@ requires values.size() > 1;
//@ ensures values.size() == \old(values.size()) - 1;
//@ ensures (\forall int i; 0 <= i && i < values.size(); values.get(i) == \old(values.get(i)));
public void removeLast() {
    if (values.size() <= 1) {
        throw new IllegalStateException("Cannot remove last temperature, invariant would be violated.");
    }
    values.remove(values.size() - 1);
}
```
In deze versterkte postconditie geven we aan dat alle elementen behalve het laatste element gelijk moeten blijven aan hun oude waarde, waardoor we duidelijk maken dat alleen het laatste element verwijderd mag worden. Dit sluit de ongewenste implementatie uit.

{{% /notice %}}

### Sterk of zwak

Gegeven volgende methodes met hun specificaties, zeg of de pre- en postcondities (te) sterk of zwak zijn, en motiveer kort waarom.
Verbeter waar mogelijk.

1. 
```java
//@ requires true;
//@ ensures \result >= 0;
public static int abs(int x) {
    return (x >= 0) ? x : -x;
}
```
{{% notice style="tip" title="Antwoord"  expanded=false %}}
De preconditie `requires true;` is zwak, omdat het geen enkele beperking oplegt aan de invoer. Dit is wenselijk in dit geval, omdat er geen zinnige beperkingen zijn.

De postconditie `ensures \result >= 0;` is te zwak, omdat het toestaat dat `\result` een willekeurige niet-negatieve waarde kan zijn, zoals `Integer.MAX_VALUE`, zelfs als `x` negatief is. Een sterkere specificatie zou zijn:

```java
//@ requires true;
//@ ensures \result == (x >= 0 ? x : -x);
```
{{% /notice %}}

2. 
```java
//@ requires x >= 0;
//@ ensures \result == x * x;
public static int square(int x) {
    return x * x;
}
```

{{% notice style="tip" title="Antwoord"  expanded=false %}}
De preconditie `requires x >= 0;` is te sterk, omdat het een duidelijk onnodige beperking oplegt aan de invoer. De methode `square` kan ook correct werken voor negatieve getallen. Een betere (zwakkere) preconditie zou zijn:

```java
//@ requires true;
//@ ensures \result == x * x;
```

De postconditie `ensures \result == x * x;` is voldoende sterk, omdat ze precies specificeert wat de uitvoer moet zijn.
{{% /notice %}}

3. 
```java
//@ requires true;
//@ ensures isSorted(\result);
public static List<Integer> sort(List<Integer> list) {
    var result = new ArrayList<>(list);
    Collections.sort(result);
    return result;
}
```

waarbij `isSorted` een functie is die controleert of de lijst gesorteerd is: 

```java
public static boolean isSorted(List<Integer> list) {
    for (int i = 0; i + 1 < list.size(); i++) {
        if (list.get(i) > list.get(i + 1)) return false;
    }
    return true;
}
```

{{% notice style="tip" title="Antwoord"  expanded=false %}}
De preconditie `requires true;` is iets te zwak. We kunnen een preconditie toevoegen die vereist dat de lijst niet null is, bijvoorbeeld `requires list != null;`, omdat het sorteren van een null-lijst niet zinvol is en waarschijnlijk een exception zou veroorzaken.

De postconditie `ensures isSorted(\result);` is ook te zwak, omdat het enkel oplegt dat de uiteindelijke lijst gesorteerd is, maar niet noodzakelijkerwijs dezelfde elementen bevat als de oorspronkelijke lijst. Met andere woorden, een implementatie die de lijst leegmaakt of vervangt door een andere gesorteerde lijst zou nog steeds aan deze postconditie voldoen, maar dat is niet wat we willen.

Een betere (sterkere) postconditie zou zijn dat de gesorteerde lijst een permutatie is van de oorspronkelijke lijst.
Dat houdt in dat

- de lijst is even lang is gebleven; en
- alle elementen van de oorspronkelijke lijst nog steeds (even vaak) aanwezig zijn in de gesorteerde lijst.


Een mogelijke formulering van deze sterkere postconditie, met behulp van een te definiëren functie `countOccurrences`, is:

```java
//@ requires list != null;
//@ ensures isSorted(\result);
//@ ensures \result.size() == \old(list.size());
//@ ensures (\forall int i; 0 <= i && i < \result.size(); 
//@          countOccurrences(\result, \result.get(i)) == countOccurrences(\old(list), \result.get(i)));
```

{{% /notice %}}

### Specificaties voor backtracking-oefeningen

Schrijf een specificatie (pre- en postcondities) voor de volgende methodes uit de [oefeningen over backtracking](../8-recursie/backtracking_oefeningen.md).
Dat mag informeel (in woorden) in plaats van formeel met JML-annotaties, maar probeer wel zo precies mogelijk te zijn in je formulering.

- Token-segmentatie (waar elke token slechts eenmalig gebruikt mag worden)

{{% notice style="tip" title="Antwoord"  expanded=false %}}
**Pre-conditie:**

- De invoerlijst van tokens is niet null.
- De te segmenteren string is niet null.

**Post-conditie:**

- Als er geen segmentatie mogelijk is, dan is het resultaat null.
- Als er een segmentatie mogelijk is, dan is het resultaat een lijst die voldoet aan volgende voorwaarden:
  - alle elementen uit de lijst zijn tokens uit de invoerlijst
  - het aantal keer dan een token voorkomt in het resultaat is niet groter dan het aantal keer dat het voorkomt in de invoerlijst
  - als we de tokens in het resultaat achter elkaar plakken, krijgen we precies de te segmenteren string terug.

{{% /notice %}}

- Uurrooster-planner

{{% notice style="tip" title="Antwoord"  expanded=false %}}
**Pre-conditie:**

- De lijst van vakken is niet null.
- De planningparameters zijn niet null.
- Het maximaal aantal slots is een positief getal.
- Het maximaal aantal vakken per slot is een positief getal.
- Elk vak heeft een niet-null verzameling van personen.

**Post-conditie:**

- Als er geen rooster mogelijk is, dan is het resultaat null.
- Als er een rooster mogelijk is, dan is het resultaat een Planning-object dat voldoet aan volgende voorwaarden:
  - elk vak uit de invoerlijst is precies één keer ingeroosterd in een slot
  - elk gebruikt slot is kleiner dan het maximaal aantal slots
  - er zijn nooit meer vakken in hetzelfde slot dan het maximaal aantal vakken per slot
  - er is geen persoon die in twee verschillende vakken is ingeroosterd in hetzelfde slot
  - er bestaat geen planning waarbij het hoogst gebruikte slotnummer lager is dan in het resultaat
{{% /notice %}}

- Traveling Sales Person

{{% notice style="tip" title="Antwoord"  expanded=false %}}
**Pre-conditie:**

- De startlocatie is niet null.
- De lijst van andere locaties is niet null.
- De startlocatie mag niet voorkomen in de lijst van andere locaties (omdat we elke locatie precies één keer moeten bezoeken, en we al beginnen bij de startlocatie).

**Post-conditie:**

- Als er geen geldige route mogelijk is, dan is het resultaat null.
- Als er een geldige route mogelijk is, dan is het resultaat een Route-object dat voldoet aan volgende voorwaarden:
  - de route begint en eindigt bij de startlocatie
  - elke locatie uit de lijst van andere locaties komt precies één keer voor in de route
  - de totale afstand van de route is kleiner dan of gelijk aan de afstand van elke andere mogelijke route die aan bovenstaande voorwaarden voldoet.

{{% /notice %}}
