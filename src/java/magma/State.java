package magma;

import magma.api.collect.List_;
import magma.api.collect.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Tuple;

public class State {
    private List_<Character> queue;
    private List_<String> segments;
    private StringBuilder buffer;
    private int depth;

    public State(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
        this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    }

    Option<Tuple<State, Character>> popNext() {
        Option<Tuple<Character, List_<Character>>> maybeNext = getQueue().popFirst();
        if (maybeNext.isEmpty()) return new None<>();

        final Tuple<Character, List_<Character>> nextTuple = maybeNext.orElse(new Tuple<>('\0', getQueue()));
        Character left = nextTuple.left();
        State withQueue = setQueue(nextTuple.right());

        return new Some<>(new Tuple<>(withQueue, left));
    }

    State advance() {
        setSegments(getSegments().add(getBuffer().toString()));
        setBuffer(new StringBuilder());
        return this;
    }

    State exit() {
        setDepth(getDepth() - 1);
        return this;
    }

    State enter() {
        setDepth(getDepth() + 1);
        return this;
    }

    State append(char c) {
        getBuffer().append(c);
        return this;
    }

    Stream<String> stream() {
        return getSegments().stream();
    }

    public List_<Character> getQueue() {
        return queue;
    }

    public State setQueue(List_<Character> queue) {
        this.queue = queue;
        return this;
    }

    public List_<String> getSegments() {
        return segments;
    }

    public void setSegments(List_<String> segments) {
        this.segments = segments;
    }

    public StringBuilder getBuffer() {
        return buffer;
    }

    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
