package magma;

import jvm.api.collect.Lists;
import magma.api.collect.List_;
import magma.api.collect.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Tuple;

public class DivideState {
    private List_<Character> queue;
    private List_<String> segments;
    private StringBuilder buffer;
    private int depth;

    public DivideState(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
        this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    }

    public DivideState(List_<Character> queue) {
        this(queue, Lists.empty(), new StringBuilder(), 0);
    }

    boolean isLevel() {
        return getDepth() == 0;
    }

    boolean isShallow() {
        return getDepth() == 1;
    }

    Option<Tuple<DivideState, Character>> pop() {
        Option<Tuple<Character, List_<Character>>> maybeNext = getQueue().popFirst();
        if (maybeNext.isEmpty()) return new None<>();

        final Tuple<Character, List_<Character>> nextTuple = maybeNext.orElse(new Tuple<>('\0', getQueue()));
        Character left = nextTuple.left();
        DivideState withQueue = setQueue(nextTuple.right());

        return new Some<>(new Tuple<>(withQueue, left));
    }

    DivideState advance() {
        setSegments(getSegments().add(getBuffer().toString()));
        setBuffer(new StringBuilder());
        return this;
    }

    DivideState exit() {
        setDepth(getDepth() - 1);
        return this;
    }

    DivideState enter() {
        setDepth(getDepth() + 1);
        return this;
    }

    DivideState append(char c) {
        getBuffer().append(c);
        return this;
    }

    Stream<String> stream() {
        return getSegments().stream();
    }

    public List_<Character> getQueue() {
        return queue;
    }

    public DivideState setQueue(List_<Character> queue) {
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

    public Option<Tuple<DivideState, Character>> append() {
        return pop().map(tuple -> {
            DivideState appended = tuple.left().append(tuple.right());
            return new Tuple<>(appended, tuple.right());
        });
    }

    public Option<DivideState> appendAndDiscard() {
        return append().map(Tuple::left);
    }
}
