package magmac.app.lang.common;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record Annotation(String value) {
    public static CompileResult<Annotation> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("value")
                .complete(Annotation::new);
    }
}
