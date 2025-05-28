package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record Segment(String value) {
    public static CompileResult<Segment> deserialize(Node node) {
        return node.deserialize()
                .withString("value")
                .complete(Segment::new);
    }
}
