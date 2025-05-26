package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.RuleResult;

public interface Rule {
    RuleResult<Node> lex(String input);
}
