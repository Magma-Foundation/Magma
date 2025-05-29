package magmac.app.lang.java.structure;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Whitespace;
import magmac.app.lang.java.function.MethodNode;

final class ClassMembers {
    public static CompileResult<ClassMember> deserialize(Node node) {
        return Deserializers.orError("class-member", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                MethodNode::deserialize
        ));
    }
}
