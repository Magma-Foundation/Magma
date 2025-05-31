package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

record Catch(JavaDefinition definition) implements BlockHeader {
    public static Option<CompileResult<BlockHeader>> deserialize(Node node) {
        return Destructors.destructWithType("catch", node).map(deserializer -> deserializer.withNode("definition", JavaDefinition::deserialize)
                .complete(Catch::new));
    }
}
