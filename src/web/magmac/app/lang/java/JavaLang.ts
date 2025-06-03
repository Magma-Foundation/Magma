import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompoundDestructor } from "../../../../magmac/app/compile/node/CompoundDestructor";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { AbstractDefinition } from "../../../../magmac/app/lang/CommonLang/AbstractDefinition";
import { CommonRules } from "../../../../magmac/app/lang/CommonRules";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { AbstractFunctionStatement } from "../../../../magmac/app/lang/common/AbstractFunctionStatement";
import { Annotation } from "../../../../magmac/app/lang/common/Annotation";
import { AbstractReturnNode } from "../../../../magmac/app/lang/node/AbstractReturnNode";
import { CaseDefinition } from "../../../../magmac/app/lang/node/CaseDefinition";
import { CaseValue } from "../../../../magmac/app/lang/node/CaseValue";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { Deserializer } from "../../../../magmac/app/lang/node/Deserializer";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
import { LambdaValueContent } from "../../../../magmac/app/lang/node/LambdaValueContent";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { NumberNode } from "../../../../magmac/app/lang/node/NumberNode";
import { Operator } from "../../../../magmac/app/lang/node/Operator";
import { Segment } from "../../../../magmac/app/lang/node/Segment";
import { StructureMembers } from "../../../../magmac/app/lang/node/StructureMembers";
import { StructureStatementValue } from "../../../../magmac/app/lang/node/StructureStatementValue";
import { StructureValue } from "../../../../magmac/app/lang/node/StructureValue";
import { TypeArguments } from "../../../../magmac/app/lang/node/TypeArguments";
import { TypedDeserializer } from "../../../../magmac/app/lang/node/TypedDeserializer";
import { TypescriptLang } from "../../../../magmac/app/lang/web/TypescriptLang";
export interface JavaArgument {
}
export interface JavaCaller {
}
export interface Assignable {
}
export interface Value {
}
export interface BlockHeader {
}
export interface Base {
}
export interface JavaLambdaContent {
}
export interface JavaType {
}
export interface JavaLambdaHeader {
}
export interface JavaLambdaParameter {
}
export class Invokable {
	Invokable(caller : JavaCaller, arguments : List<JavaArgument>) : public {super( caller, arguments);;}
}
export class Symbol {
	Symbol(value : String) : public {super( value);;}
}
export class JavaArrayType {
	constructor(arrayType : JavaType) {this.inner=arrayType;;}
	createArrayRule(rule : Rule) : Rule {child : var=new NodeRule( "child", rule);return new TypeRule( "array", new StripRule( new SuffixRule( child, "[]")));;}
	deserialize(node : Node) : Option<CompileResult<JavaType>> {return Destructors.destructWithType( "array", node).map( 0);;}
}
export class JavaRoot {
	getChildren(node : Node, deserializer : Deserializer<JavaRootSegment>) : CompileResult<JavaRoot> {return Destructors.destruct( node).withNodeList( "children", deserializer).complete( JavaRoot.new);;}
}
export class Lambda {
	Lambda(header : JavaLambdaHeader, content : JavaLambdaContent) : public {super( header, content);;}
}
export class Access {
	Access(type : JavaAccessType, receiver : Value, property : String) : public {super( type, receiver, property);;}
}
export class JavaLambdaValueContent {
	JavaLambdaValueContent(value : Value) : public {super( value);;}
}
export class JavaLambdaBlockContent {
	deserialize(node : Node) : Option<CompileResult<JavaLambdaBlockContent>> {return Destructors.destructWithType( "block", node).map( 0);;}
}
export class Conditional {
	Conditional(type : ConditionalType, condition : Value) : public {super( type, condition);;}
}
export class JavaTemplateType {
}
export class JavaStructureNodeDeserializer {
	deserializeHelper(type : JavaStructureType, deserializer : InitialDestructor) : CompileResult<StructureNode> {return JavaStructureNodeDeserializer.attachOptionals( JavaStructureNodeDeserializer.attachRequired( deserializer)).complete( 0);;}
	attachRequired(deserializer : InitialDestructor) : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> {return deserializer.withString( "name").withNodeList( "modifiers", Modifier.deserialize).withNodeList( "children", StructureMembers.deserialize);;}
	attachOptionals(attachRequired : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>>) : CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> {return attachRequired.withNodeListOptionally( "implemented", JavaDeserializers.deserializeType).withNodeListOptionally( "type-parameters", TypescriptLang.TypeParam.deserialize).withNodeListOptionally( "parameters", JavaDeserializers.deserializeParameter).withNodeListOptionally( "extended", JavaDeserializers.deserializeType).withNodeListOptionally( "variants", JavaDeserializers.deserializeType);;}
	from(type : JavaStructureType, tuple : Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>) : StructureNode {return new StructureNode( type, new StructureValue<JavaType, JavaStructureMember>( tuple.left( ).left( ).left( ).left( ).left( ).left( ).left( ), tuple.left( ).left( ).left( ).left( ).left( ).left( ).right( ), tuple.left( ).left( ).left( ).left( ).left( ).right( ), tuple.left( ).left( ).left( ).right( ), tuple.left( ).right( ), tuple.left( ).left( ).left( ).left( ).right( )), tuple.left( ).left( ).right( ), tuple.right( ));;}
	deserialize(node : Node) : Option<CompileResult<StructureNode>> {return Destructors.destructWithType( this.type( ).name( ).toLowerCase( ), node).map( 0);;}
}
export class Try {
}
export class Else {
}
export class Catch {
	deserialize(node : Node) : Option<CompileResult<BlockHeader>> {return Destructors.destructWithType( "catch", node).map( 0);;}
}
export class Qualified {
	deserializeQualified(node : Node) : Option<CompileResult<Qualified>> {return Destructors.destructWithType( "qualified", node).map( 0);;}
	createQualifiedRule() : TypeRule {return new TypeRule( "qualified", Qualified.createSegmentsRule( ));;}
	createSegmentsRule() : Rule {return NodeListRule.createNodeListRule( "segments", new DelimitedFolder( '.'), CommonRules.createSymbolRule( "value"));;}
	serialize() : Node {return new MapNode( "qualified").withNodeListAndSerializer( "segments", this.segments, Segment.serialize);;}
}
export class JavaVariadicType {
	deserialize(node : Node) : Option<CompileResult<JavaType>> {return Destructors.destructWithType( "variadic", node).map( 0);;}
	createVariadicRule(rule : Rule) : Rule {child : var=new NodeRule( "child", rule);return new TypeRule( "variadic", new StripRule( new SuffixRule( child, "...")));;}
}
export class Number {
	Number(value : String) : public {super( value);;}
}
export class StringValue {
	StringValue(value : String) : public {super( value);;}
}
export class operation {
	serialize() : Node {return new MapNode( this.operator.type( ));;}
}
export class Char {
}
export class Not {
}
export class Index {
}
export class SwitchNode {
}
export class Definition {
	Definition(maybeAnnotations : Option<List<Annotation>>, modifiers : List<Modifier>, name : String, maybeTypeParams : Option<List<TypescriptLang.TypeParam>>, type : JavaType) : public {super( maybeAnnotations, modifiers, name, maybeTypeParams, type);;}
}
export class JavaMultipleHeader {
}
export class Whitespace {
}
export class FunctionStatement {
	FunctionStatement(child : JavaFunctionSegmentValue) : public {super( child);;}
}
export class Return {
	Return(child : Value) : public {super( child);;}
}
export class Case {
}
export class Block {
	Block(header : BlockHeader, segments : List<JavaFunctionSegment>) : public {super( header, segments);;}
}
export class Assignment {
}
export class InstanceOf {
}
export class StructureNode {
	constructor(type : JavaStructureType, structureNode : StructureValue<JavaType, JavaStructureMember>, parameters : Option<List<JavaParameter>>, variants : Option<List<JavaType>>) {this.type=type;this.value=structureNode;;}
	type() : JavaStructureType {return this.type;;}
	name() : String {return this.value.name( );;}
	implemented() : Option<List<JavaType>> {return this.value.maybeImplemented( );;}
	extended() : Option<List<JavaType>> {return this.value.maybeExtended( );;}
}
export class JavaLang {
}
