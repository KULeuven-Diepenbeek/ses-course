---
title: '3.3 The TDD proglang challenge'
---

## 1. De opgave: Wimbledon

Tennisspelers staan weer klaar om een balletje heen en weer over de netten te sturen. In plaats van een tennisbal, zijn karakters voorzien die deel uitmaken van een mysterieuze zin, die ontcijferd moet worden. 

De decodering van deze `Wimbledon-versleutelde' zin bestaat uit het eerste karakter, gevolgd door het laatste karakter, het tweede karakter, het voorlaatste karakter, het derde karakter, enzovoort.

![](/img/wimbledon-schema.jpg)

Ontcijfer de zinnen zoals aangegeven in bovenstaande figuur om populaire tennis quotes te ontdekken. 

### Voorbeeld input

```
Oea  ie.mtat n
W'entwies u o,cnw ev.erse a ybtb,rta o re
Dtn  enspae srsy oemasntigt s.uo nho ne vL.ki iryl intagia
```

[Download meer voorbeeld input](/vpw/wimbledon-invoer.txt).

### Voorbeeld uitvoer

```
One at a time.
We're not waiters, but boy, can we serve.
Dating a tennis player is risky. Love means nothing to us.
```

[Download meer voorbeeld uitvoer](/vpw/wimbledon-uitvoer.txt).

### Oplossing in JavaScript

Signatuur: `output = decrypt(input)`

#### Testen

Geschreven met test harnas en framework https://jestjs.io/

```js
const decrypt = require('../src/decrypt')

describe("wimbledon decrypt", () => {
    test("decrypt voorbeeld input 1 resulteert in voorbeeld output 1", () => {
        expect(decrypt("Oea  ie.mtat n")).toEqual("One at a time.")
    })

    test("decrypt voorbeeld input 2 resulteert in voorbeeld output 2", () => {
        expect(decrypt("W'entwies u o,cnw ev.erse a ybtb,rta o re")).toEqual("We're not waiters, but boy, can we serve.")
    })

    test("decrypt voorbeeld input 3 resulteert in voorbeeld output 3", () => {
        expect(decrypt("Dtn  enspae srsy oemasntigt s.uo nho ne vL.ki iryl intagia")).toEqual("Dating a tennis player is risky. Love means nothing to us.")        
    })

    test("decrypt een lege string blijft een lege string", () => {
        expect(decrypt("")).toEqual("")
    })

    test("decrypt null of undefined blijft een lege string", () => {
        expect(decrypt(null)).toEqual("")
        expect(decrypt(undefined)).toEqual("")
    })
})
```

#### Code

```js
Array.prototype.head = function() {
    return this.splice(0, 1)
}
Array.prototype.tail = function() {
    return this.splice(-1)
}

const decrypt = (inputText) => {
    let output = ""
    const invoer = inputText?.split("")

    while(invoer?.length > 0) {
        output += invoer.head()
        if(invoer.length > 0) {
            output += invoer.tail()
        }
    }
    return output
}

module.exports = decrypt
```

Je kan dit zelf verifiëren door bovenstaande code te evalueren in de browser (buiten de `module` regel), en te experimenteren met decrypt calls!

## 2. De challenge

**Start hier:** [Github Classroom](/ses-course/extra/github-classroom/)!

Dit is een _groepsopdracht_. Dat wil zeggen dat de GitHub classroom repositories niet individueel zijn, maar dat je per groepje één repository aanmaakt om te kunnen samenwerken. Puntenverdeling /10: actief meewerken en íets proberen = 10. Het is NIET belangrijk of jullie de opdracht met succes weten af te werken of niet!

Herimplementeer bovenstaande opdracht, **test-first!**, in één van de volgende, voor jullie _totaal onbekende_ (vandaar géén Python/Java/C++/PHP) programmeertalen (nummer = aantal ogen van dobbelsteen):

1. [Julia](https://julialang.org/) **
2. [Go](https://golang.org/) *
3. [Rust](https://www.rust-lang.org/) *
4. [Elexir](https://elixir-lang.org/) **
5. [Ruby](https://www.ruby-lang.org/en/) *
6. [Kotlin](https://kotlinlang.org/) *
7. [Lua](https://www.lua.org/) *
8. [Clojure](https://clojure.org/) **


Je kan _niet kiezen_, maar een worp met een D8 dobbelsteen, het geluk dus, bepaalt welke taal je zal aanwenden:

![](/img/dice.jpg)

https://dm.tools/dice/

De hoeveelheid `*` duiden aan hoe groot de verwachtte uitdaging is. Groepen die de "pech" hebben een moeilijkere taal voorgeschoteld te krijgen: geen paniek. Jullie krijgen een _bonuspunt_ op de volgende taak!

### Tips voor een vliegende start

- Verdeel taken onder jullie team! Gebruik _git_ efficiënt.
- Probeer zo snel mogelijk cmd-line een "`println('hello world')`" programma te compileren. <br/>Zoek hiervoor naar de "Get Started!" knop bij bovenstaande links.
- Ontrafel daarna hoe unit testing werkt in die taal. <br/>De kans is groot dat er meerdere test harnas frameworks bestaan. Kies gewoon degene die je het eerste tegenkomt. 
- Schrijf een "`assertThat(true, is(true))`" test en zoek uit hoe je deze testen uitvoert. 
- Verspil geen tijd met de opgave/testen zelf: de oplossing is er al! Belangrijkere vragen:
    + Hoe loop je over karakters in die taal?
    + Hoe splits je een string, als je denkt dat dat nodig is?

Denk eraan: **RED** (1), **GREEN** (2), **REFACTOR** (3).
