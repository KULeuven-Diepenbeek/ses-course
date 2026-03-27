---
title: "10.1 AI in software engineering"
autonumbering: true
weight: 10
toc: true
draft: false
---

## Wat is een LLM? (next-token-prediction)

Wanneer we in software engineering over AI spreken, bedoelen we meestal een Large Language Model (LLM). Voor programmeurs is het belangrijk om dat model niet als een "kennisbank met waarheden" te zien, maar als een probabilistisch systeem dat telkens het meest waarschijnlijke **volgende token** kiest op basis van de huidige context. Dat klinkt eenvoudig, maar daarachter zit een volledige keten van representaties en kansberekeningen.

Een eerste stap is tokenisatie. In plaats van "woorden" werkt een model met subwoorden en symbolen, typisch via varianten van byte-pair encoding of gelijkaardige tokenizers. In code betekent dat dat `calculateDiscount`, `(`, `)`, `{`, `return`, spaties en zelfs inspringingselementen afzonderlijke tokenpatronen kunnen worden. Vervolgens wordt elk token omgezet naar een vector (embedding), en verwerkt een transformer-architectuur de volledige context via aandachtmechanismen (self-attention). Zo leert het model welke stukken context relevant zijn voor de volgende voorspelling, bijvoorbeeld een eerder gedeclareerd type, een methodesignature of een geopende haak.

Aan het einde van zo'n stap produceert het model geen tekst maar een vector met **logits**: ruwe scores per mogelijk volgend token. Na een softmax-transformatie krijg je een kansverdeling. De decodingstrategie bepaalt dan welk token effectief gekozen wordt. Bij lage temperatuur en zonder sampling krijg je meer deterministisch gedrag; bij hogere temperatuur of top-p sampling krijg je creatievere, maar minder stabiele output. Voor codegeneratie wil je doorgaans lagere entropie: je wil consistente, controleerbare voorstellen in plaats van variatie om de variatie.

Tijdens training leert het model die kansverdeling door voor elk positie-token \(t_i\) de log-waarschijnlijkheid \( \log p(t_i \mid t_{<i}) \) te maximaliseren. Praktisch komt dit neer op het minimaliseren van cross-entropy loss over heel veel tokensequenties. Dat detail is didactisch nuttig, omdat het verklaart waarom het model zo sterk reageert op contextformulering: een kleine contextwijziging verandert de conditionele kansverdeling en dus het vervolg.

Concreet zie je dit in miniatuur wanneer je deze context geeft:

```java
public static int add(int a, int b) {
    return
```

De kansmassa zal dan sterk naar tokenreeksen verschuiven die in trainingsdata vaak volgden na vergelijkbare context, zoals ` a`, ` +`, ` b`, `;`. Het model "begrijpt" hier niet op menselijke manier dat optellen nodig is; het herkent vooral een statistisch patroon van gelijkaardige programmaconstructies. Net dat verklaart waarom LLM's tegelijk sterk en kwetsbaar zijn: ze zijn uitstekend in patroonrijke domeinen zoals code, maar ze garanderen geen formele correctheid.

## Beperkingen en betrouwbaarheid

De zwakte van een LLM zit niet alleen in feitelijke fouten, maar vooral in het verschil tussen **plausibiliteit** en **correctheid**. Een antwoord kan syntactisch mooi, didactisch helder en overtuigend geformuleerd zijn, terwijl het toch fout is op API-niveau, op randvoorwaarden, of op security. Voor software engineering is dit cruciaal, omdat code die "er goed uitziet" makkelijk door review glipt als de reviewer op stijl let in plaats van op gedrag.

Een typische foutbron is contextverlies. Het model ziet slechts een contextvenster en geen volledige, semantisch perfecte representatie van je project. Als een belangrijk ontwerpcontract buiten die context valt, gaat het model dat contract niet spontaan respecteren. Daarnaast heb je distributieverschuiving: je project gebruikt misschien interne libraries, specifieke build flags of conventies die nauwelijks in publieke trainingsdata voorkomen. Dan krijgen generieke patronen te veel gewicht.

Je moet daarom werken met dezelfde discipline die je voor handgeschreven code gebruikt: compileerbaarheid, testdekking, contractvalidatie en securitycontrole. Een nuttige mentale regel is dat AI-output pas "kandidaatcode" wordt nadat ze door je normale kwaliteitspoort is gegaan. In de praktijk betekent dit dat je niet alleen happy-path tests wil, maar expliciet ook failure-path, grenswaarden, nullability, concurrerend gedrag en performance-regressie controleert waar relevant.

## Praktisch codeerproces met LLM

De meeste teams halen de beste resultaten wanneer ze AI in een bestaande engineeringlus plaatsen, in plaats van het proces te vervangen. Voor Java-projecten werkt een ritme dat sterk op red-green-refactor lijkt vaak goed. Je start met een scherp geformuleerde taak, laat een eerste implementatie voorstellen, valideert via tests en static checks, en gebruikt daarna gerichte prompts om precies de gevonden defecten of hiaten te herstellen.

Stel dat je in `OrderService` een nieuwe kortingsregel wil toevoegen. Als je enkel "voeg korting toe" vraagt, krijg je een brede gok. Als je daarentegen precondities, postcondities, scope en testverwachting meegeeft, daalt de onzekerheid sterk:

```text
Voeg in OrderService een methode `calculateDiscount(Order order)` toe.
Randvoorwaarden:
- null input -> IllegalArgumentException
- alleen PREMIUM klanten krijgen 10%
- max korting 50 euro
Schrijf ook JUnit tests voor grensgevallen.
Wijzig enkel OrderService en OrderServiceTest.
```

Die prompt werkt goed omdat ze gedrag specificeert in plaats van implementatiedetails te dicteren. Daarna kan je technisch bijsturen: laat het model verklaren welke invarianten het veronderstelt, vraag welke testgevallen nog ontbreken, en laat het patches beperken tot een kleine write-scope. Hoe kleiner de stap, hoe beter de foutlokalisatie als iets misloopt. Dat is precies waarom AI in software engineering vaak meer oplevert bij veel kleine iteraties dan bij één grote "genereer mijn feature"-vraag.

## Chat-UI, IDE-integratie en agentische UI/CLI

De keuze van toolvorm bepaalt welk soort context het model ziet en hoe snel je feedback krijgt. Een chat-UI is sterk voor conceptueel ontwerpwerk: je wil een trade-off begrijpen, alternatieve architecturen vergelijken of een lastig stuk codegedrag laten herformuleren. De latentie zit hier minder in code-editing en meer in denkkwaliteit.

IDE-integratie is vooral efficiënt bij lokale transformaties in bestanden die al openstaan. Het model krijgt directe nabijcontext, en jij kan snel accepteren, aanpassen of verwerpen. In dit patroon schuilt wel een klassiek risico: acceptatiebias. Omdat suggesties inline verschijnen en vlot leesbaar zijn, stijgt de kans dat je semantische fouten minder streng controleert dan bij handgeschreven code.

Agentische UI/CLI-omgevingen zijn bedoeld voor langere workflows: meerdere bestanden aanpassen, tests draaien, fouten interpreteren, opnieuw patchen, en rapporteren. Dat lijkt meer op een mini-CI-loop. Voor dit type werk moet je expliciet afspreken welke checks verplicht zijn, welke directories write-access hebben, en hoe het resultaat samengevat wordt. Zonder die grenzen produceert een agent snel veel verandering met te weinig controle.

## Prompting en contexting voor code

Voor programmeurs is goede prompting bijna hetzelfde als goede specificatie. Het model moet weten wat het doelgedrag is, welke constraints hard zijn, welke interfaces niet mogen breken en hoe succes objectief gemeten wordt. Zodra die informatie ontbreekt, vult het model de gaten met statistisch waarschijnlijke aannames, en daar ontstaan de meeste regressies.

Technisch is contextkwaliteit belangrijker dan promptlengte. Je wint meer door twee relevante classes, een failing test en een duidelijke contractbeschrijving mee te geven, dan door een lange algemene uitleg. Zeker bij grotere codebases loont het om context compact maar semantisch rijk te houden: type-definities, foutmeldingen, testoutput en de precieze write-scope zijn meestal waardevoller dan veel ruwe broncode zonder focus.

Een vaak onderschat element is outputsturing. Als je vraagt om eerst aannames te expliciteren, daarna een plan te geven en pas daarna een patch voor beperkte bestanden, krijg je controlepunten waar je vroeg kan bijsturen. Dat reduceert niet alleen fouten, maar ook reviewkost, omdat de redeneerketen zichtbaar blijft in plaats van verborgen in een grote code-dump.

## Wat is een agent?

Een agent is in essentie een systeem dat een doel probeert te bereiken via een herhaalde **observeer-plan-voer-uit-evalueer**-lus. Waar een klassieke chat meestal één antwoord per prompt geeft, kan een agent tussentijdse resultaten gebruiken om het volgende gedrag te bepalen. In software engineering betekent dat bijvoorbeeld: repository doorzoeken, patch toepassen, tests draaien, failing output analyseren en een gerichte vervolgpatch maken.

Conceptueel kan je dit zien als een geautomatiseerde werkstroom met expliciete tussenstappen. De waarde ontstaat vooral wanneer de taak meerdere afhankelijkheden heeft en niet in één stap oplosbaar is. De kwaliteit hangt dan sterk af van twee zaken: hoe goed het doel is afgebakend, en hoe strikt de evaluatiestap is. Een agent zonder duidelijke evaluatiecriteria blijft plausibele maar onbewezen output produceren.

Daarom is het in technische teams nuttig om agentgedrag te behandelen als orchestratieprobleem: welke signalen gebruikt de agent om te beslissen, wanneer stopt de iteratie, welke fouten zijn "hard fail", en wanneer moet een mens verplicht overnemen. Zonder zulke stopvoorwaarden krijg je schijnproductiviteit: veel activiteit, weinig betrouwbare oplevering.

## MCP in de praktijk

MCP (Model Context Protocol) wordt interessant zodra je modelinteractie niet meer alleen uit vrije tekst bestaat, maar uit gecontroleerde toegang tot externe bronnen en tools. Technisch gezien definieert MCP een protocol waarin een host en een server capabilities uitwisselen, resources en tools aanbieden, en gestructureerde calls uitvoeren, doorgaans via JSON-RPC-berichten. Je kan het zien als een contractlaag tussen modelruntime en externe systemen.

In plaats van willekeurig tekst te copy-pasten uit tickets of documentatie, kan een agent via MCP bijvoorbeeld eerst resources opvragen, dan tools ontdekken, en vervolgens doelgerichte calls doen. In veel implementaties zie je dan een patroon zoals `tools/list`, `tools/call`, `resources/list` en `resources/read`. Daardoor verschuif je van "prompt met losse context" naar "workflow met expliciete contextoperaties". Dat verhoogt reproduceerbaarheid: je kan achteraf nagaan welke bron werd gelezen, welke tool-call gebeurde en op basis van welke input een beslissing genomen werd.

Een typische flow in een bugfixscenario is technisch vrij lineair. De agent leest het issue, haalt relevante codecontext op, vraagt documentatiefragmenten op voor het verwachte contract, voert een patchtaak uit en lanceert verificatiecommando's. Elke stap heeft een duidelijk I/O-profiel, en juist dat maakt auditing mogelijk. In gereguleerde omgevingen is dat belangrijker dan modelkwaliteit alleen: je moet niet enkel correcte code hebben, maar ook kunnen aantonen hoe ze tot stand kwam.

Voor security en governance heeft MCP twee kanten. Positief is dat je permissies expliciet kan modelleren en tooling kan beperken tot wat nodig is. Het risico is dat te brede tooltoegang een groot aanvalsoppervlak creëert, zeker wanneer gevoelige data via context naar modelinput kan lekken. Praktisch betekent dit: least-privilege op toolniveau, logging van tool-calls, redactie van secrets, en duidelijke scheiding tussen omgevingen (development, staging, productie).

## Code schrijven met meerdere agents (agentic AI)

Multi-agent werken is vooral een parallelisatie- en coördinatievraagstuk. Het doel is niet om meerdere modellen hetzelfde werk te laten doen, maar om taken op te splitsen in onafhankelijke werkpaden met minimale conflictkans. In codebases betekent dat meestal: gescheiden write-sets, afgesproken interfaces en een vaste integratievolgorde.

Een praktisch patroon is dat één orchestrator-agent de globale taak bewaakt, terwijl andere agents gespecialiseerd werk doen, zoals productcode aanpassen, tests uitbreiden en regressies analyseren. Dit lijkt op een klein ontwikkelteam: specialisatie verhoogt tempo, maar alleen als contracten tussen de rollen duidelijk zijn. Als twee agents tegelijk dezelfde files of semantiek wijzigen zonder afspraak, krijg je mergeconflicten en inconsistent gedrag.

Technisch helpt het om multi-agent taken te behandelen zoals concurrerende systemen: vermijd gedeelde mutable state, definieer heldere grenzen, en voer een deterministische integratiestap uit. In praktijk vertaalt dat zich naar een verplicht eindpunt met menselijke review en CI-validatie. Multi-agent output is pas nuttig wanneer integratie en verificatie even serieus zijn als de parallelle uitvoering.

## Gevaren en faalmodi van software engineering met AI

Het bekendste risico is dat outputvolume sneller stijgt dan kwaliteitscapaciteit. Teams produceren meer code per tijdseenheid, maar review, testonderhoud en architectuurbewaking schalen niet automatisch mee. Daardoor kan defectdichtheid op termijn stijgen, zelfs als individuele patches er verzorgd uitzien.

Daarnaast ontstaan veiligheidsrisico's op subtielere manieren dan klassieke syntaxfouten. Een model kan correcte businesslogica schrijven maar onveilige foutmeldingen loggen, autorisatiechecks vergeten op minder zichtbare codepaden, of dependencykeuzes voorstellen met onbekende securitystatus. Omdat de code vaak "professioneel" oogt, worden dit soort fouten later ontdekt dan gewone compileerfouten.

Er is ook een architecturaal risico: lokale optimalisaties zonder globaal ontwerpkader. Als AI vooral gebruikt wordt voor taakjes zonder expliciete systeemvisie, verschuift het ontwerp van coherent naar opportunistisch. Dat zie je niet meteen, maar wel bij latere uitbreidingen, waar veranderingen onverwacht veel neveneffecten krijgen.

Ten slotte is er een vaardigheidsrisico. Wie structureel output accepteert zonder zelf te modelleren waarom iets correct is, verliest op termijn foutzoekvermogen en ontwerpdiscipline. In een opleiding is dat extra relevant: AI moet analytisch denken ondersteunen, niet vervangen.

## Team-guardrails in de praktijk

Technische guardrails moeten afdwingbaar zijn, niet louter intentioneel. Een teamafspraak zoals "we reviewen goed" is te vaag; beter is een controlepunt dat objectief af te tekenen valt, bijvoorbeeld verplichte testuitvoer, expliciete impactanalyse en verplicht onderscheid tussen AI-gegenereerde en handmatige wijzigingen in de PR-beschrijving.

In mature teams zie je dat AI-governance geïntegreerd wordt in bestaande engineeringprocessen: branch policies, CI gates, code ownership en security checks. Zo voorkom je dat AI een parallel proces creëert met lagere normen. Het doel is precies het omgekeerde: hogere snelheid binnen dezelfde of strengere kwaliteitsgrenzen.

Security-afspraken verdienen extra precisie. Geen secrets in prompts is een begin, maar onvoldoende. Je hebt ook regels nodig rond toegelaten datasets, dataretentie, logging van tool-interacties en incidentrespons bij mogelijke datalekken. Zeker bij connectoren naar issue trackers, repositories en interne documentatie moet duidelijk zijn welke data naar het model mag en welke expliciet geblokkeerd is.

Een nuttige praktijk is om periodiek niet alleen featuresnelheid te meten, maar ook kwaliteitsindicatoren zoals regressieratio, hotfixfrequentie en reviewdoorlooptijd. Als AI-adoptie "sneller" lijkt maar kwaliteitskosten verhoogt, moet je proces worden bijgesteld. Engineering blijft optimalisatie onder constraints, niet maximalisatie van gegenereerde code.

## Korte cheat sheet

Als samenvatting voor de praktijk: behandel een LLM als een probabilistische code-assistent die goed is in patronen maar geen formele correctheid garandeert; stuur het model met scherpe specificatiecontext in plaats van vage wensen; kies toolvorm op basis van taaktype en verificatiebehoefte; gebruik agents voor iteratieve workflows met duidelijke stopcriteria; gebruik MCP als gecontroleerde contextlaag met expliciete permissies; en behoud altijd menselijke eindverantwoordelijkheid voor correctness, security en onderhoudbaarheid.
