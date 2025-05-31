package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public final class Constructor implements JavaMethodHeader, TypeScriptMethodHeader {
    public static Option<CompileResult<JavaMethodHeader>> deserialize(Node node) {
        return Destructors.destructWithType("constructor", node)
                .map(constructor -> constructor.complete(Constructor::new));
    }

    @Override
    public Node serialize() {
        return new MapNode("constructor");
    }
}
