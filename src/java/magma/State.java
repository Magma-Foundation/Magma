package magma;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        this(queue, new LinkedList<>(), new StringBuilder(), 0);
    }

    Optional<State> popAndAppend() {
        return pop().map(this::append);
    }

    State exit() {
        this.depth = depth - 1;
        return this;
    }

    State enter() {
        this.depth = depth + 1;
        return this;
    }

    Optional<Character> pop() {
        if (queue.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(queue.pop());
        }
    }

    State append(char maybeEscape) {
        buffer.append(maybeEscape);
        return this;
    }

    boolean isLevel() {
        return depth == 0;
    }

    boolean isShallow() {
        return depth == 1;
    }

    State advance() {
        List<String> segments = segments();
        segments.add(buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    public List<String> segments() {
        return segments;
    }
}
