---
title: '3.1 TDD In de praktijk: Mockito'
---

Mockito is verreweg het meest populaire Unit Test Framework dat bovenop JUnit wordt gebruikt om heel snel Test Doubles en integratietesten op te bouwen. 

![Mockito logo](/img/teaching/ses/mockito.png)


Lees op [https://site.mockito.org](https://site.mockito.org) **hoe** je het framework moet gebruiken. (Klik op de knoppen **WHY** en **HOW** bovenaan! Volledige [javadoc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)) Denk aan de volgende zaken:

- Hoe include ik Mockito als een dependency in mijn project?
- Hoe gebruik ik de API om een Test Double/mock aan te maken?
- Hoe valideer ik verwachtingen die ik heb van deze Test Double?

### Een interface 'mocken': the hard way

Zoals aangehaald in de [TDD noties](/tdd) kan een eigen implementatie van een interface worden gemaakt die als Test Double werkt (denk aan Arnie's stuntman). Het vervelende is dat deze klasse enkel maar wordt gebruikt in test code, dus niet in productie code. Mockito maakt dit dynamisch aan, zonder dat er ooit het keyword `class` bij aan de pas komt. 

![](/img/testdouble.jpg "I'll Be Back.")


Stel, Arnold gaat acteren voor de nieuwe film **Die Hard: In A Deepen Beek** (uit in 2025 - Bruce had geen zin meer). Hij moet daarvoor een aantal fantastische stunts uithalen, die hij niet graag zelf zou doen. De volgende interface definieert een 'Arnold', waarbij `doBackFlip()` de gevaarlijke stunt is (resultaat is `true` indien geslaagd):

<div class="devselect">

```kt
interface IllBeBack {
    fun doBackFlip(): Boolean
}
```

```java
public interface IllBeBack {
    boolean doBackFlip();
}
```

</div>

De casting crew en de director verwachten dat tijdens een opname 3x een backflip succesvol wordt uitgevoerd. Indien deze niet lukt, wordt de take opnieuw genomen. Dit manifesteert zich in code in de vorm van een `RuntimeException`. De One And Only Arnold had op dat moment een crisisvergadering in Californië en kon niet aanwezig zijn op de set. Gelukkig zijn er lookalikes en stuntmannen genoeg:

<div class="devselect">

```kt
class ArnieLookalike : IllBeBack {
    override fun doBackFlip(): Boolean = false
}

class StuntmanArnie : IllBeBack {
    override fun doBackFlip(): Boolean = true
}
```

```java
public class ArnieLookalike implements IllBeBack {
    @Override
    public boolean doBackFlip() {
        return false;
    }
}

public class StuntmanArnie implements IllBeBack {
    @Override
    public boolean doBackFlip() {
        return true;
    }
}
```

</div>

Enkel `StuntmanArnie` is fysiek krachtig genoeg om consistent de backflip uit te voeren. De lookalike ziet er uit zoals Arnold, maar bakt jammer genoeg van de scene niet zo veel. Hieronder een blueprint van de opname code:

<div class="devselect">

```kt
class DieHardInADeepenBeek(var arnoldActor: IllBeBack) {
    fun recordActOne() {
        var succeeded = true
        for(i in 1..3) {
            succeeded = succeeded && arnoldActor.doBackFlip()
        }

        if(!succeeded)
            throw RuntimeException("do that again, please...")
    }
}
```

```java
public class DieHardInADeepenBeek {
    private IllBeBack arnoldActor;

    public void setArnoldActor(IllBeBack actor) {
        this.arnoldActor = actor;
    }

    public void recordActOne() {
        boolean succeeded = true;
        for(int i = 1; i <= 3; i++) {
            succeeded = succeeded && arnoldActor.doBackFlip();
        }

        if(!succeeded) {
            throw new RuntimeException("do that again, please...");
        }
    }
}
```

</div>

De hamvraag is nu: hoe testen we de **logica in `recordActOne`**? Daarvoor zal de backflip soms moeten lukken, en soms ook niet. We hebben dus zowel een `ArnieLookalike` als `StuntmanArnie` implementatie nodig:

<div class="devselect">

```kt
class DieHardInADeepenBeekTests {
    @Test
    fun `Given failing backflip When recording act one Then redo the whole thing`() {
        // 1. Arrange
        val movie = DieHardInADeepenBeek(ArnieLookalike())

        // 2/3 act/assert in one
        assertThrows(RuntimeException::class.java) { movie.recordActOne() }
    }

    @Test
    fun `Given a good backflip When recording act one Then its a success`() {
        // 1. Arrange
        val movie = DieHardInADeepenBeek(StuntmanArnie())

        // 2/3 act/assert in one
        assertDoesNotThrow { movie.recordActOne() }
    }
}
```

```java
public class DieHardInADeepenBeekTests {
    @Test
    public void recordActOne_backflipFails_haveToRedoTheWholeThing() {
        // 1. Arrange
        var movie = new DieHardInADeepenBeek();
        var actor = new ArnieLookalike();
        movie.setArnoldActor(actor);

        // 2. act
        assertThrows(RuntimeException.class, 
            () -> { movie.recordActOne() });
        // 3. assert (in annotation)
    }

    @Test
    public void recordActOne_backflipSucceeds_ok() {
        // 1. Arrange
        var movie = new DieHardInADeepenBeek();
        var actor = new StuntmanArnie();
        movie.setArnoldActor(actor);

        // 2. act
        assertDoesNotThrow(() -> { movie.recordActOne() });
        // 3. assert (not needed, doesn't crash)
        // assertTrue(true);
    }
}
```

</div>

### Een interface 'mocken': the easy way

In plaats van de `ArnieLookalike` en `StuntmanArnie` klasses zelf te maken, kunnen we hier Mockito het zware werk laten doen door gebruik te maken van de `mock()` methode. De testen worden dan lichtjes anders, omdat we daarin het gedrag van de mock eerst moeten bepalen voordat we naar de act en assert stappen kunnen gaan:

<div class="devselect">

```kt
import org.mockito.Mockito.`when` as When
class DieHardInADeepenBeekTests {
    @Test
    fun `Given failing backflip When recording act one Then redo the whole thing`() {
        // 1. Arrange
        val actor = mock(IllBeBack::class.java)
        When(actor.doBackFlip()).thenReturn(false)
        val movie = DieHardInADeepenBeek(actor)

        // 2/3 act/assert in one
        assertThrows(RuntimeException::class.java) { movie.recordActOne() }
    }

    @Test
    fun `Given a good backflip When recording act one Then its a success`() {
        // 1. Arrange
        val actor = mock(IllBeBack::class.java)
        When(actor.doBackFlip()).thenReturn(true)
        val movie = DieHardInADeepenBeek(actor)

        // 2/3 act/assert in one
        assertDoesNotThrow { movie.recordActOne() }
    }
}
```

```java
public class DieHardInADeepenBeekTests {
    @Test
    public void recordActOne_backflipFails_haveToRedoTheWholeThing() {
        // 1. Arrange
        var movie = new DieHardInADeepenBeek();
        var actor = mock(IllBeBack.class);
        when(actor.doBackFlip()).thenReturn(false);
        movie.setArnoldActor(actor);

        // 2. act
        assertThrows(RuntimeException.class, 
            () -> { movie.recordActOne() });
        // 3. assert (in annotation)
    }

    @Test
    public void recordActOne_backflipSucceeds_ok() {
        // 1. Arrange
        var movie = new DieHardInADeepenBeek();
        var actor = mock(IllBeBack.class);
        when(actor.doBackFlip()).thenReturn(true);
        movie.setArnoldActor(actor);

        // 2. act
        assertDoesNotThrow(() -> { movie.recordActOne() });
        // 3. assert (not needed, doesn't throw)
    }
}
```

</div>

Het geheim zit hem in de `mock()` en `when()` methodes, waarmee we het gedrag van de mock implementatie kunnen aansturen. Dit werd vroeger manueel geïmplementeerd, maar die klasses zijn nu niet meer nodig. 

Lees op [https://site.mockito.org](https://site.mockito.org) **hoe** je het framework moet gebruiken. (Klik op de knoppen **WHY** en **HOW** bovenaan! Volledige [javadoc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)) 

{{% notice warning %}}
Dezelfde Kotlin-specifieke problemen met reserved keywords zoals HamCrest's `is` komen hier voor met Mockito's `when`! Een Kotlin-idiomatic alternatief is gebruik maken van [MockK](https://mockk.io/) in plaats van Mockito, die specifiek geschreven is voor Kotlin. <br/>`Mockito.mock(MyClass::class.java)` wordt dan `mockk<MyClass>()`. `When(x.y()).thenReturn(z)` wordt dan `every { x.y() } returns z`. Zie `examples/kotlin/mocking` in de cursus git repository. 
{{% /notice %}}

### TDD in een groter project

De [SESsy library](/extra/sessy) webapplicatie bevat ook unit-, integratie- en endtoend-testen die een meer 'real-life' omgeving simuleren met een grotere codebase. Zij die zoeken naar een beter begrip van het concept TDD en de implementatie ervan in de dagelijkse wereld, kunnen daar hun oren en ogen de kost geven. We moedigen tevens het wijzigen van testen aan om te kijken wat er gebeurt!

