package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.split.Searcher;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConditionEndSearcher implements Searcher {
    @Override
    public Optional<Integer> search(String input) {
        var depth = 0;

        var queue = IntStream.range(0, input.length())
                .mapToObj(index -> new Tuple<>(index, input.charAt(index)))
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            var tuple = queue.pop();
            var i = tuple.left();
            var c = tuple.right();

            if (c == '\'') {
                var popped = queue.pop();
                if(popped.right() == '\\') {
                    queue.pop();
                }

                queue.pop();
                continue;
            }

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
