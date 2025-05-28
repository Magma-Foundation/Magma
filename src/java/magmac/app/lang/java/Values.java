package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Values {
    public static CompileResult<Value> deserialize(Node node) {
        return Deserializers.or(node, Lists.of(
        ));
    }
}
