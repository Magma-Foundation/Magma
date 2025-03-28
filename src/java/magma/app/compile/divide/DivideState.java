package magma.app.compile.divide;

import jvm.api.collect.Lists;
import magma.api.collect.List_;
import magma.api.collect.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Tuple;

public record DivideState(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
    public DivideState(List_<Character> queue) {
        this(queue, Lists.empty(), new StringBuilder(), 0);
    }

    public boolean isLevel() {
        return depth == 0;
    }

    public boolean isShallow() {
        return depth == 1;
    }

    Option<Tuple<DivideState, Character>> pop() {
        Option<Tuple<Character, List_<Character>>> maybeNext = queue.popFirst();
        if (maybeNext.isEmpty()) return new None<>();

        final Tuple<Character, List_<Character>> nextTuple = maybeNext.orElse(new Tuple<>('\0', queue));
        Character left = nextTuple.left();
        DivideState state = new DivideState(nextTuple.right(), segments, buffer, depth);
        return new Some<>(new Tuple<>(state, left));
    }

    public DivideState advance() {
        return new DivideState(queue, segments.add(buffer.toString()), new StringBuilder(), depth);
    }

    public DivideState exit() {
        return new DivideState(queue, segments, buffer, depth - 1);
    }

    public DivideState enter() {
        return new DivideState(queue, segments, buffer, depth + 1);
    }

    public DivideState appendChar(char c) {
        return new DivideState(queue, segments, buffer.append(c), depth);
    }

    Stream<String> stream() {
        return segments.stream();
    }

    public Option<Tuple<DivideState, Character>> append() {
        return pop().map(tuple -> {
            DivideState appended = tuple.left().appendChar(tuple.right());
            return new Tuple<>(appended, tuple.right());
        });
    }

    public Option<DivideState> appendAndDiscard() {
        return append().map(Tuple::left);
    }
}
