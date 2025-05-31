package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public record SwitchNode() implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return Deserializers.deserializeWithType("switch", node).map(deserializer -> {
            return deserializer.complete(SwitchNode::new);
        });
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
