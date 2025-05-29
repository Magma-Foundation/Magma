package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;

public class Constructor implements MethodHeader {
    public static Option<CompileResult<MethodHeader>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "constructor").map(constructor -> {
            return constructor.complete(() -> new Constructor());
        });
    }
}
