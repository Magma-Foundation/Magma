package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;

public record NumberNode(String value) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("number").map(deserializer -> {
            return deserializer.withString("value").complete(NumberNode::new);
        });
    }

    public static Rule createNumberRule() {
        return new TypeRule("number", new StripRule(FilterRule.Number(new StringRule("value"))));
    }
}
