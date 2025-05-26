package magmac.app;

import magmac.app.ast.Imports;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;

import java.util.List;

public class JavaRoots {
    public static Rule createRule() {
        return new DivideRule("children", new OrRule(List.of(
                Imports.createImportRule(),
                new StringRule("value")
        )));
    }
}
