package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.DivideRule;
import magma.compile.rule.EmptyRule;
import magma.compile.rule.InfixRule;
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
        return new DivideRule("children", new StatementDivider(), createCRootSegmentRule());
    }

    private static OrRule createCRootSegmentRule() {
        return new OrRule(Lists.of(
                createIncludeRule(),
                new TypeRule("struct", new PrefixRule("struct ", new InfixRule(new StringRule("name"), " {", new SuffixRule(new EmptyRule(), "};"))))
        ));
    }

    private static Rule createIncludeRule() {
        DivideRule path = new DivideRule("path", new CharDivider('/'), new StringRule("value"));
        return new TypeRule("include",  new PrefixRule("#include \"", new SuffixRule(path, ".h\"\n")));
    }
}
