---
title: "1. Versie- en issuebeheer"
weight: 1
author: Wouter Groeneveld en Arne Duyver
draft: false
---

> <i class="fa fa-question-circle" aria-hidden="true"></i>
> Wat is de beste manier om source code te bewaren?

## Wat is versiebeheer of _source control_?

**[Source Control](https://en.wikipedia.org/wiki/Version_control)** is een sleutelbegrip voor ontwikkelteams. Het stelt iedereen in staat om aan dezelfde source file te werken zonder bestanden op- en neer te sturen, voorziet backups, maakt het mogelijk om releases en branches uit te rollen, ...

Een versiebeheer systeem bewaart alle _wijzigingen_ aan (tekst)bestanden. Dat betekent dat eender welke wijziging, door wie dan ook, teruggedraaid kan worden. Zonder versiebeheer is het onmogelijk om code op één centrale plaats te bewaren als er met meerdere personen aan wordt gewerkt. Zelfs met maar één persoon is het toch nog steeds sterk aan te raden om te werken met versionering. Fouten worden immers snel gemaakt. Een bewaarde wijziging aan een bestand is permanent op je lokale harde schijf: de volgende dag kan je niet het origineel terug boven halen. Er wordt samen met delta's ook veel metadata bewaard (tijdstippen, commit comments, gebruikers, bestandsgroottes, ...)

Zie [extra lesmateriaal en bronnen](/versiebeheer/extra-bronnen) voor meer informatie. 

### Waarom versioneren?

Dat verduidelijkt [Geek & Poke](https://geek-and-poke.com/):

<img src="/img/teaching/ses/sourcecontrol.jpg" alt="drawing" style="max-height: 23em;"/>

Zonder versionering stuurt iedereen e-mail attachments door naar elkaar, in de verwachting een aangepaste versie terug te ontvangen. Maar, wat gebeurt er met:

- Conflicten? (iemand wijzigt iets in dezelfde cel als jij)
- Meerdere bestanden? (je ontvangt verschillende versies, welke is nu relevant?)
- Nieuwe bestanden? (je ontvangt aparte bestanden met nieuwe tabbladen)
- Bestandstypes? (iemand mailt een `.xslx`, iemand anders een `.xls`)
- ...

Het wordt al snel duidelijk dat het delen van celdata beter wordt overgelaten aan Google Sheets, waar verschillende mensen tegelijkertijd gegevens in kunnen plaatsen. Hetzelfde geldt voor source code: dit wordt beter overgelaten aan een versiebeheer systeem.

> What is “version control”, and why should you care? Version control is a system that records changes to a file or set of files over time so that you can recall specific versions later
> <cite><a href="https://git-scm.com/book/en/v2">Pro Git</a></cite>
