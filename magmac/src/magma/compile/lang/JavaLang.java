package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.extract.ExtractStringRule;

public class JavaLang {
    public static Rule createRootRule() {
        return new TypeRule("block", new ExtractStringRule("value"));
    }
}
