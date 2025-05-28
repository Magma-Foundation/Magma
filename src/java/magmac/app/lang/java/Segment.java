package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Segment(String value) {
    public static CompileResult<Segment> deserialize(Node node) {
        return node.deserialize()
                .string("value")
                .complete((String value1) -> new Segment(value1));
    }
}
