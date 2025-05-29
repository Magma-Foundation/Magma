package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

public record ArrayType(Type type) implements Type {
    public static Rule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    public static Option<CompileResult<Type>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "array").map(deserializer -> {
            return deserializer.withNode("child", Types::deserialize)
                    .complete(ArrayType::new);
        });
    }
}
