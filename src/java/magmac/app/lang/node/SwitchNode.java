package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record SwitchNode(JavaValue value, List<JavaFunctionSegment> children) implements JavaValue, TypeScriptValue {
    public static Option<CompileResult<SwitchNode>> deserialize(Node node) {
        return Destructors.destructWithType("switch", node).map(deserializer -> {
            return deserializer
                    .withNode("value", Values::deserializeOrError)
                    .withNodeList("children", FunctionSegments::deserialize)
                    .complete(tuple -> new SwitchNode(tuple.left(), tuple.right()));
        });
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
