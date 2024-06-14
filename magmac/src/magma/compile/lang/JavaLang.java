package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.extract.ExtractStringRule;

public class JavaLang {
    public static Rule createRootRule() {
        var any = new TypeRule("any", new ExtractStringRule("value"));
        return new TypeRule("block", new SplitMultipleRule(new MembersSplitter(), "", "children", any));
    }
}
