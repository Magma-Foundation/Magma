package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.divide.FoldedDivider;
import magma.app.compile.fold.DecoratedFolder;
import magma.app.compile.fold.Folder;
import magma.app.compile.rule.Rule;

import java.util.function.BiFunction;

public record DivideRule<T>(Folder folder, Rule<T> rule) implements Rule<List<T>> {
    static Rule<String> toRule(BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s));
        };
    }

    @Override
    public Option<Tuple2<CompileState, List<T>>> apply(CompileState state, String input) {
        return new FoldedDivider(new DecoratedFolder(this.folder())).divide(input).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (Option<Tuple2<CompileState, List<T>>> maybeCurrent, String segment) -> {
            return maybeCurrent.flatMap((Tuple2<CompileState, List<T>> current) -> {
                var currentState = current.left();
                var currentElement = current.right();

                return this.rule().apply(currentState, segment).map((Tuple2<CompileState, T> mappedTuple) -> {
                    var mappedState = mappedTuple.left();
                    var mappedElement = mappedTuple.right();
                    return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
                });
            });
        });
    }
}