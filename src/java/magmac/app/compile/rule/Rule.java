package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.compile.node.Node;

public interface Rule {
    Result<Node, CompileError> lex(String input);

    Result<String, CompileError> generate(Node node);
}
