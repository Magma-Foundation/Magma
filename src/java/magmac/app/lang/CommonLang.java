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
import magmac.app.compile.rule.fold.StatementFolder;

final class CommonLang {
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
                CommonLang.createWhitespaceRule(),
                definition
        )));
    }

    public static DivideRule Statements(String key, Rule childRule) {
        return new DivideRule(key, new StatementFolder(), childRule);
    }
}
