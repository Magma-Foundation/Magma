package magmac.app;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final List<String> segments;
    private StringBuilder buffer;

    public State(List<String> segments, StringBuilder buffer) {
        this.segments = segments;
        this.buffer = buffer;
    }

    public State() {
        this(new ArrayList<>(), new StringBuilder());
    }

    public State append(char c) {
        this.buffer.append(c);
        return this;
    }

    public State advance() {
        this.segments.add(this.buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    public List<String> segments() {
        return this.segments;
    }
}
