package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.node.Node;
import magmac.app.compile.error.context.StringContext;
import magmac.app.error.ImmutableCompileError;

import java.util.List;

public record ContextRule(String message, Rule rule) implements Rule {

    @Override
    public CompileResult<Node> lex(String input) {
        return this.rule.lex(input).mapErr(err -> new ImmutableCompileError(this.message, new StringContext(input), List.of(err)));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.rule.generate(node).mapErr(err -> new ImmutableCompileError(this.message, new NodeContext(node), List.of(err)));
    }
}
