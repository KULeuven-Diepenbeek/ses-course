---
title: 'Versiebeheer oude stukken die nog niet herbruikt werden'
weight: 5
draft: true
author: Wouter Groeneveld
---

## In de praktijk: Git
De output van `git diff` ziet er zo uit:

<pre>
    Wouters-MacBook-Air:brainbaking wgroenev$ git diff
diff --git a/content/teaching/cpp/labo-1.md b/content/teaching/cpp/labo-1.md
index 654a4f6..11f0597 100644
--- a/content/teaching/cpp/labo-1.md
+++ b/content/teaching/cpp/labo-1.md
@@ -4,7 +4,7 @@  disableComments: true
 ---
</pre>

Waarbij de `+++` regels wijzigingen zijn die zijn toegevoegd, en `---` die zijn verwijderd. Zowel bestanden als regels binnen bestanden zijn zichtbaar in de _difference_ tool. 

### Conflicten oplossen

**Lees eerst** [hoofdstuk 2 van het Pro Git boek](https://git-scm.com/book/en/v2/Git-Basics-Getting-a-Git-Repository).

### Branches

**Lees eerst** [hoofdstuk 3 van het Pro Git boek](https://git-scm.com/book/en/v2/Git-Branching-Branches-in-a-Nutshell).

{{% notice warning %}}
Hoofdstuk 1 t.e.m. 3 van het bovenstaande Pro Git boek horen bij de leerstof!
{{% /notice %}}

Een branch aanmaken kan via het `git branch` commando. Dit geeft een overzicht van beschikbare branches, en met een argument maak je een nieuwe aan. `git checkout` laat je switchen naar die branch. Het `*` symbool duidt de actieve branch aan. Hieronder wordt een nieuwe branch genaamd _bugfixbranch_ aangemaakt nadat git toont dat er geen branches zijn. Daarna wordt er naar die branch overgeschakeld:

<pre>
Wouters-Air:sessylibrary wgroeneveld$ git branch
* master
Wouters-Air:sessylibrary wgroeneveld$ git branch bugfixbranch
Wouters-Air:sessylibrary wgroeneveld$ git branch
  bugfixbranch
* master
Wouters-Air:sessylibrary wgroeneveld$ git checkout bugfixbranch
Switched to branch 'bugfixbranch'
Wouters-Air:sessylibrary wgroeneveld$ git branch
* bugfixbranch
  master
</pre>

Merk op dat eerst branchen en dan die branch uitchecken sneller gaat met `bit checkout -b`---_enkel_ als die branch not niet bestaat.

Een branch lokaal committen kan altijd, maar een `push` kan de volgende fout geven: _fatal: The current branch bugfixbranch has no upstream branch._ In dat geval dien je de branch 'upstream' te pushen naar de Github server door middel van `git push --set-upstream origin bugfixbranch`.

Na een branch commit is de volgende knop zichtbaar op Github:

![](/img/teaching/ses/github_compare.png)

De **Compare &amp; Pull Request** knop maakt het mogelijk om wijzigingen op de bugfixbranch tot op de master branch te brengen. Dit kan ook via het commando `git merge bugfixbranch` in de master branch. Een demo toont dit aan. Daarna hebben we de branch niet meer nodig: `git branch -d bugfixbranch`. Merk op dat dit enkel _lokaal_ de kopie van de branch verwijderd. De remote, op Github.com, verwijderen, vereist meer werk: `git push origin --delete bugfixbranch`.

{{% notice note %}}
Geen idee wat de mogelijke opties zijn van een bepaald subcommando? Gebruik `git help [cmd]`, bijvoorbeeld `git help branch`. 
{{% /notice %}}



## <a id="oef"></a>Labo oefeningen

### Opgave 1

Maak een Github.com account aan, als je dat nog niet hebt. Download de commandline tools [https://git-scm.com/downloads](https://git-scm.com/downloads), de officiele distributie, multiplatform, en probeer voor opgave twee de repository 'ses-issue-sandbox' te clonen: `git clone https://github.com/KULeuven-Diepenbeek/ses-issue-sandbox.git`. 

Gebruik het Pro Git boek om kennis te maken met basis commando's van git. Het is de bedoeling om de commandline tools te leren kennen, en _niet_ om met Github Desktop te werken. 

### Opgave 2

Kinderen vermaken zichzelf met een dagje aan zee. Het strand verdient extra veel aandacht, maar niet alle kinderen zijn blijkbaar even tevreden met onze implementatie. Kijk naar de [issue lijst van de repository](https://github.com/KULeuven-Diepenbeek/ses-issue-sandbox/issues) om te kijken wat de wensen zijn van onze gebruikers. 

Kies er eentje uit om op te lossen - het maakt niet uit welke. **Los elke issue op in een aparte branch**! De oplossing bestaat uit twee fases:

1. Implementeer de interface `Playable` door een nieuwe klasse te maken van het object waar het kind mee wenst te spelen aan zee (zie issue).
2. Maak een nieuwe instantie aan van dit object in `SandboxMain`.

Neem een kijkje bij de voorbeeld implementaties `SandCastle`.

Merk op dat je onvoldoende `push` rechten hebt om lokale wijzigingen aan de repository te uploaden naar de Github server. We geven je nu de status van '_collaborator_', zodat iedereen gemachtigd is om wijzigingen door te voeren. 

## Denkvragen

- In welk geval is het aanmaken van een branch een goed idee, in plaats van verder te werken op de (enige) master branch? Lees eerst het Pro Git boek hoofdstuk over branches.
- In welk geval is het pushen van wijzigingen vanuit een branch naar de master branch een goed idee? Wanneer niet?