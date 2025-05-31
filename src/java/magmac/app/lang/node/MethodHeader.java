package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.Serializable;

public sealed interface MethodHeader extends Serializable permits JavaConstructor, JavaDefinition {
    static CompileResult<MethodHeader> deserializeError(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(JavaDefinition::deserialize),
                Deserializers.wrap(JavaConstructor::deserialize)
        ));
    }
}
