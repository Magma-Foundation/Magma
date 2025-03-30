package magma.compile.lang;

import magma.compile.rule.DivideRule;
import magma.compile.rule.InfixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.StripRule;
import magma.compile.rule.SuffixRule;
import magma.compile.rule.divide.StatementDivider;

public class CommonLang {
    static InfixRule withContent(Rule beforeContent, Rule childRule) {
        Rule children = new DivideRule("children", new StatementDivider(), childRule);
        Rule right = new StripRule(new SuffixRule(children, "}"));
        return new InfixRule(beforeContent, "{", right);
    }
}