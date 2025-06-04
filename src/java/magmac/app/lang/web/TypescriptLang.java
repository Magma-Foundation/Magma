package magmac.app.lang.web;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.lang.CommonLang;
import magmac.app.lang.Destructors;
import magmac.app.lang.Serializable;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.node.AbstractReturnNode;
import magmac.app.lang.node.Conditional;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.Operator;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.PostVariant;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureValue;

public final class TypescriptLang {
    public interface Argument extends Serializable {
    }

    public interface TypeScriptParameter extends Serializable {
    }

    public interface TypescriptBlockHeader extends Serializable {
    }

    public interface TypescriptStructureMember extends Serializable {
    }

    public interface TypeScriptMethodHeader extends Serializable {
    }

    public interface Type extends Serializable {
    }

    public interface TypeScriptRootSegment extends Serializable {
    }

    public interface FunctionSegment extends Serializable {
        interface Value extends Serializable {
        }
    }

    public interface Value extends Caller, Argument, Assignable {
    }

    public interface Assignable extends Serializable {
    }

    public record Number(String value) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("number").withString("value", this.value);
        }
    }

    public static final class StructureNode implements TypeScriptRootSegment {
        private final StructureType type;
        private final StructureValue<Type, TypescriptStructureMember> value;

        public StructureNode(StructureType type, StructureValue<Type, TypescriptStructureMember> structureNode) {
            this.type = type;
            this.value = structureNode;
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
            return new MapNode(this.type.text())
                    .withString("name", this.value.name())
                    .withNodeListAndSerializer("modifiers", this.value.modifiers(), Serializable::serialize)
                    .withNodeListAndSerializer("members", this.value.members(), Serializable::serialize)
                    .merge(this.serializeTypeParams())
                    .merge(this.serializeExtendedParams())
                    .merge(this.serializeImplementsParams());
        }
    }

    public record TypeScriptImport(List<Segment> values, List<Segment> segments) implements TypeScriptRootSegment {
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
            Option<List<FunctionSegment>> maybeChildren
    ) implements TypescriptStructureMember {
        @Override
        public Node serialize() {
            var node = new MapNode("method")
                    .withNodeSerialized("header", this.header);

            return this.maybeChildren.map(children -> node.withNodeListSerialized("children", children)).orElse(node);
        }
    }

    public static final class TemplateType implements Type {
        private final JavaLang.Symbol base;
        private final Option<List<Type>> maybeTypeArguments;

        public TemplateType(JavaLang.Symbol base, Option<List<Type>> maybeTypeArguments) {
            this.base = base;
            this.maybeTypeArguments = maybeTypeArguments;
        }

        @Override
        public Node serialize() {
            var node = new MapNode("template")
                    .withNodeSerialized("base", this.base);

            return this.maybeTypeArguments.map(typeArguments -> {
                return node.withNodeListSerialized("arguments", typeArguments);
            }).orElse(node);
        }
    }

    public static final class TypescriptConditional extends Conditional<Value> implements TypescriptBlockHeader {
        public TypescriptConditional(ConditionalType type, Value condition) {
            super(type, condition);
        }

        @Override
        public Node serialize() {
            return new MapNode(this.type.text())
                    .withNodeSerialized("condition", this.condition);
        }
    }

    public static final class Block extends magmac.app.lang.node.Block<TypescriptBlockHeader, FunctionSegment> implements FunctionSegment {
        public Block(TypescriptBlockHeader header, List<FunctionSegment> segments) {
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

    public static final class Definition extends CommonLang.AbstractDefinition<Type> implements TypeScriptParameter, Assignable, TypeScriptMethodHeader {
        public Definition(Option<List<Annotation>> maybeAnnotations, List<Modifier> modifiers, String name, Option<List<TypeParam>> maybeTypeParams, Type type) {
            super(maybeAnnotations, modifiers, name, maybeTypeParams, type);
        }

        @Override
        public Node serialize() {
            var node = new MapNode("definition")
                    .withString("name", this.name)
                    .withNodeSerialized("type", this.type);

            if (this.modifiers.isEmpty()) {
                return node;
            }
            else {
                return node.withNodeListSerialized("modifiers", this.modifiers);
            }
        }

        public Definition withModifier(Modifier modifier) {
            return new Definition(this.maybeAnnotations, this.modifiers.addLast(modifier), this.name, this.maybeTypeParams, this.type);
        }
    }

    public static final class ArrayType implements Type {
        private final Type childType;

        public ArrayType(Type arrayType) {
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

    public record Construction(Type type) implements Caller {
        @Override
        public Node serialize() {
            return new MapNode("construction").withNodeSerialized("type", this.type);
        }
    }

    public record Post(PostVariant variant, Value value) implements FunctionSegment.Value {
        @Override
        public Node serialize() {
            return new MapNode(this.variant.type()).withNodeSerialized("child", this.value);
        }
    }

    public record Access(Value receiver, String property) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("data-access")
                    .withNodeSerialized("receiver", this.receiver)
                    .withString("property", this.property);
        }
    }

    public static final class Break implements FunctionSegment.Value {
        @Override
        public Node serialize() {
            return new MapNode("break");
        }
    }

    public static final class Continue implements FunctionSegment.Value {
        @Override
        public Node serialize() {
            return new MapNode("continue");
        }
    }

    public static final class Return extends AbstractReturnNode<Value> implements FunctionSegment.Value, FunctionSegment {
        public Return(TypescriptLang.Value child) {
            super(child);
        }

        @Override
        public Node serialize() {
            return new MapNode("return").withNodeSerialized("child", this.child);
        }
    }

    /**
     * Invocation node for the TypeScript AST. An example syntax would be
     * {@code myFunc(1, "a")}. The {@code caller} field stores {@code myFunc}
     * and the {@code arguments} list holds {@code 1} and {@code "a"}.
     */
    public static class Invokable extends magmac.app.lang.java.Invokable<Caller, Argument> implements Value, FunctionSegment.Value {
        public Invokable(Caller caller, List<Argument> arguments) {
            super(caller, arguments);
        }

        @Override
        public Node serialize() {
            return new MapNode("invokable")
                    .withNodeSerialized("caller", this.caller)
                    .withNodeListSerialized("arguments", this.arguments);
        }
    }

    public record Char(String value) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("char").withString("value", this.value);
        }
    }

    public record Index(Value parent, Value argument) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("index").withNodeSerialized("parent", this.parent).withNodeSerialized("argument", this.argument);
        }
    }

    public record Not(Value child) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("not").withNodeSerialized("child", this.child);
        }
    }

    public record StringValue(java.lang.String value) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("string").withString("value", this.value);
        }
    }

    public record Symbol(String value) implements Value {
        @Override
        public Node serialize() {
            return new MapNode("symbol").withString("value", this.value);
        }
    }

    public record Operation(
            Value left,
            Operator operator,
            Value right
    ) implements Value {
        @Override
        public Node serialize() {
            return new MapNode(this.operator.type())
                    .withNodeSerialized("left", this.left)
                    .withNodeSerialized("right", this.right);
        }
    }

    public static class Whitespace implements
            TypeScriptRootSegment,
            TypescriptStructureMember,
            TypeScriptParameter,
            FunctionSegment,
            Argument {
        @Override
        public Node serialize() {
            return new MapNode("whitespace");
        }
    }

    public record Assignment(Assignable assignable, Value value) implements FunctionSegment.Value {
        @Override
        public Node serialize() {
            return new MapNode("assignment")
                    .withNodeSerialized("destination", this.assignable)
                    .withNodeSerialized("source", this.value);
        }
    }

    public enum StructureType {
        Class("class"),
        Enum("enum"),
        Interface("interface");

        private final String text;

        StructureType(String text) {
            this.text = text;
        }

        public String text() {
            return this.text;
        }
    }
}
