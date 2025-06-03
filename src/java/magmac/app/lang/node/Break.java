package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaFunctionSegmentValue;
import magmac.app.lang.java.Lang;

class Break implements JavaFunctionSegmentValue, Lang.TypescriptFunctionSegmentValue {
    public static Option<CompileResult<Break>> deserialize(Node node) {
        return Destructors.destructWithType("break", node).map(deserializer -> deserializer.complete(Break::new));
    }

    @Override
    public Node serialize() {
        return new MapNode("break");
    }
}
