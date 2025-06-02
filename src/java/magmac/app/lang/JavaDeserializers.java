package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.node.Arguments;
import magmac.app.lang.node.InvokableNode;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.JavaStructureNodeDeserializer;
import magmac.app.lang.node.JavaStructureType;
import magmac.app.lang.node.JavaTypes;
import magmac.app.lang.node.Values;
import magmac.app.lang.node.Whitespace;

public final class JavaDeserializers {
    public static CompileResult<JavaLang.Caller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeConstruction),
                Deserializers.wrap(Values::deserialize)
        ));
    }

    public static Option<CompileResult<JavaLang.Caller>> deserializeConstruction(Node node) {
        return Destructors.destructWithType("construction", node)
                .map(deserializer -> deserializer.withNode("type", JavaTypes::deserialize)
                        .complete(JavaLang.Construction::new));
    }

    public static Option<CompileResult<InvokableNode>> deserializeInvocation(Node node) {
        return Destructors.destructWithType("invokable", node).map(deserializer -> deserializer.withNode("caller", JavaDeserializers::deserialize)
                .withNodeList("arguments", Arguments::deserialize)
                .complete(tuple -> new InvokableNode(tuple.left(), tuple.right())));
    }

    public static CompileResult<JavaRootSegment> deserializeRootSegment(Node node) {
        return Deserializers.orError("root-segment", node, Lists.of(
                Deserializers.wrap(Whitespace::deserialize),
                JavaNamespacedNode::deserialize,
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Class)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Interface)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Record)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Enum))
        ));
    }
}
