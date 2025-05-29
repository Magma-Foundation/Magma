package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Constructor implements Header {
    public static Option<CompileResult<Header>> deserialize(Node node) {
        return node.deserializeWithType("constructor").map(constructor -> {
            return constructor.complete(() -> new Constructor());
        });
    }
}
