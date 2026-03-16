# Lesson 10: Choosing the Right Data Structure — A Decision Framework

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Synthesize everything you've learned: a practical framework for picking the right data structure for any problem. Covers Java's standard library map, with trade-off tables and real design case studies.

## Master Complexity Reference

| Data Structure | Access | Search | Insert | Delete | Space |
|----------------|--------|--------|--------|--------|-------|
| Array | O(1) | O(n) | O(n) | O(n) | O(n) |
| ArrayList | O(1) | O(n) | O(n)* | O(n) | O(n) |
| LinkedList | O(n) | O(n) | O(1)† | O(1)† | O(n) |
| HashMap | O(1) | O(1) | O(1) | O(1) | O(n) |
| TreeMap | O(log n) | O(log n) | O(log n) | O(log n) | O(n) |
| Heap | O(1)‡ | O(n) | O(log n) | O(log n) | O(n) |
| BST (balanced) | O(log n) | O(log n) | O(log n) | O(log n) | O(n) |
| Trie | O(L) | O(L) | O(L) | O(L) | O(n×L) |

\* amortized O(1) at end † O(1) with node reference ‡ top/front only L = string length

## What you'll learn

- Decision framework: choosing by dominant operation
- Java standard library: ArrayList, HashMap, TreeMap, PriorityQueue, ArrayDeque
- Case studies: LRU Cache, Word Frequency Counter, Cycle Detection, Social Network BFS
- The Golden Rules for data structure selection

## Run the code

```bash
javac ChoosingDataStructures.java
java ChoosingDataStructures
```
