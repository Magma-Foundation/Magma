import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { Annotation } from "../../../../magmac/app/lang/common/Annotation";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
import { AbstractReturnNode } from "../../../../magmac/app/lang/node/AbstractReturnNode";
import { Conditional } from "../../../../magmac/app/lang/node/Conditional";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { Operator } from "../../../../magmac/app/lang/node/Operator";
import { ParameterizedMethodHeader } from "../../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { PostVariant } from "../../../../magmac/app/lang/node/PostVariant";
import { Segment } from "../../../../magmac/app/lang/node/Segment";
import { StructureValue } from "../../../../magmac/app/lang/node/StructureValue";
export interface Argument {
}
export interface TypeScriptParameter {
}
export interface TypescriptBlockHeader {
}
export interface TypescriptStructureMember {
}
export interface TypeScriptMethodHeader {
}
export interface Type {
}
export interface TypeScriptRootSegment {
}
export interface Value {
}
export interface FunctionSegment {
}
export interface Value {
}
export interface Assignable {
}
export class Number {
	public serialize() : Node {return new MapNode( "number").withString( "value", this.value);;}
}
export class StructureNode {
	 StructureNode( type : StructureType,  structureNode : StructureValue<Type, TypescriptStructureMember>) : public {this.type=type;this.value=structureNode;;}
	private serializeImplementsParams() : Node {return this.value.maybeImplemented( ).map( 0).orElse( new MapNode( ));;}
	private serializeExtendedParams() : Node {return this.value.maybeExtended( ).map( 0).orElse( new MapNode( ));;}
	private serializeTypeParams() : Node {return this.value.maybeTypeParams( ).map( 0).orElse( new MapNode( ));;}
	public serialize() : Node {return new MapNode( this.type.name( ).toLowerCase( )).withString( "name", this.value.name( )).withNodeListAndSerializer( "modifiers", this.value.modifiers( ), Serializable.serialize).withNodeListAndSerializer( "members", this.value.members( ), Serializable.serialize).merge( this.serializeTypeParams( )).merge( this.serializeExtendedParams( )).merge( this.serializeImplementsParams( ));;}
}
export class TypeScriptImport {
	public serialize() : Node {return new MapNode( "import").withNodeListAndSerializer( "values", this.values, Segment.serialize).withNodeListAndSerializer( "segments", this.segments, Segment.serialize);;}
}
export class TypescriptRoot {
	public serialize() : Node {return new MapNode( "root").withNodeListAndSerializer( "children", this.children, TypeScriptRootSegment.serialize);;}
}
export class TypescriptMethod {
	public serialize() : Node { let node : var=new MapNode( "method").withNodeSerialized( "header", this.header);return this.maybeChildren.map( 0).orElse( node);;}
}
export class TemplateType {
	 TemplateType( base : JavaLang.Symbol,  maybeTypeArguments : Option<List<Type>>) : public {this.base=base;this.maybeTypeArguments=maybeTypeArguments;;}
	public serialize() : Node { let node : var=new MapNode( "template").withNodeSerialized( "base", this.base);return this.maybeTypeArguments.map( 0).orElse( node);;}
}
export class TypescriptConditional {
	 TypescriptConditional( type : ConditionalType,  condition : Value) : public {super( type, condition);;}
	public serialize() : Node {return new MapNode( this.type.name( ).toLowerCase( )).withNodeSerialized( "condition", this.condition);;}
}
export class Block {
	 Block( header : TypescriptBlockHeader,  segments : List<FunctionSegment>) : public {super( header, segments);;}
	public serialize() : Node {return new MapNode( "block").withNodeSerialized( "header", this.header).withNodeListSerialized( "children", this.children);;}
}
export class TypescriptConstructor {
	public serialize() : Node {return new MapNode( "constructor");;}
}
export class Definition {
	 Definition( maybeAnnotations : Option<List<Annotation>>,  modifiers : List<Modifier>,  name : String,  maybeTypeParams : Option<List<TypeParam>>,  type : Type) : public {super( maybeAnnotations, modifiers, name, maybeTypeParams, type);;}
	public serialize() : Node { let node : var=new MapNode( "definition").withString( "name", this.name).withNodeSerialized( "type", this.type);if(true){ return node;;}if(true){ return node.withNodeListSerialized( "modifiers", this.modifiers);;};}
	public withModifier( modifier : Modifier) : Definition {return new Definition( this.maybeAnnotations, this.modifiers.addLast( modifier), this.name, this.maybeTypeParams, this.type);;}
}
export class ArrayType {
	 ArrayType( arrayType : Type) : public {this.childType=arrayType;;}
	public serialize() : Node {return new MapNode( "array").withNodeSerialized( "child", this.childType);;}
}
export class TypeParam {
	public static deserialize( node : Node) : CompileResult<TypeParam> {return Destructors.destruct( node).withString( "value").complete( TypeParam.new);;}
	public serialize() : Node {return new MapNode( ).withString( "value", this.value);;}
}
export class Construction {
	public serialize() : Node {return new MapNode( "construction").withNodeSerialized( "type", this.type);;}
}
export class Post {
	public serialize() : Node {return new MapNode( this.variant.type( )).withNodeSerialized( "child", this.value);;}
}
export class Access {
	public serialize() : Node {return new MapNode( "data-access").withNodeSerialized( "receiver", this.receiver).withString( "property", this.property);;}
}
export class Break {
	public serialize() : Node {return new MapNode( "break");;}
}
export class Continue {
	public serialize() : Node {return new MapNode( "continue");;}
}
export class Return {
	 Return( child : TypescriptLang.Value) : public {super( child);;}
	public serialize() : Node {return new MapNode( "return").withNodeSerialized( "child", this.child);;}
}
export class Invokable {
	 Invokable( caller : Caller,  arguments : List<Argument>) : public {super( caller, arguments);;}
	public serialize() : Node {return new MapNode( "invokable").withNodeSerialized( "caller", this.caller).withNodeListSerialized( "arguments", this.arguments);;}
}
export class Char {
	public serialize() : Node {return new MapNode( "char").withString( "value", this.value);;}
}
export class Index {
	public serialize() : Node {return new MapNode( "index").withNodeSerialized( "parent", this.parent).withNodeSerialized( "argument", this.argument);;}
}
export class Not {
	public serialize() : Node {return new MapNode( "not").withNodeSerialized( "child", this.child);;}
}
export class StringValue {
	public serialize() : Node {return new MapNode( "string").withString( "value", this.value);;}
}
export class Symbol {
	public serialize() : Node {return new MapNode( "symbol").withString( "value", this.value);;}
}
export class Operation {
	public serialize() : Node {return new MapNode( this.operator.type( )).withNodeSerialized( "left", this.left).withNodeSerialized( "right", this.right);;}
}
export class Whitespace {
	public serialize() : Node {return new MapNode( "whitespace");;}
}
export class Assignment {
	public serialize() : Node {return new MapNode( "assignment").withNodeSerialized( "destination", this.assignable).withNodeSerialized( "source", this.value);;}
}
export class TypescriptLang {
}
