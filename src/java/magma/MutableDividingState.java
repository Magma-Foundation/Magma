package magma;

import jvm.collect.list.Lists;
import magma.collect.list.List_;

public class MutableDividingState implements DividingState {
    private List_<String> segments;
    private StringBuilder buffer;
    private int depth;

    public MutableDividingState() {
        this(Lists.createEmpty(), new StringBuilder(), 0);
    }

    public MutableDividingState(List_<String> segments, StringBuilder buffer, int depth) {
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
}
