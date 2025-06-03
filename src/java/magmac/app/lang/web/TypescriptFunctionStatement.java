package magmac.app.lang.web;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.common.FunctionStatement;

public final class TypescriptFunctionStatement extends FunctionStatement<TypescriptFunctionSegmentValue> implements TypescriptLang.TypescriptFunctionSegment {
    public TypescriptFunctionStatement(TypescriptFunctionSegmentValue child) {
        super(child);
    }

    @Override
    public Node serialize() {
        return new MapNode("statement").withNodeSerialized("child", this.child);
    }
}
