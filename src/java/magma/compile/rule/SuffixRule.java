package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Result;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (!input.endsWith(suffix()))
            return new Err<>(new CompileError("Suffix '" + suffix() + "' not present", new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.parse(slice);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(result -> result + suffix);
    }
}