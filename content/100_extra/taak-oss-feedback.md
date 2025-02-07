---
title: "Taak 1 OSS Contrib.: Feedback"
---

## Contributie/code feedback

Studenten die duidelijk lieten zien dat ze geprobeerd hebben actief te zijn in de OSS community hebben hier overduidelijk een streeptje voor---hetzij via discussies in GitHub issues om hulp te vragen of aan te bieden, hetzij via Discord groepen van de maintainers indien van toepassing. 

De uiteindelijke "grootte" van aanpassing speelt minder mee: het is vaak moeilijker om de juiste aan te passen regel te vinden dan de effectieve aanpassing door te voeren. Natuurlijk speelt complexiteit wel degelijk een (grote) rol. Studenten die kozen voor een groot en complex project en die erin slaagden om hun aanpassingen te mergen werden beloond voor het risico en de extra moeite.

De effectieve code wijzigingen verdienen nog steeds de nodige aandacht. Dit is nog steeds een _software engineering_ cursus, wat betekent dat de principes aangereikt in deze cursus ook toegepast dienen te worden. Hieronder volgen enkele tips om volgende keer nog beter uit de hoek te komen:

- Enkele contributors schreven code zonder unit testen ook al waren die duidelijk wel aanwezig in het project en vermeld in de contributing guidelines. Houd er rekening mee dat---als je code aanvaard zou worden---je wijziging later opnieuw kan wijzigen. Testen verduidelijken je implementatie en kunnen falen zodat andere contributors een vangnet hebben, moesten ze jouw stuk hebben stukgemaakt.
- Hier en daar sloop er wat erg duidelijke duplicatie in de pull requests. Bijvoorbeeld: drie keer dezelfde `if { }` check in verschillende functies, of een `switch { }` dat een factory design pattern had kunnen zijn (of misschien al is?).
- Als je reeds feedback ontving, probeer dan rekening te houden met de wensen van de maintainers. Sommigen gaven het op onder het motto "het is nog maar 2 weken voor het examen, ik moet nu blokken" en lieten de maintainers in de kou staan. Dit komt niet goed over.


Sommigen hebben nog geen feedback ontvangen. Dat is prima, maar misschien is het wel leuk om in de zomer je pull request in het oog te houden om dit toch nog te laten aanvaarden. Misschien is er nog een klein beetje werk aan (bijvoorbeeld wat renames), en kan je met weinig moeite je code toch nog laten mergen. Dit lijkt me een leuke afsluiter voor dit vak en geeft veel voldoening!


## Verslag feedback

In [Taak 1: Open Source Contributie](/extra/taak-oss/) werd verwacht---naast de nodige code wijzigingen---een verslag te schrijven van je evaringen (**Stap 3: Stel een verslag op**). Hieronder volgt kort feedback die nuttig kan zijn voor toekomstig paper- en verslagwerk. 

- Dien altijd een verslag in het gewenste _formaat_ in. Als er `.pdf` staat, upload dan geen `.docx`.
- Vermeld altijd je _naam_. Uitvissen van wie een verslag is of dit moeten lezen gebaseerd op filenames (die gaan wijzigen als wij het verbeteren) is niet leuk en kost onnodig punten. 
- Gelijkaardig: vermeld het _project_ **met link** naar je pull request/issue. Dit kon inderdaad ook in het tekstveld in Toledo ingegeven worden, maar dan zijn beide gegevens niet gekoppeld. Als je schrijft "ik heb een bolletje toegevoegd in scherm X van CooleApp", weten wij nog steeds niet over wat het nu precies gaat. 
- Als je code toevoegt aan een verslag, voeg dan **NOOIT** een screenshot toe, laat staan eentje in "dark mode"! Kies voor een Mono lettertype en neem karakters over. Zelfs als je Word gebruikt is dit proces eenvoudiger én duidelijker leesbaar dan een screenshot toevoegen. 
- Conclusies van verslagen dienen om de lezer te helpen samenvatten wat er in de vorige paragrafen werd gezegd. Dit hoeft geen uitgebreide herhaling te zijn ("en toen dit, en toen dit, en toen..."), maar een summiere zin ("het was leuk") draagt evenmin weinig bij. 

Een algemene hint: **lees je verslag grondig na** voor je indient. Zijn er slordige dt-fouten? Is de toegevoegde code wel leesbaar? Heb ik alle gegevens genoteerd? Is de context volledig---kan iemand die niet aan mijn issue gewerkt heeft begrijpen wat ik heb geschreven? 
