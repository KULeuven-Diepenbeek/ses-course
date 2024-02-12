---
title: 'Remote repositories: Github'
weight: 3
author: Wouter Groeneveld en Arne Duyver
draft: false
---


## Types van versiebeheer systemen

Git is een _gedecentraliseerd_ versiebeheer systeem waarbij de hele repository inclusief historiek lokaal wordt geplaatst zodra een `clone` commando wordt uitgevoerd. Oudere _gecentraliseerde_ systemen zoals SVN en CVS bewaren (meta-)data op één centrale plaats: de version control server. Voor dit vak wordt resoluut voor git gekozen. 

Onderstaande Figuur geeft het verschil weer tussen een _gecentraliseerd_ versioneringssysteem, zoals SVN, CVS en MS SourceSafe, en een _gedecentraliseerd_ systeem, zoals Git. Elke gebuiker heeft een kopie van de volledige repository op zijn lokale harde schijf staan. Bij SVN communiceren 'working copies' met de server. Bij Git communiceren repositories (inclusief volledige history) met eender welke andere repository. 'Toevallig' is dat meestal een centrale server, zoals [Github.com](https://github.com), [Gitlab.com](https://gitlab.com) of [BitBucket.com](https://bitbucket.com). 

![](/img/teaching/ses/svngit.png "SVN VS Git")

([Image Src](http://sgdev-blog.blogspot.com/2014_03_01_archive.html))

Andere populaire platformen zijn Gitlab, Codeberg, Gitea (self-hosted), ... Deze software platformen voorzien een extra laag bovenop Git (de web UI) die het makkelijk maakt op via knoppen acties uit te voeren. Het voegt ook een social media esque aspect toe: comments, requests, issues, ... 

{{% notice note %}}
SVN, RCS, MS SourceSafe, CVS, ... zijn allemaal **version control systemen** (VCS). Merk op dat Git géén klassieke "version control" is maar eerder een collaboratieve tool om met meerdere personen tegelijkertijd aan verschillende versies van een project te werken. Er is geen revisienummer dat op elkaar volgt zoals in CVS of SVN (v1, v2, v3, v'), en er is geen logische timestamp. (Zie [git is not revision control](https://blog.feld.me/posts/2018/01/git-is-not-revision-control/)).
Ook, in tegenstelling tot bovenstaande tools, kan je Git ook compleet lokaal gebruiken, zonder ooit te pushen naar een upstream server. Het is dus "self-sufficient": er is geen "server" nodig: dat is je PC zelf. 
{{% /notice %}}

## Remote repository met Github

>"... GitHub is a website and cloud-based service that helps developers store and manage their code, as well as track and control changes to their code. ..."
><cite><a href="https://kinsta.com/knowledgebase/what-is-github/">source</a></cite>

Cloud based storage is zeer populair geworden en voor goede redenen:
- Je hebt een backup van al je data.
- Je kan op meerdere verschillende pc's aan hetzelde project werken zonder met een USB-stick dingen te copy pasten.
- Je kan met meerdere personen aan hetzelfde project werken zonder doorsturen van verschillende file versies via mail.

Github is zeer populair en wordt door veel softwareontwikkelaars en bedrijven gebruikt. Wij zullen dit platform dan ook gebruiken in dit opleidingsonderdeel en het is ook aangeraden dit in de toekomst voor je softwareprojecten te gebruiken.

*GitHub is nu eigendom van Microsoft en is closed source software. Je kan het wel de meeste features wel volledig gratis gebruiken. Wil je later toch liever een open source alternatief gebruiken? Dan is Gitlab een goed alternatief en werkt zeer gelijkaardig aan Github*

### Een account aanmaken, 2FA instellen, SSH key toevoegen
Voordat we van start kunnen gaan moeten we eerst een gratis account aanmaken. Verder moeten we ook nog een aantal belangrijke instellingen veranderen voordat we van de services gebruik kunnen maken.

Aangezien het stappenplan voor het aanmaken van een account en de gewenste instellingen wel eens kan wijzigen refereren we hieronder naar de officiële bronnen die door de ontwikkelaar up-to-date gehouden worden:
- **[Account aanmaken op Github](https://docs.github.com/en/get-started/start-your-journey/creating-an-account-on-github)** (**Let op!** gebruik hetzelfde emailadres en dezelfde username die je hebt geconfigureerd hebt in Git om het process zo vlot mogelijk te laten verlopen)
- **[2FA instellen voor je account](https://docs.github.com/en/authentication/securing-your-account-with-two-factor-authentication-2fa)** (**Let op!** Verplicht sinds eind 2023)
- **[SSH instellen voor je account](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)** (**Let op!** Je kan geen contributies meer pushen naar repositories over `https` met enkel je username en wachtwoord)

##### Handige video-tutorials:
- [YT - 2FA instellen voor je account](https://www.youtube.com/watch?v=zzY32wTAxKU&list=PLh40eaRJ4YkG9C_xx_E7_Z1Fe0P1eQtc5)
- [YT - SSH instellen voor je account](https://www.youtube.com/watch?v=snCP3c7wXw0&list=PLh40eaRJ4YkG9C_xx_E7_Z1Fe0P1eQtc5)

### Repositories clonen en veranderingen fetchen, pullen & pushen
Je kan via de webinterface van Github nieuwe repositories aanmaken. Github geeft je dan alle nodige instructies/commando's om oftewel die remote repo te koppelen aan een bestaande lokale Git directory of een nieuwe lege repo lokaal te clonen.

Vanaf nu bestaat er een link tussen de lokale repository en de remote repository.
- Je kan nieuwe aanpassingen van je remote repository `pullen` naar (&asymp; synchroniseren met) je lokale repo met:
    - **`$ git pull`**: Dit haalt alle veranderingen binnen en past ze onmiddelijk toe
    - **`$ git fetch`**: Dit haalt alle veranderingen binnen maar zo worden nog niet gemerged met jouw lokale versie. Dit doe je met **`$ git merge`**. (`git pull` is dus eigenlijk een aaneenschakeling van `git fetch` en `git merge`)
- Je kan nieuwe aanpassingen van je lokale repository `pushen` naar je remote repo met:
    - **`$ git push origin`**: Dit pusht enkel je huidige brach.
    - **`$ git push origin --all`**: Dit pusht de wijzigingen op alle braches.
    - **`$ git push origin --tags`**: Dit pusht alle tags die nog niet aanwezig zijn op de remote repo.

{{% notice warning %}}
Voeg files met gevoelige informatie zoals wachtwoorden onmiddellijk toe aan je **.gitignore**-file **VOORDAT** je een push doet naar de remote repository. Eens een bestand eenmaal gepushed is naar de remote zal er namelijk altijd een kopie van op het internet te vinden zijn en dat wil je natuurlijk niet als het gaat over je wachtwoord of api-key bijvoorbeeld.
{{% /notice %}}

### Collaboration made easy

Door een teamgenoot als collaborator toe te voegen aan je remote repository kan hij/zij de repo lokaal clonen en ook wijzigingen kunnen `pushen` en `pullen`. Door dit te combineren met `branching` waarbij elke collaborator bijvoorbeeld een eigen branch aanmaakt, kan je zeer efficiënte online workflow creëren om remote te kunnen samen werken aan software projecten.
</br>In principe kan iedereen een *public* remote repository clonen, maar ze gaan geen veranderingen kunnen pushen. (Hiervoor gebruik je `fork` en `pull requests`)

**Collaborative workflow:** to minimize merge conflicts and other problems
- Regularly `pull` all changes into your local directory.
- Regularly keep your branch up-to-date by merging master branch into it.
- (Merge the changes on your branch to the master branch when nessecary)
- Regularly `push` to remote directory.

### Forking en pull requests
Je kan een public repository waar je geen collaborator van bent `forken` (&asymp; kopiëren) naar je eigen Github. Zo kan je toch nieuwe aanpassingen pushen naar 'jouw kopie' van de repository. Github houd echter bij vanwaar jouw fork afkomstig is. Op die manier kan je een `pull request` aanvragen aan de eigenaar van de originele repo. Die persoon kan jouw aanpassingen dan nakijken en toevoegen aan zijn/haar repo door de `pull request` te accepteren en `mergen`.

Een waarheidsgetrouw voorbeeld ziet er als volgt uit:
- Je wil een kleine software developer helpen door een bug op te lossen.
- Je `forked` de repository van de software.
- Je fixed de bug, commit en pushed de aanpassing naar jouw fork van de software repo.
- Je dient een `pull request` in bij de originele repo van de software.
- De eigenaar vindt jouw oplossing geweldig en `merged` hem met zijn eigen repo.

Op deze manier kan je zeer gemakkelijk je steentje bijdragen aan de wereld van open source software en dit maakt het principe van open source ook juist mede zo aantrekkelijk.

## Oefeningen
1. Maak in je Github een repository aan voor je Git directory uit [de vorige oefenreeks](/versiebeheer/lokaal-versiebeheer-met-git/#oefeningen).
2. Koppel je lokale repo aan je remote repo en push je versiegeschiedenis naar Github.
    - Push ook je tags mee naar de remote repo en controleer of ze te zien zijn bij `versions`
3. Clone op een andere plaats op je computer je remote repository
4. Maak veranderingen aan de files, commit en push
5. Fetch of pull nu die veranderingen in de originele directory.
    - Denk na over het verschil tussen `fetch` en `pull`.
6. Bestudeer de online user interface van Github voor het beheren van je remote repositories
7. Voeg een medestudent toe als collaborator aan het project.
8. Laat die student je repo clonen, veranderingen aanbrenge, committen en pushen.
9. Pull nu de veranderingen van je teamgenoot in je eigen local repository
10. Laat je als collaborator verwijderen
11. Fork nu de repository van je medestudent, maak aanpassingen en submit een pull request.
12. Bestudeer nu grondig de `git log` van je repository. Kan je alle veranderingen volgen?
13. (Submit een issue in je eigen remote repository, los de issue op met een commit en gebruik de issuenummer met het sleutelwoord 'Close' (`Close #<issuenummer>`) om de issue automatisch op te lossen)

## Extra
### De Git workflow

Een typische workflow is als volgt:

1. `git clone [url]`: Maakt een lokale repository aan die je op Github hebt gecreëerd. Het commando maakt een subdirectory aan.
2. Doe je programmeerwerk.
3. `git status` en `git diff`: Bekijk lokale changes voordat ze naar de server gaan.
4. `git add [.]`: Geef aan welke changes staged worden voor commit
5. `git commit -m [comment]`: commit naar je **lokale** repository. Wijzingen moeten nu nog naar de Github server worden gesynchroniseerd. 
6. `git push`: push lokale commits naar de Github server. Vanaf nu kan eender wie die meewerkt aan deze repository de wijzigingen downloaden op zijn lokaal systeem.
7. `git pull`: pull remote changes naar je lokale repository. Wijzigingen moeten altijd eerst gepushed worden voordat iemand anders kan pullen. 

### Ik zie niet alle branches lokaal die wel op Github staan, hoe komt dat?

Een `git pull` commando zal soms in de console 'new branch [branchnaam]' afdrukken, maar toch zal je deze niet tot je beschikking hebben met het `git branch` commando. Dat komt omdat branches dan ook nog (lokaal) **getracked** moeten worden:

1. Controleer welke branch je lokaal wilt volgen met `git branch -r`
2. Selecteer een bepaalde branch, waarschijnlijk `origin/[naam]`
3. Track die branch lokaal met `git branch --track [naam] origin/[naam]`
4. Vanaf nu kan je ook switchen naar die branch, controleer met `git branch`

Merk op dat een branch verwijderen met `git branch -d` enkel gaat bij _lokale branches_. Een _remote_ branch verwijderen wordt niet met het `branch` subcommando gedaan, maar met `git push origin --delete [branch1] [branch2] ...`.

### Bug tracking met Github

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

### Versiebeheer met je favoriete IDE

Sommige IDE's komen met built-in tools of plug-ins om gemakkelijk gebruik te maken van Version Control en remote repositories. Ik raad je echter aan **eerst** voldoende te oefenen met de command line tools zodat je altijd weet wat je aan het doen bent. Op die manier ga je beter en vooral sneller problemen kunnen oplossen. Indien je de 'hard' way onder de knie hebt, kan je natuurlijk overschakelen naar eender welke tool jij het handigste vindt.