package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record Add(Value left, Value right) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("add").map(deserializer -> {
            return deserializer.withNode("left", Values::deserializeOrError)
                    .withNode("right", Values::deserializeOrError)
                    .complete(tuple -> new Add(tuple.left(), tuple.right()));
        });
    }
}
