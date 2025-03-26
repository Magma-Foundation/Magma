package magma;

public record Err<T, X>(X error) implements Result<T, X> {
}
