package magma.compile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record CompileError(String message, String context) implements Error_ {
    @Override
    public Optional<String> findMessage() {
        return Optional.of(message);
    }

    @Override
    public Optional<List<Error_>> findCauses() {
        return Optional.empty();
    }

    @Override
    public Optional<String> findContext() {
        return Optional.of(context);
    }
}
