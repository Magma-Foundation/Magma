package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.Node;
import magma.compile.rule.LazyRule;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.DecoratedFolder;
import magma.compile.rule.divide.FoldingDivider;
import magma.compile.rule.divide.StatementFolder;
import magma.compile.rule.divide.ValueFolder;
import magma.compile.rule.locate.FirstLocator;
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
        return new NodeListRule("children", new FoldingDivider(new DecoratedFolder(new StatementFolder())), createJavaRootSegmentRule());
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
        Rule stripRule = new StripRule(new SuffixRule(createParamsRule(), ")"));
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(stripRule, "implements ", new NodeRule("supertype", createTypeRule()), new FirstLocator()),
                stripRule
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());
        Rule right = new InfixRule(namedWithTypeParams, "(", right1, new FirstLocator());
        return new TypeRule("record", new InfixRule(new StringRule("modifiers"), "record ", right, new FirstLocator()));
    }

    private static TypeRule createInterfaceRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "extends ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        InfixRule contentRule = createContentRule(beforeContent, createClassMemberRule());
        return new TypeRule("interface", new InfixRule(new StringRule("modifiers"), "interface ", contentRule, new FirstLocator()));
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
                createDefinitionStatementRule(),
                createInitializationRule()
        ));
    }

    private static TypeRule createInitializationRule() {
        Rule definitionRule = createDefinitionRule(createTypeRule());
        TypeRule definition = new TypeRule("definition", definitionRule);
        return new TypeRule("initialization", new SuffixRule(new InfixRule(new NodeRule("definition", definition), "=", new StringRule("value"), new FirstLocator()), ";"));
    }

    private static TypeRule createDefinitionStatementRule() {
        return new TypeRule("definition", new SuffixRule(createDefinitionRule(createTypeRule()), ";"));
    }

    private static Rule createMethodRule() {
        Rule definition = new NodeRule("definition", createDefinitionRule(createTypeRule()));
        Rule params = createParamsRule();

        Rule withParams = new OrRule(Lists.of(
                createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule()),
                new SuffixRule(params, ");")
        ));

        return new TypeRule("method", new InfixRule(definition, "(", withParams, new FirstLocator()));
    }

    private static NodeListRule createParamsRule() {
        return new NodeListRule("params", new FoldingDivider(new ValueFolder()), new OrRule(Lists.of(
                createWhitespaceRule(),
                createDefinitionRule(createTypeRule())
        )));
    }

    private static Rule createStatementRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createReturnRule(),
                createIfRule(),
                createInvocationRule(),
                createForRule(),
                createAssignmentRule(),
                createPostfixRule(),
                createElseRule(),
                new TypeRule("while", new StripRule(new PrefixRule("while", new StringRule("content"))))
        ));
    }

    private static TypeRule createElseRule() {
        return new TypeRule("else", new StripRule(new PrefixRule("else", new StringRule("content"))));
    }

    private static TypeRule createPostfixRule() {
        return new TypeRule("postfix", new SuffixRule(new StringRule("value"), "++;"));
    }

    private static TypeRule createAssignmentRule() {
        return new TypeRule("assignment", new SuffixRule(new InfixRule(new StringRule("destination"), "=", new StringRule("source"), new FirstLocator()), ";"));
    }

    private static TypeRule createForRule() {
        return new TypeRule("for", new StripRule(new PrefixRule("for ", new StringRule("content"))));
    }

    private static TypeRule createInvocationRule() {
        return new TypeRule("invocation", new SuffixRule(new StringRule("content"), ");"));
    }

    private static TypeRule createIfRule() {
        return new TypeRule("if", new StripRule(new PrefixRule("if", new StringRule("content"))));
    }

    private static TypeRule createReturnRule() {
        PrefixRule rule = new PrefixRule("return ", new SuffixRule(new StringRule("value"), ";"));
        return new TypeRule("return", new StripRule(rule));
    }

    private static TypeRule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new EmptyRule()));
    }

    private static Rule createTypeRule() {
        LazyRule type = new LazyRule();
        type.set(new OrRule(Lists.of(
                createArrayRule(type),
                createGenericRule(type),
                createSymbolTypeRule()
        )));
        return type;
    }

    private static TypeRule createArrayRule(LazyRule type) {
        return new TypeRule("array", new SuffixRule(new NodeRule("child", type), "[]"));
    }

    private static Rule createGenericRule(Rule type) {
        Rule typeArguments = new NodeListRule("arguments", new FoldingDivider(new ValueFolder()), type);
        Rule base = createMaybeQualifiedNameRule();

        return new TypeRule("generic", new StripRule(new SuffixRule(new InfixRule(new NodeRule("base", base), "<", typeArguments, new FirstLocator()), ">")));
    }

    private static Rule createMaybeQualifiedNameRule() {
        return new NodeListRule("segments", new CharDivider('.'), createSymbolRule("value"));
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
