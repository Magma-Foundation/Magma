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
        return rule.parse(left).flatMapValue(parsed -> rule.transform(parseState, parsed))
                .flatMapValue(rule::generate);
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        return new Ok<>(new MapNode().withString(INPUT, input));
    }

    @Override
    public Result<MapNode, CompileError> transform(ParseState state, MapNode input) {
        return apply(state, input.find(INPUT).orElse("")).mapValue(tuple -> {
            return new MapNode().withString(HEADER, tuple.left()).withString(TARGET, tuple.right());
        });
    }

    @Override
    public Result<Tuple<String, String>, CompileError> generate(MapNode node) {
        return new Ok<>(new Tuple<>(node.find(HEADER).orElse(""), node.find(TARGET).orElse("")));
    }
}