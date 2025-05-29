package magmac.app.lang.java.block;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.define.Definition;

public record Catch(Definition definition) implements BlockHeader {
    public static Option<CompileResult<BlockHeader>> deserialize(Node node) {
        return node.deserializeWithType("catch").map(deserializer -> {
            return deserializer.withNode("definition", Definition::deserializeError)
                    .complete(Catch::new);
        });
    }
}
