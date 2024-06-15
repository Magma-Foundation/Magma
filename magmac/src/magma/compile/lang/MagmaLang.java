package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;

import java.util.List;

public class MagmaLang {
    public static Rule createRootRule() {
        return Lang.createBlock(new OrRule(List.of(
                new TypeRule("import", new LeftRule("import ", new RightRule(new EmptyRule(), ";")))
        )));
    }
}
