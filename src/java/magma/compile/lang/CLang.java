package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.DivideRule;
import magma.compile.rule.PrefixRule;
import magma.compile.rule.SuffixRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.StringRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

public class CLang {
    public static DivideRule createCRootRule() {
        return new DivideRule(new StatementDivider(), createCRootSegmentRule(), "children");
    }

    private static OrRule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule()
        ));
    }

    private static PrefixRule createIncludeRule() {
        DivideRule path = new DivideRule(new CharDivider('/'), new StringRule("value"), "path");
        return new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n"));
    }
}
