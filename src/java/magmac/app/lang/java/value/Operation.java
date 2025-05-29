package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializer;
import magmac.app.lang.java.Deserializers;

public record Operation(Value left, Operator operator, Value right) implements Value {
    public static Option<CompileResult<Value>> deserialize(Operator operator, Node node) {
        return node.deserializeWithType(operator.text()).map(deserializer -> {
            return deserializer.withNode("left", Values::deserializeOrError)
                    .withNode("right", Values::deserializeOrError)
                    .complete(tuple -> new Operation(tuple.left(), operator, tuple.right()));
        });
    }

    static Deserializer<Value> deserializeAs(Operator operator) {
        return Deserializers.wrap(node2 -> deserialize(operator, node2));
    }
}
