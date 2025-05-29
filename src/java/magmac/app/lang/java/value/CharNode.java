package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record CharNode(String value) implements Value {
    public static Option<CompileResult<CharNode>> deserialize(Node node) {
        return node.deserializeWithType("char").map(deserializer -> {
            return deserializer.withString("value").complete(CharNode::new);
        });
    }
}
