package magma.api.contain.stream;

import magma.api.option.None;
import magma.api.option.Option;

import java.util.function.Function;

class FlatMapHead<T, R> implements Head<R> {
    private final Head<T> outer;
    private final Function<T, Head<R>> mapper;
    private Head<R> current;

    public FlatMapHead(Head<R> initial, HeadedStream<T> outer, Function<T, Head<R>> mapper) {
        this.outer = outer;
        this.mapper = mapper;
        current = initial;
    }

    @Override
    public Option<R> head() {
        while (true) {
            var currentHead = current.head();
            if (currentHead.isPresent()) return currentHead;

            var tuple = outer.head()
                    .map(mapper)
                    .toTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                return None.None();
            }
        }
    }
}
