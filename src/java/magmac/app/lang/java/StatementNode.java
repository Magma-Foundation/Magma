package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record StatementNode(FunctionSegmentValue value) implements FunctionSegment {
    public static Option<CompileResult<FunctionSegment>> deserialize(Node node) {
        return node.deserializeWithType("statement").map(deserializer -> deserializer.withNode("child", FunctionSegmentValues::deserialize)
                .complete(StatementNode::new)
                .mapValue(value -> value));
    }
}
