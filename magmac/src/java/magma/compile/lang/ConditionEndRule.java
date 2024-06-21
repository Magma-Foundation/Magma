package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.Rule;
import magma.compile.rule.split.SplitOnceRule;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ConditionEndRule extends SplitOnceRule {
    public ConditionEndRule(Rule conditionParent, Rule valueParent) {
        super(conditionParent, ")", valueParent);
    }

    private static Optional<Integer> getInteger(LinkedList<Tuple<Integer, Character>> queue, int depth) {
        var current = new State(queue, depth, Optional.empty());
        while (!queue.isEmpty()) {
            var result = ok(current);
            if (result.index.isPresent()) {
                return result.index;
            } else {
                current = result;
            }
        }

        return Optional.empty();
    }

    private static State ok(State state) {
        var queue = state.queue;
        var depth = state.depth;

        var pop = queue.pop();
        var i = pop.left();
        var c = pop.right();

        if (c == '\'') {
            var next = queue.pop();
            if (next.right() == '\\') {
                queue.pop();
            }

            queue.pop();
            return new State(queue, depth, Optional.empty());
        }

        if (c == ')' && depth == 1) {
            return new State(queue, depth, Optional.of(i));
        } else {
            if (c == '(')
                return new State(queue, depth + 1, Optional.empty());
            if (c == ')')
                return new State(queue, depth - 1, Optional.empty());
            return new State(queue, depth, Optional.empty());
        }
    }

    @Override
    protected Optional<Integer> computeIndex(String input) {
        /*
        TODO: reduce nesting
         */

        var queue = IntStream.range(0, input.length())
                .mapToObj(i -> new Tuple<>(i, input.charAt(i)))
                .collect(Collectors.toCollection(LinkedList::new));

        var depth = 0;
        return getInteger(queue, depth);
    }

    record State(LinkedList<Tuple<Integer, Character>> queue, int depth, Optional<Integer> index) {
    }
}
