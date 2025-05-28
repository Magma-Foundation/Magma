package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record Return(Value value) implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return node.deserializeWithType("return").map(deserializer -> {
            return deserializer.node("value", Values::deserializeError).complete(value -> new Return(value));
        });
    }
}
