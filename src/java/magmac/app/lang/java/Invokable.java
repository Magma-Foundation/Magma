package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record Invokable(Type left, List<Value> right) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("invokable").map(deserializer -> {
            return deserializer.node("type", Type::deserialize)
                    .nodeList("arguments", Values::deserialize)
                    .complete(tuple -> new Invokable(tuple.left(), tuple.right()));
        });
    }
}
