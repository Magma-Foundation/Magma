package magma.compile.rule.split;

import magma.compile.rule.Rule;
import magma.compile.rule.Rules;

public final class FirstRule extends SplitOnceRule {
    public FirstRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule, input -> Rules.wrapIndex(input.indexOf(slice)));
    }
}