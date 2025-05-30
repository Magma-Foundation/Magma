package magmac.app.lang.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.Deserializers;

public final class JavaRootSegments {
    public static CompileResult<JavaRootSegment> deserialize(Node node) {
        return Deserializers.orError("root-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                JavaNamespacedNode::deserialize,
                Deserializers.wrap(new JavaStructureNodeDeserializer(StructureType.Class)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(StructureType.Interface)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(StructureType.Record)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(StructureType.Enum))
        ));
    }

    public static Node serialize(JavaRootSegment segment) {
        return new MapNode();
    }

    static Rule getChildRule() {
        return new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                JavaNamespacedNode.createNamespacedRule("package"),
                JavaNamespacedNode.createNamespacedRule("import"),
                Structures.createStructureRule("record"),
                Structures.createStructureRule("interface"),
                Structures.createStructureRule("class"),
                Structures.createStructureRule("enum")
        ));
    }
}
