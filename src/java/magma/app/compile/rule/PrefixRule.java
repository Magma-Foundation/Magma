package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

public record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
    static Err<Tuple<String, String>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
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
    public Result<MapNode, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString("header", tuple.left()).withString("target", tuple.right());
        });
    }

    @Override
    public Result<Output, CompileError> generate(MapNode node) {
        return new Ok<>(new MapOutput().with("header", node.find("header").orElse("")));
    }
}