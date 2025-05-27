package magmac.app.compile.ast;

import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

public final class Namespaced {
    public static Rule createRule(String type, String prefix) {
        Rule childRule = new InfixRule(new StringRule("namespace"), ".", new StringRule("child"));
        Rule stripRule = new StripRule(new SuffixRule(new PrefixRule(prefix, childRule), ";"));
        return new TypeRule(type, stripRule);
    }
}
