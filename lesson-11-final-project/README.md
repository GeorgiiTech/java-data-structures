# Lesson 11: Final Project — Build a Data Structure Toolkit in Java

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Put it all together by building a real-world Data Structure Toolkit: a text search engine using Tries, a task scheduler using a Priority Queue, a graph-based route finder using BFS, and a caching system using a HashMap-backed LRU Cache.

## Project Components

| Component | Data Structure | Key Operations |
|-----------|---------------|----------------|
| TextSearchEngine | Trie | insert, search, autocomplete |
| TaskScheduler | Priority Queue (Min-Heap) | schedule, executeNext, peek |
| RouteNavigator | Adjacency List + BFS | addRoute, findShortestPath |
| LRUCache | HashMap + Doubly Linked List | get O(1), put O(1) |

## What you'll build

- **TextSearchEngine** — autocomplete with Trie prefix traversal
- **TaskScheduler** — priority-based task execution with a min-heap
- **RouteNavigator** — BFS shortest path on an unweighted graph (London Underground demo)
- **LRUCache** — O(1) get/put with LinkedHashMap or custom DLL + HashMap

## Run the code

```bash
javac DataStructureToolkit.java
java DataStructureToolkit
```
