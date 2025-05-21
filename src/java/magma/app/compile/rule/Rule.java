package magma.app.compile.rule;

import magma.api.Tuple2;
import magma.api.option.Option;
import magma.app.compile.CompileState;

public interface Rule<T> {
    Option<Tuple2<CompileState, T>> apply(CompileState state, String input);
}
