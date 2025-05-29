package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public class Continue implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "continue").map(deserializer -> deserializer.complete(Continue::new));
    }
}
