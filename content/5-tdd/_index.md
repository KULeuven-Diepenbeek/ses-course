---
title: '5. Test Driven Development'
weight: 50
author: Wouter Groeneveld
draft: true
---

> <i class="fa fa-question-circle" aria-hidden="true"></i>
 Wat is de beste manier om het aantal bugs in code te reduceren?


<!-- TODO op de juist plaats zetten
### Gradle en JUnit integratie

JUnit 5 splitst de test library op in een aantal submodules, waarvan er twee belangrijke zijn die we nodig hebben om te testen:

1. `junit-jupiter-api` - nodig om testen te SCHRIJVEN (de API waar `@BeforeEach` e.a. in zitten)
2. `junit-jupiter-engine` - nodig om testen UIT TE VOEREN (cmdline interface)

Aangezien Gradle verschillende test bibliotheken ondersteund, zoals ook TestNG, dient men in de Gradle build file ondersteuning voor elk framework te activeren. Dit is _enkel nodig bij cmdline uitvoeren van de testen_. Als je beslist om enkel binnen IntelliJ testen uit te voeren, verzorgt IntelliJ dit zelf, en is de jupiter-engine ook niet nodig. 

<pre>
test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

dependencies {
    // for WRITING tests, this will suffice:
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    // for RUNNING tests (cmdline, without IntelliJ), this is also needed:
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
}
</pre>

Optionele test libraries zoals Hamcrest en Selenium/WebDriver kunnen daarna ook worden toegevoegd onder de `testImplementation` groep.

{{% notice note %}}
Merk op dat dit betekent dat dependencies in de `testRuntimeOnly` groep eigenlijk _runtime dependencies_ zijn: deze worden niet gebruikt om mee te builden. Denk aan het verschil tussen statisch en dynamisch linken in C. 
{{% /notice %}}

 -->
## Test-Driven Development

TDD (Test-Driven Development) is een hulpmiddel bij softwareontwikkeling om minder fouten te maken en sneller fouten te vinden, door éérst een test te schrijven en dan pas de implementatie. Die (unit) test zal dus eerst **falen** (ROOD), want er is nog helemaal geen code, en na de correcte implementatie uiteindelijk **slagen** (GROEN). 

{{<mermaid>}}
graph LR;
    T{"Write FAILING<br/> test"}
    D{"Write<br/> IMPLEMENTATION"}
    C{"Run test<br/> SUCCEEDS"}
    S["Start Hier"]
    S --> T
    T --> D
    D --> C
    C --> T
{{< /mermaid >}}

Testen worden opgenomen in een build omgeving, waardoor alle testen automatisch worden gecontroleerd bij bijvoorbeeld het compileren, starten, of packagen van de applicatie. Op deze manier krijgt men **onmiddellijk feedback** van modules die door bepaalde wijzigingen niet meer werken zoals beschreven in de test. 

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

{{% notice note %}}
Als je meer info wil hebben over de werking van het Date object, dan kan je met `ctrl + q` de javadoc laten weergeven.
{{% /notice %}}

#### 1. Schrijf Falende Testen

Bij het aanmaken van het project in IntelliJ, heeft de IDE je al een heel stuk geholpen om het testraamwerk op te stellen. Testen over een bepaalde klasse bundel je namelijk in een file onder de `test`-directory. Zorg er ook voor dat de testfile zich in dezelfde package onder de testdiretory bevindt. De conventie is dat we de testfile dezelfde naam geven als de klasse die we willen testen met `Test` achter. In principe is dit ook gewoon een javaklasse die we op een speciale manier gaan gebruiken.

<img src="/img/tdd/test_file_structuur.png" alt="test file structuur" style="max-height: 23em;"/>

Voordat de methode wordt opgevuld met een implementatie dienen we na te denken over de mogelijke gevallen van een periode. Wat kan overlappen met wat? Wanneer geeft de methode `true` terug, en wanneer `false`? Wat met lege waardes?

- Het standaard geval: beide periodes hebben start- en einddatum ingevuld, en de periodes overlappen. 

<div class="devselect">

```java
@Test
public void GegevenBeidePeriodesDatumIngevuld_Wanneeroverlapt_DanIsTrue() {
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
public void gegevenNietOverlappendePeriodes_WanneerOverlaptMet_DanIsFalse() {
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

{{% notice note %}}
We maken gebruik van CamelCase and snake_case om alle testen de vorm te geven van **gegevenDit_wanneerDezeMethodeEropToegepastWordt_danMoetDitDeUitkomstZijn**. 
{{% /notice %}}

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

## Unit Testing Basics

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

#### Test Libraries bestaande uit twee componenten

Een test framework, zoals JUnit voor Java, MSUnit/NUnit voor C#, of Jasmine voor Javascript, bevat twee delen: 

##### 1. Het Test Harnas

Een 'harnas' is het concept waar alle testen aan worden opgehangen. Het harnas identificeert en verzamelt testen, en het harnas stelt het mogelijk om bepaalde of alle testen uit te voeren. De ingebouwde Test UI in IntelliJ fungeert hier als visueel harnas. Elke test methode, een `public void` methode geannoteerd met `@Test`, wordt herkent als mogelijke test. Bovenstaande screenshot van NUnit in C# is een visuele weergave van de resultaten, verzameld door dit systeem.

Gradle en het JUnit harnas verzamelen data van testen in de vorm van HTML rapporten.

##### 2. Het Assertion Framework

Naast het harnas, die zorgt voor het uitvoeren van testen, hebben we ook een _verificatie framework_ nodig, dat fouten genereert wanneer nodig, om te bepalen of een test al dan niet geslaagd is. Dit gebeurt typisch met **assertions**, die vereisten dat een argument een bepaalde waarde heeft. Is dit niet het geval, wordt er een `AssertionError` exception gegooid, die door het harnas herkent wordt, met als resultaat een falende test. 

Assertions zijn er in alle kleuren en gewichten, waarbij in de oefeningen de statische methode `assertThat()` wordt gebruikt, die leeft in `org.assertj.core.api.Assertions`. AssertJ is een plugin library die ons in staat stelt om een _fluent API_ te gebruiken in plaats van moeilijk leesbare assertions:

<div class="devselect">

```java
@Test
public void testWithDefaultAssertions() {
    var result = doStuff();
    AssertEquals(result, 3);    // arg1: expected, arg2: actual
}
@Test
public void testWithAssertJMatchers() {
    var result = doStuff();
    assertThat(result).isEqualTo(3);
}
```

</div>


Het tweede voorbeeld leest als een vloeiende zin, terwijl de eerste `AssertEquals()` vereist dat als eerste argument de expected value wordt meegegeven - dit is vaak het omgekeerde van wat wij verwachten! 

[AssertJ core API Documentation](https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html)

Een populair alternatief voor AssertJ is bijvoorbeeld [Hamcrest](http://hamcrest.org/JavaHamcrest/javadoc/). De keuze is aan jou: alle frameworks bieden ongeveer dezelfde fluent API aan met ongeveer dezelfde features. 

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

### Soorten van Testen

Er zijn drie grote types van testen:

![](/img/testniveaus.jpg "De drie soorten van testen.")

#### 1. Unit Testing (GROEN)

Een unit test test zaken op _individueel niveau_, per klasse dus. De methodes van de `Periode` klasse testen zijn unit testen: er zijn geen andere klasses mee gemoeid. De meeste testen zijn unit testen. Hoe kleiner het blokje op bovenstaande figuur, hoe beter de **F.I.R.S.T. principes** kunnen nageleefd worden. Immers, hoe meer systemen opgezet moeten worden voordat het assertion framework zijn ding kan doen, hoe meer tijd verloren, en hoe meer tijd de test in totaal nodig heeft om zijn resultaat te verwerken. 

#### 2. Integration Testing (ORANJE)

Een integratie test test het _integratie_ pad tussen twee verschillende klasses. Hier ligt de nadruk op _interactie_ in plaats van op individuele functionaliteit, zoals bij de unit test. We willen bijvoorbeeld controleren of een bepaalde service wel iets wegschrijft naar de database, maar het schrijven zelf is op unit niveau bij de database reeds getest. Waar wij nu interesse in hebben, is de interactie tussen service en database, niet de functionaliteit van de database. 

Typische eigenschappen van integration testen:

- Test geïntegreerd met externen. (db, webservice, ...)
- Test integratie twee verschillende lagen.
- Trager dan unit tests.
- Minder test cases.

##### Test Doubles

Stel dat we een `Service` en een `Repository` klasse hebben gemaakt, waarvan de tweede gegevens wegschrijft naar een database. Als we de eerste klasse willen testen, willen we niet weer een verbinding opstellen, omdat dit te traag is (1), én omdat dit al getest is (2):

<div class="devselect">

```java
public class Repository {
    public void save(Customer c) {
        // insert into ... 
    }
}
public class Service {
    private Repository repository;

    public void updateCustomerWallet(Customer c, double balance) {
        c.setBalance(balance);
        repository.save(c);
    }
}
```

</div>

Hoe testen we de `updateCustomerWallet()` methode, zonder de effectieve implementatie van `save()` te moeten gebruiken? Door middel van _test doubles_.

![](/img/testdouble.jpg "I'll Be Back.")

Zoals Arnie in zijn films bij gevaarlijke scenes een stuntman lookalike gebruikt, zo gaan wij in onze code een `Repository` lookalike gebruiken, zodat de `Service` dénkt dat hij `save()` aanroept, terwijl dit in werkelijkheid niet zo is. Daarvoor moet de repository een interface zijn. We passen in principe een design pattern toe, waarbij in de service een repository instantie wordt geïnjecteerd:

<div class="devselect">

```java
public interface Repository {
    void save(Customer c);
}
public class RepositoryDBImpl implements Repository {
    @Override
    void save(Customer c) {
        // insert into...
    }
}
public class RepositoryForTesting implements Repository {
    @Override
    public void save(Customer c) {
        // do nothing!
    }
}
public class Service {
    private Repository repository;
    public Service(Repository r) {
        this.repository = r;
    }
}
```

</div>

In de test wordt een instantie van `RepositoryForTesting` in service gebruikt in plaats van de effectieve `RepositoryDBImpl`. De test klasse _gedraagt_ zich als een `Repository`, omdat deze de betreffende interface implementeert. De `Service` klasse weet niet welke implementatie van de interface binnen komt: daar kan bij het integration testing handig gebruik van worden gemaakt.


#### 3. End-To-End Testing (ROOD)

Een laatste groep van testen genaamd _end-to-end_ testen, ofwel **scenario testen**, testen de héle applicatie, van UI tot DB. Voor een GUI applicatie bijvoorbeeld betekent dit het simuleren van de acties van de gebruiker, door op knoppen te klikken en te navigeren doorheen de applicatie, waarbij bepaalde verwachtingen worden afgetoetst. Bijvoorbeeld, klik op 'voeg toe aan winkelmandje', ga naar 'winkelmandje', controleer of het item effectief is toegevoegd.

Typische eigenschappen van end-to-end testen:

- Test hele applicatie!
- Niet alle limieten.
- Traag, moeilijker onderhoudbaar.
- Test integratie van alle lagen.

![](/img/tdd/selenium.png)

In plaats van dit in (Java) code te schrijven, is het ook mogelijk om de [Selenium IDE](https://selenium.dev/selenium-ide/) extentie voor Google Chrome of [Mozilla Firefox](https://addons.mozilla.org/en-US/firefox/addon/selenium-ide/) te gebruiken. Deze browser extentie laat recorden in de browser zelf toe, en vergemakkelijkt het gebruik (er is geen nood meer aan het vanbuiten kennen van zulke commando's). Dit wordt in de praktijk vaak gebruikt door software analisten of testers die niet de technische kennis hebben om te programmeren, maar toch deel zijn van het ontwikkelteam. 

Recente versies van de Selenium IDE plugin bewaren scenario's in `.side` bestanden, wat een JSON-notatie is. Oudere versies bewaren commando's in het `.html` formaat. deze bestanden bevatten een lijst van je opgenomen records:

```
  "tests": [{
    "id": "73bc78d5-1ca2-44c4-9ad2-6ccfe7cba5fe",
    "name": "bla",
    "commands": [{
      "id": "f192c93d-064a-4298-8d84-a44fd617622b",
      "comment": "",
      "command": "open",
      "target": "/mattersof/workshops",
      "targets": [],
      "value": ""
    }, {
      "id": "b94bd35b-0fc0-4ede-9b47-7e24d78126e8",
      "comment": "",
      "command": "setWindowSize",
      "target": "1365x691",
      "targets": [],
      "value": ""
    }, {
        ...
```

### TDD voor JavaFX
Er bestaan een aantal frameworks om end-to-end testing uit te voeren op JavaFX projecten. Deze vallen echter buiten de scope van dit vak. Je kan echter wel goede coding conventies gebruiken om UI-elementen te testen zoals we in lessen gaan zien.

## <a name="oef"></a>Labo oefeningen

Clone of fork het <i class='fab fa-github'></i> GitHub project https://github.com/KULeuven-Diepenbeek/ses-tdd-exercise-1-template

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

Mockito is verreweg het meest populaire Unit Test Framework dat bovenop JUnit wordt gebruikt om heel snel Test Doubles en integratietesten op te bouwen. 

![Mockito logo](/img/teaching/ses/mockito.png)

Gebruik dus hiervoor Mockito, en injecteer een `mock(SpeculaasFabriek.class)` in de verkoopster (de setter is reeds voorzien). 

Lees op [https://site.mockito.org](https://site.mockito.org) **hoe** je het framework moet gebruiken. (Volledige [javadoc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)) Denk aan de volgende zaken:

- Hoe include ik Mockito als een dependency in mijn project?
- Hoe gebruik ik de API om een Test Double/mock aan te maken?
- Hoe valideer ik verwachtingen die ik heb van deze Test Double?

##### Mocking
Meer info rond faking vs. mocking vindt je [hier](https://www.educative.io/answers/what-is-faking-vs-mocking-vs-stubbing):
<blockquote>Fakes are objects that have working implementations. On the other hand, mocks are objects that have predefined behavior. Lastly, stubs are objects that return predefined values. When choosing a test double, we should use the simplest test double to get the job done.
</blockquote>

### Opgave 5

Gebruik Selenium IDE om een test scenario op te nemen van het volgende scenarios op de website [https://www.saucedemo.com/](https://www.saucedemo.com/). :

1. Log in met "locked_out_user" en wachtwoord "secret_sauce" en verifieer dat je een error boodschap krijgt. 
2. Log in met "standard_user" en wachtwoord "secret_sauce", klik op het eerste item, voeg toe aan je winkelmandje, ga naar je winkelmandje. Verifieer dat er een product inzit.
3. Log in met "standard_user" en wachtwoord "secret_sauce" en test of de afbeeldingen van de producten verschillend zijn.
4. Log in met "problem_user" en wachtwoord "secret_sauce" en test of de afbeeldingen van de producten verschillend zijn. (Deze test moet nu falen omdat je je voordoet als een user die een bug ervaart.)  

{{% notice info %}}
**Bewaar dit scenario, opgenomen met de Selenium IDE, in bestand _opgave5.html_ (of `.side` voor nieuwe versies)** in de root de [repository van opgave 1](https://github.com/KULeuven-Diepenbeek/ses-tdd-exercise-1-template).
{{% /notice %}}

Je zal voor deze opgave dus de Selenium (Chromium/Firefox) plugin moeten installeren: zie hierboven.




