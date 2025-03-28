package magma.app.compile.rule;

import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.Node;

public interface Rule {
    Result<Node, CompileError> parse(String input);

    Result<String, CompileError> generate(Node node);
}
