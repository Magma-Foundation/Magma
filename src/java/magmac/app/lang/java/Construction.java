package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Construction(Type type) implements Caller {
    public static Option<CompileResult<Caller>> deserialize(Node node) {
        return node.deserializeWithType("construction").map(deserializer -> {
            return deserializer.node("type", Type::deserialize).complete(type -> {
                return new Construction(type);
            });
        });
    }
}
