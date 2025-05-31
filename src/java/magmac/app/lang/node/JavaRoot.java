package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public record JavaRoot(List<JavaRootSegment> children) {
    public static CompileResult<JavaRoot> getChildren(Node node, Deserializer<JavaRootSegment> deserializer) {
        return Deserializers.destruct(node)
                .withNodeList("children", deserializer)
                .complete(JavaRoot::new);
    }
}
