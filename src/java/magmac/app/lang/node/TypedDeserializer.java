package magmac.app.lang.node;

/**
 * A deserializer that only handles nodes of a specific type.
 */

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public interface TypedDeserializer<T> {
    Option<CompileResult<T>> deserialize(Node node);
}
