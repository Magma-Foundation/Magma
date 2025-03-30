package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OptionalNodeListRule;
import magma.compile.rule.OptionalNodeRule;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.FoldingDivider;
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

public class CLang {
    public static Rule createCRootRule() {
        return new TypeRule("root", CommonLang.createBlockRule(createCRootSegmentRule()));
    }

    private static Rule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule(),
                new TypeRule("ifndef", new PrefixRule("#ifndef ", new SuffixRule(new StringRule("value"), "\n"))),
                new TypeRule("define", new PrefixRule("#define ", new SuffixRule(new StringRule("value"), "\n"))),
                new TypeRule("endif", new PrefixRule("#endif\n", new EmptyRule())),
                createStructRule(),
                createFunctionRule(),
                createExpansionRule()
        ));
    }

    private static TypeRule createExpansionRule() {
        StringRule name = new StringRule("name");
        InfixRule childRule = new InfixRule(name, " = ", new NodeRule("type", createTypeRule()), new FirstLocator());
        return new TypeRule("expansion", new PrefixRule("// expand ", new SuffixRule(childRule, "\n")));
    }

    private static TypeRule createFunctionRule() {
        OrRule definitionRule = createDefinitionsRule();
        NodeRule definition = new NodeRule("definition", definitionRule);
        NodeListRule params = CommonLang.createParamsRule(definitionRule);

        Rule block = CommonLang.createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule());
        Rule block1 = new OptionalNodeRule("content",
                block,
                new SuffixRule(params, ");\n")
        );

        return new TypeRule("function", new InfixRule(definition, "(", block1, new FirstLocator()));
    }

    private static Rule createStatementRule() {
        return new OrRule(Lists.of(
                createWhitespaceRule(),
                createReturnRule(new OrRule(Lists.of())),
                createIfRule(),
                createInvocationRule(),
                createForRule(),
                createAssignmentRule(),
                createPostfixRule(),
                createElseRule(),
                createWhileRule()
        ));
    }

    private static Rule createStructRule() {
        Rule name = CommonLang.createNamedWithTypeParams();
        Rule contentRule = CommonLang.createContentRule(name, createStructMemberRule());
        return new TypeRule("struct", new PrefixRule("struct ", new SuffixRule(contentRule, ";\n")));
    }

    private static OrRule createStructMemberRule() {
        return createDefinitionsRule();
    }

    private static OrRule createDefinitionsRule() {
        return new OrRule(Lists.of(
                new TypeRule("definition", CommonLang.createDefinitionRule(createTypeRule())),
                createFunctionalDefinitionType()
        ));
    }

    private static Rule createFunctionalDefinitionType() {
        Rule returns = new NodeRule("return", createTypeRule());
        Rule params = new NodeListRule("params", new FoldingDivider(new ValueFolder()), createTypeRule());
        Rule maybeParams = new OptionalNodeListRule("params", params, new EmptyRule());
        Rule right = new InfixRule(new PrefixRule("*", new StringRule("name")), ")(", new SuffixRule(maybeParams, ")"), new FirstLocator());
        return new TypeRule("functional-definition", new InfixRule(returns, "(", right, new FirstLocator()));
    }

    private static Rule createTypeRule() {
        LazyRule type = new LazyRule();
        type.set(new OrRule(Lists.of(
                new TypeRule("struct-type", new PrefixRule("struct ", new StringRule("value"))),
                new TypeRule("ref", new SuffixRule(new NodeRule("child", type), "*")),
                CommonLang.createSymbolTypeRule(),
                CommonLang.createGenericRule(type)
        )));
        return type;
    }

    private static Rule createIncludeRule() {
        NodeListRule path = new NodeListRule("path", new CharDivider('/'), new StringRule("value"));
        return new TypeRule("include", new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
