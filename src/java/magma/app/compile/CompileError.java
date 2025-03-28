package magma.app.compile;

import magma.api.error.Error;

public class CompileError implements Error {
    private final String message;
    private final String context;

    public CompileError(String message, String context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public String display() {
        return message + ": " + context;
    }
}
