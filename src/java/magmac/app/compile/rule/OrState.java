package magmac.app.compile.rule;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.context.Context;
import magmac.app.compile.error.error.CompileError;
import magmac.app.error.ImmutableCompileError;

record OrState<T>(Option<T> maybeValue, List<CompileError> errors) {
    OrState() {
        this(new None<T>(), Lists.empty());
    }

    OrState<T> withValue(T value) {
        if (this.maybeValue.isPresent()) {
            return this;
        }
        return new OrState<>(new Some<T>(value), this.errors);
    }

    CompileResult<T> toResult(Context context) {
        return this.maybeValue
                .map((T value) -> CompileResults.fromResult(new Ok<>(value)))
                .orElseGet(() -> CompileResults.fromResult(new Err<>(new ImmutableCompileError("Invalid combination", context, this.errors))));
    }

    OrState<T> withError(CompileError error) {
        return new OrState<>(this.maybeValue, this.errors.addLast(error));
    }
}
