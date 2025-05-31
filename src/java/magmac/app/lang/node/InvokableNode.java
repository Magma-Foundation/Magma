package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.split.DividingSplitter;
import magmac.app.lang.Deserializers;
import magmac.app.lang.InvocationFolder;

record InvokableNode(Caller caller, List<Argument> arguments) implements Value, FunctionSegmentValue {
    public static Option<CompileResult<InvokableNode>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "invokable").map(deserializer -> deserializer.withNode("caller", Caller::deserialize)
                .withNodeList("arguments", Arguments::deserialize)
                .complete(tuple -> new InvokableNode(tuple.left(), tuple.right())));
    }

    public static Rule createInvokableRule(Rule value) {
        Rule childRule = new OrRule(Lists.of(Construction.createConstructionRule(), value));
        Rule caller = new NodeRule("caller", new SuffixRule(childRule, "("));
        Rule arguments = Arguments.createArgumentsRule(value);
        Splitter splitter = DividingSplitter.Last(new FoldingDivider(new InvocationFolder()), "");
        return new TypeRule("invokable", new StripRule(new SuffixRule(new LocatingRule(caller, splitter, arguments), ")")));
    }

    @Override
    public Node serialize() {
        return new MapNode("invokable")
                .withNodeSerialized("caller", this.caller)
                .withNodeListSerialized("arguments", this.arguments);
    }
}
