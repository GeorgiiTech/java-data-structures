————import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Lesson 3: Stacks and Queues - LIFO, FIFO, and Real-World Applications
 * Course: Java Data Structures - A Deep Dive with Visualizations
 * https://georgii.tech/courses/java-data-structures-a-deep-dive-with-visualizations
 *
 * Compile: javac StacksAndQueues.java
 * Run:     java StacksAndQueues
 */
public class StacksAndQueues {

    // STACK - Last In, First Out (LIFO)
    static class MyStack<T> {
        private final Deque<T> deque = new ArrayDeque<>();
        public void push(T item) { deque.push(item); }
        public T pop() {
            if (isEmpty()) throw new RuntimeException("Stack is empty");
            return deque.pop();
        }
        public T peek() {
            if (isEmpty()) throw new RuntimeException("Stack is empty");
            return deque.peek();
        }
        public boolean isEmpty() { return deque.isEmpty(); }
        public int size() { return deque.size(); }
        public String toString() { return deque.toString(); }
    }

    // QUEUE - First In, First Out (FIFO)
    static class MyQueue<T> {
        private final Queue<T> queue = new LinkedList<>();
        public void enqueue(T item) { queue.offer(item); }
        public T dequeue() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            return queue.poll();
        }
        public T peek() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            return queue.peek();
        }
        public boolean isEmpty() { return queue.isEmpty(); }
        public int size() { return queue.size(); }
        public String toString() { return queue.toString(); }
    }

    // EXAMPLE 1: Balanced Parentheses using Stack
    static boolean isBalanced(String expression) {
        MyStack<Character> stack = new MyStack<>();
        for (char c : expression.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // EXAMPLE 2: Undo/Redo with Stacks
    static class TextEditor {
        private final Deque<String> undoStack = new ArrayDeque<>();
        private final Deque<String> redoStack = new ArrayDeque<>();
        private String content = "";
        public void type(String text) {
            undoStack.push(content);
            redoStack.clear();
            content += text;
        }
        public void undo() {
            if (!undoStack.isEmpty()) {
                redoStack.push(content);
                content = undoStack.pop();
            }
        }
        public void redo() {
            if (!redoStack.isEmpty()) {
                undoStack.push(content);
                content = redoStack.pop();
            }
        }
        public String getContent() { return content; }
    }

    // EXAMPLE 3: Print Queue simulation
    static void simulatePrintQueue() {
        MyQueue<String> printQueue = new MyQueue<>();
        printQueue.enqueue("Document1.pdf");
        printQueue.enqueue("Resume.docx");
        printQueue.enqueue("Invoice.pdf");
        System.out.println("Documents in queue: " + printQueue);
        while (!printQueue.isEmpty()) {
            System.out.println("Printing: " + printQueue.dequeue());
        }
        System.out.println("All documents printed.");
    }

    public static void main(String[] args) {
        System.out.println("=== STACK (LIFO) Demo ===");
        MyStack<Integer> stack = new MyStack<>();
        stack.push(10); stack.push(20); stack.push(30);
        System.out.println("Stack: " + stack);
        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        System.out.println("After pop: " + stack);

        System.out.println();
        System.out.println("=== QUEUE (FIFO) Demo ===");
        MyQueue<String> queue = new MyQueue<>();
        queue.enqueue("Alice"); queue.enqueue("Bob"); queue.enqueue("Charlie");
        System.out.println("Queue: " + queue);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("After dequeue: " + queue);

        System.out.println();
        System.out.println("=== Balanced Parentheses ===");
        String[] exprs = {"({[]})", "([)]", "{[}", "(())"};
        for (String e : exprs) {
            System.out.println(e + " -> " + (isBalanced(e) ? "Balanced" : "NOT balanced"));
        }

        System.out.println();
        System.out.println("=== Undo/Redo Demo ===");
        TextEditor editor = new TextEditor();
        editor.type("Hello"); editor.type(", World");
        System.out.println("After typing: " + editor.getContent());
        editor.undo();
        System.out.println("After undo: " + editor.getContent());
        editor.redo();
        System.out.println("After redo: " + editor.getContent());

        System.out.println();
        System.out.println("=== Print Queue ===");
        simulatePrintQueue();
    }
}
