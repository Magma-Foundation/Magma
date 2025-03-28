package magma.app.compile.rule;

import jvm.api.collect.Lists;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.app.compile.CompileError;

public record OrState<T>(Option<T> maybeValue, List_<CompileError> errors) {
    public OrState() {
        this(new None<>(), Lists.empty());
    }

    public OrState<T> withValue(T result) {
        return new OrState<>(new Some<>(result), errors);
    }

    public boolean isPresent() {
        return maybeValue.isPresent();
    }

    public OrState<T> withErr(CompileError error) {
        return new OrState<T>(maybeValue, errors.add(error));
    }

    public Result<T, List_<CompileError>> toResult() {
        return maybeValue.<Result<T, List_<CompileError>>>map(Ok::new).orElseGet(() -> new Err<>(errors));
    }
}