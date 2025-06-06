package magmac.app.error;

import magmac.api.error.Error;

import java.io.PrintWriter;
import java.io.StringWriter;

public record ThrowableError(Throwable throwable) implements Error {
    @Override
    public String display() {
        var writer = new StringWriter();
        this.throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
