package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.define.Definition;

public interface MethodHeader {
    static CompileResult<MethodHeader> deserializeError(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(Definition::deserialize),
                Deserializers.wrap(Constructor::deserialize)
        ));
    }
}
