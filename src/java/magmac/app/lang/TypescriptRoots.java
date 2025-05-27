package magmac.app.lang;

import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;

import java.util.List;

public class TypescriptRoots {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(List.of(
                new TypeRule("import", new ExactRule("")),
                new TypeRule("class", new ExactRule("")),
                new TypeRule("interface", new ExactRule("")),
                new TypeRule("enum", new ExactRule(""))
        ))));
    }
}
