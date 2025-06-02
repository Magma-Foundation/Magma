package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record BlockContent(List<FunctionSegment> children) implements LambdaContent {
    public static Option<CompileResult<LambdaContent>> deserialize(Node node) {
        return Destructors.destructWithType("block", node).map(destructor -> {
            return destructor.withNodeList("children", FunctionSegments::deserialize)
                    .complete(BlockContent::new);
        });
    }

    @Override
    public Node serialize() {
        return new MapNode("block").withNodeListSerialized("children", this.children);
    }
}
