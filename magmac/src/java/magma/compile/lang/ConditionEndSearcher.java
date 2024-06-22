package magma.compile.lang;

import magma.compile.rule.split.Searcher;

import java.util.Optional;

public class ConditionEndSearcher implements Searcher {
    @Override
    public Optional<Integer> search(String input) {
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ')' && depth == 1) {
                return Optional.of(i);
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        return Optional.empty();
    }
}
