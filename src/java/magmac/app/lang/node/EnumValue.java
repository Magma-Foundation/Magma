package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public class EnumValue {
    public static CompileResult<EnumValue> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .complete(() -> new EnumValue());
    }
}
