import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { Invokable } from "../../../../magmac/app/lang/java/Invokable";
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
	serialize() : Node {return 0;;}
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
export class NumberNode {
	serialize() : Node {return 0;;}
}
export class TypescriptStructureNode {
	TypescriptStructureNode(type : TypescriptStructureType, structureNode : StructureValue<TypeScriptType, TypescriptStructureMember>) : public {break;break;;}
	serializeImplementsParams() : Node {return 0;;}
	serializeExtendedParams() : Node {return 0;;}
	serializeTypeParams() : Node {return 0;;}
	serialize() : Node {return 0;;}
}
export class TypeScriptImport {
	serialize() : Node {return 0;;}
}
export class TypescriptRoot {
	serialize() : Node {return 0;;}
}
export class TypescriptMethod {
	serialize() : Node {break;return 0;;}
}
export class TypeScriptTemplateType {
	TypeScriptTemplateType(base : JavaLang.JavaSymbol, typeArguments : TypeArguments<TypeScriptType>) : public {break;break;;}
	serialize() : Node {return 0;;}
}
export class TypescriptConditional {
	TypescriptConditional(type : ConditionalType, condition : TypescriptValue) : public {0( 0, 0);;}
	serialize() : Node {return 0;;}
}
export class TypescriptBlock {
	TypescriptBlock(header : TypescriptBlockHeader, segments : List<TypescriptFunctionSegment>) : public {0( 0, 0);;}
	serialize() : Node {return 0;;}
}
export class TypescriptConstructor {
	serialize() : Node {return 0;;}
}
export class TypeScriptDefinition {
	TypeScriptDefinition(definition : CommonLang.Definition<TypeScriptType>) : public {break;;}
	serialize() : Node {return 0;;}
}
export class TypescriptArrayType {
	TypescriptArrayType(arrayType : TypeScriptType) : public {break;;}
	serialize() : Node {return 0;;}
}
export class TypeParam {
	deserialize(node : Node) : CompileResult<TypeParam> {return 0;;}
	serialize() : Node {return 0;;}
}
export class Construction {
	serialize() : Node {return 0;;}
}
export class Post {
	serialize() : Node {return 0;;}
}
export class Access {
	serialize() : Node {return 0;;}
}
export class TypescriptBreak {
	serialize() : Node {return 0;;}
}
export class TypescriptContinue {
	serialize() : Node {return 0;;}
}
export class TypescriptReturnNode {
	TypescriptReturnNode(child : TypescriptValue) : public {0( 0);;}
	serialize() : Node {return 0;;}
}
export class TypescriptInvokable {
	TypescriptInvokable(caller : TypescriptCaller, arguments : List<TypescriptArgument>) : public {0( 0, 0);;}
	serialize() : Node {return 0;;}
}
export class Char {
	serialize() : Node {return 0;;}
}
export class TypescriptLang {
}
