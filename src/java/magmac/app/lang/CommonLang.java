package magmac.app.lang;

import magmac.api.collect.list.Lists;
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
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.java.invoke.Invokable;
import magmac.app.lang.java.value.Access;
import magmac.app.lang.java.value.CharNode;
import magmac.app.lang.java.value.Lambda;
import magmac.app.lang.java.value.NumberNode;
import magmac.app.lang.java.value.Operation;
import magmac.app.lang.java.value.StringNode;
import magmac.app.lang.java.value.Symbols;

public final class CommonLang {
    public static NodeListRule Statements(String key, Rule childRule) {
        return new NodeListRule(key, new StatementFolder(), childRule);
    }

    public static LazyRule initValueRule(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        return value.set(new OrRule(Lists.of(
                Lambda.createLambdaRule(value, segment, lambdaInfix, definition),
                new StripRule(new PrefixRule("!", new NodeRule("child", value))),
                CharNode.createCharRule(),
                StringNode.createStringRule(),
                Invokable.createInvokableRule(value),
                CommonLang.createIndexRule(value),
                NumberNode.createNumberRule(),
                Symbols.createSymbolValueRule(),
                Access.createAccessRule("data-access", ".", value),
                Access.createAccessRule("method-access", "::", value),
                Operation.createOperationRule(value, "add", "+"),
                Operation.createOperationRule(value, "subtract", "-"),
                Operation.createOperationRule(value, "equals", "=="),
                Operation.createOperationRule(value, "less-than", "<"),
                Operation.createOperationRule(value, "and", "&&"),
                Operation.createOperationRule(value, "or", "||"),
                Operation.createOperationRule(value, "not-equals", "!="),
                Operation.createOperationRule(value, "greater-than", ">")
        )));
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
