package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.DivideFolder;
import magma.compile.rule.divide.FoldingDivider;
import magma.compile.rule.divide.ValueFolder;
import magma.compile.rule.locate.FirstLocator;
import magma.compile.rule.locate.LastLocator;
import magma.compile.rule.text.EmptyRule;
import magma.compile.rule.text.InfixRule;
import magma.compile.rule.text.PrefixRule;
import magma.compile.rule.text.StringRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.SuffixRule;
import magma.compile.rule.tree.NodeListRule;
import magma.compile.rule.tree.NodeRule;
import magma.compile.rule.tree.OrRule;
import magma.compile.rule.tree.TypeRule;

import static magma.compile.lang.CommonLang.*;

public class JavaLang {
    public static NodeListRule createJavaRootRule() {
        return new NodeListRule("children", new FoldingDivider(new DivideFolder()), createJavaRootSegmentRule());
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

        return new TypeRule("interface", new InfixRule(new StringRule("modifiers"), "interface ", createContentRule(beforeContent, createClassMemberRule()), new FirstLocator()));
    }

    private static TypeRule createClassRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "implements ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());
        return new TypeRule("class", new InfixRule(new StringRule("modifiers"), "class ", right1, new FirstLocator()));
    }

    private static Rule createClassMemberRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createMethodRule(),
                new TypeRule("definition", new SuffixRule(createDefinitionRule(), ";"))
        ));
    }

    private static Rule createMethodRule() {
        Rule definition = new NodeRule("definition", createDefinitionRule());
        Rule params = new NodeListRule("params", new FoldingDivider(new ValueFolder()), new OrRule(Lists.of(
                createWhitespaceRule(),
                createDefinitionRule()
        )));

        Rule withParams = new OrRule(Lists.of(
                createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule()),
                new SuffixRule(params, ");")
        ));

        return new TypeRule("method", new InfixRule(definition, "(", withParams, new FirstLocator()));
    }

    private static Rule createStatementRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createReturnRule(),
                new TypeRule("if", new PrefixRule("if ", new StringRule("content")))
        ));
    }

    private static TypeRule createReturnRule() {
        PrefixRule rule = new PrefixRule("return ", new SuffixRule(new StringRule("value"), ";"));
        return new TypeRule("return", new StripRule(rule));
    }

    private static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new EmptyRule()));
    }

    private static Rule createDefinitionRule() {
        NodeListRule modifiers = new NodeListRule("modifiers", new CharDivider(' '), new StringRule("modifier"));
        NodeRule type = new NodeRule("type", createTypeRule());
        Rule beforeName = new OrRule(Lists.of(
                new InfixRule(modifiers, " ", type, new TypeSeparatorLocator()),
                type
        ));

        return new StripRule(new InfixRule(beforeName, " ", createSymbolRule("name"), new LastLocator()));
    }

    private static Rule createTypeRule() {
        return new OrRule(Lists.of(
                createGenericRule(),
                createSymbolTypeRule()
        ));
    }

    private static Rule createGenericRule() {
        Rule typeArguments = new NodeListRule("type-arguments", new FoldingDivider(new ValueFolder()), createSymbolRule("value"));
        return new TypeRule("generic", new StripRule(new SuffixRule(new InfixRule(createSymbolRule("base"), "<", typeArguments, new LastLocator()), ">")));
    }

    private static Rule createNamedWithTypeParams() {
        Rule name = createSymbolRule("name");
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
