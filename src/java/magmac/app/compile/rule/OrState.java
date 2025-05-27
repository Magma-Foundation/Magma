package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.context.Context;
import magmac.app.error.ImmutableCompileError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

record OrState<T>(Optional<T> maybeValue, List<CompileError> errors) {
    OrState() {
        this(Optional.empty(), new ArrayList<>());
    }

    OrState<T> withValue(T value) {
        if (this.maybeValue.isPresent()) {
            return this;
        }
        return new OrState<>(Optional.of(value), this.errors);
    }

    Result<T, CompileError> toResult(Context context) {
        return this.maybeValue
                .<Result<T, CompileError>>map(value -> new Ok<>(value))
                .orElseGet(() -> new Err<>(new ImmutableCompileError("Invalid combination", context, this.errors)));
    }

    OrState<T> withError(CompileError error) {
        this.errors.add(error);
        return this;
    }
}
