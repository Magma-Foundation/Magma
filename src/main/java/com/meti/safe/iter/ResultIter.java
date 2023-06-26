package com.meti.safe.iter;

import com.meti.safe.option.Option;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;

public class ResultIter<T, E extends Throwable> extends AbstractIterator<Result<T, E>> {
    private final Iterator<Result<T, E>> iter;

    public ResultIter(Iterator<Result<T, E>> iter) {
        this.iter = iter;
    }

    @Override
    public Option<Result<T, E>> head() {
        return iter.head();
    }

    public <R> Result<R, E> collectToResult(Collector<R, T> collector) {
        return foldLeft(Ok.apply(collector.initial()),
                (reResult, teResult) -> reResult.flatMapValue(
                        accumulated -> teResult.mapValue(
                                value -> collector.folder(accumulated, value))));
    }
}
