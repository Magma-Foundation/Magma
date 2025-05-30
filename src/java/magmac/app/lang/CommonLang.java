package magmac.app.lang;

import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;

public final class CommonLang {
    public static Rule Statements(String key, Rule childRule) {
        return NodeListRule.createNodeListRule(key, new StatementFolder(), childRule);
    }

    public static Rule createIndexRule(LazyRule value) {
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(new NodeRule("parent", value), "[", new NodeRule("argument", value)), "]")));
    }
}
