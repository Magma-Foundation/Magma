package magma.compile;

import java.util.List;
import java.util.Optional;

public class MultipleError implements Error_ {
    private final List<Error_> errors;

    public MultipleError(List<Error_> errors) {
        this.errors = errors;
    }

    @Override
    public Optional<String> findMessage() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Error_>> findCauses() {
        return Optional.of(errors);
    }

    @Override
    public Optional<String> findContext() {
        return Optional.empty();
    }

    @Override
    public int calculateDepth() {
        return 1 + errors.stream()
                .mapToInt(Error_::calculateDepth)
                .max()
                .orElse(0);
    }
}
