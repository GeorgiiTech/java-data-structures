# Lesson 7: Graphs — Representations, BFS, DFS, and Shortest Paths

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Learn how to represent graphs with adjacency lists and matrices, traverse them with BFS and DFS, detect cycles, and find shortest paths with Dijkstra's algorithm — all visualized step by step.

## Big-O Summary

| Operation | Adjacency List | Adjacency Matrix |
|-----------|----------------|-----------------|
| Space | O(V + E) | O(V²) |
| Add Edge | O(1) | O(1) |
| Check Edge | O(degree) | O(1) |
| BFS / DFS | O(V + E) | O(V²) |
| Dijkstra | O((V+E) log V) | O(V² log V) |

## What you'll learn

- Adjacency list vs adjacency matrix representations
- Breadth-First Search (BFS) — shortest path in unweighted graphs
- Depth-First Search (DFS) — recursive and iterative
- Cycle detection in undirected graphs
- Dijkstra's shortest path algorithm
- Topological sort for DAGs

## Run the code

```bash
javac Graphs.java
java Graphs
```
