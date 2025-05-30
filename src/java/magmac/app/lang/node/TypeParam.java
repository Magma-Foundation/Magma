package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

record TypeParam(String value) {
    public static CompileResult<TypeParam> deserialize(Node node) {
        return Deserializers.destruct(node)
                .withString("value")
                .complete(TypeParam::new);
    }
}
