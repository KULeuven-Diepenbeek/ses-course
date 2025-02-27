---
title: 'Cmdline Java Compiling'
weight: 2
draft: true
author: Wouter Groeneveld en Arne Duyver
---

Dit is in principe iets wat je in het INF1 vak onbewust reeds uitvoerde door op de groene "Compile" knop te drukken van je NetBeans/IntelliJ IDE. Het is belangrijk om te weten welke principes hier achter zitten. Hieronder volgt ter herhaling een kort overzicht over het compileren van Java programma's _zonder_ een buildtool.

## Een minimaal programma compileren

Als je je `JAVA_HOME` en `PATH` omgevingsvariabelen goed hebt ingesteld (zie [installatie van gebruikte software](/extra/software/)), dan kan je in een CMD window de commando's:

- `javac` om te compileren; en 
- `java` om te runnen

uitvoeren. Een simpel voorbeeld:

```java
public class Main {

	public static void main(String[] args) {
		System.out.println("Hallo Wereld!");
	}
}
```

Dit programma heeft **geen package** en moet bewaard worden onder `Main.java` om de naam van de klasse te respecteren. Compileren en uitvoeren:

```
$ javac Main.java
$ java Main
Hallo Wereld
```

Het `javac` commando heeft vanuit `Main.java` een bytecode bestand aangemaakt genaamd `Main.class`, dat met `java` wordt uitgevoerd.

## Verschillende bestanden compileren

Stel dat onze `Main` klasse een `Student` instantie aanmaakt en daar de naam van afdrukt. De code wordt dus als volgt:

`Main.java`:

```java
import student.*;

public class Main {

	public static void main(String[] args) {
		var student = new Student("Jos");
		System.out.println("Hekyes " + student.getName());
	}
}
```

`Student.java`:

```java
package student;

public class Student {
	private String name;
	
	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
```

**LET OP**: de `Student` klasse leeft in package `student`---die op zijn beurt wordt geïmporteerd in `Main.java`. Dat betekent dat we `Student.java` moeten bewaren in de juiste subfolder ofwel package. Dit zou je moeten herkennen vanuit INF1, waar de structuur ook `src/main/java/[pkg1]/[pkg2]` is. We hebben dus nu twee bestanden:

```
oefening/
	Main.java
	student/
		Student.java
```

Alle files apart compileren levert `.class` files op in diezelfde folders. `java Main` zoekt dan nog steeds de `student/Student.class` file vanwege de import. Dit betekent dat je je programma moeilijker kan delen met anderen: er zijn nu twee bestanden én een subdirectory met juiste naamgeving vereist.

Gelukkig kan je met de juiste argumenten alle `.class` files in één keer genereren en die in een aparte folder---meestal genaamd `build`---plaatsen:

```
$ javac -d ./build *.java
$ cd build
$ ls
Main.class student
$ java Main
Heykes Jos
```

## Java programma's packagen

Omdat het vervelend is om verschillende bestanden te kopiëren naar andere computers worden Java programma's typisch verpakt in een `.jar` bestand: een veredelde `.zip` met metadata informatie zoals de auteur, de java versie die gebruikt werd om te compileren, welke klasse te starten (die de `main()` methode bevat), ... Indien deze metadata, in de `META-INF` subfolder, niet bestaat, worden defaults aangemaakt. Zie de [JDK Jar file specification](https://docs.oracle.com/javase/7/docs/technotes/guides/jar/jar.html) voor meer informatie. 

We gebruiken een derde commando, `jar`, om, na het compileren naar de `build` folder, alles te verpakkken in één kant-en-klaar programma:

```
$ cd build
$ jar cvf programma.jar *
added manifest
adding: Main.class(in = 957) (out= 527)(deflated 44%)
adding: student/(in = 0) (out= 0)(stored 0%)
adding: student/Student.class(in = 358) (out= 243)(deflated 32%)
$ ls
Main.class    programma.jar student
```

Nu kunnen we `programma.jar` makkelijk delen. De vraag is echter: hoe voeren we dit uit, ook met `java`? Ja, maar met de juiste parameters, want deze moet nu _IN_ het bestand gaan zoeken naar de juiste `.class` files om die bytecode uit te kunnen voeren:

```
$ java -cp "programma.jar;." Main
Heykes Jos
```

{{% notice warning %}}
Java classpath separators zijn [OS-specifiek](https://howtodoinjava.com/java/basics/java-classpath/)! Unix: `:` in plaats van Windows: `;`.
{{% /notice %}}

{{% notice note %}}
Die `;.` is nodig om aan te geven dat `java` binnenin de `.jar` file moet zoeken. Dit is enkel nodig als je Main klasse **niet** in een package zit (we hebben geen `package main` in onze `Main.java` file). Anders is de [fully qualified classname](https://docs.oracle.com/javase/specs/jls/se11/html/jls-6.html#jls-6.7) nodig als argument. <br/>Voor meer informatie over de `-cp` (classpath) parameter, zie de note in sectie [Dependency Management](/dependency-management). 
{{% /notice %}}

Vanaf nu kan je `programma.jar` ook uploaden naar een Maven repository of gebruiken als dependency in een ander project. Merk opnieuw op dat dit handmatig aanroepen van `javac` in de praktijk wordt overgelaten aan de gebruikte build tool---in ons geval, Gradle.

<!-- TODO voeg shadowjar toe -->

<!-- 
Inspect jar file
jar -tf app.jar

java -jar app.jar => no main manifest attribute, in app.jar

Inspect manifest (temp unzip)
unzip -q -c myarchive.jar META-INF/MANIFEST.MF

Run class inside jar (-cp staat voor classpath)
java -cp app.jar be.ses.higher_lower.App

`java -cp "gson-2.9.0.jar:." Main`
-->

<!-- TODO ### Virtual environment met Java
Compilen en linken in java versus C + anders want Java Virtual Machine => niet builden naar specifieke hardware
-->
