package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.CompoundDestructor;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.CommonLang.AbstractDefinition;
import magmac.app.lang.CommonRules;
import magmac.app.lang.Destructors;
import magmac.app.lang.Serializable;
import magmac.app.lang.common.AbstractFunctionStatement;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.node.AbstractReturnNode;
import magmac.app.lang.node.CaseDefinition;
import magmac.app.lang.node.CaseValue;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.Deserializer;
import magmac.app.lang.node.FunctionSegments;
import magmac.app.lang.node.LambdaValueContent;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.NumberNode;
import magmac.app.lang.node.Operator;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureMembers;
import magmac.app.lang.node.StructureStatementValue;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.TypedDeserializer;
import magmac.app.lang.web.TypescriptLang;

public class JavaLang {
    public sealed interface JavaArgument permits Value, Whitespace {
    }

    public sealed interface JavaCaller permits Construction, Value {
    }

    public sealed interface Assignable {
    }

    public sealed interface Value extends JavaCaller, JavaArgument, Assignable permits Access, Char, Index, InstanceOf, Invokable, Lambda, Not, Number, StringValue, SwitchNode, Symbol, operation {
    }

    public sealed interface BlockHeader permits Catch, Conditional, Else, Try {
    }

    public sealed interface Base extends Serializable permits Qualified, Symbol {
    }

    public interface JavaLambdaContent {
    }

    public sealed interface JavaType permits JavaArrayType, Symbol, JavaTemplateType, Qualified, JavaVariadicType {
    }

    public interface JavaLambdaHeader {
    }

    public interface JavaLambdaParameter {
    }

    public interface InstanceOfDefinition {
    }

    public record InstanceOfDefinitionWithParameters(Base base,
                                                     List<Definition> parameters) implements InstanceOfDefinition {
    }

    public record InstanceOfDefinitionWithName(Base base, String name) implements InstanceOfDefinition {
    }

    /**
     * Invocation of a callable expression. For example {@code doStuff(5)} will
     * be represented by an {@code Invokable} where {@code doStuff} is the caller
     * and {@code 5} is stored in the argument list.
     */
    public static final class Invokable extends magmac.app.lang.java.Invokable<JavaCaller, JavaArgument> implements Value, JavaFunctionSegmentValue {
        public Invokable(JavaCaller caller, List<JavaArgument> arguments) {
            super(caller, arguments);
        }
    }

    public static final class Symbol extends magmac.app.lang.common.Symbol implements JavaType, Value, Base, JavaLambdaHeader, JavaLambdaParameter {
        public Symbol(String value) {
            super(value);
        }
    }

    public static final class JavaArrayType implements JavaType {
        public final JavaType inner;

        JavaArrayType(JavaType arrayType) {
            this.inner = arrayType;
        }

        public static Rule createArrayRule(Rule rule) {
            var child = new NodeRule("child", rule);
            return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
        }

        public static Option<CompileResult<JavaType>> deserialize(Node node) {
            return Destructors.destructWithType("array", node)
                    .map(deserializer -> deserializer.withNode("child", JavaDeserializers::deserializeType)
                            .complete(JavaArrayType::new));
        }
    }

    public record Root(List<JavaRootSegment> children) {
        public static CompileResult<Root> getChildren(Node node, Deserializer<JavaRootSegment> deserializer) {
            return Destructors.destruct(node)
                    .withNodeList("children", deserializer)
                    .complete(Root::new);
        }
    }

    public static final class Lambda extends magmac.app.lang.node.Lambda implements Value {
        public Lambda(JavaLambdaHeader header, JavaLambdaContent content) {
            super(header, content);
        }
    }

    public static final class Access extends magmac.app.lang.common.Access<JavaAccessType, Value, JavaType> implements Value {
        public Access(JavaAccessType type, Value receiver, Option<List<JavaType>> arguments, String property) {
            super(type, receiver, property, arguments);
        }
    }

    public static final class JavaLambdaValueContent extends LambdaValueContent implements JavaLambdaContent {
        public JavaLambdaValueContent(Value value) {
            super(value);
        }
    }

    public record JavaLambdaBlockContent(List<JavaFunctionSegment> children) implements JavaLambdaContent {
        public static Option<CompileResult<JavaLambdaBlockContent>> deserialize(Node node) {
            return Destructors.destructWithType("block", node).map(destructor -> destructor.withNodeList("children", FunctionSegments::deserialize)
                    .complete(JavaLambdaBlockContent::new));
        }
    }

    public static final class Conditional extends magmac.app.lang.node.Conditional<Value> implements BlockHeader {
        public Conditional(ConditionalType type, Value condition) {
            super(type, condition);
        }
    }

    public record JavaTemplateType(Base base, Option<List<JavaType>> typeArguments) implements JavaType {
    }

    public record JavaStructureNodeDeserializer(
            JavaStructureType type) implements TypedDeserializer<Structure> {
        private static CompileResult<Structure> deserializeHelper(JavaStructureType type, InitialDestructor deserializer) {
            return JavaStructureNodeDeserializer.attachOptionals(JavaStructureNodeDeserializer.attachRequired(deserializer))
                    .complete(tuple -> JavaStructureNodeDeserializer.from(type, tuple));
        }

        private static CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> attachRequired(InitialDestructor deserializer) {
            return deserializer.withString("name")
                    .withNodeList("modifiers", Modifier::deserialize)
                    .withNodeList("children", StructureMembers::deserialize);
        }

        private static CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> attachOptionals(CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> attachRequired) {
            return attachRequired.withNodeListOptionally("implemented", JavaDeserializers::deserializeType)
                    .withNodeListOptionally("type-parameters", TypescriptLang.TypeParam::deserialize)
                    .withNodeListOptionally("parameters", JavaDeserializers::deserializeParameter)
                    .withNodeListOptionally("extended", JavaDeserializers::deserializeType)
                    .withNodeListOptionally("variants", JavaDeserializers::deserializeType);
        }

        private static Structure from(
                JavaStructureType type,
                Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>> tuple) {
            return new Structure(type,
                    new StructureValue<JavaType, JavaStructureMember>(tuple.left().left().left().left().left().left().left(), tuple.left().left().left().left().left().left().right(), tuple.left().left().left().left().left().right(), tuple.left().left().left().right(), tuple.left().right(), tuple.left().left().left().left().right()), tuple.left().left().right(),
                    tuple.right()
            );
        }

        public Option<CompileResult<Structure>> deserialize(Node node) {
            return Destructors.destructWithType(this.type().text(), node)
                    .map((InitialDestructor deserializer) -> JavaStructureNodeDeserializer.deserializeHelper(this.type(), deserializer));
        }
    }

    public static final class Try implements BlockHeader {
    }

    public static final class Else implements BlockHeader {
    }

    public record Catch(Definition definition) implements BlockHeader {
        public static Option<CompileResult<BlockHeader>> deserialize(Node node) {
            return Destructors.destructWithType("catch", node).map(deserializer -> deserializer.withNode("definition", JavaDeserializers::deserializeDefinition)
                    .complete(Catch::new));
        }
    }

    public record Qualified(List<Segment> segments) implements Base, JavaType {
        public static Option<CompileResult<Qualified>> deserializeQualified(Node node) {
            return Destructors.destructWithType("qualified", node)
                    .map(deserializer -> deserializer.withNodeList("segments", Segment::deserialize)
                            .complete(Qualified::new));
        }

        public static TypeRule createQualifiedRule() {
            return new TypeRule("qualified", Qualified.createSegmentsRule());
        }

        private static Rule createSegmentsRule(

        ) {
            return NodeListRule.createNodeListRule("segments", new DelimitedFolder('.'), CommonRules.createSymbolRule("value"));
        }

        @Override
        public Node serialize() {
            return new MapNode("qualified").withNodeListAndSerializer("segments", this.segments, Segment::serialize);
        }
    }

    public record JavaVariadicType(JavaType child) implements JavaType {
        public static Option<CompileResult<JavaType>> deserialize(Node node) {
            return Destructors.destructWithType("variadic", node).map(deserializer -> deserializer.withNode("child", JavaDeserializers::deserializeType)
                    .complete(JavaVariadicType::new));
        }

        public static Rule createVariadicRule(Rule rule) {
            var child = new NodeRule("child", rule);
            return new TypeRule("variadic", new StripRule(new SuffixRule(child, "...")));
        }
    }

    public static final class Number extends NumberNode implements Value {
        public Number(String value) {
            super(value);
        }
    }

    public static final class StringValue extends magmac.app.lang.common.StringNode implements Value {
        public StringValue(String value) {
            super(value);
        }
    }

    public record operation(
            Value left,
            Operator operator,
            Value right
    ) implements Value, TypescriptLang.Value {
        @Override
        public Node serialize() {
            return new MapNode(this.operator.type());
        }
    }

    public record Char(String value) implements Value {
    }

    public record Not(Value value) implements Value {
    }

    public record Index(Value parent, Value argument) implements Value {
    }

    public record SwitchNode(Value value, List<JavaFunctionSegment> children) implements Value {
    }

    public static final class Definition extends AbstractDefinition<JavaType>
            implements JavaParameter, Assignable, JavaMethodHeader, StructureStatementValue, JavaLambdaParameter {
        public Definition(Option<List<Annotation>> maybeAnnotations, List<Modifier> modifiers, String name, Option<List<TypescriptLang.TypeParam>> maybeTypeParams, JavaType type) {
            super(maybeAnnotations, modifiers, name, maybeTypeParams, type);
        }
    }

    public record JavaMultipleHeader(List<JavaLambdaParameter> parameters) implements JavaLambdaHeader {
    }

    public static final class Whitespace implements
            JavaArgument,
            JavaFunctionSegment,
            JavaRootSegment,
            JavaParameter,
            JavaStructureMember {
    }

    public static final class FunctionStatement extends AbstractFunctionStatement<JavaFunctionSegmentValue> implements JavaFunctionSegment {
        public FunctionStatement(JavaFunctionSegmentValue child) {
            super(child);
        }
    }

    public static final class Return extends AbstractReturnNode<Value> implements JavaFunctionSegmentValue, JavaFunctionSegment {
        public Return(Value child) {
            super(child);
        }
    }

    public record Case(List<CaseDefinition> definitions, CaseValue value) implements JavaFunctionSegment {
    }

    public static final class Block extends magmac.app.lang.node.Block<BlockHeader, JavaFunctionSegment> implements JavaFunctionSegment {
        public Block(BlockHeader header, List<JavaFunctionSegment> segments) {
            super(header, segments);
        }
    }

    public record Assignment(
            Assignable assignable,
            Value value) implements JavaFunctionSegmentValue, StructureStatementValue {
    }

    public record InstanceOf(Value value, InstanceOfDefinition definition) implements Value {
    }

    public static final class Structure implements JavaRootSegment, JavaStructureMember {
        public final StructureValue<JavaType, JavaStructureMember> value;
        private final JavaStructureType type;

        Structure(
                JavaStructureType type,
                StructureValue<JavaType, JavaStructureMember> structureNode,
                Option<List<JavaParameter>> parameters,
                Option<List<JavaType>> variants
        ) {
            this.type = type;
            this.value = structureNode;
        }

        public JavaStructureType type() {
            return this.type;
        }

        public String name() {
            return this.value.name();
        }

        public Option<List<JavaType>> implemented() {
            return this.value.maybeImplemented();
        }

        public Option<List<JavaType>> extended() {
            return this.value.maybeExtended();
        }
    }

    public record Construction(JavaType type) implements JavaCaller {
    }

    public enum JavaAccessType {
        Data("data-access"),
        Method("method-access");

        private final String value;

        JavaAccessType(String value) {
            this.value = value;
        }

        public String type() {
            return this.value;
        }
    }

    public enum JavaStructureType {
        Class("class"),
        Record("record"),
        Enum("enum"),
        Interface("interface");

        private final String text;

        JavaStructureType(String text) {
            this.text = text;
        }

        public String text() {
            return this.text;
        }
    }
}
