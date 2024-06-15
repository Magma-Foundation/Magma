package magma.compile;

import java.util.List;
import java.util.Optional;

public interface Error_ {
    Optional<String> findMessage();

    Optional<List<Error_>> findCauses();

    Optional<String> findContext();

    int calculateDepth();
}
