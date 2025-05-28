package magmac.app.stage;

import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.parse.ParseState;

import java.util.function.BiFunction;

public final class ParseUnit<T> {
    private final ParseState state;
    private final T node;

    public ParseUnit(ParseState state, T node) {
        this.state = state;
        this.node = node;
    }

    public Unit<T> toLocationUnit() {
        return this.merge((state, node) -> new SimpleUnit<>(state.findLocation(), node));
    }

    public <R> R merge(BiFunction<ParseState, T, R> merge) {
        return merge.apply(this.state, this.node);
    }

    public ParseUnit<NodeList> retainWithList() {
        return new ParseUnit<>(this.state, InlineNodeList.empty());
    }

    public ParseState left() {
        return this.state;
    }

    public T right() {
        return this.node;
    }

    public ParseState state() {
        return this.state;
    }
}
