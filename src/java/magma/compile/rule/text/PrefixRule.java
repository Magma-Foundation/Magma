package magma.compile.rule.text;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.compile.rule.Rule;
import magma.result.Err;
import magma.result.Result;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    private static Result<Node, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(input)));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        if (!input.startsWith(prefix()))
            return createPrefixErr(input, prefix());

        String slice = input.substring(prefix().length());
        return childRule().parse(slice);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(value -> prefix + value);
    }
}