package magmac.app.error;

import magmac.app.Error;

public record ApplicationError(magmac.app.Error error) implements Error {
    @Override
    public String display() {
        return this.error.display();
    }
}
