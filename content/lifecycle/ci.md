---
title: '6.1 Continuous Integration & Deployment'
---

Begeleidende screencast:

{{< vimeo 400180594 >}}

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

In deze systemen zijn configureerbaar en beschikken over een UI, zoals bijvoorbeeld zichtbaar in de [TeamCity screencast](https://www.jetbrains.com/teamcity/documentation/). Betalende systemen zoals TeamCity en Bamboo zijn erg uitgebreid, maar wij gaan werken met CircleCI. CircleCI is eenvoudig configureerbaar door middel van een `.yml` bestand en het gratis plan integreert naadloos met GitHub. Merk op dat oplossingen zoals Travis en CircleCI betalende _Hosted Solutions_ zijn!

Nieuwere spelers zoals Dagger focussen niet enkel op container deployment maar ook op "pipelining as code". Dat betekent dat je _code_ in plaats van _configuratie_ schrijft, wat als bijkomend voordeel heeft dat deze config-as-code met compilatie sneller fouten rapporteert. Met Dagger schrijf je in Go, Python, TypeScript/JS, of interageer je rechtstreeks met de CLI, met voorkeur van Go: zie de [Dagger Quickstart](https://docs.dagger.io/648215/quickstart/) guide.

### 1.2. De flow van een CI server

De [SESsy library](/extra/sessy) git repository bevat een [.circleci/config.yml](https://raw.githubusercontent.com/KULeuven-Diepenbeek/sessylibrary/master/.circleci/config.yml) bestand dat de CircleCI server instrueert wat te doen op welk moment: (onderstaand voorbeeld is een vereenvoudigde versie, zie link voor het volledig config bestand)

```
version: 2

jobs:
  build:
    docker:
      - image: cimg/openjdk:17.0.5
    working_directory: ~/sessylib

    environment:
      # eventueel extra opties aan de JVM mee te geven
      JVM_OPTS: -Xmx3200m
    steps:
      - checkout
      - run: gradle dependencies
      - run: gradle test
```

Afhankelijk van het gebruik van containers (zoals hierboven) en de specifieke CI syntax zien deze config files er ongeveer hetzelfde uit. Onderstaande is een Travis voorbeeld dat ook terug te vinden is in de Sessylibrary repository, maar niet meer actief is wegens het wegvallen van de gratis `travis-ci.org` servers (merk op dat dit meer stappen uitvoert door de `shadowJar` gradle target en de Node `before_install` scripts, die in CircleCI extra `steps` punten zouden zijn):

```
language: java

jdk:
  - openjdk11

env:
  - NODE_VERSION="12.10.0"

notifications:
  email: false

before_install:
  - nvm install $NODE_VERSION
  - nvm use $NODE_VERSION
  - pushd frontend
  - npm install
  - mkdir dist
  - popd

install:
  - ./gradlew check
  - ./gradlew frontendSync --stacktrace
  - ./gradlew shadowJar
```

#### 1. Checkout Code

`git clone [repository]` (met de juiste branch). Klaar. Dit gaat vanzelf met de `- checkout` step.

#### 2. Setup Environment

Elke build op CircleCI begint met een _clean_ Docker image en gegeven basistools. Zie ook [CircleCI docs: language guides](https://circleci.com/docs/examples-and-guides-overview/). De preconfigured convenience image voor Java is de `cimg/openjdk` waarvan de tags terug te vinden zijn op https://hub.docker.com/r/cimg/openjdk. Als bepaalde versies van `node` of Java nodig zijn, deze eerst moeten worden geïnstalleerd: de `opendjk` image bevat reeds een preconfigured JDK omgeving. CircleCI voorziet openJDK versies---de Oracle versies moeten zelf worden afgehaald. Voor SESsy library is in principe ook `node` vereist die de frontend kan builden, dit laten we als een oefening voor de lezer. 

#### 3. Build Code

Gebruik simpelweg de [Gradle](/dependency-management/gradle) tasks.

In `steps` plaats je uit te voeren instructies. Als `gradle test` zou falen krijg je dit te zien in het CircleCI dashboard.

Merk op dat bij bovenstaande Travis config ook nog de volgende tasks worden uitgevoerd:

1. `check` - installeert de juiste Gradle versie, compileert en test.
2. `frontendSync` - build met `node` de frontend en kopiëert naar `src/main/resources`.

#### 4. Package Code & Upload Artifact

`./gradlew shadowJar` - package alles, inclusief dependencies, in één grote jarfile.

Zodra één stap mis gaat (zoals een falende test), worden volgende stappen niet uitgevoerd. Als alles goed gaat is de output van het builden de binary die we _het artifact_ noemen, die de huidige buildstamp draagt (meestal een datumcode).

Alle gebuilde artifacts kunnen daarna worden geupload naar een repository server, zoals de Central Maven Repository, of onze eigen artifact server, waar een historiek wordt bijgehouden. 

#### 5. Publish Results

Op hat dashboard van https://app.circleci.com/ kan je de gepubliceerde resultaten (live) raadplegen. De dashboard mechanics werken voor alle CI systemen ongeveer hetzelfde. Hieronder is zichtbaar hoe de sessylibrary repository wordt gebuild in de Travis CI:

![](/img/teaching/ses/travis.png)

## 2. Continuous Deployment (CD)

Automatisch code compileren, testen, en packagen, is slechts één stap in het continuous development proces. De volgende stap is deze package ook automatisch _deployen_, of _installeren_. Op deze manier staat er altijd de laatste versie op een interne development website, kunnen installaties automatisch worden uitgevoerd op bepaalde tijdstippen, ... 

Populaire CD systemen:

- [Octopus Deploy](https://octopus.com)
- [Codeship](https://codeship.com)
- Modules van moderne CI systemen zoals hier boven vermeld, bijvoorbeeld Dagger/Drone Deployment
- Eigen scripts gebaseerd op CI systemen
- Cloud deployment systemen (Amazon AWS, Heroku, Google Apps, ...)

De `shadowJar` task genereert één `jar` bestand die uitvoerbaar is (`java -jar [filename].jar`), en onze DropWizard server lokaal opstart op poort `8000`. Eender welke server kan dit programma dus op eenvoudige wijze uitvoeren. 

Automatisch packagen en installeren van programma's stopt hier niet. Een niveau hoger is het automatisch packagen en installeren van _hele omgevingen_: het virtualiseren van een server. Infrastructuur automatisatie is een vak apart, en wordt vaak uitgevoerd met behulp van tools als [Puppet](https://www.puppet.com/), [Docker](https://www.docker.com/), [Terraform](https://www.terraform.io/), [Ansible](https://www.ansible.com/) of [Chef](https://www.chef.io/). Dit valt buiten de scope van deze cursus. (Merk op dat CircleCI intern al gebruik maakt van Docker images om builds te triggeren!)

### 2.1. De flow van deployment en releases beheren

Nieuwe features in productie plaatsen brengt altijd een risico met zich mee. Het zou kunnen dat bepaalde nieuwe features bugs veroorzaken in de gekende oude features, of dat het (slecht getest) systeem helemaal niet werkt zoals men verwachtte. Om dat zo veel mogelijk te vermijden wordt er een release plan opgesteld waarin men aan '**smart routing**' doet. 

Stel, onze [SESsy bibliotheek webapplicatie](/extra/sessy) is toe aan vernieuwing. We wensen een nieuw scherm te ontwerpen om efficiënter te zoeken. We zijn echter niet zeker wat de gebruikers gaan vinden van deze nieuwe manier van werken, en beslissen daarom om slechts een _aantal gebruikers_ bloot te stellen aan deze radicale verandering. Dat kan op twee manieren:

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


## <a name="oef"></a>Labo oefeningen

### Opgave 1

Omwille van privacy instellingen tussen CI systemen en de KULeuven-Diepenbeek organisatie op Github, wordt voor deze opgave verwacht dat je een **nieuwe repository** zelf aanmaakt vanaf nul, via [Github.com](https://github.com). Geef de repository de naam `ses-ci-opgave`, zoals in onderstaande screenshot:

![](/img/teaching/ses/ci-newrepo.png)


Het is belangrijk dat de repository **public** is, zodat de CI server je project kan builden. CircleCI ondersteunt ook private repositories tegen betaling.

#### 1.1 De Calculator app

Ontwerp een command-line interface programma, getiteld 'calculator', dat twee argumenten inleest die terecht komen in de `main()` methode, en de sommatie ervan afdrukt:

`java -cp . Calculator 1 4` print `5` in `stdout`.

Uiteraard gebruiken we hier Gradle voor. Zet de berekening in een aparte methode, die ook _getest_ werd. Voorzie dus ook minstens 2 eenvoudige unit testen in `src/main/test` om te controleren of de CI server deze testen correct gaat uitvoeren. 

#### 1.2 De app builden op Travis

Nadat de code voor 1.1 werd gecommit en gepushed op je Git repository, is de volgende stap dit project automatisch te builden op een hosted CI server zoals CircleCI. Om dit te activeren moeten er twee stappen worden uitgevoerd:

1. Activeer je repository door op https://circleci.com/ in te loggen met je GitHub account, de repository terug te zoeken in de lijst door op `+` te drukken.
2. Voorzie een `config.yml` bestand in de `.circleci` subdirectory waarin staat beschreven op welke manier je project moet worden gebuild. Het is ook mogelijk om CircleCI deze file te laten genereren, maar **pas op met oude openjdk versies**! Kies voor een recente Docker image, zoals `cimg/openjdk:17.0.5`. Tip: Welke stappen denk je dat CircleCI zou moeten ondernemen op je project te builden? Is enkel builden voldoende? Wat als er een test faalt? 
3. Stel dat we de gebuilde jar ergens willen uploaden, hoe gaan we dan tewerk? Tip: Klik op de **Artifacts** tab in het dashboard of lees de docs: [storing artifacts - CircleCI](https://circleci.com/docs/artifacts/).
4. Op welke manier heb je vanuit het CircleCI dashboard toegang tot de gegenereerde HTML reports van Gradle waar van de testverslagen? Tip: kijk in de `build` folder en denk aan vraag 3.

Raadpleeg de documentatie om jezelf te bekwamen in de Yaml syntax (lees zeker de eerste twee links!):

- [QuickStart Guide - CircleCI](https://circleci.com/docs/getting-started/)
- [YAML Configuration Intro - CircleCI](https://circleci.com/docs/introduction-to-yaml-configurations/)
- [Examples and Guides Overview - CircleCI](https://circleci.com/docs/examples-and-guides-overview/)

### Opgave 2

Er duikt plots een onvoorziene periode van boekenschaarste op! Om niet te veel paniek te veroorzaken, willen we gebruik maken van blue/green releasing om het uitleensysteem van [SESsy library](/extra/sessy) gradueel te wijzigen. 

De klasse `BorrowBooksResource` is het _entry point_ voor alle calls naar `/borrow`, zoals de `@Path` annotatie aanduidt. Deze klasse zal voortaan moeten dienen als API gateway die gebruikers omleidt, ofwel naar het gebruikelijke uitleenproces, ofwel naar een methode die niet meer toestaat dat boeken worden uitgeleend, onafhankelijk van de ingelogde gebruiker of het type boek. 

Maak twee nieuwe klasses aan, `BorrowBooksOriginalResource` (1) met de originele `borrow()` implementatie, en `BorrowBooksNotAllowedResource` (2), die altijd een status `FORBIDDEN` teruggeeft. Kennis van patterns is een pluspunt hier. 

Denk na over hoe je de bestaande klasse `BorrowBooksResource` gaat aanpassen om bepaalde gebruikers naar ofwel (1) ofwel (2) te leiden. Je kan bijvoorbeeld een aantal boeken (op basis van ISBN) wel of niet toestaan, of gebaseerd op de sessie een aantal gebruikers wel of niet toestaan. Neem een kijkje in `request.getSession()`: wat is bruikbaar, en wat niet?

## Denkvragen

- Waarom is het belangrijk om gebuilde artifacts van de CI server bij te houden? 
- Wat zijn de voordelen van het werken met een CI en CD systeem, ten opzichte van alles met de hand (of met eigen gemaakte scripts) in te stellen? 
- Version control en continuous delivery zijn klassiek gezien vijanden van database migratie (of omgekeerd). Toch is het mogelijk om een database systeem ook automatisch te up- of downgraden, met bijvoorbeeld https://flywaydb.org. Hoe gaat zoiets in zijn werk?
- Zou het ook mogelijk zijn om een geautomatiseerde scenariotest uit te voeren in CircleCI? Zo ja, welke wijzigingen zou je moeten doorvoeren aan de `config.yml`?

## Extra Leermateriaal

- [Martin Fowler on Continuous Integration](https://martinfowler.com/articles/continuousIntegration.html)
- [Martin Fowler on Canary Releases](https://martinfowler.com/bliki/CanaryRelease.html)
- [Continuous Delivery: Reliable Software Releases through Build, Test, and Deployment Automation](https://www.amazon.com/dp/0321601912?tag=duckduckgo-d-20&linkCode=osi&th=1&psc=1)