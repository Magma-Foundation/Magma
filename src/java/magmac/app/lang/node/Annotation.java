package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

record Annotation(String value) {
    public static CompileResult<Annotation> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("value")
                .complete(Annotation::new);
    }
}
