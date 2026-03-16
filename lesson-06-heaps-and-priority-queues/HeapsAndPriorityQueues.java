import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Lesson 6: Heaps and Priority Queues - Min-Heap, Max-Heap, and Heap Sort
 * Course: Java Data Structures - A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations
 *
 * Compile: javac HeapsAndPriorityQueues.java
 * Run:     java HeapsAndPriorityQueues
 */
public class HeapsAndPriorityQueues {

    // Min-Heap implementation using array
    static class MinHeap {
        private final int[] heap;
        private int size;

        MinHeap(int capacity) {
            heap = new int[capacity];
        }

        private int parent(int i) { return (i - 1) / 2; }
        private int leftChild(int i) { return 2 * i + 1; }
        private int rightChild(int i) { return 2 * i + 2; }

        public void insert(int value) {
            if (size >= heap.length) throw new RuntimeException("Heap full");
            heap[size] = value;
            siftUp(size++);
        }

        private void siftUp(int i) {
            while (i > 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        public int extractMin() {
            if (size == 0) throw new RuntimeException("Heap empty");
            int min = heap[0];
            heap[0] = heap[--size];
            siftDown(0);
            return min;
        }

        private void siftDown(int i) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);
            if (left < size && heap[left] < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;
            if (smallest != i) {
                swap(i, smallest);
                siftDown(smallest);
            }
        }

        public int peek() {
            if (size == 0) throw new RuntimeException("Heap empty");
            return heap[0];
        }

        private void swap(int a, int b) {
            int tmp = heap[a]; heap[a] = heap[b]; heap[b] = tmp;
        }

        public int size() { return size; }

        public String toString() {
            return Arrays.toString(Arrays.copyOf(heap, size));
        }
    }

    // Heap Sort using max-heap approach (in-place)
    static void heapSort(int[] arr) {
        int n = arr.length;
        // Build max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }
        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0]; arr[0] = arr[i]; arr[i] = tmp;
            heapifyDown(arr, i, 0);
        }
    }

    private static void heapifyDown(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;
        if (largest != i) {
            int tmp = arr[i]; arr[i] = arr[largest]; arr[largest] = tmp;
            heapifyDown(arr, n, largest);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Min-Heap Demo ===");
        MinHeap minHeap = new MinHeap(10);
        int[] insertValues = {15, 10, 5, 20, 8, 1, 30};
        for (int v : insertValues) {
            minHeap.insert(v);
            System.out.println("Insert " + v + " -> Heap: " + minHeap);
        }
        System.out.println("Peek (min): " + minHeap.peek());
        System.out.print("Extract all (sorted): ");
        while (minHeap.size() > 0) {
            System.out.print(minHeap.extractMin() + " ");
        }
        System.out.println();

        System.out.println();
        System.out.println("=== Java PriorityQueue (Min-Heap) ===");
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(15); pq.offer(10); pq.offer(5); pq.offer(20); pq.offer(8);
        System.out.println("PriorityQueue: " + pq);
        System.out.println("Poll (min first): " + pq.poll() + ", " + pq.poll() + ", " + pq.poll());

        System.out.println();
        System.out.println("=== Java PriorityQueue (Max-Heap) ===");
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());
        maxPQ.offer(15); maxPQ.offer(10); maxPQ.offer(5); maxPQ.offer(20); maxPQ.offer(8);
        System.out.println("Max-PriorityQueue: " + maxPQ);
        System.out.println("Poll (max first): " + maxPQ.poll() + ", " + maxPQ.poll() + ", " + maxPQ.poll());

        System.out.println();
        System.out.println("=== Heap Sort ===");
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Before: " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println("After:  " + Arrays.toString(arr));

        System.out.println();
        System.out.println("=== Real-World: Task Scheduler ===");
        PriorityQueue<int[]> tasks = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        tasks.offer(new int[]{3, 0}); // priority 3
        tasks.offer(new int[]{1, 1}); // priority 1 (highest)
        tasks.offer(new int[]{2, 2}); // priority 2
        tasks.offer(new int[]{1, 3}); // priority 1
        String[] names = {"Download", "Display UI", "Send Email", "Update DB"};
        System.out.println("Processing tasks by priority:");
        while (!tasks.isEmpty()) {
            int[] task = tasks.poll();
            System.out.println("  Task: " + names[task[1]] + " (priority=" + task[0] + ")");
        }
    }
}
