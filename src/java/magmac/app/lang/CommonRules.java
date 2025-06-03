package magmac.app.lang;

import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;

public class CommonRules {
    public static Rule createSymbolRule(String key) {
        return new StripRule(FilterRule.Symbol(new StringRule(key)));
    }

    public static Rule createSymbolRule() {
        return new TypeRule("symbol", createSymbolRule("value"));
    }
}
