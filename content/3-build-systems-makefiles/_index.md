---
title: "3. Build systems and Makefiles"
weight: 30
author: Arne Duyver
draft: true
---

## Hoe ga je van broncode naar een werkende software applicatie

In de wereld van software engineering vormt de compiler een essentieel hulpmiddel dat **de kloof overbrugt tussen de door ontwikkelaars geschreven broncode en de uitvoerbare machinecode die door de computer wordt begrepen**. Wanneer programmeurs een programma schrijven, doen zij dit vaak in een hoog-niveau taal die leesbaar en begrijpelijk is voor mensen. De compiler vertaalt deze code vervolgens naar een lager-niveau, binaire instructies die specifiek zijn voor de hardware (CPU architecture) waarop het programma draait.

Het compilatieproces bestaat doorgaans uit verschillende stappen die gezamenlijk zorgen voor een correcte en efficiënte omzetting van de broncode. 
1. Allereerst vindt een **lexicale analyse** plaats, waarbij de broncode wordt opgesplitst in basiselementen, of tokens. 
2. Vervolgens controleert **de parser** of deze tokens in de juiste volgorde staan volgens de grammaticale regels van de taal, waardoor een abstracte syntaxisboom ontstaat. 
4. Hierna volgt een **semantische analyse** om te verifiëren dat de code logisch en consistent is. 
5. Optimalisatiefasen kunnen daarna ingrijpen om de prestaties te verbeteren, waarna de **codegenerator de uiteindelijke machinecode produceert**. 
6. Ten slotte wordt deze code vaak gekoppeld/gelinkt met externe bibliotheken en modules, zodat er een volledig functioneel uitvoerbaar bestand ontstaat, ook wel een `executable` of `binary` genoemd.

Naast de technische vertaalslag biedt het gebruik van een compiler ook andere belangrijke voordelen. 
- Zo kan de compiler **programmeerfouten** al vroeg in het ontwikkelproces **opsporen**, zoals syntaxis- of typefouten, waardoor deze sneller gecorrigeerd kunnen worden. 
- Tevens zorgt de **optimalisatie** tijdens de compilatie ervoor dat de uiteindelijke applicatie efficiënter draait, wat cruciaal is in productieomgevingen. 
- Deze scheiding tussen broncode en machinecode maakt het bovendien mogelijk voor ontwikkelaars om zich te **richten op de logica en architectuur van hun programma**, terwijl de compiler de complexe taak van vertalen en optimaliseren op zich neemt.

### Build systems
Naast het compileren en linken van code, kunnen build-systems het manuele werk aanzienlijk vereenvoudigen voor developers. Build-systems **automatiseren** niet alleen het proces van compileren en linken, maar **beheren vaak ook dependencies**, **voeren tests uit** en zorgen voor een **consistente en reproduceerbare build-omgeving**. Dit betekent dat ontwikkelaars niet langer handmatig complexe commando’s hoeven uit te voeren voor elke stap in het buildproces. 

Door alleen de gewijzigde onderdelen opnieuw te compileren, optimaliseren build-systems de efficiëntie en verminderen ze de kans op menselijke fouten. Bovendien dragen ze bij aan een gestandaardiseerde workflow, wat vooral binnen teams zorgt voor een soepelere samenwerking en minder integratieproblemen.

_**In de volgende onderdelen leer je hoe dit er praktisch uitziet voor verschillende programmeertalen**_

