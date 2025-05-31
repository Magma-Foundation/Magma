package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;

public final class JavaConstructor implements MethodHeader {
    public static Option<CompileResult<MethodHeader>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "constructor").map(constructor -> constructor.complete(JavaConstructor::new));
    }

    @Override
    public Node serialize() {
        return new MapNode("constructor");
    }
}
