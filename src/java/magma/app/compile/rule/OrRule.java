package magma.app.compile.rule;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.list.Iterable;
import magma.api.option.Option;
import magma.app.CompilerUtils;
import magma.app.compile.CompileState;

public record OrRule<T>(Iterable<Rule<T>> rules) implements Rule<T> {
    public static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            Iterable<Rule<String>> rules
    ) {
        return new OrRule<String>(rules).apply(state, input).orElseGet(() -> {
            return new Tuple2Impl<CompileState, String>(state, CompilerUtils.generatePlaceholder(input));
        });
    }

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