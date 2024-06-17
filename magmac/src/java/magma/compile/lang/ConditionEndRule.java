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

    @Override
    protected Optional<Integer> computeIndex(String input) {
        /*
        TODO: reduce nesting
         */

        var queue = IntStream.range(0, input.length())
                .mapToObj(i -> new Tuple<>(i, input.charAt(i)))
                .collect(Collectors.toCollection(LinkedList::new));

        var depth = 0;
        while (!queue.isEmpty()) {
            var pop = queue.pop();
            var i = pop.left();
            var c = pop.right();

            if (c == '\'') {
                var next = queue.pop();
                if(next.right() == '\\') {
                    queue.pop();
                }

                queue.pop();
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
