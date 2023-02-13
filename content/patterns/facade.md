---
title: '5.1 Facade'
---

&laquo;&nbsp;[Terug naar Software Engineering Skills](/)<br/>
&raquo;&nbsp;[Naar de labo opgave](#oef)

## _"Facade"_ - Design Pattern

Begeleidende screencast[^host]:

[^host]: Merk op dat de cursus nu wordt gehost op https://kuleuven-diepenbeek.github.io/ses-course/


{{< vimeo 398523177 >}}

### Doelstelling

* Scherm een complex stukje software af met behulp van een simpele interface
* Voorzie éénzelfde interface naar een set van verschillende mogelijke subsystemen. Een Facade is een high-level interface die mogelijks low-level systemen aanspreekt. 

[Dive Into Design Patterns: Facade](https://sourcemaking.com/design_patterns/facade)

### Voorbeeld

#### 1. Opzet

Stel dat we gegevens van de klant moeten versturen naar een overheidsinstantie. Die instantie beschikt jammer genoeg niet over voldoende budgetten om ook een cutting-edge server interface en implementatie aan te bieden. Het komt er op neer dat we verplicht zijn om tekst bestanden op een FTP server te plaatsen. 

<div class="devselect">

```kt
class ClientFtpSender {
    fun upload(client: Client) {
        // create ftp connection, upload, ...
    }
}
```

```java
public class ClientFtpSender {
    public void upload(Client client) {
        // create ftp connection, upload, ...
    }
}
```

</div>

Deze code gebruiken we als de gebruiker op een knop genaamd `export`
 klikt:

<div class="devselect">

```kt
class ClientHTTPHandler(val clientRepository: ClientRepository) {
    fun onExport(clientId: Int): HTTPResponse {
        val client = clientRepository.getById(clientId)
        ClientFtpSender().upload(client)
        return HTTPResponse.success()  // 200 OK
    }
}
```

```java
public class ClientHTTPHandler {
    private ClientRepository clientRepository;
    public HTTPResponse onExport(int clientId) {
        Client client = clientRepository.getById(clientId);
        new ClientFtpSender().upload(client);
        return HTTPResponse.success();  // 200 OK
    }
}
```

</div>

{{<mermaid>}}
graph LR;
    A[HTTP Handler]
    B[FTP Sender]
    A --> B
{{< /mermaid >}}

#### 2. Probleemstelling

We verkopen onze software aan een andere partij, die niet alleen met de overheid wenst te communiceren, maar ook met een derde instantie. Deze instantie biedt ons de mogelijkheid aan om de klant in de vorm van XML met een `POST` HTTPS call op te sturen. Onze `ClientFTPSender` is dus niet meer genoeg:

<div class="devselect">

```kt
class ClientPOSTSender {
    fun upload(client: Client) {
        // secure HTTPS, encode client in XML, post...
    }
}
```

```java
public class ClientPOSTSender {
    public void upload(Client client) {
        // secure HTTPS, encode client in XML, post...
    }
}
```

</div>

Deze complexe stukjes software, de `POST` en `FTP` senders, willen we niet langer rechtstreeks aanspreken in de HTTP handler. Het is zo dat afhankelijk van een bepaalde instelling, het ene of het andere gebruikt kan worden. 


#### 3. Oplossing

We hebben dus **een facade** nodig, die de juiste delegaties voor ons doorvoert, zoals in het volgende schema:

{{<mermaid>}}
graph LR;
    A[HTTP Handler]
    POST[POST Sender]
    FTP[FTP Sender]
    F{Facade}
    A --> F
    F -.-> POST
    F -.-> FTP
{{< /mermaid >}}


Waarbij de Facade een klasse is die de details "wegstopt" voor onze HTTP handler:

<div class="devselect">

```kt
class UploadClientFacade {
    fun upload(client: Client) {
        when {
            settings.isPOST() -> ClientPOSTSender().upload(client)
            settings.isSFTP() -> ClientFtpSender().upload(client)
            else -> throw UnsupportedOperationException("settings incorrect?")
        }
    }
}
```

```java
public class UploadClientFacade {
    public void upload(Client client) {
        if(settings.isPOST()) {
            new ClientPOSTSender().upload(client);
        } else if(settings.isFTP()) {
            new ClientFtpSender().upload(client);
        } else {
            throw new UnsupportedOperationException("settings incorrect?");
        }
    }
}
```
</div>

{{% notice note %}}
Merk op dat in Kotlin de `when { }` block een heel krachtige manier is om selecties te maken. `when` is een expressie, geen statement: dat betekent dat je toekenningen kan doen, zoals `val getal = when(someString) { "twee" -> 2 "drie" -> 3 else -> -1 }`. Zie de [control flow - when expression Kotlin docs](https://kotlinlang.org/docs/control-flow.html#if-expression) voor meer informatie. <br/>
Om te begrijpen wat er gebeurt in de JVM kan je de Kotlin-compiled bytecode inspecteren via menu _Tools - Kotlin - Show Kotlin Bytecode_. Dit wordt in bytecode nog steeds vertaald naar een "simpele(re)" sequentie van Java `if {}` statements. 
{{% /notice %}}

### Eigenschappen van dit patroon

* Een Facade is een _nieuwe interface_, niet eentje die oude interfaces herbruikt (Adapter). Beide zijn een soort van **"wrappers"**, die onderliggende implementaties verbergen voor de hogerliggende interface - in ons geval de `ClientHTTPHandler`.
* Het verschil tussen een Facade en een Factory is dat de facade alles verbergt en **logica uitvoert**, terwijl de Factory enkel de juiste instanties **aanmaakt** en teruggeeft. In dat geval zou de handler nog steeds `upload()` zelf moeten uitvoeren, inclusief eventuele encoding stappen.

## <a name="oef"></a>Labo oefeningen

Clone of fork <i class='fab fa-github'></i> GitHub project https://github.com/KULeuven-Diepenbeek/ses-patterns-facade-template

### Opgave 1

Opgave tekst: zie repository bestand `README.md`!

### Opgave 2

[sessy library](/extra/sessy): 

1. identificeer waar jij denkt dat een facade nodig zou kunnen zijn. Waar moet logica worden afgeschermd? 
2. Pas het patroon toe waar jij denkt dat het nodig is. 

## Denkvragen

* Op welk moment beslis je dat een Facade écht nodig is? Is het mogelijk om ook een facade te maken zonder bijvoorbeeld nieuwe dieren in oefening 1 of een nieuwe verzendmethode voor de klant bij de probleemstelling? 
* Kan een Facade een Facade verbergen? Wanneer is dat nodig, of niet?
