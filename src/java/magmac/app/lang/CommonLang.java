package magmac.app.lang;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.java.invoke.Invokable;
import magmac.app.lang.java.value.Access;
import magmac.app.lang.java.value.CharNode;
import magmac.app.lang.java.value.Lambda;
import magmac.app.lang.java.value.Not;
import magmac.app.lang.java.value.NumberNode;
import magmac.app.lang.java.value.Operation;
import magmac.app.lang.java.value.Operator;
import magmac.app.lang.java.value.StringNode;
import magmac.app.lang.java.value.Symbols;

public final class CommonLang {
    public static NodeListRule Statements(String key, Rule childRule) {
        return new NodeListRule(key, new StatementFolder(), childRule);
    }

    public static LazyRule initValueRule(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        return value.set(new OrRule(CommonLang.getValueRules(segment, value, lambdaInfix, definition)));
    }

    private static List<Rule> getValueRules(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        List<Rule> ruleList = Lists.of(
                Lambda.createLambdaRule(value, segment, lambdaInfix, definition),
                Not.createNotRule(value),
                CharNode.createCharRule(),
                StringNode.createStringRule(),
                Invokable.createInvokableRule(value),
                CommonLang.createIndexRule(value),
                NumberNode.createNumberRule(),
                Symbols.createSymbolValueRule(),
                Access.createAccessRule("data-access", ".", value),
                Access.createAccessRule("method-access", "::", value)
        );

        List<Rule> operatorLists = Iters.fromValues(Operator.values())
                .map(operator -> Operation.createOperationRule(value, operator.type(), operator.text()))
                .collect(new ListCollector<>());

        return ruleList.addAll(operatorLists);
    }

    private static Rule createIndexRule(LazyRule value) {
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(new NodeRule("parent", value), "[", new NodeRule("argument", value)), "]")));
    }

    public static Rule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    public static Rule attachTypeParams(Rule beforeTypeParams) {
        Rule typeParams = new NodeListRule("type-parameters", new ValueFolder(), new StringRule("value"));
        return new OptionNodeListRule("type-parameters",
                new StripRule(new SuffixRule(LocatingRule.First(beforeTypeParams, "<", typeParams), ">")),
                beforeTypeParams
        );
    }
}
