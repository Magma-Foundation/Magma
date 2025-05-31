package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

record Catch(JavaDefinition definition) implements BlockHeader {
    public static Option<CompileResult<BlockHeader>> deserialize(Node node) {
        return Deserializers.deserializeWithType("catch", node).map(deserializer -> deserializer.withNode("definition", JavaDefinition::deserialize)
                .complete(Catch::new));
    }
}
