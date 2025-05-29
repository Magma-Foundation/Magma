package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record NumberNode(String value) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("number").map(deserializer -> {
            return deserializer.withString("value").complete(NumberNode::new);
        });
    }
}
