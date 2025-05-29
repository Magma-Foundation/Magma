package magmac.app.lang.java.type;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Type;

public record Variadic(Type node) implements Type {
    public static Option<CompileResult<Type>> deserialize(Node node) {
        return node.deserializeWithType("variadic").map(deserializer -> {
            return deserializer.withNode("child", Types::deserialize)
                    .complete(Variadic::new);
        });
    }
}
