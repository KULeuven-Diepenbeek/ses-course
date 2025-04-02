---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
author: "Koen Yskout"
draft: false
---

## Volatile (1)

Waarom zou je niet elke variabele als `volatile` markeren?

{{% notice style=tip title=Antwoord expanded=false %}}
Dat zou als voordeel hebben dat je meer zichtbaarheisgaranties hebt, maar dit komt wel met een belangrijke kost: veel performantie-optimalisaties die gebruik maken van een lokale cache worden zo teniet gedaan, en je programma wordt dus trager.
Bovendien heb je met volatile variabelen nog steeds geen thread safety; je moet nog steeds synchronisatie toevoegen.
{{% /notice %}}



## Volatile (2)

We zagen in de [uitleg bij het synchroniseren met semaforen](_index.md#semafoor) dat deze ook zichbaarheidsgaranties bieden, net zoals `volatile`.
Is in onderstaand voorbeeld het `volatile` keyword dan nog nuttig? Waarom (niet)?

```java
import java.util.concurrent.Semaphore;

class Counter {
    private volatile int count = 0;
    private final Semaphore sem = new Semaphore(1);

    public synchronized void increment() {
        try {
            sem.acquire();
            count++;
            sem.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void decrement() {
        try {
            sem.acquire();
            count--;
            sem.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount() {
        return count;
    }
}
```

{{% notice style=tip title=Antwoord expanded=false %}}
Ja, volatile is nog steeds nuttig. Niet voor de increment- en decrement-methodes en alle andere code die daarvan gebruik maakt (de semafoor geeft dezelfde garanties als `volatile`), maar wel voor het gebruik van `count` in de `getCount`-methode.
Zonder `volatile` heb je geen garantie dat verschillende threads dezelfde/de meest recente waarde van de counter zouden uitlezen.
{{% /notice %}}

## Volatile (3)

Welke zichtbaarheidsgaranties krijg je bij een volatile variabele met een lijst als type, bijvoorbeeld `private volatile ArrayList<Element> myList`?

{{% notice style=tip title=Antwoord expanded=false %}}
De zichtbaarheidsgaranties gaan enkel over de _referentie_ (pointer) naar de lijst, die in variabele `myList` zit.
Met andere woorden: variabele `myList` wijzigen zodat die naar een _andere_ lijst verwijst (`myList = new ArrayList<>()`) zal steeds meteen zichtbaar zijn voor andere threads.
Maar: wijzigingen in de lijst zelf (bv. elementen toevoegen of van plaats veranderen) zijn **niet** automatisch zichtbaar voor andere threads.
{{% /notice %}}


## Synchronized

Waarom zou je niet gewoon elke methode van een klasse `synchronized` maken?

{{% notice style=tip title=Antwoord expanded=false %}}
Als je dat doet, verlies je mogelijk veel van de voordelen van multithreading.
Er kan immers op elk moment slechts één thread met je object werken; al de rest moet wachten, zelfs wanneer al die threads enkel lees-operaties zouden uitvoeren en er dus geen reden is om synchronisatie te gebruiken.
Bovendien volstaat dit niet om thread-safe te zijn (zie het voorbeeld met [het gebruik van de iterators](_index.md#concurrent-data-structures)).
{{% /notice %}}

## Counter + jcstress

Pas de jcstress-test uit [het voorbeeld](_index.md#anatomie-van-een-jcstress-test) aan om de thread-safe Counter-implementatie uit [oefening 2](#volatile-2) te testen.

## Ticketverkoop

Hieronder vind je een klasse voor een ticket-verkoop met bijhorende zitplaatsen (bijvoorbeeld van een bioscoopzaal, vliegtuig, ...).
Deze bevat ook een main-methode die deze klasse gebruikt.

De main-methode simuleert hoe het `BookingSystem` door meerdere klanten tegelijk gebruikt wordt.
We maken hierbij gebruik van een CyclicBarrier: een ander synchronisatie-primitief, waarmee je kan wachten tot een aantal threads hetzelfde punt bereikt hebben voor ze verder mogen gaan.
We gebruiken dit om de grote toestroom te simuleren bij het openen van de ticketverkoop, door elke thread (klant) te laten wachten tot ook alle andere threads (klanten) klaar staan.
Bekijk ook de andere concurrency-aspecten van deze code, zoals het gebruik van een thread pool en synchronized list.

_Opmerking: voor de eenvoud maakt het niet uit welke zitjes een klant krijgt._

1. Voer de huidige versie uit. Wat zie je?
2. Maak deze klasse thread-safe, zodat meerdere klanten tegelijk tickets kunnen boeken zonder dezelfde zitplaatsen toegewezen te krijgen.
   Doe dit op de simpelste manier die je kan bedenken (hint: _synchronized_). Wat is het effect op de uitvoeringstijd?
   (_De main-methode moet je niet aanpassen_)
3. Probeer daarna om de oplossing efficiënter (maar nog steeds thread-safe) te maken. (_De main-methode moet je niet aanpassen_)

```java
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class BookingSystem {

    private final String[] seats = {"A", "B", "C", "D", "E", "F"};
    private int availableTickets = seats.length;
    private final Map<String, String> seatAssignments = new HashMap<>();

    public boolean isAssigned(String seat) {
        return seatAssignments.containsKey(seat);
    }

    private void generateTicket(String seat, String customer) {
        // simulate that creating a ticket takes some time
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }

    public List<String> bookSeats(String customer, int requestedSeats) {
        if (requestedSeats > availableTickets)
            throw new IllegalStateException("Not enough seats available");

        // simulate that preprocessing takes a lot of time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        List<String> seatsForCustomer = new ArrayList<>();
        int seatIndex = 0;
        while (seatsForCustomer.size() < requestedSeats) {
            String seat = seats[seatIndex];
            if (!isAssigned(seat)) {
                generateTicket(seat, customer);
                seatAssignments.put(seat, customer);
                seatsForCustomer.add(seat);
            }
            seatIndex++;
        }
        availableTickets -= requestedSeats;
        return seatsForCustomer;
    }

    public static void main(String[] args) {
        var nbCustomers = 20;
        var bookingSystem = new BookingSystem();
        var assignedSeats = Collections.synchronizedList(new ArrayList<String>());
        try (var executor = Executors.newFixedThreadPool(nbCustomers)) {
            // wait until all customers are ready: simulates opening ticket sales
            var ticketSalesOpening = new CyclicBarrier(nbCustomers);
            for (int i = 0; i < nbCustomers; i++) {
                final var customer = "Customer %02d".formatted(i);
                executor.execute(() -> {
                    try {
                        ticketSalesOpening.await();
                        var seats = bookingSystem.bookSeats(customer, 1);
                        System.out.println(customer + ": " + seats);
                        assignedSeats.addAll(seats);
                    } catch (IllegalStateException e) {
                        // no tickets left for customer
                    } catch (BrokenBarrierException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        var uniqueSeats = new HashSet<>(assignedSeats);
        System.out.println((assignedSeats.size() - uniqueSeats.size()) + " overbookings.");
    }
}
```
## Thread-safe cache

Hieronder vind je een `Downloader`-klasse die een (in-memory) cache gebruikt.
Als de te downloaden URL nog niet in de cache zit, wordt die gedownload.
Anders wordt de inhoud uit de cache teruggegeven.

> In plaats van een echte URL te downloaden, wordt een download hier gesimuleerd door 1 seconde te wachten

```java
import java.util.HashMap;
import java.util.Map;

public class Downloader {

    private final Map<String, String> cache = new HashMap<>();

    public String get(String url) {
        if (!cache.containsKey(url)) {
            var contents = download(url);
            cache.put(url, contents);
        }
        return cache.get(url);
    }

    private static String download(String url) {
        try {
            System.out.println("Downloading " + url + " (" + Thread.currentThread() + ")");
            Thread.sleep(1000); // 1 seconde
            return "Contents of " + url;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

1. Wat kan er gebeuren er als twee threads dezelfde URL op hetzelfde moment willen opvragen uit eenzelfde `Downloader`-object?
2. Zou een `ConcurrentHashMap` gebruiken in plaats van een gewone `HashMap` dit probleem oplossen?
3. Maak de klasse thread-safe, maar nog steeds zo efficiënt mogelijk (zodat threads nooit onnodig moeten wachten).
4. Maak ook een `Client`-klasse met een `main`-methode, waarin 4 threads elk 100 keer een random URL opvragen (gebruik een thread pool).

    > In plaats van echte URL's kan je gewoon strings gebruiken.
    > Hieronder vind je een methode die een willekeurige String uit de lijst van url's teruggeeft:
    > 
    > ```java
    > public static final String[] urls = { "abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx", "yz" };
    > public static String randomURL() {
    >     return urls[new Random().nextInt(urls.length)];
    > }
    > ```


5. **Denkvraag**: wat zouden de gevolgen zijn van een _cleanup_-thread toe te voegen aan je Downloader-klasse, die elke 5 seconden de cache helemaal leegmaakt?
