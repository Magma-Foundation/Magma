package magma.result;

public record Err<T, X>(X t) implements Result<T, X> {
}
