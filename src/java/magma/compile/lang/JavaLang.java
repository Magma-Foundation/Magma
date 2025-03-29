package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.compile.lang.r.SymbolRule;
import magma.compile.rule.DivideRule;
import magma.compile.rule.InfixRule;
import magma.compile.rule.PrefixRule;
import magma.compile.rule.Rule;
import magma.compile.rule.StripRule;
import magma.compile.rule.SuffixRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.StringRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.divide.CharDivider;
import magma.compile.rule.divide.StatementDivider;

import java.io.StringWriter;

public class JavaLang {
    public static DivideRule createJavaRootRule() {
        return new DivideRule(new StatementDivider(), createJavaRootSegmentRule(), "children");
    }

    private static OrRule createJavaRootSegmentRule() {
        return new OrRule(Lists.of(
                createImportRule("package ", "package"),
                createImportRule("import ", "import"),
                createClassRule()
        ));
    }

    private static TypeRule createClassRule() {
        return new TypeRule("class", new InfixRule(new StringRule("modifiers"), "class ", new InfixRule(new StripRule(new SymbolRule(new StringRule("name"))), "{", new StringRule("with-end"))));
    }

    private static Rule createImportRule(String prefix, String type) {
        DivideRule namespace = new DivideRule(new CharDivider('.'), new StringRule("value"), "namespace");
        return new TypeRule(type, new StripRule(new PrefixRule(prefix, new SuffixRule(namespace, ";"))));
    }
}
