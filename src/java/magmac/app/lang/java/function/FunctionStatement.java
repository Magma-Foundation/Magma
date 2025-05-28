package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record FunctionStatement(FunctionSegmentValue value) implements FunctionSegment {
    public static Option<CompileResult<FunctionSegment>> deserialize(Node node) {
        return node.deserializeWithType("statement").map(deserializer -> deserializer.withNode("child", FunctionSegmentValues::deserialize)
                .complete(FunctionStatement::new)
                .mapValue(value -> value));
    }
}
