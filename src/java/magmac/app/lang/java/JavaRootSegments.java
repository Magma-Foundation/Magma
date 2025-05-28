package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

final class JavaRootSegments {
    public static CompileResult<JavaRootSegment> deserialize(Node node) {
        return Namespaced.deserializeNamespaces(node)
                .or(() -> ClassNode.deserialize(node))
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize", node));
    }

    public static Node serialize(JavaRootSegment segment) {
        return new MapNode();
    }
}
