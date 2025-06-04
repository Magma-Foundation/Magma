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
    private boolean inSingle;
    private boolean inDouble;
    private boolean inLineComment;
    private boolean inBlockComment;
    private boolean escape;
    private char last;

    private MutableDivideState(List<String> segments, StringBuilder buffer, String input) {
        this.segments = segments;
        this.buffer = buffer;
        this.input = input;
        this.depth = 0;
        this.inSingle = false;
        this.inDouble = false;
        this.inLineComment = false;
        this.inBlockComment = false;
        this.escape = false;
        this.last = 0;
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
        this.segments = this.segments.addLast(this.buffer.toString());
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
            var c = this.input.charAt(this.index);
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
            var append = popped.left().append(popped.right());
            return new Tuple2<>(append, popped.right());
        });
    }

    @Override
    public Option<DivideState> popAndAppendToOption() {
        return this.popAndAppendToTuple().map(Tuple2::left);
    }

    @Override
    public char peek() {
        return this.input.charAt(this.index);
    }

    @Override
    public boolean inSingle() {
        return this.inSingle;
    }

    @Override
    public DivideState inSingle(boolean inSingle) {
        this.inSingle = inSingle;
        return this;
    }

    @Override
    public boolean inDouble() {
        return this.inDouble;
    }

    @Override
    public DivideState inDouble(boolean inDouble) {
        this.inDouble = inDouble;
        return this;
    }

    @Override
    public boolean inLineComment() {
        return this.inLineComment;
    }

    @Override
    public DivideState inLineComment(boolean inLineComment) {
        this.inLineComment = inLineComment;
        return this;
    }

    @Override
    public boolean inBlockComment() {
        return this.inBlockComment;
    }

    @Override
    public DivideState inBlockComment(boolean inBlockComment) {
        this.inBlockComment = inBlockComment;
        return this;
    }

    @Override
    public boolean escape() {
        return this.escape;
    }

    @Override
    public DivideState escape(boolean escape) {
        this.escape = escape;
        return this;
    }

    @Override
    public char last() {
        return this.last;
    }

    @Override
    public DivideState last(char c) {
        this.last = c;
        return this;
    }
}
