package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.Node;
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
import magmac.app.lang.CommonLang;
import magmac.app.lang.OptionNodeListRule;
import magmac.app.lang.Deserializers;

public record MethodNode(MethodHeader methodHeader, Option<List<FunctionSegment>> maybeChildren, List<Parameter> right) implements JavaStructureMember {
    public static Option<CompileResult<JavaStructureMember>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "method").map((InitialDestructor deserializer) -> deserializer
                .withNode("header", MethodHeader::deserializeError)
                .withNodeListOptionally("children", FunctionSegment::deserialize)
                .withNodeList("parameters", Parameters::deserialize)
                .complete((tuple) -> new MethodNode(tuple.left().left(), tuple.left().right(), tuple.right()))
                .mapValue((MethodNode type) -> type));
    }

    public static Rule createMethodRule(Rule childRule) {
        NodeRule header = new NodeRule("header", new OrRule(Lists.of(
                Definition.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        Rule parameters = Parameters.createParametersRule(Definition.createDefinitionRule());
        Rule content = CommonLang.Statements("children", childRule);
        Rule rightRule = new StripRule(new PrefixRule("{", new SuffixRule(new StripRule("", content, "after-children"), "}")));
        Rule withParams = new OptionNodeListRule("parameters",
                new SuffixRule(parameters, ");"),
                LocatingRule.First(parameters, ")", rightRule)
        );

        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }
}
