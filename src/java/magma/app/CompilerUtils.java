package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.DivideState;
import magma.app.compile.text.Whitespace;
import magma.app.divide.DecoratedFolder;
import magma.app.divide.FoldedDivider;
import magma.app.divide.Folder;
import magma.app.divide.StatementsFolder;
import magma.app.select.LastSelector;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CompilerUtils {
    static Tuple2<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper) {
        return CompilerUtils.compileAll(state, input, new StatementsFolder(), mapper, CompilerUtils::mergeStatements);
    }

    private static Tuple2<CompileState, String> compileAll(CompileState state, String input, Folder folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, Merger merger) {
        var folded = CompilerUtils.parseAll(state, input, folder, (CompileState state1, String s) -> new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s))).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), CompilerUtils.generateAll(folded.right(), merger));
    }

    private static String generateAll(Iterable<String> elements, Merger merger) {
        return elements.iter().foldWithInitial("", merger::apply);
    }

    private static <T> Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, String input, Folder folder, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> biFunction) {
        return new FoldedDivider(new DecoratedFolder(folder)).divide(input).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (Option<Tuple2<CompileState, List<T>>> maybeCurrent, String segment) -> maybeCurrent.flatMap((Tuple2<CompileState, List<T>> current) -> {
            var currentState = current.left();
            var currentElement = current.right();

            return biFunction.apply(currentState, segment).map((Tuple2<CompileState, T> mappedTuple) -> {
                var mappedState = mappedTuple.left();
                var mappedElement = mappedTuple.right();
                return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
            });
        }));
    }

    private static String mergeStatements(String cache, String element) {
        return cache + element;
    }

    static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            Iterable<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return CompilerUtils.or(state, input, rules).orElseGet(() -> new Tuple2Impl<CompileState, String>(state, CompilerUtils.generatePlaceholder(input)));
    }

    public static <T> Option<Tuple2<CompileState, T>> or(
            CompileState state,
            String input,
            Iterable<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.iter()
                .map((BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) -> CompilerUtils.getApply(state, input, rule))
                .flatMap(Iters::fromOption)
                .next();
    }

    private static <T> Option<Tuple2<CompileState, T>> getApply(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return rule.apply(state, input);
    }

    static Tuple2<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return CompilerUtils.compileOrPlaceholder(state, input, Lists.of(
                CompilerUtils::compileWhitespace,
                FunctionSegmentCompiler::compileEmptySegment,
                FunctionSegmentCompiler::compileBlock,
                FunctionSegmentCompiler::compileFunctionStatement,
                FunctionSegmentCompiler::compileReturnWithoutSuffix
        ));
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
        return CompilerUtils.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate()));
    }

    static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<Tuple2<CompileState, Whitespace>>();
    }

    static DivideState foldDelimited(DivideState state1, char c, char delimiter) {
        if (delimiter == c) {
            return state1.advance();
        }
        return state1.append(c);
    }

    static List<String> divideValues(String input) {
        return new FoldedDivider(new DecoratedFolder((state, c) -> CompilerUtils.foldValues(state, c))).divide(input)
                .map((String input1) -> Strings.strip(input1))
                .filter((String value) -> !Strings.isEmpty(value))
                .collect(new ListCollector<String>());
    }

    public static String generateValueStrings(Iterable<String> values) {
        return CompilerUtils.generateAll(values, CompilerUtils::mergeValues);
    }

    static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper
    ) {
        return CompilerUtils.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
    }

    static <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> mapper) {
        return CompilerUtils.parseAll(state, input, (state1, c) -> CompilerUtils.foldValues(state1, c), mapper);
    }

    private static String mergeValues(String cache, String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    private static DivideState foldValues(DivideState state, char c) {
        if (',' == c && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if ('-' == c) {
            var peeked = appended.peek();
            if ('>' == peeked) {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if ('<' == c || '(' == c) {
            return appended.enter();
        }

        if ('>' == c || ')' == c) {
            return appended.exit();
        }

        return appended;
    }

    public static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return CompilerUtils.compileInfix(input, infix, CompilerUtils::findLast, mapper);
    }

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    public static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<T>();
        }

        var length = Strings.length(input);
        var length1 = Strings.length(suffix);
        var content = Strings.sliceBetween(input, 0, length - length1);
        return mapper.apply(content);
    }

    public static <T> Option<T> compileFirst(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return CompilerUtils.compileInfix(input, infix, CompilerUtils::findFirst, mapper);
    }

    private static <T> Option<T> compileInfix(String input, String infix, Locator locator, BiFunction<String, String, Option<T>> mapper) {
        return CompilerUtils.compileSplit(CompilerUtils.split(input, infix, locator), mapper);
    }

    static <T> Option<T> compileSplit(Option<Tuple2<String, String>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.flatMap((Tuple2<String, String> tuple) -> mapper.apply(tuple.left(), tuple.right()));
    }

    private static Option<Tuple2<String, String>> split(String input, String infix, Locator locator) {
        var index = locator.apply(input, infix);
        if (0 > index) {
            return new None<Tuple2<String, String>>();
        }

        var left = Strings.sliceBetween(input, 0, index);

        var length = Strings.length(infix);
        var right = Strings.sliceFrom(input, index + length);
        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(left, right));
    }

    private static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    public static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }

    static Folder foldOperator(String infix) {
        return (DivideState state, char c) -> {
            if (c == infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
                var length = Strings.length(infix) - 1;
                var counter = 0;
                var current = state;
                while (counter < length) {
                    counter++;

                    current = current.pop().map((Tuple2<DivideState, Character> tuple) -> tuple.left()).orElse(current);
                }
                return current.advance();
            }

            return state.append(c);
        };
    }

    static Option<Tuple2<String, String>> selectFirst(List<String> divisions, String delimiter) {
        var first = divisions.findFirst().orElse("");
        var afterFirst = divisions.subList(1, divisions.size()).orElse(divisions)
                .iter()
                .collect(new Joiner(delimiter))
                .orElse("");

        return new Some<Tuple2<String, String>>(new Tuple2Impl<String, String>(first, afterFirst));
    }

    static <T, R> Iterable<R> retain(Iterable<T> args, Function<T, Option<R>> mapper) {
        return args.iter()
                .map(mapper)
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<R>());
    }

    static Option<Tuple2<String, String>> splitFoldedLast(String input, String delimiter, Folder folder) {
        return CompilerUtils.splitFolded(input, folder, new LastSelector(delimiter));
    }

    public static Option<Tuple2<String, String>> splitFolded(
            String input,
            Folder folder,
            Selector selector
    ) {
        var divisions = new FoldedDivider(new DecoratedFolder(folder)).divide(input)
                .collect(new ListCollector<String>());

        if (2 > divisions.size()) {
            return new None<Tuple2<String, String>>();
        }

        return selector.apply(divisions);
    }
}