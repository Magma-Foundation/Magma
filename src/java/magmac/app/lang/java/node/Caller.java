package magmac.app.lang.java.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public interface Caller {
    static CompileResult<Caller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Construction::deserialize,
                node1 -> Values.deserialize(node1).map(result -> result.mapValue(type -> type))
        ));
    }
}
