---
title: "Oefeningen"
toc: true
weight: 20
autonumbering: true
author: "Koen Yskout"
draft: false
---

## Volatile

Is het `volatile` keyword nog nodig in de implementaties van Counter met een semafoor (of lock), zoals hier?
Waarom wel/niet?

```java
class Counter {
    private volatile int count = 0;
    private final Semaphore sem = new Semaphore(1);

    public void increment() throws InterruptedException {
        sem.acquire();
        try {
            count++;
        } finally {
            sem.release();
        }
    }

    public void decrement() throws InterruptedException {
        sem.acquire();
        try {
            count--;
        } finally {
            sem.release();
        }
    }

    public int getCount() {
        return count;
    }
}
```

{{% notice style=tip title=Antwoord expanded=false %}}
Ja, volatile is nog steeds nodig. Niet voor de increment- en decrement-methodes en alle andere code die daarvan gebruik maakt (de semafoor geeft dezelfde garanties als `volatile`), maar wel voor het gebruik van `count` in de `getCount`-methode.
Zonder `volatile` heb je geen garantie dat meerdere threads consistente waarden van de counter zouden uitlezen.
{{% /notice %}}

## Synchronized

Waarom zou je niet gewoon elke methode van een klasse `synchronized` maken?

{{% notice style=tip title=Antwoord expanded=false %}}
Als je dat doet, verlies je mogelijk veel van de voordelen van multithreading.
Er kan immers op elk moment slechts één thread met je object werken; al de rest moet wachten, zelfs wanneer al die threads enkel lees-operaties zouden uitvoeren en er dus geen reden is om synchronisatie te gebruiken.
Bovendien volstaat dit niet om thread-safe te zijn (zie het voorbeeld met [het gebruik van de iterators](_index.md#concurrent-data-structures)).
{{% /notice %}}

## Ticketverkoop

Maak volgende klasse voor het boeken van tickets thread-safe, zodat meerdere klanten tegelijk tickets kunnen boeken.
Je vindt hieronder ook een main-methode die deze klasse gebruikt.
Doe eerst het simpelste wat je kan bedenken, en kijk wat het effect is op de uitvoeringstijd.
Probeer daarna om de oplossing efficiënter te maken.

```java
public class BookingSystem {

    private final String[] seats = {"A", "B", "C", "D", "E", "F"};
    private int availableTickets = seats.length;
    private final Map<String, String> seatAssignments = new HashMap<>();

    public boolean isAssigned(String seat) {
        return seatAssignments.containsKey(seat);
    }

    private void assignSeat(String seat, String customer) {
        // simulate that creating a ticket takes some time
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        seatAssignments.put(seat, customer);
    }

    public List<String> bookSeats(String customer, int requestedSeats) {
        if (requestedSeats > availableTickets) throw new IllegalStateException("Not enough seats available");
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
                assignSeat(seat, customer);
                seatsForCustomer.add(seat);
            }
            seatIndex++;
        }
        availableTickets -= requestedSeats;
        return seatsForCustomer;
    }
}
```

Hieronder vind je een main-methode die deze klasse gebruikt.
We maken hier gebruik van een CyclicBarrier: een ander synchronisatie-primitief, waarmee je kan wachten tot een aantal threads hetzelfde punt bereikt hebben voor ze verder mogen gaan.
We gebruiken dit om de grote toestroom te simuleren bij het openen van de ticketverkoop.
Bekijk ook de andere concurrency-aspecten van deze code.

```java
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
```

## Thread-safe cache

Maak een thread-safe `Downloader`-klasse die intern een (in-memory) cache gebruikt.
Als de te downloaden URL nog niet in de cache zit, wordt die gedownload.
Anders wordt de inhoud uit de cache teruggegeven.
In plaats van een echte URL te downloaden kan je gewoon `Thread.sleep` gebruiken:

```java
public static String download(String url) {
    try {
        System.out.println("Downloading " + url + " (" + Thread.currentThread() + ")");
        Thread.sleep(1000); // 1 seconde
        return "Contents of " + url;
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}
```

Maak ook een client met 4 threads die 100 keer een random URL opvragen (gebruik een thread pool).
In plaats van echte URL's kan je gewoon strings gebruiken:

```java
public static final String[] urls = { "abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwx", "yz" };
public static String randomURL() {
    return urls[new Random().nextInt(urls.length)];
}
```

Zorg ervoor dat dezelfde URL nooit twee keer gedownload wordt door twee verschillende threads.

Hoelang verwacht je dat dit zal duren? Klopt dat met wat er gebeurt?

**Uitbreiding**: voeg een cleanup-thread toe aan je Downloader-klasse. Deze maakt elke 5 seconden de cache helemaal leeg. Gebruik hiervoor een `ScheduledExecutor`. Opgelet: deze thread kan er mogelijk voor zorgen dat je programma niet meer stopt - zorg dat je de cleanup-thread ook beëindigt.
