package magma.compile.rule.result;

import magma.compile.rule.Rule;
import magma.compile.rule.Rules;

import java.util.Optional;

public final class LastRule extends SplitRule implements Rule {
    public LastRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    @Override
    protected Optional<Integer> computeIndex(String input) {
        return Rules.wrapIndex(input.lastIndexOf(slice));
    }
}