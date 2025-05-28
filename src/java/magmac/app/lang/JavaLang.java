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
        LazyRule value = CommonLang.initValueRule(functionSegmentRule, valueLazy, "->", JavaLang.createDefinitionRule());
        Rule functionSegment = CommonLang.initFunctionSegmentRule(functionSegmentRule, value, JavaLang.createDefinitionRule());
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                CommonLang.createStructureStatementRule(new TypeRule("definition", JavaLang.createDefinitionRule()), value),
                JavaLang.createMethodRule(functionSegment)
        ));
    }

    private static Rule createMethodRule(Rule childRule) {
        NodeRule header = new NodeRule("header", new OrRule(Lists.of(
                JavaLang.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        Rule parameters = CommonLang.createParametersRule(JavaLang.createDefinitionRule());
        Rule content = CommonLang.Statements("children", childRule);
        Rule rightRule = new StripRule(new PrefixRule("{", new SuffixRule(new StripRule("", content, "after-children"), "}")));
        Rule withParams = new OptionNodeListRule("parameters",
                new SuffixRule(parameters, ");"),
                LocatingRule.First(parameters, ")", rightRule)
        );

        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }

    private static Rule createNamespacedRule(String type) {
        Rule childRule = new DivideRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }

    private static Rule createDefinitionRule() {
        Rule modifiers = CommonLang.createModifiersRule();
        Rule annotations = new DivideRule("annotations", new DelimitedFolder('\n'), new StripRule(new PrefixRule("@", new StringRule("value"))));
        Rule beforeTypeParams = new OrRule(Lists.of(
                LocatingRule.Last(annotations, "\n", modifiers),
                modifiers
        ));

        Rule typeParams = new DivideRule("type-parameters", new ValueFolder(), new StringRule("value"));
        Rule leftRule1 = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeTypeParams, "<", typeParams), ">")),
                beforeTypeParams
        ));

        Rule rightRule = new NodeRule("type", CommonLang.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = DividingSplitter.Last(divider, " ");
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        return new StripRule(LocatingRule.Last(leftRule, " ", new StringRule("name")));
    }

}
