import java.util.*;

/**
 * Lesson 7: Graphs — Representations, BFS, DFS, and Shortest Paths
 * Course: Java Data Structures: A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations/lessons/graphs-representations-bfs-dfs-shortest-paths/
 */
public class Graphs {

    // ─────────────────────────────────────────────
    // Graph represented as adjacency list
    // ─────────────────────────────────────────────
    private final Map<Integer, List<Integer>> adjList = new HashMap<>();

    public void addEdge(int u, int v) {
        adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        adjList.computeIfAbsent(v, k -> new ArrayList<>()).add(u); // undirected
    }

    // ─────────────────────────────────────────────
    // Breadth-First Search (BFS) — O(V + E)
    // Finds shortest path in unweighted graphs
    // ─────────────────────────────────────────────
    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            order.add(node);

            for (int neighbor : adjList.getOrDefault(node, List.of())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return order;
    }

    // BFS shortest path
    public List<Integer> shortestPath(int start, int end) {
        Map<Integer, Integer> parent = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        parent.put(start, -1);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == end) break;

            for (int neighbor : adjList.getOrDefault(node, List.of())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, node);
                    queue.offer(neighbor);
                }
            }
        }

        if (!parent.containsKey(end)) return List.of(); // no path

        List<Integer> path = new ArrayList<>();
        for (int v = end; v != -1; v = parent.get(v)) {
            path.add(v);
        }
        Collections.reverse(path);
        return path;
    }

    // ─────────────────────────────────────────────
    // Depth-First Search (DFS) — O(V + E)
    // ─────────────────────────────────────────────
    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfsRecursive(start, visited, order);
        return order;
    }

    private void dfsRecursive(int node, Set<Integer> visited, List<Integer> order) {
        visited.add(node);
        order.add(node);
        for (int neighbor : adjList.getOrDefault(node, List.of())) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, order);
            }
        }
    }

    // DFS iterative (avoids stack overflow on deep graphs)
    public List<Integer> dfsIterative(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(start);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visited.contains(node)) continue;
            visited.add(node);
            order.add(node);
            for (int neighbor : adjList.getOrDefault(node, List.of())) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        return order;
    }

    // ─────────────────────────────────────────────
    // Cycle Detection (undirected graph)
    // ─────────────────────────────────────────────
    public boolean hasCycle(int numNodes) {
        Set<Integer> visited = new HashSet<>();
        for (int node : adjList.keySet()) {
            if (!visited.contains(node)) {
                if (dfsCycle(node, -1, visited)) return true;
            }
        }
        return false;
    }

    private boolean dfsCycle(int node, int parent, Set<Integer> visited) {
        visited.add(node);
        for (int neighbor : adjList.getOrDefault(node, List.of())) {
            if (!visited.contains(neighbor)) {
                if (dfsCycle(neighbor, node, visited)) return true;
            } else if (neighbor != parent) {
                return true; // back edge = cycle
            }
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // Dijkstra's Shortest Path (weighted graph)
    // ─────────────────────────────────────────────
    public static Map<Integer, Integer> dijkstra(Map<Integer, List<int[]>> graph, int source) {
        Map<Integer, Integer> dist = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        pq.offer(new int[]{0, source});
        dist.put(source, 0);

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], node = curr[1];

            if (d > dist.getOrDefault(node, Integer.MAX_VALUE)) continue; // stale

            for (int[] edge : graph.getOrDefault(node, List.of())) {
                int neighbor = edge[0], weight = edge[1];
                int newDist = d + weight;
                if (newDist < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, newDist);
                    pq.offer(new int[]{newDist, neighbor});
                }
            }
        }
        return dist;
    }

    // ─────────────────────────────────────────────
    // Topological Sort (Kahn's algorithm, DAGs only)
    // ─────────────────────────────────────────────
    public static List<Integer> topologicalSort(Map<Integer, List<Integer>> graph, int n) {
        int[] inDegree = new int[n];
        for (List<Integer> neighbors : graph.values()) {
            for (int nb : neighbors) inDegree[nb]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) queue.offer(i);
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            for (int nb : graph.getOrDefault(node, List.of())) {
                if (--inDegree[nb] == 0) queue.offer(nb);
            }
        }
        return result.size() == n ? result : List.of(); // empty = cycle
    }

    public static void main(String[] args) {
        System.out.println("=== Graph Representations: BFS, DFS, Dijkstra ===\n");

        Graphs g = new Graphs();
        // Build graph: 0-1-2-3, 0-2 (shortcut)
        //   0
        //  / \
        // 1   2
        //  \ /
        //   3
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        System.out.println("BFS from 0: " + g.bfs(0));
        // BFS: [0, 1, 2, 3]

        System.out.println("DFS from 0: " + g.dfs(0));
        // DFS: [0, 1, 3, 2]

        System.out.println("DFS iterative from 0: " + g.dfsIterative(0));

        System.out.println("Shortest path 0→3: " + g.shortestPath(0, 3));
        // [0, 1, 3] or [0, 2, 3]

        System.out.println("Has cycle: " + g.hasCycle(4));
        // true (0-1-3-2-0)

        // ── Dijkstra demo ──
        System.out.println("\n=== Dijkstra's Shortest Paths ===");
        Map<Integer, List<int[]>> weighted = new HashMap<>();
        // A=0 --1-- C=2, A=0 --4-- B=1, C=2 --2-- B=1, B=1 --3-- D=3, C=2 --5-- D=3
        weighted.put(0, List.of(new int[]{2, 1}, new int[]{1, 4}));
        weighted.put(2, List.of(new int[]{0, 1}, new int[]{1, 2}, new int[]{3, 5}));
        weighted.put(1, List.of(new int[]{0, 4}, new int[]{2, 2}, new int[]{3, 3}));
        weighted.put(3, List.of(new int[]{1, 3}, new int[]{2, 5}));

        Map<Integer, Integer> distances = dijkstra(weighted, 0);
        System.out.println("Distances from node 0: " + distances);
        // {0=0, 1=3, 2=1, 3=6}

        // ── Topological sort demo ──
        System.out.println("\n=== Topological Sort ===");
        // DAG: 0→1, 0→2, 1→3, 2→3
        Map<Integer, List<Integer>> dag = new HashMap<>();
        dag.put(0, List.of(1, 2));
        dag.put(1, List.of(3));
        dag.put(2, List.of(3));
        dag.put(3, List.of());
        System.out.println("Topological order: " + topologicalSort(dag, 4));
        // [0, 1, 2, 3] or [0, 2, 1, 3]

        // ── Adjacency matrix demo ──
        System.out.println("\n=== Adjacency Matrix ===");
        int n = 4;
        int[][] matrix = new int[n][n];
        int[][] edges = {{0,1},{0,2},{1,3},{2,3}};
        for (int[] e : edges) {
            matrix[e[0]][e[1]] = 1;
            matrix[e[1]][e[0]] = 1; // undirected
        }
        System.out.println("Matrix (1=edge exists):");
        for (int[] row : matrix) System.out.println(Arrays.toString(row));
    }
}
