package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypescriptStructureNode implements TypeScriptRootSegment {
    private final TypescriptStructureType type;
    private final StructureValue<TypescriptStructureMember> value;

    public TypescriptStructureNode(TypescriptStructureType type, StructureValue<TypescriptStructureMember> structureNode) {
        this.type = type;
        this.value = structureNode;
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
