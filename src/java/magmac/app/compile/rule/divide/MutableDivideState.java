package magmac.app.compile.rule.divide;

import magmac.api.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MutableDivideState implements DivideState {
    private final List<String> segments;
    private final String input;
    private StringBuilder buffer;
    private int depth;
    private int index = 0;

    private MutableDivideState(List<String> segments, StringBuilder buffer, String input) {
        this.segments = segments;
        this.buffer = buffer;
        this.input = input;
        this.depth = 0;
    }

    public MutableDivideState(String input) {
        this(new ArrayList<>(), new StringBuilder(), input);
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
        return 0 == this.depth;
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

    @Override
    public boolean isShallow() {
        return 1 == this.depth;
    }

    @Override
    public Optional<Tuple2<DivideState, Character>> pop() {
        if (this.index < this.input.length()) {
            char c = this.input.charAt(this.index);
            this.index++;
            return Optional.of(new Tuple2<>(this, c));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tuple2<DivideState, Character>> popAndAppendToTuple() {
        return this.pop().map(popped -> {
            DivideState append = popped.left().append(popped.right());
            return new Tuple2<>(append, popped.right());
        });
    }

    @Override
    public Optional<DivideState> popAndAppendToOption() {
        return this.popAndAppendToTuple().map(Tuple2::left);
    }
}
