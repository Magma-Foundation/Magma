package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.divide.FoldedDivider;
import magma.app.compile.fold.DecoratedFolder;
import magma.app.compile.fold.Folder;
import magma.app.compile.merge.Merger;
import magma.app.compile.rule.Rule;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CompilerUtils {
    public static Tuple2<CompileState, String> compileAll(CompileState state, String input, Folder folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, Merger merger) {
        var folded = CompilerUtils.parseAll(state, input, folder, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s));
        }).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), CompilerUtils.generateAll(folded.right(), merger));
    }

    public static String generateAll(Iterable<String> elements, Merger merger) {
        return elements.iter().foldWithInitial("", merger::merge);
    }

    public static <T> Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, String input, Folder folder, Rule<T> rule) {
        return new FoldedDivider(new DecoratedFolder(folder)).divide(input).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (Option<Tuple2<CompileState, List<T>>> maybeCurrent, String segment) -> {
            return maybeCurrent.flatMap((Tuple2<CompileState, List<T>> current) -> {
                var currentState = current.left();
                var currentElement = current.right();

                return rule.apply(currentState, segment).map((Tuple2<CompileState, T> mappedTuple) -> {
                    var mappedState = mappedTuple.left();
                    var mappedElement = mappedTuple.right();
                    return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
                });
            });
        });
    }

    public static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }

    static <T, R> Iterable<R> retain(Iterable<T> args, Function<T, Option<R>> mapper) {
        return args.iter()
                .map(mapper)
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<R>());
    }
}