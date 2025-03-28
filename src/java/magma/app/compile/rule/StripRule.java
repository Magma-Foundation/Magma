package magma.app.compile.rule;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

import static magma.app.compile.Compiler.HEADER;
import static magma.app.compile.Compiler.TARGET;

public record StripRule(Rule rule) implements Rule {
    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        String stripped = input.strip();
        return rule.parse(state, stripped)
                .flatMapValue(rule::generate)
                .mapValue(Output::toTuple);
    }

    @Override
    public Result<MapNode, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString(HEADER, tuple.left()).withString(TARGET, tuple.right());
        });
    }

    @Override
    public Result<Output, CompileError> generate(MapNode node) {
        return new Ok<>(new MapOutput().with("header", node.find(HEADER).orElse("")));
    }
}