package magmac.app.stage.unit;

import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.parse.ParseState;

import java.util.function.BiFunction;

public final class ParseUnitImpl<T> implements ParseUnit<T> {
    private final ParseState state;
    private final T node;

    public ParseUnitImpl(ParseState state, T node) {
        this.state = state;
        this.node = node;
    }

    @Override
    public Unit<T> toLocationUnit() {
        return this.merge((ParseState state, T node) -> new SimpleUnit<>(state.findLocation(), node));
    }

    @Override
    public <R> R merge(BiFunction<ParseState, T, R> merge) {
        return merge.apply(this.state, this.node);
    }

    @Override
    public ParseUnit<NodeList> retainWithList() {
        return new ParseUnitImpl<>(this.state, InlineNodeList.empty());
    }

    @Override
    public ParseState left() {
        return this.state;
    }

    @Override
    public T right() {
        return this.node;
    }
}
