package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.Compiler;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

import static magma.app.compile.Compiler.HEADER;
import static magma.app.compile.Compiler.TARGET;

public record PrefixRule(String prefix, SuffixRule childRule) implements Rule {
    static Err<Tuple<String, String>, CompileError> createPrefixErr(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        if (input.startsWith(prefix)) {
            String slice = input.substring(prefix.length());
            return childRule.parse(slice).flatMapValue(parsed -> childRule.transform(state, parsed))
                    .flatMapValue(childRule::generate);
        }

        return createPrefixErr(input, prefix);
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        return new Ok<>(new MapNode().withString(Compiler.INPUT, input));
    }

    @Override
    public Result<MapNode, CompileError> transform(ParseState state, MapNode input) {
        return apply(state, input.find(Compiler.INPUT).orElse("")).mapValue(tuple -> {
            return new MapNode().withString(HEADER, tuple.left()).withString(TARGET, tuple.right());
        });
    }

    @Override
    public Result<Tuple<String, String>, CompileError> generate(MapNode node) {
        return new Ok<>(new Tuple<>(node.find(HEADER).orElse(""), node.find(TARGET).orElse("")));
    }
}