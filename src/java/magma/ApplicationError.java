package magma;

public record ApplicationError(Error child) implements Error {
    @Override
    public String display() {
        return child.display();
    }
}
