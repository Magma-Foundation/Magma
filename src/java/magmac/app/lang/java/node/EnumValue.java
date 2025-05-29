package magmac.app.lang.java.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public class EnumValue {
    public static CompileResult<EnumValue> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .complete(() -> new EnumValue());
    }
}
