package magma.compile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

public record JavaError(GeneratingException e) implements Error_ {
    @Override
    public Optional<String> findMessage() {
        return Optional.of(e.getMessage());
    }

    @Override
    public Optional<List<Error_>> findCauses() {
        return Optional.empty();
    }

    @Override
    public Optional<String> findContext() {
        var writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return Optional.of(writer.toString());
    }

    @Override
    public int calculateDepth() {
        return 1;
    }
}
