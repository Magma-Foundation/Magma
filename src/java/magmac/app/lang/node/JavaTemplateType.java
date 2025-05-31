package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

public final class JavaTemplateType implements JavaType {
    public final JavaBase base;
    public final TypeArguments<JavaType> typeArguments;

    public JavaTemplateType(JavaBase base, TypeArguments<JavaType> typeArguments) {
        this.base = base;
        this.typeArguments = typeArguments;
    }

    public static Option<CompileResult<JavaTemplateType>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "template").map(deserializer -> deserializer
                .withNode("base", JavaTemplateType::deserializeBase)
                .withNodeList("arguments", Types::deserialize)
                .complete(tuple -> new JavaTemplateType(tuple.left(), new TypeArguments<JavaType>(tuple.right()))));
    }

    private static CompileResult<JavaBase> deserializeBase(Node node) {
        return Deserializers.orError("base", node, Lists.of(
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(QualifiedType::deserializeQualified)
        ));
    }

    public static Rule createTemplateRule(Rule type) {
        Rule base = new NodeRule("base", new OrRule(Lists.of(
                Symbols.createSymbolRule(),
                QualifiedType.createQualifiedRule()
        )));

        Rule arguments = NodeListRule.Values("arguments", type);
        return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(base, "<", arguments), ">")));
    }
}
