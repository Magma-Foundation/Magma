package magmac.app.lang.java.function.segment;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.function.FunctionSegmentValue;
import magmac.app.lang.java.value.Value;
import magmac.app.lang.java.value.Values;

public record Return(Value value) implements FunctionSegmentValue {
    public static Option<CompileResult<FunctionSegmentValue>> deserialize(Node node) {
        return node.deserializeWithType("return").map(deserializer -> deserializer.withNode("value", Values::deserializeError).complete(value -> new Return(value)));
    }

    public static Rule createReturnRule(Rule value) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("value", value))));
    }
}
