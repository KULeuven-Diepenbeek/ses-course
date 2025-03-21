---
title: "5.2 Generics"
toc: true
autonumbering: true
draft: false
---

{{% notice info "In andere programmeertalen" %}}
De concepten in andere programmeertalen die het dichtst aanleunen bij Java generics zijn
- templates in C++
- generic types in Python (as type hints)
- generics in C#
{{% /notice %}}

> [!todo]
> - [TODO: generische methodes](#generische-methodes)
> - [TODO: overerven van generisch type](#overerven-van-een-generisch-type)

In dit hoofdstuk behandelen we **generics**. Die worden veelvuldig gebruikt in datastructuren, en een goed begrip ervan is dan ook essentieel.
Je vindt alle startcode voor dit hoofdstuk [op deze GitHub-repository](https://github.com/KULeuven-Diepenbeek/ses-demos-exercises-student).

Generics zijn een manier om klassen en methodes te voorzien van type-parameters.
Bijvoorbeeld, neem de volgende klasse `ArrayList`[^1]:

[^1]: Deze klasse is geïnspireerd op de ArrayList-klasse die standaard in Java zit.

```java
class ArrayList {
  private Object[] elements;
  public void add(Object element) { /* ... */  }
  public Object get(int index) { /* ... */  }
}
```

Stel dat we deze klasse makkelijk willen kunnen herbruiken, telkens met een ander type van elementen in de lijst.
We kunnen nu nog niet zeggen wat het type wordt van die elementen.
Gaan er Student-objecten in de lijst terechtkomen? Of Animal-objecten?
Dat weten we nog niet.
We kiezen daarom voor `Object`, het meest algemene type in Java.

Maar dat betekent ook dat je nu objecten van verschillende, niet-gerelateerde types kan opnemen in één en dezelfde lijst, hoewel dat niet de bedoeling is!
Stel bijvoorbeeld dat je een lijst van studenten wil bijhouden, dan houdt de compiler je niet tegen om ook andere types van objecten toe te voegen:

```java
{
ArrayList students = new ArrayList();

Student student = new Student();
students.add(student);

Animal animal = new Animal();
students.add(animal); // <-- compiler vindt dit OK 🙁
}
```

Om dat tegen te gaan, zou je afzonderlijke klassen `ArrayListOfStudents`, `ArrayListOfAnimals`, ... kunnen maken, waar het bedoelde type van elementen wel duidelijk is, en ook wordt afgedwongen door de compiler.
Bijvoorbeeld:

```java
class ArrayListOfStudents {
  private Student[] elements;
  public void add(Student element) { /* ... */  }
  public Student get(int index) { /* ... */  }
}

class ArrayListOfAnimals {
  private Animal[] elements;
  public void add(Animal element) { /* ... */  }
  public Animal get(int index) { /* ... */  }
}
```

Met deze implementaties is het probleem hierboven opgelost:

```java
{
ArrayListOfStudents students = new ArrayListOfStudents();
students.add(student); // OK
students.add(animal);  // compiler error 👍
}
```

De prijs die we hiervoor betalen is echter dat we nu veel quasi-identieke implementaties moeten maken, die *enkel* verschillen in het type van hun elementen.
Dat leidt tot veel onnodige en ongewenste code-duplicatie.

Met generics kan je een _type_ gebruiken als parameter voor een klasse (of methode, zie later) om code-duplicatie zoals hierboven te vermijden.
Dat ziet er dan als volgt uit (we gaan zodadelijk verder in op de details):
```java
class ArrayList<T> { 
  private T[] elements;
  // ...
}
```
Generics geven je dus een combinatie van de beste eigenschappen van de twee opties die we overwogen hebben:

1. er moet slechts één implementatie gemaakt worden (zoals bij `ArrayList` hierboven), en
2. deze implementatie kan gebruikt worden om lijsten te maken waarbij het gegarandeerd is dat alle elementen een specifiek type hebben (zoals bij `ArrayListOfStudents`).

