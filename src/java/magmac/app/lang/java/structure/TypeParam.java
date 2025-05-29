package magmac.app.lang.java.structure;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record TypeParam(String value) {
    public static CompileResult<TypeParam> deserialize(Node node) {
        return node.deserialize()
                .withString("value")
                .complete(TypeParam::new);
    }
}
