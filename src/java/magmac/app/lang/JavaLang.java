package magmac.app.lang;

import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.LocatingRule;
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

public final class JavaLang {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(List.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createRule("package", "package "),
                JavaLang.createRule("import", "import "),
                JavaLang.createStructureRule("record"),
                JavaLang.createStructureRule("interface"),
                JavaLang.createStructureRule("class"),
                JavaLang.createStructureRule("enum")
        ))));
    }

    private static Rule createStructureRule(String keyword) {
        Rule name = new StripRule(new SymbolRule(new StringRule("name")));
        Rule beforeContent = new OrRule(List.of(
                new StripRule(new SuffixRule(LocatingRule.First(name, "<", new StringRule("type-params")), ">")),
                name
        ));

        Rule withParameters = new OrRule(List.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", new StringRule("parameters")), ")")),
                beforeContent
        ));

        Rule withEnds = new OrRule(List.of(
                LocatingRule.First(withParameters, " extends ", new NodeRule("extended", JavaLang.createTypeRule())),
                withParameters
        ));

        Rule withImplements = new OrRule(List.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, " implements ", new NodeRule("implemented", JavaLang.createTypeRule()))),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = LocatingRule.First(withImplements, "{", new StripRule(new SuffixRule(DivideRule.Statements("children", JavaLang.createStructureMemberRule()), "}")));
        return new TypeRule(keyword, LocatingRule.First(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }

    private static OrRule createStructureMemberRule() {
        return new OrRule(List.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createDefinitionStatementRule(),
                JavaLang.createMethodRule()
        ));
    }

    private static Rule createDefinitionStatementRule() {
        return new TypeRule("definition-statement", new StripRule(new SuffixRule(new NodeRule("definition", JavaLang.createDefinitionRule()), ";")));
    }

    private static Rule createMethodRule() {
        return new TypeRule("method", LocatingRule.First(new NodeRule("header", new OrRule(List.of(
                JavaLang.createDefinitionRule(),
                new StringRule("name")
        ))), "(", new StringRule("with-params")));
    }

    private static Rule createDefinitionRule() {
        Rule leftRule = LocatingRule.Last(new StringRule("before-type"), " ", new NodeRule("type", JavaLang.createTypeRule()));
        return new StripRule(LocatingRule.Last(leftRule, " ", new StringRule("name")));
    }

    private static OrRule createTypeRule() {
        return new OrRule(List.of(
                JavaLang.createTemplateRule(),
                JavaLang.createSymbolTypeRule()
        ));
    }

    private static TypeRule createSymbolTypeRule() {
        return new TypeRule("symbol", new StripRule(new SymbolRule(new StringRule("value"))));
    }

    private static TypeRule createTemplateRule() {
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(new StripRule(new StringRule("base")), "<", new StringRule("arguments")), ">")));
    }

    public static Rule createRule(String type, String prefix) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        Rule stripRule = new StripRule(new SuffixRule(new PrefixRule(prefix, childRule), ";"));
        return new TypeRule(type, stripRule);
    }
}
