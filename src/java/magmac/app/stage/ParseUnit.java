package magmac.app.stage;

import magmac.app.compile.node.NodeList;
import magmac.app.stage.parse.ParseState;

import java.util.function.BiFunction;

public interface ParseUnit<T> {
    Unit<T> toLocationUnit();

    <R> R merge(BiFunction<ParseState, T, R> merge);

    ParseUnit<NodeList> retainWithList();

    @Deprecated
    ParseState left();

    @Deprecated
    T right();
}
