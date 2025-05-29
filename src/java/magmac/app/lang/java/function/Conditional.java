package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.value.Value;
import magmac.app.lang.java.value.Values;

public record Conditional(ConditionalType type, Value condition) implements BlockHeader {
    public static Option<CompileResult<Conditional>> deserialize(ConditionalType type, Node node) {
        return node.deserializeWithType(type.name().toLowerCase()).map(deserializer -> {
            return deserializer.withNode("condition", Values::deserializeOrError)
                    .complete(value -> new Conditional(type, value));
        });
    }
}
