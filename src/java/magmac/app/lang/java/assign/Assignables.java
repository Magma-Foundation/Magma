package magmac.app.lang.java.assign;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.define.Definition;
import magmac.app.lang.java.value.Values;

public class Assignables {
    public static CompileResult<Assignable> deserializeError(Node node) {
        return Deserializers.orError("assignable", node, Lists.of(
                Deserializers.wrap(Definition::deserialize),
                Deserializers.wrap(Values::deserialize)
        ));
    }
}
