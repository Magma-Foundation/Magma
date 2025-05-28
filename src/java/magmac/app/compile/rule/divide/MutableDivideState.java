package magmac.app.compile.rule.divide;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iter;

public class MutableDivideState implements DivideState {
    private final String input;
    private List<String> segments;
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
        this(Lists.empty(), new StringBuilder(), input);
    }

    @Override
    public DivideState append(char c) {
        this.buffer.append(c);
        return this;
    }

    @Override
    public DivideState advance() {
        this.segments = this.segments.add(this.buffer.toString());
        this.buffer = new StringBuilder();
        return this;
    }

    @Override
    public Iter<String> iter() {
        return this.segments.iter();
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
    public Option<Tuple2<DivideState, Character>> pop() {
        if (this.index < this.input.length()) {
            char c = this.input.charAt(this.index);
            this.index++;
            return new Some<>(new Tuple2<DivideState, Character>(this, c));
        }
        else {
            return new None<>();
        }
    }

    @Override
    public Option<Tuple2<DivideState, Character>> popAndAppendToTuple() {
        return this.pop().map((Tuple2<DivideState, Character> popped) -> {
            DivideState append = popped.left().append(popped.right());
            return new Tuple2<>(append, popped.right());
        });
    }

    @Override
    public Option<DivideState> popAndAppendToOption() {
        return this.popAndAppendToTuple().map((Tuple2<DivideState, Character> divideStateCharacterTuple2) -> divideStateCharacterTuple2.left());
    }

    @Override
    public char peek() {
        return this.input.charAt(this.index);
    }
}
