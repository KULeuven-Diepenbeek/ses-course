---
title: '5.2 Dependency Injection'
---

## _"Dependency Injection (DI)"_ - Design Pattern

Begeleidende screencast[^host]:

[^host]: Merk op dat de cursus nu wordt gehost op https://kuleuven-diepenbeek.github.io/ses-course/

{{< vimeo 398530942 >}}


### Doelstelling

* Promoot _modulariteit_ door afhankelijkheden te injecteren, zodat aparte modules eenvoudig inplugbaar zijn in andere productiecode. Op deze manier worden modules ook makkelijker tesbaar.
* Promoot _Inversion of Control_: een client die services aanroept zou niet mogen weten hoe services worden aangemaakt - deze zou moeten worden 'geinjecteerd'.

[Wikipedia: Dependency Injection](https://en.wikipedia.org/wiki/Dependency_injection)

### Voorbeeld

#### 1. Opzet

Als we verder gaan op het voorbeeld van de [singleton](/patterns/singleton), zien we dat een database handle nodig is om shopping cart gegevens op te halen. Hoe deze database wordt aangemaakt, daar heeft een typische API geen kaas van gegeten: daar komt meestal een connection string bij kijken met gebruikersnaam, wachtwoord, en IP adres naar de juiste DB server. 

De DB accessor:

<div class="devselect">

```kt
// eigenlijk is dit al DI: de connectionString wordt meegegeven
class DBHandle(val connectionString: String) {
    fun getShoppingCart(): ShoppingCart {
        // SELECT * FROM ...
    }
}
```

```java
public class DBHandle {
    private String connectionString;

    // eigenlijk is dit al DI: de connectionString wordt meegegeven
    public DBHandle(String connectionString) {
        this.connectionString = connectionString;
    }

    public ShoppingCart getShoppingCart() {
        // SELECT * FROM ...
    }
}
```

</div>

Met als REST endpoint:

<div class="devselect">

```kt
@Path("/shoppingcart")
class ShoppingResource {
    @GET
    fun getCart(): ShoppingCart {
        val conStr = ConfigurationManager.getConfig("dbconnection")
        return DBHandle(conStr).getShoppingCart()
    }
}
```

```java
@Path("/shoppingcart")
public class ShoppingResource {
    @GET
    public ShoppingCart getCart() {
        String conStr = ConfigurationManager.getConfig("dbconnection");
        return new DBHandle(conStr).getShoppingCart();
    }
}
```

</div>

#### 2. Probleemstelling

Elke Resource klasse die een `DBHandle` instance wenst, zal ook via de `ConfigurationManager` het constructor argument moeten ophalen, om een instantie te kunnen aanmaken. Dit is uiteraard **niet** de juiste manier en introduceert veel duplicatie. Nu is de shopping resource "in control", terwijl we in dat geval de Database de controle willen geven: **Inversion of Control** dus.

#### 3. Oplossing

Een mogelijke oplossing is een Singleton maken: zie [het Singleton pattern](/patterns/singleton). Maar dan hebben we nog steeds:

<div class="devselect">

```kt
@Path("/shoppingcart")
class ShoppingResource {
    @GET
    fun getCart(): ShoppingCart {
        return DBHandle.getShoppingCart()
    }
}
```

```java
@Path("/shoppingcart")
public class ShoppingResource {
    @GET
    public ShoppingCart getCart() {
        return DBHandle.getInstance().getShoppingCart();
    }
}
```

</div>

Als we deze methode willen **unit testen**, door `getCart()` op te roepen, spreken we steeds de échte database aan, wat duidelijk niet het gewenste gedrag is. We willen in dat geval de database **injecteren**. Een tweede stap is om de implementatie te verbergen achter een interface. 

<div class="devselect">

```kt
@Path("/shoppingcart")
class ShoppingResource(val dbHandle: DBHandle) {
    @GET fun getCart(): ShoppingCart {
        return dbHandle.getShoppingCart()
    }
}
```

```java
@Path("/shoppingcart")
public class ShoppingResource {
    private final DBHandle dbHandle;

    public ShoppingResource(DBHandle handle) {
        this.dbHandle = handle;
    }

    @GET
    public ShoppingCart getCart() {
        return dbHandle.getShoppingCart();
    }
}
```
</div>

{{% notice note %}}
Merk op dat de Kotlin implementatie veel korter is dankzij **primary constructors**: alles tussen de haakjes wordt automatisch omgezet in een veld dat injecteerbaar is. Bijkomend, `val` is automatisch een `final` veld.<br/>
Om te begrijpen wat er gebeurt in de JVM kan je de Kotlin-compiled bytecode inspecteren via menu _Tools - Kotlin - Show Kotlin Bytecode_. 
{{% /notice %}}

Nu weet deze klasse niet meer hoe hij de database aanmaakt: hij krijgt dit slechts toegeschoven via de constructor. Uiteraard hebben we het probleem verlegt: wie maakt deze resource klasse aan? Om dit probleem op te lossen zijn er typische Dependency Injection frameworks beschikbaar die objecten in een pool aanmaken en zo injecteren. Voorbeelden hiervan zijn:

- [Google Guice](https://github.com/google/guice)
- [Google Dagger](https://github.com/google/dagger) (vooral, maar niet alleen, voor _Android_ development)
- [Spring Framework](https://spring.io)
- [Kodein-DI](https://github.com/Kodein-Framework/Kodein-DI) (vooral, maar niet alleen, voor _Kotlin_ projecten)

Als `DBHandle` een interface is, kunnen we op een eenvoudige manier een dummy implementatie maken en dit injecteren in de klasse ter test:

<div class="devselect">

```kt
class DummyDBHandle : DBHandle {
    var called = false
    
    override fun getShoppingCart(): ShoppingCart? {
        called = true
        return null
    }
}

class ShoppingResourceTest {
    @Test fun getCart_callsGetShoppingCartFromDb() {
        val dbHandle = DummyDBHandle()
        // hier "injecteren" we de dbHandle dummy in de ShoppingResource.
        val resource = ShoppingResource(dbHandle)
        resource.getCart()

        assertThat(dbHandle.called, Is(true))
    }
}
```

```java
public class DummyDBHandle implements DBHandle {
    public boolean called;
    @Override
    public ShoppingCart getShoppingCart() {
        called = true;
        return null;
    }
}

public class ShoppingResourceTest {
    @Test
    public void getCart_callsGetShoppingCartFromDb() {
        DummyDBHandle dbHandle = new DummyDBHandle();
        // hier "injecteren" we de dbHandle dummy in de ShoppingResource.
        ShoppingResource resource = new ShoppingResource(dbHandle);
        resource.getCart();

        assertThat(dbHandle.called, is(true));
    }
}
```
</div>

Merk op dat de `connectionString` van de `DBHandle` ook via de constructor als argument wordt doorgegeven: dit is evenzeer een vorm van Dependency Injection. 

### Eigenschappen van dit patroon

* Geef de verantwoordelijkheid van het _aanmaken_ van een object af. Een instantie wordt geinjecteerd door middel van een constructor of setter. 
* Maak van objecten geïsoleerde(re) stukjes code die makkelijker testbaar zijn dan hard gekoppelde objecten. 

## <a name="oef"></a>Labo oefeningen

Via [<i class='fab fa-github'></i> Github Classroom](/extra/github-classroom). 

### Opgave 1

* Er staan twee TODO items: verwijder eerst de `new DBHandle()` vanuit de resource klasse, en injecteer het via een constructor argument. Pas dan de unit test aan om de compile fouten te fixen.
* Gebruik een interface om bovenstaande `DummyDBHandle` in het project te introduceren. Dat wil zeggen, hernoem `DBHandle` naar `DBHandleImplementation`, en maak een nieuwe interface genaamd `DBHandle`. Nu kan je de tweede unit test zoals hierboven toevoegen. 

### Opgave 2

In plaats van manueel te injecteren, kunnen we deze zaken ook overlaten aan gespecialiseerde frameworks, zoals Google Guice. Bovenstaand project heeft als Gradle dependency een link naar Guice. Neem een kijkje in de `ShoppingCartGuiceResource` klasse, en probeer dit principe toe te passen op de andere resouce klasse. `@Inject` verzogt het DI systeem, zonder zelf ergens objecten aan te maken, behalve in de config klasse. 

Zie ook [Google Guice: getting started](https://github.com/google/guice/wiki/GettingStarted). 

### Opgave 3

[sessy library](/extra/sessy): 

1. Welke klassen worden reeds geïnjecteerd, en op welke manier? (Constructor injectie, setter injectie, ...)
2. identificeer welke klassen in een DI systeem kunnen worden opgenomen. 
3. Introduceer een DI systeem: hetzij door Google Guice te gebruiken, hetzij door zelf te injecteren. Waar wordt DI reeds toegepast?

## Denkvragen

* Dependency Injection kan via de constructor, via setters (of direct op het veld via reflectie). Wat zijn de voor- en nadelen van via de constructor te werken, ten opzichte van via setters? 
* Denk je dat de `GuiceConfigModule` klasse op termijn niet te groot en verwarrend wordt, als dit constant wordt uitgebreid met nieuwe instanties die geregistreerd worden bij Guice? Wat zou je dan doen om dit tegen te gaan? 