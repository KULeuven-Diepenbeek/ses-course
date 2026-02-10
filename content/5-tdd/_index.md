---
title: '5. Test Driven Development'
weight: 50
author: Wouter Groeneveld en Arne Duyver
draft: true
---

> <i class="fa fa-question-circle" aria-hidden="true"></i>
 Wat is de beste manier om het aantal bugs in code te reduceren?

## Test-Driven Development

TDD (Test-Driven Development) is een hulpmiddel bij softwareontwikkeling om minder fouten te maken en sneller fouten te vinden, door éérst een test te schrijven en dan pas de implementatie. Die (unit) test zal dus eerst **falen** (ROOD), want er is nog helemaal geen code, en na de correcte implementatie uiteindelijk **slagen** (GROEN). 

{{<mermaid>}}
graph LR;
    T{"Write FAILING<br/> test"}
    D{"Write<br/> IMPLEMENTATION"}
    C{"Run test<br/> SUCCEEDS"}
    S["Start Hier"]
    S --> T
    T --> D
    D --> C
    C --> T
{{< /mermaid >}}

Testen worden opgenomen in een build omgeving, waardoor alle testen automatisch worden gecontroleerd bij bijvoorbeeld het compileren, starten, of packagen van de applicatie. Op deze manier krijgt men **onmiddellijk feedback** van modules die door bepaalde wijzigingen niet meer werken zoals beschreven in de test. 

### Soorten van Testen

Er zijn drie grote types van testen:

![](/img/testniveaus.jpg "De drie soorten van testen.")

#### 1. Unit Testing (GROEN)

Een unit test test zaken op _individueel niveau_, per klasse dus. De meeste testen zijn unit testen. Hoe kleiner het blokje op bovenstaande figuur, hoe beter de **F.I.R.S.T. principes** kunnen nageleefd worden. Immers, hoe meer systemen opgezet moeten worden voordat het assertion framework zijn ding kan doen, hoe meer tijd verloren, en hoe meer tijd de test in totaal nodig heeft om zijn resultaat te verwerken. 

#### 2. Integration Testing (ORANJE)

Een integratie test test het _integratie_ pad tussen twee verschillende klasses. Hier ligt de nadruk op _interactie_ in plaats van op individuele functionaliteit, zoals bij de unit test. We willen bijvoorbeeld controleren of een bepaalde service wel iets wegschrijft naar de database, maar het schrijven zelf is op unit niveau bij de database reeds getest. Waar wij nu interesse in hebben, is de interactie tussen service en database, niet de functionaliteit van de database. 

Typische eigenschappen van integration testen:

- Test geïntegreerd met externen. (db, webservice, ...)
- Test integratie twee verschillende lagen.
- Trager dan unit tests.
- Minder test cases.

#### 3. End-To-End Testing (ROOD)

Een laatste groep van testen genaamd _end-to-end_ testen, ofwel **scenario testen**, testen de héle applicatie, van UI tot DB. Voor een GUI applicatie bijvoorbeeld betekent dit het simuleren van de acties van de gebruiker, door op knoppen te klikken en te navigeren doorheen de applicatie, waarbij bepaalde verwachtingen worden afgetoetst. Bijvoorbeeld, klik op 'voeg toe aan winkelmandje', ga naar 'winkelmandje', controleer of het item effectief is toegevoegd.

Typische eigenschappen van end-to-end testen:

- Test hele applicatie!
- Niet alle limieten.
- Traag, moeilijker onderhoudbaar.
- Test integratie van alle lagen.

![](/img/tdd/selenium.png)

In plaats van dit in (Java) code te schrijven, is het ook mogelijk om de [Selenium IDE](https://selenium.dev/selenium-ide/) extentie voor Google Chrome of [Mozilla Firefox](https://addons.mozilla.org/en-US/firefox/addon/selenium-ide/) te gebruiken. Deze browser extentie laat recorden in de browser zelf toe, en vergemakkelijkt het gebruik (er is geen nood meer aan het vanbuiten kennen van zulke commando's). Dit wordt in de praktijk vaak gebruikt door software analisten of testers die niet de technische kennis hebben om te programmeren, maar toch deel zijn van het ontwikkelteam. 

Recente versies van de Selenium IDE plugin bewaren scenario's in `.side` bestanden, wat een JSON-notatie is. Oudere versies bewaren commando's in het `.html` formaat. deze bestanden bevatten een lijst van je opgenomen records:

```
  "tests": [{
    "id": "73bc78d5-1ca2-44c4-9ad2-6ccfe7cba5fe",
    "name": "bla",
    "commands": [{
      "id": "f192c93d-064a-4298-8d84-a44fd617622b",
      "comment": "",
      "command": "open",
      "target": "/mattersof/workshops",
      "targets": [],
      "value": ""
    }, {
      "id": "b94bd35b-0fc0-4ede-9b47-7e24d78126e8",
      "comment": "",
      "command": "setWindowSize",
      "target": "1365x691",
      "targets": [],
      "value": ""
    }, {
        ...
```

### Opgave rond end-to-end testen

Gebruik Selenium IDE om een test scenario op te nemen van het volgende scenarios op de website [https://www.saucedemo.com/](https://www.saucedemo.com/). :

1. Log in met "locked_out_user" en wachtwoord "secret_sauce" en verifieer dat je een error boodschap krijgt. 
2. Log in met "standard_user" en wachtwoord "secret_sauce", klik op het eerste item, voeg toe aan je winkelmandje, ga naar je winkelmandje. Verifieer dat er een product inzit.
3. Log in met "standard_user" en wachtwoord "secret_sauce" en test of de afbeeldingen van de producten verschillend zijn.
4. Log in met "problem_user" en wachtwoord "secret_sauce" en test of de afbeeldingen van de producten verschillend zijn. (Deze test moet nu falen omdat je je voordoet als een user die een bug ervaart.)  

Je zal voor deze opgave dus de Selenium (Chromium/Firefox) plugin moeten installeren: zie hierboven.

{{% notice important %}}
**In de volgende delen wordt er dieper ingegaan op de verschillende concepten die komen kijken bij Test Driven Development. We gaan de theoretische concepten echter enkel aanhalen in de tekst rond TDD in Java, maar die zijn natuurlijk wel van toepassing op alle talen zoals Python en C. We halen later bij de pagina's rond TDD in Python en C die theoretische kant niet zo zwaar meer boven en houden ons daar meer bezig met de praktische kant.**
{{% /notice %}}

