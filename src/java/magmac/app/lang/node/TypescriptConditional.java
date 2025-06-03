package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypescriptConditional extends Conditional<TypeScriptValue> implements TypescriptBlockHeader {
    public TypescriptConditional(ConditionalType type, TypeScriptValue condition) {
        super(type, condition);
    }

    @Override
    public Node serialize() {
        return new MapNode(this.type.name().toLowerCase())
                .withNodeSerialized("condition", this.condition);
    }
}
