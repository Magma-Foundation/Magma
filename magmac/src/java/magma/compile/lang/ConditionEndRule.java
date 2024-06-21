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
        return Optional.empty();
    }
}
