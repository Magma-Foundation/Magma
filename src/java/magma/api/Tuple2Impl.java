package magma.api;

public record Tuple2Impl<A, B>(A leftValue, B rightValue) implements Tuple2<A, B> {
    @Override
    public A left() {
        return this.leftValue;
    }

    @Override
    public B right() {
        return this.rightValue;
    }
}
