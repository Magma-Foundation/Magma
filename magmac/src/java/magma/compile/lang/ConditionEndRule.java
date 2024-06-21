package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.split.AbstractSplitOnceRule;

import java.util.Optional;

class ConditionEndRule extends AbstractSplitOnceRule {
    public ConditionEndRule(Rule conditionParent, Rule valueParent) {
        super(conditionParent, ")", valueParent);
    }

    @Override
    protected Optional<Integer> computeIndexImpl(String input) {
        return Optional.empty();
    }
}
