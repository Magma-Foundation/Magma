package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.Node;
import magma.app.compile.StringContext;

public record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (input.startsWith(prefix)) {
            String slice = input.substring(prefix.length());
            return childRule.parse(slice);
        }

        String format = "Prefix '%s' not present";
        String message = format.formatted(prefix);
        StringContext context = new StringContext(input);
        CompileError error = new CompileError(message, context);
        return new Err<>(error);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(output -> prefix + output);
    }
}