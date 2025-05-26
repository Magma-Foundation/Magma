package magmac.app.rule;

import magmac.app.rule.result.RuleResult;

public interface Rule {
    RuleResult lex(String input);
}
