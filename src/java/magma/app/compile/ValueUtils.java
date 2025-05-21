package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.app.CompilerUtils;
import magma.app.DivideRule;
import magma.app.compile.fold.ValueFolder;
import magma.app.compile.merge.ValueMerger;
import magma.app.compile.rule.Rule;

public class ValueUtils {
    public static String generateValueStrings(Iterable<String> values) {
        return CompilerUtils.generateAll(values, new ValueMerger());
    }

    public static <T> Tuple2<CompileState, List<T>> parseValuesOrEmpty(
            CompileState state,
            String input,
            Rule<T> mapper
    ) {
        return parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
    }

    public static <T> Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, String input, Rule<T> mapper) {
        return new DivideRule<>(new ValueFolder(), mapper).apply(state, input);
    }
}
