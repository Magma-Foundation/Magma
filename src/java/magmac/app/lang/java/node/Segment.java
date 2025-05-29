package magmac.app.lang.java.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public record Segment(String value) {
    public static CompileResult<Segment> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .withString("value")
                .complete(Segment::new);
    }
}
