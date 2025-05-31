package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypeScriptTemplateType implements TypeScriptType {
    private final Symbol base;
    private final TypeArguments<TypeScriptType> typeArguments;

    public TypeScriptTemplateType(Symbol base, TypeArguments<TypeScriptType> typeArguments) {
        this.base = base;
        this.typeArguments = typeArguments;
    }

    @Override
    public Node serialize() {
        return new MapNode("template")
                .withNodeSerialized("base", this.base)
                .withNodeListSerialized("arguments", this.typeArguments.arguments());
    }
}
