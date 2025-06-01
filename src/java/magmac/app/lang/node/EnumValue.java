package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

final record EnumValue(String value) {
    public static CompileResult<EnumValue> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("value")
                .complete(EnumValue::new);
    }
}
