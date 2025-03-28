package magma.app.compile.rule;

import magma.api.collect.List_;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.ParseState;

import static magma.app.compile.Compiler.HEADER;
import static magma.app.compile.Compiler.TARGET;

public record OrRule(List_<Rule> rules) implements Rule {
    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String input) {
        return rules().stream().foldWithInitial(new OrState(), (orState, rule) -> {
            if (orState.isPresent()) return orState;
            return rule.parse(state, input)
                    .flatMapValue(rule::generate)
                    .mapValue(Output::toTuple).match(orState::withValue, orState::withErr);
        }).toResult().mapErr(errors -> new CompileError("No valid combinations", input, errors));
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