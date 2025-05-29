package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

interface MethodHeader {
    static CompileResult<MethodHeader> deserializeError(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(Definition::deserialize),
                Deserializers.wrap(Constructor::deserialize)
        ));
    }
}
