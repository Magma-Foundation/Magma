package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.Deserializers;

public class Symbols {
    public static Option<CompileResult<Symbol>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "symbol").map(deserializer -> deserializer.withString("value")
                .complete(Symbol::new)
                .mapValue(type -> type));
    }

    public static Rule createSymbolRule(String key) {
        return new StripRule(FilterRule.Symbol(new StringRule(key)));
    }

    public static Rule createSymbolRule() {
        return new TypeRule("symbol", Symbols.createSymbolRule("value"));
    }
}
