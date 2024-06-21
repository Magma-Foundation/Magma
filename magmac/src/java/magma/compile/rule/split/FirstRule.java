package magma.compile.rule.split;

import magma.compile.rule.Rule;
import magma.compile.rule.Rules;

import java.util.Optional;

public final class FirstRule extends AbstractSplitOnceRule implements Rule {
    public FirstRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    @Override
    protected Optional<Integer> computeIndexImpl(String input) {
        return Rules.wrapIndex(input.indexOf(slice));
    }
}