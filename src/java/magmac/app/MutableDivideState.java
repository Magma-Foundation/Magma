package magmac.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MutableDivideState implements DivideState {
    private final List<String> segments;
    private StringBuilder buffer;

    public MutableDivideState(List<String> segments, StringBuilder buffer) {
        this.segments = segments;
        this.buffer = buffer;
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
}
