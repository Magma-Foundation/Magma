package magma.compile.rule.split;

import magma.compile.rule.Rule;
import magma.compile.rule.Rules;

public final class LastRule extends SplitOnceRule {
    public LastRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule, input -> Rules.wrapIndex(input.lastIndexOf(slice)));
    }
}