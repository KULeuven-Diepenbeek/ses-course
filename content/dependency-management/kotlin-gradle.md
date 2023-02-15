---
title: '2.3 Kotlin Gradle projecten'
---

De `build.gradle` bestanden die tot nu toe in de cursus zijn beschreven (zie [2.1 Java Gradle Projecten](/dependency-management/gradle)), werden geschreven in de [Groovy programmeertaal](http://www.groovy-lang.org/), een multi-purpose dynamische taal voor het Java platform. Aangezien we voor Kotlin projecten geen Groovy, noch Java, maar Kotlin schrijven, zou het fijn zijn om de syntax van de Gradle configuratie óók te kunnen wijzigen naar Kotlin. 

Dat kan.

Daarvoor vink je bij het aanmaken van nieuwe projecten in IntelliJ het vinkje **Kotlin DSL Build Script** aan:

![](/img/kotlindsl.jpg "Het eerste vinkje, onder 'Project SDK'")

Dan wordt een `build.gradle.kts` script file gegenereerd. Onthoud: de `.kts` extensie betekent dat de Kotlin DSL actief is. Wat betekent dat concreet? De syntax van het bestand is lichtjes gewijzigd. Dit is een typische minimale config file in Kotlin:

```
plugins {
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks.test {
    useJUnitPlatform()
}
```

De wijzigingen zijn onder andere:

- Alles is een **functie**! `dependencies` is een functie die een block binnen pakt, dat een closure is. 
- Dependencies worden gewrapped in een functie in plaats van een gewone string. 
- De Kotlin versie wordt bovenaan ingesteld, samen met het doelplatform (`"jvm"`)
- Kotlin komt met built-in JUnit 5 compatibiliteit als je `useJUnitPlatform()` activeert.

Meer lezen? Vast met een instelling?

- [Official Kotlin documentation on Gradle](https://kotlinlang.org/docs/gradle.html)
- [Gradle Kotlin DSL Primer](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

{{% notice note %}}
Je kan gerust een Kotlin project aanmaken zonder te kiezen voor de Kotlin DSL. In dat geval blijft de build file de standaard Groovy-style `build.gradle`. Het betekent echter drie talen moeten beheersen: Java, Groovy, én Kotlin. <br/>Je kan ook zowel Java als Kotlin compilen; dan verschijnen er twee entries onder de `plugins {}` block: `kotlin()` voor `src/main/kotlin` source files en `java()` voor `src/main/java` source files.
{{% /notice %}}


### Geavanceerde instellingen

Dit is geen deel van de cursus maar soms wel handig om te weten. 

Stel een JDK doel in om compatibiliteit met oude Java runtimes te behouden:

```
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.6"
```

Zie [Kotlin compiler options](https://kotlinlang.org/docs/gradle.html#compiler-options) voor een lijst van mogelijkheden. 