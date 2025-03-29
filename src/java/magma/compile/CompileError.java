package magma.compile;

import magma.compile.context.Context;
import magma.error.Error;

public class CompileError implements Error {
    private final String message;
    private final Context context;

    public CompileError(String message, Context context) {
        this.message = message;
        this.context = context;
    }

    @Override
    public String display() {
        return message + ": " + context.display();
    }
}
