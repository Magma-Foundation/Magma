package magmac.app.lang.node;

import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.JavaFunctionSegmentValue;

public record JavaReturnNode(JavaValue child) implements JavaFunctionSegmentValue, JavaFunctionSegment {
    public static Rule createReturnRule(Rule value) {
        return new TypeRule("return", new StripRule(new PrefixRule("return ", new NodeRule("child", value))));
    }
}
