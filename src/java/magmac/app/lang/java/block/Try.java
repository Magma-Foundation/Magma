package magmac.app.lang.java.block;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Try implements BlockHeader {
    public static Option<CompileResult<Try>> deserialize(Node node) {
        return node.deserializeWithType("try").map(deserializer -> {
            return deserializer.complete(Try::new);
        });
    }
}
