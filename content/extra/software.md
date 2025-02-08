---
title: 'Gebruikte Software'
---

Voor deze cursus werken we met Java en met Git. Dat betekent dat **JDK 15.0.2** (en de Gradle build tool, `v6.7`) en een **Git client** de enige vereisten zijn. 

Onderstaande lijst kan helpen bij het installeren en configureren van zulke tools:

## Commandline Tools

- [git-scm](https://git-scm.com/downloads)
- [gradle](/dependency-management/gradle)

Het is onze intentie om jullie kennis van **Linux commando's**, opgedaan in het vak Besturingssystemen en C, hier ook van pas te laten komen. Daarom is het aangeraden om dezelfde virtuele machine te gebruiken voor BESC en voor SES. Indien dit voor jou niet lukt, vanwege performantieproblemen of andere, kan je toch nog op Windows met unix-like commandos werken. Daarvoor raden we één van de volgende toolchain aan: 

- [cmder, Portable console Emulator for Windows](https://cmder.net)
- [MinGW, minimalist GNU for Windows](http://www.mingw.org/wiki/msys)
- [Cygwin](https://www.cygwin.com)

**Let op**, bovenstaande tools mixen kan grote negatieve gevolgen hebben, iedere GNU flavor werkt op een andere manier en ze bëinvloeden allemaal je `%PATH%` systeen variabele (Unix: `$PATH`)

## Software Development Tools

### Installatieinstructies JDK

#### 1. Install JDK

Via:

- https://jdk.java.net/archive/ zonder in te loggen, of
- https://www.oracle.com/java/technologies/downloads/archive/ (Oracle account vereist).

{{% notice warning %}}
De exacte JDK versie is erg belangrijk. Installeer versie `15.0.2`. Waarom `15` en geen recentere? Vanwege de [Gradle compatibility matrix](https://docs.gradle.org/current/userguide/compatibility.html): te nieuwe JDKs werken niet met oudere Gradle versies, waardoor je de oefeningen niet met Gradle zal kunnen compileren. 
{{% /notice %}}



#### 2. Set JAVA_HOME

**Windows gebruikers**: volg De [HowToDoInJava Installing Java on 64 bit Windows](https://howtodoinjava.com/java/basics/install-java-on-64-bit-windows/) guide om jullie `%PATH%` en `%JAVA_HOME%` omgevingsvariabelen correct op te zetten. 

Als alles gelukt is, kan je in een opdrachtprompt `java -version` typen en krijg je als resultaat de geïnstalleerde Java versie. 

**Unix gebruikers**: Editeer jullie `~/.bash_profile` , `~/.bashrc` of `~/.zshrc`, afhankelijk van de gebruikte cmdline `$SHELL`, en voeg de `$JAVA_HOME` variabele toe op de volgende manier (pas het pad aan):

```sh
export JAVA_HOME="/dir/to/jdk/home"
export PATH="$JAVA_HOME/bin:$PATH"
```

### Installatieinstructies Gradle

**NIET** apart installeren - dit wordt automatisch gedownload bij het bootstrappen van een Gradle project. 

### Installatieinstructies IntelliJ

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Java Development Kit 15.x of later](https://www.oracle.com/java/technologies/javase-downloads.html)

De IntelliJ **community edition** is voldoende. Studenten krijgen een gratis licentie op alle JetBrains producten. 

Studenten gebrand op de "Ultimate Edition" kunnen hun registreren op de website met het `@student.uhasselt.be` e-mail adres zodat het bedrijf herkent dat je studeert aan een Universitaire Instelling. Dan is ook die versie gratis te downloaden. 

