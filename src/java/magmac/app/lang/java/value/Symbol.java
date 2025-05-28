package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Type;

public record Symbol(String value) implements Type, Value {
    public static Option<CompileResult<Symbol>> deserialize(Node node) {
        return node.deserializeWithType("symbol").map(deserializer -> deserializer.withString("value")
                .complete(Symbol::new)
                .mapValue(type -> type));
    }

    public static Rule createSymbolRule(String name) {
        return new StripRule(FilterRule.Symbol(new StringRule(name)));
    }

    public static Rule createSymbolValueRule() {
        return new TypeRule("symbol", Symbol.createSymbolRule("value"));
    }

    public static Rule createSymbolTypeRule() {
        return new TypeRule("symbol", createSymbolRule("value"));
    }
}
