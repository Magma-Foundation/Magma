package magma.compile.lang;

import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class Transformers {
    public static Result<Node, CompileError> find(Node node, String propertyKey) {
        return node.findNode(propertyKey)
                .<Result<Node, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("Node '" + propertyKey + "' not present", new NodeContext(node))));
    }

    public static Result<List_<Node>, CompileError> findNodeList(Node value, String propertyKey) {
        return value.findNodeList(propertyKey)
                .<Result<List_<Node>, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(value))));
    }
}
