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
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;

public final class JavaLang {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
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
                LocatingRule.First(withParameters, " extends ", new NodeRule("extended", CommonLang.createTypeRule())),
                withParameters
        ));

        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, " implements ", new NodeRule("implemented", CommonLang.createTypeRule()))),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = LocatingRule.First(withImplements, "{", new StripRule(new SuffixRule(CommonLang.Statements("children", JavaLang.createStructureMemberRule()), "}")));
        return new TypeRule(keyword, LocatingRule.First(new StringRule("before-keyword"), keyword + " ", afterKeyword));
    }

    private static OrRule createStructureMemberRule() {
        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule valueLazy = new MutableLazyRule();
        LazyRule value = CommonLang.initValueRule(functionSegmentRule, valueLazy, "->");
        Rule functionSegment = CommonLang.initFunctionSegmentRule(functionSegmentRule, value);
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                JavaLang.createStructureStatementRule(value),
                JavaLang.createMethodRule(functionSegment)
        ));
    }

    private static Rule createStructureStatementRule(LazyRule value1) {
        Rule definition = new OrRule(Lists.of(
                new NodeRule("value", new TypeRule("definition", CommonLang.createDefinitionRule())),
                CommonLang.createAssignmentRule(value1))
        );

        return new TypeRule("statement", new StripRule(new SuffixRule(definition, ";")));
    }

    private static Rule createMethodRule(Rule childRule) {
        NodeRule header = new NodeRule("header", new OrRule(Lists.of(
                CommonLang.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        Rule parameters = CommonLang.createParametersRule(CommonLang.createDefinitionRule());
        Rule content = CommonLang.Statements("children", childRule);
        Rule rightRule = new StripRule(new PrefixRule("{", new SuffixRule(new StripRule("", content, "after-children"), "}")));
        Rule withParams = new OrRule(Lists.of(
                new SuffixRule(parameters, ");"),
                LocatingRule.First(parameters, ")", rightRule)
        ));

        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }

    private static Rule createNamespacedRule(String type) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }
}
