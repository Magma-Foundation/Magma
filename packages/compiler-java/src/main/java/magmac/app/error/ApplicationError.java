package magmac.app.error;

import magmac.api.error.Error;

public record ApplicationError(Error error) implements Error {
    @Override
    public String display() {
        return this.error.display();
    }
}
