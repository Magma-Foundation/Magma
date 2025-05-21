package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.compose.Composable;
import magma.app.compile.divide.FoldedDivider;
import magma.app.compile.fold.DecoratedFolder;
import magma.app.compile.fold.Folder;
import magma.app.compile.fold.StatementsFolder;
import magma.app.compile.fold.ValueFolder;
import magma.app.compile.locate.LastLocator;
import magma.app.compile.merge.Merger;
import magma.app.compile.merge.StatementsMerger;
import magma.app.compile.rule.OrRule;
import magma.app.compile.rule.Rule;
import magma.app.compile.split.LocatingSplitter;
import magma.app.compile.split.Splitter;
import magma.app.compile.text.Whitespace;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CompilerUtils {
    static Tuple2<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return CompilerUtils.compileAll(state, input, new StatementsFolder(), mapper, new StatementsMerger());
    }

    private static Tuple2<CompileState, String> compileAll(CompileState state, String input, Folder folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, Merger merger) {
        var folded = CompilerUtils.parseAll(state, input, folder, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s));
        }).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), CompilerUtils.generateAll(folded.right(), merger));
    }

    private static String generateAll(Iterable<String> elements, Merger merger) {
        return elements.iter().foldWithInitial("", merger::apply);
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, String input, Folder folder, Rule<T> rule) {
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

    static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            Iterable<Rule<String>> rules
    ) {
        return CompilerUtils.or(state, input, new OrRule<String>(rules)).orElseGet(() -> {
            return new Tuple2Impl<CompileState, String>(state, CompilerUtils.generatePlaceholder(input));
        });
    }

    public static <T> Option<Tuple2<CompileState, T>> or(
            CompileState state,
            String input,
            OrRule<T> orRule) {
        return orRule.rules().iter()
                .map((Rule<T> rule) -> {
                    return rule.apply(state, input);
                })
                .flatMap(Iters::fromOption)
                .next();
    }

    static <T> Option<Tuple2<CompileState, T>> compilePrefix(
            String input,
            String infix,
            Function<String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        if (!input.startsWith(infix)) {
            return new None<Tuple2<CompileState, T>>();
        }

        var slice = Strings.sliceFrom(input, Strings.length(infix));
        return mapper.apply(slice);
    }

    static Option<Tuple2<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return CompilerUtils.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> {
            return new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate());
        });
    }

    static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<Tuple2<CompileState, Whitespace>>();
    }

    public static String generateValueStrings(Iterable<String> values) {
        return CompilerUtils.generateAll(values, CompilerUtils::mergeValues);
    }

    static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            Rule<T> mapper
    ) {
        return CompilerUtils.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
    }

    static <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, Rule<T> mapper) {
        return CompilerUtils.parseAll(state, input, new ValueFolder(), mapper);
    }

    private static String mergeValues(String cache, String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    public static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return CompilerUtils.compileSplit(input, new LocatingSplitter(infix, new LastLocator()), mapper);
    }

    public static <T> Option<T> compileSuffix(String input, String suffix, Composable<String, T> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        var length = Strings.length(input);
        var length1 = Strings.length(suffix);
        var content = Strings.sliceBetween(input, 0, length - length1);
        return mapper.apply(content);
    }

    static <T> Option<T> compileSplit(String input, Splitter splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.apply(input).flatMap((Tuple2<String, String> tuple) -> {
            return mapper.apply(tuple.left(), tuple.right());
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