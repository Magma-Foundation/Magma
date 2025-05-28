package magmac.app.lang;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.NodeListRule;
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
import magmac.app.lang.java.Symbol;

public final class TypescriptLang {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                TypescriptLang.createImportRule(),
                TypescriptLang.createClassRule("class"),
                TypescriptLang.createClassRule("interface")
        ))));
    }

    private static Rule createImportRule() {
        Rule segments = new SuffixRule(new NodeListRule("segments", new DelimitedFolder('/'), new StringRule("value")), "\";\n");
        Rule first = LocatingRule.First(new StringRule("child"), " } from \"", segments);
        return new TypeRule("import", new PrefixRule("import { ", first));
    }

    private static Rule createClassRule(String type) {
        Rule children = CommonLang.Statements("children", TypescriptLang.createStructureMemberRule());
        Rule name = new StringRule("name");
        Rule afterKeyword = LocatingRule.First(CommonLang.attachTypeParams(name), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule(type, new PrefixRule("export " + type + " ", afterKeyword));
    }

    private static Rule createStructureMemberRule() {
        Rule definitionRule = TypescriptLang.createDefinitionRule();
        LazyRule valueLazy = new MutableLazyRule();
        return new OrRule(Lists.of(
                CommonLang.createWhitespaceRule(),
                TypescriptLang.createMethodRule(definitionRule, valueLazy),
                CommonLang.createStructureStatementRule(definitionRule, valueLazy)
        ));
    }

    private static Rule createMethodRule(Rule definition, LazyRule valueLazy) {
        Rule header = new PrefixRule("\n\t", new NodeRule("header", new OrRule(Lists.of(
                definition,
                TypescriptLang.createConstructorRule(definition)
        ))));

        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule value = CommonLang.initValueRule(functionSegmentRule, valueLazy, " => ", definition);
        Rule children = CommonLang.Statements("children", CommonLang.initFunctionSegmentRule(functionSegmentRule, value, definition));
        Rule childRule = new SuffixRule(LocatingRule.First(header, " {", new StripRule("", children, "after-children")), "}");
        return new TypeRule("method", new OptionNodeListRule("children",
                childRule,
                new SuffixRule(header, ";")
        ));
    }

    private static Rule createConstructorRule(Rule definition) {
        NodeListRule parametersRule = CommonLang.createParametersRule(definition);
        return new TypeRule("constructor", new PrefixRule("constructor(", new SuffixRule(parametersRule, ")")));
    }

    private static Rule createDefinitionRule() {
        LazyRule definition = new MutableLazyRule();
        Rule modifiers = CommonLang.createModifiersRule();

        Rule parameters = CommonLang.createParametersRule(definition);
        Rule name = new StringRule("name");
        Rule leftRule = new OptionNodeListRule("parameters",
                new SuffixRule(LocatingRule.First(name, "(", parameters), ")"),
                name
        );

        Rule first = LocatingRule.First(leftRule, " : ", new NodeRule("type", TypescriptLang.createTypeRule()));
        return definition.set(new OptionNodeListRule("modifiers", LocatingRule.Last(modifiers, " ", first), first));
    }

    private static Rule createTypeRule() {
        LazyRule orRule = new MutableLazyRule();
        orRule.set(new OrRule(Lists.of(
                Symbol.createSymbolTypeRule(),
                CommonLang.createTemplateRule(),
                new TypeRule("array", new SuffixRule(new NodeRule("child", orRule), "[]"))
        )));
        return orRule;
    }
}
