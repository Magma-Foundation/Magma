package magma.result;

public record Ok<T, X>(T value) implements Result<T, X> {
}
