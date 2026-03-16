import java.util.*;

/**
 * Lesson 9: Sorting Algorithms Deep Dive — QuickSort, MergeSort, and When to Use Each
 * Course: Java Data Structures: A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations/lessons/sorting-algorithms-quicksort-mergesort-when-to-use/
 */
public class SortingAlgorithms {

    // ─────────────────────────────────────────────
    // Insertion Sort — O(n²) avg, O(n) best
    // Best for small arrays or nearly-sorted data
    // ─────────────────────────────────────────────
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ─────────────────────────────────────────────
    // Merge Sort — O(n log n) guaranteed, stable
    // Best for linked lists, external sort, stability needed
    // ─────────────────────────────────────────────
    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) temp[k++] = arr[i++];
            else                  temp[k++] = arr[j++];
        }
        while (i <= mid)  temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // ─────────────────────────────────────────────
    // Quick Sort — O(n log n) avg, in-place, fast in practice
    // ─────────────────────────────────────────────
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // Randomized pivot to avoid O(n²) worst case
        int randomIndex = low + (int)(Math.random() * (high - low + 1));
        swap(arr, randomIndex, high);

        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    // ─────────────────────────────────────────────
    // Heap Sort — O(n log n), in-place, not stable
    // ─────────────────────────────────────────────
    public static void heapSort(int[] arr) {
        int n = arr.length;
        // Build max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);       // move current root (max) to end
            heapify(arr, i, 0);   // re-heapify reduced heap
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left  < n && arr[left]  > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // ─────────────────────────────────────────────
    // Counting Sort — O(n + k), for integers in [0, k]
    // ─────────────────────────────────────────────
    public static int[] countingSort(int[] arr, int maxValue) {
        int[] count = new int[maxValue + 1];
        for (int x : arr) count[x]++;

        int[] sorted = new int[arr.length];
        int idx = 0;
        for (int val = 0; val <= maxValue; val++) {
            while (count[val]-- > 0) sorted[idx++] = val;
        }
        return sorted;
    }

    // ─────────────────────────────────────────────
    // Java's Arrays.sort() — uses different algorithms:
    // - primitives: Dual-Pivot QuickSort (fastest in practice)
    // - objects:    TimSort (stable, hybrid merge + insertion sort)
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=== Sorting Algorithms Deep Dive ===\n");

        // Test data
        int[] original = {64, 34, 25, 12, 22, 11, 90, 3, 78, 45};

        // Insertion Sort
        int[] arr = original.clone();
        insertionSort(arr);
        System.out.println("Insertion Sort: " + Arrays.toString(arr));

        // Merge Sort
        arr = original.clone();
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("Merge Sort:     " + Arrays.toString(arr));

        // Quick Sort
        arr = original.clone();
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Quick Sort:     " + Arrays.toString(arr));

        // Heap Sort
        arr = original.clone();
        heapSort(arr);
        System.out.println("Heap Sort:      " + Arrays.toString(arr));

        // Counting Sort
        int[] countArr = {4, 2, 2, 8, 3, 3, 1, 7, 5, 6};
        System.out.println("Counting Sort:  " + Arrays.toString(countingSort(countArr, 8)));

        // Java's Arrays.sort()
        int[] primitives = original.clone();
        Arrays.sort(primitives);  // uses Dual-Pivot QuickSort
        System.out.println("Arrays.sort():  " + Arrays.toString(primitives));

        // Stability demo: sort by first name, then by last name — stable preserves first order
        System.out.println("\n=== Stability Demo ===");
        String[][] people = {
            {"Alice", "Smith"}, {"Bob", "Smith"}, {"Alice", "Jones"}, {"Charlie", "Jones"}
        };
        // Sort by last name (stable = Alices stay in original order within same last name)
        Arrays.sort(people, Comparator.comparing(p -> p[1]));
        for (String[] p : people) System.out.println(p[0] + " " + p[1]);
        // Expected: Alice Jones, Charlie Jones, Alice Smith, Bob Smith

        // Performance comparison on large array
        System.out.println("\n=== Performance (1,000,000 elements) ===");
        int size = 1_000_000;
        int[] big = new int[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) big[i] = rand.nextInt(size);

        int[] copy1 = big.clone();
        long t = System.currentTimeMillis();
        mergeSort(copy1, 0, copy1.length - 1);
        System.out.printf("Merge Sort:    %d ms%n", System.currentTimeMillis() - t);

        int[] copy2 = big.clone();
        t = System.currentTimeMillis();
        quickSort(copy2, 0, copy2.length - 1);
        System.out.printf("Quick Sort:    %d ms%n", System.currentTimeMillis() - t);

        int[] copy3 = big.clone();
        t = System.currentTimeMillis();
        Arrays.sort(copy3);
        System.out.printf("Arrays.sort(): %d ms%n", System.currentTimeMillis() - t);
    }
}
