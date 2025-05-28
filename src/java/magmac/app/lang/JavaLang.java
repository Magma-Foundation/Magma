package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
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
                JavaLang.createAssignmentRule(JavaLang.createFunctionSegmentRule()))
        );

        return new TypeRule("statement", new StripRule(new SuffixRule(definition, ";")));
    }

    private static Rule createAssignmentRule(Rule functionSegment) {
        Rule definition = new OrRule(Lists.of(
                new NodeRule("definition", JavaLang.createDefinitionRule()),
                new NodeRule("destination", JavaLang.createValueRule(functionSegment))
        ));

        Rule value = new NodeRule("source", JavaLang.createValueRule(functionSegment));
        return new TypeRule("assignment", LocatingRule.First(definition, "=", value));
    }

    private static Rule createValueRule(Rule functionSegment) {
        LazyRule value = new MutableLazyRule();
        return value.set(new OrRule(Lists.of(
                JavaLang.createLambdaRule(value, functionSegment),
                new StripRule(new PrefixRule("!", new NodeRule("child", value))),
                JavaLang.createCharRule(),
                JavaLang.createStringRule(),
                JavaLang.createInvokableRule(value),
                JavaLang.createNumberRule(),
                JavaLang.createAccessRule(value),
                JavaLang.createSymbolValueRule(),
                JavaLang.createOperationRule(value, "add", "+"),
                JavaLang.createOperationRule(value, "subtract", "-"),
                JavaLang.createOperationRule(value, "equals", "=="),
                JavaLang.createOperationRule(value, "less-than", "<"),
                JavaLang.createOperationRule(value, "and", "&&"),
                JavaLang.createOperationRule(value, "or", "||"),
                JavaLang.createOperationRule(value, "not-equals", "!="),
                JavaLang.createOperationRule(value, "greater-than", ">"),
                JavaLang.createIndexRule(value)
        )));
    }

    private static TypeRule createIndexRule(LazyRule value) {
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(new NodeRule("parent", value), "[", new NodeRule("argument", value)), "]")));
    }

    private static TypeRule createLambdaRule(LazyRule value, Rule functionSegment) {
        Rule value1 = new OrRule(Lists.of(
                new StripRule(new PrefixRule("{", new SuffixRule(CommonLang.Statements("children", functionSegment), "}"))),
                new NodeRule("value", value)
        ));

        return new TypeRule("lambda", LocatingRule.First(new StringRule("before-arrow"), "->", value1));
    }

    private static TypeRule createOperationRule(Rule value, String type, String infix) {
        return new TypeRule(type, LocatingRule.First(new NodeRule("left", value), infix, new NodeRule("right", value)));
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
        Rule caller = new ContextRule("With caller", new SuffixRule(new OrRule(Lists.of(
                new ContextRule("As construction", new StripRule(new PrefixRule("new ", new NodeRule("type", JavaLang.createTypeRule())))),
                new ContextRule("As invocation", new NodeRule("caller", value))
        )), "("));

        DivideRule arguments = new DivideRule("arguments", new ValueFolder(), new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                value
        )));

        Splitter splitter = DividingSplitter.Last(new FoldingDivider(new InvocationFolder()), "");
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
        LazyRule functionSegmentRule = new MutableLazyRule();
        return functionSegmentRule.set(new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                new TypeRule("statement", new StripRule(new SuffixRule(JavaLang.createFunctionSegmentValueRule(functionSegmentRule), ";"))),
                JavaLang.createBlockRule(functionSegmentRule)
        )));
    }

    private static Rule createBlockRule(LazyRule functionSegmentRule) {
        Rule header = new NodeRule("header", JavaLang.createBlockHeaderRule(functionSegmentRule));
        Rule children = CommonLang.Statements("children", functionSegmentRule);
        Splitter first = DividingSplitter.First(new FoldingDivider(new BlockFolder()), "");
        Rule childRule = new LocatingRule(new SuffixRule(header, "{"), first, children);
        return new StripRule(new SuffixRule(childRule, "}"));
    }

    private static Rule createBlockHeaderRule(Rule functionSegment) {
        return new OrRule(Lists.of(
                new TypeRule("else", new StripRule(new ExactRule("else"))),
                new TypeRule("try", new StripRule(new ExactRule("try"))),
                JavaLang.createConditionalRule(functionSegment, "if"),
                JavaLang.createConditionalRule(functionSegment, "while"),
                new StripRule(new PrefixRule("catch", new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("definition", JavaLang.createDefinitionRule()), ")")))))
        ));
    }

    private static StripRule createConditionalRule(Rule functionSegment, String prefix) {
        return new StripRule(new PrefixRule(prefix, new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("condition", JavaLang.createValueRule(functionSegment)), ")")))));
    }

    private static Rule createFunctionSegmentValueRule(Rule functionSegment) {
        return new OrRule(Lists.of(
                JavaLang.createInvokableRule(JavaLang.createValueRule(functionSegment)),
                JavaLang.createAssignmentRule(functionSegment),
                JavaLang.createReturnRule(functionSegment),
                JavaLang.createPostRule("post-increment", "++", functionSegment),
                JavaLang.createPostRule("post-decrement", "--", functionSegment),
                new ExactRule("break"),
                new ExactRule("continue")
        ));
    }

    private static TypeRule createPostRule(String type, String suffix, Rule functionSegment) {
        return new TypeRule(type, new StripRule(new SuffixRule(new NodeRule("child", JavaLang.createValueRule(functionSegment)), suffix)));
    }

    private static TypeRule createReturnRule(Rule functionSegment) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("value", JavaLang.createValueRule(functionSegment)))));
    }

    private static Rule createDefinitionRule() {
        Rule leftRule1 = new StringRule("before-type");
        Rule rightRule = new NodeRule("type", JavaLang.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = DividingSplitter.Last(divider, " ");
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        return new StripRule(LocatingRule.Last(leftRule, " ", new StringRule("name")));
    }

    private static Rule createTypeRule() {
        LazyRule orRule = new MutableLazyRule();
        return orRule.set(new OrRule(Lists.of(
                JavaLang.createVariadicRule(orRule),
                JavaLang.createArrayRule(orRule),
                CommonLang.createTemplateRule(),
                CommonLang.createSymbolTypeRule()
        )));
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
