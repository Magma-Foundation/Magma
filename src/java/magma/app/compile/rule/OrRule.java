package magma.app.compile.rule;

import magma.api.Tuple2;
import magma.api.collect.Iters;
import magma.api.collect.list.Iterable;
import magma.api.option.Option;
import magma.app.compile.CompileState;

public record OrRule<T>(Iterable<Rule<T>> rules) implements Rule<T> {
    @Override
    public Option<Tuple2<CompileState, T>> apply(CompileState state, String input) {
        return this.rules.iter()
                .map((Rule<T> rule) -> {
                    return rule.apply(state, input);
                })
                .flatMap(Iters::fromOption)
                .next();
    }
}