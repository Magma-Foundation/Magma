package magmac.app.stage.unit;

import magmac.app.compile.node.NodeList;
import magmac.app.stage.parse.ParseState;

import java.util.function.BiFunction;

/**
 * Compilation unit produced by the parser.
 */
public interface ParseUnit<T> {
    Unit<T> toLocationUnit();

    <R> R merge(BiFunction<ParseState, T, R> merge);

    ParseUnit<NodeList> retainWithList();

    @Deprecated
    ParseState left();

    @Deprecated
    T right();
}
