package magma;

import magma.result.Result;

import java.util.List;
import java.util.function.Function;

public record OrRule(List<Rule> rules) implements Rule {
    @Override
    public Result<String, CompileError> generate(MapNode node) {
        return applyDisjunction(new NodeContext(node), rule -> rule.generate(node));
    }

    private <R> Result<R, CompileError> applyDisjunction(Context context, Function<Rule, Result<R, CompileError>> mapper) {
        return rules.stream()
                .reduce(new State<R>(),
                        (state, rule) -> mapper.apply(rule).match(state::withValue, state::withError),
                        (_, next) -> next).toResult()
                .mapErr(errors -> new CompileError("No valid combination", context, errors));
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        return applyDisjunction(new StringContext(input), rule -> rule.parse(input));
    }
}
