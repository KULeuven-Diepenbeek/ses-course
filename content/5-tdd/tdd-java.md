---
title: 'TDD in Java'
weight: 2
author: Wouter Groeneveld en Arne Duyver
draft: false
---

### Een TDD Scenario

Stel dat een programma een notie van periodes nodig heeft, waarvan elke periode een start- en einddatum heeft, die al dan niet ingevuld kunnen zijn. Een contract bijvoorbeeld geldt voor een periode van _bepaalde duur_, waarvan beide data ingevuld zijn, of voor gelukkige werknemers voor een periode van _onbepaalde duur_, waarvan de einddatum ontbreekt:

<div class="devselect">

```java
public class Contract {
    private Periode periode;
}

public class Periode {
    private Date startDatum;
    private Date eindDatum; 
}
```

</div>

We wensen aan de `Periode` klasse een methode toe te voegen om te controleren of periodes overlappen, zodat de volgende statement mogelijk is: `periode1.overlaptMet(periode2)`.

#### 1. Schrijf Falende Testen
Voordat de methode wordt opgevuld met een implementatie dienen we na te denken over de mogelijke gevallen van een periode. Wat kan overlappen met wat? Wanneer geeft de methode `true` terug, en wanneer `false`? Wat met lege waardes?

- Het standaard geval: beide periodes hebben start- en einddatum ingevuld, en de periodes overlappen. 

<div class="devselect">

```java
@Test
public void gegevenBeidePeriodesDatumIngevuld_wanneeroverlapt_danIsTrue() {
    var jandec19 = new Periode(new Date(2019, 01, 01), 
            new Date(2019, 12, 31));
    var maartnov19 = new Periode(new Date(2019, 03, 01),
            new Date(2019, 11, 31));

    assert(jandec19.overlaptMet(maartnov19) == true);
}
```

</div>

- Beide periodes hebben start- en einddatum ingevuld, en periodes overlappen niet. 

<div class="devselect">

```java
@Test
public void gegevenNietOverlappendePeriodes_wanneerOverlaptMet_danIsFalse() {
    var jandec19 = new Periode(new Date(2019, 01, 01), 
            new Date(2019, 12, 31));
    var maartnov20 = new Periode(new Date(2020, 03, 01),
            new Date(2020, 11, 31));

    assert(jandec19.overlaptMet(maartnov20) == false);
}
```
</div>

Merk op dat de namen van de testen zeer descriptief zijn. Op die manier wordt in één opslag duidelijk waar er problemen opduiken in je code.

- ... Er zijn nog tal van mogelijkheden, waarvan voornamelijk de extreme gevallen belangrijk zijn om **de kans op bugs te minimaliseren**. Immers, gebruikers van onze `Periode` klasse kunnen onbewust `null` mee doorgeven, waardoor de methode onverwachte waardes teruggeeft. 

De testen compileren niet, omdat de methode `overlaptMet()` nog niet bestaat. Voordat we overschakelen naar het schrijven van de implementatie willen we eerst de testen zien ROOD kleuren, waarbij wel de bestaande code nog compileert:

<div class="devselect">

```java
public class Periode {
    private Date startDatum;
    private Date eindDatum; 
    public boolean overlaptMet(Periode anderePeriode) {
        throw new UnsupportedOperationException();
    }
}
```

</div>

De aanwezigheid van het skelet van de methode zorgt er voor dat de testen compileren. De inhoud, die een `UnsupportedOperationException` gooit, dient nog aangevuld te worden in stap 2. Op dit punt falen alle testen (met hopelijk als oorzaak de voorgaande exception).

#### 2. Schrijf Implementatie

Pas nadat er minstens 4 verschillende testen werden voorzien (standaard gevallen, edge cases, null cases, ...), kan met een gerust hart aan de implementatie worden gewerkt:

<div class="devselect">

```java
public boolean overlaptMet(Periode anderePeriode) {
    return startDatum.after(anderePeriode.startDatum) && 
        eindDatum.before(anderePeriode.eindDatum);
}
```

</div>

#### 3. Voer Testen uit

Deze eerste aanzet verandert de deprimerende rode kleur van minstens één test naar GROEN. Echter, lang niet alle testen zijn in orde. Voer de testen uit na elke wijziging in implementatie totdat alles in orde is. Het is mogelijk om terug naar stap 1 te gaan en extra test gevallen toe te voegen. 

#### 4. Pas code aan (en herbegin)

De cyclus is compleet: red, green, refactor, red, green, refactor, ... 

Wat is **'refactoring'**?

> Structuur veranderen, zonder de inhoud te wijzigen. 

Als de `overlaptMet()` methode veel conditionele checks bevat is de kans groot dat bij elke groene test de inhoud stelselmatig ingewikkelder wordt, door bijvoorbeeld het veelvuldig gebruik van `if` statements. In dat geval is het verbeteren van de code, zonder de functionaliteit te wijzigen, een refactor stap. Na elke refactor stap verifiëer je de wijziging door middel van de testen. 

Voel jij je veilig genoeg om grote wijzigingen door te voeren zonder te kunnen vertrouwen op een vangnet van testen? Wij als docenten alvast niet.

## Naamgeving van testen en projectstructuur

Om testen op een correcte manier uit te voeren wordt aan een bepaalde structuur vastgehouden. 

{{% notice note %}}
We maken gebruik van CamelCase en snake_case om alle testen de vorm te geven van **gegevenDit_wanneerDezeMethodeEropToegepastWordt_danMoetDitDeUitkomstZijn**. 
{{% /notice %}}

## Unit Tests

### Wat is Unit Testing

Unit testen zijn stukjes code die productie code verifiëren op verschillende niveau's. Het resultaat van een test is GROEN (geslaagd) of ROOD (gefaald met een bepaalde reden). Een collectie van testen geeft ontwikkelaars het zelfvertrouwen om stukken van de applicatie te wijzigen met de zekerheid dat de aanwezige testen rapporteren wat nog werkt, en wat niet. Het uitvoeren van deze testen gebeurt meestal in een IDE zoals IntelliJ voor Java, of Visual Studio voor C#, zoals deze screenshot:

![](/img/nunit.jpg "De visuele output van NUnit in C#")

Elke validatieregel wordt apart opgelijst in één test. Als de `validate()` methode 4 regels test, zijn er minstens 4 testen geimplementeerd. In de praktijk is dat meestal meer omdat **edge cases** - uitzonderingsgevallen zoals `null` checks - ook aanzien worden als een apart testgeval. 

#### Eigenschappen van een goede test

Elke unit test is **F.I.R.S.T.**:

1. **Fast**. Elk nieuw stukje functionaliteit vereist nieuwe testen, waarbij de bestaande testen ook worden uitgevoerd. In de praktijk zijn er duizenden testen die per compile worden overlopen. Als elke test één seconde duurt, wordt dit wel erg lang wachten...  
2. **Isolated.** Elke test bevat zijn eigen test scenario dat géén invloed heeft op een andere test. Vermijd ten allen tijden het gebruik van het keyword `static`, en kuis tijdelijk aangemaakte data op, om te vermijden dat andere testen worden beïnvloed.
3. **Repeatable**. Elke test dient hetzelfde resultaat te tonen, of die nu éénmalig wordt uitgevoerd, of honderden keren achter elkaar. State kan hier typisch roet in het eten gooien. 
4. **Self-Validating**. Geen manuele inspectie is vereist om te controleren wat de status van de test is. Een falende foutboodschap is een duidelijke foutboodschap.
5. **Thorough**. Testen moeten alle scenarios dekken: denk terug aan _edge cases_, randgevallen, het gebruik van `null`, het opvangen van mogelijke `Exception`s, ...

### Het Raamwerk van een test

Bij het aanmaken van het project met Gradle, heeft Gradle je al een heel stuk geholpen om het testraamwerk op te stellen. Testen over een bepaalde klasse bundel je namelijk in een file onder de `test`-directory. **Zorg er ook voor dat de testfile zich in dezelfde package** onder de testdiretory bevindt. De conventie is dat we de testfile dezelfde naam geven als de klasse die we willen testen met `Test` achter. In principe is dit ook gewoon een javaklasse die we op een speciale manier gaan gebruiken. Bij het aanmaken van je project voorziet Gradle zelfs al een test. De structuur van de `app/src`-directory van je project ziet er dus als volgt uit:
```
./app/src
├── main
│   ├── java
│   │   └── be
│   │       └── ses
│   │           └── App.java
│   └── resources
└── test
    ├── java
    │   └── be
    │       └── ses
    │           └── AppTest.java
    └── resources
```

#### Test Libraries bestaande uit twee componenten

Een test framework, zoals JUnit voor Java, MSUnit/NUnit voor C#, of Jasmine voor Javascript, bevat twee delen: 

##### 1. Het Test Harnas

Een 'harnas' is het concept waar alle testen aan worden opgehangen. Het harnas identificeert en verzamelt testen, en het harnas stelt het mogelijk om bepaalde of alle testen uit te voeren. De ingebouwde Test UI in VSCode fungeert hier als visueel harnas. Elke test methode, een `public void` methode geannoteerd met `@Test`, wordt herkent als mogelijke test.

Gradle en het JUnit harnas verzamelen data van testen in de vorm van HTML rapporten.

_Hiervoor dient dus de dependency `testRuntimeOnly 'org.junit.platform:junit-platform-launcher'` in onze `gradle.build`-file._

##### 2. Het Assertion Framework

Naast het harnas, dat zorgt voor het uitvoeren van testen, hebben we ook een _verificatie framework_ nodig, dat fouten genereert wanneer nodig, om te bepalen of een test al dan niet geslaagd is. Dit gebeurt typisch met **assertions**, die vereisten dat een argument een bepaalde waarde heeft. Is dit niet het geval, wordt er een `AssertionError` exception gegooid, die door het harnas herkent wordt, met als resultaat een falende test. 

Assertions zijn er in alle kleuren en gewichten, waarbij in de oefeningen de statische methode `assertThat()` wordt gebruikt, die leeft in `org.assertj.core.api.Assertions`. AssertJ is een plugin library die ons in staat stelt om een _fluent API_ te gebruiken in plaats van moeilijk leesbare assertions:

<div class="devselect">

```java
import static org.junit.jupiter.api.Assertions.*;
@Test
public void testWithDefaultAssertions() {
    var result = doStuff();
    AssertEquals(result, 3);    // arg1: expected, arg2: actual
}

import static org.assertj.core.api.Assertions.*;
@Test
public void testWithAssertJMatchers() {
    var result = doStuff();
    assertThat(result).isEqualTo(3);
}
```

</div>

Het tweede voorbeeld leest als een vloeiende zin, terwijl de eerste `AssertEquals()` vereist dat als eerste argument de expected value wordt meegegeven - dit is vaak het omgekeerde van wat wij verwachten! 

[AssertJ core API Documentation](https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html)

_Je kan simpelweg de dependency `testCompile("org.assertj:assertj-core:3.11.1")` in de `gradle.build`-file toevoegen. En de import van `import static org.junit.jupiter.api.Assertions.*;` in de test file veranderen naar `import static org.assertj.core.api.Assertions.*;`_

Een populair alternatief voor AssertJ is bijvoorbeeld [Hamcrest](http://hamcrest.org/JavaHamcrest/javadoc/). De keuze is aan jou: alle frameworks bieden ongeveer dezelfde fluent API aan met ongeveer dezelfde features. 

##### Messages meegeven aan Testresultaten
Je kan ook extra messages meegeven aan testresultaten die afhankelijk kunnen zijn van het resultaat, bekijk hiervoor de documentatie van [AssertJ](https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html).

##### Testen op Exceptions

{{% notice info %}}
Je kan testen met AssertJ op exceptions op de volgende manier:
{{% /notice %}}
```java
@Test
public void myTest(){
    // when
    Throwable thrown = catchThrowable(() -> {
        // ...
    });

    // then
    assertThat(thrown)
    .isInstanceOf(Exception.class)
    .hasMessageContaining("/ by zero");
}

@Test
public void myTest(){
    assertThatExceptionOfType(Exception.class)
        .isThrownBy(() -> {
            // ...
        }).withMessageContaining("Substring in message");
}

```

#### Arrange, Act, Assert

De body van een test bestaat typisch uit drie delen:

<div class="devselect">

```java
@Test
public void givenArranged_whenActing_thenSomeExpectedResult() {
    // 1. Arrange 
    var instance = new ClassToTest(arg1, arg2);

    // 2. Act
    var result = instance.callStuff();

    // 3. Assert
    assertThat(result).isEqualTo(true);
}
```

</div>

1. **Arrange**. Het klaarzetten van data, nodig om te testen, zoals een instantie van een klasse die wordt getest, met nodige parameters/DB waardes/...
2. **Act**. Het uitvoeren van de methode die wordt getest, en opvangen van het resultaat.
3. **Assert**. Het verifiëren van het resultaat van de methode.

#### Setup, Execute, Teardown

Wanneer de **Arrange** stap dezelfde is voor een serie van testen, kunnen we dit veralgemenen naar een `@Before` methode, die voor het uitvoeren van bepaalde of alle testen wordt uitgevoerd. Op dezelfde manier kan data worden opgekuist na elke test met een `@After` methode - dit noemt men de _teardown_ stap. 

JUnit 4 en JUnit 5 verschillen hierin op niveau van gebruik. Vanaf JUnit 5 werkt men met `@BeforeEach`/`@BeforeAll`. Raadpleeg [de documentatie](https://junit.org/junit5/docs/current/user-guide/) voor meer informatie over het verschil tussen each/all en tussen v4/v5.

### Demo: Calculator app unit testen

1. Om testen op de correcte manier uit te kunnen voeren gaan we starten met de juiste dependencies in te stellen in onze `build.gradle` file:
```groovy
dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation libs.junit.jupiter
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

    testImplementation "org.assertj:assertj-core:3.11.1"

    // This dependency is used by the application.
    implementation libs.guava
}
```

2. Vervolgens maken we een Calculator klasse aan in ons gradle project in het package `be.ses`.
```java
package be.ses;

public class Calculator {

}
```

3. Omdat we goede developers zijn maken we ook meteen een `CalculatorTest` klasse aan in de overeenkomstige package in de `app/src/test` directory. En voegen hier al de nodige imports aan toe. _hier zullen al onze testen in geschreven worden als  `public void` methodes_:
```java
package be.ses;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CalculatorTest {
    
}
```

We willen aan onze calculator een `divide`-methode toevoegen die 2 parameters meekrijgt `teller` en `noemer`.

4. We gaan volgens de correcte principes dus EERST testen schrijven die de onze method gaan testen voordat we de implementatie ervan gaan uitschrijven. (En aangezien het kan zijn dat er door 0 gedeeld wordt, gaan we dit ook testen):
```java
package be.ses;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class CalculatorTest {

  @Test
  public void gegevenTeller2Noemer1_wanneerDivide_danResult2() {
    // 1. Arrange
    Calculator calculator = new Calculator();

    // 2. Act
    Float result = calculator.divide(2, 1);
    System.out.println(result);

    // 3. Assert
    assertThat(result).isEqualTo(2);
  }

  @Test
  public void gegevenTeller2Noemer4_wanneerDivide_danResult0point5() {
    // 1. Arrange
    Calculator calculator = new Calculator();

    // 2. Act
    Float result = calculator.divide(2, 4);
    System.out.println(result);

    // 3. Assert
    assertThat(result).isEqualTo(0.5f);
  }

  @Test
  public void gegevenTellerXNoemer0_wanneerDivide_danDivideByZeroException() {
    // when
    Throwable thrown = catchThrowable(() -> {
      // 1. Arrange
      Calculator calculator = new Calculator();

      // 2. Act
      Float result = calculator.divide(2, 0);
    });

    // 3. Assert
    assertThat(thrown)
        .isInstanceOf(ArithmeticException.class)
        .hasMessageContaining("/ by zero");
  }
}
```

5. Nu kunnen we aan onze implementatie werken en proberen alle testen te laten slagen:
```java
package be.ses;

public class Calculator {

  public Calculator() {

  }

  public float divide(float teller, float noemer) {
    if (noemer == 0) {
      throw new ArithmeticException("/ by zero");
    }
    return teller / noemer;
  }
}
```

### Oefening
- Breid je `Calculator` klasse uit om ook `add`, `subtract` en `multiply` te doen. En schrijf natuurlijk eerst enkel tests.
- Voeg eigen messages toe aan je testen om nog beter te kunnen kijken wat eventueel misloopt.

## Integration testen

##### Test Doubles
Stel dat we nu een andere klasse hebben `Doubler` die een methode heeft `doubleCalculator`. Die methode neemt 3 parameters: `operation`,`x`,`y` en voert dus de gekozen operatie uit met de `Calculator` klasse en verdubbeld gewoon het resultaat.  

Hoe testen we de `doubleCalculator()` methode, zonder de effectieve implementatie van `Calculator` te moeten gebruiken? Want testen moeten geïsoleerd zijn. 

Door middel van _test doubles_.

![](/img/testdouble.jpg "I'll Be Back.")

Zoals Arnie in zijn films bij gevaarlijke scenes een stuntman lookalike gebruikt, zo gaan wij in onze code een `Calculator` lookalike gebruiken, zodat de `Doubler`-klasse dénkt dat hij `Calculator`-methoden aanroept, terwijl dit in werkelijkheid niet zo is. Daarvoor gaan we **Mocks** gebruiken. (Je kan ook een interface `CalculatorInterface` zodat je overal waar je `Calculator` wil gebruiken ook een eigen `CalculatorMock`-klasse kan gebruiken met dezelfde methodes maar waar je een aantal testscenarios gewoon hardcode, maar dit gaan we hier niet voordoen.)

### Mocking

Meer info rond faking vs. mocking vindt je [hier](https://www.educative.io/answers/what-is-faking-vs-mocking-vs-stubbing):
<blockquote>Fakes are objects that have working implementations. On the other hand, mocks are objects that have predefined behavior. Lastly, stubs are objects that return predefined values. When choosing a test double, we should use the simplest test double to get the job done.
</blockquote>

Mockito is verreweg het meest populaire Unit Test Framework dat bovenop JUnit wordt gebruikt om heel snel Test Doubles en integratietesten op te bouwen.

![Mockito logo](/img/teaching/ses/mockito.png)

Op [https://site.mockito.org](https://site.mockito.org) kan je lezen **hoe** je het framework moet gebruiken. (Volledige [javadoc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)) 

We gaan nu Mockito gebruiken om in onze testen een PowerMock te doen wat wil zeggen dat als er in een test `new` aangeroepen wordt voor een klasse die wij met Mockito kiezen dan gaan we die nu met onze testdouble klasse verwisselen. Hiervoor heb je enkele dependencies en nodig:
<!-- ```groovy
testImplementation 'org.powermock:powermock-api-mockito2:2.0.9'
testImplementation 'org.powermock:powermock-module-junit4:2.0.9'
testImplementation 'org.mockito:mockito-core:3.12.4'
```

Hieronder vind je een klein voorbeeld in verband met het voorbeeld dat hierboven werd aangehaald:

We voegen eerst Mockito toe aan onze dependencies met `testImplementation 'org.mockito:mockito-core:2.1.0'`.

De `Doubler` klasse ziet er als volgt uit:
```

``` -->


## End-to-end testen

_Zie [TDD-pagina](/5-tdd/_index.md#3-end-to-end-testing-rood)_

## Opgaven
### Opgave 1

De Artisanale Bakkers Associatie vertrouwt op uw technische bekwaamheid om hun probleem op te lossen. 
Er wordt veel Hasseltse [Speculaas](https://en.wikipedia.org/wiki/Speculaas) gebakken, maar niemand weet 
precies wat de **beste** Speculaas is. Schrijf een methode die _speculaas_ beoordeelt op basis van de ingrediënten. 
De methode, in de klasse `Speculaas`, zou er zo uit moeten zien:

<div class="devselect">

```java
    public int beoordeel() {
        // TODO ...
    }
```

</div>

De functie geeft een nummer terug - hoe hoger dit nummer, hoe beter de beoordeling en hoe gelukkiger de bakker. Een speculaas kan de volgende ingrediënten bevatten: kruiden, boter, suiker, eieren, melk, honing, bloem, zout. Elke eigenschap is een nummer dat de hoeveelheid in gram uitdrukt.

Het principe is simpel: hoe meer ingrediënten, hoe beter de beoordeling.

Kijk naar een voorbeeld test hoe de methodes te hanteren. Er zijn al enkele testen voorzien. Die kan je uitvoeren met IntelliJ door op het groen pijltje te drukken, of met Gralde: `./gradlew test`. Dit genereert een **test rapport** HTML bestand in de `build/test` map.

We zijn dus geïnteresseerd in **edge cases**. Probeer alle mogelijkheden te controleren. Denk bij het testen aan de volgende zaken:

- Hoe zit het met een industriële speculaas, zonder kruiden of boter? 
- Wat doet de functie beoordeel als het argument `null` is?
- Wat als een speculaas wordt meegegeven zonder ingrediënten?

### Opgave 2

Clone of fork het <i class='fab fa-github'></i> GitHub project https://github.com/KULeuven-Diepenbeek/ses-tdd-exercise-2-template


#### A. Mislukte login pogingen

Er is een foutje geslopen in de login module, waardoor `Abigail` altijd kan inloggen, maar `jos` soms wel en soms niet. De senior programmeur in ons team heeft de bug geïdentificeerd en beweert dat het in een stukje _oude code_ zit, 
maar hij heeft geen tijd om dit op te lossen. Nu is het aan jou. De `logins.json` file bevat alle geldige login namen die mogen inloggen. Er kan kunnen geen twee gebruikers met dezelfde voornaam zijn.
(Andere namen die moeten kunnen inloggen zijn "James", "Emma", "Isabella" ...)
(Andere namen die niet mogen kunnen inloggen zijn "Arne", "Kris", "Markske" ...)

<div class="devselect">

```java

public class LoginChecker {
    public static boolean control(String username) {
        ArrayList<String> loginList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("./logins.json"));
            JsonArray data = gson.fromJson(reader, JsonArray.class);
            for (JsonElement jo : data) {
                String login = gson.fromJson(jo, String.class);
                loginList.add(login);
            }
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }

        boolean found = false;
        for (String naam : loginList) {
            if (naam.equals(username)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
```

</div>

Deze methode geeft `true` terug als Abigail probeert in te loggen, en `false` als Jos probeert in te loggen. Hoe komt dit? Schrijf éérst een falende test!

#### B. URL Verificatie fouten

Een tweede bug wordt gemeld: URL verificatie features werken plots niet meer. Deze methode faalt steeds, ook al zijn er reeds unit testen voorzien. Het probleem is dat **HTTPS** URLs met een SSL certificaat niet werken. Je onderzocht de URL verificatie code en vond de volgende verdachte regels:

<div class="devselect">

```java
import java.util.regex.Pattern;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public static boolean verifyUrl(String url) {
    Pattern pattern = Pattern.compile("http:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)", CASE_INSENSITIVE);
    return pattern.matcher(url).matches();
}
```

</div>

De code blijkt reeds **unit testen** te hebben, dus schrijf éérst een falende test (in `VerifierTests`).

### Opgave 3

Werk een volledige implementatie van `Periode.overlaptMet()` uit, zoals hierboven uitgelegd. 

### Opgave 4

{{% notice info %}}
Dit is een vervolgopgave van de code van **Opgave 1**. Werk verder op dat bestaand project, in diezelfde repository!
{{% /notice %}}

Een verkoopster werkt in een (goede) speculaasfabriek. De verkoopster wilt graag 2 EUR aanrekenen per speculaas die de fabriek produceert. 
Echter, als de klant meer dan 5 stuks verkoopt, mag er een korting van 10% worden aangerekend. In dit voorbeeld gaan we ervan uit dat een fabriek een willekeurig aantal speculaas per dag maakt en dat de klant steeds alle speculazen koopt. De verkoop gebeurt in de Verkoopsterklasse en het bakken van de speculazen gebeurt in de SpeculaasFabriek. Als we nu willen testen of onze `verkoop` methode uit de `Verkoopster`-klasse werkt, dan willen we dit **isolated** doen. We willen dus de onzekerheid van de Fabriek weghalen door specifieke gevallen aan te halen. Dit kan echter niet via de standaard `SpeculaasFabriek`. Daarom gaan we een **test double** gebruiken. Hiervoor gaan we deze keer een **mock** gebruiken zoals verder duidelijk wordt.

<div class="devselect">

```java
    public double verkoop() {
        var gebakken = speculaasFabriek.bak();
        // TODO ...
    }
```

</div>

Je ziet aan bovenstaande code dat de speculaasfabriek instantie wordt gebruikt. We hebben dus eigenlijk **geen controle** op de hoeveelheid speculaas die deze fabriek produceert.

### Unit testen

Hoe kunnen we dan toch nog testen wat we willen testen? Mogelijke scenario's:

1. De fabriek produceert niets. De klant betaalt niets.
2. De fabriek produceert minder dan 5 speculaasjes. De klant betaalt per stuk, 2 EUR.
3. De fabriek produceert meer dan 5 stuks. De klant krijgt 10% korting op zijn totaal.

### Hoe controleer ik het gedrag van de fabriek?



