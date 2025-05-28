package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Modifier(String value) {
    public static CompileResult<Modifier> deserialize(Node node) {
        return node.deserialize()
                .withString("value")
                .complete(Modifier::new);
    }
}
