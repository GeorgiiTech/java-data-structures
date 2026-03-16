# Lesson 6: Heaps and Priority Queues — Min-Heap, Max-Heap, and Heap Sort

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Understand how heaps store a tree in an array, why insert and extract-min are O(log n), how Java's PriorityQueue works internally, and how to implement Heap Sort from scratch.

## Big-O Summary

| Operation | Min-Heap / Max-Heap |
|-----------|---------------------|
| Peek (min/max) | O(1) |
| Insert | O(log n) |
| Extract min/max | O(log n) |
| Build heap | O(n) |
| Heap Sort | O(n log n) |

## What you'll learn

- Heap property and complete binary tree structure
- Insert and extract operations with sift-up / sift-down
- Min-Heap and Max-Heap implementation
- Java PriorityQueue usage
- Heap Sort algorithm in O(n log n)

## Run the code

```bash
javac HeapsAndPriorityQueues.java
java HeapsAndPriorityQueues
```
