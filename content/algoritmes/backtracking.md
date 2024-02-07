---
title: "5.2 Backtracking"
autonumbering: true
toc: true
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

## Oefening: n-queens

## Oefening: knight tour

## Oefening: Task scheduler

Precedence constraints between tasks

Find valid schedule with minimal total time

Extension: multiple CPU's; task pinned to one CPU

## Oefening: Uurrooster planner
