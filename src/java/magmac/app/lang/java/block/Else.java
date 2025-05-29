package magmac.app.lang.java.block;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Else implements BlockHeader {
    public static Option<CompileResult<Else>> deserialize(Node node) {
        return node.deserializeWithType("else").map(deserializer -> {
            return deserializer.complete(Else::new);
        });
    }
}
