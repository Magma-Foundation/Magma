package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

class Values {
    public static CompileResult<Value> deserializeError(Node node) {
        return Values.deserialize(node).orElseGet(() -> CompileResults.NodeErr("Cannot deserialize", node));
    }

    public static Option<CompileResult<Value>> deserialize(Node node) {
        return Deserializers.or(node, Lists.of(
                Invokable::deserialize,
                StringNode::deserialize
        ));
    }
}
