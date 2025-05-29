package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.ContextRule;
import magmac.app.compile.rule.FilterRule;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
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
import magmac.app.lang.java.Whitespace;
import magmac.app.lang.java.define.Modifier;
import magmac.app.lang.java.function.FunctionSegment;
import magmac.app.lang.java.function.Parameters;
import magmac.app.lang.java.structure.StructureStatement;
import magmac.app.lang.java.value.Arguments;
import magmac.app.lang.java.value.Symbols;

public final class JavaLang {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
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
        Rule beforeContent = CommonLang.attachTypeParams(name);

        Rule withParameters = new OrRule(Lists.of(
                new StripRule(new SuffixRule(LocatingRule.First(beforeContent, "(", Parameters.createParametersRule(JavaLang.createDefinitionRule())), ")")),
                beforeContent
        ));

        Rule extended = new NodeListRule("extended", new ValueFolder(), CommonLang.createTypeRule());
        Rule withEnds = new OrRule(Lists.of(
                LocatingRule.First(withParameters, " extends ", extended),
                withParameters
        ));

        Rule implemented = new NodeListRule("implemented", new ValueFolder(), CommonLang.createTypeRule());
        Rule withImplements = new OrRule(Lists.of(
                new ContextRule("With implements", LocatingRule.First(withEnds, " implements ", implemented)),
                new ContextRule("Without implements", withEnds)
        ));

        Rule afterKeyword = LocatingRule.First(withImplements, "{", new StripRule(new SuffixRule(CommonLang.Statements("children", JavaLang.createClassMemberRule()), "}")));
        return new TypeRule(keyword, LocatingRule.First(Modifier.createModifiersRule(), keyword + " ", afterKeyword));
    }

    private static Rule createClassMemberRule() {
        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule valueLazy = new MutableLazyRule();
        LazyRule value = CommonLang.initValueRule(functionSegmentRule, valueLazy, "->", JavaLang.createDefinitionRule());
        Rule functionSegment = FunctionSegment.initFunctionSegmentRule(functionSegmentRule, value, JavaLang.createDefinitionRule());
        return new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                StructureStatement.createStructureStatementRule(new TypeRule("definition", JavaLang.createDefinitionRule()), value),
                JavaLang.createMethodRule(functionSegment),
                JavaLang.createEnumValuesRule(value)
        ));
    }

    private static TypeRule createEnumValuesRule(Rule value) {
        Rule name = new StripRule(FilterRule.Symbol(new StringRule("name")));
        Rule rule = new SuffixRule(LocatingRule.First(name, "(", Arguments.createArgumentsRule(value)), ")");
        Rule enumValue = new StripRule(new OrRule(Lists.of(
                Symbols.createSymbolRule("value"),
                rule
        )));

        Rule enumValues = new NodeListRule("children", new ValueFolder(), enumValue);
        Rule withEnd = new StripRule(new SuffixRule(enumValues, ";"));
        return new TypeRule("enum-values", new OrRule(Lists.of(
                withEnd,
                enumValues
        )));
    }

    private static Rule createMethodRule(Rule childRule) {
        NodeRule header = new NodeRule("header", new OrRule(Lists.of(
                JavaLang.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        Rule parameters = Parameters.createParametersRule(JavaLang.createDefinitionRule());
        Rule content = CommonLang.Statements("children", childRule);
        Rule rightRule = new StripRule(new PrefixRule("{", new SuffixRule(new StripRule("", content, "after-children"), "}")));
        Rule withParams = new OptionNodeListRule("parameters",
                new SuffixRule(parameters, ");"),
                LocatingRule.First(parameters, ")", rightRule)
        );

        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }

    private static Rule createNamespacedRule(String type) {
        Rule childRule = new NodeListRule("segments", new DelimitedFolder('.'), new StringRule("value"));
        return new TypeRule(type, new StripRule(new SuffixRule(new PrefixRule(type + " ", childRule), ";")));
    }

    private static Rule createDefinitionRule() {
        Rule modifiers = Modifier.createModifiersRule();
        Rule annotations = new NodeListRule("annotations", new DelimitedFolder('\n'), new StripRule(new PrefixRule("@", new StringRule("value"))));
        Rule beforeTypeParams = new OrRule(Lists.of(
                LocatingRule.Last(annotations, "\n", modifiers),
                modifiers
        ));

        Rule leftRule1 = CommonLang.attachTypeParams(beforeTypeParams);

        Rule rightRule = new NodeRule("type", CommonLang.createTypeRule());
        Divider divider = new FoldingDivider(new TypeSeparatorFolder());
        Splitter splitter = DividingSplitter.Last(divider, " ");
        Rule leftRule = new LocatingRule(leftRule1, splitter, rightRule);
        Rule stripRule = new StripRule(LocatingRule.Last(leftRule, " ", new StripRule(FilterRule.Symbol(new StringRule("name")))));
        return new TypeRule("definition", stripRule);
    }
}
