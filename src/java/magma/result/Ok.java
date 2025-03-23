package magma.result;

public record Ok<T, X>(T t) implements Result<T, X> {
}
