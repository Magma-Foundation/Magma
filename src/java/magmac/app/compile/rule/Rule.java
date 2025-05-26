package magmac.app.compile.rule;

import magmac.app.compile.rule.result.RuleResult;

public interface Rule {
    RuleResult lex(String input);
}
