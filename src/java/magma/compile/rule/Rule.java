package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.result.Result;

public interface Rule {
    Result<Node, CompileError> parse(String input);

    Result<String, CompileError> generate(Node input);
}
