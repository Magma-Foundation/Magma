package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public record ExceptionalIOError(IOException e) implements IOError {
    @Override
    public String display() {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
