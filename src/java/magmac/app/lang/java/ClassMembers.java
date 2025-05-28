package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

final class ClassMembers {
    public static CompileResult<ClassMember> deserialize(Node node) {
        return MethodNode.deserialize(node).orElseGet(() -> CompileResults.NodeErr("Not a class member", node));
    }
}
