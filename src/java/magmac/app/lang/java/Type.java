package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

interface Type {
    static CompileResult<Type> deserialize(Node node) {
        return Deserializers.orError("type", node, Lists.of(
                Deserializers.wrap(Symbol::deserialize)
        ));
    }
}
