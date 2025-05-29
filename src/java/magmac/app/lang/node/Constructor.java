package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

class Constructor implements MethodHeader {
    public static Option<CompileResult<MethodHeader>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "constructor").map(constructor -> constructor.complete(Constructor::new));
    }
}
