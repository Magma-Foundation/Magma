package magma.app.compile.text;

import jvm.api.collect.list.Lists;
import jvm.api.text.Strings;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public record DivideState(List<String> segments, String buffer, int depth, String input, int index) {
    public static DivideState createInitial(final String input) {
        return new DivideState(Lists.empty(), "", 0, input, 0);
    }

    public DivideState advance() {
        return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
    }

    public DivideState append(final char c) {
        return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
    }

    public boolean isLevel() {
        return 0 == this.depth;
    }

    public DivideState enter() {
        return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
    }

    public DivideState exit() {
        return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
    }

    public boolean isShallow() {
        return 1 == this.depth;
    }

    public Option<Tuple2<DivideState, Character>> pop() {
        if (this.index >= Strings.length(this.input)) {
            return new None<Tuple2<DivideState, Character>>();
        }

        final var c = Strings.charAt(this.input, this.index);
        final var nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
        return new Some<Tuple2<DivideState, Character>>(new Tuple2Impl<DivideState, Character>(nextState, c));
    }

    public Option<Tuple2<DivideState, Character>> popAndAppendToTuple() {
        return this.pop().map((Tuple2<DivideState, Character> inner) -> new Tuple2Impl<DivideState, Character>(inner.left().append(inner.right()), inner.right()));
    }

    public Option<DivideState> popAndAppendToOption() {
        return this.popAndAppendToTuple().map((Tuple2<DivideState, Character> tuple) -> tuple.left());
    }

    public char peek() {
        return Strings.charAt(this.input, this.index);
    }

    public boolean startsWith(final String slice) {
        return Strings.sliceFrom(this.input, this.index).startsWith(slice);
    }
}
