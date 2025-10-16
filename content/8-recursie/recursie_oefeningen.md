---
title: "Recursie: oefeningen"
autonumbering: true
weight: 20
toc: true
draft: false
math: true
---

Je vind tests en skelet-code voor de oefeningen rond recursie op [Github](https://github.com/KULeuven-Diepenbeek/ses-oefeningen-recursie).
## Palindroom

Schrijf een recursieve functie `isPalindrome` die nagaat of een String een palindroom is.
Een String is een palindroom als die hetzelfde is van links naar rechts als van rechts naar links, bijvoorbeeld

- `racecar`
- `level`
- `deified`
- `lepel`
- `droomoord`
- `redder`
- `meetsysteem`
- `koortsmeetsysteemstrook`

## String omkeren

Schrijf een recursieve methode `reverse` om een String om te keren, bijvoorbeeld:

- `Hello` -> `olleH`
- `racecar` -> `racecar`

## Element zoeken in lijst

Schrijf een recursieve functie `<T> int search(List<T> list, T element)` die nagaat of het gegeven element voorkomt in de gegeven lijst.
Als het element voorkomt, wordt de index teruggegeven, anders -1.

- Maak eerst een versie die werkt voor een willekeurige (niet-gesorteerde) lijst.
- Maar vervolgens een snellere versie die werkt voor een _gesorteerde_ lijst, door het te doorzoeken stuk van de lijst telkens halveert en slechts in één van die helften verder gaat zoeken. (_Dit is 'binary search'_)

## Duplicaten verwijderen

Schrijf een recursieve methode `removeDuplicateCharacters` die opeenvolgende dezelfde karakters uit een String verwijdert.
Bijvoorbeeld:

- `aaaaa` -> `a`
- `koortsmeetsysteemstrook` -> `kortsmetsystemstrok`
- `AAAbbCdddAAA` -> `AbCdA`

## Greatest common divisor

Schrijf een functie `gcd` die de grootste gemene deler bepaalt tussen twee getallen.
Maak gebruik van het feit dat, als \\( x \geq y \\), dat dan \\( \gcd(x, y) = \gcd(y, x \\% y) \\) met % de modulo-operatie, en dat \\( \gcd(x, 0) = x \\).

## Snelle macht

Schrijf een recursieve functie `double power(double x, int n)` die \\( x^n \\) berekent met zo weinig mogelijk berekeningen.
Maak gebruik van het feit dat \\( x^{2n} = x^n \cdot x^n \\) en \\( x^{2n+1} = x \cdot x^{2n} \\).

## Sum of digits

Schrijf een recursieve methode `int sumOfDigits(long number)` die de som van de cijfers van een (niet-negatief) getal berekent, en dat herhaalt tot die som kleiner is dan 10.
Bijvoorbeeld: `62984` geeft `6+2+9+8+4 = 29`; dat wordt vervolgens `2+9 = 11`; en dat wordt uiteindelijk `1+1 = 2`.
Het resultaat is dus 2.

## Trap beklimmen

Schrijf een functie om te berekenen hoeveel _verschillende_ manieren er zijn om een trap met \\( n \\) treden op te gaan, als je bij elke stap kan kiezen om 1 of 2 treden tegelijk te nemen.
Bijvoorbeeld, een trap met \\( n = 4 \\) treden kan je op 5 verschillende manieren beklimmen:

<div style="max-width: 500px">

```goat
               _________
            ___|
         ___|
      ___|
______|
```

</div>

1. 1 trede, 1 trede, 1 trede, 1 trede
2. 1 trede, 1 trede, 2 treden
3. 1 trede, 2 treden, 1 trede
4. 2 treden, 1 trede, 1 trede
5. 2 treden, 2 treden

## Gepast betalen

Schrijf een recursieve methode `boolean kanGepastBetalen(int bedrag, List<Integer> munten)` die nagaat of je het gegeven bedrag (uitgedrukt in eurocent) gepast kan betalen met (een deel van) de gegeven munten (en briefjes). Elke munt in de lijst mag slechts één keer gebruikt worden.
Bijvoorbeeld:

- `kanGepastBetalen(20, List.of(50, 10, 10, 5))` geeft **true** terug, want 10+10 = 20.
- `kanGepastBetalen(125, List.of(100, 100, 50, 20, 10, 5 ))` geeft **true** terug, want 100+20+5 = 125.
- `kanGepastBetalen(260, List.of(100, 100, 50, 20, 5 ))` geeft **false** terug: er is geen combinatie van munten die samen 260 geeft.

## Gepast betalen (bis)

Gegeven een lijst van muntwaarden (bv. 5, 10, 20, 50, 100, 200), schrijf een recursieve methode `int countChange(int amount, List<Integer> coinValues)` die bepaal **op hoeveel manieren** je een specifiek bedrag kan betalen.
Je mag nu veronderstellen dat je een voldoende aantal (of oneindig veel) munten van elke opgegeven waarde hebt.

Bijvoorbeeld, met bovenstaande muntwaarden

- kan je een bedrag van 35 betalen op 6 manieren:
  - 7×5
  - 1×10 en 5×5
  - 1×10 en 1×20 en 1×5
  - 2×10 en 3×5
  - 3×10 en 1×5
  - 1×20 en 3×5
- kan je een bedrag van 260 betalen op 646 manieren
- kan je een bedrag van 1000 betalen op 98411 manieren

## Alle prefixen van een String

Schrijf een recursieve functie `allPrefixes` die een Set teruggeeft met alle prefixen van een gegeven String.
Bijvoorbeeld:

- `allPrefixes("cat") == { "", "c", "ca", "cat" }`
- `allPrefixes("Hello") == { "", "H", "He", "Hel", "Hell", "Hello" }`

## Alle interleavings van twee strings

Schrijf een recursieve functie `allInterleavings(String s1, String s2)` die een Set teruggeeft met alle interleavings van de twee strings `s1` en `s2`.
Een interleaving is een nieuwe string met daarin alle karakters van de eerste en de tweede string, in dezelfde volgorde waarin ze in elk van de originele strings voorkomen, maar mogelijk door elkaar.
Bijvoorbeeld:

- `allInterleavings("A", "B") = [AB, BA]`
- `allInterleavings("ABC", "x") = [ABCx, ABxC, AxBC, xABC]`
- `allInterleavings("AB", "xy") = [ABxy, AxBy, AxyB, xABy, xAyB, xyAB]`
- `allInterleavings("ABC", "xy") = [ABCxy, ABxCy, ABxyC, AxBCy, AxByC, AxyBC, xABCy, xAByC, xAyBC, xyABC]`

## Vliegtuigreis

Gegeven een record `Item`:

```java
record Item(String name, int weight, int value) {}
```

wat een item voorstelt dat je mee wil nemen op reis. Het item heeft een naam, een gewicht, en een waarde (hoe graag je het mee wil).

Schrijf een methode `List<Item> pack(List<Item> choices, int maxWeight)` die een keuze maakt uit de gegeven lijst van items, zodanig dat de items met een zo groot mogelijke totaalwaarde kan meenemen zonder dat het totale gewicht hoger wordt dan het maximumgewicht (opgelegd door de vliegtuigmaatschappij).

Bijvoorbeeld, met vier items (A, 50kg, 10), (B, 20kg, 5), (C, 10kg, 2), en (D, 5kg, 1):

- met een totaal toegelaten gewicht van 100kg kan je alle items meenemen, voor een totale waarde van 18;
- met een totaal toegelaten gewicht van 60kg kan je best items A en C meenemen, voor een totale waarde van 12;
- met een totaal toegelaten gewicht van 20kg kan je best item B meenemen, voor een totale waarde van 5;

## Powerset

Schrijf een recursieve functie `Set<Set<T>> powerset(Set<T> s)` die de powerset berekent van de gegeven set `s`.
De powerset is de set van alle deelverzamelingen van `s`.
Bijvoorbeeld:

- `powerset(Set.of("A", "B")) = [[], [A], [B], [A, B]]`
- `powerset(Set.of("A", "B", "C")) = [[], [A], [B], [C], [A, B], [A, C], [B, C], [A, B, C]]`

## Alle permutaties van een lijst berekenen

Schrijf een functie `allPermutations` die een Set teruggeeft met alle permutaties van een gegeven lijst.
Bijvoorbeeld:

- `allPermutations(List.of("A", "B")) = [ [A, B], [B, A] ]`
- `allPermutations(List.of("A", "B", "C")) = [ [A, B, C], [A, C, B], [B, A, C], [B, C, A], [C, A, B], [C, B, A] ]`


## Gebalanceerde haakjes

Schrijf met behulp van recursie een methode `boolean balancedParentheses(String s)` die nagaat of alle haakjes in de gegeven string gebalanceerd zijn.
Bijvoorbeeld:

- `()` geeft **true** terug
- `())` geeft **false** terug
- `()()` geeft **true** terug
- `)(` geeft **false** terug
- `((` geeft **false** terug
- `abc(def(xy))z` geeft **true** terug
- `a(bc(def(xy))z` geeft **false** terug

_Hint: gebruik het worker-wrapper-patroon, en denk (in plaats van 'is deze string gebalanceerd') aan een andere (gerelateerde) vraag die wél nuttig kan zijn om het probleem recursief op te lossen._

## Longest common subsequence

Schrijf een recursieve methode `String longestCommonSubsequence(String s1, String s2)` die de langste reeks karakters teruggeeft die in beide strings in dezelfde volgorde terugkomen (niet noodzakelijk aaneensluitend). Als er meerdere oplossingen zijn, maakt het niet uit welke je teruggeeft.


Bijvoorbeeld:

<style>
  pre.lcs-pre, pre.lcs-pre * {
    display: inline;
    margin: 0;
    padding: 0;
  }
  pre.lcs-pre strong {
    color: red;
  }
  pre.lcs-pre {
    padding: 0.2rem;
  }

</style>

- `longestCommonSubsequence("gitaarsnaar", "imaginair") == "ginar" of "ianar"`: <pre class="lcs-pre">**gi**taars**na**a**r**</pre> en <pre  class="lcs-pre">ima**gina**i**r**</pre>, of <pre  class="lcs-pre">g**i**t**a**ars**na**a**r**</pre> en <pre  class="lcs-pre">**i**m**a**gi**na**i**r**</pre>

- `longestCommonSubsequence("aardappel", "adoptie") == "adpe"`: <pre  class="lcs-pre">**a**ar**d**a**p**p**e**l</pre> en <pre  class="lcs-pre">**ad**o**p**ti**e**</pre>


- `longestCommonSubsequence("sterrenstelselsamenstellingsanalyseinstrument", "restaurantkeukenapparatuurontwerpmethodiek") == "restantenaantrmet"` (met dank aan ChatGPT voor de suggestie): <pre class="lcs-pre">ster**re**n**st**elsels**a**me**n**s**te**lli**n**gs**a**n**a**lysei**n**s**tr**u**me**n**t**</pre> en <pre class="lcs-pre">**resta**ura**nt**k**e**uke**na**pp**a**ratuuro**nt**we**r**p**met**hodiek</pre>

_Het laatste voorbeeld zal waarschijnlijk veel te lang duren. Je kan nadenken of je je algoritme wat 'slimmer' kan maken._

## Boomstructuur

> In deze oefening maken we gebruik van een boomstructuur. Deze structuur zal in het vak rond algoritmen en datastructuren een belangrijke rol spelen en daar dan ook uitgebreid aan bod komen. We gebruiken deze hier enkel als voorbeeld.

Gegeven onderstaande record om een boomstructuur op te bouwen:

```java
public record TreeNode<T>(T value, TreeNode<T> left, TreeNode<T> right){}
```

De boom

<div style="max-width: 150px">

```goat
      A
     / \
    /   \
   B     E
  / \   /
 /   \ /
C    D F
```

</div>

kan je hiermee aanmaken als:

```java
var tree = new TreeNode<>("A",
              new TreeNode<>("B",
                new TreeNode<>("C", null, null),
                new TreeNode<>("D", null, null)),
              new TreeNode<>("E",
                new TreeNode<>("F", null, null),
                null));
```

1. Schrijf een recursieve methode `int treeSize(TreeNode<?> tree)` om het totale aantal knopen in de boom te berekenen. Voor de boom hierboven is de grootte 6.
2. Schrijf een recursieve methode `int treeHeight(TreeNode<?> tree)` om de hoogte van de boom te berekenen. De hoogte is het maximum aantal stappen van de wortel ("A" hierboven) tot een kind ("C", "D", of "F"). In het voorbeeld is de hoogte dus 2.
3. Schrijf een recursieve methode `<T> void visitDepthFirstPreOrder(TreeNode<T> tree, Consumer<T> consumer)` die de elementen van de boom overloopt in depth-first, pre-order volgorde. Dat houdt in: eerst de huidige knoop, dan de knopen van de linkertak (opnieuw in depth-first pre-order volgorde) en daarna die van de rechtertak. Voor de boom hierboven worden dus achtereenvolgens knopen "A", "B", "C", "D", "E", en "F" bezocht en doorgegeven aan de consumer. Een voorbeeld van het gebruik van de methode om de knopen uit te printen:
   ```java
   visitDepthFirstPreOrder(tree, System.out::print);
   System.out.println();
   ```
4. Schrijf een recursieve methode `<T> void visitDepthFirstInOrder(TreeNode<T> tree, Consumer<T> consumer)` die de elementen van de boom overloopt in depth-first, in-order volgorde. Dat houdt in: eerst de linkertak (in depth-first in-order volgorde), dan de knoop zelf, dan de rechtertak. Voor de boom hierboven worden dus achtereenvolgens knopen "C", "B", "D", "A", "F", en "E" bezocht en doorgegeven aan de consumer.
5. Schrijf een recursieve methode `<T> TreeNode<T> mirrorTree(TreeNode<T> tree)` om een willekeurige boom te spiegelen: het resultaat moet een nieuwe boom zijn, waar de linker-takken de rechtertakken geworden zijn en omgekeerd.
   De gespiegelde versie van de boom hierboven is dus:

<div style="max-width: 150px">

```goat
      A
     / \
    /   \
   E     B
    \   / \
     \ /   \
     F D   C
```

</div>

6. (**extra**) Schrijf een methode `String prettyPrint(TreeNode<?> tree)` die een ASCII-voorstelling van de boom maakt.
   De voorbeeldboom wordt bijvoorbeeld:
   ```
   A
   |
   +-- B
   |   |
   |   +-- C
   |   '-- D
   '-- E
       |
       +-- F
   ```

## Stack omkeren

Schrijf een recursieve methode `<T> void reverse(Deque<T> stack)` die de volgorde van de items in een stack (Deque) omdraait, zonder gebruik te maken van een extra datastructuur (dus zonder een andere array, lijst, set, ...).
Maak enkel gebruik van de `isEmpty()`-, `pollFirst()` en `addFirst()`-methodes van de Deque-interface.

```java
var stack = new LinkedList<>(List.of("A", "B", "C", "D", "E", "F"));
System.out.println(stack); // [A, B, C, D, E, F]
reverse(stack);
System.out.println(stack); // [F, E, D, C, B, A]
```

_Hint: overweeg een (recursieve) hulpoperatie om een element onderaan de stack in te voegen._

## Toren van Hanoi (uitbreiding)

Los de toren van Hanoi op voor \\( n\\) schijven op 4 stapels (dus 2 hulpstapels).
Je kan je oplossing manueel uittesten [via deze simulator](https://towersofhanoi.info/Play.aspx).
_(In de simulator moet je klikken, niet slepen)_

## Sorteren van een lijst

Schrijf een recursieve methode om een lijst van getallen te sorteren. De sortering moet _in-place_ gebeuren (je past de lijst zelf aan door elementen van volgorde te wisselen, en geeft dus geen nieuwe lijst terug).

## reduce

Schrijf een recursieve `reduce`-operatie die werkt op een lijst, naar analogie met de reduce-operatie op streams.

```java
List<Integer> lst = List.of(1, 2, 3, 4);
int sum = reduce(lst, 0, (sum, x) -> sum + y); // sum == 10
```
