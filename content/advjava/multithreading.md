---
title: "5.4 Multithreading en concurrency"
toc: true
autonumbering: true
author: "Koen Yskout"
draft: true
---

## Wat en waarom?

We maken eerst onderscheid tussen de begrippen parallellisme en concurrency.

Bij **parallellisme** (soms ook multiprocessing genoemd) worden meerdere instructies gelijktijdig uitgevoerd.
Dit vereist meerdere eenheden (machines, CPU's, CPU cores, ...) waarop die instructies uitgevoerd worden.
Parallellisme wordt interessant wanneer er veel instructies uitgevoerd moeten worden die onafhankelijk van elkaar kunnen gebeuren.
Wanneer er afhankelijkheden zijn, moet er gecoördineerd worden, wat leidt tot meer overhead.

<img src="/img/parallel.png" alt="drawing" style="max-width: 500px;"/>

Bij **concurrency** (soms ook multitasking genoemd) zijn meerdere taken actief op een bepaald moment.
Dat betekent dat een tweede taak al kan starten voor de eerste afgelopen is.
Het hoeft echter niet zo te zijn dat er ook gelijktijdig instructies voor elk van die taken uitgevoerd worden.
Concurrency impliceert dus geen parallelisme: je kan concurrency hebben met slechts 1 single-core CPU.
Er zijn meerdere mogelijkheden om concurrency te bereiken met slechts één CPU:

- **pre-emption**: een externe scheduler (bijvoorbeeld in het besturingssysteem) beslist wanneer en voor hoelang een bepaalde taak de processor ter beschikking krijgt. Een voorbeeld hiervan is **time slicing**: het besturingssysteem onderbreekt elke taak na een bepaalde vaste tijd (of aantal instructies), om de controle vervolgens aan een andere taak te geven.
- **coöperative multitasking**: een taak beslist zelf wanneer die de controle teruggeeft, bijvoorbeeld wanneer er gewacht moet worden op een 'trage' operatie zoals het lezen van een bestand, het ontvangen van een inkomend netwerkpakket, .... Veel programmeertalen (maar niet Java) implementeren coöperatieve multitasking via coroutines en _async/await_ keywords.

<img src="/img/concurrent.png" alt="drawing" style="max-width: 600px;"/>

Het spreekt voor zich dat, op moderne (multi-core) machines, concurrency en parallelism vaak gecombineerd worden. Er zijn dus meerdere taken actief, waarvan sommige ook tegelijkertijd uitgevoerd worden.

**Multithreading** tenslotte is een specifiek concurrency-mechanisme: er worden, binnen eenzelfde proces van het besturingssysteem, meerdere 'threads' gestart om taken uit te voeren.
Deze threads delen hetzelfde geheugen (namelijk dat van het proces), en kunnen daardoor dus efficiënter data uitwisselen dan wanneer elke taak als apart proces gestart zou worden.
Op elk moment kunnen er dus meerdere threads bestaan, die (afhankelijk van of er ook parallellisme is in het systeem) al dan niet gelijktijdig instructies uitvoeren.
Binnen een Java-programma is multithreading de voornaamste manier om concurrency te bereiken.

## IO-bound vs CPU-bound tasks

De taken die al dan niet gelijktijdig uitgevoerd moeten worden, kunnen onderverdeeld worden in zogenaamde CPU-bound en IO-bound taken.

Bij een **CPU-bound** taak wordt de tijd voornamelijk gedomineerd door de uitvoering van instructies (berekeningen), bijvoorbeeld algoritmes, simulaties, ....
Hoe sneller de CPU, hoe sneller de taak dus afgewerkt zal zijn.

Een **IO-bound** task daarentegen is vaak aan het wachten op externe gebeurtenissen (interrupts), zoals netwerk- of schijf-toegang, timers, gebruikersinvoer, ...
Een snellere CPU zal deze taak niet sneller doen gaan.

In het algemeen is parallellisme vooral nuttig wanneer er veel CPU-bound taken zijn.

## Threads in Java

De basisklasse in Java om te werken met multithreading is de [`Thread`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html)-klasse.
Typisch geef je een `Runnable` mee die uitgevoerd moet worden.
Dat kan een object zijn, een lambda, of een method reference.
Het aanmaken van een thread start deze niet automatisch; om de uitvoering van de thread te starten, moet je de `start`-methode oproepen.
Als je wil wachten tot de thread afgelopen is, kan dat via de `join`-methode. Deze blokkeert de uitvoering tot de uitvoering van de thread afloopt.
Je kan een timeout meegeven aan join, met de maximale tijdsduur dat je wilt wachten. Als deze tijd voorbij is vooraleer de thread afloopt, wordt een InterruptedException gegooid.

```java
var t1 = new Thread(() -> {
    System.out.println("Hello from a new thread");
});
t1.start();
...
t1.join(Duration.ofSeconds(2));
```

Een standaard Java thread wordt uitgevoerd met een thread van het besturingssysteem (een _platform thread_, die typisch overeenkomt met een kernel thread).
Zo krijg je dus, op machines die het ondersteunen, ook automatisch parallellisme in Java: meerdere threads kunnen effectief tegelijkertijd instructies uitvoeren.
Een nadeel hiervan is dat het aantal threads beperkt is: elke platform-thread komt met een zekere overhead.

Sinds Java 21 beschikt Java ook over **virtual threads**.
Dat zijn threads die beheerd worden door de Java Virtual Machine (JVM) zelf, en dus niet 1-1 overeenkomen met een platform thread.
Je JVM zal een virtuele thread koppelen aan een platform thread, en deze terug ontkoppelen op het moment dat de virtuele thread moet wachten op een trage operatie.
De platform thread is dan terug vrij, en kan gebruikt worden om een andere virtuele thread uit te voeren.
Virtual threads zijn het Java-alternatief voor co-operative multitasking.
Het teruggeven van de controle wordt echter afgehandeld door de onderliggende bibliotheken, dus als programmeur word je niet geconfronteerd met wachten op blokkerende operaties, of async/await keywords.
Virtuele threads hebben weinig overhead; je kan er bijvoorbeeld makkelijk miljoenen van aanmaken.

## Synchronisatie

Wanneer meerdere threads (of processen) met gedeelde data werken, ontstaat er een nood aan **synchronisatie**.
Het kan immers zijn dat beide threads dezelfde data lezen of aanpassen, waardoor de data inconsistent wordt.
Dat is een **race conditie**.
Neem het voorbeeld hieronder: één thread verhoogt de waarde van een teller 10000 keer, de andere verlaagt die 10000 keer.
Welke waarde van de teller verwacht je op het einde van de code?

```java
class Counter {
  private int count = 0;
  public void increment() { count++; }
  public void decrement() { count--; }
  public int getCount() { return count; }
}

var counter = new Counter();

var inc = new Thread(() -> {
  for (int i = 0; i < 10000; i++) counter.increment();
});
var dec = new Thread(() -> {
  for (int i = 0; i < 10000; i++) counter.decrement();
});

inc.start(); dec.start();
inc.join(); dec.join();

System.out.println(counter.getCount());
```

Op mijn machine gaven drie uitvoeringen van dit programma achtereenvolgens de waarden -803, -5134, en 3041.
Dat komt omdat het lezen en terug wegschrijven van de (verhoogde of verlaagde) variabele (i.e., `count++` en `count--`) twee afzonderlijke operaties zijn,
die door het onderlinge verschil in timing tussen de threads op verschillende momenten kunnen plaatsvinden.
Bijvoorbeeld, stel dat count=40; de `inc`-thread verhoogt de teller 2 keer, en de `dec`-thread verlaagt de teller 1 keer,
in onderstaande volgorde:

<div style="max-width: 500px">

| Thread `inc`   | Thread `dec`    |
| -------------- | --------------- |
| lees count=40  |                 |
|                | lees count = 40 |
| zet count=41   |                 |
| lees count=41  |                 |
| zet count = 42 |                 |
|                | zet count = 39  |

</div>

De teller is nu niet 41 (wat de correcte waarde zou zijn na 2 verhogingen en 1 verlaging), maar 39.
Door andere volgordes (_interleavings_) van beide threads kan je dus verschillende resultaten krijgen.

### Synchronizatie-primitieven

Java biedt enkele synchronizatie-primitieven aan in de packages [`java.util.concurrent`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/package-summary.html) en [`java.util.concurrent.locks`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/locks/package-summary.html), bijvoorbeeld:

- een [**Semaphore**]() deelt een gegeven maximum aantal toestemmingen uit aan threads (via `acquire()`), en alle volgende threads die toestemming willen moeten wachten tot een van de vorige toestemmingen terug ingeleverd worden (via `release()`). Een binaire semafoor (met maximum 1 toestemming) kan dienen als _mutual exclusion lock_ (_mutex_). Een semafoor houdt enkel een teller bij van het resterend aantal toestemmingen, en niet welke thread al toestemming heeft. Er is dan ook geen enkele garantie of verificatie dat een thread die `release()` oproept eerder al een toestemming verkregen heeft.

- de **Lock** interface, en de implementatie **ReentrantLock**, waarbij een thread de lock kan verkrijgen (`lock()`) en terug vrijgeven (`unlock()`). Hierbij wordt nagegaan dat enkel de thread die de lock verkregen heeft, de lock terug kan vrijgeven. 'Re-entrant' betekent dat een thread die reeds een lock heeft, verder mag gaan wanneer die een tweede keer `lock()` oproept (op hetzelfde lock-object).

```java
class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        try {
            lock.lock();
            count++;
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        try {
            lock.lock();
            count--;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
```

### Synchronized

### Deadlocks

### Immutability

Immutable = immuun voor race conditions
Te verkiezen boven synchronisatie

Functioneel programmeren

## Concurrent data structures

Niet elke datastructuur kan gebruikt worden door meerdere threads tegelijk.

## High-level concurrency abstractions

ThreadPool, Executors, ...

CompletableFuture

## Parallel
