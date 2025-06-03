package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.web.TypescriptLang;
import magmac.app.lang.node.CaseDefinition;
import magmac.app.lang.node.CaseValue;
import magmac.app.lang.node.CaseValues;
import magmac.app.lang.node.MultipleCaseValue;
import magmac.app.lang.node.SingleCaseValue;

public record JavaCaseNode(List<CaseDefinition> definitions, CaseValue value) implements JavaFunctionSegment, TypescriptLang.TypescriptFunctionSegment {
    public static Option<CompileResult<JavaCaseNode>> deserialize(Node node) {
        return Destructors.destructWithType("case", node).map(destructor -> {
            return destructor
                    .withNodeList("definitions", CaseDefinition::deserialize)
                    .withNode("value", CaseValues::deserializeOrError)
                    .complete(tuple -> new JavaCaseNode(tuple.left(), tuple.right()));
        });
    }

    public static Rule createCaseRule(Rule value, Rule segment) {
        Rule typeRule = JavaLang.JavaTypes.createTypeRule();
        Rule name = new StripRule(new StringRule("name"));
        Rule last = LocatingRule.Last(new NodeRule("type", typeRule), " ", name);
        Rule definitions = new TypeRule("case-definition", new OrRule(Lists.of(
                new StripRule(last),
                name
        )));

        Rule beforeArrow = NodeListRule.Values("definitions", definitions);
        OrRule children = new OrRule(Lists.of(
                SingleCaseValue.createRule(value),
                MultipleCaseValue.createRule(segment)
        ));

        Rule childRule = LocatingRule.First(beforeArrow, "->", new NodeRule("value", children));
        return new TypeRule("case", new StripRule(new PrefixRule("case", childRule)));
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
