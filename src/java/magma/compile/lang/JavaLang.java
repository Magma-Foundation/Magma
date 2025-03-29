package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.rule.DivideRule;
import magma.compile.rule.PrefixRule;
import magma.compile.rule.StripRule;
import magma.compile.rule.SuffixRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.StringRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

public class JavaLang {
    public static DivideRule createJavaRootRule() {
        return new DivideRule(new StatementDivider(), createJavaRootSegmentRule(), "children");
    }

    private static OrRule createJavaRootSegmentRule() {
        return new OrRule(Lists.of(
                createImportRule(),
                createImportRule()
        ));
    }

    private static StripRule createImportRule() {
        return new StripRule(new PrefixRule("import ", new SuffixRule(new DivideRule(new CharDivider('.'), new StringRule("value"), "namespace"), ";")));
    }
}
