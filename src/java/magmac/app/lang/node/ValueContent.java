package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record ValueContent(Value value) implements LambdaContent {
    public static Option<CompileResult<LambdaContent>> deserialize(Node node) {
        return Destructors.destructWithType("value", node).map(destructor -> {
            return destructor.withNode("value", Values::deserializeOrError).complete(ValueContent::new);
        });
    }
}
