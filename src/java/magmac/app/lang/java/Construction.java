package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.value.Caller;

public record Construction(Type type) implements Caller {
    public static Option<CompileResult<Caller>> deserialize(Node node) {
        return node.deserializeWithType("construction").map(deserializer -> deserializer.withNode("type", Type::deserialize).complete(type -> new Construction(type)));
    }
}
