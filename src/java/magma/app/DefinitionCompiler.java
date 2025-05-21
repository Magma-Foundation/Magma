package magma.app;

import magma.api.Tuple2;
import magma.api.collect.Iters;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.ValueUtils;
import magma.app.compile.define.Definition;
import magma.app.compile.define.Parameter;

final class DefinitionCompiler {
    public static Iterable<Definition> retainDefinitionsFromParameters(Iterable<Parameter> parameters) {
        return parameters.iter()
                .map((Parameter parameter) -> {
                    return parameter.asDefinition();
                })
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<Definition>());
    }

    public static String joinParameters(Iterable<Definition> parameters) {
        return parameters.iter()
                .map((Definition definition) -> {
                    return definition.generate();
                })
                .map((String generated) -> {
                    return "\n\t" + generated + ";";
                })
                .collect(Joiner.empty())
                .orElse("");
    }

    public static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String params) {
        return ValueUtils.parseValuesOrEmpty(state, params, (CompileState state1, String s) -> {
            return new Some<Tuple2<CompileState, Parameter>>(DefiningCompiler.parseParameterOrPlaceholder(state1, s));
        });
    }
}