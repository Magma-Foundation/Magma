package magmac.app.compile.error;

import magmac.api.iter.collect.Collector;
import magmac.api.result.Ok;

public record CompileResultCollector<T, C>(Collector<T, C> joiner
) implements Collector<CompileResult<T>, CompileResult<C>> {
    @Override
    public CompileResult<C> createInitial() {
        return CompileResults.fromResult(new Ok<>(this.joiner.createInitial()));
    }

    @Override
    public CompileResult<C> fold(CompileResult<C> maybeCurrent, CompileResult<T> maybeElement) {
        return maybeCurrent.flatMapValue((C currentValue) -> maybeElement.mapValue((T element) -> this.joiner.fold(currentValue, element)));
    }
}
