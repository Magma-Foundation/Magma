package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.split.SplitOnceRule;

import java.util.Optional;

class ConditionEndRule extends SplitOnceRule {
    public ConditionEndRule(Rule conditionParent, Rule valueParent) {
        super(conditionParent, ")", valueParent);
    }

    @Override
    protected Optional<Integer> computeIndex(String input) {
        /*
        TODO: reduce nesting
         */

        var depth = 0;
        int i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);
            if (c == ')' && depth == 1) {
                return Optional.of(i);
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
            i++;
        }

        return Optional.empty();
    }
}
