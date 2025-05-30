package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

final class EnumValue {
    public static CompileResult<EnumValue> deserialize(Node node) {
        return Deserializers.destruct(node)
                .complete(EnumValue::new);
    }
}
