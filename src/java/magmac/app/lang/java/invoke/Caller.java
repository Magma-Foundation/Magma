package magmac.app.lang.java.invoke;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.value.Construction;
import magmac.app.lang.java.value.Values;

public interface Caller {
    static CompileResult<Caller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Construction::deserialize,
                node1 -> Values.deserialize(node1).map(result -> result.mapValue(type -> type))
        ));
    }
}
