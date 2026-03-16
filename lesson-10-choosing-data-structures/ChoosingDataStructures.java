import java.util.*;

/**
 * Lesson 10: Choosing the Right Data Structure — A Decision Framework
 * Course: Java Data Structures: A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations/lessons/choosing-right-data-structure-decision-framework/
 */
public class ChoosingDataStructures {

    // ─────────────────────────────────────────────
    // Case Study 1: LRU Cache
    // HashMap (O(1) lookup) + Doubly Linked List (O(1) ordering)
    // ─────────────────────────────────────────────
    static class LRUCache<K, V> {
        private final int capacity;
        private final Map<K, Node<K, V>> map = new HashMap<>();
        private final Node<K, V> head, tail; // sentinel nodes

        static class Node<K, V> {
            K key; V value;
            Node<K, V> prev, next;
            Node(K k, V v) { key = k; value = v; }
        }

        public LRUCache(int capacity) {
            this.capacity = capacity;
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        public V get(K key) {
            Node<K, V> node = map.get(key);
            if (node == null) return null;
            moveToFront(node);
            return node.value;
        }

        public void put(K key, V value) {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                node.value = value;
                moveToFront(node);
            } else {
                if (map.size() == capacity) evict();
                Node<K, V> node = new Node<>(key, value);
                insertAtFront(node);
                map.put(key, node);
            }
        }

        private void moveToFront(Node<K, V> node) { removeNode(node); insertAtFront(node); }
        private void insertAtFront(Node<K, V> node) {
            node.next = head.next; node.prev = head;
            head.next.prev = node; head.next = node;
        }
        private void removeNode(Node<K, V> node) {
            node.prev.next = node.next; node.next.prev = node.prev;
        }
        private void evict() {
            Node<K, V> lru = tail.prev;
            removeNode(lru);
            map.remove(lru.key);
            System.out.println("Evicted: " + lru.key);
        }
    }

    // ─────────────────────────────────────────────
    // Case Study 2: Word Frequency Counter
    // HashMap for counting + MinHeap for top-K
    // ─────────────────────────────────────────────
    static Map<String, Integer> countFrequency(String[] words) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) freq.merge(word, 1, Integer::sum);
        return freq;
    }

    static List<String> topK(Map<String, Integer> freq, int k) {
        PriorityQueue<Map.Entry<String, Integer>> heap =
            new PriorityQueue<>(k + 1, Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            heap.offer(entry);
            if (heap.size() > k) heap.poll();
        }
        List<String> result = new ArrayList<>();
        while (!heap.isEmpty()) result.add(0, heap.poll().getKey() + ":" + heap.peek() ); // just collect keys
        // simpler version:
        result.clear();
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());
        for (int i = 0; i < Math.min(k, sorted.size()); i++) {
            result.add(sorted.get(i).getKey() + "=" + sorted.get(i).getValue());
        }
        return result;
    }

    // ─────────────────────────────────────────────
    // Case Study 3: Floyd's Cycle Detection
    // No extra data structure needed — just pointers!
    // ─────────────────────────────────────────────
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true; // met → cycle
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // Case Study 4: Social Network BFS
    // Adjacency List + BFS Queue for shortest path
    // ─────────────────────────────────────────────
    static int degreesOfSeparation(Map<String, List<String>> graph, String source, String target) {
        Map<String, Integer> distance = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(source);
        distance.put(source, 0);
        while (!queue.isEmpty()) {
            String u = queue.poll();
            for (String friend : graph.getOrDefault(u, List.of())) {
                if (!distance.containsKey(friend)) {
                    distance.put(friend, distance.get(u) + 1);
                    if (friend.equals(target)) return distance.get(friend);
                    queue.offer(friend);
                }
            }
        }
        return -1; // not reachable
    }

    public static void main(String[] args) {
        System.out.println("=== Choosing the Right Data Structure ===\n");

        // LRU Cache demo
        System.out.println("--- LRU Cache (capacity=3) ---");
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "Alice");
        cache.put(2, "Bob");
        cache.put(3, "Carol");
        System.out.println("get(1): " + cache.get(1)); // Alice (now most recent)
        cache.put(4, "Dave");  // evicts 2 (Bob) — least recently used
        System.out.println("get(2): " + cache.get(2)); // null (evicted)
        System.out.println("get(3): " + cache.get(3)); // Carol

        // Word frequency demo
        System.out.println("\n--- Top-3 Word Frequencies ---");
        String[] words = {"java", "data", "java", "structure", "data", "java",
                          "algorithm", "data", "structure", "java"};
        Map<String, Integer> freq = countFrequency(words);
        System.out.println("Frequencies: " + freq);
        System.out.println("Top-3: " + topK(freq, 3));
        // [java=4, data=3, structure=2]

        // Cycle detection demo
        System.out.println("\n--- Floyd's Cycle Detection ---");
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        n1.next = n2; n2.next = n3; n3.next = n4; // no cycle
        System.out.println("Linear list has cycle: " + hasCycle(n1)); // false
        n4.next = n2; // create cycle: 4→2
        System.out.println("Cyclic list has cycle:  " + hasCycle(n1)); // true

        // Social network BFS demo
        System.out.println("\n--- Social Network Degrees of Separation ---");
        Map<String, List<String>> network = new HashMap<>();
        network.put("Alice", List.of("Bob", "Carol"));
        network.put("Bob", List.of("Alice", "Dave", "Eve"));
        network.put("Carol", List.of("Alice", "Frank"));
        network.put("Dave", List.of("Bob"));
        network.put("Eve", List.of("Bob", "Grace"));
        network.put("Frank", List.of("Carol"));
        network.put("Grace", List.of("Eve"));

        System.out.println("Alice → Dave:  " + degreesOfSeparation(network, "Alice", "Dave") + " degrees");
        // 2: Alice→Bob→Dave
        System.out.println("Alice → Grace: " + degreesOfSeparation(network, "Alice", "Grace") + " degrees");
        // 3: Alice→Bob→Eve→Grace
        System.out.println("Alice → Frank: " + degreesOfSeparation(network, "Alice", "Frank") + " degrees");
        // 2: Alice→Carol→Frank

        // Decision framework summary
        System.out.println("\n=== Quick Decision Reference ===");
        System.out.println("Fast key-value lookup?        → HashMap");
        System.out.println("Sorted key-value?             → TreeMap");
        System.out.println("Membership check?             → HashSet");
        System.out.println("Access by index?              → ArrayList");
        System.out.println("Insert/delete at both ends?   → ArrayDeque");
        System.out.println("Always get min/max?           → PriorityQueue");
        System.out.println("Prefix string search?         → Trie");
        System.out.println("Shortest unweighted path?     → BFS + Queue");
        System.out.println("Shortest weighted path?       → Dijkstra + PQ");
        System.out.println("LRU Cache?                    → LinkedHashMap");
        System.out.println("Detect cycle (linked list)?   → Floyd's (no DS!)");
    }
}
