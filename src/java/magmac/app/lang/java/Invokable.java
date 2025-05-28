package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.value.Caller;
import magmac.app.lang.java.value.Value;
import magmac.app.lang.java.value.Values;

public record Invokable(Caller left, List<Value> right) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("invokable").map(deserializer -> deserializer.withNode("caller", Caller::deserialize)
                .nodeList("arguments", Values::deserializeError)
                .complete(tuple -> new Invokable(tuple.left(), tuple.right())));
    }
}
