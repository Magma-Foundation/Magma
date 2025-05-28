package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public interface Caller {
    static CompileResult<Caller> deserialize(Node node) {
        return Deserializers.orError(node, Lists.of(
                Construction::deserialize,
                node1 -> Values.deserialize(node1).map(result -> result.mapValue(type -> type))
        ));
    }
}
