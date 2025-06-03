package magmac.app.lang.web;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.common.AbstractFunctionStatement;

public final class FunctionStatement extends AbstractFunctionStatement<TypescriptLang.FunctionSegment.Value> implements TypescriptLang.FunctionSegment {
    public FunctionStatement(Value child) {
        super(child);
    }

    @Override
    public Node serialize() {
        return new MapNode("statement").withNodeSerialized("child", this.child);
    }
}
