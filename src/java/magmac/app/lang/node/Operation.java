package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;

record Operation(Value left, Operator operator, Value right) implements Value {

    public static Rule createOperationRule(Rule value, String type, String infix) {
        return new TypeRule(type, LocatingRule.First(new NodeRule("left", value), infix, new NodeRule("right", value)));
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
