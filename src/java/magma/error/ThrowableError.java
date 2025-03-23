package magma.error;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableError implements Error {
    private final Throwable throwable;

    public ThrowableError(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String display() {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
