package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypescriptArrayType implements TypeScriptType {
    private final ArrayType<TypeScriptType> arrayType;

    public TypescriptArrayType(ArrayType<TypeScriptType> arrayType) {
        this.arrayType = arrayType;
    }

    @Override
    public Node serialize() {
        return new MapNode("array").withNodeSerialized("child", this.arrayType.child());
    }
}
