# Lesson 9: Sorting Algorithms Deep Dive — QuickSort, MergeSort, and When to Use Each

> 📚 [Back to Course](https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations) | [Course Repo](../../README.md)

Master the most important sorting algorithms — from the naive O(n²) sorts to production-grade QuickSort and MergeSort. See each algorithm visualized step by step, understand their trade-offs, and know what Java's Arrays.sort() actually does internally.

## Algorithm Comparison

| Algorithm | Best | Average | Worst | Space | Stable? |
|-----------|------|---------|-------|-------|---------|
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes ✓ |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | No |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | No |
| Counting Sort | O(n + k) | O(n + k) | O(n + k) | O(k) | Yes |

## What you'll learn

- Insertion Sort — simple and great for small/nearly-sorted arrays
- Merge Sort — guaranteed O(n log n), stable, ideal for linked lists
- Quick Sort — fastest in practice for arrays, in-place
- Randomized pivot to avoid O(n²) worst case
- Counting Sort — O(n) for integers in known range
- What Java's Arrays.sort() actually uses (TimSort vs DualPivot QuickSort)

## Run the code

```bash
javac SortingAlgorithms.java
java SortingAlgorithms
```
