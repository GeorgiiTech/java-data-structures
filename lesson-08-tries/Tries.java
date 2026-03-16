import java.util.*;

/**
 * Lesson 8: Tries (Prefix Trees) — Autocomplete, Spell Check, and IP Routing
 * Course: Java Data Structures: A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations/lessons/tries-prefix-trees-autocomplete-spell-check-ip-routing/
 */
public class Tries {

    // ─────────────────────────────────────────────
    // Trie Node
    // ─────────────────────────────────────────────
    static class TrieNode {
        TrieNode[] children = new TrieNode[26]; // a-z
        boolean isEnd = false;
    }

    // ─────────────────────────────────────────────
    // Trie Implementation
    // ─────────────────────────────────────────────
    static class Trie {
        private final TrieNode root = new TrieNode();

        // Insert a word — O(L)
        public void insert(String word) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                int i = c - 'a';
                if (curr.children[i] == null) {
                    curr.children[i] = new TrieNode();
                }
                curr = curr.children[i];
            }
            curr.isEnd = true;
        }

        // Search for exact word — O(L)
        public boolean search(String word) {
            TrieNode node = navigate(word);
            return node != null && node.isEnd;
        }

        // Check if any word starts with prefix — O(P)
        public boolean startsWith(String prefix) {
            return navigate(prefix) != null;
        }

        // Navigate to end of string (null if path doesn't exist)
        private TrieNode navigate(String s) {
            TrieNode curr = root;
            for (char c : s.toCharArray()) {
                int i = c - 'a';
                if (curr.children[i] == null) return null;
                curr = curr.children[i];
            }
            return curr;
        }

        // Autocomplete — list all words with given prefix — O(P + results)
        public List<String> autocomplete(String prefix) {
            List<String> results = new ArrayList<>();
            TrieNode node = navigate(prefix);
            if (node == null) return results;
            collectWords(node, new StringBuilder(prefix), results);
            return results;
        }

        private void collectWords(TrieNode node, StringBuilder current, List<String> results) {
            if (node.isEnd) {
                results.add(current.toString());
            }
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    current.append((char) ('a' + i));
                    collectWords(node.children[i], current, results);
                    current.deleteCharAt(current.length() - 1); // backtrack
                }
            }
        }

        // Delete a word — O(L)
        public void delete(String word) {
            delete(root, word, 0);
        }

        private boolean delete(TrieNode node, String word, int depth) {
            if (depth == word.length()) {
                if (!node.isEnd) return false;
                node.isEnd = false;
                return isEmpty(node);
            }
            int i = word.charAt(depth) - 'a';
            if (node.children[i] == null) return false;
            boolean shouldDelete = delete(node.children[i], word, depth + 1);
            if (shouldDelete) {
                node.children[i] = null;
                return !node.isEnd && isEmpty(node);
            }
            return false;
        }

        private boolean isEmpty(TrieNode node) {
            for (TrieNode child : node.children) {
                if (child != null) return false;
            }
            return true;
        }

        // Count all words in trie
        public int countWords() {
            return countWords(root);
        }

        private int countWords(TrieNode node) {
            int count = node.isEnd ? 1 : 0;
            for (TrieNode child : node.children) {
                if (child != null) count += countWords(child);
            }
            return count;
        }
    }

    // ─────────────────────────────────────────────
    // TextSearchEngine using Trie
    // ─────────────────────────────────────────────
    static class TextSearchEngine {
        private final Trie trie = new Trie();

        public void addWord(String word) {
            trie.insert(word.toLowerCase());
        }

        public boolean contains(String word) {
            return trie.search(word.toLowerCase());
        }

        public List<String> autocomplete(String prefix) {
            return trie.autocomplete(prefix.toLowerCase());
        }

        public void loadDictionary(List<String> words) {
            words.forEach(this::addWord);
            System.out.println("Loaded " + words.size() + " words into Trie");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Trie (Prefix Tree) Demo ===\n");

        Trie trie = new Trie();

        // Insert words
        String[] words = {"apple", "app", "application", "apply", "apt",
                          "banana", "band", "bandana", "java", "javascript", "jar"};
        for (String w : words) trie.insert(w);
        System.out.println("Inserted " + trie.countWords() + " words");

        // Search
        System.out.println("\n--- Search ---");
        System.out.println("search('java'):        " + trie.search("java"));        // true
        System.out.println("search('jav'):         " + trie.search("jav"));         // false
        System.out.println("search('javascript'):  " + trie.search("javascript"));  // true
        System.out.println("startsWith('app'):     " + trie.startsWith("app"));     // true
        System.out.println("startsWith('xyz'):     " + trie.startsWith("xyz"));     // false

        // Autocomplete
        System.out.println("\n--- Autocomplete ---");
        System.out.println("autocomplete('app'):  " + trie.autocomplete("app"));
        // [app, apple, application, apply]
        System.out.println("autocomplete('ban'):  " + trie.autocomplete("ban"));
        // [banana, band, bandana]
        System.out.println("autocomplete('ja'):   " + trie.autocomplete("ja"));
        // [jar, java, javascript]
        System.out.println("autocomplete('xyz'):  " + trie.autocomplete("xyz"));
        // []

        // Delete
        System.out.println("\n--- Delete ---");
        System.out.println("Before delete, search('app'): " + trie.search("app")); // true
        trie.delete("app");
        System.out.println("After delete('app'), search('app'): " + trie.search("app")); // false
        System.out.println("After delete('app'), startsWith('app'): " + trie.startsWith("app")); // true (apple, etc. still exist)
        System.out.println("After delete('app'), autocomplete('app'): " + trie.autocomplete("app"));
        // [apple, application, apply] — no 'app'

        // TextSearchEngine demo
        System.out.println("\n=== TextSearchEngine Demo ===");
        TextSearchEngine engine = new TextSearchEngine();
        engine.loadDictionary(Arrays.asList("HashMap", "HashSet", "HashTable",
                "LinkedList", "LinkedHashMap", "ArrayList", "ArrayDeque",
                "TreeMap", "TreeSet", "PriorityQueue"));
        System.out.println("contains('HashMap'):   " + engine.contains("hashmap"));  // true
        System.out.println("contains('Vector'):    " + engine.contains("vector"));   // false
        System.out.println("autocomplete('hash'):  " + engine.autocomplete("hash"));
        // [hashmap, hashset, hashtable]
        System.out.println("autocomplete('linked'):" + engine.autocomplete("linked"));
        // [linkedhashmap, linkedlist]
        System.out.println("autocomplete('tree'):  " + engine.autocomplete("tree"));
        // [treemap, treeset]
    }
}
