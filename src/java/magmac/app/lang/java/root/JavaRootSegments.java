package magmac.app.lang.java.root;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Whitespace;
import magmac.app.lang.java.structure.StructureNode;
import magmac.app.lang.java.structure.StructureType;

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
