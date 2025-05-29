package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.LazyRule;
import magmac.app.lang.Deserializers;

public record Not(Value value) implements Value {
    public static TypeRule createNotRule(LazyRule value) {
        return new TypeRule("not", new StripRule(new PrefixRule("!", new NodeRule("child", value))));
    }

    public static Option<CompileResult<Value>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "not").map(deserializer -> {
            return deserializer.withNode("child", Values::deserializeOrError)
                    .complete(Not::new);
        });
    }
}
