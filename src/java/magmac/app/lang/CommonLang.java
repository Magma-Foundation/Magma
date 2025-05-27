package magmac.app.lang;

import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;

public class CommonLang {
    static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }
}
