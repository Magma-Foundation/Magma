package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.lang.r.SymbolRule;
import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.text.EmptyRule;
import magma.compile.rule.locate.FirstLocator;
import magma.compile.rule.text.InfixRule;
import magma.compile.rule.locate.LastLocator;
import magma.compile.rule.tree.NodeRule;
import magma.compile.rule.tree.OrRule;
import magma.compile.rule.text.PrefixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.text.StringRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.tree.TypeRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

public class JavaLang {
    public static NodeListRule createJavaRootRule() {
        return new NodeListRule("children", new StatementDivider(), createJavaRootSegmentRule());
    }

    private static OrRule createJavaRootSegmentRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createImportRule("package ", "package"),
                createImportRule("import ", "import"),
                createClassRule(),
                createInterfaceRule(),
                createRecordRule()
        ));
    }

    private static TypeRule createRecordRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        return new TypeRule("record", new InfixRule(new StringRule("modifiers"), "record ", new InfixRule(namedWithTypeParams, "(", new StringRule("with-end"), new FirstLocator()), new FirstLocator()));
    }

    private static TypeRule createInterfaceRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "extends ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        return new TypeRule("interface", new InfixRule(new StringRule("modifiers"), "interface ", CommonLang.withContent(beforeContent, createClassMemberRule()), new FirstLocator()));
    }

    private static TypeRule createClassRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "implements ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        Rule right1 = CommonLang.withContent(beforeContent, createClassMemberRule());
        return new TypeRule("class", new InfixRule(new StringRule("modifiers"), "class ", right1, new FirstLocator()));
    }

    private static Rule createClassMemberRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                new TypeRule("method", new InfixRule(new NodeRule("definition", createDefinitionRule()), "(", new StringRule("with-params"), new FirstLocator())),
                new TypeRule("definition", new SuffixRule(createDefinitionRule(), ";"))
        ));
    }

    private static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new EmptyRule()));
    }

    private static Rule createDefinitionRule() {
        return new StripRule(new InfixRule(new StringRule("before-name"), " ", new StringRule("name"), new LastLocator()));
    }

    private static Rule createNamedWithTypeParams() {
        Rule name = new StripRule(new SymbolRule(new StringRule("name")));
        return new OrRule(Lists.of(
                new StripRule(new InfixRule(name, "<", new SuffixRule(new StringRule("type-params"), ">"), new FirstLocator())),
                name
        ));
    }

    private static Rule createImportRule(String prefix, String type) {
        NodeListRule namespace = new NodeListRule("namespace", new CharDivider('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new PrefixRule(prefix, new SuffixRule(namespace, ";"))));
    }
}
