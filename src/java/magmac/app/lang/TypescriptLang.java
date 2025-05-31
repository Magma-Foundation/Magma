package magmac.app.lang;

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
import magmac.app.lang.node.FunctionSegment;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.Parameters;
import magmac.app.lang.node.StructureStatement;
import magmac.app.lang.node.Symbols;
import magmac.app.lang.node.JavaTemplateType;
import magmac.app.lang.node.TypeScriptImport;
import magmac.app.lang.node.TypescriptStructureNode;
import magmac.app.lang.node.Values;
import magmac.app.lang.node.Whitespace;

public final class TypescriptLang {
    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                TypeScriptImport.createImportRule(),
                TypescriptStructureNode.createClassRule("class"),
                TypescriptStructureNode.createClassRule("interface")
        ))));
    }

    public static Rule createStructureMemberRule() {
        Rule definitionRule = TypescriptLang.createDefinitionRule();
        LazyRule valueLazy = new MutableLazyRule();
        return new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                TypescriptLang.createMethodRule(definitionRule, valueLazy),
                StructureStatement.createStructureStatementRule(definitionRule, valueLazy)
        ));
    }

    private static Rule createMethodRule(Rule definition, LazyRule valueLazy) {
        Rule header = new PrefixRule("\n\t", new NodeRule("header", new OrRule(Lists.of(
                definition,
                TypescriptLang.createConstructorRule(definition)
        ))));

        LazyRule functionSegmentRule = new MutableLazyRule();
        LazyRule value = Values.initValueRule(functionSegmentRule, valueLazy, " => ", definition);
        Rule children = CommonLang.Statements("children", FunctionSegment.initFunctionSegmentRule(functionSegmentRule, value, definition));
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

        Rule first = LocatingRule.First(leftRule, " : ", new NodeRule("type", TypescriptLang.createTypeRule()));
        return definition.set(new OptionNodeListRule("modifiers", LocatingRule.Last(modifiers, " ", first), first));
    }

    private static Rule createTypeRule() {
        LazyRule type = new MutableLazyRule();
        return type.set(new OrRule(Lists.of(
                JavaTemplateType.createTemplateRule(type),
                TypescriptLang.createArrayRule(type),
                Symbols.createSymbolRule()
        )));
    }

    private static TypeRule createArrayRule(LazyRule orRule) {
        return new TypeRule("array", new SuffixRule(new NodeRule("child", orRule), "[]"));
    }
}
