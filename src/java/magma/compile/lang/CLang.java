package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.LazyRule;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.CharDivider;
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
                createFunctionRule()
        ));
    }

    private static TypeRule createFunctionRule() {
        Rule definitionRule = CommonLang.createDefinitionRule(createTypeRule());
        NodeRule definition = new NodeRule("definition", definitionRule);
        NodeListRule params = CommonLang.createParamsRule(definitionRule);

        Rule block = CommonLang.createContentRule(new StripRule(new SuffixRule(params, "}")), createStatementRule());
        Rule block1 = new OrRule(Lists.of(
                block,
                new SuffixRule(params, ");\n")
        ));

        return new TypeRule("function", new InfixRule(definition, "(", block1, new FirstLocator()));
    }

    private static Rule createStatementRule() {
        return new OrRule(Lists.empty(

        ));
    }

    private static TypeRule createStructRule() {
        StringRule name = new StringRule("name");
        InfixRule contentRule = CommonLang.createContentRule(name, createStructMemberRule());
        return new TypeRule("struct", new PrefixRule("struct ", new SuffixRule(contentRule, ";\n")));
    }

    private static OrRule createStructMemberRule() {
        return new OrRule(Lists.of(
                new TypeRule("definition", CommonLang.createDefinitionRule(createTypeRule())),
                new TypeRule("functional-definition", new NodeRule("returns", createTypeRule()))
        ));
    }

    private static Rule createTypeRule() {
        LazyRule type = new LazyRule();
        type.set(new OrRule(Lists.of(
                new TypeRule("struct-type", new PrefixRule("struct ", new StringRule("value"))),
                new TypeRule("ref", new SuffixRule(new NodeRule("child", type), "*")),
                CommonLang.createSymbolTypeRule()
        )));
        return type;
    }

    private static Rule createIncludeRule() {
        NodeListRule path = new NodeListRule("path", new CharDivider('/'), new StringRule("value"));
        return new TypeRule("include", new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
