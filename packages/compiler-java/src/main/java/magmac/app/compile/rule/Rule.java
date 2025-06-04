package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

/**
 * Describes how to lex source text and generate output for a language.
 */
public interface Rule {
    CompileResult<Node> lex(String input);

    CompileResult<String> generate(Node node);
}
