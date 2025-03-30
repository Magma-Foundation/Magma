package magma.compile.rule.divide;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.option.Option;
import magma.option.Tuple;

public class MutableDividingState implements DividingState {
    private final List_<Character> queue;
    private List_<String> segments;
    private StringBuilder buffer;
    private int depth;

    public MutableDividingState(List_<Character> queue) {
        this(queue, Lists.empty(), new StringBuilder(), 0);
    }

    public MutableDividingState(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
        this.queue = queue;
        this.segments = segments;
        this.buffer = buffer;
        this.depth = depth;
    }

    @Override
    public DividingState append(char c) {
        buffer.append(c);
        return this;
    }

    @Override
    public boolean isLevel() {
        return depth == 0;
    }

    @Override
    public DividingState exit() {
        this.depth = depth - 1;
        return this;
    }

    @Override
    public DividingState enter() {
        this.depth = depth + 1;
        return this;
    }

    @Override
    public DividingState advance() {
        this.segments = segments.add(buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    @Override
    public List_<String> segments() {
        return segments;
    }

    @Override
    public boolean isShallow() {
        return depth == 1;
    }

    @Override
    public Option<Tuple<Character, DividingState>> append() {
        return pop().map(tuple -> {
            return new Tuple<>(tuple.left(), tuple.right().append(tuple.left()));
        });
    }

    @Override
    public Option<Tuple<Character, DividingState>> pop() {
        return queue.popFirst().map(tuple -> new Tuple<>(tuple.left(), new MutableDividingState(tuple.right(), segments, buffer, depth)));
    }

    @Override
    public Option<DividingState> appendAndDiscard() {
        return append().map(Tuple::right);
    }
}
