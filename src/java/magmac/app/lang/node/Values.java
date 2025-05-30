package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.Deserializer;
import magmac.app.lang.Deserializers;
import magmac.app.lang.LazyRule;

public final class Values {
    public static CompileResult<Value> deserializeOrError(Node node) {
        return Values.deserialize(node).orElseGet(() -> CompileResults.NodeErr("Cannot deserialize value", node));
    }

    public static Option<CompileResult<Value>> deserialize(Node node) {
        List<Deserializer<Value>> deserializers = Lists.of(
                Deserializers.wrap(SwitchNode::deserialize),
                Deserializers.wrap(InvokableNode::deserialize),
                StringNode::deserialize,
                node1 -> Access.deserialize(AccessType.Data, node1),
                node1 -> Access.deserialize(AccessType.Method, node1),
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(CharNode::deserialize),
                Deserializers.wrap(Lambda::deserialize),
                Deserializers.wrap(NumberNode::deserialize),
                Deserializers.wrap(Not::deserialize)
        );

        List<Deserializer<Value>> operatorRules = Iters.fromValues(Operator.values())
                .map(operator -> Deserializers.wrap(Operation.deserializeAs(operator)))
                .collect(new ListCollector<>());

        return Deserializers.or(node, deserializers.addAll(operatorRules));
    }

    public static LazyRule initValueRule(Rule segment, LazyRule value, String lambdaInfix, Rule definition) {
        return value.set(new OrRule(Values.getValueRules(segment, value, lambdaInfix, definition)));
    }

    private static List<Rule> getValueRules(Rule functionSegment, LazyRule value, String lambdaInfix, Rule definition) {
        List<Rule> ruleList = Lists.of(
                Values.createSwitchRule(functionSegment, value),
                Lambda.createLambdaRule(value, functionSegment, lambdaInfix, definition),
                Not.createNotRule(value),
                CharNode.createCharRule(),
                StringNode.createStringRule(),
                InvokableNode.createInvokableRule(value),
                CommonLang.createIndexRule(value),
                NumberNode.createNumberRule(),
                Symbols.createSymbolRule(),
                Access.createAccessRule("data-access", ".", value),
                Access.createAccessRule("method-access", "::", value)
        );

        List<Rule> operatorLists = Iters.fromValues(Operator.values())
                .map(operator -> Operation.createOperationRule(value, operator.type(), operator.text()))
                .collect(new ListCollector<>());

        return ruleList.addAll(operatorLists);
    }

    private static TypeRule createSwitchRule(Rule functionSegmentRule, Rule value) {
        NodeRule value1 = new NodeRule("value", value);
        PrefixRule header = new PrefixRule("switch", new StripRule(new PrefixRule("(", new SuffixRule(value1, ")"))));
        return new TypeRule("switch", Block.createBlockRule0(new StripRule(header), functionSegmentRule));
    }
}
