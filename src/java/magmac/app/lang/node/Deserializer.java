package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Serializable;

public interface Deserializer<T> {
    CompileResult<T> deserialize(Node node);
}
