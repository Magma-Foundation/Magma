package magmac.app.lang.java.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.LazyRule;
import magmac.app.lang.java.Deserializer;
import magmac.app.lang.java.Deserializers;

public class Values {
    public static CompileResult<Value> deserializeOrError(Node node) {
        return Values.deserialize(node).orElseGet(() -> CompileResults.NodeErr("Cannot deserialize value", node));
    }

    public static Option<CompileResult<Value>> deserialize(Node node) {
        List<Deserializer<Value>> deserializers = Lists.of(
                Deserializers.wrap(Invokable::deserialize),
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
        return value.set(new OrRule(getValueRules(segment, value, lambdaInfix, definition)));
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
                Symbols.createSymbolRule(),
                Access.createAccessRule("data-access", ".", value),
                Access.createAccessRule("method-access", "::", value)
        );

        List<Rule> operatorLists = Iters.fromValues(Operator.values())
                .map(operator -> Operation.createOperationRule(value, operator.type(), operator.text()))
                .collect(new ListCollector<>());

        return ruleList.addAll(operatorLists);
    }
}
