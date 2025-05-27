package magmac.app.lang;

import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.InfixRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.SymbolRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;

import java.util.List;

public final class JavaRoots {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(List.of(
                JavaRoots.createRule("package", "package "),
                JavaRoots.createRule("import", "import "),
                JavaRoots.createStructureRule("record"),
                JavaRoots.createStructureRule("interface"),
                JavaRoots.createStructureRule("class"),
                JavaRoots.createStructureRule("enum")
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

        Rule afterKeyword = new InfixRule(withImplements, "{", new StripRule(new SuffixRule(DivideRule.Statements("children", JavaRoots.createStructureMemberRule()), "}")));
        return new TypeRule(keyword, new InfixRule(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }

    private static OrRule createStructureMemberRule() {
        return new OrRule(List.of(
                new StripRule(new ExactRule("")),
                JavaRoots.createDefinitionStatementRule(),
                JavaRoots.createMethodRule()
        ));
    }

    private static StripRule createDefinitionStatementRule() {
        return new StripRule(new SuffixRule(new NodeRule("definition", JavaRoots.createDefinitionRule()), ";"));
    }

    private static InfixRule createMethodRule() {
        return new InfixRule(new NodeRule("definition", JavaRoots.createDefinitionRule()), "(", new StringRule("with-params"));
    }

    private static Rule createDefinitionRule() {
        return new StringRule("definition");
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

    public static Rule createRule(String type, String prefix) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        Rule stripRule = new StripRule(new SuffixRule(new PrefixRule(prefix, childRule), ";"));
        return new TypeRule(type, stripRule);
    }
}
