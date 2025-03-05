---
title: 'In Python (met pip/venv)'
weight: 4
draft: true
author: Arne Duyver
---

## Pip and venv
Dependency management in Python kan efficiënt worden afgehandeld met `pip` en `venv`. Deze tools helpen ervoor te zorgen dat je projecten de benodigde libraries hebben en dat verschillende projecten elkaar niet verstoren door **conflicting dependencies**. 

`pip` is de package manager voor Python. Hiermee kun je extra libraries en dependencies installeren en beheren die niet zijn opgenomen in de standaardbibliotheek. Om een pakket te installeren met pip, kun je de volgende command prompt gebruiken:
```bash
pip install <package_name>
```
Je kunt ook de versie van het pakket specificeren die je wilt installeren:
```bash
pip install package_name==1.2.3
```
Om de dependencies van je project bij te houden, kun je een `requirements.txt`-bestand maken waarin alle packages en hun versies worden vermeld. Dit bestand kan worden gegenereerd met:
```bash
pip freeze > requirements.txt
```
Om de dependencies in `requirements.txt` dan ergens te installeren, kun je het volgende commando gebruiken:
```bash
pip install -r requirements.txt
```

`venv` is een module die ondersteuning biedt voor het maken van lichte, **geïsoleerde** Python-omgevingen. Je installeer de module opt je WSL via `sudo apt install python3.12-venv -y`. Venv is vooral handig wanneer je meerdere projecten hebt met verschillende dependencies en zeker bij meerdere projecten met verschillende versies van dezelfde dependencies. Om **een virtuele omgeving** (virtual environment = venv) te maken, kun je de volgende opdracht gebruiken (dit hoeft niet specifiek in de directory van je prject te zijn):
```bash
python3 -m venv <myenv_name>
```
Hiermee wordt een directory genaamd `myenv_name` gemaakt die een kopie van de Python-interpreter en een pip-executable bestand bevat. Om de virtuele omgeving te activeren, gebruik je:
```bash
source <myenv_name>/bin/activate
```
{{% notice info %}}
Je kan `venv` ook op Windows gebruiken maar dan gebruik je het commando: `<myenv_name>\Scripts\activate` om de virtual environment te activeren.
{{% /notice %}}

Zodra de virtuele omgeving is geactiveerd, worden alle packages die met pip worden geïnstalleerd beperkt tot deze omgeving, zodat de dependencies van je project geïsoleerd blijven van andere projecten. Om de virtuele omgeving te deactiveren, voer je eenvoudigweg volgend commando uit:
```bash
deactivate
```
Je zal dan zien dat je CLI je dan ook toont in welke 'venv' je je bevindt: 
```bash
(<myenv_name>) arne@LT3210121:~/myproject$
```

Door pip en venv te combineren, kun je effectief dependencies beheren, meerdere projectomgevingen simpel onderhouden en conflicten tussen verschillende projecten vermijden. Deze aanpak zorgt ervoor dat je projecten reproduceerbaar blijven en eenvoudig op verschillende systemen kunnen worden opgezet.

## Voorbeeld 'hoger lager'
Hieronder staat een voorbeeld van hoe je het 'hoger lager'-project kunt inrichten als een Python-project met een virtual environment, een requirements.txt-file voor de externe JSON-library (via `simplejson`) en met de `scorebord.py` als interne dependency in een `libs`-directory. De projectstructuur ziet er dan als volgt uit:
```bash
higher_lower_python/
├── app.py
├── requirements.txt
└── libs/
    ├── __init__.py
    └── scorebord.py
└── higher_lower_venv/
    ├── ...
```
Creëer en start een 'venv' voor dit project met zodat de `higher_lower_venv`-directory in je projectdirectory wordt aangemaakt:
```bash
python3 -m venv higher_lower_venv
source higher_lower_venv/bin/activate
```
`Requirements.txt` bevat nu enkel volgende regel: `simplejson==3.20.1` en kan je installeren in de 'venv' met `pip install -r requirements.txt`.

Je `main.py` ziet er dan als volgt uit, waarbij de `if __name__ == '__main__':` een indicatie geeft dat deze file de entrypoint tot onze applicatie is:
<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>main.py</code></b></i>🔽</summary>
<p>

```python
import random
from libs.scorebord import Scoreboard

def main():
    # Laad het scorebord vanuit het JSON-bestand (als het bestaat)
    sb = Scoreboard.load_from_json('highscores.json')
    
    print("Welcome to the Higher or Lower game!")
    print("This is the current leaderboard:\n", sb, "\n")
    print("Guess if the next number will be higher or lower.")
    
    current_number = random.randint(1, 100)
    score = 0
    playing = True

    while playing:
        print("Current number:", current_number)
        guess = input("Will the next number be higher or lower? (h/l): ").lower().strip()
        next_number = random.randint(1, 100)
        if (guess == 'h' and next_number > current_number) or (guess == 'l' and next_number < current_number):
            print("Correct! The next number was:", next_number)
            score += 1
        else:
            print("Wrong! The next number was:", next_number)
            playing = False
        current_number = next_number

    print("Game over! Your final score:", score)
    name = input("What is your name? ")
    sb.add(name, score)
    print("This is the new leaderboard:\n", sb, "\n")
    Scoreboard.save_to_json('highscores.json', sb)

if __name__ == '__main__':
    main()
```

</p>
</details>

### Bestand: libs/__init__.py
Dit bestand kan leeg zijn, maar het zorgt ervoor dat Python de map libs als package herkent.

{{% notice info %}}
Wil je meer info over de conventies over het werken met interne libraries in python projecten, bekijk dan [deze video](https://www.youtube.com/watch?v=EH-TFaX-R-o).
{{% /notice %}}

### Interne dependency: scorebord.py
Dit is de interne dependency die het scorebord beheert. Hierin gebruiken we de externe JSON-library (simplejson) om de gegevens op te slaan en te laden.
<details closed>
<summary><i><b>Klik hier om de code te zien/verbergen voor <code>./libs/scorebord.py</code></b></i>🔽</summary>
<p>

```python
import simplejson as json

class Scoreboard:
    def __init__(self):
        self.scores = []

    def add(self, name, score):
        self.scores.append({'name': name, 'score': score})

    def total_score(self, name):
        return sum(player['score'] for player in self.scores if player['name'] == name)

    def get_winner(self):
        if not self.scores:
            return "No players yet"
        return max(self.scores, key=lambda player: player['score'])['name']

    def __str__(self):
        # Sorteer de scores aflopend en formatteer de output
        sorted_scores = sorted(self.scores, key=lambda player: player['score'], reverse=True)
        return "\n".join(f"{player['name']}: {player['score']}" for player in sorted_scores)

    @staticmethod
    def save_to_json(filename, scoreboard):
        with open(filename, 'w') as f:
            json.dump(scoreboard.scores, f, indent=4)

    @staticmethod
    def load_from_json(filename):
        sb = Scoreboard()
        try:
            with open(filename, 'r') as f:
                scores = json.load(f)
                if scores is not None:
                    sb.scores = scores
        except (FileNotFoundError, json.JSONDecodeError):
            # Bestand bestaat niet of is ongeldig, geef een nieuw scorebord terug.
            pass
        return sb
```

</p>
</details>

{{% notice info %}}
Meer info over het gebruik van classes in Python vind je [hier](https://www.youtube.com/watch?v=ZDa-Z5JzLYM).
{{% /notice %}}

### Builden/runnen
Je zou dit project nu kunnen laten compileren tot een binary met bijvoorbeeld `pyinstaller` zoals we gezien hebben in [build tools voor python](/3-build-systems-makefiles/build-systems-python.md), maar we kunnen het project ook simpelweg uitvoeren met python via:
```bash
python3 ./app.py
```

_De bronbestanden voor dit voorbeeld kan je ook in deze [github repo terugvinden](). Merk op dat in dit project de 'venv' niet is opgenomen in het versiebeheer via de `higher_lower_venv/` in de `./.gitignore`-file wat de conventie is._