---
title: 5.6 Catching Patterns in the Wild
---

De design pattern opgaves tot nu toe zijn vrij kleine self-contained oefeningen geweest die enkel de basisprincipes verduidelijken. In de praktijk worden patronen vooral **gecombineerd** toegepast. Het is om te beginnen al een kunst om ze te ontdekken in bestaande code. 

Een goede softwareingenieur is een ingenieur die ook **veel code leest**. Een goede schrijver kan niet schrijven zonder te lezen. Hetzelfde is waar voor code schrijven. 

## Gotta Catch 'em All

![](/img/pokemon.jpg "Pokemon Leaf Green op de GBA")

De opdracht is misleidend eenvoudig: identificeer in onderstaande codebases de vijf aangeleerde software design patterns. Het zou kunnen dat ze niet voorkomen, het zou kunnen dat ze in "gemuteerde vorm" voorkomen (door de berperkingen van de programmeertaal, of door een combinatie die de programmeur verzon).


### De codebases

Onderstaande publiek beschikbare code is gekozen omwille van drie redenen:

1. Deze codebases zijn stuk voor stuk _groot_.
2. Deze codebases zijn open source en gehost op GitHub.
3. Deze codebases zijn jullie als student totaal onbekend.

Succes met de jacht!

- https://github.com/id-Software/Quake-III-Arena <br/>C, GPL-2.0, released 2012. Hét arena shooter spel van id Software. Beperk je tot `code/game`.
- https://github.com/wgroeneveld/gba-sprite-engine <br/>C++, MIT, released 2018. Een high level GBA sprite engine geschreven in C++. Beperk je tot `engine`.
- https://github.com/ccomeaux/boardgamegeek4android <br/>Kotlin, GPL-3.0, released 2015. De onofficiële [BoardGameGeek](https://boardgamegeek.com/) app voor Android. Gebruik je Android dev kennis om gericht te zoeken. 
- https://github.com/caddyserver/caddy <br/>Go, Apache-2.0, released 2019. Een moderne HTTP(S) webserver. De main source code zit in de root, maar `modules` is misschien interessanter (en kleiner) om mee te beginnen. 
- https://github.com/runelite/runelite <br/>Java, BSD-2, released 2021. Een old school MMO [RuneScape](https://play.runescape.com/) game client. Beperk je tot `runelite-client/src`.
- https://github.com/alibaba/arthas <br/>Java, Apache-2.0, released 2019. Een door Ali Baba ontwikkelde diagnostic tool om productie issues op te sporen zonder de server te herstarten. Beperk je tot `core/src/main/java/com/taobao/arthas/core`.