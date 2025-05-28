package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Parameters {
    public static CompileResult<Parameter> deserialize(Node node) {
        return Deserializers.orError("parameter", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(Definition::deserialize)
        ));
    }
}
