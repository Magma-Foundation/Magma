package magmac.app.lang.java;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

final class JavaRootSegments {
    public static CompileResult<JavaRootSegment> deserialize(Node node) {
        return Deserializers.orError("root-segment", node, Lists.of(
                Namespaced::deserialize,
                ClassNode::deserialize
        ));
    }

    public static Node serialize(JavaRootSegment segment) {
        return new MapNode();
    }
}
