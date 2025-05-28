package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Definition(String name, Type right) {
    public static CompileResult<Definition> deserialize(Node node) {
        return node.deserialize()
                .string("name")
                .node("type", Type::deserialize)
                .complete((result) -> new Definition(result.left(), result.right()));
    }
}
