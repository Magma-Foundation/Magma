package magmac.app.compile.rule.divide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MutableDivideState implements DivideState {
    private final List<String> segments;
    private StringBuilder buffer;
    private int depth;

    public MutableDivideState(List<String> segments, StringBuilder buffer) {
        this.segments = segments;
        this.buffer = buffer;
        this.depth = 0;
    }

    public MutableDivideState() {
        this(new ArrayList<>(), new StringBuilder());
    }

    @Override
    public DivideState append(char c) {
        this.buffer.append(c);
        return this;
    }

    @Override
    public DivideState advance() {
        this.segments.add(this.buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    @Override
    public Stream<String> stream() {
        return this.segments.stream();
    }

    @Override
    public boolean isLevel() {
        return 0 == depth;
    }

    @Override
    public DivideState enter() {
        this.depth++;
        return this;
    }

    @Override
    public DivideState exit() {
        this.depth--;
        return this;
    }
}
