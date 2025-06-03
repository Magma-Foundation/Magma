package magmac.app.lang.web;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class TypescriptContinue implements TypescriptFunctionSegmentValue {
    @Override
    public Node serialize() {
        return new MapNode("continue");
    }
}
