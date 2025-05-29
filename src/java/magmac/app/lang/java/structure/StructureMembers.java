package magmac.app.lang.java.structure;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Whitespace;
import magmac.app.lang.java.function.MethodNode;

final class StructureMembers {
    public static CompileResult<StructureMember> deserialize(Node node) {
        return Deserializers.orError("structure-member", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Deserializers.wrap(MethodNode::deserialize),
                Deserializers.wrap(StructureStatement::deserialize),
                Deserializers.wrap(EnumValues::deserialize)
        ));
    }
}
