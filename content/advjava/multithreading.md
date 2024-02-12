---
title: "4.4 Multithreading en concurrency"
---

## Wat en waarom?

Concurrency: shared resource, divide over multiple (unrelated) tasks using time slicing

Parallelism: divide one task in multiple subtasks, execute them simultaneously (on their own processing device: CPU, core)

Beiden worden vaak gecombineerd (taken opsplitsen, gebruik makend van gedeelde resources).

Multithreading: meerdere 'threads' die berekeningen kunnen uitvoeren. Kan gebruikt worden voor concurrency en parallelism.

## Threads in Java

Origineel: Java thread = OS thread
Tegenwoordig (sinds Java 21): Java thread beheerd door JVM ('virtual threads'); =/= OS thread

## Race conditions

Voorbeeld

### Java memory model

Happens-before:
https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/package-summary.html#MemoryVisibility

### Immutability

Immutable = immuun voor race conditions
Te verkiezen boven synchronisatie

## Concurrent data structures

Niet elke datastructuur kan gebruikt worden door meerdere threads tegelijk.

## High-level concurrency abstractions

ThreadPool, Executors, ...

CompletableFuture

## Parallel
