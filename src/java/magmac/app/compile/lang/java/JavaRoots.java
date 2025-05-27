package magmac.app.compile.lang.java;

import magmac.app.compile.ast.Namespaced;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.StatementFolder;

import java.util.List;

public final class JavaRoots {
    public static Rule createRule() {
        return new TypeRule("root", new DivideRule("children", new StatementFolder(), new OrRule(List.of(
                Namespaced.createRule("package", "package "),
                Namespaced.createRule("import", "import "),
                JavaRoots.createStructureRule("record"),
                JavaRoots.createStructureRule("interface"),
                JavaRoots.createStructureRule("class")
        ))));
    }

    private static Rule createStructureRule(String keyword) {
        Rule beforeContent = new StringRule("before-content");
        Rule leftRule = new OrRule(List.of(
                new InfixRule(beforeContent, " implements ", new StringRule("implemented")),
                beforeContent
        ));

        Rule afterKeyword = new InfixRule(leftRule, "{", new StringRule("after-content"));
        return new TypeRule(keyword, new InfixRule(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }
}
