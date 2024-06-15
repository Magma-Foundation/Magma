package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.split.SplitOnceRule;

import java.util.Optional;

class InvocationStart extends SplitOnceRule {
    public InvocationStart(Rule caller, Rule arguments) {
        super(caller, "(", arguments);
    }

    @Override
    protected Optional<Integer> computeIndex(String input) {
        var depth = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            var c = input.charAt(i);
            if (c == '(' && depth == 0) return Optional.of(i);
            else if (c == ')') depth++;
            else if (c == '(') depth--;
        }

        return Optional.empty();
    }
}
