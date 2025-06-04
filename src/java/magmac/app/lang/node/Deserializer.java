package magmac.app.lang.node;

/**
 * Converts a raw {@link Node} produced by the parser into a typed AST element.
 */

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public interface Deserializer<T> {
    CompileResult<T> deserialize(Node node);
}
