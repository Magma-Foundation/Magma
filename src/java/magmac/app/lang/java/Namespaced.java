package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.EmptyDestroyer;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

record Namespaced(NamespacedType type, List<Segment> segments) implements JavaRootSegment {
    static Option<CompileResult<JavaRootSegment>> deserialize(NamespacedType type, Node node) {
        return node.deserializeWithType(type.type()).map((EmptyDestroyer deserializer) -> deserializer
                .nodeList("segments", (Node node1) -> Segment.deserialize(node1))
                .complete((List<Segment> segments1) -> new Namespaced(type, segments1)));
    }

    static Option<CompileResult<JavaRootSegment>> deserializeNamespaces(Node node) {
        return Iters.fromValues(NamespacedType.values())
                .map((NamespacedType type) -> deserialize(type, node))
                .flatMap((Option<CompileResult<JavaRootSegment>> option) -> Iters.fromOption(option))
                .next();
    }
}
