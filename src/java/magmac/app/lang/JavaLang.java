package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.Splitter;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.Divider;
import magmac.app.compile.rule.divide.FoldingDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.compile.rule.split.DividingSplitter;

public final class JavaLang {
    public static Rule createRule() {
        return new TypeRule("root", DivideRule.Statements("children", new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createNamespacedRule("package"),
                JavaLang.createNamespacedRule("import"),
                JavaLang.createStructureRule("record"),
                JavaLang.createStructureRule("interface"),
                JavaLang.createStructureRule("class"),
                JavaLang.createStructureRule("enum")
        ))));
    }

    private static Rule createStructureRule(String keyword) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule beforeContent = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(name, "<", new StringRule("type-params")), ">")),
                name
        ));

        Rule withParameters = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", new StringRule("parameters")), ")")),
                beforeContent
        ));

        Rule withEnds = new OrRule(Lists.of(
                LocatingRule.First(withParameters, " extends ", new NodeRule("extended", JavaLang.createTypeRule())),
                withParameters
        ));

        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, " implements ", new NodeRule("implemented", JavaLang.createTypeRule()))),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = LocatingRule.First(withImplements, "{", new StripRule(new SuffixRule(DivideRule.Statements("children", JavaLang.createStructureMemberRule()), "}")));
        return new TypeRule(keyword, LocatingRule.First(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }

    private static OrRule createStructureMemberRule() {
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createStatementRule(),
                JavaLang.createMethodRule()
        ));
    }

    private static Rule createStatementRule() {
        Rule definition = new OrRule(Lists.of(
                new NodeRule("value", new TypeRule("definition", JavaLang.createDefinitionRule())),
                JavaLang.createAssignmentRule())
        );

        return new TypeRule("statement", new StripRule(new SuffixRule(definition, ";")));
    }

    private static Rule createAssignmentRule() {
        Rule definition = new NodeRule("definition", JavaLang.createDefinitionRule());
        Rule value = new NodeRule("value", JavaLang.createValueRule());
        return new TypeRule("assignment", LocatingRule.First(definition, "=", value));
    }

    private static Rule createValueRule() {
        return new OrRule(Lists.of(
                new StripRule(FilterRule.Number(new StringRule("value")))
        ));
    }

    private static Rule createMethodRule() {
        NodeRule header = new NodeRule("header", new OrRule(Lists.of(
                JavaLang.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        Rule parameters = new DivideRule("parameters", new ValueFolder(), new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createDefinitionRule()
        )));

        Rule withParams = LocatingRule.First(parameters, ")", new StringRule("with-braces"));
        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }

    private static Rule createDefinitionRule() {
        Rule leftRule1 = new StringRule("before-type");
        Rule rightRule = new NodeRule("type", JavaLang.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = new DividingSplitter(divider);
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        return new StripRule(LocatingRule.Last(leftRule, " ", new StringRule("name")));
    }

    private static Rule createTypeRule() {
        LazyRule orRule = new LazyRule();
        orRule.set(new OrRule(Lists.of(
                JavaLang.createVariadicRule(orRule),
                JavaLang.createArrayRule(orRule),
                CommonLang.createTemplateRule(),
                CommonLang.createSymbolTypeRule()
        )));
        return orRule;
    }

    private static TypeRule createArrayRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
    }

    private static TypeRule createVariadicRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("variadic", new StripRule(new SuffixRule(child, "...")));
    }

    private static Rule createNamespacedRule(String type) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }
}
