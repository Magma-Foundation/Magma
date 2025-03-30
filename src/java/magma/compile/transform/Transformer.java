package magma.compile.transform;

import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.result.Result;

public interface Transformer {
    Result<Node, CompileError> afterPass(List_<String> namespace, Node node);
}
