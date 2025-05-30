package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record JavaStructureNode(
        StructureType type,
        String name,
        List<Modifier> modifiers,
        List<StructureMember> members,
        Option<List<Type>> implemented,
        Option<List<TypeParam>> typeParams,
        Option<List<Parameter>> parameters,
        Option<List<Type>> extended,
        Option<List<Type>> variants
) implements JavaRootSegment {

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
