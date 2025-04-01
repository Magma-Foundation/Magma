package magma;

import magma.error.Error;

public record ApplicationError(magma.error.Error error) implements Error {
    @Override
    public String display() {
        return error.display();
    }
}
