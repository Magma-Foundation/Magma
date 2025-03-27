package magma;

public record ThrowableError(Throwable throwable) implements Error {
}
