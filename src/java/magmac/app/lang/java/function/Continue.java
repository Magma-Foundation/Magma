package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Continue implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return node.deserializeWithType("continue").map(deserializer -> deserializer.complete(Continue::new));
    }
}
