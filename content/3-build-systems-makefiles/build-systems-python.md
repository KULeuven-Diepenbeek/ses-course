---
title: 'Makefiles for Python'
weight: 3
draft: false
author: Arne Duyver
---

## Interpreted vs compiled languages
In tegenstelling tot programmeertalen waarvan de broncode eerst gecompileerd moet worden om te runnen (zoals Java en C), is Python een **interpreted programming language**. Dit betekent dat de broncode van Python direct wordt uitgevoerd door **een interpreter**, zonder dat er een aparte compilatiestap nodig is. De interpreter (de python software die je geïnstalleerd moet hebben) leest de broncode regel na regel en voert deze direct uit, wat **het ontwikkelproces vaak sneller en flexibeler** maakt. Dit komt omdat fouten direct tijdens het uitvoeren van de code kunnen worden opgespoord en gecorrigeerd, zonder dat de hele applicatie opnieuw gecompileerd hoeft te worden.

Het verschil met **compiled languages** is dat bij deze talen de broncode eerst wordt omgezet in machinecode door een compiler voordat de code kan worden uitgevoerd. Dit proces, bekend als compilatie, genereert een uitvoerbaar bestand dat direct door de computer kan worden uitgevoerd. Hoewel dit een extra stap toevoegt aan het ontwikkelproces, kan het resulteren in **snellere uitvoeringstijden** van de uiteindelijke applicatie, omdat de machinecode direct door de hardware wordt uitgevoerd zonder tussenkomst van een interpreter.

Een tussenoplossing tussen interpreted en compiled languages is **Just-In-Time (JIT) compiling**. JIT-compiling combineert aspecten van beide benaderingen door de broncode tijdens de uitvoering te compileren naar machinecode. Dit betekent dat de code aanvankelijk wordt geïnterpreteerd, maar dat veelgebruikte delen van de code tijdens de uitvoering worden gecompileerd naar machinecode om de prestaties te verbeteren. Talen zoals Java en C# maken gebruik van JIT-compiling om een balans te vinden tussen de flexibiliteit van interpreted languages en de snelheid van compiled languages.

## Compilen naar .exe
Iedereen kan dus in principe door het `python`-commando te gebruiken je python bronbestanden runnen. We willen het de eindgebruiker echter zo simpel mogelijk maken, daarom gaan we met behulp van `pinstaller` al onze python files kunnen "compileren" naar een single binary, dat je dan op Linux kan runnen. (Of naar een Windows executable, `.exe`)

Je kan pyinstaller installeren met: `sudo pip install pyinstaller --break-system-packages`

Je kan nu met een simpel command je python applicatie compileren naar een binary: `pyinstaller --onefile --name app.bin app.py`

Je ziet meteen dat pyinstaller een `build/<appname>`-directory aanmaakt waar pyinstaller alle files zet die nodig zijn voor de omvorming tot een binary. Een tweede belangrijke file die aangemaakt wordt is de `<appname>.spec`, hierin kan je verschillende eigenschappen aanpassen zoals de targeted architecture bijvoorbeeld (`target_arch`)

Test nu eens je binary in de `./dist`-directory met `./dist/app.bin`.

## Oefening
Extract alle files in [dit zip bestand](/files/ses-monstergame-python-start.zip) in een directory naar keuze OF clone [de repository](https://github.com/ArneDuyver/ses-monstergame-python-start). Schrijf een simpele makefile dat de volgende dingen kan doen en plaats het in de root van je directory:
- `compile`: Compileert de bronbestanden naar de single 'monstergame.bin' file
- `clean`: Verwijdert het '.bin'-bestand
- `run` : Voert de 'monstergame.bin' uit
- `test` : Voert de 'app.py' uit

<!-- EXSOL -->
<!-- <details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen</b></i>🔽</summary>
<p>

```makefile
MAIN = app.py
TARGET = app.bin

compile:
	@echo "compiling ..."
	pyinstaller --onefile --name app.bin app.py
	@echo "Done compiling."

clean:
	@echo "cleaning ..."
	-rm -R ./build/*
	@echo "Done cleaning."

test: 
	@echo "testing program $(MAIN) ...\n---------------"
	python3 app.py
	@echo "---------------\nProgram exited."

run: 
	@echo "testing program $(MAIN) ...\n---------------"
	@cd ./dist && ./app.bin
	@echo "---------------\nProgram exited."
```

</p>
</details> -->