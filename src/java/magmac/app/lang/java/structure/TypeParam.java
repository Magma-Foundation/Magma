package magmac.app.lang.java.structure;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public record TypeParam(String value) {
    public static CompileResult<TypeParam> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .withString("value")
                .complete(TypeParam::new);
    }
}
