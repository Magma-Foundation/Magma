import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
import { Block } from "../../../../magmac/app/lang/node/Block";
import { Conditional } from "../../../../magmac/app/lang/node/Conditional";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { ParameterizedMethodHeader } from "../../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { PostVariant } from "../../../../magmac/app/lang/node/PostVariant";
import { ReturnNode } from "../../../../magmac/app/lang/node/ReturnNode";
import { Segment } from "../../../../magmac/app/lang/node/Segment";
import { StructureValue } from "../../../../magmac/app/lang/node/StructureValue";
import { TypeArguments } from "../../../../magmac/app/lang/node/TypeArguments";
export class TypescriptWhitespace {
	serialize() : Node {return new MapNode( "whitespace");;}
}
export interface TypescriptArgument {
}
export interface TypeScriptParameter {
}
export interface TypescriptBlockHeader {
}
export interface TypescriptStructureMember {
}
export interface TypescriptLambdaContent {
}
export interface TypeScriptMethodHeader {
}
export interface TypeScriptType {
}
export interface TypeScriptRootSegment {
}
export interface TypescriptFunctionSegment {
}
export interface TypescriptValue {
}
export class Number {
	serialize() : Node {return new MapNode( "number").withString( "value", 0.value);;}
}
export class TypescriptStructureNode {
	TypescriptStructureNode(type : TypescriptStructureType, structureNode : StructureValue<TypeScriptType, TypescriptStructureMember>) : public {break;break;;}
	serializeImplementsParams() : Node {return 0.value.maybeImplemented( ).map( 0).orElse( new MapNode( ));;}
	serializeExtendedParams() : Node {return 0.value.maybeExtended( ).map( 0).orElse( new MapNode( ));;}
	serializeTypeParams() : Node {return 0.value.maybeTypeParams( ).map( 0).orElse( new MapNode( ));;}
	serialize() : Node {return new MapNode( 0.type.name( ).toLowerCase( )).withString( "name", 0.value.name( )).withNodeListAndSerializer( "modifiers", 0.value.modifiers( ), 0.serialize).withNodeListAndSerializer( "members", 0.value.members( ), 0.serialize).merge( 0.serializeTypeParams( )).merge( 0.serializeExtendedParams( )).merge( 0.serializeImplementsParams( ));;}
}
export class TypeScriptImport {
	serialize() : Node {return new MapNode( "import").withNodeListAndSerializer( "values", 0.values, 0.serialize).withNodeListAndSerializer( "segments", 0.segments, 0.serialize);;}
}
export class TypescriptRoot {
	serialize() : Node {return new MapNode( "root").withNodeListAndSerializer( "children", 0.children, 0.serialize);;}
}
export class TypescriptMethod {
	serialize() : Node {break;return 0.maybeChildren.map( 0).orElse( 0);;}
}
export class TypeScriptTemplateType {
	TypeScriptTemplateType(base : JavaLang.JavaSymbol, typeArguments : TypeArguments<TypeScriptType>) : public {break;break;;}
	serialize() : Node {return new MapNode( "template").withNodeSerialized( "base", 0.base).withNodeListSerialized( "arguments", 0.typeArguments.arguments( ));;}
}
export class TypescriptConditional {
	TypescriptConditional(type : ConditionalType, condition : TypescriptValue) : public {0( 0, 0);;}
	serialize() : Node {return new MapNode( 0.type.name( ).toLowerCase( )).withNodeSerialized( "condition", 0.condition);;}
}
export class TypescriptBlock {
	TypescriptBlock(header : TypescriptBlockHeader, segments : List<TypescriptFunctionSegment>) : public {0( 0, 0);;}
	serialize() : Node {return new MapNode( "block").withNodeSerialized( "header", 0.header).withNodeListSerialized( "children", 0.children);;}
}
export class TypescriptConstructor {
	serialize() : Node {return new MapNode( "constructor");;}
}
export class TypeScriptDefinition {
	TypeScriptDefinition(definition : CommonLang.Definition<TypeScriptType>) : public {break;;}
	serialize() : Node {return new MapNode( "definition").withString( "name", 0.definition.name( )).withNodeSerialized( "type", 0.definition.type( ));;}
}
export class TypescriptArrayType {
	TypescriptArrayType(arrayType : TypeScriptType) : public {break;;}
	serialize() : Node {return new MapNode( "array").withNodeSerialized( "child", 0.childType);;}
}
export class TypeParam {
	deserialize(node : Node) : CompileResult<TypeParam> {return 0.destruct( 0).withString( "value").complete( 0.new);;}
	serialize() : Node {return new MapNode( ).withString( "value", 0.value);;}
}
export class Construction {
	serialize() : Node {return new MapNode( "construction").withNodeSerialized( "type", 0.type);;}
}
export class Post {
	serialize() : Node {return new MapNode( 0.variant.type( )).withNodeSerialized( "child", 0.value);;}
}
export class Access {
	serialize() : Node {return new MapNode( "data-access").withNodeSerialized( "receiver", 0.receiver).withString( "property", 0.property);;}
}
export class TypescriptBreak {
	serialize() : Node {return new MapNode( "break");;}
}
export class TypescriptContinue {
	serialize() : Node {return new MapNode( "continue");;}
}
export class TypescriptReturnNode {
	TypescriptReturnNode(child : TypescriptValue) : public {0( 0);;}
	serialize() : Node {return new MapNode( "return").withNodeSerialized( "child", 0.child);;}
}
export class Invokable {
	Invokable(caller : TypescriptCaller, arguments : List<TypescriptArgument>) : public {0( 0, 0);;}
	serialize() : Node {return new MapNode( "invokable").withNodeSerialized( "caller", 0.caller).withNodeListSerialized( "arguments", 0.arguments);;}
}
export class Char {
	serialize() : Node {return new MapNode( "char").withString( "value", 0.value);;}
}
export class Index {
	serialize() : Node {return new MapNode( "index").withNodeSerialized( "parent", 0.parent).withNodeSerialized( "argument", 0.argument);;}
}
export class Not {
	serialize() : Node {return new MapNode( "not").withNodeSerialized( "child", 0.child);;}
}
export class StringNode {
	serialize() : Node {return new MapNode( "string").withString( "value", 0.value);;}
}
export class TypescriptLang {
}
