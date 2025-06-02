package magmac.app.lang;

import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.fold.StatementFolder;

public final class CommonLang {
    public static Rule Statements(String key, Rule childRule) {
        return NodeListRule.createNodeListRule(key, new StatementFolder(), childRule);
    }
}
