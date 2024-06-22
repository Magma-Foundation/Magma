package magma.compile.rule.split;

import java.util.Optional;

public interface Searcher {
    Optional<Integer> computeIndex(String input);
}
