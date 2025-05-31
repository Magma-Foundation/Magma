package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypescriptArrayType implements TypeScriptType {
    private final TypeScriptType childType;

    public TypescriptArrayType(TypeScriptType arrayType) {
        this.childType = arrayType;
    }

    @Override
    public Node serialize() {
        return new MapNode("array").withNodeSerialized("child", this.childType);
    }
}
