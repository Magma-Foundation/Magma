package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.java.assign.Assignment;
import magmac.app.lang.java.invoke.Invokable;
import magmac.app.lang.java.type.TemplateType;
import magmac.app.lang.java.value.CharNode;
import magmac.app.lang.java.value.DataAccess;
import magmac.app.lang.java.value.Lambda;
import magmac.app.lang.java.value.StringNode;
import magmac.app.lang.java.value.Symbols;

public final class CommonLang {
    public static NodeListRule Statements(String key, Rule childRule) {
        return new NodeListRule(key, new StatementFolder(), childRule);
    }

    static LazyRule initValueRule(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        return value.set(new OrRule(Lists.of(
                Lambda.createLambdaRule(value, segment, lambdaInfix, definition),
                new StripRule(new PrefixRule("!", new NodeRule("child", value))),
                CharNode.createCharRule(),
                StringNode.createStringRule(),
                Invokable.createInvokableRule(value),
                CommonLang.createIndexRule(value),
                CommonLang.createNumberRule(),
                Symbols.createSymbolValueRule(),
                DataAccess.createAccessRule(".", value, "data-access"),
                DataAccess.createAccessRule("::", value, "method-access"),
                CommonLang.createOperationRule(value, "add", "+"),
                CommonLang.createOperationRule(value, "subtract", "-"),
                CommonLang.createOperationRule(value, "equals", "=="),
                CommonLang.createOperationRule(value, "less-than", "<"),
                CommonLang.createOperationRule(value, "and", "&&"),
                CommonLang.createOperationRule(value, "or", "||"),
                CommonLang.createOperationRule(value, "not-equals", "!="),
                CommonLang.createOperationRule(value, "greater-than", ">")
        )));
    }

    private static Rule createIndexRule(LazyRule value) {
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(new NodeRule("parent", value), "[", new NodeRule("argument", value)), "]")));
    }

    private static Rule createOperationRule(Rule value, String type, String infix) {
        return new TypeRule(type, LocatingRule.First(new NodeRule("left", value), infix, new NodeRule("right", value)));
    }

    private static Rule createNumberRule() {
        return new TypeRule("number", new StripRule(FilterRule.Number(new StringRule("value"))));
    }

    public static Rule createPostRule(String type, String suffix, Rule value) {
        return new TypeRule(type, new StripRule(new SuffixRule(new NodeRule("child", value), suffix)));
    }

    public static Rule createTypeRule() {
        LazyRule type = new MutableLazyRule();
        return type.set(new OrRule(Lists.of(
                CommonLang.createVariadicRule(type),
                CommonLang.createArrayRule(type),
                TemplateType.createTemplateRule(type),
                Symbols.createSymbolTypeRule(),
                Symbols.createQualifiedRule()
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

    static Rule createStructureStatementRule(Rule definitionRule, LazyRule valueRule) {
        Rule definition = new NodeRule("value", new OrRule(Lists.of(
                definitionRule,
                Assignment.createAssignmentRule(definitionRule, valueRule))
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
