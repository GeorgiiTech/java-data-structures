/**
 * Lesson 2 – Linked Lists: Singly, Doubly, and Circular
    *
    * Implements a generic singly-linked list from scratch,
    * then demonstrates a doubly-linked list.
    */
public class LinkedLists {

    // ── Singly Linked List ────────────────────────────────────────────
    static class SinglyLinkedList<T> {
              private static class Node<T> {
                            T data;
                            Node<T> next;
                            Node(T data) { this.data = data; }
              }

          private Node<T> head;
              private int size;

          void addFirst(T data) {               // O(1)
                  Node<T> newNode = new Node<>(data);
                        newNode.next = head;
                        head = newNode;
                        size++;
          }

          void addLast(T data) {                // O(n)
                  Node<T> newNode = new Node<>(data);
                        if (head == null) { head = newNode; }
                        else {
                                          Node<T> cur = head;
                                          while (cur.next != null) cur = cur.next;
                                          cur.next = newNode;
                        }
                        size++;
          }

          T removeFirst() {                     // O(1)
                  if (head == null) throw new RuntimeException("List is empty");
                        T data = head.data;
                        head = head.next;
                        size--;
                        return data;
          }

          boolean contains(T data) {            // O(n)
                  Node<T> cur = head;
                        while (cur != null) {
                                          if (cur.data.equals(data)) return true;
                                          cur = cur.next;
                        }
                        return false;
          }

          // Reverse the list in-place O(n)
          void reverse() {
                        Node<T> prev = null, cur = head;
                        while (cur != null) {
                                          Node<T> next = cur.next;
                                          cur.next = prev;
                                          prev = cur;
                                          cur = next;
                        }
                        head = prev;
          }

          int size() { return size; }

          @Override
              public String toString() {
                            StringBuilder sb = new StringBuilder("HEAD -> ");
                            Node<T> cur = head;
                            while (cur != null) { sb.append(cur.data).append(" -> "); cur = cur.next; }
                            return sb.append("NULL").toString();
              }
    }

    // ── Doubly Linked List ────────────────────────────────────────────
    static class DoublyLinkedList<T> {
              private static class Node<T> {
                            T data;
                            Node<T> prev, next;
                            Node(T data) { this.data = data; }
              }

          private Node<T> head, tail;
              private int size;

          void addFirst(T data) {
                        Node<T> n = new Node<>(data);
                        if (head == null) { head = tail = n; }
                        else { n.next = head; head.prev = n; head = n; }
                        size++;
          }

          void addLast(T data) {
                        Node<T> n = new Node<>(data);
                        if (tail == null) { head = tail = n; }
                        else { tail.next = n; n.prev = tail; tail = n; }
                        size++;
          }

          T removeFirst() {
                        if (head == null) throw new RuntimeException("Empty");
                        T d = head.data;
                        head = head.next;
                        if (head != null) head.prev = null; else tail = null;
                        size--;
                        return d;
          }

          T removeLast() {
                        if (tail == null) throw new RuntimeException("Empty");
                        T d = tail.data;
                        tail = tail.prev;
                        if (tail != null) tail.next = null; else head = null;
                        size--;
                        return d;
          }

          @Override
              public String toString() {
                            StringBuilder sb = new StringBuilder("NULL <-> ");
                            Node<T> cur = head;
                            while (cur != null) { sb.append(cur.data).append(" <-> "); cur = cur.next; }
                            return sb.append("NULL").toString();
              }
    }

    public static void main(String[] args) {

          System.out.println("=== Singly Linked List ===");
              SinglyLinkedList<Integer> sll = new SinglyLinkedList<>();
              sll.addLast(1); sll.addLast(2); sll.addLast(3); sll.addLast(4);
              System.out.println("After addLast 1-4: " + sll);
              sll.addFirst(0);
              System.out.println("After addFirst(0): " + sll);
              System.out.println("contains(3): " + sll.contains(3));
              System.out.println("removeFirst: " + sll.removeFirst() + " -> " + sll);
              sll.reverse();
              System.out.println("After reverse:     " + sll);

          System.out.println("\n=== Doubly Linked List ===");
              DoublyLinkedList<String> dll = new DoublyLinkedList<>();
              dll.addLast("A"); dll.addLast("B"); dll.addLast("C");
              System.out.println("After addLast A,B,C: " + dll);
              dll.addFirst("Z");
              System.out.println("After addFirst(Z):   " + dll);
              System.out.println("removeLast: " + dll.removeLast() + " -> " + dll);
              System.out.println("removeFirst: " + dll.removeFirst() + " -> " + dll);

          System.out.println("\n=== Java's LinkedList (doubly linked) ===");
              java.util.LinkedList<Integer> jll = new java.util.LinkedList<>();
              jll.addFirst(10); jll.addLast(20); jll.addLast(30);
              System.out.println("LinkedList: " + jll);
              System.out.println("peek (head): " + jll.peek());
              System.out.println("poll (removes head): " + jll.poll() + " -> " + jll);
    }
}
