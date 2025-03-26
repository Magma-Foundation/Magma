package magma;

import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    boolean isLevel() {
        return depth == 0;
    }

    boolean isShallow() {
        return depth == 1;
    }

    State advance() {
        segments.add(buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    State exit() {
        this.depth = depth - 1;
        return this;
    }

    State enter() {
        this.depth = depth + 1;
        return this;
    }

    Optional<State> popAndAppend() {
        return pop().map(this::append);
    }

    State append(char popped) {
        buffer.append(popped);
        return this;
    }

    public Optional<Character> pop() {
        return queue.isEmpty() ? Optional.empty() : Optional.of(queue.pop());
    }

    public Stream<String> stream() {
        return segments.stream();
    }
}
