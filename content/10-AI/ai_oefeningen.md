---
title: "AI in software engineering: oefeningen"
autonumbering: true
weight: 20
toc: true
draft: false
---

## Oefening 1: Next-token-denken

Je krijgt deze context:

```java
public static int abs(int x) {
    if (x < 0) return
```

1. Welke volgende tokens verwacht je dat een LLM hier waarschijnlijk voorstelt?
2. Geef minstens 2 mogelijke vervolgen.
3. Leg uit waarom beide statistisch plausibel zijn.
4. Welke variant heeft jouw voorkeur in productiecode, en waarom?

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Mogelijke vervolgen:

- `-x;`
- `(x * -1);`

Beide zijn plausibel omdat ze hetzelfde gedrag implementeren voor negatieve waarden.
De eerste variant (`-x`) heeft meestal de voorkeur: korter, duidelijker, en idiomatisch Java.
{{% /notice %}}

## Oefening 2: Prompt verbeteren voor Java

Verbeter deze zwakke prompt:

```text
Schrijf code voor een parser.
```

Doel:

1. Maak de prompt concreet voor een Java-project.
2. Voeg constraints toe.
3. Voeg testverwachtingen toe.
4. Beperk welke bestanden mogen wijzigen.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Voorbeeld van sterke prompt:

```text
Doel: implementeer `parseDuration(String s)` in `DurationParser`.
Context: Java 25, inputformaten `45s`, `10m`, `2h`.
Constraints:
- null of lege string -> IllegalArgumentException
- geen externe libraries
- behoud publieke API van DurationParser
Bestanden die mogen wijzigen:
- src/main/java/.../DurationParser.java
- src/test/java/.../DurationParserTest.java
Acceptatiecriteria:
- bestaande tests groen
- voeg tests toe voor null, lege input, onbekende suffix, grote waarden
Output:
- geef eerst kort plan
- lever daarna patch
```
{{% /notice %}}

## Oefening 3: Toolkeuze per scenario

Kies per scenario het best passende tooltype: chat-UI, IDE-integratie, of agentische UI/CLI.
Motiveer telkens kort.

1. Je wil snel het verschil begrijpen tussen optimistic locking en pessimistic locking.
2. Je wil in een bestaande Java-klasse een kleine refactor met 15 regels aanpassen.
3. Je wil in 40 files deprecated API-calls vervangen en alle tests laten draaien.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
1. Chat-UI: conceptuele uitleg en vergelijking.
2. IDE-integratie: lokale kleine wijziging met directe editorcontext.
3. Agentische UI/CLI: repo-brede taak met iteratieve checks en test-run.
{{% /notice %}}

## Oefening 4: Ontwerp een agent

Ontwerp een agent-opdracht voor dit doel:
"Voeg inputvalidatie toe aan alle REST endpoints rond `Customer` in een Spring Boot project."

Beschrijf:

1. doel en scope,
2. toegelaten tools,
3. bestanden die mogen wijzigen,
4. acceptatiecriteria,
5. rapporteringsformaat.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Voorbeeld:

- Doel: valideer request payloads voor Customer endpoints met Bean Validation.
- Scope: alleen controller/request DTO/gerelateerde tests.
- Tools: repository search, editor patching, gradle test.
- Bestanden: `CustomerController`, request DTO's, controller tests.
- Acceptatiecriteria:
  - alle create/update endpoints valideren verplichte velden;
  - invalide input geeft HTTP 400;
  - tests voor success + failure cases.
- Rapportering:
  - lijst gewijzigde files;
  - samenvatting per wijziging;
  - testresultaat;
  - resterende risico's.
{{% /notice %}}

## Oefening 5: MCP-flow en risicoanalyse

Stel dat een agent via MCP toegang heeft tot:

- Git repository,
- issue tracker,
- interne API-documentatie.

Werk een concrete flow uit voor "fix bug #431", en noem minstens 4 risico's met bijhorende mitigatie.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Voorbeeldflow:

1. Lees issue #431 (reproductiestappen, impact).
2. Zoek relevante codepaden in repo.
3. Lees API-contract uit interne docs.
4. Maak patch met minimale scope.
5. Draai unit/integratietests.
6. Rapporteer wijziging + risico's.

Risico + mitigatie:

- Te brede data-toegang -> least privilege per MCP tool.
- Verkeerde bronversie -> expliciete branch/commit pinnen.
- Secret leakage in logs -> redactie en logpolicy.
- Verkeerde interpretatie van issue -> reproduceer bug eerst met test.
{{% /notice %}}

## Oefening 6: Multi-agent taakverdeling

Je moet een Java module refactoren:

- `BillingService` opsplitsen,
- tests uitbreiden,
- documentatie updaten.

Maak een taakverdeling voor 3 agents zodat write-conflicten minimaal zijn.
Definieer ook wie finale integratie doet.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Mogelijke verdeling:

1. Agent A (code): splitst `BillingService` in `InvoiceCalculator` en `BillingFacade`.
2. Agent B (tests): update unit tests en voegt regressietests toe.
3. Agent C (docs): past technische documentatie en ADR-notitie aan.

Conflictbeperking:

- elk met eigen file-scope;
- gezamenlijke interface-contracten vooraf vastleggen;
- integratie door orchestrator (mens of hoofd-agent) na alle checks.
{{% /notice %}}

## Oefening 7: AI-geintroduceerde bug opsporen en herstellen

Een LLM stelde deze code voor:

```java
public static int average(int[] values) {
    int sum = 0;
    for (int v : values) sum += v;
    return sum / values.length;
}
```

1. Welke bug/randvoorwaarde ontbreekt?
2. Schrijf een veilige versie.
3. Voeg minstens 2 JUnit tests toe die het probleem afdekken.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Probleem: deling door nul bij lege array.

Veilige versie:

```java
public static int average(int[] values) {
    if (values == null || values.length == 0) {
        throw new IllegalArgumentException("values must contain at least one element");
    }
    int sum = 0;
    for (int v : values) sum += v;
    return sum / values.length;
}
```

Voorbeeldtests:

- lege array geeft `IllegalArgumentException`.
- `[2, 4, 6]` geeft `4`.
{{% /notice %}}

## Oefening 8: Van AI-output naar productieklare patch

Beschrijf een concrete checklist die je team gebruikt voordat AI-gegenereerde code gemerged mag worden.
Je checklist moet minstens 8 controlepunten bevatten.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Voorbeeldchecklist:

1. Build slaagt lokaal.
2. Nieuwe en bestaande tests slagen.
3. Geen hardcoded secrets of tokens.
4. Inputvalidatie aanwezig op externe inputs.
5. Logging bevat geen gevoelige data.
6. Dependency-wijzigingen zijn expliciet gemotiveerd.
7. Reviewer begrijpt en kan code uitleggen.
8. PR beschrijft welke delen met AI zijn opgesteld.
{{% /notice %}}

## Oefening 9: Evalueer een AI-werkflow voor jouw team

Werk een mini-protocol uit voor studenten die samen aan een project werken:

1. wanneer AI wel/niet gebruiken,
2. hoe prompts gedeeld worden,
3. hoe kwaliteit bewaakt wordt,
4. hoe je voorkomt dat iemand blind code overneemt.

Lever je antwoord als 10 concrete teamafspraken.

{{% notice style=tip title="Modeloplossing" expanded=false %}}
Voorbeeldafspraken:

1. AI mag voor ideevorming, refactorvoorstellen en testsuggesties.
2. Geen directe merge van AI-output.
3. Altijd eerst taak scherp afbakenen.
4. Prompt + output samenvatten in PR.
5. Minstens 1 extra reviewer bij securityrelevante code.
6. Test-first bij bugfixes.
7. Geen secrets in prompts.
8. Onzekere externe claims verifieerbaar citeren.
9. Kleine commits met duidelijke boodschap.
10. Teamretro na elke sprint over AI-kwaliteitseffect.
{{% /notice %}}
