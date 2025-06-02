package magmac.app.lang.node;

import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.JavaRules;

public class Roots {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", JavaRules.createRootSegmentRule()));
    }
}
