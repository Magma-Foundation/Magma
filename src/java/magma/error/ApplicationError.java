package magma.error;

public class ApplicationError implements Error {
    private final Error error;

    public ApplicationError(Error error) {
        this.error = error;
    }

    @Override
    public String display() {
        return error.display();
    }
}
