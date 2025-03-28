package magma.app.compile.rule;

import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.Compiler;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

import static magma.app.compile.Compiler.HEADER;
import static magma.app.compile.Compiler.TARGET;

public record SuffixRule(Rule child, String suffix) implements Rule {
    private Result<Tuple<String, String>, CompileError> apply(ParseState parseState, String input) {
        if (!input.endsWith(suffix())) return Compiler.createSuffixErr(input, suffix());
        String left = input.substring(0, input.length() - suffix().length());
        Rule rule = child();
        return rule.parse(parseState, left)
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