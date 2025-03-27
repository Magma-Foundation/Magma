package magma;

public record ApplicationError(Error child) implements Error {
}
