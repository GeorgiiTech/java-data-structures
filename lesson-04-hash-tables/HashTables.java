import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Lesson 4: Hash Tables - How Hashing Works and HashMap Internals
 * Course: Java Data Structures - A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations
 *
 * Compile: javac HashTables.java
 * Run:     java HashTables
 */
public class HashTables {

    // Simple custom hash map using separate chaining
    static class SimpleHashMap<K, V> {
        private static final int CAPACITY = 16;
        private final Object[][] buckets = new Object[CAPACITY][];
        private int size;

        private int hash(K key) {
            return Math.abs(key.hashCode()) % CAPACITY;
        }

        public void put(K key, V value) {
            int idx = hash(key);
            buckets[idx] = new Object[]{key, value};
            size++;
        }

        @SuppressWarnings("unchecked")
        public V get(K key) {
            int idx = hash(key);
            if (buckets[idx] != null && buckets[idx][0].equals(key)) {
                return (V) buckets[idx][1];
            }
            return null;
        }

        public int size() { return size; }
    }

    static boolean areAnagrams(String a, String b) {
        if (a.length() != b.length()) return false;
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : a.toCharArray()) freq.merge(c, 1, Integer::sum);
        for (char c : b.toCharArray()) {
            if (!freq.containsKey(c)) return false;
            freq.merge(c, -1, Integer::sum);
            if (freq.get(c) < 0) return false;
        }
        return true;
    }

    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        System.out.println("=== HashMap Demo ===");
        HashMap<String, Integer> wordCount = new HashMap<>();
        String[] words = {"java", "is", "great", "java", "is", "fun", "java"};
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        System.out.println("Word frequencies: " + wordCount);
        System.out.println("Count of java: " + wordCount.get("java"));

        System.out.println();
        System.out.println("=== LinkedHashMap (insertion order) ===");
        LinkedHashMap<String, String> capitals = new LinkedHashMap<>();
        capitals.put("France", "Paris");
        capitals.put("Japan", "Tokyo");
        capitals.put("Brazil", "Brasilia");
        System.out.println("Capitals: " + capitals);

        System.out.println();
        System.out.println("=== TreeMap (sorted keys) ===");
        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("Charlie", 85);
        scores.put("Alice", 92);
        scores.put("Bob", 78);
        scores.put("Diana", 95);
        System.out.println("Scores sorted: " + scores);
        System.out.println("Highest: " + scores.lastKey() + " = " + scores.lastEntry().getValue());

        System.out.println();
        System.out.println("=== Custom Hash Map ===");
        SimpleHashMap<String, Integer> myMap = new SimpleHashMap<>();
        myMap.put("apple", 1);
        myMap.put("banana", 2);
        myMap.put("cherry", 3);
        System.out.println("apple -> " + myMap.get("apple"));
        System.out.println("banana -> " + myMap.get("banana"));
        System.out.println("mango -> " + myMap.get("mango"));

        System.out.println();
        System.out.println("=== Anagram Check ===");
        System.out.println("listen/silent: " + areAnagrams("listen", "silent"));
        System.out.println("hello/world: " + areAnagrams("hello", "world"));

        System.out.println();
        System.out.println("=== Two-Sum Problem ===");
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("Indices: [" + result[0] + ", " + result[1] + "]");
    }
}
