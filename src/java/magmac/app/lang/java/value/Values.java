package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializer;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.invoke.Invokable;

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
                Deserializers.wrap(Symbols::deserializeSymbol),
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

}
