package magmac.app.lang.web;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.node.ReturnNode;

public final class TypescriptReturnNode extends ReturnNode<TypescriptLang.TypescriptValue> implements magmac.app.lang.web.TypescriptFunctionSegmentValue, TypescriptLang.TypescriptFunctionSegment {
    public TypescriptReturnNode(TypescriptLang.TypescriptValue child) {
        super(child);
    }

    @Override
    public Node serialize() {
        return new MapNode("return").withNodeSerialized("child", this.child);
    }
}
