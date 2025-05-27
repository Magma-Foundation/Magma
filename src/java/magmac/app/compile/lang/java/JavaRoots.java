package magmac.app.compile.lang.java;

import magmac.app.compile.ast.Namespaced;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.SymbolRule;
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
        Rule name = new StripRule(new SymbolRule(new StringRule("name")));
        Rule beforeContent = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(name, "<", new StringRule("type-params")), ">")),
                name
        ));

        Rule withParameters = new OrRule(List.of(
                new StripRule(new SuffixRule(new InfixRule(beforeContent, "(", new StringRule("parameters")), ")")),
                beforeContent
        ));

        Rule withEnds = new OrRule(List.of(
                new InfixRule(withParameters, " extends ", new NodeRule("extended", JavaRoots.createTypeRule())),
                withParameters
        ));

        Rule withImplements = new OrRule(List.of(
                new ContextRule("With implements", new InfixRule(withEnds, " implements ", new NodeRule("implemented", JavaRoots.createTypeRule()))),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = new InfixRule(withImplements, "{", new StringRule("after-content"));
        return new TypeRule(keyword, new InfixRule(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }

    private static OrRule createTypeRule() {
        return new OrRule(List.of(
                JavaRoots.createTemplateRule(),
                JavaRoots.createSymbolTypeRule()
        ));
    }

    private static TypeRule createSymbolTypeRule() {
        return new TypeRule("symbol", new StripRule(new SymbolRule(new StringRule("value"))));
    }

    private static TypeRule createTemplateRule() {
        return new TypeRule("template", new StripRule(new SuffixRule(new InfixRule(new StripRule(new StringRule("base")), "<", new StringRule("arguments")), ">")));
    }
}
