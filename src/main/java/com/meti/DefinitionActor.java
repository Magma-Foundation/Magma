package com.meti;

public record DefinitionActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        // let x : u64 = 10
        return input.firstIndexAfterSlice(NativeString.from("let"))
                .map(input::sliceFrom)
                .flatMap(range -> {
                    // x : u64 = 10
                    return range.firstIndexOfChar('=').map(valueSeparator -> {
                        var split = range.split(valueSeparator);
                        var definition = split.left();
                        var value = split.right();

                        return definition.firstIndexOfChar(':')
                                .map(definition::split)
                                .map(tuple -> {
                                    var name = tuple.left();
                                    var type = tuple.right();

                                    return new Definition(name, type, value);
                                }).unwrapOrElseGet(() -> {
                                    var type = new Resolver(value).resolve();
                                    return new Definition(definition, type, value);
                                });
                    });
                })
                .map(definition -> {
                    return state.mapStack(stack -> Ok.apply(stack.define(definition)));
                })
                .map(value -> {
                    return value;
                });
    }
}