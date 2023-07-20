package com.meti.iterate;

import com.meti.core.Option;
import com.meti.core.Tuple;

public class TupleIterator<A, B> extends AbstractIterator<Tuple<A, B>> {
    private final Iterator<Tuple<A, B>> parent;

    public TupleIterator(Iterator<Tuple<A, B>> parent) {
        this.parent = parent;
    }

    public <C, D> Tuple<C, D> collectTuple(Collector<A, C> aCollector, Collector<B, D> bCollector) {
        return parent.collect(new Collector<>() {
            @Override
            public Tuple<C, D> initial() {
                return new Tuple<>(aCollector.initial(), bCollector.initial());
            }

            @Override
            public Tuple<C, D> foldLeft(Tuple<C, D> accumulated, Tuple<A, B> element) {
                var left = aCollector.foldLeft(accumulated.a(), element.a());
                var right = bCollector.foldLeft(accumulated.b(), element.b());
                return new Tuple<>(left, right);
            }
        });
    }

    @Override
    public Option<Tuple<A, B>> head() {
        return parent.head();
    }
}
