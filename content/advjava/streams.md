---
title: "4.4 Streams"
---

## Wat en waarom?

https://www.youtube.com/watch?v=8fMFa6OqlY8&ab_channel=Devoxx

Source
Intermediate ops (Transforms)
Terminal (collect, sum)

## Lambda functies

@FunctionalInterface annotatie

Method references

## Uitvoering

Time of evaluation / lazy evaluation
https://www.youtube.com/watch?v=q2T9NlROLqw&ab_channel=Devoxx

```java
int[] factor = new int[] { 2 };

var numbers = List.of(1, 2, 3);

var stream = numbers.stream().map(n -> n * factor[0]);

factor[0] = 0;

stream.forEach(System.out::print);
```

prints 000

## Streams aanmaken

## Tussentijdse (intermediate) operaties

### map

### filter

### reduce

### flatMap

### limit

## Terminale (terminal) operaties

### count

### toList

### toSet

### findFirst en findAny

### forEach

### min en max

### Collectors.groupingBy

### Collectors.joining

### partitioningBy

### ...

## Parallel streams

### Spliterator

https://www.youtube.com/watch?v=Kv-EZtNzgkg&ab_channel=DouglasSchmidt
