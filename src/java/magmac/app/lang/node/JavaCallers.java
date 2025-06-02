package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public class JavaCallers {
    public static CompileResult<JavaCaller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(Construction::deserialize),
                Deserializers.wrap(Values::deserialize)
        ));
    }
}
