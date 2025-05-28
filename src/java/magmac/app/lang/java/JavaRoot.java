package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

public record JavaRoot(List<JavaRootSegment> children) {
    public static CompileResult<JavaRoot> deserialize(Node node) {
        return node.destroy()
                .nodeList("children", JavaRootSegments::deserialize)
                .complete(segments -> new JavaRoot(segments));
    }

    public Node serialize() {
        return new MapNode().withNodeListFromElements(this.children, JavaRootSegments::serialize);
    }
}
