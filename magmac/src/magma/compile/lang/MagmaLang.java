package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.TypeRule;

public class MagmaLang {
    public static TypeRule createRootRule() {
        return new TypeRule("block", new EmptyRule());
    }
}
