---
title: "10. AI in software engineering"
weight: 140
draft: true
toc: true
---

In dit hoofdstuk bekijken we hoe je AI praktisch en verantwoord inzet tijdens software engineering.
We focussen op Large Language Models (LLMs), modern toolgebruik, agents, MCP, en op concrete werkwijzen om sneller te werken zonder kwaliteit of veiligheid te verliezen.

We behandelen volgende onderwerpen:

- Hoe werken LLMs?
  * next token prediction
  * tool calls en MCP
  * reasoning
- Agents vs Agentic AI
- Hoe werkt codegeneratie met LLMs?
- Welke verschillende soorten tools zijn er? (chatbots, code-assistenten, MCP, agents)
  * Chatbots, bv. ChatGPT, Claude, Gemini, ...
  * Code-assistenten (IDE-integraties, CLI-tools), bv. Claude Code, Codex, opencode, ...
  * Agents
- Aanpak
  * Vibe coding
  * Spec-driven development met AI
    * Programmeren in markdown
- AI, code quality, software engineering principles

## Hoe werken LLMs?

Wanneer we in software engineering over AI spreken, bedoelen we meestal een Large Language Model (LLM). Intuïtief is een LLM een systeem (in de vorm van een _diep neuraal netwerk_) dat een tekst (en/of code) stukje per stukje aanvult. Het kijkt naar wat er al staat, en kiest daarna een volgende stukje tekst, waarbij het meer waarschijnlijke vervolg met meer kans gekozen worden.
Vervolgens kijkt het model naar de nieuwe tekst, en kiest opnieuw een volgend stukje, enzovoort.
Dat stuk per stuk aanvullen heet **autoregressief genereren** (auto = zelf, regressie = gebaseerd op vorige stukjes, dus gebaseerd op vorige stukjes die het eerder zelf gegenereerd heeft).

Een voorbeeld: als we een LLM vragen om volgende zin te vervolledigen:

```
Beter één 
```

dan zal het model als volgend woord waarschijnlijk `vogel` kiezen.
Vervolgens vervolgens zal het model kijken naar de nieuwe tekst `Beter één vogel` en als volgende woord waarschijnlijk `in` kiezen, enzovoort, tot het een volledige zin heeft gegenereerd: `Beter één vogel in de hand dan tien in de lucht.`
Maar in plaats van _vogel_ had het model ook voor _goede_ kunnen kiezen, en dat verder vervolledigen tot _Beter één goede vriend dan tien slechte vrienden._

Het model bouwt dus geen antwoord in één keer op, of bepaalt op voorhand niet welke richting het uitgaat.
Het kiest gewoon steeds het volgende stuk tekst, dan het volgende, en zo verder tot een volledige output ontstaat.
Een LLM zelf heeft, omwille van het autoregressieve karakter, dus ook geen manier om een vorig woord te herzien of te corrigeren, behalve door het volgende woord te kiezen dat een correctere zin oplevert[^reasoning].

[^reasoning]: Er zijn wel technieken om een LLM toch te laten "redeneren" of "reviseren", maar dat is niet de standaardwerking van een LLM, en vereist specifieke voorzieningen. We bespreken dat in het [deel rond reasoning](#reasoning).

Een voorbeeld uit de software engineering context: stel dat de LLM gevraagd wordt om volgende tekst aan te vullen:

```java
public static int add(int a, int b) {
    return
```

Een waarschijnlijke aanvullig hiervoor is `a + b;`, en het model zal dat dan waarschijnlijk ook voorstellen. Dat lijkt op "begrijpen", maar technisch is het vooral patroonherkenning op basis van heel veel eerdere voorbeelden, alsook de geleerde samenhang tussen het woord `add` en de `+`-operator.

### Tokens

Een "stukje" tekst noemen we een token.
Hierboven gebruikten we volledige woorden als tokens, maar in werkelijkheid zijn tokens meestal kleinere woorddelen of zelfs individuele letters.
Je kan dat ruwweg zien als een klein fragment: een woorddeel, operator, haakje, enzovoort.
[Op deze webpagina](https://platform.openai.com/tokenizer) kan je zien hoe OpenAI/ChatGPT een tekst opsplitst in tokens.
Bijvoorbeeld, de zin `Beter één vogel in de hand dan tien in de lucht.` wordt bij OpenAI opgesplitst in 13 tokens:

```text
B eter ␣één ␣vogel ␣in ␣de ␣hand ␣dan ␣tien ␣in ␣de ␣lucht .
```

Merk op dat de hoofdletter B een apart token is, en heel wat tokens bestaan uit een spatie gevolgd door een woord.

Niet elk model gebruikt dezelfde tokenisatie, maar het concept van tokens is universeel in LLMs.
Het aantal tokens dat een model kan verwerken in één keer (het "contextvenster") is een belangrijke beperking, omdat het bepaalt hoeveel tekst het model kan zien en gebruiken bij het genereren van output.

### Embeddings

Een LLM leert niet alleen patronen in tekst, maar ook een soort "betekenis" van woorden en zinnen, door ze te representeren als vectoren in een zeer hoog-dimensionale ruimte (duizenden dimensies). Deze vectoren worden "embeddings" genoemd. Ze stellen het model in staat om semantische relaties tussen woorden te begrijpen, zoals dat `koning` en `koningin` dichter bij elkaar liggen dan `koning` en `auto`. Embeddings zijn cruciaal voor het genereren van coherente en contextueel relevante output.

In de praktijk zal elke token omgezet worden in een overeenkomstige embedding-vector voordat het model er een volgend token op gaat voorspellen. Dat betekent dat het model niet rechtstreeks met tekst werkt, maar met deze vectorrepresentaties van tekst.

Net zoals bij tokens kunnen verschillende modellen ook verschillende embeddings gebruiken, maar het concept van het representeren van tekst als vectoren is een fundamenteel onderdeel van hoe LLMs werken.

### Transformer architecture

Zowat elke LLM is tegenwoordig gebaseerd op de transformer-architectuur (geïntroduceerd in 2017 in de paper [Attention is All You Need](https://arxiv.org/abs/1706.03762)). Deze architectuur maakt gebruik van een mechanisme genaamd "self-attention", waarmee het model kan bepalen hoe woorden (tokens) elkaar beïnvloeden en welke delen van de inputtekst het meest relevant zijn voor het genereren van het volgende token. Dit stelt het model in staat om lange-afstandsrelaties in tekst te begrijpen, wat cruciaal is voor het genereren van coherente en contextueel relevante output.

Een ouder model, GPT-2, kan je lokaal in je browser draaien en visualiseren aan de hand van deze interactieve explainer:
[https://poloclub.github.io/transformer-explainer/](https://poloclub.github.io/transformer-explainer/).

Deze self-attention is ook een van de voornaamste redenen waarom LLMs een contextvenster hebben met beperkte lengte.
Omdat elk woord elk ander woord kan beïnvloeden, moet het model alle woorden in het contextvenster met elkaar vergelijken.
Voor 1000 tokens betekent dat 1 miljoen vergelijkingen, voor 2000 tokens 4 miljoen, enzovoort.

### Instruction tuning

Een LLM zelf is eigenlijk enkel een tekstvoorspeller die een bestaande tekst verder uitbreidt.
Als je aan een 'pure' LLM zou vragen om de tekst 

```text
Hoe bak ik een brood?
```

aan te vullen, dan zou het best kunnen dat je dit als aanvulling krijgt:

```text
Deze vraag stellen mensen zich al sinds de oudheid, want brood is een van de oudste en meest fundamentele voedingsmiddelen. Het proces van brood bakken is een combinatie van wetenschap, kunst en geduld.
```

Als we een LLM als chatbot of assistent willen gebruiken, willen we echter een concreet antwoord krijgen op de vraag, zoals een recept of stapsgewijze instructies.
Om dat te doen, wordt de aan te vullen tekst opgedeeld in een afwisseling van "systeem", "gebruiker" en "model" berichten, waarbij het systeem een instructie geeft aan het model over hoe het zich moet gedragen, de gebruiker een vraag stelt of een opdracht geeft, en het model een antwoord genereert.
Deze instructies kunnen heel krachtig zijn, omdat ze de context van het model sturen en de mogelijke vervolgen beperken.
Bijvoorbeeld, je vraagt aan het model om volgende tekst aan te vullen:
```text
<Systeem> Je bent een behulpzame assistent.
<Gebruiker> Hoe bak ik een brood?
<Model>
```

In het begin van LLM-gebaseerde assistenten werd dit gedaan met een 'trucje', door bijvoorbeeld volgende tekst te laten aanvullen:

```text
Dit is een conversatie tussen een gebruiker en een behulpzame assistent. De assistent geeft duidelijke, stapsgewijze instructies om de vraag van de gebruiker te beantwoorden.

<Gebruiker> Hoe bak ik een brood?
<Assistent> 
```

Tegenwoordig wordt een LLM specifiek getraind om instructies van gebruikers op te volgen. Dit proces heet **instruction tuning** en vormt een belangrijke stap in de ontwikkeling van chatbots en AI-assistenten. Daarbij wordt het model getraind op voorbeelden van instructies en bijbehorende gewenste antwoorden, zodat het leert hoe het adequaat moet reageren op verschillende soorten prompts.

Daarnaast wordt vaak gebruikgemaakt van RLHF (Reinforcement Learning from Human Feedback). Hierbij beoordelen mensen de kwaliteit van modelantwoorden, en die feedback wordt gebruikt om het model verder te optimaliseren zodat het nuttigere, relevantere en veiligere antwoorden genereert.

Van veel (open weight) modellen zijn er verschillende versies, waaronder een basismodel (een algemene tekstvoorspeller) en een instructie-getunede versie.

### Hallucinaties

Een LLM genereert tekst op basis van statistische patronen die het tijdens training heeft geleerd, niet op basis van een expliciet begrip van waarheid of feitelijke correctheid.
Die voorspellingen steunen op veel meer dan enkel woordfrequenties: het model leert bv. ook complexe patronen in taal en de betekenis van woorden. Toch blijft het fundamenteel een systeem dat voorspelt welke tekst waarschijnlijk volgt op eerdere tekst.
Een standaard-LLM controleert daarbij niet expliciet of uitspraken waar zijn, zoekt niet automatisch informatie op in betrouwbare bronnen, en beschikt doorgaans niet over expliciete kennis van de oorsprong van zijn informatie[^tools]. Daarom kan een LLM geen garantie geven dat gegenereerde antwoorden correct zijn.

[^tools]: We zullen later (bij het deel rond [Tools](#tool-calls)) zien dat er manieren zijn om een LLM toch informatie te laten teruggeven uit bronnen, maar dat is niet de standaardwerking van een LLM.

De formulering van je prompt heeft een grote invloed op het resultaat. Als jij vaag bent, zijn er veel mogelijke vervolgen die allemaal "kloppen" binnen de statistische patronen van het model.
Als jij daarentegen expliciet bent over randvoorwaarden en context, beperk je de mogelijke vervolgen en verhoog je de kans op een correct antwoord.
Daarom krijg je in praktijk betere output met concrete prompts dan met korte algemene vragen.

Een instructie geven aan het model zoals _"hallucineer niet, verifieer je informatie"_ is weinig effectief (vergelijk het met zeggen tegen iemand die niet kan koken om enkel lekkere gerechten te maken).
Het model kan enkel de volgende token kiezen op basis van waarschijnlijkheid, en niet op basis van een begrip van waarheid of logica.
Het enige (waarschijnlijk beperkte) effect van deze instructie is dat de kans van een vervolg dat statistisch geassocieerd is met welgekende onwaarheden kleiner wordt.
Hierdoor zal de LLM dat vervolg minder snel kiezen. Maar dat is geen garantie dat het model niet alsnog een fout antwoord genereert.
Opnieuw in de keuken-analogie: het helpt de slechte kok misschien om zich te beperken tot traditionele en daarvoor veilige smaakcombinaties, maar verhindert niet dat er alsnog iets oneetbaar ontstaat.

Voor deze cursus volstaat dit mentaal model: een LLM is een zeer geavanceerde "volgende-stap-voorspeller" voor tekst en code.
Gebruik het als snelle assistent voor voorstellen, maar niet als automatische waarheidsmachine.

### Reasoning

Van moderne LLM's wordt vaak gezegd dat ze kunnen redeneren (_reasoning models_).
Dat is niet helemaal correct, omdat in de LLM zelf geen expliciete redeneercapaciteit ingebouwd zit: het blijft een voorspeller van volgende tokens.
Wat wordt er dan bedoeld met "reasoning" in de context van LLMs?
In essentie komt het erop neer dat de LLM niet meteen het antwoord begint te formuleren, maar eerst een soort "tussenstappen" genereert die lijken op een redenering of een gedachtegang.
Deze tussenstappen komen terecht in de context voor de voorspelling van volgende woorden, en tenslotte het antwoord.
Ze worden echter niet (standaard) getoond aan de eindgebruiker.

Een bekende techniek hiervoor is de zogenaamde _chain of thought (CoT)_. Hierbij wordt het model expliciet gevraagd (of getraind) om eerst zijn redenering in stappen te formuleren, voordat het tot een antwoord komt. Met andere woorden, luidop nadenken. Bijvoorbeeld:

```text
Gebruiker: Als ik groter ben dan mijn broer, en mijn broer groter is dan mijn zus, wie is er dan het kleinst?
Model: <Reasoning> De gebruiker vraagt wie het kleinst is in de gegeven situatie. Laten we de informatie stap voor stap analyseren:
1. Ik ben groter dan mijn broer. (Ik > Broer)
2. Mijn broer is groter dan mijn zus. (Broer > Zus)
3. Uit de eerste twee stappen kunnen we afleiden dat ik ook groter ben dan mijn zus, door de transitiviteit van >. (Ik > Zus)
4. Dus, de volgorde van grootte is: Ik > Broer > Zus.
</Reasoning>
Jouw zus is het kleinst.
```

Geavanceerdere technieken, zoals (Tree of Thoughts)[https://proceedings.neurips.cc/paper_files/paper/2023/file/271db9922b8d1f4dd7aaef84ed5ac703-Paper-Conference.pdf], gaan nog een stap verder en plaatsen een extra sturingslaag rond het model om het redeneerproces te plannen, evalueren en bij te sturen.

Daarbij kan hetzelfde model meerdere keren worden aangeroepen om verschillende mogelijke redeneringspaden of plannen te genereren. Vervolgens kunnen die plannen opnieuw door het model (of door bijkomende evaluatiemechanismen) beoordeeld worden, waarna het meest veelbelovende pad geselecteerd wordt om verder uit te werken.

Bij alle redeneer-technieken is het belangrijk om te beseffen dat het model nog steeds een volgende-token-voorspeller is, en dat de "redenering" die het genereert niet noodzakelijkerwijs logisch of correct is. Het model kan bijvoorbeeld een redenering genereren die er plausibel uitziet, maar toch fouten bevat of tot een onjuist antwoord leidt. Daarom is het cruciaal om de output van dergelijke redeneerprocessen kritisch te evalueren en waar nodig te corrigeren.

### Tool calls

Een belangrijke uitbreiding op de standaardwerking van een LLM is dat het model niet alleen tekst kan genereren, maar ook externe tools kan aanroepen of informatie kan opvragen via zogenaamde _tool calls_ (ook wel _function calling_ genoemd).

Daarbij genereert het model niet enkel gewone tekst, maar ook een gestructureerde representatie van een gewenste actie, bijvoorbeeld het opvragen van informatie, het uitvoeren van een berekening, of het raadplegen van een externe API.

Conceptueel kan dat er bijvoorbeeld zo uitzien:

```text
<tool_call>
{
  "tool": "weather_api",
  "parameters": {
    "location": "Amsterdam"
  }
}
</tool_call>
```

Hiermee geeft het model aan dat het de weather_api tool wil aanroepen met de parameter location="Amsterdam".

De omgeving rond het model (het systeem dat de LLM aanstuurt) herkent deze tool call, voert de bijbehorende actie uit, en geeft het resultaat vervolgens terug aan het model als bijkomende context. Het model kan die informatie daarna gebruiken om een beter geïnformeerd antwoord te genereren.

Belangrijk is dat de LLM zelf geen directe toegang heeft tot externe systemen. Het model genereert enkel tekst of gestructureerde output die een tool call voorstelt; het is de software rond het model die beslist of en hoe die tool call effectief uitgevoerd wordt.

De mogelijkheid om tool calls te maken is niet standaard in een LLM, maar vereist specifieke training: de LLM moet leren om de juiste syntax te gebruiken voor tool calls.
Daarnaast moet er een infrastructuur zijn die deze tool calls kan herkennen en uitvoeren, en de resultaten terug kan geven aan het model. Deze combinatie van LLM met tool calls maakt het mogelijk om veel krachtigere en contextueel relevante antwoorden te genereren, omdat het model nu toegang heeft tot actuele informatie en specifieke functionaliteiten die het zelf niet kan genereren.

De mogelijkheid om tools betrouwbaar te gebruiken vereist doorgaans bijkomende training of fine-tuning, zodat het model leert:

welke tools beschikbaar zijn;
wanneer een tool nuttig is;
hoe een correcte tool call gestructureerd moet worden.

Daarnaast is infrastructuur nodig die:

tool calls detecteert;
de juiste tool uitvoert;
en de resultaten teruggeeft aan het model.

Deze combinatie van LLM en externe tools maakt het mogelijk om veel krachtigere systemen te bouwen, omdat het model zo toegang krijgt tot actuele informatie en functionaliteiten die niet in de modelgewichten zelf opgeslagen zitten.

Hoe weet een LLM welke tools er beschikbaar zijn en hoe die werken?

Een eenvoudige techniek is om de beschikbare tools expliciet mee te geven in de prompt of systeemcontext. Bijvoorbeeld:

```text
<Systeem>
Je bent een behulpzame assistent. Je hebt toegang tot de volgende tools:

1. weather_api:
   vraagt het weer op voor een gegeven locatie.

Gebruik:
<tool_call>
{
  "tool": "weather_api",
  "parameters": {
    "location": "<locatie>"
  }
}
</tool_call>

2. calculator:
   voert eenvoudige wiskundige berekeningen uit.

Gebruik:
<tool_call>
{
  "tool": "calculator",
  "parameters": {
    "expression": "<uitdrukking>"
  }
}
</tool_call>
```

Op die manier krijgt het model informatie over welke tools bestaan, waarvoor ze dienen, en hoe ze correct aangeroepen moeten worden.


#### Model Context Protocol (MCP)

Model Context Protocol (MCP) definieert een gestandaardiseerde manier waarop externe programma’s tools, databronnen en functionaliteiten beschikbaar kunnen maken aan een LLM. In plaats van voor elke toepassing een aparte integratie te bouwen, kan een LLM-client via MCP dynamisch ontdekken welke tools beschikbaar zijn, welke parameters ze verwachten, en hoe ze aangeroepen moeten worden.

Daardoor kan hetzelfde model in verschillende omgevingen met verschillende tools werken zonder specifiek voor die tools getraind te moeten worden. De intelligentie rond toolgebruik zit grotendeels in het algemene vermogen van het model om instructies en gestructureerde context te interpreteren.

Een veelgebruikte toepassing van MCP is integratie met een command line interface (CLI). Daarbij kan een LLM bijvoorbeeld toegang krijgen tot shellcommando’s, programmeertools, versiebeheersystemen zoals Git, of build- en testomgevingen. De LLM genereert dan geen rechtstreekse systeemaanroepen, maar gestructureerde instructies die door een MCP-server of orchestration layer vertaald worden naar concrete CLI-commando’s.

Zo kan een model bijvoorbeeld bestanden opzoeken, tests uitvoeren, logs analyseren of code compileren. Het resultaat van die acties wordt vervolgens teruggegeven aan het model als bijkomende context, zodat het verder kan redeneren of antwoorden genereren.

Fundamenteel blijft de LLM echter een volgende-token-voorspeller: het model voert zelf geen commando’s uit, maar genereert enkel output die door externe software geïnterpreteerd en uitgevoerd kan worden.

## Welke verschillende soorten tools zijn er?

### Chat-UI

De eenvoudigste vorm van AI-ondersteunde softwareontwikkeling gebeurt via een klassieke chatinterface, zoals ChatGPT, Claude, of Google Gemini. Daarbij beschrijft de gebruiker een programmeerprobleem in natuurlijke taal, waarna het model code, uitleg of suggesties genereert.

De context die het model heeft is hier meestal beperkt tot de huidige conversatie, eventueel geüploade bestanden, en expliciet meegestuurde codefragmenten.
Daardoor werkt een chatinterface goed voor kleinere of afgebakende taken (zoals het genereren van kleine codefragmenten of uitleg vragen), maar minder goed voor complexe softwareprojecten met veel onderlinge afhankelijkheden.

Een belangrijk voordeel van een chat-UI is de lage instapdrempel: er is geen integratie met een ontwikkelomgeving nodig, en de interactie verloopt volledig via natuurlijke taal.

### IDE-integratie

Een tweede categorie bestaat uit AI-tools die rechtstreeks geïntegreerd zijn in een IDE zoals Visual Studio Code, IntelliJ, of gespecialiseerde AI-first editors zoals Cursor.

In tegenstelling tot een losse chatinterface heeft het model hier toegang tot veel rijkere context over het softwareproject. Het systeem kan bijvoorbeeld informatie gebruiken over de projectstructuur, meerdere broncodebestanden, type-informatie, compileerfouten, testresultaten en versiebeheer. Daardoor kunnen IDE-geïntegreerde assistenten veel geavanceerdere ondersteuning bieden, zoals intelligente code completion, refactorings, projectbrede wijzigingen of contextgevoelige codegeneratie.

Veel moderne IDE-tools combineren klassieke autocomplete met chatfunctionaliteit en tool use. Het model kan bijvoorbeeld relevante bestanden zoeken, symbolen analyseren, terminalcommando’s uitvoeren of tests starten. Omdat het model toegang heeft tot de volledige ontwikkelcontext, zijn de gegenereerde suggesties doorgaans consistenter en beter afgestemd op de bestaande codebase dan bij een losse chatinterface.

### Agentische UI/CLI

Een derde categorie bestaat uit zogenaamde agentische systemen (bv. Claude Code CLI of OpenAI Codex). Daarbij beperkt het model zich niet tot het beantwoorden van één prompt, maar probeert het zelfstandig een grotere programmeertaak uit te voeren via meerdere opeenvolgende stappen.

Deze systemen werken vaak via een terminalinterface (CLI) of een gespecialiseerde agentische UI. Het model krijgt dan toegang tot tools zoals shellcommando’s, programmeertools, versiebeheer, testframeworks, buildsystemen of deployment-tools. Een agentisch systeem kan bijvoorbeeld eerst een codebase analyseren, vervolgens relevante bestanden zoeken, code aanpassen, tests uitvoeren, foutmeldingen interpreteren en daarna bijkomende wijzigingen uitvoeren totdat een taak succesvol afgerond lijkt.

De “agentische” component zit grotendeels in de software rond het model. Een orchestration layer bepaalt welke tool calls uitgevoerd worden, geeft resultaten terug aan het model en organiseert iteratieve feedbacklussen waarin het model zijn eerdere resultaten kan evalueren en bijsturen.

Dergelijke systemen zijn bijzonder krachtig voor grotere softwaretaken, maar brengen ook extra complexiteit mee. Omdat het model autonoom meerdere acties na elkaar kan uitvoeren, wordt het moeilijker om elke stap volledig te controleren of voorspellen. Daarom blijven menselijke validatie en toezicht essentieel, zeker wanneer agentische systemen wijzigingen uitvoeren aan grotere codebases of productieomgevingen.

### Skills en gespecialiseerde agents

Veel moderne AI-systemen ondersteunen zogenaamde skills: herbruikbare pakketten van instructies, context, tools en workflows die een model gespecialiseerd gedrag geven voor een bepaalde taak. In agentische systemen spelen skills vaak een belangrijke rol. Een agent kan afhankelijk van de taak verschillende gespecialiseerde skills activeren.
Net zoals bij tool calls krijgt het model een overzicht van welke skills beschikbaar zijn, en kan het die dynamisch inzetten op basis van de context en de vereisten van de taak.

Conceptueel lijken skills op een combinatie van configuratie, prompt engineering, toolintegratie, en domeinspecifieke automatisering. Ze maken het mogelijk om een algemeen LLM gedeeltelijk te specialiseren of te aligneren op een specifieke werkwijze zonder het model opnieuw te trainen.

In de praktijk worden skills vaak beschreven in een SKILL.md bestand, waarin de functionaliteit, parameters, en gebruiksinstructies van de skill worden vastgelegd. Het model kan dat bestand lezen en gebruiken als context om te bepalen wanneer en hoe de skill in te zetten.

Een zeer populaire skill is bijvoorbeeld de ['grill-me' skill](https://github.com/mattpocock/skills/tree/main/skills/productivity/grill-me).
Deze is slechts enkele regels lang, en laat het model de gebruiker ondervragen over een plan of ontwerp tot alle details uitgeklaard zijn.

```markdown
---
name: grill-me
description: Interview the user relentlessly about a plan or design until reaching shared understanding, resolving each branch of the decision tree. Use when user wants to stress-test a plan, get grilled on their design, or mentions "grill me".
---

Interview me relentlessly about every aspect of this plan until we reach a shared understanding. Walk down each branch of the design tree, resolving dependencies between decisions one-by-one. For each question, provide your recommended answer.

Ask the questions one at a time.

If a question can be answered by exploring the codebase, explore the codebase instead.
```

## Software schrijven met AI: aanpak

### Vibe coding

De term _vibe coding_ verwijst naar een informele manier van softwareontwikkeling waarbij de programmeur vooral op hoog niveau beschrijft wat een systeem moet doen, terwijl een LLM grote delen van de concrete implementatie genereert. De interactie verloopt meestal via natuurlijke taal: de gebruiker beschrijft gewenste functionaliteit, vraagt wijzigingen, geeft feedback op gegenereerde code en stuurt iteratief bij.

Bij vibe coding verschuift de focus van het handmatig schrijven van code naar het begeleiden, evalueren en corrigeren van gegenereerde code. De programmeur werkt meer als een reviewer of architect die de globale richting bepaalt, terwijl het model veel implementatiedetails invult.

Deze aanpak werkt bijzonder goed voor prototypes, kleine scripts of tools, experimentele projecten, of wanneer de programmeur snel een werkend voorbeeld wil hebben om verder mee te werken. Het is ook een krachtige manier om te leren: door te zien hoe een LLM code genereert op basis van een beschrijving, kan een programmeur nieuwe patronen, bibliotheken of technieken ontdekken.

Omdat moderne LLM’s grote hoeveelheden programmeerpatronen hebben geleerd, kunnen ze vaak verrassend snel werkende code genereren voor veelvoorkomende problemen.

Tegelijk brengt vibe coding ook risico’s mee. Het model kan immers code genereren die niet doet wat de programmeur bedoelt, of code die er op het eerste gezicht correct uitziet maar in werkelijkheid fouten bevat. Het is ook mogelijk dat de gegenereerde code moeilijk te begrijpen of te onderhouden is, vooral als het model complexe patronen gebruikt die niet goed aansluiten bij de rest van de codebase. Daarom is het belangrijk om gegenereerde code altijd kritisch te beoordelen, en niet zomaar aan te nemen dat het correct is.

Bij grotere softwareprojecten kan een puur iteratieve “prompt → code → prompt → code”-workflow daarom snel leiden tot inconsistente architecturen of moeilijk onderhoudbare codebases. Vibe coding werkt meestal het best wanneer de programmeur voldoende expertise heeft om gegenereerde code kritisch te beoordelen.

### Spec-driven development met AI

Een meer gestructureerde aanpak bestaat uit spec-driven development met AI. Daarbij gebruikt men natuurlijke taal niet alleen om losse codefragmenten te genereren, maar om expliciete specificaties, ontwerpdocumenten en plannen op te stellen die als basis dienen voor verdere codegeneratie.

In plaats van rechtstreeks te vragen “Schrijf een webapplicatie” werkt men eerder stapsgewijs:

1. de vereisten beschrijven;
2. een architectuur definiëren;
3. interfaces en datamodellen vastleggen;
4. componenten opdelen;
5. en pas daarna implementaties laten genereren.

De specificatie wordt hierbij een centrale bron van waarheid waar zowel de programmeur als het model voortdurend naar kunnen teruggrijpen.
Deze aanpak sluit beter aan bij klassieke software-engineeringprincipes.

Omdat LLM’s sterk afhankelijk zijn van context, helpt een goede specificatie bovendien om consistentere output te genereren over langere interacties en grotere codebases heen.
Spec-driven development combineert dus de snelheid van AI-ondersteunde codegeneratie met meer expliciete controle over architectuur en ontwerpbeslissingen.

#### Programmeren in markdown

Een opvallende evolutie binnen spec-driven development is het idee van “programmeren in markdown”. Daarbij worden Markdown-documenten gebruikt als centrale interface tussen programmeur en AI-systeem.

In plaats van uitsluitend broncode te schrijven, genereert en onderhoudt de programmeur Markdown-documenten met vereisten, architectuurbeschrijvingen, 
datamodellen, ...
Een AI-systeem gebruikt deze documenten vervolgens als context voor codegeneratie, refactorings, testgeneratie of projectanalyse.

Markdown is hiervoor bijzonder geschikt omdat het eenvoudig leesbaar is voor mensen en gemakkelijk verwerkt kan worden door LLM’s, en structuur biedt zonder zware formele syntax.

In veel moderne AI-workflows verschuift een deel van het programmeerwerk daardoor van direct code schrijven naar het expliciet beschrijven van intentie, structuur en constraints.
De kwaliteit van de specificatie wordt dan steeds belangrijker, omdat die in sterke mate bepaalt welke code het model genereert.

## AI, code quality, software engineering principles

Hoewel AI-ondersteunde softwareontwikkeling veel voordelen biedt, zoals snellere prototyping en toegang tot geavanceerde codegeneratie, brengt het ook uitdagingen mee op het gebied van codekwaliteit en software-engineeringprincipes.
Omdat LLM’s geen expliciet begrip hebben van software-architectuur, design patterns, of best practices, kunnen ze code genereren die functioneel is maar moeilijk te onderhouden, slecht gestructureerd of inconsistent met de rest van de codebase. Daarom is het belangrijk om bij AI-gegenereerde code dezelfde kwaliteitsnormen toe te passen als bij handgeschreven code, en om regelmatig refactorings en code reviews uit te voeren.
Daarnaast is het cruciaal om goede testpraktijken te volgen, omdat gegenereerde code mogelijk fouten bevat die niet meteen zichtbaar zijn. Unit tests, integratietests en code coverage analyses blijven essentieel om de betrouwbaarheid van AI-gegenereerde code te waarborgen. Tot slot is het belangrijk om bewust te zijn van de beperkingen van LLM’s en om menselijke expertise en kritische beoordeling te blijven inzetten, vooral bij grotere of complexere softwareprojecten.
