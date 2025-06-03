package magmac.app.lang.web;

import magmac.api.collect.list.Lists;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.DelimitedDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.CommonLang;
import magmac.app.lang.CommonRules;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.OptionNodeListRule;
import magmac.app.lang.java.JavaStructureStatement;
import magmac.app.lang.node.FunctionSegments;
import magmac.app.lang.node.Modifier;

public class TypescriptRules {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                JavaRules.createWhitespaceRule(),
                createImportRule(),
                createStructureRule("class"),
                createStructureRule("interface")
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
        Rule parametersRule = JavaRules.createParametersRule(definition);
        return new TypeRule("constructor", new PrefixRule("constructor(", new SuffixRule(parametersRule, ")")));
    }

    private static Rule createDefinitionRule() {
        LazyRule definition = new MutableLazyRule();
        Rule modifiers = Modifier.createModifiersRule();

        Rule parameters = JavaRules.createParametersRule(definition);
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
                JavaRules.createTemplateRule(type),
                createArrayRule(type),
                CommonRules.createSymbolRule()
        )));
    }

    public static Rule createStructureRule(String type) {
        Rule children = CommonLang.Statements("members", createStructureMemberRule());
        Rule name = new StringRule("name");
        Rule afterKeyword = LocatingRule.First(JavaRules.attachTypeParams(name), " {", new SuffixRule(children, "\n}\n"));
        return new TypeRule(type, new PrefixRule("export " + type + " ", afterKeyword));
    }

    public static Rule createImportRule() {
        Rule segments = new SuffixRule(NodeListRule.createNodeListRule("segments", new DelimitedFolder('/'), new StringRule("value")), "\";\n");
        Rule leftRule = new NodeListRule("values", new StringRule("value"), new DelimitedDivider(", "));
        Rule first = LocatingRule.First(leftRule, " } from \"", segments);
        return new TypeRule("import", new PrefixRule("import { ", first));
    }

    public static TypeRule createArrayRule(LazyRule orRule) {
        return new TypeRule("array", new SuffixRule(new NodeRule("child", orRule), "[]"));
    }
}
