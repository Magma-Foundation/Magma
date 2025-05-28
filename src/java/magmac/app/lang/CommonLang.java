package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

public final class CommonLang {
    static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }

    static Rule createSymbolTypeRule() {
        return new TypeRule("symbol-type", new StripRule(FilterRule.Symbol(new StringRule("value"))));
    }

    static Rule createTemplateRule() {
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(new StripRule(new StringRule("base")), "<", new StringRule("arguments")), ">")));
    }

    static DivideRule createParametersRule(Rule definition) {
        return new DivideRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                createWhitespaceRule(),
                definition
        )));
    }
}
