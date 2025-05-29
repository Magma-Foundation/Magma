package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public class Assignables {
    public static CompileResult<Assignable> deserializeError(Node node) {
        return Deserializers.orError("assignable", node, Lists.of(
                Deserializers.wrap(Definition::deserialize),
                Deserializers.wrap(Values::deserialize)
        ));
    }
}
