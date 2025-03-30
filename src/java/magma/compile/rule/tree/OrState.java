package magma.compile.rule.tree;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public record OrState<T>(Option<T> maybeValue, List_<CompileError> errors) {
    public OrState() {
        this(new None<>(), Lists.empty());
    }

    public OrState<T> withValue(T value) {
        return new OrState<>(new Some<>(value), errors);
    }

    public Result<T, List_<CompileError>> toResult() {
        return maybeValue
                .<Result<T, List_<CompileError>>>map(Ok::new)
                .orElseGet(() -> new Err<>(errors));
    }

    public OrState<T> withError(CompileError error) {
        return new OrState<>(maybeValue, errors.add(error));
    }
}
