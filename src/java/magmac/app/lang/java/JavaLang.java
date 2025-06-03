package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.CompoundDestructor;
import magmac.app.compile.node.InitialDestructor;
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
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.CommonLang;
import magmac.app.lang.CommonRules;
import magmac.app.lang.Deserializers;
import magmac.app.lang.Destructors;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.Serializable;
import magmac.app.lang.common.Access;
import magmac.app.lang.common.Symbol;
import magmac.app.lang.node.Conditional;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.Deserializer;
import magmac.app.lang.node.FunctionSegments;
import magmac.app.lang.node.Lambda;
import magmac.app.lang.node.LambdaValueContent;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.NumberNode;
import magmac.app.lang.node.Operator;
import magmac.app.lang.node.Parameters;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureMembers;
import magmac.app.lang.node.StructureStatementValue;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.TypeArguments;
import magmac.app.lang.node.TypedDeserializer;
import magmac.app.lang.web.TypescriptLang;

public class JavaLang {
    public sealed interface JavaArgument permits JavaValue, JavaWhitespace {
    }

    public sealed interface JavaCaller permits JavaConstruction, JavaValue {
    }

    public sealed interface JavaValue extends JavaCaller, JavaArgument, JavaAssignable permits JavaAccess, JavaCharNode, JavaIndexNode, Invokable, JavaLambda, JavaNot, JavaNumberNode, JavaOperation, JavaStringNode, JavaSwitchNode, JavaSymbol {
    }

    public interface JavaAssignable {
    }

    public sealed interface JavaBlockHeader permits JavaCatch, JavaConditional, JavaElse, JavaTry {
    }

    public sealed interface JavaBase extends Serializable permits JavaQualified, JavaSymbol {
    }

    public interface JavaLambdaContent {
    }

    public sealed interface JavaType permits JavaArrayType, JavaSymbol, JavaTemplateType, JavaQualified, JavaVariadicType {
    }

    public interface JavaLambdaHeader {
    }

    public interface JavaLambdaParameter {
    }

    public static final class Invokable extends magmac.app.lang.java.Invokable<JavaCaller, JavaArgument> implements JavaValue, JavaFunctionSegmentValue {
        public Invokable(JavaCaller caller, List<JavaArgument> arguments) {
            super(caller, arguments);
        }
    }

    public static final class JavaSymbol extends Symbol implements JavaType, JavaValue, JavaBase, JavaLambdaHeader, JavaLambdaParameter {
        public JavaSymbol(String value) {
            super(value);
        }
    }

    public static final class JavaArrayType implements JavaType {
        public final JavaType inner;

        public JavaArrayType(JavaType arrayType) {
            this.inner = arrayType;
        }

        public static Rule createArrayRule(Rule rule) {
            NodeRule child = new NodeRule("child", rule);
            return new TypeRule("array", new StripRule(new SuffixRule(child, "[]")));
        }

        public static Option<CompileResult<JavaType>> deserialize(Node node) {
            return Destructors.destructWithType("array", node)
                    .map(deserializer -> deserializer.withNode("child", JavaTypes::deserialize)
                            .complete(JavaArrayType::new));
        }
    }

    public record JavaRoot(List<JavaRootSegment> children) {
        public static CompileResult<JavaRoot> getChildren(Node node, Deserializer<JavaRootSegment> deserializer) {
            return Destructors.destruct(node)
                    .withNodeList("children", deserializer)
                    .complete(JavaRoot::new);
        }
    }

    public static final class JavaLambda extends Lambda implements JavaValue {
        public JavaLambda(JavaLambdaHeader header, JavaLambdaContent content) {
            super(header, content);
        }
    }

    public static final class JavaAccess extends Access<JavaAccessType, JavaValue> implements JavaValue {
        public JavaAccess(JavaAccessType type, JavaValue receiver, String property) {
            super(type, receiver, property);
        }
    }

    public static final class JavaLambdaValueContent extends LambdaValueContent implements JavaLambdaContent {
        public JavaLambdaValueContent(JavaValue value) {
            super(value);
        }
    }

    public record JavaLambdaBlockContent(List<JavaFunctionSegment> children) implements JavaLambdaContent {
        public static Option<CompileResult<JavaLambdaBlockContent>> deserialize(Node node) {
            return Destructors.destructWithType("block", node).map(destructor -> {
                return destructor.withNodeList("children", FunctionSegments::deserialize)
                        .complete(JavaLambdaBlockContent::new);
            });
        }
    }

    public static final class JavaConditional extends Conditional<JavaValue> implements JavaBlockHeader {
        public JavaConditional(ConditionalType type, JavaValue condition) {
            super(type, condition);
        }
    }

    public static final class JavaTemplateType implements JavaType {
        public final JavaBase base;
        public final TypeArguments<JavaType> typeArguments;

        public JavaTemplateType(JavaBase base, TypeArguments<JavaType> typeArguments) {
            this.base = base;
            this.typeArguments = typeArguments;
        }

        public static Option<CompileResult<JavaTemplateType>> deserialize(Node node) {
            return Destructors.destructWithType("template", node).map(deserializer -> deserializer
                    .withNode("base", JavaTemplateType::deserializeBase)
                    .withNodeList("arguments", JavaTypes::deserialize)
                    .complete(tuple -> new JavaTemplateType(tuple.left(), new TypeArguments<JavaType>(tuple.right()))));
        }

        private static CompileResult<JavaBase> deserializeBase(Node node) {
            return Deserializers.orError("base", node, Lists.of(
                    Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                    Deserializers.wrap(JavaQualified::deserializeQualified)
            ));
        }

        public static Rule createTemplateRule(Rule type) {
            Rule base = new NodeRule("base", new OrRule(Lists.of(
                    CommonRules.createSymbolRule(),
                    JavaQualified.createQualifiedRule()
            )));

            Rule arguments = NodeListRule.Values("arguments", type);
            return new TypeRule("template", new StripRule(new SuffixRule(LocatingRule.First(base, "<", arguments), ">")));
        }
    }

    public record JavaStructureNodeDeserializer(
            JavaStructureType type) implements TypedDeserializer<JavaStructureNode> {
        private static CompileResult<JavaStructureNode> deserializeHelper(JavaStructureType type, InitialDestructor deserializer) {
            return JavaStructureNodeDeserializer.attachOptionals(JavaStructureNodeDeserializer.attachRequired(deserializer))
                    .complete(tuple -> JavaStructureNodeDeserializer.from(type, tuple));
        }

        private static CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> attachRequired(InitialDestructor deserializer) {
            return deserializer.withString("name")
                    .withNodeList("modifiers", Modifier::deserialize)
                    .withNodeList("children", StructureMembers::deserialize);
        }

        private static CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> attachOptionals(CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> attachRequired) {
            return attachRequired.withNodeListOptionally("implemented", JavaTypes::deserialize)
                    .withNodeListOptionally("type-parameters", TypescriptLang.TypeParam::deserialize)
                    .withNodeListOptionally("parameters", Parameters::deserialize)
                    .withNodeListOptionally("extended", JavaTypes::deserialize)
                    .withNodeListOptionally("variants", JavaTypes::deserialize);
        }

        private static JavaStructureNode from(
                JavaStructureType type,
                Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>> tuple) {
            return new JavaStructureNode(type,
                    new StructureValue<JavaType, JavaStructureMember>(tuple.left().left().left().left().left().left().left(), tuple.left().left().left().left().left().left().right(), tuple.left().left().left().left().left().right(), tuple.left().left().left().right(), tuple.left().right(), tuple.left().left().left().left().right()), tuple.left().left().right(),
                    tuple.right()
            );
        }

        public Option<CompileResult<JavaStructureNode>> deserialize(Node node) {
            return Destructors.destructWithType(this.type().name().toLowerCase(), node)
                    .map((InitialDestructor deserializer) -> JavaStructureNodeDeserializer.deserializeHelper(this.type(), deserializer));
        }
    }

    public static final class JavaTypes {
        public static CompileResult<JavaType> deserialize(Node node) {
            return Deserializers.orError("type", node, Lists.of(
                    Deserializers.wrap(JavaDeserializers::deserializeSymbol),
                    Deserializers.wrap(JavaTemplateType::deserialize),
                    Deserializers.wrap(JavaVariadicType::deserialize),
                    Deserializers.wrap(JavaArrayType::deserialize),
                    Deserializers.wrap(JavaQualified::deserializeQualified)
            ));
        }

        public static Rule createTypeRule() {
            LazyRule type = new MutableLazyRule();
            return type.set(new OrRule(Lists.of(
                    JavaVariadicType.createVariadicRule(type),
                    JavaArrayType.createArrayRule(type),
                    JavaTemplateType.createTemplateRule(type),
                    CommonRules.createSymbolRule(),
                    JavaQualified.createQualifiedRule()
            )));
        }
    }

    public static final class JavaTry implements JavaBlockHeader {
    }

    public static final class JavaElse implements JavaBlockHeader {
    }

    public record JavaCatch(JavaDefinition definition) implements JavaBlockHeader {
        public static Option<CompileResult<JavaBlockHeader>> deserialize(Node node) {
            return Destructors.destructWithType("catch", node).map(deserializer -> deserializer.withNode("definition", JavaDeserializers::deserializeDefinition)
                    .complete(JavaCatch::new));
        }
    }

    public record JavaQualified(List<Segment> segments) implements JavaBase, JavaType {
        public static Option<CompileResult<JavaQualified>> deserializeQualified(Node node) {
            return Destructors.destructWithType("qualified", node)
                    .map(deserializer -> deserializer.withNodeList("segments", Segment::deserialize)
                            .complete(JavaQualified::new));
        }

        public static TypeRule createQualifiedRule() {
            return new TypeRule("qualified", JavaQualified.createSegmentsRule("segments"));
        }

        private static Rule createSegmentsRule(String key) {
            return NodeListRule.createNodeListRule(key, new DelimitedFolder('.'), CommonRules.createSymbolRule("value"));
        }

        @Override
        public Node serialize() {
            return new MapNode("qualified").withNodeListAndSerializer("segments", this.segments, Segment::serialize);
        }
    }

    public record JavaVariadicType(JavaType child) implements JavaType {
        public static Option<CompileResult<JavaType>> deserialize(Node node) {
            return Destructors.destructWithType("variadic", node).map(deserializer -> deserializer.withNode("child", JavaTypes::deserialize)
                    .complete(JavaVariadicType::new));
        }

        public static Rule createVariadicRule(Rule rule) {
            NodeRule child = new NodeRule("child", rule);
            return new TypeRule("variadic", new StripRule(new SuffixRule(child, "...")));
        }
    }

    public static final class JavaNumberNode extends NumberNode implements JavaValue {
        public JavaNumberNode(String value) {
            super(value);
        }
    }

    public static final class JavaStringNode extends magmac.app.lang.common.StringNode implements JavaValue {
        public JavaStringNode(String value) {
            super(value);
        }
    }

    public record JavaOperation(
            JavaValue left,
            Operator operator,
            JavaValue right
    ) implements JavaValue, TypescriptLang.TypescriptValue {
        @Override
        public Node serialize() {
            return new MapNode(this.operator.type());
        }
    }

    public record JavaCharNode(String value) implements JavaValue {
    }

    public record JavaNot(JavaValue value) implements JavaValue {
    }

    public record JavaIndexNode(JavaValue parent, JavaValue argument) implements JavaValue {
    }

    public record JavaSwitchNode(JavaValue value, List<JavaFunctionSegment> children) implements JavaValue {
    }

    public record JavaDefinition(CommonLang.Definition<JavaType> definition)
            implements JavaParameter, JavaAssignable, JavaMethodHeader, StructureStatementValue, JavaLambdaParameter {
    }

    public record JavaMultipleHeader(List<JavaLambdaParameter> parameters) implements JavaLambdaHeader {
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
        Class,
        Record,
        Enum,
        Interface
    }

    public static final class JavaWhitespace implements
            JavaArgument,
            JavaFunctionSegment,
            JavaRootSegment,
            JavaParameter,
            JavaStructureMember {
    }
}
