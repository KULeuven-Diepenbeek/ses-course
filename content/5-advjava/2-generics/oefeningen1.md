---
title: "Oefeningen: definitie"
toc: true
weight: 20
autonumbering: true
draft: false
---

Voor de tests maken we gebruik van [assertJ](https://assertj.github.io/doc).

## Maybe-klasse

1. Schrijf een generische klasse (of record) `Maybe` die een object voorstelt dat nul of één waarde van een bepaald type kan bevatten.
   Dat type wordt bepaald door een generische parameter. Je kan Maybe-objecten enkel aanmaken via de statische methodes `some` en `none`.
   Hieronder vind je twee tests:

```java
@Test
public void maybeWithValue() {
    Maybe<String> maybe = Maybe.some("Yes");
    assertThat(maybe.hasValue()).isTrue();
    assertThat(maybe.getValue()).isEqualTo("Yes");
}

@Test
public void maybeWithoutValue() {
    Maybe<String> maybe = Maybe.none();
    assertThat(maybe.hasValue()).isFalse();
    assertThat(maybe.getValue()).isNull();
}
```

2. Maak de `print`-methode hieronder ook generisch, zodat deze niet enkel werkt voor een `Maybe<String>` maar ook voor andere types dan `String`.

```java
class MaybePrint {
  public static void print(Maybe<String> maybe) {
    if (maybe.hasValue()) {
      System.out.println("Contains a value: " + maybe.getValue());
    } else {
      System.out.println("No value :(");
    }
  }

  public static void main(String[] args) {
    Maybe<String> maybeAString = Maybe.some("yes");
    Maybe<String> maybeAnotherString = Maybe.none();

    print(maybeAString);
    print(maybeAnotherString);
  }
}
```

3. Voeg aan `Maybe` een generische methode `map` toe die een `java.util.function.Function<T, R>`-object als parameter heeft, en die een nieuw Maybe-object teruggeeft, met daarin het resultaat van de functie toegepast op het element als er een element is, of een leeg Maybe-object in het andere geval.
   Zie de tests hieronder voor een voorbeeld van hoe deze map-functie gebruikt wordt:

```java
@Test
public void maybeMapWithValue() {
    Maybe<String> maybe = Maybe.some("Hello");
    Maybe<Integer> result = maybe.map((str) -> str.length());
    assertThat(result.hasValue()).isTrue();
    assertThat(result.getValue()).isEqualTo(5);
}

@Test
public void maybeMapWithValue2() {
    Maybe<String> maybe = Maybe.some("Hello");
    Maybe<String> result = maybe.map((str) -> str + "!");
    assertThat(result.hasValue()).isTrue();
    assertThat(result.getValue()).isEqualTo("Hello!");
}

@Test
public void maybeMapWithoutValue() {
    Maybe<String> maybe = Maybe.none();
    Maybe<Integer> result = maybe.map((str) -> str.length());
    assertThat(result.hasValue()).isFalse();
}
```

4. (optioneel) Herschrijf `Maybe` als een sealed interface met twee record-subklassen `None` en `Some`.
   Geef een voorbeeld van hoe je deze klasse gebruikt met pattern matching.
   Kan je ervoor zorgen dat je getValue() nooit kan oproepen als er geen waarde is (compiler error)?

## (extra) SuccessOrFail

Schrijf een generische klasse (of record) `SuccessOrFail` die een object voorstelt dat precies één element bevat.
Dat element heeft 1 van 2 mogelijke types (die types zijn generische parameters).
Het eerste type stelt het type van een succesvol resultaat voor; het tweede type is dat van een fout.
Je kan objecten enkel aanmaken via de statische methodes `success` en `fail`.
Een voorbeeld van tests voor die klasse vind je hieronder:

```java
@Test
public void success() {
    SuccessOrFail<String, Exception> result = SuccessOrFail.success("This is the result");
    assertThat(result.isSuccess()).isTrue();
    assertThat(result.successValue()).isEqualTo("This is the result");
}

@Test
public void failure() {
    SuccessOrFail<String, Exception> result = SuccessOrFail.fail(new IllegalStateException());
    assertThat(result.isSuccess()).isFalse();
    assertThat(result.failValue()).isInstanceOf(IllegalStateException.class);
}
```
