package magma.app;

import magma.api.error.Error;

public record ApplicationError(Error error) implements Error {
    @Override
    public String display() {
        return error.display();
    }
}
