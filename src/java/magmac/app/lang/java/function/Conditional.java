package magmac.app.lang.java.function;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.block.BlockHeader;
import magmac.app.lang.java.value.Value;
import magmac.app.lang.java.value.Values;

public record Conditional(ConditionalType type, Value condition) implements BlockHeader {
    public static Option<CompileResult<Conditional>> deserialize(ConditionalType type, Node node) {
        return Deserializers.deserializeWithType(node, type.name().toLowerCase()).map(deserializer -> {
            return deserializer.withNode("condition", Values::deserializeOrError)
                    .complete(value -> new Conditional(type, value));
        });
    }

    public static Rule createConditionalRule(String type, Rule value) {
        Rule condition = new NodeRule("condition", value);
        Rule childRule = new StripRule(new PrefixRule("(", new SuffixRule(condition, ")")));
        return new TypeRule(type, new StripRule(new PrefixRule(type, childRule)));
    }
}
