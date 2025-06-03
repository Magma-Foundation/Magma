package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaFunctionSegmentValue;
import magmac.app.lang.java.Lang;

class Continue implements JavaFunctionSegmentValue, Lang.TypescriptFunctionSegmentValue {
    public static Option<CompileResult<Continue>> deserialize(Node node) {
        return Destructors.destructWithType("continue", node).map(deserializer -> deserializer.complete(Continue::new));
    }

    @Override
    public Node serialize() {
        return new MapNode("continue");
    }
}
