package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.StripRule;

public class Lang {
    static Rule createBlock(Rule child) {
        return new TypeRule("block", new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(child)));
    }
}
