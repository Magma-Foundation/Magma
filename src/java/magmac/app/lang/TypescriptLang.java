package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.DivideRule;
import magmac.app.compile.rule.ExactRule;
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

public final class TypescriptLang {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                TypescriptLang.createImportRule(),
                TypescriptLang.createClassRule("class"),
                TypescriptLang.createClassRule("interface")
        ))));
    }

    private static TypeRule createImportRule() {
        Rule segments = new SuffixRule(new DivideRule("segments", new DelimitedFolder('/'), new StringRule("value")), "\";\n");
        Rule first = LocatingRule.First(new StringRule("child"), " } from \"", segments);
        return new TypeRule("import", new PrefixRule("import { ", first));
    }

    private static Rule createClassRule(String type) {
        Rule children = CommonLang.Statements("children", TypescriptLang.createStructureMemberRule());
        Rule name = LocatingRule.First(new StringRule("name"), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule(type, new PrefixRule("export " + type + " ", name));
    }

    private static Rule createStructureMemberRule() {
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                TypescriptLang.createMethodRule(),
                new TypeRule("statement", new ExactRule("\n\ttemp : ?;"))
        ));
    }

    private static TypeRule createMethodRule() {
        Rule header = new OrRule(Lists.of(
                TypescriptLang.createDefinitionRule(),
                TypescriptLang.createConstructorRule()
        ));

        PrefixRule header1 = new PrefixRule("\n\t", new NodeRule("header", header));
        DivideRule children = CommonLang.Statements("children", TypescriptLang.createFunctionSegmentRule());
        SuffixRule childRule = new SuffixRule(LocatingRule.First(header1, " {", new StripRule("", children, "after-children")), "}");
        return new TypeRule("method", new OptionNodeListRule("children",
                childRule,
                new SuffixRule(header1, ";")
        ));
    }

    private static Rule createFunctionSegmentRule() {
        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule valueLazy = new MutableLazyRule();
        LazyRule value = CommonLang.initValueRule(functionSegmentRule, valueLazy, " => ", TypescriptLang.createDefinitionRule());
        return CommonLang.initFunctionSegmentRule(functionSegmentRule, value);
    }

    private static TypeRule createConstructorRule() {
        DivideRule parametersRule = CommonLang.createParametersRule(TypescriptLang.createDefinitionRule());
        return new TypeRule("constructor", new PrefixRule("constructor(", new SuffixRule(parametersRule, ")")));
    }

    private static Rule createDefinitionRule() {
        LazyRule definition = new MutableLazyRule();
        DivideRule parameters = CommonLang.createParametersRule(definition);
        Rule name = new StringRule("name");
        Rule leftRule = new OrRule(Lists.of(
                new SuffixRule(LocatingRule.First(name, "(", parameters), ")"),
                name
        ));

        return definition.set(LocatingRule.First(leftRule, " : ", new NodeRule("type", TypescriptLang.createTypeRule())));
    }

    private static Rule createTypeRule() {
        return new OrRule(Lists.of(
                CommonLang.createSymbolTypeRule(),
                CommonLang.createTemplateRule()
        ));
    }
}
