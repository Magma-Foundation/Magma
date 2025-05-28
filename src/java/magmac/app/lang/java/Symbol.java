package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

record Symbol(String value) implements Type {
    public static Option<CompileResult<Type>> deserialize(Node node) {
        return node.deserializeWithType("symbol-type").map(deserializer -> deserializer.withString("value")
                .complete(Symbol::new)
                .mapValue(type -> type));
    }
}
