package magmac.app.lang.java.define;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record Annotation(String value) {
    public static CompileResult<Annotation> deserialize(Node node) {
        return node.deserialize()
                .withString("value")
                .complete(Annotation::new);
    }
}
