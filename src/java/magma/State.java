package magma;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class State {
    private final Deque<Character> queue;
    private final List<String> segments;
    private StringBuilder buffer;
    private int depth;

    public State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
        this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    }

    public State(Deque<Character> queue) {
        this(queue, new ArrayList<>(), new StringBuilder(), 0);
    }

    void popAndAppend() {
        buffer.append(pop());
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }

    void append(char c) {
        buffer.append(c);
    }

    Character pop() {
        return queue.pop();
    }

    void advance() {
        segments().add(buffer.toString());
        this.buffer = new StringBuilder();
    }

    void enter() {
        this.depth = depth + 1;
    }

    void exit() {
        this.depth = depth - 1;
    }

    boolean isLevel() {
        return depth == 0;
    }

    boolean isShallow() {
        return depth == 1;
    }

    public List<String> segments() {
        return segments;
    }
}
