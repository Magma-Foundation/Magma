package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

final class Assignables {
    public static CompileResult<Assignable> deserializeError(Node node) {
        return Deserializers.orError("assignable", node, Lists.of(
                Deserializers.wrap(JavaDefinition::deserializeTyped),
                Deserializers.wrap(Values::deserialize)
        ));
    }
}
