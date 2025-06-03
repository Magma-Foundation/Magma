package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public interface TypedDeserializer<T> {
    Option<CompileResult<T>> deserialize(Node node);
}
