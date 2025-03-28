package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.Node;
import magma.app.compile.ParseState;
import magma.app.compile.StringContext;

public record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
    static Err<Tuple<String, String>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", new StringContext(input)));
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        if (input.startsWith(prefix)) {
            String slice = input.substring(prefix.length());
            return childRule.parse(state, slice)
                    .flatMapValue(childRule::generate)
                    .mapValue(Output::toTuple);
        }

        return createPrefixErr(input, prefix);
    }

    @Override
    public Result<Node, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString("header", tuple.left()).withString("target", tuple.right());
        });
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return new Ok<>(new MapOutput().with("header", node.findString("header").orElse("")));
    }
}