package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaRootSegment;

public record JavaRoot(List<JavaRootSegment> children) {
    public static CompileResult<JavaRoot> getChildren(Node node, Deserializer<JavaRootSegment> deserializer) {
        return Destructors.destruct(node)
                .withNodeList("children", deserializer)
                .complete(JavaRoot::new);
    }
}
