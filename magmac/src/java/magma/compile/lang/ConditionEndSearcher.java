package magma.compile.lang;

import magma.compile.rule.split.Searcher;

import java.util.Optional;

public class ConditionEndSearcher implements Searcher {
    @Override
    public Optional<Integer> search(String input) {
        return Optional.empty();
    }
}
