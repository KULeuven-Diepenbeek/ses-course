---
title: "6.2 Backtracking"
autonumbering: true
toc: true
draft: false
---

## Wat is backtracking?

## Skelet-programma's

### Een willekeurige oplossing zoeken

```java
public class Problem {
  private class PartialSolution { ... }
  public class Solution { ... }

  public Solution solve() {
    PartialSolution initial = ...
    return findSolution(initial);
  }

  private Solution findSolution(PartialSolution candidate) {
    if (candidate.isComplete()) return candidate.toSolution();
    if (candidate.shouldAbort()) return null;
    for (PartialSolution newSolution : candidate.extend()) {
      var newCandidate = findSolution(newSolution);
      if (newCandidate) return newCandidate;
    }
    return null;
  }
}
```

#### Voorbeeld: 8-queens

### Alle oplossingen zoeken

### Een optimale oplossing zoeken

## Efficiëntie van backtracking

## Oefeningen

### n-queens

### knight tour

### Task scheduler

Precedence constraints between tasks

Find valid schedule with minimal total time

Extension: multiple CPU's; task pinned to one CPU

### Uurrooster planner

### SEND+MORE=MONEY

### Pattern match

TODO: backtracking

Schrijf een methode die nagaat of een String voldoet aan een geven patroon.
Het patroon bestaat uit letters, waar elke letter staat voor een deel van de string.
Bijvoorbeeld:

- "hoihoihoi" voldoet aan het patroon "XXX" (waarbij X=hoi)
- "choochoo" en "redder" voldoen aan het patroon "XX" maar niet aan "XXX"
- "appelmoes" voldoet aan het patroon "X", maar ook aan "XY", "XYZ", ...
- "meetsysteem" voldoet aan het patroon "ABCXYXCBA"
