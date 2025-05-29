package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

final class JavaRootSegments {
    public static CompileResult<JavaRootSegment> deserialize(Node node) {
        return Deserializers.orError("root-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                Namespaced::deserialize,
                node1 -> StructureNode.deserialize(StructureType.Class, node1),
                node1 -> StructureNode.deserialize(StructureType.Interface, node1),
                node1 -> StructureNode.deserialize(StructureType.Record, node1),
                node1 -> StructureNode.deserialize(StructureType.Enum, node1)
        ));
    }

    public static Node serialize(JavaRootSegment segment) {
        return new MapNode();
    }
}
