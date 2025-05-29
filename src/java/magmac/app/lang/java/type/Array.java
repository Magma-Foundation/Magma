package magmac.app.lang.java.type;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Type;

public record Array(Type type) implements Type {
    public static Rule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    public static Option<CompileResult<Type>> deserialize(Node node) {
        return node.deserializeWithType("array").map(deserializer -> {
            return deserializer.withNode("child", Types::deserialize)
                    .complete(Array::new);
        });
    }
}
