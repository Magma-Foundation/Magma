package magma.compile.rule.split;

import magma.compile.rule.Rule;

import java.util.Optional;

@Deprecated
public abstract class AbstractSplitOnceRule extends SplitOnceRule {
    public AbstractSplitOnceRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    @Override
    protected Optional<Integer> computeIndex(String input) {
        return computeIndexImpl(input);
    }

    protected abstract Optional<Integer> computeIndexImpl(String input);

}
