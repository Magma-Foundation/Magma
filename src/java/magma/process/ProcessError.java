package magma.process;

import magma.error.Error;

public record ProcessError(Error child) implements Error {
    @Override
    public String display() {
        return child.display();
    }
}
