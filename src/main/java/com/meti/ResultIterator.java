package com.meti;

public class ResultIterator<T, E> extends AbstractIterator<Result<T, E>> {
    private final Iterator<Result<T, E>> parent;

    public ResultIterator(Iterator<Result<T, E>> parent) {
        this.parent = parent;
    }

    public <C> Result<C, E> collectToResult(Collector<T, C> collector) {
        return parent.collect(new Collector<>() {
            @Override
            public Result<C, E> initial() {
                return new Ok<>(collector.initial());
            }

            @Override
            public Result<C, E> foldLeft(Result<C, E> accumulated, Result<T, E> element) {
                return accumulated.mapValueToResult(accumulatedValue ->
                        element.mapValue(elementValue ->
                                collector.foldLeft(accumulatedValue, elementValue)));
            }
        });
    }

    @Override
    public Option<Result<T, E>> head() {
        return parent.head();
    }
}
