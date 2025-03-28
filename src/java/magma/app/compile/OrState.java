package magma.app.compile;

import jvm.api.collect.Lists;
import magma.api.collect.List_;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;

public record OrState(Option<Tuple<String, String>> maybeValue, List_<CompileError> errors) {
    public OrState() {
        this(new None<>(), Lists.empty());
    }

    public OrState withValue(Tuple<String, String> result) {
        return new OrState(new Some<>(result), errors);
    }

    public boolean isPresent() {
        return maybeValue.isPresent();
    }

    public OrState withErr(CompileError error) {
        return new OrState(maybeValue, errors.add(error));
    }

    public Result<Tuple<String, String>, List_<CompileError>> toResult() {
        return maybeValue.<Result<Tuple<String, String>, List_<CompileError>>>map(value -> new Ok<>(value))
                .orElseGet(() -> new Err<>(errors));
    }
}