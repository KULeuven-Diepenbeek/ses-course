---
title: Opdrachten
weight: 210
author: Arne Duyver, koen Yskout
draft: true
toc: true
---

## Algemene informatie

Hier vind je de opdrachten voor de permanente evaluatie van SES+EES.
De opdrachten voor **deel 1** worden gemaakt door alle studenten (SES+EES).
De opdrachten voor **deel 2** worden enkel gemaakt door de studenten SES.

### GitHub repository linken

Om de permanente evaluatie opdrachten in te dienen voor het vak Software Engineering Skills maken we gebruik van GitHub. In de lessen zal je onder begeleiding een gratis account aanmaken op dit platform. 
Je maakt de opdrachten in een repository die je aanmaakt via GitHub Classroom. Daarvoor moet je je GitHub gebruikersnaam linken aan je naam, zodat wij weten bij welke student de repository van 'FluffyBunny' hoort :). Zie de instructies bij de eerste opdracht voor meer details.

### Extra start files van andere start repositories downloaden in je eigen repo
Om jullie het leven makkelijk te maken, hebben we voor veel van de opdrachten al startcode geschreven in de vorm van een github repository. Die nieuwe repositories simpelweg clonen in een subdirectory van je eigen repository zorgt echter voor een aantal problemen. Die nieuwe directories met startcode worden dan namelijk als een **submodule** toegevoegd aan je main repo. Die repo wordt dus als een soort link beschouwd en aangezien die nieuwe repo niet van jullie zelf is ga je dan ook geen veranderingen aan die code kunnen pushen. <br />
In de plaats moeten we die start-code-repositories als een **subtree** toevoegen. Dat heeft als voordeel dat je zelf gewoon wijzigingen kan aanbrengen, committen en pushen naar je eigen hoofd repository. Bovendien blijft er ook een zachte link met de originele start-code-repository zodat als hier iets aan wijzigt je die online wijzigingen nog wel kan fetchen. (Bijvoorbeeld als we merken dat er nog een foutje in de startcode stond!).

Zo een subtree aanmaken doe je dan op de volgende manier:
1. Voeg de remote-start-code-repository toe aan je eigen repo met volgende commando:
```bash
git subtree add --prefix=<subdirectory_waarin_je_de_files_wil_clonen> <remote-start-code-repository-link> <branch_name> --squash
```
   - met `--prefix=` geef je aan in welke subdirectory de inhoud van de externe repo moet komen.
   - je kan een specifieke branch meegeven (meestal `main`)
   - met `--squash` condenseer je de git history van de externe repo tot één enkele commit. (Je kan deze optie weglaten als je toch de hele git history wil bekijken)
2. Zodra de files in jouw repo staan, kan je ze bewerken zoals normaal. De changes worden dan onderdeel van de history van JOUW repository, onafhankelijk van de oorspronkelijke externe repository.
3. Moesten er later toch wijzigingen aangebracht worden in de remote-start-code-repository die je wil fetchen dan kan je volgend commando gebruiken:
```bash
git subtree pull --prefix=<subdirectory_waarin_je_de_files_wil_pullen> <remote-start-code-repository-link> <branch_name> --squash
```

{{% notice warning %}}
Heb je toch al zo een repo gecloned en is dit nu een een submodule waarvan je de changes niet kan committen dan kan je best je wijzigingen even kopieren naar een andere locatie buiten je repo. De subdirectory verwijderen en nu als subtree toevoegen en dan je oplossingen terug erin zetten.
{{% /notice %}}

### Naamgeving bij indienen (tags)
Om de verschillende (sub)opdrachten te identificeren wordt er gebruik gemaakt van "tags" bij het committen van je finale resultaat. (In de eerste lessen worden deze onderwerpen klassikaal uitgelegd zodat alle studenten de juiste werkwijze gebruiken).

De naamgeving: voor het committen van je resultaat van "Opdracht 1" gebruik je de tag `v1`, voor "Opdracht 2" gebruik je de tag `v2`, enzoverder.

**Tags pushen naar github**: `git push --tags`
**Tags op github verwijderen**: `git push --delete origin <tagname>`. Dit moet je doen als je bijvoorbeeld lokaal een tag verplaatst hebt.
**Lokaal een tag deleten**: `git tag --delete <tagname>`

### Deadlines

Er is 1 deadline voor alle opdrachten van het eerste deel (SES+EES): **vrijdag 21/03/2025 23u59**.

De deadline voor alle opdrachten van het tweede deel (enkel SES) is **zondag 25/05/2025 23u59**.
