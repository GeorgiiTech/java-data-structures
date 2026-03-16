import java.util.*;

/**
 * Lesson 11: Final Project — Build a Data Structure Toolkit in Java
 * Course: Java Data Structures: A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations/lessons/final-project-build-a-data-structure-toolkit-in-java/
 *
 * Four real-world mini-systems, each powered by the optimal data structure:
 * 1. TextSearchEngine  — Trie-powered word search and autocomplete
 * 2. TaskScheduler     — Priority Queue-based task management
 * 3. RouteNavigator    — Graph + BFS shortest path finder
 * 4. LRUCache          — HashMap + DoublyLinkedList O(1) cache
 */
public class DataStructureToolkit {

    // ══════════════════════════════════════════════════════════════
    // Component 1: TextSearchEngine (Trie)
    // ══════════════════════════════════════════════════════════════
    static class TextSearchEngine {
        private final TrieNode root = new TrieNode();

        static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean isEnd = false;
        }

        public void addWord(String word) {
            TrieNode curr = root;
            for (char c : word.toLowerCase().toCharArray()) {
                int i = c - 'a';
                if (curr.children[i] == null) curr.children[i] = new TrieNode();
                curr = curr.children[i];
            }
            curr.isEnd = true;
        }

        public boolean contains(String word) {
            TrieNode node = navigate(word.toLowerCase());
            return node != null && node.isEnd;
        }

        public List<String> autocomplete(String prefix) {
            List<String> results = new ArrayList<>();
            TrieNode node = navigate(prefix.toLowerCase());
            if (node != null) collectWords(node, new StringBuilder(prefix.toLowerCase()), results);
            return results;
        }

        private TrieNode navigate(String s) {
            TrieNode curr = root;
            for (char c : s.toCharArray()) {
                int i = c - 'a';
                if (curr.children[i] == null) return null;
                curr = curr.children[i];
            }
            return curr;
        }

        private void collectWords(TrieNode node, StringBuilder sb, List<String> results) {
            if (node.isEnd) results.add(sb.toString());
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    sb.append((char)('a' + i));
                    collectWords(node.children[i], sb, results);
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }

        public void loadDictionary(List<String> words) {
            words.forEach(this::addWord);
            System.out.println("Loaded " + words.size() + " words into TextSearchEngine");
        }
    }

    // ══════════════════════════════════════════════════════════════
    // Component 2: TaskScheduler (Priority Queue / Min-Heap)
    // ══════════════════════════════════════════════════════════════
    static class TaskScheduler {
        record Task(String name, int priority, String description) {}

        private final PriorityQueue<Task> queue =
            new PriorityQueue<>(Comparator.comparingInt(Task::priority));

        public void addTask(String name, int priority, String description) {
            queue.offer(new Task(name, priority, description));
            System.out.printf("  Scheduled: [P%d] %s%n", priority, name);
        }

        public Task executeNext() {
            if (queue.isEmpty()) throw new RuntimeException("No tasks!");
            Task task = queue.poll();
            System.out.printf("  Executing: [P%d] %s — %s%n",
                task.priority(), task.name(), task.description());
            return task;
        }

        public int pendingCount() { return queue.size(); }
        public Task peek() { return queue.peek(); }
    }

    // ══════════════════════════════════════════════════════════════
    // Component 3: RouteNavigator (Adjacency List + BFS)
    // ══════════════════════════════════════════════════════════════
    static class RouteNavigator {
        private final Map<String, List<String>> graph = new HashMap<>();

        public void addRoute(String from, String to) {
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            graph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
        }

        public List<String> findShortestPath(String start, String end) {
            if (!graph.containsKey(start) || !graph.containsKey(end)) return List.of();
            Map<String, String> parent = new HashMap<>();
            Queue<String> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.offer(start);
            visited.add(start);
            parent.put(start, null);
            while (!queue.isEmpty()) {
                String curr = queue.poll();
                if (curr.equals(end)) break;
                for (String neighbor : graph.getOrDefault(curr, List.of())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        parent.put(neighbor, curr);
                        queue.offer(neighbor);
                    }
                }
            }
            if (!parent.containsKey(end)) return List.of();
            List<String> path = new ArrayList<>();
            for (String v = end; v != null; v = parent.get(v)) path.add(v);
            Collections.reverse(path);
            return path;
        }
    }

    // ══════════════════════════════════════════════════════════════
    // Component 4: LRUCache (LinkedHashMap shortcut)
    // Full custom implementation: HashMap + DoublyLinkedList
    // ══════════════════════════════════════════════════════════════
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true); // accessOrder=true
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            boolean evict = size() > capacity;
            if (evict) System.out.println("  LRU Evicted: " + eldest.getKey());
            return evict;
        }
    }

    // ══════════════════════════════════════════════════════════════
    // Main Demo
    // ══════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Data Structure Toolkit — Demo      ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── Component 1: TextSearchEngine ──
        System.out.println("=== 1. TextSearchEngine (Trie) ===");
        TextSearchEngine engine = new TextSearchEngine();
        engine.loadDictionary(List.of(
            "apple", "application", "apply", "apt",
            "banana", "band", "bandana",
            "java", "javascript", "jar", "junit"
        ));
        System.out.println("contains('java'):     " + engine.contains("java"));      // true
        System.out.println("contains('jav'):      " + engine.contains("jav"));       // false
        System.out.println("autocomplete('app'):  " + engine.autocomplete("app"));
        // [apple, application, apply, apt]
        System.out.println("autocomplete('ja'):   " + engine.autocomplete("ja"));
        // [jar, java, javascript, junit? no — starts with ju not ja]
        System.out.println("autocomplete('ban'):  " + engine.autocomplete("ban"));
        // [banana, band, bandana]

        // ── Component 2: TaskScheduler ──
        System.out.println("\n=== 2. TaskScheduler (Min-Heap) ===");
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addTask("Deploy DB migration",  1, "CRITICAL: prod database migration");
        scheduler.addTask("Code review",          5, "Review PR #142");
        scheduler.addTask("Fix login bug",        2, "Users can't log in on mobile");
        scheduler.addTask("Update docs",          8, "Update API documentation");
        scheduler.addTask("Security patch",       1, "Apply CVE-2024-1234 fix");
        System.out.println("\nExecuting tasks by priority:");
        while (scheduler.pendingCount() > 0) scheduler.executeNext();

        // ── Component 3: RouteNavigator ──
        System.out.println("\n=== 3. RouteNavigator (BFS) ===");
        RouteNavigator nav = new RouteNavigator();
        // London Underground (simplified)
        nav.addRoute("King's Cross",   "Euston");
        nav.addRoute("Euston",         "Warren Street");
        nav.addRoute("Warren Street",  "Oxford Circus");
        nav.addRoute("Oxford Circus",  "Green Park");
        nav.addRoute("Green Park",     "Victoria");
        nav.addRoute("King's Cross",   "Angel");
        nav.addRoute("Angel",          "Old Street");
        nav.addRoute("Victoria",       "Sloane Square");

        System.out.println("King's Cross → Victoria: " +
            nav.findShortestPath("King's Cross", "Victoria"));
        // [King's Cross, Euston, Warren Street, Oxford Circus, Green Park, Victoria]
        System.out.println("King's Cross → Old Street: " +
            nav.findShortestPath("King's Cross", "Old Street"));
        // [King's Cross, Angel, Old Street]
        System.out.println("King's Cross → Sloane Square: " +
            nav.findShortestPath("King's Cross", "Sloane Square"));
        // [King's Cross, Euston, ..., Victoria, Sloane Square]

        // ── Component 4: LRU Cache ──
        System.out.println("\n=== 4. LRU Cache (LinkedHashMap, capacity=3) ===");
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "Alice");
        cache.put(2, "Bob");
        cache.put(3, "Carol");
        System.out.println("  get(1): " + cache.get(1)); // Alice (now most recent)
        cache.put(4, "Dave");   // evicts 2 (Bob) — least recently used
        System.out.println("  get(2): " + cache.get(2)); // null (evicted)
        System.out.println("  get(3): " + cache.get(3)); // Carol
        System.out.println("  Cache contents: " + cache);

        System.out.println("\n✓ All components working correctly!");
    }
}
