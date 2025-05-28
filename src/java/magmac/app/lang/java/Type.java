package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

interface Type {
    static CompileResult<Type> deserialize(Node node) {
        return Symbol.deserialize(node)
                .orElseGet(() -> CompileResults.NodeErr("Not a type", node));
    }
}
