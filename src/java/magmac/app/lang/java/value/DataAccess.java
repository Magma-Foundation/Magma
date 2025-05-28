package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.LazyRule;
import magmac.app.lang.java.Symbol;

public record DataAccess(String property, Value caller) implements Value {
    public static Option<CompileResult<Value>> deserialize(Node node) {
        return node.deserializeWithType("data-access").map(deserializer -> {
            return deserializer.withString("property")
                    .node("instance", Values::deserializeError)
                    .complete(tuple -> new DataAccess(tuple.left(), tuple.right()));
        });
    }

    public static Rule createAccessRule(String infix, LazyRule value, String type) {
        Rule property = Symbol.createSymbolRule("property");
        return new TypeRule(type, LocatingRule.Last(new NodeRule("instance", value), infix, property));
    }
}
