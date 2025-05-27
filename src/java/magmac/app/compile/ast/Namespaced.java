package magmac.app.compile.ast;

import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;

public final class Namespaced {
    public static Rule createRule(String type, String prefix) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        Rule stripRule = new StripRule(new SuffixRule(new PrefixRule(prefix, childRule), ";"));
        return new TypeRule(type, stripRule);
    }
}
