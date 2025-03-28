package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.Node;
import magma.app.compile.StringContext;

public record SuffixRule(Rule child, String suffix) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (input.endsWith(this.suffix)) {
            String left = input.substring(0, input.length() - this.suffix.length());
            return child.parse(left);
        }

        String format = "Suffix '%s' not present";
        String message = format.formatted(this.suffix());
        StringContext context = new StringContext(input);
        CompileError error = new CompileError(message, context);
        return new Err<>(error);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return child.generate(node).mapValue(value -> value + ";");
    }
}