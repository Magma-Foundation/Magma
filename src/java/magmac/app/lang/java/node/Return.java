package magmac.app.lang.java.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializers;

public record Return(Value value) implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "return").map(deserializer -> deserializer.withNode("value", Values::deserializeOrError).complete(value -> new Return(value)));
    }

    public static Rule createReturnRule(Rule value) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("value", value))));
    }
}
