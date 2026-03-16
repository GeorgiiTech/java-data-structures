# Lesson 1: Arrays — Memory Layout, Access Patterns, and Performance

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Understand arrays at the memory level — how elements are stored in contiguous blocks, why random access is O(1), and how Java's ArrayList is built on top of a raw array.

## Big-O Summary
| Operation | Array | ArrayList |
|-----------|-------|-----------|
| Access    | O(1)  | O(1)      |
| Search    | O(n)  | O(n)      |
| Insert    | O(n)  | O(n) amortized |
| Delete    | O(n)  | O(n)      |

## Run the code
```bash
javac ArrayMemoryLayout.java
java ArrayMemoryLayout
```
