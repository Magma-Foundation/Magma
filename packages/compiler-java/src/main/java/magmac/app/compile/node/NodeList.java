package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.iter.Iter;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

/**
 * Immutable collection of {@link Node} values.
 */
public interface NodeList {
    Iter<Node> iter();

    Option<Node> findLast();

    NodeList add(Node element);

    NodeList addAll(NodeList others);

    CompileResult<String> join(String delimiter, Function<Node, CompileResult<String>> generator);
}
