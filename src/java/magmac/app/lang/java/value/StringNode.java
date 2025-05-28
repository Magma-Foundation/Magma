package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record StringNode(String value) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("string").map(deserializer -> deserializer.withString("value").complete(StringNode::new));
    }
}
