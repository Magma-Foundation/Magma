package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public sealed interface JavaMethodHeader permits Constructor, JavaDefinition {
    static CompileResult<JavaMethodHeader> deserializeError(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(JavaDefinition::deserializeTyped),
                Deserializers.wrap(Constructor::deserialize)
        ));
    }
}
