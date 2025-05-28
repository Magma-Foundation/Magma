package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

public class Type {
    public static CompileResult<Type> deserialize(Node node) {
        return CompileResults.NodeErr("Not a type", node);
    }
}
