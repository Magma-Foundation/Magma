package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.DivideRule;
import magma.compile.rule.PrefixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SuffixRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.StringRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

public class CLang {
    public static Rule createCRootRule() {
        return new DivideRule(new StatementDivider(), createCRootSegmentRule(), "children");
    }

    private static OrRule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule()
        ));
    }

    private static Rule createIncludeRule() {
        DivideRule path = new DivideRule(new CharDivider('/'), new StringRule("value"), "path");
        return new TypeRule("include",  new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
