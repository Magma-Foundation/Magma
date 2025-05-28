package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.Divider;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.compile.rule.split.DividingSplitter;

public final class JavaLang {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createNamespacedRule("package"),
                JavaLang.createNamespacedRule("import"),
                JavaLang.createStructureRule("record"),
                JavaLang.createStructureRule("interface"),
                JavaLang.createStructureRule("class"),
                JavaLang.createStructureRule("enum")
        ))));
    }

    private static Rule createStructureRule(String keyword) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule beforeContent = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(name, "<", new StringRule("type-params")), ">")),
                name
        ));

        Rule withParameters = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", new StringRule("parameters")), ")")),
                beforeContent
        ));

        Rule withEnds = new OrRule(Lists.of(
                LocatingRule.First(withParameters, " extends ", new NodeRule("extended", JavaLang.createTypeRule())),
                withParameters
        ));

        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, " implements ", new NodeRule("implemented", JavaLang.createTypeRule()))),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = LocatingRule.First(withImplements, "{", new StripRule(new SuffixRule(CommonLang.Statements("children", JavaLang.createStructureMemberRule()), "}")));
        return new TypeRule(keyword, LocatingRule.First(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }

    private static OrRule createStructureMemberRule() {
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createStructureStatementRule(),
                JavaLang.createMethodRule()
        ));
    }

    private static Rule createStructureStatementRule() {
        Rule definition = new OrRule(Lists.of(
                new NodeRule("value", new TypeRule("definition", JavaLang.createDefinitionRule())),
                JavaLang.createAssignmentRule())
        );

        return new TypeRule("statement", new StripRule(new SuffixRule(definition, ";")));
    }

    private static Rule createAssignmentRule() {
        Rule definition = new OrRule(Lists.of(
                new NodeRule("definition", JavaLang.createDefinitionRule()),
                new NodeRule("destination", JavaLang.createValueRule())
        ));

        Rule value = new NodeRule("source", JavaLang.createValueRule());
        return new TypeRule("assignment", LocatingRule.First(definition, "=", value));
    }

    private static Rule createValueRule() {
        LazyRule value = new LazyRule();
        value.set(new OrRule(Lists.of(
                new StripRule(new PrefixRule("!", new NodeRule("child", value))),
                JavaLang.createCharRule(),
                JavaLang.createStringRule(),
                JavaLang.createInvokableRule(value),
                JavaLang.createNumberRule(),
                JavaLang.createAccessRule(value),
                JavaLang.createSymbolValueRule(),
                JavaLang.createAddRule(value)
        )));
        return value;
    }

    private static TypeRule createAddRule(LazyRule value) {
        return new TypeRule("add", LocatingRule.First(new NodeRule("left", value), "+", new NodeRule("right", value)));
    }

    private static StripRule createSymbolValueRule() {
        return new StripRule(FilterRule.Symbol(new StringRule("value")));
    }

    private static Rule createAccessRule(LazyRule value) {
        StripRule property = new StripRule(FilterRule.Symbol(new StringRule("property")));
        return new TypeRule("access", LocatingRule.Last(new NodeRule("instance", value), ".", property));
    }

    private static Rule createNumberRule() {
        return new TypeRule("number", new StripRule(FilterRule.Number(new StringRule("value"))));
    }

    private static TypeRule createStringRule() {
        return new TypeRule("string", new StripRule(new PrefixRule("\"", new SuffixRule(new StringRule("value"), "\""))));
    }

    private static TypeRule createCharRule() {
        return new TypeRule("char", new StripRule(new PrefixRule("'", new SuffixRule(new StringRule("value"), "'"))));
    }

    private static Rule createInvokableRule(Rule value) {
        Rule caller = new SuffixRule(new OrRule(Lists.of(
                new ContextRule("As construction", new StripRule(new PrefixRule("new ", new NodeRule("type", JavaLang.createTypeRule())))),
                new ContextRule("As invocation", new NodeRule("caller", value))
        )), "(");

        DivideRule arguments = new DivideRule("arguments", new ValueFolder(), new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                value
        )));

        Splitter splitter = DividingSplitter.First(new FoldingDivider(new InvocationFolder()));
        return new TypeRule("invocation", new StripRule(new SuffixRule(new LocatingRule(caller, splitter, arguments), ")")));
    }

    private static Rule createMethodRule() {
        NodeRule header = new NodeRule("header", new OrRule(Lists.of(
                JavaLang.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        Rule parameters = CommonLang.createParametersRule(JavaLang.createDefinitionRule());
        Rule content = CommonLang.Statements("content", JavaLang.createFunctionSegmentRule());
        Rule rightRule = new StripRule(new PrefixRule("{", new SuffixRule(content, "}")));
        Rule withParams = new OrRule(Lists.of(
                new SuffixRule(parameters, ");"),
                LocatingRule.First(parameters, ")", rightRule)
        ));

        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }

    private static Rule createFunctionSegmentRule() {
        LazyRule functionSegmentRule = new LazyRule();
        functionSegmentRule.set(new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                new TypeRule("statement", new StripRule(new SuffixRule(JavaLang.createFunctionSegmentValueRule(), ";"))),
                new StripRule(new SuffixRule(LocatingRule.First(new NodeRule("header", JavaLang.createBlockHeaderRule()), "{", CommonLang.Statements("children", functionSegmentRule)), "}"))
        )));

        return functionSegmentRule;
    }

    private static Rule createBlockHeaderRule() {
        return new OrRule(Lists.of(
                new StripRule(new PrefixRule("if", new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("condition", JavaLang.createValueRule()), ")")))))
        ));
    }

    private static Rule createFunctionSegmentValueRule() {
        return new OrRule(Lists.of(
                JavaLang.createInvokableRule(JavaLang.createValueRule()),
                JavaLang.createAssignmentRule(),
                new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("value", JavaLang.createValueRule()))))
        ));
    }

    private static Rule createDefinitionRule() {
        Rule leftRule1 = new StringRule("before-type");
        Rule rightRule = new NodeRule("type", JavaLang.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = DividingSplitter.Last(divider);
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        return new StripRule(LocatingRule.Last(leftRule, " ", new StringRule("name")));
    }

    private static Rule createTypeRule() {
        LazyRule orRule = new LazyRule();
        orRule.set(new OrRule(Lists.of(
                JavaLang.createVariadicRule(orRule),
                JavaLang.createArrayRule(orRule),
                CommonLang.createTemplateRule(),
                CommonLang.createSymbolTypeRule()
        )));
        return orRule;
    }

    private static TypeRule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    private static TypeRule createVariadicRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("variadic", new StripRule(new SuffixRule(child, "...")));
    }

    private static Rule createNamespacedRule(String type) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }
}
