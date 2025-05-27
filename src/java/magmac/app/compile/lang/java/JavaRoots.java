package magmac.app.compile.lang.java;

import magmac.app.compile.ast.Namespaced;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;

import java.util.List;

public final class JavaRoots {
    public static Rule createRule() {
        return new TypeRule("root", new DivideRule("children", new OrRule(List.of(
                Namespaced.createRule("package", "package "),
                Namespaced.createRule("import", "import "),
                JavaRoots.createStructureRule("record "),
                JavaRoots.createStructureRule("interface "),
                JavaRoots.createStructureRule("class ")
        ))));
    }

    private static InfixRule createStructureRule(String infix) {
        return new InfixRule(new StringRule("left"), infix, new StringRule("right"));
    }
}
