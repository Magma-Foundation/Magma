package magma.app.compile.rule;

import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.Node;
import magma.app.compile.ParseState;

public interface Rule {
    Result<Node, CompileError> parse(ParseState state, String input);

    Result<String, CompileError> generate(Node node);
}
