package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public class DiscardRule implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return CompileResults.Ok(new MapNode());
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return CompileResults.Ok("");
    }
}
