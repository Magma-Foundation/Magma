package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;

public class MagmaLang {
    public static Rule createRootRule() {
        return Lang.createBlock(new TypeRule("empty", new EmptyRule()));
    }
}
