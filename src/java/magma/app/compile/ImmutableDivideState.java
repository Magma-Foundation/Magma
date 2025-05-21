package magma.app.compile;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iter;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;

public record ImmutableDivideState(List<String> segments, String buffer, int depth, String input,
                                   int index) implements DivideState {
    @Override
    public Iter<String> query() {
        return this.segments.iter();
    }

    @Override
    public DivideState advance() {
        return new ImmutableDivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
    }

    @Override
    public DivideState append(char c) {
        return new ImmutableDivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
    }

    @Override
    public boolean isLevel() {
        return 0 == this.depth;
    }

    @Override
    public DivideState enter() {
        return new ImmutableDivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
    }

    @Override
    public DivideState exit() {
        return new ImmutableDivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
    }

    @Override
    public boolean isShallow() {
        return 1 == this.depth;
    }

    @Override
    public Option<Tuple2<DivideState, Character>> pop() {
        if (this.index >= Strings.length(this.input)) {
            return new None<Tuple2<DivideState, Character>>();
        }

        var c = this.input.charAt(this.index);
        var nextState = new ImmutableDivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
        return new Some<Tuple2<DivideState, Character>>(new Tuple2Impl<DivideState, Character>(nextState, c));
    }

    @Override
    public Option<Tuple2<DivideState, Character>> popAndAppendToTuple() {
        return this.pop().map((Tuple2<DivideState, Character> inner) -> {
            return new Tuple2Impl<DivideState, Character>(inner.left().append(inner.right()), inner.right());
        });
    }

    @Override
    public Option<DivideState> popAndAppendToOption() {
        return this.popAndAppendToTuple().map((Tuple2<DivideState, Character> tuple) -> {
            return tuple.left();
        });
    }

    @Override
    public char peek() {
        return this.input.charAt(this.index);
    }

    @Override
    public boolean startsWith(String slice) {
        return Strings.sliceFrom(this.input, this.index).startsWith(slice);
    }
}
