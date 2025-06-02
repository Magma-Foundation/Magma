package magmac.app.lang;

import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.node.JavaTypes;

public class JavaRules {
    public static Rule createConstructionRule() {
        return new TypeRule("construction", new StripRule(new PrefixRule("new ", new NodeRule("type", JavaTypes.createTypeRule()))));
    }
}
