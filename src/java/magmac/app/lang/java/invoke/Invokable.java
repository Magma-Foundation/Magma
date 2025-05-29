package magmac.app.lang.java.invoke;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.split.DividingSplitter;
import magmac.app.lang.CommonLang;
import magmac.app.lang.InvocationFolder;
import magmac.app.lang.java.function.FunctionSegmentValue;
import magmac.app.lang.java.value.Argument;
import magmac.app.lang.java.value.Arguments;
import magmac.app.lang.java.value.Value;

public record Invokable(Caller left, List<Argument> right) implements Value, FunctionSegmentValue {
    public static Option<CompileResult<Invokable>> deserialize(Node node) {
        return node.deserializeWithType("invokable").map(deserializer -> deserializer.withNode("caller", Caller::deserialize)
                .withNodeList("arguments", Arguments::deserialize)
                .complete(tuple -> new Invokable(tuple.left(), tuple.right())));
    }

    public static Rule createInvokableRule(Rule value) {
        Rule childRule = new OrRule(Lists.of(Invokable.createConstructionRule(), value));
        Rule caller = new NodeRule("caller", new SuffixRule(childRule, "("));
        Rule arguments = Arguments.createArgumentsRule(value);
        Splitter splitter = DividingSplitter.Last(new FoldingDivider(new InvocationFolder()), "");
        return new TypeRule("invokable", new StripRule(new SuffixRule(new LocatingRule(caller, splitter, arguments), ")")));
    }

    private static Rule createConstructionRule() {
        return new TypeRule("construction", new StripRule(new PrefixRule("new ", new NodeRule("type", CommonLang.createTypeRule()))));
    }
}
