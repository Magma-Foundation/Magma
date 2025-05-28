package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public class Whitespace implements Argument, FunctionSegment {
    public static Option<CompileResult<Whitespace>> deserialize(Node node) {
        return node.deserializeWithType("whitespace").map(deserializer -> {
            return deserializer.complete(Whitespace::new);
        });
    }
}
