---
title: 3.3 TDD opgave feedback
draft: true
---


Deze opgave feedback werd gegeven in academiejaar 2021-2022 voor opgave 1, 4, en 5. We laten dit hier staan voor jullie ter referentie zodat het kan helpen om laatste controles voor het indienen van de opgaves tot een goed einde te brengen. 


## Algemene feedback

Bij het indienen van een opdracht worden de volgende zaken altijd eerst nagekeken:

1. Compileert het programma? 
2. Zijn alle testen groen?

Aangezien het vaak over veel opgaves gaat wordt dit gescript met behulp van [Gradle](/dependency-management/gradle/). Na het downloaden van de opdracht wordt vaak het commando `./gradlew clean test` uitgevoerd, die ook impliciet build. Zorg er dan ook steeds voor dat je project correct build vóórdat je indient! Test dit zelf met het commando in je opdrachtprompt, los van IntelliJ. Waarom? Omdat individuele testen uitvoeren in IntelliJ niet betekent dat je hele project compileert. 

Een niet-compilerend ingediend project komt over als erg slordig werk. Het zou kunnen dat er discrepanties zijn tussen JDK versies, waar ook rekening mee gehouden wordt, maar in de praktijk merken we vaak dat nalatigheden zoals het vergeten van een constructor veel punten kost. 

Zorg ervoor dat je **Gradle buildfile** in orde is. Zonder een syntactisch correcte `build.gradle` file kan het project niet builden met het `gradlew` commando. Dat komt opnieuw over als erg slordig, zeker omdat er in dit vak nadruk gelegd wordt op dependency management. 

Zorg ervoor dat **testen groen zijn**. Verwijder niet-relevante testen die misschien zijn opgesteld om te experimenteren. Controleer dit simpelweg met het gradle commando, los van het comfort van de IDE!

Zorg ervoor dat je **code leesbaar is**. Dit is het vak Software Engineering, waarbij we constant de nadruk leggen op zaken als _clean code_, _TDD_, en _software design_. Als je programma doet wat het moet doen, betekent dit nog niet dat het volgens deze principes als goede code aanschouwd wordt. 

## [3.1 Opgave 4](/tdd#opgave-4): integratie testen

Hieronder volgen een aantal veel voorkomende fouten, die eenvoudig te vermijden zijn als je aandacht schenkt aan je unit test designs.

### Fout 1: assertions verkeerd gebruiken

Bijvoorbeeld:

```java
@Test
public void someTestMethod() {
    int result = someCalculation(2, 5);
    Assert.assertTrue(result == 5 * 20);
}
```

Als deze test faalt, krijg je de boodschap: `expected true, but was false`. _That's it._ Heb je daar iets aan, buiten dat de test faalt? Nee: je zal breakpoints moeten plaatsen om te zien wat er precies mis ging. `assertTrue()` is hier de verkeerde keuze. Gebruik nooit `==` om een expressie om te vormen naar een boolean statement. Laat dit aan de juiste assertion method over:

```java
@Test
public void someTestMethod() {
    int result = someCalculation(2, 5);
    Assert.assertEquals(100, result);
}
```

Nu krijg je bijvoorbeeld de boodschap `expected 100, but was 45`. Dat is veel duidelijker. Vermijd ook het a-la-minute berekenen van de expected outcome (`5 * 20` is minder expliciet dan `100`). Gebruik maken van de betere leesbaarheid van Hamcrest is nog beter: `assertThat(result, is(100))`.

### Fout 2: expected en actual in assertions omkeren

In veel xUnit test frameworks, inclusief JUnit, verwachten assertion methodes **eerst de expected** value, daarna pas de actual. 

Dus niet `Assert.assertEquals(result, 100);` maar `Assert.assertEquals(100, result);`. De test geeft misschien hetzelfde resultaat (rood/groen), maar de boodschap en interpretatie is een wereld van verschil. 

Opnieuw, **leesbaarheid** primeert hier, en Hamcrest helpt en keert dit wel correct om. 

### Fout 3: mocks als fields definiëren

Een Mockito mock (of een zelfgeschreven Test Double) mag je **nooit herbruiken**. Er is state aan verbonden, die bij voor elke test uniek moet zijn. Dus niet:

```java
public class MyTest {
    private MyInterface mockObj = Mockito.mock(MyInterface.class);

    @Test
    public void test1() {
        Mockito.when(mockObj.doStuff()).thenReturn(10);
        // ...
    }
    @Test
    public void test2() {
        Mockito.when(mockObj.doMoreStuff()).thenReturn(true);
        // ...
    }
}
```

Het probleem hier is dat na het uitvoeren van `test1()`, je `mockObj` nog steeds `10` gaat teruggeven bij het oproepen van `doStuff()`, omdat de mock in `test2` hetzelfde object is! Dit geeft heel vreemde side effects en kan tot erg buggy testen leiden waarvan de bugs moeilijk te achterhalen zijn. 

Om dit te vermijden maak je elke mock aan in de test zelf. Ook al heb je voor elke test dezelfde soort mock nodig. Dus:

```java
public class MyTest {
    @Test
    public void test1() {
        MyInterface mockObj = Mockito.mock(MyInterface.class);
        Mockito.when(mockObj.doStuff()).thenReturn(10);
        // ...
    }
    @Test
    public void test2() {
        MyInterface mockObj = Mockito.mock(MyInterface.class);
        Mockito.when(mockObj.doMoreStuff()).thenReturn(true);
        // ...
    }
}
```

Dit lijkt niet goed te zijn omdat we code regels dupliceren, maar hiermee encapsuleren we `mockObj`: deze variabele is nu een lokale variabele en geen field, en zal dus stoppen met bestaan na het uitvoeren van de test. 

Vergeet niet de **F.I.R.S.T.** principes van unit testen, waar we fouten tegen maken als we dit niet doen. 

### Fout 4: onduidelijke test namen

Dit spreekt voor zich. In bovenstaande voorbeelden staat `test1` en `test2`, maar niemand weet precies wat dit betekent, en welke condities ze aftesten. Dit is uiteraard slechts een voorbeeld, maar helaas dienen studenten soms ook zulke testnamen in. 

Als een vuistregel is het een goed idee om in testnamen het volgende te verwerken:

- De methode die je wenst te testen
- De condities van de test
- Het effect

Bijvoorbeeld: `encrypt_withEmptyString_returnsEmptyString`, of `encrypt_withSpaces_ignoresSpecialChars`, en niet `encryptTest1` of `encryptDoesStuff`. Test methodes mogen lang zijn: zolang opnieuw **leesbaarheid** primeert is alles oké, inclusief snake casing (gebruik maken van `_`) of camelCasing. Vergeet niet dat in Kotlin een hele zin mag. Schrijf bijvoorbeeld test `fun`s in de stijl van `Given blah When this Then that`. Zie het [unit testen hoofdstuk](/tdd/). 

