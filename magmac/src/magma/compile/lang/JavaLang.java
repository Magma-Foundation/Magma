package magma.compile.lang;

import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;

public class JavaLang {
    public static Rule createRootRule() {
        var rootMember = new OrRule(List.of(
                new TypeRule("package", new LeftRule("package ", new RightRule(new ExtractStringRule("namespace"), ";"))),
                new TypeRule("any", new ExtractStringRule("value"))
        ));

        return new TypeRule("block", new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(rootMember)));
    }
}
