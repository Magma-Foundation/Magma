package magmac.app.lang.java.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public class Else implements BlockHeader {
    public static Option<CompileResult<Else>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "else").map(deserializer -> {
            return deserializer.complete(Else::new);
        });
    }
}
