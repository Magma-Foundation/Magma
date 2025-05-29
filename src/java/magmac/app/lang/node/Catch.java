package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

record Catch(Definition definition) implements BlockHeader {
    public static Option<CompileResult<BlockHeader>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "catch").map(deserializer -> deserializer.withNode("definition", Definition::deserializeError)
                .complete(Catch::new));
    }
}
