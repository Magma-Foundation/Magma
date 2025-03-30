package magma.compile.lang;

import magma.compile.lang.r.SymbolRule;
import magma.compile.rule.divide.DivideFolder;
import magma.compile.rule.text.StringRule;
import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.locate.FirstLocator;
import magma.compile.rule.text.InfixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.divide.FoldingDivider;
import magma.compile.rule.tree.TypeRule;

public class CommonLang {
    static InfixRule withContent(Rule beforeContent, Rule childRule) {
        Rule children = new NodeListRule("children", new FoldingDivider(new DivideFolder()), childRule);
        Rule right = new StripRule(new SuffixRule(children, "}"));
        return new InfixRule(beforeContent, "{", right, new FirstLocator());
    }

    static TypeRule createSymbolTypeRule() {
        return new TypeRule("symbol-type", createSymbolRule("value"));
    }

    static StripRule createSymbolRule(String propertyKey) {
        return new StripRule(new SymbolRule(new StringRule(propertyKey)));
    }
}