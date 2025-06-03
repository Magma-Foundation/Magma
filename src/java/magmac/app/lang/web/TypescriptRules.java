package magmac.app.lang.web;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.CommonRules;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.OptionNodeListRule;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaStructureStatement;
import magmac.app.lang.node.FunctionSegments;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.Parameters;

public class TypescriptRules {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                JavaRules.createWhitespaceRule(),
                TypescriptLang.TypeScriptImport.createImportRule(),
                TypescriptLang.TypescriptStructureNode.createStructureRule("class"),
                TypescriptLang.TypescriptStructureNode.createStructureRule("interface")
        ))));
    }

    public static Rule createStructureMemberRule() {
        Rule definitionRule = createDefinitionRule();
        LazyRule valueLazy = new MutableLazyRule();
        return new OrRule(Lists.of(
                JavaRules.createWhitespaceRule(),
                createMethodRule(definitionRule, valueLazy),
                JavaStructureStatement.createStructureStatementRule(definitionRule, valueLazy)
        ));
    }

    private static Rule createMethodRule(Rule definition, LazyRule valueLazy) {
        Rule header = new PrefixRule("\n\t", new NodeRule("header", new OrRule(Lists.of(
                definition,
                createConstructorRule(definition)
        ))));

        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule value = JavaRules.initValueRule(functionSegmentRule, valueLazy, " => ", definition);
        Rule children = CommonLang.Statements("children", FunctionSegments.initFunctionSegmentRule(functionSegmentRule, value, definition));
        Rule childRule = new SuffixRule(LocatingRule.First(header, " {", new StripRule("", children, "after-children")), "}");
        return new TypeRule("method", new OptionNodeListRule("children",
                childRule,
                new SuffixRule(header, ";")
        ));
    }

    private static Rule createConstructorRule(Rule definition) {
        Rule parametersRule = Parameters.createParametersRule(definition);
        return new TypeRule("constructor", new PrefixRule("constructor(", new SuffixRule(parametersRule, ")")));
    }

    private static Rule createDefinitionRule() {
        LazyRule definition = new MutableLazyRule();
        Rule modifiers = Modifier.createModifiersRule();

        Rule parameters = Parameters.createParametersRule(definition);
        Rule name = new StringRule("name");
        Rule leftRule = new OptionNodeListRule("parameters",
                new SuffixRule(LocatingRule.First(name, "(", parameters), ")"),
                name
        );

        Rule first = LocatingRule.First(leftRule, " : ", new NodeRule("type", createTypeRule()));
        return definition.set(new OptionNodeListRule("modifiers", LocatingRule.Last(modifiers, " ", first), first));
    }

    private static Rule createTypeRule() {
        LazyRule type = new MutableLazyRule();
        return type.set(new OrRule(Lists.of(
                JavaLang.JavaTemplateType.createTemplateRule(type),
                TypescriptLang.createArrayRule(type),
                CommonRules.createSymbolRule()
        )));
    }
}
