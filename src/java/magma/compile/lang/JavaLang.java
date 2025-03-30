package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.lang.r.SymbolRule;
import magma.compile.rule.DivideRule;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.InfixRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.PrefixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.StringRule;
import magma.compile.rule.StripRule;
import magma.compile.rule.SuffixRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

public class JavaLang {
    public static DivideRule createJavaRootRule() {
        return new DivideRule("children", new StatementDivider(), createJavaRootSegmentRule());
    }

    private static OrRule createJavaRootSegmentRule() {
        return new OrRule(Lists.of(
                createImportRule("package ", "package"),
                createImportRule("import ", "import"),
                createClassRule(),
                createInterfaceRule(),
                createRecordRule()
        ));
    }

    private static TypeRule createRecordRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        return new TypeRule("record", new InfixRule(new StringRule("modifiers"), "record ", new InfixRule(namedWithTypeParams, "(", new StringRule("with-end"))));
    }

    private static TypeRule createInterfaceRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "extends ", new StringRule("supertype")),
                namedWithTypeParams
        ));

        return new TypeRule("interface", new InfixRule(new StringRule("modifiers"), "interface ", CommonLang.withContent(beforeContent, createClassMemberRule())));
    }

    private static TypeRule createClassRule() {
        Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "implements ", new StringRule("supertype")),
                namedWithTypeParams
        ));

        Rule right1 = CommonLang.withContent(beforeContent, createClassMemberRule());
        return new TypeRule("class", new InfixRule(new StringRule("modifiers"), "class ", right1));
    }

    private static Rule createClassMemberRule() {
        return new OrRule(Lists.of(
                new TypeRule("whitespace", new StripRule(new EmptyRule())),
                new TypeRule("method", new InfixRule(createDefinitionRule(), "(", new StringRule("with-params"))),
                new TypeRule("definition", new SuffixRule(createDefinitionRule(), ";"))
        ));
    }

    private static StringRule createDefinitionRule() {
        return new StringRule("definition");
    }

    private static Rule createNamedWithTypeParams() {
        Rule name = new StripRule(new SymbolRule(new StringRule("name")));
        return new OrRule(Lists.of(
                new StripRule(new InfixRule(name, "<", new SuffixRule(new StringRule("type-params"), ">"))),
                name
        ));
    }

    private static Rule createImportRule(String prefix, String type) {
        DivideRule namespace = new DivideRule("namespace", new CharDivider('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new PrefixRule(prefix, new SuffixRule(namespace, ";"))));
    }
}
