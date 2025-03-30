package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.lang.r.SymbolRule;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.DecoratedFolder;
import magma.compile.rule.divide.FoldingDivider;
import magma.compile.rule.divide.StatementFolder;
import magma.compile.rule.divide.ValueFolder;
import magma.compile.rule.locate.FirstLocator;
import magma.compile.rule.locate.LastLocator;
import magma.compile.rule.text.EmptyRule;
import magma.compile.rule.text.InfixRule;
import magma.compile.rule.text.StringRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.tree.NodeRule;
import magma.compile.rule.tree.OrRule;
import magma.compile.rule.tree.TypeRule;

public class CommonLang {
    static InfixRule createContentRule(Rule beforeContent, Rule childRule) {
        Rule children1 = createBlockRule(childRule);
        Rule right = new StripRule(new SuffixRule(children1, "}"));
        return new InfixRule(beforeContent, "{", right, new FirstLocator());
    }

    static Rule createBlockRule(Rule childRule) {
        Rule children = new NodeListRule("children", new FoldingDivider(new DecoratedFolder(new StatementFolder())), childRule);
        return new NodeRule("content", new TypeRule("block", children));
    }

    static TypeRule createSymbolTypeRule() {
        return new TypeRule("symbol-type", createSymbolRule("value"));
    }

    static StripRule createSymbolRule(String propertyKey) {
        return new StripRule(new SymbolRule(new StringRule(propertyKey)));
    }

    static Rule createDefinitionRule(Rule type) {
        NodeListRule modifiers = new NodeListRule("modifiers", new CharDivider(' '), new StringRule("modifier"));
        NodeRule typeProperty = new NodeRule("type", type);
        Rule beforeName = new OrRule(Lists.of(
                new InfixRule(modifiers, " ", typeProperty, new TypeSeparatorLocator()),
                typeProperty
        ));

        return new StripRule(new InfixRule(beforeName, " ", createSymbolRule("name"), new LastLocator()));
    }

    static NodeListRule createParamsRule(Rule definition) {
        return new NodeListRule("params", new FoldingDivider(new ValueFolder()), new OrRule(Lists.of(
                createWhitespaceRule(),
                definition
        )));
    }

    static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new EmptyRule()));
    }
}