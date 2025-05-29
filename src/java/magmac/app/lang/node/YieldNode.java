package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

record YieldNode(Value value) implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "yield").map(deserializer -> deserializer.withNode("yield", Values::deserializeOrError).complete(YieldNode::new));
    }

    public static Rule createYieldRule(Rule value) {
        return new TypeRule("yield", new StripRule(new PrefixRule("yield ", new NodeRule("value", value))));
    }
}
