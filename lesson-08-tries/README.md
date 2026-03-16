# Lesson 8: Tries (Prefix Trees) — Autocomplete, Spell Check, and IP Routing

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Explore the Trie — a tree where each path spells a word. Learn why it beats HashMap for prefix searches, implement insert/search/startsWith from scratch, and see how autocomplete and spell-check systems are built.

## Big-O Summary

| Operation | Trie | HashMap |
|-----------|------|---------|
| Insert | O(L) | O(L) |
| Search (exact) | O(L) | O(L) |
| Search by prefix | O(P) ✓ | O(n × L) ✗ |
| Autocomplete | O(P + results) ✓ | O(n × L) ✗ |
| Space | O(n × L) shared | O(n × L) |

L = word length, P = prefix length, n = number of words

## What you'll learn

- Trie node structure with children array and isEnd flag
- Insert, search, and startsWith operations
- Autocomplete with prefix traversal
- Word deletion with node pruning
- Real-world applications: search autocomplete, spell check, IP routing

## Run the code

```bash
javac Tries.java
java Tries
```
