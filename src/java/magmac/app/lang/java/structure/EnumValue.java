package magmac.app.lang.java.structure;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class EnumValue {
    public static CompileResult<EnumValue> deserialize(Node node) {
        return node.deserialize()
                .complete(() -> new EnumValue());
    }
}
