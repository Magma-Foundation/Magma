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
import magmac.app.lang.JavaNodes;

record YieldNode(Value value) implements JavaNodes.FunctionSegmentValue {
    public static Option<CompileResult<JavaNodes.FunctionSegmentValue>> deserialize(Node node) {
        return Destructors.destructWithType("yield", node)
                .map(deserializer -> deserializer.withNode("value", Values::deserializeOrError)
                        .complete(YieldNode::new));
    }

    public static Rule createYieldRule(Rule value) {
        return new TypeRule("yield", new StripRule(new PrefixRule("yield ", new NodeRule("value", value))));
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
