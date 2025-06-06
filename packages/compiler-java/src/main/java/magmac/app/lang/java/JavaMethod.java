package magmac.app.lang.java;

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
import magmac.app.lang.Destructors;
import magmac.app.lang.JavaRules;
import magmac.app.lang.OptionNodeListRule;
import magmac.app.lang.node.FunctionSegments;

public record JavaMethod(
        JavaMethodHeader header,
        List<JavaParameter> parameters,
        Option<List<JavaFunctionSegment>> maybeChildren
) implements JavaStructureMember {
    public static Option<CompileResult<JavaStructureMember>> deserialize(Node node) {
        return Destructors.destructWithType("method", node).map((InitialDestructor deserializer) -> deserializer
                .withNode("header", JavaMethodHeader::deserializeError)
                .withNodeListOptionally("children", FunctionSegments::deserialize)
                .withNodeList("parameters", JavaDeserializers::deserializeParameter)
                .complete((tuple) -> new JavaMethod(tuple.left().left(), tuple.right(), tuple.left().right()))
                .mapValue((JavaMethod type) -> type));
    }

    public static Rule createMethodRule(Rule childRule) {
        var header = new NodeRule("header", new OrRule(Lists.of(
                JavaRules.createDefinitionRule(),
                new TypeRule("constructor", new StripRule(FilterRule.Symbol(new StringRule("name"))))
        )));

        var parameters = JavaRules.createParametersRule(JavaRules.createDefinitionRule());
        var content = CommonLang.Statements("children", childRule);
        Rule rightRule = new StripRule(new PrefixRule("{", new SuffixRule(new StripRule(content), "}")));
        Rule withParams = new OptionNodeListRule("parameters",
                new SuffixRule(parameters, ");"),
                LocatingRule.First(parameters, ")", rightRule)
        );

        return new TypeRule("method", LocatingRule.First(header, "(", withParams));
    }
}
