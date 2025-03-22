package magma;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MutableDivideState implements DivideState {
    private final Deque<Character> queue;
    private final List<String> segments;
    private StringBuilder buffer;
    private int depth;

    public MutableDivideState(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
        this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    }

    public MutableDivideState(Deque<Character> queue) {
        this(queue, new LinkedList<>(), new StringBuilder(), 0);
    }

    @Override
    public Optional<DivideState> popAndAppend() {
        return pop().map(this::append);
    }

    @Override
    public DivideState exit() {
        this.depth = depth - 1;
        return this;
    }

    @Override
    public DivideState enter() {
        this.depth = depth + 1;
        return this;
    }

    @Override
    public Optional<Character> pop() {
        if (queue.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(queue.pop());
        }
    }

    @Override
    public DivideState append(char maybeEscape) {
        buffer.append(maybeEscape);
        return this;
    }

    @Override
    public boolean isLevel() {
        return depth == 0;
    }

    @Override
    public boolean isShallow() {
        return depth == 1;
    }

    @Override
    public DivideState advance() {
        List<String> segments = segments();
        segments.add(buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    @Override
    public List<String> segments() {
        return segments;
    }
}
