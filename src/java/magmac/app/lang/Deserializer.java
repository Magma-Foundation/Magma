package magmac.app.lang;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

/**
 * Converts a raw {@link Node} into a typed value.
 */
public interface Deserializer<T> {
    Option<CompileResult<T>> deserialize(Node node);
}
