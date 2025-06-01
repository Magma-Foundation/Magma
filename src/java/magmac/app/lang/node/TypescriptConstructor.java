package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public final class TypescriptConstructor implements TypeScriptMethodHeader {
    @Override
    public Node serialize() {
        return new MapNode("constructor");
    }
}
