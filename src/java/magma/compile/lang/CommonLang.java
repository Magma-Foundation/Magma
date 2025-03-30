package magma.compile.lang;

import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.locate.FirstLocator;
import magma.compile.rule.text.InfixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.divide.StatementDivider;

public class CommonLang {
    static InfixRule withContent(Rule beforeContent, Rule childRule) {
        Rule children = new NodeListRule("children", new StatementDivider(), childRule);
        Rule right = new StripRule(new SuffixRule(children, "}"));
        return new InfixRule(beforeContent, "{", right, new FirstLocator());
    }
}