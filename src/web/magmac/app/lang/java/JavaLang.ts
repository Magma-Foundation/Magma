import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompoundDestructor } from "../../../../magmac/app/compile/node/CompoundDestructor";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { CommonRules } from "../../../../magmac/app/lang/CommonRules";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { MutableLazyRule } from "../../../../magmac/app/lang/MutableLazyRule";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { Conditional } from "../../../../magmac/app/lang/node/Conditional";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { Deserializer } from "../../../../magmac/app/lang/node/Deserializer";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
import { Lambda } from "../../../../magmac/app/lang/node/Lambda";
import { LambdaValueContent } from "../../../../magmac/app/lang/node/LambdaValueContent";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { NumberNode } from "../../../../magmac/app/lang/node/NumberNode";
import { Operator } from "../../../../magmac/app/lang/node/Operator";
import { Parameters } from "../../../../magmac/app/lang/node/Parameters";
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
export interface JavaValue {
}
export interface JavaAssignable {
}
export interface JavaBlockHeader {
}
export interface JavaBase {
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
	JavaArrayType(arrayType : JavaType) : public {break;;}
	createArrayRule(rule : Rule) : Rule {break;return new TypeRule( "array", new StripRule( new SuffixRule( child, "[]")));;}
	deserialize(node : Node) : Option<CompileResult<JavaType>> {return Destructors.destructWithType( "array", node).map( 0);;}
}
export class JavaRoot {
	getChildren(node : Node, deserializer : Deserializer<JavaRootSegment>) : CompileResult<JavaRoot> {return Destructors.destruct( node).withNodeList( "children", deserializer).complete( JavaRoot.new);;}
}
export class JavaLambda {
	JavaLambda(header : JavaLambdaHeader, content : JavaLambdaContent) : public {super( header, content);;}
}
export class Access {
	Access(type : JavaAccessType, receiver : JavaValue, property : String) : public {super( type, receiver, property);;}
}
export class JavaLambdaValueContent {
	JavaLambdaValueContent(value : JavaValue) : public {super( value);;}
}
export class JavaLambdaBlockContent {
	deserialize(node : Node) : Option<CompileResult<JavaLambdaBlockContent>> {return Destructors.destructWithType( "block", node).map( 0);;}
}
export class JavaConditional {
	JavaConditional(type : ConditionalType, condition : JavaValue) : public {super( type, condition);;}
}
export class JavaTemplateType {
	JavaTemplateType(base : JavaBase, typeArguments : TypeArguments<JavaType>) : public {break;break;;}
	deserialize(node : Node) : Option<CompileResult<JavaTemplateType>> {return Destructors.destructWithType( "template", node).map( 0);;}
	deserializeBase(node : Node) : CompileResult<JavaBase> {return Deserializers.orError( "base", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( JavaQualified.deserializeQualified)));;}
	createTemplateRule(type : Rule) : Rule {break;break;return new TypeRule( "template", new StripRule( new SuffixRule( LocatingRule.First( base, "<", arguments), ">")));;}
}
export class JavaStructureNodeDeserializer {
	deserializeHelper(type : JavaStructureType, deserializer : InitialDestructor) : CompileResult<JavaStructureNode> {return JavaStructureNodeDeserializer.attachOptionals( JavaStructureNodeDeserializer.attachRequired( deserializer)).complete( 0);;}
	attachRequired(deserializer : InitialDestructor) : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> {return deserializer.withString( "name").withNodeList( "modifiers", Modifier.deserialize).withNodeList( "children", StructureMembers.deserialize);;}
	attachOptionals(attachRequired : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>>) : CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> {return attachRequired.withNodeListOptionally( "implemented", JavaTypes.deserialize).withNodeListOptionally( "type-parameters", TypescriptLang.TypeParam.deserialize).withNodeListOptionally( "parameters", Parameters.deserialize).withNodeListOptionally( "extended", JavaTypes.deserialize).withNodeListOptionally( "variants", JavaTypes.deserialize);;}
	from(type : JavaStructureType, tuple : Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>) : JavaStructureNode {return new JavaStructureNode( type, new StructureValue<JavaType, JavaStructureMember>( tuple.left( ).left( ).left( ).left( ).left( ).left( ).left( ), tuple.left( ).left( ).left( ).left( ).left( ).left( ).right( ), tuple.left( ).left( ).left( ).left( ).left( ).right( ), tuple.left( ).left( ).left( ).right( ), tuple.left( ).right( ), tuple.left( ).left( ).left( ).left( ).right( )), tuple.left( ).left( ).right( ), tuple.right( ));;}
	deserialize(node : Node) : Option<CompileResult<JavaStructureNode>> {return Destructors.destructWithType( this.type( ).name( ).toLowerCase( ), node).map( 0);;}
}
export class JavaTypes {
	deserialize(node : Node) : CompileResult<JavaType> {return Deserializers.orError( "type", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( JavaTemplateType.deserialize), Deserializers.wrap( JavaVariadicType.deserialize), Deserializers.wrap( JavaArrayType.deserialize), Deserializers.wrap( JavaQualified.deserializeQualified)));;}
	createTypeRule() : Rule {break;return type.set( new OrRule( Lists.of( JavaVariadicType.createVariadicRule( type), JavaArrayType.createArrayRule( type), JavaTemplateType.createTemplateRule( type), CommonRules.createSymbolRule( ), JavaQualified.createQualifiedRule( ))));;}
}
export class JavaTry {
}
export class JavaElse {
}
export class JavaCatch {
	deserialize(node : Node) : Option<CompileResult<JavaBlockHeader>> {return Destructors.destructWithType( "catch", node).map( 0);;}
}
export class JavaQualified {
	deserializeQualified(node : Node) : Option<CompileResult<JavaQualified>> {return Destructors.destructWithType( "qualified", node).map( 0);;}
	createQualifiedRule() : TypeRule {return new TypeRule( "qualified", JavaQualified.createSegmentsRule( "segments"));;}
	createSegmentsRule(key : String) : Rule {return NodeListRule.createNodeListRule( key, new DelimitedFolder( '.'), CommonRules.createSymbolRule( "value"));;}
	serialize() : Node {return new MapNode( "qualified").withNodeListAndSerializer( "segments", this.segments, Segment.serialize);;}
}
export class JavaVariadicType {
	deserialize(node : Node) : Option<CompileResult<JavaType>> {return Destructors.destructWithType( "variadic", node).map( 0);;}
	createVariadicRule(rule : Rule) : Rule {break;return new TypeRule( "variadic", new StripRule( new SuffixRule( child, "...")));;}
}
export class Number {
	Number(value : String) : public {super( value);;}
}
export class StringValue {
	StringValue(value : String) : public {super( value);;}
}
export class JavaOperation {
	serialize() : Node {return new MapNode( this.operator.type( ));;}
}
export class Char {
}
export class Not {
}
export class Index {
}
export class JavaSwitchNode {
}
export class JavaDefinition {
}
export class JavaMultipleHeader {
}
export class JavaWhitespace {
}
export class JavaLang {
}
