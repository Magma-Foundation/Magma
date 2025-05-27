package magmac.app.compile.rule;

import magmac.api.None;
import magmac.api.Some;
import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.error.context.Context;
import magmac.app.error.ImmutableCompileError;

import java.util.ArrayList;
import java.util.List;
import magmac.api.Option;

record OrState<T>(Option<T> maybeValue, List<CompileError> errors) {
    OrState() {
        this(new None<T>(), new ArrayList<>());
    }

    OrState<T> withValue(T value) {
        if (this.maybeValue.isPresent()) {
            return this;
        }
        return new OrState<>(new Some<T>(value), this.errors);
    }

    CompileResult<T> toResult(Context context) {
        return this.maybeValue
                .map(value -> InlineCompileResult.fromResult(new Ok<>(value)))
                .orElseGet(() -> InlineCompileResult.fromResult(new Err<>(new ImmutableCompileError("Invalid combination", context, this.errors))));
    }

    OrState<T> withError(CompileError error) {
        this.errors.add(error);
        return this;
    }
}
