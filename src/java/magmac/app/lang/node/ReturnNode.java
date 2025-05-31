package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;

record ReturnNode(Value child) implements FunctionSegmentValue, FunctionSegment {
    public static Option<CompileResult<ReturnNode>> deserialize(Node node) {
        return Destructors.destructWithType("return", node)
                .map(deserializer -> deserializer.withNode("child", Values::deserializeOrError).complete(ReturnNode::new));
    }

    public static Rule createReturnRule(Rule value) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("child", value))));
    }

    @Override
    public Node serialize() {
        return new MapNode("return").withNodeSerialized("child", this.child);
    }
}
