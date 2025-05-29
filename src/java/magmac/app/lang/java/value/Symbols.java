package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;

public class Symbols {
    public static NodeListRule createSegmentsRule(String key) {
        return new NodeListRule(key, new DelimitedFolder('.'), Symbols.createSymbolRule("value"));
    }

    public static TypeRule createQualifiedRule() {
        return new TypeRule("qualified", Symbols.createSegmentsRule("segments"));
    }

    public static Option<CompileResult<Symbol>> deserializeSymbol(Node node) {
        return node.deserializeWithType("symbol").map(deserializer -> deserializer.withString("value")
                .complete(Symbol::new)
                .mapValue(type -> type));
    }

    public static Rule createSymbolRule(String name) {
        return new StripRule(FilterRule.Symbol(new StringRule(name)));
    }

    public static Rule createSymbolValueRule() {
        return new TypeRule("symbol", Symbols.createSymbolRule("value"));
    }

    public static Rule createSymbolTypeRule() {
        return new TypeRule("symbol", Symbols.createSymbolRule("value"));
    }

}
