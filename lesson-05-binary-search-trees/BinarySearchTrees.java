/**
 * Lesson 5: Binary Search Trees - Insert, Search, and Delete Visualized
 * Course: Java Data Structures - A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations
 *
 * Compile: javac BinarySearchTrees.java
 * Run:     java BinarySearchTrees
 */
public class BinarySearchTrees {

    static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    static class BST {
        Node root;

        // Insert a value into the BST
        public void insert(int value) {
            root = insertRec(root, value);
        }

        private Node insertRec(Node node, int value) {
            if (node == null) return new Node(value);
            if (value < node.value) node.left = insertRec(node.left, value);
            else if (value > node.value) node.right = insertRec(node.right, value);
            return node;
        }

        // Search for a value
        public boolean search(int value) {
            return searchRec(root, value);
        }

        private boolean searchRec(Node node, int value) {
            if (node == null) return false;
            if (value == node.value) return true;
            return value < node.value ? searchRec(node.left, value) : searchRec(node.right, value);
        }

        // Delete a value
        public void delete(int value) {
            root = deleteRec(root, value);
        }

        private Node deleteRec(Node node, int value) {
            if (node == null) return null;
            if (value < node.value) {
                node.left = deleteRec(node.left, value);
            } else if (value > node.value) {
                node.right = deleteRec(node.right, value);
            } else {
                // Node found - handle 3 cases
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                // Two children: replace with in-order successor (smallest in right subtree)
                Node successor = findMin(node.right);
                node.value = successor.value;
                node.right = deleteRec(node.right, successor.value);
            }
            return node;
        }

        private Node findMin(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        // Traversals
        public void inOrder() {
            System.out.print("In-order (sorted): ");
            inOrderRec(root);
            System.out.println();
        }

        private void inOrderRec(Node node) {
            if (node != null) {
                inOrderRec(node.left);
                System.out.print(node.value + " ");
                inOrderRec(node.right);
            }
        }

        public void preOrder() {
            System.out.print("Pre-order: ");
            preOrderRec(root);
            System.out.println();
        }

        private void preOrderRec(Node node) {
            if (node != null) {
                System.out.print(node.value + " ");
                preOrderRec(node.left);
                preOrderRec(node.right);
            }
        }

        public void postOrder() {
            System.out.print("Post-order: ");
            postOrderRec(root);
            System.out.println();
        }

        private void postOrderRec(Node node) {
            if (node != null) {
                postOrderRec(node.left);
                postOrderRec(node.right);
                System.out.print(node.value + " ");
            }
        }

        public int height() {
            return heightRec(root);
        }

        private int heightRec(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(heightRec(node.left), heightRec(node.right));
        }
    }

    public static void main(String[] args) {
        BST tree = new BST();

        System.out.println("=== Inserting values: 50, 30, 70, 20, 40, 60, 80 ===");
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) {
            tree.insert(v);
            System.out.print("Inserted " + v + " -> root=" + tree.root.value);
            System.out.println();
        }

        System.out.println();
        System.out.println("=== Tree Traversals ===");
        tree.inOrder();
        tree.preOrder();
        tree.postOrder();
        System.out.println("Height: " + tree.height());

        System.out.println();
        System.out.println("=== Search ===");
        System.out.println("Search 40: " + tree.search(40));
        System.out.println("Search 55: " + tree.search(55));
        System.out.println("Search 70: " + tree.search(70));

        System.out.println();
        System.out.println("=== Delete ===");
        System.out.println("Deleting leaf node 20:");
        tree.delete(20);
        tree.inOrder();

        System.out.println("Deleting node with one child 30:");
        tree.delete(30);
        tree.inOrder();

        System.out.println("Deleting node with two children 70:");
        tree.delete(70);
        tree.inOrder();

        System.out.println("Height after deletions: " + tree.height());
    }
}
