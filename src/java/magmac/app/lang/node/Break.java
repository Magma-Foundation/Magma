package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

class Break implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return Destructors.destructWithType("break", node).map(deserializer -> deserializer.complete(Break::new));
    }

    @Override
    public Node serialize() {
        return new MapNode("break");
    }
}
