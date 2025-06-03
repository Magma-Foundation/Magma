package magmac.app.lang.web;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.node.ReturnNode;

public final class TypescriptReturnNode extends ReturnNode<TypescriptValue> implements magmac.app.lang.web.TypescriptFunctionSegmentValue, magmac.app.lang.node.TypescriptFunctionSegment {
    public TypescriptReturnNode(TypescriptValue child) {
        super(child);
    }

    @Override
    public Node serialize() {
        return new MapNode("return").withNodeSerialized("child", this.child);
    }
}
