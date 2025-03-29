package magma;

import magma.collect.list.JavaList;
import magma.collect.list.List_;
import magma.collect.list.Lists;

import java.util.ArrayList;
import java.util.List;

public class MutableDividingState implements DividingState {
    private final List<String> segments;
    private StringBuilder buffer;
    private int depth;

    public MutableDividingState() {
        this(new ArrayList<>(), new StringBuilder(), 0);
    }

    public MutableDividingState(List<String> segments, StringBuilder buffer, int depth) {
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
        Lists.toNative(segments()).add(buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    private List<String> segments0() {
        return segments;
    }

    @Override
    public List_<String> segments() {
        return new JavaList<>(segments0());
    }
}
