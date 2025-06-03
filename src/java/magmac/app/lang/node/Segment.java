package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record Segment(String value) {
    public static CompileResult<Segment> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("value")
                .complete(Segment::new);
    }

    public Node serialize() {
        return new MapNode("segment").withString("value", this.value);
    }
}
