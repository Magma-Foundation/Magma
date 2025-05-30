package magmac.app.lang.node;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.Serializable;

record TypeParam(String value) implements Serializable {
    public static CompileResult<TypeParam> deserialize(Node node) {
        return Deserializers.destruct(node)
                .withString("value")
                .complete(TypeParam::new);
    }

    @Override
    public Node serialize() {
        return new MapNode().withString("value", this.value);
    }
}
