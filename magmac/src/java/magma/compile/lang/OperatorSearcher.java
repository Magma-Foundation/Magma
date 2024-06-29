package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.split.Searcher;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record OperatorSearcher(String slice) implements Searcher {
    @Override
    public Optional<Integer> search(String input) {
        if (!input.contains(slice)) return Optional.empty();

        var queue = IntStream.range(0, input.length())
                .mapToObj(i -> new Tuple<>(i, input.charAt(i)))
                .collect(Collectors.toCollection(LinkedList::new));

        var depth = 0;
        while (!queue.isEmpty()) {
            var tuple = queue.pop();
            var i = tuple.left();

            var maybeSlice = input.substring(i, Math.min(i + slice.length(), input.length()));
            if (maybeSlice.equals(slice) && depth == 0) {
                return Optional.of(i);
            } else {
                var c = maybeSlice.charAt(0);
                if (c == '\'') {
                    var pop = queue.pop();
                    if (pop.right() == '\\') queue.pop();
                    queue.pop();
                    continue;
                }

                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }

        /*
        TODO: find the operator
         */

        return Optional.empty();
    }
}
