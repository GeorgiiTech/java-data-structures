import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lesson 1 – Arrays: Memory Layout, Access Patterns, and Performance
   *
   * Key insight: a Java array is a contiguous block of memory.
   * This is why random access is O(1) but insertion/deletion is O(n).
   */
public class ArrayMemoryLayout {

    // Simple dynamic-array implementation (like ArrayList under the hood)
    static class DynamicArray {
              private int[] data;
              private int size;

          DynamicArray(int initialCapacity) {
                        data = new int[initialCapacity];
                        size = 0;
          }

          void add(int value) {
                        if (size == data.length) grow();  // amortised O(1)
                  data[size++] = value;
          }

          void insertAt(int index, int value) {
                        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
                        if (size == data.length) grow();
                        // Shift elements right — O(n)
                  System.arraycopy(data, index, data, index + 1, size - index);
                        data[index] = value;
                        size++;
          }

          void removeAt(int index) {
                        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
                        // Shift elements left — O(n)
                  System.arraycopy(data, index + 1, data, index, size - index - 1);
                        size--;
          }

          int get(int index) { return data[index]; }  // O(1) — just pointer arithmetic
          int size()         { return size; }

          private void grow() {
                        int newCapacity = data.length * 2;
                        data = Arrays.copyOf(data, newCapacity);
                        System.out.println("  [DynamicArray] grew to capacity " + newCapacity);
          }

          @Override
              public String toString() {
                            return Arrays.toString(Arrays.copyOf(data, size));
              }
    }

    public static void main(String[] args) {

          // ── 1. Fixed-size array performance demo ────────────────────────
          System.out.println("=== Fixed Array – O(1) access ===");
              int[] arr = {10, 20, 30, 40, 50};

          // Random access: O(1) regardless of position
          long start = System.nanoTime();
              int val = arr[arr.length - 1];
              long elapsed = System.nanoTime() - start;
              System.out.println("arr[" + (arr.length-1) + "] = " + val + " (accessed in ~" + elapsed + " ns)");

          // ── 2. Why insertion is O(n) ────────────────────────────────────
          System.out.println("\n=== Insertion at index 0 requires shifting O(n) ===");
              int[] original = {1, 2, 3, 4, 5};
              int[] expanded = new int[original.length + 1];
              expanded[0] = 0;  // new element
          System.arraycopy(original, 0, expanded, 1, original.length);
              System.out.println("Original: " + Arrays.toString(original));
              System.out.println("After insert 0 at idx 0: " + Arrays.toString(expanded));

          // ── 3. Dynamic array (amortised growth) ────────────────────────
          System.out.println("\n=== DynamicArray (ArrayList-like) ===");
              DynamicArray dynArr = new DynamicArray(2);
              for (int i = 1; i <= 8; i++) dynArr.add(i * 10);
              System.out.println("After 8 adds: " + dynArr);

          dynArr.insertAt(3, 999);
              System.out.println("After insertAt(3, 999): " + dynArr);

          dynArr.removeAt(0);
              System.out.println("After removeAt(0): " + dynArr);
              System.out.println("get(0) = " + dynArr.get(0) + "  [O(1)]");

          // ── 4. Compare with java.util.ArrayList ────────────────────────
          System.out.println("\n=== java.util.ArrayList ===");
              ArrayList<Integer> list = new ArrayList<>(4);
              for (int i = 1; i <= 6; i++) list.add(i * 100);
              System.out.println("ArrayList: " + list);
              list.add(2, 999);
              System.out.println("After add(2, 999): " + list);
              list.remove(Integer.valueOf(999));
              System.out.println("After remove(999): " + list);

          // ── 5. Cache performance: row-major vs column-major traversal ───
          System.out.println("\n=== 2D Array: row-major (fast) vs column-major (slow) ===");
              int SIZE = 1000;
              int[][] matrix = new int[SIZE][SIZE];
              for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) matrix[i][j] = i + j;

          // Row-major (cache-friendly)
          long t1 = System.nanoTime();
              long sum1 = 0;
              for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) sum1 += matrix[i][j];
              long t2 = System.nanoTime();

          // Column-major (cache-unfriendly)
          long t3 = System.nanoTime();
              long sum2 = 0;
              for (int j = 0; j < SIZE; j++) for (int i = 0; i < SIZE; i++) sum2 += matrix[i][j];
              long t4 = System.nanoTime();

          System.out.printf("Row-major    traversal: %6d ms (sum=%d)%n", (t2-t1)/1_000_000, sum1);
              System.out.printf("Column-major traversal: %6d ms (sum=%d)%n", (t4-t3)/1_000_000, sum2);
              System.out.println("Row-major is faster because of CPU cache locality.");
    }
}
