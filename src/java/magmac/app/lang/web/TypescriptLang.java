package magmac.app.lang.web;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.PrefixRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StringRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.compile.rule.divide.DelimitedDivider;
import magmac.app.compile.rule.fold.DelimitedFolder;
import magmac.app.lang.CommonLang;
import magmac.app.lang.Destructors;
import magmac.app.lang.JavaRules;
import magmac.app.lang.LazyRule;
import magmac.app.lang.Serializable;
import magmac.app.lang.java.Invokable;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.node.Block;
import magmac.app.lang.node.Conditional;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.PostVariant;
import magmac.app.lang.node.ReturnNode;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.TypeArguments;

public final class TypescriptLang {
    public interface TypescriptArgument extends Serializable {
        class TypescriptWhitespace implements
                TypeScriptRootSegment,
                TypescriptStructureMember,
                TypeScriptParameter,
                TypescriptFunctionSegment,
                TypescriptArgument {
            @Override
            public Node serialize() {
                return new MapNode("whitespace");
            }
        }
    }

    public interface TypeScriptParameter extends Serializable {
    }

    public interface TypescriptBlockHeader extends Serializable {
    }

    public interface TypescriptStructureMember extends Serializable {
    }

    public interface TypescriptLambdaContent extends Serializable {
    }

    public interface TypeScriptMethodHeader extends Serializable {
    }

    public interface TypeScriptType extends Serializable {
    }

    public interface TypeScriptRootSegment extends Serializable {
    }

    public interface TypescriptFunctionSegment extends Serializable {
    }

    public interface TypescriptValue extends TypescriptCaller, TypescriptArgument {
    }

    public record NumberNode(String value) implements TypescriptValue {
        @Override
        public Node serialize() {
            return new MapNode("number").withString("value", this.value);
        }
    }

    public static final class TypescriptStructureNode implements TypeScriptRootSegment {
        private final TypescriptStructureType type;
        private final StructureValue<TypeScriptType, TypescriptStructureMember> value;

        public TypescriptStructureNode(TypescriptStructureType type, StructureValue<TypeScriptType, TypescriptStructureMember> structureNode) {
            this.type = type;
            this.value = structureNode;
        }

        public static Rule createStructureRule(String type) {
            Rule children = CommonLang.Statements("members", TypescriptRules.createStructureMemberRule());
            Rule name = new StringRule("name");
            Rule afterKeyword = LocatingRule.First(JavaRules.attachTypeParams(name), " {", new SuffixRule(children, "\n}\n"));
            return new TypeRule(type, new PrefixRule("export " + type + " ", afterKeyword));
        }

        private Node serializeImplementsParams() {
            return this.value.maybeImplemented()
                    .map(implemented -> new MapNode().withNodeListSerialized("implemented", implemented))
                    .orElse(new MapNode());
        }

        private Node serializeExtendedParams() {
            return this.value.maybeExtended()
                    .map(extended -> new MapNode().withNodeListSerialized("extended", extended))
                    .orElse(new MapNode());
        }

        private Node serializeTypeParams() {
            return this.value.maybeTypeParams()
                    .map(typeParams -> new MapNode().withNodeListSerialized("type-parameters", typeParams))
                    .orElse(new MapNode());
        }

        @Override
        public Node serialize() {
            return new MapNode(this.type.name().toLowerCase())
                    .withString("name", this.value.name())
                    .withNodeListAndSerializer("modifiers", this.value.modifiers(), Serializable::serialize)
                    .withNodeListAndSerializer("members", this.value.members(), Serializable::serialize)
                    .merge(this.serializeTypeParams())
                    .merge(this.serializeExtendedParams())
                    .merge(this.serializeImplementsParams());
        }
    }

    public record TypeScriptRoots(List<TypeScriptRootSegment> children) implements Serializable {
        @Override
        public Node serialize() {
            return new MapNode().withNodeListAndSerializer("children", this.children, Serializable::serialize);
        }
    }

    public record TypeScriptImport(List<Segment> values,
                                   List<Segment> segments) implements TypeScriptRootSegment {
        public static Rule createImportRule() {
            Rule segments = new SuffixRule(NodeListRule.createNodeListRule("segments", new DelimitedFolder('/'), new StringRule("value")), "\";\n");
            Rule leftRule = new NodeListRule("values", new StringRule("value"), new DelimitedDivider(", "));
            Rule first = LocatingRule.First(leftRule, " } from \"", segments);
            return new TypeRule("import", new PrefixRule("import { ", first));
        }

        @Override
        public Node serialize() {
            return new MapNode("import")
                    .withNodeListAndSerializer("values", this.values, Segment::serialize)
                    .withNodeListAndSerializer("segments", this.segments, Segment::serialize);
        }
    }

    public record TypescriptRoot(List<TypeScriptRootSegment> children) implements Serializable {
        @Override
        public Node serialize() {
            return new MapNode("root")
                    .withNodeListAndSerializer("children", this.children, TypeScriptRootSegment::serialize);
        }
    }

    public record TypescriptMethod(
            ParameterizedMethodHeader<TypeScriptParameter> header,
            Option<List<TypescriptFunctionSegment>> maybeChildren
    ) implements TypescriptStructureMember {
        @Override
        public Node serialize() {
            Node node = new MapNode("method")
                    .withNodeSerialized("header", this.header);

            return this.maybeChildren.map(children -> node.withNodeListSerialized("children", children)).orElse(node);
        }
    }

    public static final class TypeScriptTemplateType implements TypeScriptType {
        private final JavaLang.JavaSymbol base;
        private final TypeArguments<TypeScriptType> typeArguments;

        public TypeScriptTemplateType(JavaLang.JavaSymbol base, TypeArguments<TypeScriptType> typeArguments) {
            this.base = base;
            this.typeArguments = typeArguments;
        }

        @Override
        public Node serialize() {
            return new MapNode("template")
                    .withNodeSerialized("base", this.base)
                    .withNodeListSerialized("arguments", this.typeArguments.arguments());
        }
    }

    public static final class TypescriptConditional extends Conditional<TypescriptValue> implements TypescriptBlockHeader {
        public TypescriptConditional(ConditionalType type, TypescriptValue condition) {
            super(type, condition);
        }

        @Override
        public Node serialize() {
            return new MapNode(this.type.name().toLowerCase())
                    .withNodeSerialized("condition", this.condition);
        }
    }

    public static final class TypescriptBlock extends Block<TypescriptBlockHeader, TypescriptFunctionSegment> implements TypescriptFunctionSegment {
        public TypescriptBlock(TypescriptBlockHeader header, List<TypescriptFunctionSegment> segments) {
            super(header, segments);
        }

        @Override
        public Node serialize() {
            return new MapNode("block")
                    .withNodeSerialized("header", this.header)
                    .withNodeListSerialized("children", this.children);
        }
    }

    public static final class TypescriptConstructor implements TypeScriptMethodHeader {
        @Override
        public Node serialize() {
            return new MapNode("constructor");
        }
    }

    public static final class TypeScriptDefinition implements TypeScriptParameter, JavaLang.JavaAssignable, TypeScriptMethodHeader {
        private final CommonLang.Definition<TypeScriptType> definition;

        public TypeScriptDefinition(CommonLang.Definition<TypeScriptType> definition) {
            this.definition = definition;
        }

        @Override
        public Node serialize() {
            return new MapNode("definition")
                    .withString("name", this.definition.name())
                    .withNodeSerialized("type", this.definition.type());
        }
    }

    public static final class TypescriptArrayType implements TypeScriptType {
        private final TypeScriptType childType;

        public TypescriptArrayType(TypeScriptType arrayType) {
            this.childType = arrayType;
        }

        @Override
        public Node serialize() {
            return new MapNode("array").withNodeSerialized("child", this.childType);
        }
    }

    public record TypeParam(String value) implements Serializable {
        public static CompileResult<TypeParam> deserialize(Node node) {
            return Destructors.destruct(node)
                    .withString("value")
                    .complete(TypeParam::new);
        }

        @Override
        public Node serialize() {
            return new MapNode().withString("value", this.value);
        }
    }

    public record Construction(TypeScriptType type) implements TypescriptCaller {
        @Override
        public Node serialize() {
            return new MapNode("construction").withNodeSerialized("type", this.type);
        }
    }

    public record Post(PostVariant variant, TypescriptValue value) implements TypescriptFunctionSegmentValue {
        @Override
        public Node serialize() {
            return new MapNode(this.variant.type()).withNodeSerialized("child", this.value);
        }
    }

    public record Access(TypescriptValue receiver, String property) implements TypescriptValue {
        @Override
        public Node serialize() {
            return new MapNode("data-access")
                    .withNodeSerialized("receiver", this.receiver)
                    .withString("property", this.property);
        }
    }

    public static final class TypescriptBreak implements TypescriptFunctionSegmentValue {
        @Override
        public Node serialize() {
            return new MapNode("break");
        }
    }

    public static final class TypescriptContinue implements TypescriptFunctionSegmentValue {
        @Override
        public Node serialize() {
            return new MapNode("continue");
        }
    }

    public static final class TypescriptReturnNode extends ReturnNode<TypescriptValue> implements TypescriptFunctionSegmentValue, TypescriptFunctionSegment {
        public TypescriptReturnNode(TypescriptValue child) {
            super(child);
        }

        @Override
        public Node serialize() {
            return new MapNode("return").withNodeSerialized("child", this.child);
        }
    }

    public static class TypescriptInvokable extends Invokable<TypescriptCaller, TypescriptArgument> implements TypescriptValue, TypescriptFunctionSegmentValue {
        public TypescriptInvokable(TypescriptCaller caller, List<TypescriptArgument> arguments) {
            super(caller, arguments);
        }

        @Override
        public Node serialize() {
            return new MapNode("invokable")
                    .withNodeSerialized("caller", this.caller)
                    .withNodeListSerialized("arguments", this.arguments);
        }
    }

    public static TypeRule createArrayRule(LazyRule orRule) {
        return new TypeRule("array", new SuffixRule(new NodeRule("child", orRule), "[]"));
    }

    public enum TypescriptStructureType {
        Class,
        Enum,
        Interface
    }
}
