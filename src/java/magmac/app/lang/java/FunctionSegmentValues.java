package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

class FunctionSegmentValues {
    public static CompileResult<FunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError(node, Lists.of(
                Return::deserialize
        ));
    }
}
