package magmac.app.lang.node;

import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.LazyRule;

record Operation(Value left, Operator operator, Value right) implements Value {
    static Rule createOperationRule(Operator operator, LazyRule value) {
        return new TypeRule(operator.type(), LocatingRule.First(new NodeRule("left", value), operator.text(), new NodeRule("right", value)));
    }

    @Override
    public Node serialize() {
        return new MapNode(this.operator.type());
    }
}
