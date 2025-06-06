package magmac.api.collect;

import magmac.api.Tuple2;
import magmac.api.iter.collect.Collector;

/**
 * Collects a pair of iterables using individual collectors for each side.
 */

public record TupleCollector<A, AC, B, BC>(
        Collector<A, AC> leftCollector,
        Collector<B, BC> rightCollector
) implements Collector<Tuple2<A, B>, Tuple2<AC, BC>> {
    @Override
    public Tuple2<AC, BC> createInitial() {
        return new Tuple2<>(this.leftCollector.createInitial(), this.rightCollector.createInitial());
    }

    @Override
    public Tuple2<AC, BC> fold(Tuple2<AC, BC> current, Tuple2<A, B> element) {
        var leftValue = this.leftCollector.fold(current.left(), element.left());
        var rightValue = this.rightCollector.fold(current.right(), element.right());
        return new Tuple2<>(leftValue, rightValue);
    }
}
