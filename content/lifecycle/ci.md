---
title: 'Continuous Integration & Deployment'
draft: false
author: Wouter Groeneveld
---

<!-- Begeleidende screencast:

{{< vimeo 400180594 >}} -->

## 1. Continuous Integration (CI)

Het softwareontwikkel proces is een continu proces: als een eerste versie van het product klaar is, en wordt overgemaakt aan klanten, volgt het onderhoud en een mogelijke volgende versie. Elke wijziging maakt potentiëel dingen kapot (geminimaliseerd met [TDD](/tdd)), of introduceert nieuwe features. Dat betekent dat bij _elke wijziging_, een computer het hele build proces moet doorlopen om te controleren of er niets stuk is. Dit noemen we het "_integreren_" van nieuwe code, vandaar de naam.

### 1.1. De CI Server

In de praktijk worden aparte servers verantwoordelijk gesteld om regelmatig de hele codebase te downloaden, alles te compileren, en testen uit te voeren. Als iets niet compileert, of een test faalt, rapporteert dit systeem via diverse kanalen aan de ontwikkelaars. Niemand wilt dit in de achtergrond op zijn eigen machine een tweede build systeem geïnstalleerd hebben, die CPU kracht afsnoept van je eigen IDE. De volgende Figuur verduidelijkt de flow ([bron](https://devopscube.com/continuous-integration-delivery-deployment/)):

![](/img/teaching/ses/devops.jpg "Het Continuous Integration build systeem")

Dit hele proces verloopt volledig automatisch: niemand drukt op een "build" knop, maar door middel van ingecheckte code wijzigingen (1) start dit systeem. De CI server haalt wijzigingen op (2), build (3) en test (4) om te kunnen rapporteren of dit lukte of niet (5), via verschillende kanalen (6).

De Source Control server, zoals Github.com of een lokale `git` server, werd reeds besproken in labo '[versiebeheer](/versiebeheer)'. Er zijn voor Java verschillende populaire CI software systemen:

- [TeamCity](https://www.jetbrains.com/teamcity) van JetBrains, de makers van IntelliJ
- [Bamboo](https://www.atlassian.com/software/bamboo)---van Atlassian, de makers van Jira
- [Travis](https://www.travis-ci.com/)---de eerste CI die naadloos met GitHub integreerde, maar nu betalend is
- [CirlceCi](https://circleci.com/)---een populair hosted alternatief voor Travis
- [Jenkins](https://www.jenkins.io/)---een ondertussen oudere speler op de CI markt
- [Drone](https://www.drone.io/)---een nieuwe speler met focus op Docker module integratie
- [Dagger](https://dagger.io/)---een CI/CD engine die in containers runt en programmeerbaar in plaats van configureerbaar is

Deze systemen zijn configureerbaar en beschikken over een UI, zoals bijvoorbeeld zichtbaar in de [TeamCity screencast](https://www.jetbrains.com/teamcity/documentation/). Betalende systemen zoals TeamCity en Bamboo zijn erg uitgebreid, en er zijn ook een aantal gratis alternatieven zoals CircleCI. CircleCI is eenvoudig configureerbaar door middel van een `.yml` bestand en het gratis plan integreert naadloos met GitHub. Wij gaan echter rechtstreeks via Github Actions werken aangezien dit rechtstreeks via Github kan geconfigureerd worden en op zeer gelijkaardige manier werkt zoals bovenstaande services.
<!-- 
Nieuwere spelers zoals Dagger focussen niet enkel op container deployment maar ook op "pipelining as code". Dat betekent dat je _code_ in plaats van _configuratie_ schrijft, wat als bijkomend voordeel heeft dat deze config-as-code met compilatie sneller fouten rapporteert. Met Dagger schrijf je in Go, Python, TypeScript/JS, of interageer je rechtstreeks met de CLI, met voorkeur van Go: zie de [Dagger Quickstart](https://docs.dagger.io/648215/quickstart/) guide. -->

### 1.2. De flow van een CI server

Je Github Actions moeten gedefinieerd worden in de folder `.github/workflows` in een `.yml`-bestand. Github helpt je echter om snel veelgebruikte actions op te stellen op basis van je code in de repository. 

Via het `Actions` tabblad worden enkele voorstellen gedaan. Je kan gebruik maken van de Gradle test template. Je `.yml`-bestand wordt automatisch aangemaakt en zie er uit zoals op onderstaande figuut is weergegeven

![example.yml](/img/ci-cd/githubActionsCiGradleExample.png)

**Let op: de yml-syntax is zeer gevoelig aan het juiste gebruik van tabs en spaces (gelijkaardig aan python)**

#### 1. on

Onder de `on` sectie ga je definieren bij welke specifieke acties de onderstaande jobs uitevoerd moeten worden.

#### 2. Setup Environment

In de jobs sectie kunnen we dan verschillende jobs oplijsten. In het begin van elke job moeten we vermelden op welk besturingssysteem we onze job willen uitvoeren. (Bv. Windows, Mac, Linux). 

_Je kan jobs ook automatisch op meerdere besturingssystemen laten uitvoeren._

We moeten er natuurlijk ook voor zorgen dat alle applicaties aanwezig zijn om onze code te kunnen compileren en runnen.

#### 3. Build Code

Nu kunnen we simpelweg gebruik maken van de [Gradle](/dependency-management/gradle) tasks.

#### 4. Package Code & Upload Artifact
<!-- 
`./gradlew shadowJar` - package alles, inclusief dependencies, in één grote jarfile. -->

Zodra één stap mis gaat (zoals een falende test), worden volgende stappen niet uitgevoerd. Als alles goed gaat is de output van het builden de binary die we _het artifact_ noemen, die de huidige buildstamp draagt (meestal een datumcode).

Alle gebuilde artifacts kunnen daarna worden geupload naar een repository server, zoals de Central Maven Repository, of onze eigen artifact server, waar een historiek wordt bijgehouden. 

#### 5. Publish Results

In de README.md van je repository kan je een Github Badge plaatsen die de status van je action snel weergeeft. 

![Github badge](/img/ci-cd/githubBadge.png)

Je kan dit toevoegen via onderstaande code:
```markdown
![example workflow](https://github.com/USERNAME/REPONAME/actions/workflows/YMLFILENAME.yml/badge.svg)
```

## 2. Continuous Deployment (CD)

Automatisch code compileren, testen, en packagen, is slechts één stap in het continuous development proces. De volgende stap is deze package ook automatisch _deployen_, of _installeren_. Op deze manier staat er altijd de laatste versie op een interne development website, kunnen installaties automatisch worden uitgevoerd op bepaalde tijdstippen, ... 

Populaire CD systemen:

- [Octopus Deploy](https://octopus.com)
- [Codeship](https://codeship.com)
- Modules van moderne CI systemen zoals hier boven vermeld, bijvoorbeeld Dagger/Drone Deployment
- Eigen scripts gebaseerd op CI systemen
- Cloud deployment systemen (Amazon AWS, Heroku, Google Apps, ...)

<!-- De `shadowJar` task genereert één `jar` bestand die uitvoerbaar is (`java -jar [filename].jar`), en onze DropWizard server lokaal opstart op poort `8000`. Eender welke server kan dit programma dus op eenvoudige wijze uitvoeren.  -->

Automatisch packagen en installeren van programma's stopt hier niet. Een niveau hoger is het automatisch packagen en installeren van _hele omgevingen_: het virtualiseren van een server. Infrastructuur automatisatie is een vak apart, en wordt vaak uitgevoerd met behulp van tools als [Puppet](https://www.puppet.com/), [Docker](https://www.docker.com/), [Terraform](https://www.terraform.io/), [Ansible](https://www.ansible.com/) of [Chef](https://www.chef.io/). Dit valt buiten de scope van deze cursus.

### 2.1. De flow van deployment en releases beheren

Nieuwe features in productie plaatsen brengt altijd een risico met zich mee. Het zou kunnen dat bepaalde nieuwe features bugs veroorzaken in de gekende oude features, of dat het (slecht getest) systeem helemaal niet werkt zoals men verwachtte. Om dat zo veel mogelijk te vermijden wordt er een release plan opgesteld waarin men aan '**smart routing**' doet. 

Stel, onze bibliotheek-website is toe aan vernieuwing. We wensen een nieuw scherm te ontwerpen om efficiënter te zoeken. We zijn echter niet zeker wat de gebruikers gaan vinden van deze nieuwe manier van werken, en beslissen daarom om slechts een _aantal gebruikers_ bloot te stellen aan deze radicale verandering. Dat kan op twee manieren:

1. _Blue/green releasing_: Een 'harde switch' in het systeem bepaalt welke personen (bijvoorbeeld op regio of IP) naar versie van het zoekscherm worden begeleid. 
2. _Canary releasing_: Een load balancer verdeelt het netwerkverkeer over verschillende servers, waarvan op één server de nieuwe versie is geïnstalleerd. In het begin gaat `90%` van de bezoekers nog steeds naar de oude versie. Dit kan gradueel worden verhoogd, totdat de oude server wordt uitgeschakeld. 

Onderstaand schema verduidelijkt dit idee ([bron](https://blog.getambassador.io/deploy-and-release-decouple-for-speed-and-safety-a8c99a9b4d7b)).

![](/img/teaching/ses/releasing.png)
  

De juiste logging en monitoring tools zorgen ervoor dat we een idee krijgen over het gebruik van het nieuwe scherm (groen, versie B). Gaat alles zoals verwacht, dan wordt de switch weggehaald in geval (1), of wordt de loadbalancer ge-herconfigureerd zodat het hele verkeer naar de nieuwe site gaat in geval (2). Ook deze aanpassingen zijn volledig geautomatiseerd. Na verloop van tijd valt de oude versie (blauw, versie A) volledig weg, en kunnen we ons concentreren op de volgende uitbreidingen. 

Het groene vlak, de 'Ambassador/API gateway', kan aanzien worden als:

- Een fysiek aparte machine, zoals een loadbalancer. 
- Een publieke API, die de juiste redirects verzorgt.
- Een switch in de code, die binnen dezelfde toepassing naar scherm 1 of 2 verwijst. 

Versie A en B hoeven dus niet noodzakelijk aparte versies van applicaties te zijn: dit kan binnen dezelfde applicatie, softwarematig dus, worden ingebouwd. 


## Opgaven

### Opgave 1
Ontwerp een 'calculator' applicatie die 2 getallen kan optellen, aftrekken, het product en het quotient (zonder rest) kan berekenen. (gebruik IntelliJ en Gradle)
1. Schrijf een aantal testen voor deze applicatie. 
2. Maak een github repository aan en gebruik Github Actions om je applicatie automatisch te laten testen bij een push naar de main branch.  

### Opgave 2
Gebruik een andere `workflow.yml` om je repo uit opgave 1 te beveiligen tegen pull-requests. Zorg ervoor dat eerst je testen moeten slagen en je applicatie gebuild kan worden voordat je een pull request kan toelaten. (Test dit ook uit met een medestudent).

Kijk in Github bij de `settings van je repo -> branches -> require status checks`

**Let op: je repo moet hiervoor public zijn. Je kan zoeken op status checks via de `job` names in je `.yml` action files**

### Opgave 3

Neem je JavaFX darts applicatie van het applicatie-college over dependecy management. Voeg de moeglijkheid toe om in te loggen.

Simuleer nu Continuous Development door de achtergrond kleur groen te maken voor alle gebruikers, maar rood voor alle gebruikers wiens gebruikersnaam start met de letter 'A'. 



<!-- ## Denkvragen

- Waarom is het belangrijk om gebuilde artifacts van de CI server bij te houden? 
- Wat zijn de voordelen van het werken met een CI en CD systeem, ten opzichte van alles met de hand (of met eigen gemaakte scripts) in te stellen? 
- Version control en continuous delivery zijn klassiek gezien vijanden van database migratie (of omgekeerd). Toch is het mogelijk om een database systeem ook automatisch te up- of downgraden, met bijvoorbeeld https://flywaydb.org. Hoe gaat zoiets in zijn werk?
- Zou het ook mogelijk zijn om een geautomatiseerde scenariotest uit te voeren in CircleCI? Zo ja, welke wijzigingen zou je moeten doorvoeren aan de `config.yml`? -->

## Extra Leermateriaal

- [Martin Fowler on Continuous Integration](https://martinfowler.com/articles/continuousIntegration.html)
- [Martin Fowler on Canary Releases](https://martinfowler.com/bliki/CanaryRelease.html)
- [Continuous Delivery: Reliable Software Releases through Build, Test, and Deployment Automation](https://www.amazon.com/dp/0321601912?tag=duckduckgo-d-20&linkCode=osi&th=1&psc=1)