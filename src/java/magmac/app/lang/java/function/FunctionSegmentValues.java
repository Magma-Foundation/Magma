package magmac.app.lang.java.function;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializer;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.define.Assignment;
import magmac.app.lang.java.function.segment.Return;

public final class FunctionSegmentValues {
    public static CompileResult<FunctionSegmentValue> deserialize(Node node) {
        return Deserializers.orError("function-segment-value", node, Lists.<Deserializer<FunctionSegmentValue>>of(
                Return::deserialize,
                Assignment::deserialize
        ));
    }
}
