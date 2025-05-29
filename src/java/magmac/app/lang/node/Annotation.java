package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

record Annotation(String value) {
    public static CompileResult<Annotation> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .withString("value")
                .complete(Annotation::new);
    }
}
