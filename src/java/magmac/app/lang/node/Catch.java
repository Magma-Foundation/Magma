package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaDefinition;

public record Catch(JavaDefinition definition) implements JavaBlockHeader {
    public static Option<CompileResult<JavaBlockHeader>> deserialize(Node node) {
        return Destructors.destructWithType("catch", node).map(deserializer -> deserializer.withNode("definition", JavaDefinition::deserialize)
                .complete(Catch::new));
    }
}
