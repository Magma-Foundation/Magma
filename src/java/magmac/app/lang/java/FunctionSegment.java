package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

interface FunctionSegment {
    static CompileResult<FunctionSegment> deserialize(Node node) {
        return Deserializers.orError(node, Lists.of(
                StatementNode::deserialize
        ));
    }
}
