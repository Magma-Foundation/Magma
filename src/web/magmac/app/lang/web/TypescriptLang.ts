import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
import { AbstractReturnNode } from "../../../../magmac/app/lang/node/AbstractReturnNode";
import { Conditional } from "../../../../magmac/app/lang/node/Conditional";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { Operator } from "../../../../magmac/app/lang/node/Operator";
import { ParameterizedMethodHeader } from "../../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { PostVariant } from "../../../../magmac/app/lang/node/PostVariant";
import { Segment } from "../../../../magmac/app/lang/node/Segment";
import { StructureValue } from "../../../../magmac/app/lang/node/StructureValue";
import { TypeArguments } from "../../../../magmac/app/lang/node/TypeArguments";
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
export interface TypeScriptType {
}
export interface TypeScriptRootSegment {
}
export interface Value {
}
export interface FunctionSegment {
}
export interface Value {
}
export class Number {
	serialize() : Node {return new MapNode( "number").withString( "value", this.value);;}
}
export class TypescriptStructureNode {
	TypescriptStructureNode(type : TypescriptStructureType, structureNode : StructureValue<TypeScriptType, TypescriptStructureMember>) : public {break;break;;}
	serializeImplementsParams() : Node {return this.value.maybeImplemented( ).map( 0).orElse( new MapNode( ));;}
	serializeExtendedParams() : Node {return this.value.maybeExtended( ).map( 0).orElse( new MapNode( ));;}
	serializeTypeParams() : Node {return this.value.maybeTypeParams( ).map( 0).orElse( new MapNode( ));;}
	serialize() : Node {return new MapNode( this.type.name( ).toLowerCase( )).withString( "name", this.value.name( )).withNodeListAndSerializer( "modifiers", this.value.modifiers( ), Serializable.serialize).withNodeListAndSerializer( "members", this.value.members( ), Serializable.serialize).merge( this.serializeTypeParams( )).merge( this.serializeExtendedParams( )).merge( this.serializeImplementsParams( ));;}
}
export class TypeScriptImport {
	serialize() : Node {return new MapNode( "import").withNodeListAndSerializer( "values", this.values, Segment.serialize).withNodeListAndSerializer( "segments", this.segments, Segment.serialize);;}
}
export class TypescriptRoot {
	serialize() : Node {return new MapNode( "root").withNodeListAndSerializer( "children", this.children, TypeScriptRootSegment.serialize);;}
}
export class TypescriptMethod {
	serialize() : Node {break;return this.maybeChildren.map( 0).orElse( node);;}
}
export class TypeScriptTemplateType {
	TypeScriptTemplateType(base : JavaLang.Symbol, typeArguments : TypeArguments<TypeScriptType>) : public {break;break;;}
	serialize() : Node {return new MapNode( "template").withNodeSerialized( "base", this.base).withNodeListSerialized( "arguments", this.typeArguments.arguments( ));;}
}
export class TypescriptConditional {
	TypescriptConditional(type : ConditionalType, condition : Value) : public {super( type, condition);;}
	serialize() : Node {return new MapNode( this.type.name( ).toLowerCase( )).withNodeSerialized( "condition", this.condition);;}
}
export class Block {
	Block(header : TypescriptBlockHeader, segments : List<FunctionSegment>) : public {super( header, segments);;}
	serialize() : Node {return new MapNode( "block").withNodeSerialized( "header", this.header).withNodeListSerialized( "children", this.children);;}
}
export class TypescriptConstructor {
	serialize() : Node {return new MapNode( "constructor");;}
}
export class TypeScriptDefinition {
	TypeScriptDefinition(definition : CommonLang.Definition<TypeScriptType>) : public {break;;}
	serialize() : Node {return new MapNode( "definition").withString( "name", this.definition.name( )).withNodeSerialized( "type", this.definition.type( ));;}
}
export class TypescriptArrayType {
	TypescriptArrayType(arrayType : TypeScriptType) : public {break;;}
	serialize() : Node {return new MapNode( "array").withNodeSerialized( "child", this.childType);;}
}
export class TypeParam {
	deserialize(node : Node) : CompileResult<TypeParam> {return Destructors.destruct( node).withString( "value").complete( TypeParam.new);;}
	serialize() : Node {return new MapNode( ).withString( "value", this.value);;}
}
export class Construction {
	serialize() : Node {return new MapNode( "construction").withNodeSerialized( "type", this.type);;}
}
export class Post {
	serialize() : Node {return new MapNode( this.variant.type( )).withNodeSerialized( "child", this.value);;}
}
export class Access {
	serialize() : Node {return new MapNode( "data-access").withNodeSerialized( "receiver", this.receiver).withString( "property", this.property);;}
}
export class Break {
	serialize() : Node {return new MapNode( "break");;}
}
export class Continue {
	serialize() : Node {return new MapNode( "continue");;}
}
export class Return {
	Return(child : TypescriptLang.Value) : public {super( child);;}
	serialize() : Node {return new MapNode( "return").withNodeSerialized( "child", this.child);;}
}
export class Invokable {
	Invokable(caller : TypescriptCaller, arguments : List<Argument>) : public {super( caller, arguments);;}
	serialize() : Node {return new MapNode( "invokable").withNodeSerialized( "caller", this.caller).withNodeListSerialized( "arguments", this.arguments);;}
}
export class Char {
	serialize() : Node {return new MapNode( "char").withString( "value", this.value);;}
}
export class Index {
	serialize() : Node {return new MapNode( "index").withNodeSerialized( "parent", this.parent).withNodeSerialized( "argument", this.argument);;}
}
export class Not {
	serialize() : Node {return new MapNode( "not").withNodeSerialized( "child", this.child);;}
}
export class StringValue {
	serialize() : Node {return new MapNode( "string").withString( "value", this.value);;}
}
export class Symbol {
	serialize() : Node {return new MapNode( "symbol").withString( "value", this.value);;}
}
export class Operation {
	serialize() : Node {return new MapNode( this.operator.type( )).withNodeSerialized( "left", this.left).withNodeSerialized( "right", this.right);;}
}
export class Whitespace {
	serialize() : Node {return new MapNode( "whitespace");;}
}
export class TypescriptLang {
}
