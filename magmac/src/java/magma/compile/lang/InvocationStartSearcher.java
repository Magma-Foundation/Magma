package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.split.Searcher;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class InvocationStartSearcher implements Searcher {
    @Override
    public Optional<Integer> search(String input) {
        var depth = 0;

        var queue = IntStream.range(0, input.length())
                .map(i -> input.length() - i - 1)
                .mapToObj(i -> new Tuple<>(i, input.charAt(i)))
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            var pop = queue.pop();
            var i = pop.left();
            var c = pop.right();

            if (c == '\"') {
                while (!queue.isEmpty()) {
                    var next = queue.pop().right();

                    if (next == '\"') {
                        if (!queue.isEmpty()) {
                            var after = queue.peek().right();
                            if (after == '\\') {
                                continue;
                            }
                        }
                    }

                    if (next == '\"') {
                        break;
                    }
                }
            }

            if (c == '(' && depth == 0) return Optional.of(i);
            else if (c == ')') depth++;
            else if (c == '(') depth--;
        }

        return Optional.empty();
    }
}
