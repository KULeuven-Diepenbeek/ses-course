---
title: 'Cmdline Java Compiling'
weight: 2
draft: false
author: Wouter Groeneveld en Arne Duyver
---

Dit is in principe iets wat je in het INF1 vak onbewust reeds uitvoerde door op de groene "Compile" knop te drukken van je NetBeans/IntelliJ IDE. Het is belangrijk om te weten welke principes hier achter zitten net als in C. Hieronder volgt dus een kort overzicht over het compileren van Java programma's _zonder_ een buildtool, later gaan we hier meestal een buildtool voor gebruiken om ons leven gemakkelijk te maken.

## Prerequisites

Zoals in [Java development environment in VSCode](/1-wsl-vscode/vscode.md#java-development-environment) kan zien heb je twee programma's nodig:
- de Java Runtime Environment (JRE) om het commando `java` te gebruiken om gecompileerde Java programma's te kunnen runnen.
- de Java Development Kit (JDK) om het commando `javac` te gebruiken om Java broncode (`.java`-files) te compileren (en verzamelen in een `jar`-file.)

### De Java Virtual Machine (JVM)
Waarom heb je nu toch een extra stukje software nodig om Java-applicaties te runnen? In C hebben we echter gezien dat we juist willen compileren naar een binary zodat we dit native kunnen uitvoeren. Java wil namelijk een oplossing bieden voor de 'flaw' in hoe C-programma's werken. Zoals daar aangehaald compileer je een C-programma naar een bepaalde architectuur, daarom kan je een C-programma dat gecompileerd is voor een `x86`-cpu niet runnen op een `arm`-cpu bijvoorbeeld. Java lost dit probleem op door te compileren naar een speciale bytecode, die dan door de **Java Virtual Machine (JVM)** kan worden uitgevoerd op de onderliggende architectuur. De JVM vormt dus een laag tussen je bytecode en de hardware. Op die manier moet de gebruiker enkel één programma specifiek voor zijn/haar architectuur downloaden (de JVM) en kunnen de Java-binaries hetzelfde blijven. Je hoeft dan als developer geen meerdere verschillende binaries meer te voorzien. TOP!

## Een minimaal programma compileren

```java
public class Main {

	public static void main(String[] args) {
		System.out.println("Hallo Wereld!");
	}
}
```

Dit programma heeft **geen package** en moet bewaard worden onder `Main.java` om de naam van de klasse te respecteren. Compileren en uitvoeren:

```bash
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

**LET OP**: de `Student` klasse leeft in package `student`---die op zijn beurt wordt geïmporteerd in `Main.java`. Dat betekent dat we `Student.java` moeten bewaren in de juiste subfolder ofwel package. Dit zou je moeten herkennen vanuit INF1, waar de structuur ook `src/main/java/[pkg1]/[pkg2]` is. We hebben nu dus twee bestanden:

```
java-met-cli/
├── Main.java
├── Makefile
└── student
    └── Student.java
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

We gebruiken een derde commando, `jar`, om, **na het compileren** naar de `build` folder, alles te verpakkken in één kant-en-klaar programma:

```
$ cd build
$ jar cvf programma.jar *
added manifest
adding: Main.class(in = 957) (out= 527)(deflated 44%)
adding: student/(in = 0) (out= 0)(stored 0%)
adding: student/Student.class(in = 358) (out= 243)(deflated 32%)
$ ls
Main.class programma.jar student
```

{{% notice info %}}
In het `jar cvf programma.jar *` commando staat:
- 'c' voor "create" (aanmaken van een nieuwe JAR-file)
- 'v' voor "verbose" (gedetailleerde uitvoer)
- 'f' voor "file" (de naam van de JAR-file die je wilt maken).
{{% /notice %}}

Nu kunnen we `programma.jar` makkelijk delen. De vraag is echter: hoe voeren we dit uit, ook met `java`? Ja, maar met de juiste parameters, want deze moet nu _IN_ het bestand gaan zoeken naar de juiste `.class` files om die bytecode uit te kunnen voeren: (In het onderstaande commando staat de `-cp`-flag voor Classpath. Hier geef je dus aan waar het java commando mag gaan zoeken naar `.class`-files naar de klasse die je wil uitvoeren)

```
$ java -cp "programma.jar" Main
Heykes Jos
```

{{% notice warning %}}
Java classpath separators zijn [OS-specifiek](https://howtodoinjava.com/java/basics/java-classpath/)! Unix: `:` in plaats van Windows: `;`.
{{% /notice %}}

<!-- {{% notice note %}}
Die `:.` is nodig om aan te geven dat `java` binnenin de `.jar` file moet zoeken. Dit is enkel nodig als je Main klasse **niet** in een package zit (we hebben geen `package main` in onze `Main.java` file). Anders is de [fully qualified classname](https://docs.oracle.com/javase/specs/jls/se11/html/jls-6.html#jls-6.7) nodig als argument. <br/>Voor meer informatie over de `-cp` (classpath) parameter, zie de note in sectie [Dependency Management](/dependency-management). 
{{% /notice %}} -->

Vanaf nu kan je `programma.jar` ook uploaden naar een Maven repository of gebruiken als dependency in een ander project. Merk opnieuw op dat dit handmatig aanroepen van `javac` in de praktijk wordt overgelaten aan de gebruikte build tool---in ons geval, gaan we dit eerst automatiseren met een Makefile.

### Jar files inspecteren
Mocht je `jar` ooit niet goed werken kan het handig zijn om te inspecteren wat er juist allemaal in de `jar`-package zit. Je kan hier het volgende commando voor gebruiken: `jar -tf naam.jar`

Voorbeeld:
```bash
arne@LT3210121:~/ses/java-met-cli/build$ jar -tf programma.jar
META-INF/
META-INF/MANIFEST.MF
Main.class
student/
student/Student.class
```

Nu je de structuur ziet kan je ook makkelijk de `main` methode oproepen van de `Student.class` in de `jar` _(als die methode zou bestaan)_ met `java -cp "programma.jar" student.Student`

### Main class instellen

Merk op dat als je de `jar` wil runnen zonder een specifieke klasse mee te geven dan ga je nu een error krijgen in de vorm van `no main manifest attribute, in programma.jar`. We kunnen er wel voor zorgen dat automatisch de klasse `Main` gebruikt wordt. Hiervoor moeten we het attribuut `Main-Class` in de **MANIFEST.MF** file de waarde van de klassenaam meegeven (eventueel met packages ervoor).

Met het volgende commando kunnen we inspecteren hoe die MANIFEST file er nu uit ziet. `unzip -q -c programma.jar META-INF/MANIFEST.MF`. Voorlopig bestaat dat attribuut dus nog niet.

Met `jar cvfe programma.jar Main *` waar de 'e' nu staat voor "entry point" (de Main-Class die je wilt specificeren).

Nu kan je je `jar` simpel runnen met `java -jar programma.jar`:

```bash
arne@LT3210121:~/ses/java-met-cli/build$ java -jar programma.jar
Hekyes Jos
```

## Oefening
Extract alle files in [dit zip bestand](/files/ses-monstergame-java-start.zip) naar een directory naar keuze  OF clone [de repository](https://github.com/ArneDuyver/ses-monstergame-java-start). Schrijf een simpele makefile dat de volgende dingen kan doen en plaats het in de root van je directory:
- `compile`: Compileert de bronbestanden naar de `/build`-directory
- `jar` : packaged alle klassen naar een jar genaamd 'app.jar' in de 'build'-directory met entrypoint de 'App'-klasse.
- `run` : Voert de jar file uit
- `clean`: Verwijdert de '.class'-bestanden en het '.jar'-bestand uit de 'build'-directory

<!-- EXSOL -->
<details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

```makefile
MAINCLASS = App
JAR = app.jar

compile:
	@echo "compiling ..."
	javac -d ./build *.java
	@echo "Done compiling."

clean:
	@echo "cleaning ..."
	-rm -R ./build/*
	@echo "Done cleaning."

run: 
	@echo "running program $(JAR) ...\n---------------"
	@cd ./build && java -cp "$(JAR)" $(MAINCLASS)
	@echo "---------------\nProgram exited."

jar:
	@echo "Packaging to jar ..."
	cd ./build && jar cvfe $(JAR) $(MAINCLASS) *
	@echo "Done packaging."
```

</p>
</details>