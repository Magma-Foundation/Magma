package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.Node;
import magmac.app.lang.Deserializers;
import magmac.app.lang.Destructors;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.node.Assignables;
import magmac.app.lang.node.CaseDefinition;
import magmac.app.lang.node.CaseValues;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.FunctionSegmentValues;
import magmac.app.lang.node.FunctionSegments;
import magmac.app.lang.node.LambdaContents;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.OperationDeserializer;
import magmac.app.lang.node.Operator;
import magmac.app.lang.node.PostVariant;
import magmac.app.lang.node.StructureStatementValue;
import magmac.app.lang.node.TypedDeserializer;
import magmac.app.lang.web.TypescriptLang;

import java.util.function.Function;
import java.util.function.Supplier;

import static magmac.app.lang.java.JavaLang.*;

public final class JavaDeserializers {

    private static <T> Option<CompileResult<T>> simpleNode(String type, Node node, Supplier<T> factory) {
        return Destructors.destructWithType(type, node)
                .map(destructor -> destructor.complete(factory));
    }

    private static <T> Option<CompileResult<T>> simpleValueNode(String type, Node node, Function<String, T> factory) {
        return Destructors.destructWithType(type, node)
                .map(destructor -> destructor.withString("value").complete(factory));
    }

    @SuppressWarnings("unchecked")
    private static <T> CompileResult<T> commonWrap(String type, Node node, TypedDeserializer<? extends T> tail) {
        return Deserializers.orError(type, node, Lists.of(
                (TypedDeserializer<T>) Deserializers.wrap(JavaDeserializers::deserializeWhitespace),
                (TypedDeserializer<T>) Deserializers.wrap(JavaDeserializers::deserializeComment),
                (TypedDeserializer<T>) Deserializers.wrap(tail)
        ));
    }
    private static CompileResult<JavaCaller> deserializeCaller(Node node) {
        return Deserializers.orError("caller", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeConstruction),
                Deserializers.wrap(JavaDeserializers::deserializeValue)
        ));
    }

    private static Option<CompileResult<JavaCaller>> deserializeConstruction(Node node) {
        return Destructors.destructWithType("construction", node)
                .map(deserializer -> deserializer.withNode("type", JavaDeserializers::deserializeType)
                        .complete(Construction::new));
    }

    public static Option<CompileResult<JavaLang.Invokable>> deserializeInvocation(Node node) {
        return Destructors.destructWithType("invokable", node).map(deserializer -> deserializer.withNode("caller", JavaDeserializers::deserializeCaller)
                .withNodeList("arguments", JavaDeserializers::deserializeArguments)
                .complete(tuple -> new JavaLang.Invokable(tuple.left(), tuple.right())));
    }

    public static CompileResult<JavaRootSegment> deserializeRootSegment(Node node) {
        return Deserializers.orError("root-segment", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeWhitespace),
                Deserializers.wrap(JavaDeserializers::deserializeComment),
                JavaNamespacedNode::deserialize,
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Class)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Interface)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Record)),
                Deserializers.wrap(new JavaStructureNodeDeserializer(JavaStructureType.Enum))
        ));
    }

    private static Option<CompileResult<Access>> deserializeAccess(JavaAccessType type, Node node) {
        return Destructors.destructWithType(type.type(), node)
                .map(deserializer -> deserializer.withString("property")
                        .withNode("receiver", JavaDeserializers::deserializeValueOrError)
                        .withNodeListOptionally("type-arguments", JavaDeserializers::deserializeType)
                        .complete(tuple -> new Access(type, tuple.left().right(), tuple.right(), tuple.left().left())));
    }

    public static Option<CompileResult<JavaYieldNode>> deserializeYield(Node node) {
        return Destructors.destructWithType("yield", node)
                .map(deserializer -> deserializer.withNode("value", JavaDeserializers::deserializeValueOrError)
                        .complete(JavaYieldNode::new));
    }

    public static Option<CompileResult<JavaPost>> deserializePost(PostVariant variant, Node node) {
        return Destructors.destructWithType(variant.type(), node).map(deserializer -> deserializer
                .withNode("child", JavaDeserializers::deserializeValueOrError)
                .complete(child -> new JavaPost(variant, child)));
    }

    private static Option<CompileResult<Lambda>> deserializeLambda(Node node) {
        return Destructors.destructWithType("lambda", node).map(deserializer -> deserializer
                .withNode("header", JavaDeserializers::deserializeLambdaHeader)
                .withNode("content", LambdaContents::deserialize)
                .complete(tuple -> new Lambda(tuple.left(), tuple.right())));
    }

    private static TypedDeserializer<Access> deserializeAccessWithType(JavaAccessType type) {
        return node1 -> JavaDeserializers.deserializeAccess(type, node1);
    }

    public static Option<CompileResult<FunctionStatement>> deserializeFunctionStatement(Node node) {
        return Destructors.destructWithType("statement", node)
                .map(deserializer -> deserializer.withNode("child", FunctionSegmentValues::deserialize)
                        .complete(FunctionStatement::new)
                        .mapValue(value -> value));
    }

    public static Option<CompileResult<Return>> deserializeReturn(Node node) {
        return Destructors.destructWithType("return", node)
                .map(deserializer -> deserializer.withNode("child", JavaDeserializers::deserializeValueOrError).complete(Return::new));
    }

    private static CompileResult<BlockHeader> deserializeBlockHeader(Node node) {
        return Deserializers.orError("header", node, Lists.of(
                Deserializers.wrap(node1 -> JavaDeserializers.deserializeConditional(ConditionalType.If, node1)),
                Deserializers.wrap(node1 -> JavaDeserializers.deserializeConditional(ConditionalType.While, node1)),
                Deserializers.wrap(JavaDeserializers::deserializeElse),
                Deserializers.wrap(JavaDeserializers::deserializeTry),
                Deserializers.wrap(Catch::deserialize)
        ));
    }

    public static Option<CompileResult<Block>> deserializeBlock(Node node) {
        return Destructors.destructWithType("block", node).map(deserializer -> deserializer
                .withNodeList("children", FunctionSegments::deserialize)
                .withNode("header", JavaDeserializers::deserializeBlockHeader)
                .complete(tuple -> new Block(tuple.right(), tuple.left())));
    }

    private static Option<CompileResult<Conditional>> deserializeConditional(ConditionalType type, Node node) {
        return Destructors.destructWithType(type.text(), node).map(deserializer -> deserializer.withNode("condition", JavaDeserializers::deserializeValueOrError)
                .complete(value -> new Conditional(type, value)));
    }

    public static CompileResult<StructureStatementValue> deserializeStructureStatement(Node node) {
        return Deserializers.orError("structure-statement-value", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeTypedDefinition),
                Deserializers.wrap(JavaDeserializers::deserializeAssignment)
        ));
    }

    public static Option<CompileResult<JavaBreak>> deserializeBreak(Node node) {
        return JavaDeserializers.simpleNode("break", node, JavaBreak::new);
    }

    public static Option<CompileResult<JavaContinue>> deserializeContinue(Node node) {
        return JavaDeserializers.simpleNode("continue", node, JavaContinue::new);
    }

    public static Option<CompileResult<Whitespace>> deserializeWhitespace(Node node) {
        return JavaDeserializers.simpleNode("whitespace", node, Whitespace::new);
    }

    public static Option<CompileResult<Comment>> deserializeComment(Node node) {
        return JavaDeserializers.simpleValueNode("comment", node, Comment::new);
    }

    private static Option<CompileResult<Try>> deserializeTry(Node node) {
        return JavaDeserializers.simpleNode("try", node, Try::new);
    }

    private static Option<CompileResult<Else>> deserializeElse(Node node) {
        return JavaDeserializers.simpleNode("else", node, Else::new);
    }

    private static Option<CompileResult<StringValue>> deserializeString(Node node) {
        return JavaDeserializers.simpleValueNode("string", node, StringValue::new);
    }

    private static Option<CompileResult<Char>> deserializeChar(Node node) {
        return JavaDeserializers.simpleValueNode("char", node, Char::new);
    }

    private static CompileResult<JavaLambdaHeader> deserializeLambdaHeader(Node node) {
        return Deserializers.orError("lambda-header", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                Deserializers.wrap(JavaDeserializers::deserializeMultipleHeader)
        ));
    }

    private static Option<CompileResult<JavaLang.Number>> deserializeNumber(Node node) {
        return JavaDeserializers.simpleValueNode("number", node, JavaLang.Number::new);
    }

    private static Option<CompileResult<Not>> deserializeNot(Node node) {
        return Destructors.destructWithType("not", node).map(deserializer -> deserializer.withNode("child", JavaDeserializers::deserializeValueOrError)
                .complete(Not::new));
    }

    private static Option<CompileResult<Index>> deserializeIndex(Node value) {
        return Destructors.destructWithType("index", value).map(destructor -> destructor.withNode("parent", JavaDeserializers::deserializeValueOrError)
                .withNode("argument", JavaDeserializers::deserializeValueOrError)
                .complete(tuple -> new Index(tuple.left(), tuple.right())));
    }

    private static Option<CompileResult<SwitchNode>> deserializeSwitch(Node node) {
        return Destructors.destructWithType("switch", node).map(deserializer -> deserializer
                .withNode("value", JavaDeserializers::deserializeValueOrError)
                .withNodeList("children", FunctionSegments::deserialize)
                .complete(tuple -> new SwitchNode(tuple.left(), tuple.right())));
    }

    public static Option<CompileResult<Value>> deserializeValue(Node node) {
        List<TypedDeserializer<Value>> deserializers = Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeSwitch),
                Deserializers.wrap(JavaDeserializers::deserializeInvocation),
                Deserializers.wrap(JavaDeserializers::deserializeString),
                Deserializers.wrap(JavaDeserializers.deserializeAccessWithType(JavaAccessType.Data)),
                Deserializers.wrap(JavaDeserializers.deserializeAccessWithType(JavaAccessType.Method)),
                Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                Deserializers.wrap(JavaDeserializers::deserializeChar),
                Deserializers.wrap(JavaDeserializers::deserializeLambda),
                Deserializers.wrap(JavaDeserializers::deserializeNumber),
                Deserializers.wrap(JavaDeserializers::deserializeNot),
                Deserializers.wrap(JavaDeserializers::deserializeIndex),
                Deserializers.wrap(JavaDeserializers::deserializeInstanceOf)
        );

        var operatorRules = Iters.fromValues(Operator.values())
                .map(JavaDeserializers::wrapAsDeserializer)
                .collect(new ListCollector<>());

        return Deserializers.or(node, deserializers.addAllLast(operatorRules));
    }

    private static Option<CompileResult<InstanceOf>> deserializeInstanceOf(Node node) {
        return Destructors.destructWithType("instance-of", node).map(destructor -> destructor
                .withNode("child", JavaDeserializers::deserializeValueOrError)
                .withNode("definition", JavaDeserializers::deserializeInstanceOfDefinition)
                .complete(tuple -> new InstanceOf(tuple.left(), tuple.right())));
    }

    private static CompileResult<InstanceOfDefinition> deserializeInstanceOfDefinition(Node node) {
        return Deserializers.orError("instance-of-definition", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeInstanceOfWithParameters),
                Deserializers.wrap(JavaDeserializers::deserializeInstanceOfWithName)
        ));
    }

    private static Option<CompileResult<InstanceOfDefinitionWithName>> deserializeInstanceOfWithName(Node node) {
        return Destructors.destructWithType("with-name", node).map(destructor -> {
            return destructor
                    .withString("name")
                    .withNode("base", JavaDeserializers::deserializeBase)
                    .complete(tuple -> new InstanceOfDefinitionWithName(tuple.right(), tuple.left()));
        });
    }

    private static Option<CompileResult<InstanceOfDefinitionWithParameters>> deserializeInstanceOfWithParameters(Node node) {
        return Destructors.destructWithType("with-parameters", node).map(destructor -> {
            return destructor
                    .withNode("base", JavaDeserializers::deserializeBase)
                    .withNodeList("parameters", JavaDeserializers::deserializeDefinition)
                    .complete(tuple -> new InstanceOfDefinitionWithParameters(tuple.left(), tuple.right()));
        });
    }

    private static TypedDeserializer<Value> wrapAsDeserializer(Operator operator) {
        return Deserializers.wrap(Deserializers.wrap(new OperationDeserializer(operator)));
    }

    public static CompileResult<Value> deserializeValueOrError(Node node) {
        return JavaDeserializers.deserializeValue(node).orElseGet(() -> CompileResults.NodeErr("Cannot deserialize value", node));
    }

    private static Option<CompileResult<Symbol>> deserializeSymbol(Node node) {
        return Destructors.destructWithType("symbol", node).map(deserializer -> deserializer.withString("value")
                .complete(Symbol::new)
                .mapValue(type -> type));
    }

    private static CompileResult<JavaLambdaParameter> deserializeLambdaParameter(Node node) {
        return Deserializers.orError("lambda-parameter", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                Deserializers.wrap(JavaDeserializers::deserializeTypedDefinition)
        ));
    }

    public static CompileResult<Definition> deserializeDefinition(Node node) {
        return JavaDeserializers.deserialize0(Destructors.destruct(node));
    }

    public static Option<CompileResult<Definition>> deserializeTypedDefinition(Node node) {
        return Destructors.destructWithType("definition", node).map(JavaDeserializers::deserialize0);
    }

    private static Option<CompileResult<JavaLambdaHeader>> deserializeMultipleHeader(Node node) {
        return Destructors.destructWithType("multiple", node).map(destructor -> destructor.withNodeList("parameters", JavaDeserializers::deserializeLambdaParameter).complete(JavaMultipleHeader::new));
    }

    private static CompileResult<Definition> deserialize0(InitialDestructor deserialize) {
        return deserialize.withString("name")
                .withNode("type", JavaDeserializers::deserializeType)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .withNodeListOptionally("type-parameters", TypescriptLang.TypeParam::deserialize)
                .complete((result) -> new Definition(result.left().right(), result.left().left().right(), result.left().left().left().left(), result.right(), result.left().left().left().right()));
    }

    public static Option<CompileResult<Case>> deserializeCase(Node node) {
        return Destructors.destructWithType("case", node).map(destructor -> destructor
                .withNodeList("definitions", CaseDefinition::deserialize)
                .withNode("value", CaseValues::deserializeOrError)
                .complete(tuple -> new Case(tuple.left(), tuple.right())));
    }

    public static Option<CompileResult<Assignment>> deserializeAssignment(Node node) {
        return Destructors.destructWithType("assignment", node).map(deserializer -> deserializer
                .withNode("destination", Assignables::deserializeError)
                .withNode("source", JavaDeserializers::deserializeValueOrError)
                .complete(tuple -> new Assignment(tuple.left(), tuple.right())));
    }

    public static CompileResult<JavaType> deserializeType(Node node) {
        return Deserializers.orError("type", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                Deserializers.wrap(JavaDeserializers::deserializeTemplate),
                Deserializers.wrap(JavaVariadicType::deserialize),
                Deserializers.wrap(JavaArrayType::deserialize),
                Deserializers.wrap(Qualified::deserializeQualified)
        ));
    }

    private static CompileResult<Base> deserializeBase(Node node) {
        return Deserializers.orError("base", node, Lists.of(
                Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                Deserializers.wrap(Qualified::deserializeQualified)
        ));
    }

    private static Option<CompileResult<JavaTemplateType>> deserializeTemplate(Node node) {
        return Destructors.destructWithType("template", node).map(deserializer -> deserializer
                .withNode("base", JavaDeserializers::deserializeBase)
                .withNodeListOptionally("arguments", JavaDeserializers::deserializeType)
                .complete(tuple -> new JavaTemplateType(tuple.left(), tuple.right())));
    }

    private static CompileResult<JavaArgument> deserializeArguments(Node node) {
        return JavaDeserializers.<JavaArgument>commonWrap("argument", node, n -> (Option) JavaDeserializers.deserializeValue(n));
    }

    public static CompileResult<JavaParameter> deserializeParameter(Node node) {
        return JavaDeserializers.<JavaParameter>commonWrap("parameter", node, n -> (Option) JavaDeserializers.deserializeTypedDefinition(n));
    }
}
