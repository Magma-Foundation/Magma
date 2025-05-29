package magmac.app.lang.java.value;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.invoke.Invokable;

public class Values {
    public static CompileResult<Value> deserializeOrError(Node node) {
        return Values.deserialize(node).orElseGet(() -> CompileResults.NodeErr("Cannot deserialize value", node));
    }

    public static Option<CompileResult<Value>> deserialize(Node node) {
        return Deserializers.or(node, Lists.of(
                Deserializers.wrap(Invokable::deserialize),
                StringNode::deserialize,
                node1 -> Access.deserialize(AccessType.Data, node1),
                node1 -> Access.deserialize(AccessType.Method, node1),
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(CharNode::deserialize),
                Deserializers.wrap(Lambda::deserialize),
                Deserializers.wrap(Add::deserialize),
                Deserializers.wrap(NumberNode::deserialize)
        ));
    }
}
