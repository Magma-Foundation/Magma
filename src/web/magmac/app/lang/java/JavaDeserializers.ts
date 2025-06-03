import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Annotation } from "../../../../magmac/app/lang/common/Annotation";
import { Assignables } from "../../../../magmac/app/lang/node/Assignables";
import { CaseDefinition } from "../../../../magmac/app/lang/node/CaseDefinition";
import { CaseValues } from "../../../../magmac/app/lang/node/CaseValues";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { FunctionSegmentValues } from "../../../../magmac/app/lang/node/FunctionSegmentValues";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
import { LambdaContents } from "../../../../magmac/app/lang/node/LambdaContents";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { OperationDeserializer } from "../../../../magmac/app/lang/node/OperationDeserializer";
import { Operator } from "../../../../magmac/app/lang/node/Operator";
import { PostVariant } from "../../../../magmac/app/lang/node/PostVariant";
import { StructureStatementValue } from "../../../../magmac/app/lang/node/StructureStatementValue";
import { TypedDeserializer } from "../../../../magmac/app/lang/node/TypedDeserializer";
import { TypescriptLang } from "../../../../magmac/app/lang/web/TypescriptLang";
import { * } from "../../../../static magmac/app/lang/java/JavaLang/*";
export class JavaDeserializers {
	private static deserializeCaller( node : Node) : CompileResult<JavaCaller> {return Deserializers.orError( "caller", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeConstruction), Deserializers.wrap( JavaDeserializers.deserializeValue)));;}
	private static deserializeConstruction( node : Node) : Option<CompileResult<JavaCaller>> {return Destructors.destructWithType( "construction", node).map( 0);;}
	public static deserializeInvocation( node : Node) : Option<CompileResult<JavaLang.Invokable>> {return Destructors.destructWithType( "invokable", node).map( 0);;}
	public static deserializeRootSegment( node : Node) : CompileResult<JavaRootSegment> {return Deserializers.orError( "root-segment", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeWhitespace), JavaNamespacedNode.deserialize, Deserializers.wrap( new JavaStructureNodeDeserializer( JavaStructureType.Class)), Deserializers.wrap( new JavaStructureNodeDeserializer( JavaStructureType.Interface)), Deserializers.wrap( new JavaStructureNodeDeserializer( JavaStructureType.Record)), Deserializers.wrap( new JavaStructureNodeDeserializer( JavaStructureType.Enum))));;}
	private static deserializeAccess( type : JavaAccessType,  node : Node) : Option<CompileResult<Access>> {return Destructors.destructWithType( type.type( ), node).map( 0);;}
	public static deserializeYield( node : Node) : Option<CompileResult<JavaYieldNode>> {return Destructors.destructWithType( "yield", node).map( 0);;}
	public static deserializePost( variant : PostVariant,  node : Node) : Option<CompileResult<JavaPost>> {return Destructors.destructWithType( variant.type( ), node).map( 0);;}
	private static deserializeLambda( node : Node) : Option<CompileResult<Lambda>> {return Destructors.destructWithType( "lambda", node).map( 0);;}
	private static deserializeAccessWithType( type : JavaAccessType) : TypedDeserializer<Access> {return 0;;}
	public static deserializeFunctionStatement( node : Node) : Option<CompileResult<FunctionStatement>> {return Destructors.destructWithType( "statement", node).map( 0);;}
	public static deserializeReturn( node : Node) : Option<CompileResult<Return>> {return Destructors.destructWithType( "return", node).map( 0);;}
	private static deserializeBlockHeader( node : Node) : CompileResult<BlockHeader> {return Deserializers.orError( "header", node, Lists.of( Deserializers.wrap( 0), Deserializers.wrap( 0), Deserializers.wrap( JavaDeserializers.deserializeElse), Deserializers.wrap( JavaDeserializers.deserializeTry), Deserializers.wrap( Catch.deserialize)));;}
	public static deserializeBlock( node : Node) : Option<CompileResult<Block>> {return Destructors.destructWithType( "block", node).map( 0);;}
	private static deserializeConditional( type : ConditionalType,  node : Node) : Option<CompileResult<Conditional>> {return Destructors.destructWithType( type.name( ).toLowerCase( ), node).map( 0);;}
	public static deserializeStructureStatement( node : Node) : CompileResult<StructureStatementValue> {return Deserializers.orError( "structure-statement-value", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeTypedDefinition), Deserializers.wrap( JavaDeserializers.deserializeAssignment)));;}
	public static deserializeBreak( node : Node) : Option<CompileResult<JavaBreak>> {return Destructors.destructWithType( "break", node).map( 0);;}
	public static deserializeContinue( node : Node) : Option<CompileResult<JavaContinue>> {return Destructors.destructWithType( "continue", node).map( 0);;}
	public static deserializeWhitespace( node : Node) : Option<CompileResult<Whitespace>> {return Destructors.destructWithType( "whitespace", node).map( 0);;}
	private static deserializeTry( node : Node) : Option<CompileResult<Try>> {return Destructors.destructWithType( "try", node).map( 0);;}
	private static deserializeElse( node : Node) : Option<CompileResult<Else>> {return Destructors.destructWithType( "else", node).map( 0);;}
	private static deserializeString( node : Node) : Option<CompileResult<StringValue>> {return Destructors.destructWithType( "string", node).map( 0);;}
	private static deserializeChar( node : Node) : Option<CompileResult<Char>> {return Destructors.destructWithType( "char", node).map( 0);;}
	private static deserializeLambdaHeader( node : Node) : CompileResult<JavaLambdaHeader> {return Deserializers.orError( "lambda-header", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( JavaDeserializers.deserializeMultipleHeader)));;}
	private static deserializeNumber( node : Node) : Option<CompileResult<JavaLang.Number>> {return Destructors.destructWithType( "number", node).map( 0);;}
	private static deserializeNot( node : Node) : Option<CompileResult<Not>> {return Destructors.destructWithType( "not", node).map( 0);;}
	private static deserializeIndex( value : Node) : Option<CompileResult<Index>> {return Destructors.destructWithType( "index", value).map( 0);;}
	private static deserializeSwitch( node : Node) : Option<CompileResult<SwitchNode>> {return Destructors.destructWithType( "switch", node).map( 0);;}
	public static deserializeValue( node : Node) : Option<CompileResult<Value>> { let deserializers : List<TypedDeserializer<Value>>=Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSwitch), Deserializers.wrap( JavaDeserializers.deserializeInvocation), Deserializers.wrap( JavaDeserializers.deserializeString), Deserializers.wrap( JavaDeserializers.deserializeAccessWithType( JavaAccessType.Data)), Deserializers.wrap( JavaDeserializers.deserializeAccessWithType( JavaAccessType.Method)), Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( JavaDeserializers.deserializeChar), Deserializers.wrap( JavaDeserializers.deserializeLambda), Deserializers.wrap( JavaDeserializers.deserializeNumber), Deserializers.wrap( JavaDeserializers.deserializeNot), Deserializers.wrap( JavaDeserializers.deserializeIndex), Deserializers.wrap( JavaDeserializers.deserializeInstanceOf)); let operatorRules : var=Iters.fromValues( Operator.values( )).map( JavaDeserializers.wrapAsDeserializer).collect( new ListCollector<>( ));return Deserializers.or( node, deserializers.addAllLast( operatorRules));;}
	private static deserializeInstanceOf( node : Node) : Option<CompileResult<InstanceOf>> {return Destructors.destructWithType( "instance-of", node).map( 0);;}
	private static deserializeInstanceOfDefinition( node : Node) : CompileResult<InstanceOfDefinition> {return Deserializers.orError( "instance-of-definition", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeInstanceOfWithParameters), Deserializers.wrap( JavaDeserializers.deserializeInstanceOfWithName)));;}
	private static deserializeInstanceOfWithName( node : Node) : Option<CompileResult<InstanceOfDefinitionWithName>> {return Destructors.destructWithType( "with-name", node).map( 0);;}
	private static deserializeInstanceOfWithParameters( node : Node) : Option<CompileResult<InstanceOfDefinitionWithParameters>> {return Destructors.destructWithType( "with-parameters", node).map( 0);;}
	private static wrapAsDeserializer( operator : Operator) : TypedDeserializer<Value> {return Deserializers.wrap( Deserializers.wrap( new OperationDeserializer( operator)));;}
	public static deserializeValueOrError( node : Node) : CompileResult<Value> {return JavaDeserializers.deserializeValue( node).orElseGet( ( )->CompileResults.NodeErr( "Cannot deserialize value", node));;}
	private static deserializeSymbol( node : Node) : Option<CompileResult<Symbol>> {return Destructors.destructWithType( "symbol", node).map( 0);;}
	private static deserializeLambdaParameter( node : Node) : CompileResult<JavaLambdaParameter> {return Deserializers.orError( "lambda-parameter", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( JavaDeserializers.deserializeTypedDefinition)));;}
	public static deserializeDefinition( node : Node) : CompileResult<Definition> {return JavaDeserializers.deserialize0( Destructors.destruct( node));;}
	public static deserializeTypedDefinition( node : Node) : Option<CompileResult<Definition>> {return Destructors.destructWithType( "definition", node).map( JavaDeserializers.deserialize0);;}
	private static deserializeMultipleHeader( node : Node) : Option<CompileResult<JavaLambdaHeader>> {return Destructors.destructWithType( "multiple", node).map( 0);;}
	private static deserialize0( deserialize : InitialDestructor) : CompileResult<Definition> {return deserialize.withString( "name").withNode( "type", JavaDeserializers.deserializeType).withNodeList( "modifiers", Modifier.deserialize).withNodeListOptionally( "annotations", Annotation.deserialize).withNodeListOptionally( "type-parameters", TypescriptLang.TypeParam.deserialize).complete( 0);;}
	public static deserializeCase( node : Node) : Option<CompileResult<Case>> {return Destructors.destructWithType( "case", node).map( 0);;}
	public static deserializeAssignment( node : Node) : Option<CompileResult<Assignment>> {return Destructors.destructWithType( "assignment", node).map( 0);;}
	public static deserializeType( node : Node) : CompileResult<JavaType> {return Deserializers.orError( "type", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( JavaDeserializers.deserializeTemplate), Deserializers.wrap( JavaVariadicType.deserialize), Deserializers.wrap( JavaArrayType.deserialize), Deserializers.wrap( Qualified.deserializeQualified)));;}
	private static deserializeBase( node : Node) : CompileResult<Base> {return Deserializers.orError( "base", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeSymbol), Deserializers.wrap( Qualified.deserializeQualified)));;}
	private static deserializeTemplate( node : Node) : Option<CompileResult<JavaTemplateType>> {return Destructors.destructWithType( "template", node).map( 0);;}
	private static deserializeArguments( node : Node) : CompileResult<JavaArgument> {return Deserializers.orError( "argument", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeWhitespace), Deserializers.wrap( JavaDeserializers.deserializeValue)));;}
	public static deserializeParameter( node : Node) : CompileResult<JavaParameter> {return Deserializers.orError( "parameter", node, Lists.of( Deserializers.wrap( JavaDeserializers.deserializeWhitespace), Deserializers.wrap( JavaDeserializers.deserializeTypedDefinition)));;}
}
