package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.JavaConstruction;
import magmac.app.lang.java.JavaInvokable;
import magmac.app.lang.java.Lang;
import magmac.app.lang.node.Arguments;
import magmac.app.lang.node.JavaBlockHeader;
import magmac.app.lang.node.Catch;
import magmac.app.lang.node.Conditional;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.Else;
import magmac.app.lang.node.FunctionSegmentValues;
import magmac.app.lang.node.FunctionStatement;
import magmac.app.lang.node.JavaAccess;
import magmac.app.lang.node.JavaAccessType;
import magmac.app.lang.node.JavaLambda;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.JavaPost;
import magmac.app.lang.node.JavaReturnNode;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.JavaStructureNodeDeserializer;
import magmac.app.lang.node.JavaStructureType;
import magmac.app.lang.node.JavaTypes;
import magmac.app.lang.node.JavaYieldNode;
import magmac.app.lang.node.LambdaContents;
import magmac.app.lang.node.LambdaHeaders;
import magmac.app.lang.node.PostVariant;
import magmac.app.lang.node.Try;
import magmac.app.lang.node.TypedDeserializer;
import magmac.app.lang.node.Values;
import magmac.app.lang.node.Whitespace;

public final class JavaDeserializers {
    public static CompileResult<Lang.JavaCaller> deserialize(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeConstruction),
                Deserializers.wrap(Values::deserialize)
        ));
    }

    public static Option<CompileResult<Lang.JavaCaller>> deserializeConstruction(Node node) {
        return Destructors.destructWithType("construction", node)
                .map(deserializer -> deserializer.withNode("type", JavaTypes::deserialize)
                        .complete(JavaConstruction::new));
    }

    public static Option<CompileResult<JavaInvokable>> deserializeInvocation(Node node) {
        return Destructors.destructWithType("invokable", node).map(deserializer -> deserializer.withNode("caller", JavaDeserializers::deserialize)
                .withNodeList("arguments", Arguments::deserialize)
                .complete(tuple -> new JavaInvokable(tuple.left(), tuple.right())));
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

    public static Option<CompileResult<JavaAccess>> deserializeAccess(JavaAccessType type, Node node) {
        return Destructors.destructWithType(type.type(), node)
                .map(deserializer -> deserializer.withString("property")
                        .withNode("receiver", Values::deserializeOrError)
                        .complete(tuple -> new JavaAccess(type, tuple.right(), tuple.left())));
    }

    public static Option<CompileResult<JavaYieldNode>> deserializeYield(Node node) {
        return Destructors.destructWithType("yield", node)
                .map(deserializer -> deserializer.withNode("value", Values::deserializeOrError)
                        .complete(JavaYieldNode::new));
    }

    public static Option<CompileResult<JavaPost>> deserializePost(PostVariant variant, Node node) {
        return Destructors.destructWithType(variant.type(), node).map(deserializer -> deserializer
                .withNode("child", Values::deserializeOrError)
                .complete(child -> new JavaPost(variant, child)));
    }

    public static Option<CompileResult<JavaLambda>> deserializeLambda(Node node) {
        return Destructors.destructWithType("lambda", node).map(deserializer -> deserializer
                .withNode("header", LambdaHeaders::deserialize)
                .withNode("content", LambdaContents::deserialize)
                .complete(tuple -> new JavaLambda(tuple.left(), tuple.right())));
    }

    public static TypedDeserializer<JavaAccess> deserializeAccessWithType(JavaAccessType type) {
        return node1 -> JavaDeserializers.deserializeAccess(type, node1);
    }

    public static Option<CompileResult<FunctionStatement>> deserializeFunctionStatement(Node node) {
        return Destructors.destructWithType("statement", node)
                .map(deserializer -> deserializer.withNode("child", FunctionSegmentValues::deserialize)
                        .complete(FunctionStatement::new)
                        .mapValue(value -> value));
    }

    public static Option<CompileResult<JavaReturnNode>> deserializeReturn(Node node) {
        return Destructors.destructWithType("return", node)
                .map(deserializer -> deserializer.withNode("child", Values::deserializeOrError).complete(JavaReturnNode::new));
    }

    public static CompileResult<JavaBlockHeader> deserializeBlockHeader(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(node1 -> Conditional.deserialize(ConditionalType.If, node1)),
                Deserializers.wrap(node1 -> Conditional.deserialize(ConditionalType.While, node1)),
                Deserializers.wrap(Else::deserialize),
                Deserializers.wrap(Try::deserialize),
                Deserializers.wrap(Catch::deserialize)
        ));
    }
}
