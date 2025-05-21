package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.fold.Folder;
import magma.app.compile.merge.Merger;

import java.util.function.BiFunction;

public final class CompilerUtils {
    public static Tuple2<CompileState, String> compileAll(CompileState state, String input, Folder folder, BiFunction<CompileState, String, Tuple2<CompileState, String>> mapper, Merger merger) {
        var folded = new DivideRule<>(folder, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, String>>(mapper.apply(state1, s));
        }).apply(state, input).orElse(new Tuple2Impl<CompileState, List<String>>(state, Lists.empty()));
        return new Tuple2Impl<CompileState, String>(folded.left(), CompilerUtils.generateAll(folded.right(), merger));
    }

    public static String generateAll(Iterable<String> elements, Merger merger) {
        return elements.iter().foldWithInitial("", merger::merge);
    }

    public static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}