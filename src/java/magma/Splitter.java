package magma;

import java.util.Optional;

public interface Splitter {
    Optional<Tuple<String, String>> split(String input);
}
