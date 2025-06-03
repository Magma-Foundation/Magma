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
import { Access } from "../../../../magmac/app/lang/common/Access";
import { Symbol } from "../../../../magmac/app/lang/common/Symbol";
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
	Invokable(caller : JavaCaller, arguments : List<JavaArgument>) : public {0( 0, 0);;}
}
export class JavaSymbol {
	JavaSymbol(value : String) : public {0( 0);;}
}
export class JavaArrayType {
	JavaArrayType(arrayType : JavaType) : public {break;;}
	createArrayRule(rule : Rule) : Rule {break;return new TypeRule( 0, new StripRule( new SuffixRule( 0, 0)));;}
	deserialize(node : Node) : Option<CompileResult<JavaType>> {return 0.destructWithType( 0, 0).map( 0);;}
}
export class JavaRoot {
	getChildren(node : Node, deserializer : Deserializer<JavaRootSegment>) : CompileResult<JavaRoot> {return 0.destruct( 0).withNodeList( 0, 0).complete( 0.new);;}
}
export class JavaLambda {
	JavaLambda(header : JavaLambdaHeader, content : JavaLambdaContent) : public {0( 0, 0);;}
}
export class JavaAccess {
	JavaAccess(type : JavaAccessType, receiver : JavaValue, property : String) : public {0( 0, 0, 0);;}
}
export class JavaLambdaValueContent {
	JavaLambdaValueContent(value : JavaValue) : public {0( 0);;}
}
export class JavaLambdaBlockContent {
	deserialize(node : Node) : Option<CompileResult<JavaLambdaBlockContent>> {return 0.destructWithType( 0, 0).map( 0);;}
}
export class JavaConditional {
	JavaConditional(type : ConditionalType, condition : JavaValue) : public {0( 0, 0);;}
}
export class JavaTemplateType {
	JavaTemplateType(base : JavaBase, typeArguments : TypeArguments<JavaType>) : public {break;break;;}
	deserialize(node : Node) : Option<CompileResult<JavaTemplateType>> {return 0.destructWithType( 0, 0).map( 0);;}
	deserializeBase(node : Node) : CompileResult<JavaBase> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeSymbol), 0.wrap( 0.deserializeQualified)));;}
	createTemplateRule(type : Rule) : Rule {break;break;return new TypeRule( 0, new StripRule( new SuffixRule( 0.First( 0, 0, 0), 0)));;}
}
export class JavaStructureNodeDeserializer {
	deserializeHelper(type : JavaStructureType, deserializer : InitialDestructor) : CompileResult<JavaStructureNode> {return 0.attachOptionals( 0.attachRequired( 0)).complete( 0);;}
	attachRequired(deserializer : InitialDestructor) : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> {return 0.withString( 0).withNodeList( 0, 0.deserialize).withNodeList( 0, 0.deserialize);;}
	attachOptionals(attachRequired : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>>) : CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> {return 0.withNodeListOptionally( 0, 0.deserialize).withNodeListOptionally( 0, 0.TypeParam.deserialize).withNodeListOptionally( 0, 0.deserialize).withNodeListOptionally( 0, 0.deserialize).withNodeListOptionally( 0, 0.deserialize);;}
	from(type : JavaStructureType, tuple : Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>) : JavaStructureNode {return new JavaStructureNode( 0, new StructureValue<JavaType, JavaStructureMember>( 0.left( ).left( ).left( ).left( ).left( ).left( ).left( ), 0.left( ).left( ).left( ).left( ).left( ).left( ).right( ), 0.left( ).left( ).left( ).left( ).left( ).right( ), 0.left( ).left( ).left( ).right( ), 0.left( ).right( ), 0.left( ).left( ).left( ).left( ).right( )), 0.left( ).left( ).right( ), 0.right( ));;}
	deserialize(node : Node) : Option<CompileResult<JavaStructureNode>> {return 0.destructWithType( 0.type( ).name( ).toLowerCase( ), 0).map( 0);;}
}
export class JavaTypes {
	deserialize(node : Node) : CompileResult<JavaType> {return 0.orError( 0, 0, 0.of( 0.wrap( 0.deserializeSymbol), 0.wrap( 0.deserialize), 0.wrap( 0.deserialize), 0.wrap( 0.deserialize), 0.wrap( 0.deserializeQualified)));;}
	createTypeRule() : Rule {break;return 0.set( new OrRule( 0.of( 0.createVariadicRule( 0), 0.createArrayRule( 0), 0.createTemplateRule( 0), 0.createSymbolRule( ), 0.createQualifiedRule( ))));;}
}
export class JavaTry {
}
export class JavaElse {
}
export class JavaCatch {
	deserialize(node : Node) : Option<CompileResult<JavaBlockHeader>> {return 0.destructWithType( 0, 0).map( 0);;}
}
export class JavaQualified {
	deserializeQualified(node : Node) : Option<CompileResult<JavaQualified>> {return 0.destructWithType( 0, 0).map( 0);;}
	createQualifiedRule() : TypeRule {return new TypeRule( 0, 0.createSegmentsRule( 0));;}
	createSegmentsRule(key : String) : Rule {return 0.createNodeListRule( 0, new DelimitedFolder( '.'), 0.createSymbolRule( 0));;}
	serialize() : Node {return new MapNode( 0).withNodeListAndSerializer( 0, 0.segments, 0.serialize);;}
}
export class JavaVariadicType {
	deserialize(node : Node) : Option<CompileResult<JavaType>> {return 0.destructWithType( 0, 0).map( 0);;}
	createVariadicRule(rule : Rule) : Rule {break;return new TypeRule( 0, new StripRule( new SuffixRule( 0, 0)));;}
}
export class JavaNumberNode {
	JavaNumberNode(value : String) : public {0( 0);;}
}
export class JavaStringNode {
	JavaStringNode(value : String) : public {0( 0);;}
}
export class JavaOperation {
	serialize() : Node {return new MapNode( 0.operator.type( ));;}
}
export class JavaCharNode {
}
export class JavaNot {
}
export class JavaIndexNode {
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
