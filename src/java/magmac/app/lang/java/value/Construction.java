package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.invoke.Caller;
import magmac.app.lang.java.type.Types;

public record Construction(Type type) implements Caller {
    public static Option<CompileResult<Caller>> deserialize(Node node) {
        return node.deserializeWithType("construction").map(deserializer -> deserializer.withNode("type", Types::deserialize).complete(type -> new Construction(type)));
    }
}
