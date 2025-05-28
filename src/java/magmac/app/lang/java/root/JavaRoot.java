package magmac.app.lang.java.root;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Serializable;

public record JavaRoot(List<JavaRootSegment> children) implements Serializable {
    public static CompileResult<JavaRoot> deserialize(Node node) {
        return node.deserialize()
                .withNodeList("children", (Node node1) -> JavaRootSegments.deserialize(node1))
                .complete((List<JavaRootSegment> segments) -> new JavaRoot(segments));
    }

    @Override
    public Node serialize() {
        return new MapNode().withNodeListFromElements("children", this.children, (JavaRootSegment segment) -> JavaRootSegments.serialize(segment));
    }
}
