package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

final class JavaRootSegments {
    public static CompileResult<JavaRootSegment> deserialize(Node node) {
        return Iters.fromValues(NamespacedType.values())
                .map((NamespacedType type) -> Namespaced.deserialize(type, node))
                .flatMap((Option<CompileResult<JavaRootSegment>> option) -> Iters.fromOption(option))
                .next()
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize", node));
    }

    public static Node serialize(JavaRootSegment segment) {
        return new MapNode();
    }
}
