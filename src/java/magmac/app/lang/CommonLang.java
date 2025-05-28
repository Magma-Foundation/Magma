package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.compile.rule.split.DividingSplitter;

final class CommonLang {
    static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }

    static Rule createSymbolTypeRule() {
        return new TypeRule("symbol-type", CommonLang.createSymbolRule("value"));
    }

    private static StripRule createSymbolRule(String name) {
        return new StripRule(FilterRule.Symbol(new StringRule(name)));
    }

    static Rule createTemplateRule() {
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(new StripRule(new StringRule("base")), "<", new StringRule("arguments")), ">")));
    }

    static NodeListRule createParametersRule(Rule definition) {
        return new NodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                definition
        )));
    }

    public static NodeListRule Statements(String key, Rule childRule) {
        return new NodeListRule(key, new StatementFolder(), childRule);
    }

    private static Rule createAssignmentRule(Rule definition, Rule value) {
        Rule before = new OrRule(Lists.of(
                new NodeRule("definition", definition),
                new NodeRule("destination", value)
        ));

        Rule source = new NodeRule("source", value);
        return new TypeRule("assignment", LocatingRule.First(before, "=", source));
    }

    static LazyRule initValueRule(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        return value.set(new OrRule(Lists.of(
                CommonLang.createLambdaRule(value, segment, lambdaInfix, definition),
                new StripRule(new PrefixRule("!", new NodeRule("child", value))),
                CommonLang.createCharRule(),
                CommonLang.createStringRule(),
                CommonLang.createInvokableRule(value),
                CommonLang.createNumberRule(),
                CommonLang.createAccessRule(".", value, "data-access"),
                CommonLang.createAccessRule("::", value, "method-access"),
                CommonLang.createSymbolValueRule(),
                CommonLang.createOperationRule(value, "add", "+"),
                CommonLang.createOperationRule(value, "subtract", "-"),
                CommonLang.createOperationRule(value, "equals", "=="),
                CommonLang.createOperationRule(value, "less-than", "<"),
                CommonLang.createOperationRule(value, "and", "&&"),
                CommonLang.createOperationRule(value, "or", "||"),
                CommonLang.createOperationRule(value, "not-equals", "!="),
                CommonLang.createOperationRule(value, "greater-than", ">"),
                CommonLang.createIndexRule(value)
        )));
    }

    private static Rule createIndexRule(LazyRule value) {
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(new NodeRule("parent", value), "[", new NodeRule("argument", value)), "]")));
    }

    private static Rule createLambdaRule(LazyRule value, Rule functionSegment, String infix, Rule definition) {
        Rule value1 = new OrRule(Lists.of(
                new StripRule(new PrefixRule("{", new SuffixRule(CommonLang.Statements("children", functionSegment), "}"))),
                new NodeRule("value", value)
        ));

        NodeListRule parameters = new NodeListRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                definition,
                CommonLang.createSymbolRule("param")
        )));

        PrefixRule rule = new PrefixRule("(", new SuffixRule(parameters, ")"));
        OrRule rule1 = new OrRule(Lists.of(
                rule,
                CommonLang.createSymbolRule("param")
        ));

        return new TypeRule("lambda", LocatingRule.First(new StripRule(rule1), infix, value1));
    }

    private static Rule createOperationRule(Rule value, String type, String infix) {
        return new TypeRule(type, LocatingRule.First(new NodeRule("left", value), infix, new NodeRule("right", value)));
    }

    private static Rule createSymbolValueRule() {
        return CommonLang.createSymbolRule("value");
    }

    private static Rule createAccessRule(String infix, LazyRule value, String type) {
        Rule property = CommonLang.createSymbolRule("property");
        return new TypeRule(type, LocatingRule.Last(new NodeRule("instance", value), infix, property));
    }

    private static Rule createNumberRule() {
        return new TypeRule("number", new StripRule(FilterRule.Number(new StringRule("value"))));
    }

    private static Rule createStringRule() {
        return new TypeRule("string", new StripRule(new PrefixRule("\"", new SuffixRule(new StringRule("value"), "\""))));
    }

    private static Rule createCharRule() {
        return new TypeRule("char", new StripRule(new PrefixRule("'", new SuffixRule(new StringRule("value"), "'"))));
    }

    private static Rule createInvokableRule(Rule value) {
        Rule caller = new ContextRule("With caller", new SuffixRule(new OrRule(Lists.of(
                new ContextRule("As construction", new StripRule(new PrefixRule("new ", new NodeRule("type", CommonLang.createTypeRule())))),
                new ContextRule("As invocation", new NodeRule("caller", value))
        )), "("));

        NodeListRule arguments = CommonLang.createArgumentsRule(value);
        Splitter splitter = DividingSplitter.Last(new FoldingDivider(new InvocationFolder()), "");
        return new TypeRule("invocation", new StripRule(new SuffixRule(new LocatingRule(caller, splitter, arguments), ")")));
    }

    public static NodeListRule createArgumentsRule(Rule value) {
        return new NodeListRule("arguments", new ValueFolder(), new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                value
        )));
    }

    static Rule initFunctionSegmentRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule functionSegmentValueRule = CommonLang.createFunctionSegmentValueRule(value, definition);

        Rule rule = new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                CommonLang.createStatementRule(functionSegmentValueRule),
                CommonLang.createBlockRule(functionSegmentRule, value, definition)
        ));

        return functionSegmentRule.set(new StripRule("before", rule, ""));
    }

    private static Rule createStatementRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("statement", new StripRule(new SuffixRule(child, ";")));
    }

    private static Rule createBlockRule(LazyRule functionSegmentRule, Rule value, Rule definition) {
        Rule header = new NodeRule("header", CommonLang.createBlockHeaderRule(value, definition));
        Rule children = CommonLang.Statements("children", functionSegmentRule);
        Splitter first = DividingSplitter.First(new FoldingDivider(new BlockFolder()), "");
        Rule childRule = new LocatingRule(new SuffixRule(header, "{"), first, children);
        return new TypeRule("block", new StripRule(new SuffixRule(childRule, "}")));
    }

    private static Rule createBlockHeaderRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                new TypeRule("else", new StripRule(new ExactRule("else"))),
                new TypeRule("try", new StripRule(new ExactRule("try"))),
                CommonLang.createConditionalRule("if", value),
                CommonLang.createConditionalRule("while", value),
                new StripRule(new PrefixRule("catch", new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("definition", definition), ")")))))
        ));
    }

    private static Rule createConditionalRule(String prefix, Rule value) {
        return new StripRule(new PrefixRule(prefix, new StripRule(new PrefixRule("(", new SuffixRule(new NodeRule("condition", value), ")")))));
    }

    private static Rule createFunctionSegmentValueRule(Rule value, Rule definition) {
        return new OrRule(Lists.of(
                CommonLang.createInvokableRule(value),
                CommonLang.createAssignmentRule(definition, value),
                CommonLang.createReturnRule(value),
                CommonLang.createPostRule("post-increment", "++", value),
                CommonLang.createPostRule("post-decrement", "--", value),
                new TypeRule("break", new ExactRule("break")),
                new TypeRule("continue", new ExactRule("continue"))
        ));
    }

    private static Rule createPostRule(String type, String suffix, Rule value) {
        return new TypeRule(type, new StripRule(new SuffixRule(new NodeRule("child", value), suffix)));
    }

    private static Rule createReturnRule(Rule value) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("value", value))));
    }

    static Rule createTypeRule() {
        LazyRule orRule = new MutableLazyRule();
        return orRule.set(new OrRule(Lists.of(
                CommonLang.createVariadicRule(orRule),
                CommonLang.createArrayRule(orRule),
                CommonLang.createTemplateRule(),
                CommonLang.createSymbolTypeRule()
        )));
    }

    private static Rule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    private static Rule createVariadicRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("variadic", new StripRule(new SuffixRule(child, "...")));
    }

    static Rule createStructureStatementRule(Rule definition1, LazyRule value) {
        Rule definition = new NodeRule("value", new OrRule(Lists.of(
                definition1,
                CommonLang.createAssignmentRule(definition1, value))
        ));

        return new TypeRule("statement", new StripRule(new SuffixRule(definition, ";")));
    }

    static Rule createModifiersRule() {
        return new StripRule(new NodeListRule("modifiers", new DelimitedFolder(' '), new StringRule("value")));
    }

    static Rule attachTypeParams(Rule beforeTypeParams) {
        Rule typeParams = new NodeListRule("type-parameters", new ValueFolder(), new StringRule("value"));
        return new OptionNodeListRule("type-parameters",
                new StripRule(new SuffixRule(LocatingRule.First(beforeTypeParams, "<", typeParams), ">")),
                beforeTypeParams
        );
    }
}
