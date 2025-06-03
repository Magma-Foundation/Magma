package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public interface Rule {
    CompileResult<Node> lex(String input);

    CompileResult<String> generate(Node node);
}
