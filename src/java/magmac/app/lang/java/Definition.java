package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Definition(String name) {
    public static CompileResult<Definition> deserialize(Node node) {
        return node.deserialize()
                .string("name")
                .complete((String name1) -> new Definition(name1));
    }
}
