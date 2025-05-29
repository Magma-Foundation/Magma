package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public interface BlockHeader {
    static CompileResult<BlockHeader> deserialize(Node node) {
        return Deserializers.orError("header", node, Lists.of(

        ));
    }
}
