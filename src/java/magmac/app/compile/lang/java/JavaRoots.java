package magmac.app.compile.lang.java;

import magmac.app.compile.ast.Imports;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

import java.util.List;

public final class JavaRoots {
    public static Rule createRule() {
        return new TypeRule("root", new DivideRule("children", new OrRule(List.of(
                Imports.createImportRule(),
                new StringRule("value")
        ))));
    }
}
