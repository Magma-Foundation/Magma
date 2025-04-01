package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record State<T>(Optional<T> maybeValue, List<CompileError> errors) {
    public State() {
        this(Optional.empty(), Collections.emptyList());
    }

    public State<T> withValue(T value) {
        return new State<>(Optional.of(value), errors);
    }

    public State<T> withError(CompileError error) {
        ArrayList<CompileError> copy = new ArrayList<>(errors);
        copy.add(error);
        return new State<>(maybeValue, copy);
    }

    public Result<T, List<CompileError>> toResult() {
        return maybeValue.<Result<T, List<CompileError>>>map(Ok::new).orElseGet(() -> new Err<>(errors));
    }
}
