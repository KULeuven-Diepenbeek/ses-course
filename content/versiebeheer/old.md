---
title: 'Versiebeheer oude versie'
weight: 5
draft: true
author: Wouter Groeneveld
---

## In de praktijk: Git

Git is een _gedecentraliseerd_ versiebeheer systeem waarbij de hele repository inclusief historiek lokaal wordt geplaatst zodra een `clone` commando wordt uitgevoerd. Oudere _gecentraliseerde_ systemen zoals SVN en CVS bewaren (meta-)data op één centrale plaats: de version control server. Voor dit vak wordt resoluut voor git gekozen. 

Onderstaande Figuur geeft het verschil weer tussen een _gecentraliseerd_ versioneringssysteem, zoals SVN, CVS en MS SourceSafe, en een _gedecentraliseerd_ systeem, zoals Git. Elke gebuiker heeft een kopie van de volledige repository op zijn lokale harde schijf staan. Bij SVN communiceren 'working copies' met de server. Bij Git communiceren repositories (inclusief volledige history) met eender welke andere repository. 'Toevallig' is dat meestal een centrale server, zoals [Github.com](https://github.com), [Gitlab.com](https://gitlab.com) of [BitBucket.com](https://bitbucket.com). 

![](/img/teaching/ses/svngit.png "SVN VS Git")

([Image Src](http://sgdev-blog.blogspot.com/2014_03_01_archive.html))

Vanaf nu wordt verondersteld dat labo oefeningen gecommit worden in `git` op [Github.com](https://github.com): een zeer populair open source software ontwikkelingsplatform dat gebruiksvriendelijk is. Andere populaire platformen zijn Gitlab, Codeberg, Gitea (self-hosted), ... Deze software platformen voorzien een extra laag bovenop Git (de web UI) die het makkelijk maakt op via knoppen acties uit te voeren. Het voegt ook een social media esque aspect toe: comments, requests, issues, ... 

Wat is de beste manier om vanaf nul te leren werken met Git? 

1. [git - the simple guide](http://rogerdudler.github.io/git-guide/); en daarna:
2. Het [Pro Git](https://git-scm.com/book/en/v2) handboek.


{{% notice note %}}
SVN, RCS, MS SourceSafe, CVS, ... zijn allemaal **version control systemen** (VCS). Merk op dat Git géén klassieke "version control" is maar eerder een collaboratieve tool om met meerdere personen tegelijkertijd aan verschillende versies van een project te werken. Er is geen revisienummer dat op elkaar volgt zoals in CVS of SVN (v1, v2, v3, v'), en er is geen logische timestamp. (Zie [git is not revision control](https://blog.feld.me/posts/2018/01/git-is-not-revision-control/)).
Ook, in tegenstelling tot bovenstaande tools, kan je Git ook compleet lokaal gebruiken, zonder ooit te pushen naar een upstream server. Het is dus "self-sufficient": er is geen "server" nodig: dat is je PC zelf. 
{{% /notice %}}

### De Git workflow

Een typische workflow is als volgt:

1. `git clone [url]`: Maakt een lokale repository aan die je op Github hebt gecreëerd. Het commando maakt een subdirectory aan.
2. Doe je programmeerwerk.
3. `git status` en `git diff`: Bekijk lokale changes voordat ze naar de server gaan.
4. `git add [.]`: Geef aan welke changes staged worden voor commit
5. `git commit -m [comment]`: commit naar je **lokale** repository. Wijzingen moeten nu nog naar de Github server worden gesynchroniseerd. 
6. `git push`: push lokale commits naar de Github server. Vanaf nu kan eender wie die meewerkt aan deze repository de wijzigingen downloaden op zijn lokaal systeem.
7. `git pull`: pull remote changes naar je lokale repository. Wijzigingen moeten altijd eerst gepushed worden voordat iemand anders kan pullen. 

De output van `git status` ziet er zo uit:

<pre>
Wouters-MacBook-Air:brainbaking wgroenev$ git status
On branch master
Your branch is up-to-date with 'origin/master'.

Changes not staged for commit:
  (use "git add/rm <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

    modified:   content/teaching/cpp/labo-1.md
    modified:   content/teaching/cpp/labo-2.md
    modified:   content/teaching/cpp/labo-3.md
    modified:   content/teaching/cpp/labo-4.md
    modified:   content/teaching/cpp/labo-5.md
    modified:   content/teaching/cpp/labo-6.md
    deleted:    docs/CNAME
    deleted:    docs/essays/i-am-jealous-of-my-dog/index.html
</pre>

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

#### Ik zie niet alle branches, hoe komt dat?

Een `git pull` commando zal soms in de console 'new branch [branchnaam]' afdrukken, maar toch zal je deze niet tot je beschikking hebben met het `git branch` commando. Dat komt omdat branches dan ook nog (lokaal) **getracked** moeten worden:

1. Controleer welke branch je lokaal wilt volgen met `git branch -r`
2. Selecteer een bepaalde branch, waarschijnlijk `origin/[naam]`
3. Track die branch lokaal met `git branch --track [naam] origin/[naam]`
4. Vanaf nu kan je ook switchen naar die branch, controleer met `git branch`

Merk op dat een branch verwijderen met `git branch -d` enkel gaat bij _lokale branches_. Een _remote_ branch verwijderen wordt niet met het `branch` subcommando gedaan, maar met `git push origin --delete [branch1] [branch2] ...`.

## Bug tracking met Github

Enkele 'kleine probleempjes' in software worden al snel een hele berg aan grote problemen als er niet op tijd iets aan wordt gedaan. Bedrijven beheren deze problemen (_issues_) met een bug tracking systeem, waar alle door klant of collega gemeldde fouten van het systeem in worden gelogd en opgevolgd. Op die manier kan een ontwikkelaar op zoek naar werk in deze lijst de hoge prioritaire problemen identificeren, oplossen, en terug koppelen naar de melder. 

{{<mermaid>}}
graph LR;
    Bug[Bug discovery]
    Report[Bug report]
    Backlog[Bug in backlog met issues]
    Prio[Bug in behandeling]
    Fix[Bug fixed]
    Bug --> Report
    Report --> Backlog
    Backlog --> Prio
    Prio --> Fix
{{< /mermaid >}}

De inhoud van deze stappen hangt af van bedrijf tot bedrijf, maar het skelet blijft hetzelfde. Bugs worden typisch gereproduceerd door middel van [unit testen](/tdd) op een **bepaalde git branch** om de bestaande ontwikkeling van nieuwe features niet in de weg te lopen. Wanneer het probleem is opgelost, wordt deze branch gemerged met de _master_ branch:

{{<mermaid>}}
graph LR;
    masterx[Master branch x]
    mastery[Master branch y]
    bugbranch[Create bug branch]
    bugfix[Bugfix on branch]
    masterx --> mastery
    masterx --> bugbranch
    bugbranch --> bugfix
    bugfix --> mastery
{{< /mermaid >}}

[Github Issues](https://github.com/KULeuven-Diepenbeek/sessylibrary/issues) is een minimalistische feature van Github die het mogelijk maakt om zulke bugs op te volgen. Een nieuw issue openen en een beschrijving van het probleem meegeven (samen met stappen om het te reproduceren), maakt een nieuw item aan dat standaard op '_open_' staat. Dit kan worden toegewezen aan personen, en daarna worden '_closed_', om aan te geven dat ofwel het probleem is opgelost, ofwel het geen echt probleem was. Issues kunnen worden gecategoriseerd door middel van labels. 

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

## Extra leermateriaal

- [Pro Git](https://git-scm.com/book/en/v2) handboek, hoofdstuk 1 tot en met 3.
- [git - the simple guide](http://rogerdudler.github.io/git-guide/)
- [Code Forest: Git VS SVN](https://www.codeforest.net/git-vs-svn)
- [the Harvard.edu guide to Git](https://cs61.seas.harvard.edu/site/ref/git/)
