package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.web.TypescriptValue;

public final class TypescriptConditional extends Conditional<TypescriptValue> implements TypescriptBlockHeader {
    public TypescriptConditional(ConditionalType type, TypescriptValue condition) {
        super(type, condition);
    }

    @Override
    public Node serialize() {
        return new MapNode(this.type.name().toLowerCase())
                .withNodeSerialized("condition", this.condition);
    }
}
