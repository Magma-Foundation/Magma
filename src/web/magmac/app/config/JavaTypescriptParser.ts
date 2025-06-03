import { Tuple2 } from "../../../magmac/api/Tuple2";
import { TupleCollector } from "../../../magmac/api/collect/TupleCollector";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../magmac/app/compile/error/CompileResultCollector";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { Location } from "../../../magmac/app/io/Location";
import { UnitSetCollector } from "../../../magmac/app/io/sources/UnitSetCollector";
import { CommonLang } from "../../../magmac/app/lang/CommonLang";
import { JavaAssignmentNode } from "../../../magmac/app/lang/java/JavaAssignmentNode";
import { JavaBlock } from "../../../magmac/app/lang/java/JavaBlock";
import { JavaBreak } from "../../../magmac/app/lang/java/JavaBreak";
import { JavaCaseNode } from "../../../magmac/app/lang/java/JavaCaseNode";
import { JavaConstruction } from "../../../magmac/app/lang/java/JavaConstruction";
import { JavaConstructor } from "../../../magmac/app/lang/java/JavaConstructor";
import { JavaContinue } from "../../../magmac/app/lang/java/JavaContinue";
import { JavaEnumValues } from "../../../magmac/app/lang/java/JavaEnumValues";
import { JavaFunctionSegment } from "../../../magmac/app/lang/java/JavaFunctionSegment";
import { JavaFunctionSegmentValue } from "../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaFunctionStatement } from "../../../magmac/app/lang/java/JavaFunctionStatement";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { JavaMethod } from "../../../magmac/app/lang/java/JavaMethod";
import { JavaMethodHeader } from "../../../magmac/app/lang/java/JavaMethodHeader";
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { JavaParameter } from "../../../magmac/app/lang/java/JavaParameter";
import { JavaPost } from "../../../magmac/app/lang/java/JavaPost";
import { JavaReturnNode } from "../../../magmac/app/lang/java/JavaReturnNode";
import { JavaRootSegment } from "../../../magmac/app/lang/java/JavaRootSegment";
import { JavaStructureMember } from "../../../magmac/app/lang/java/JavaStructureMember";
import { JavaStructureNode } from "../../../magmac/app/lang/java/JavaStructureNode";
import { JavaStructureStatement } from "../../../magmac/app/lang/java/JavaStructureStatement";
import { JavaYieldNode } from "../../../magmac/app/lang/java/JavaYieldNode";
import { ConditionalType } from "../../../magmac/app/lang/node/ConditionalType";
import { ParameterizedMethodHeader } from "../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { StructureValue } from "../../../magmac/app/lang/node/StructureValue";
import { TypeArguments } from "../../../magmac/app/lang/node/TypeArguments";
import { Symbol } from "../../../magmac/app/lang/web/Symbol";
import { TypescriptCaller } from "../../../magmac/app/lang/web/TypescriptCaller";
import { TypescriptFunctionSegmentValue } from "../../../magmac/app/lang/web/TypescriptFunctionSegmentValue";
import { TypescriptFunctionStatement } from "../../../magmac/app/lang/web/TypescriptFunctionStatement";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaTypescriptParser {
	parseUnit(unit : Unit<JavaLang.JavaRoot>) : CompileResult<Unit<TypescriptLang.TypescriptRoot>> {return unit.destruct( JavaTypescriptParser.parseRoot);;}
	parseRoot(location : Location, root : JavaLang.JavaRoot) : CompileResult<Unit<TypescriptLang.TypescriptRoot>> {break;return CompileResults.Ok( new SimpleUnit<>( location, new TypescriptLang.TypescriptRoot( rootSegments)));;}
	parseRootSegment(location : Location, rootSegment : JavaRootSegment) : List<TypescriptLang.TypeScriptRootSegment> {;;;}
	getCollect(structureNode : JavaStructureNode) : List<TypescriptLang.TypeScriptRootSegment> {return JavaTypescriptParser.parseStructure( structureNode).iter( ).map( JavaTypescriptParser.wrap).collect( new ListCollector<>( ));;}
	wrap(value : TypescriptLang.TypescriptStructureNode) : TypescriptLang.TypeScriptRootSegment {return value;;}
	parseStructure(structureNode : JavaStructureNode) : List<TypescriptLang.TypescriptStructureNode> {;;;}
	parseStructureWithType(type : TypescriptLang.TypescriptStructureType, structureNode : JavaStructureNode) : List<TypescriptLang.TypescriptStructureNode> {break;break;break;break;break;return structures.addLast( new TypescriptLang.TypescriptStructureNode( type, structureNode1));;}
	parseTypeList(list : List<JavaLang.JavaType>) : List<TypescriptLang.TypeScriptType> {return list.iter( ).map( JavaTypescriptParser.parseType).collect( new ListCollector<>( ));;}
	parseStructureMember(structureNode : JavaStructureMember) : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> {;;;}
	getListListTuple2(typescriptStructureMember : TypescriptLang.TypescriptStructureMember) : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> {return new Tuple2<>( Lists.of( typescriptStructureMember), Lists.empty( ));;}
	getList() : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> {return JavaTypescriptParser.getListListTuple2( new TypescriptLang.TypescriptArgument.TypescriptWhitespace( ));;}
	parseMethod(methodNode : JavaMethod) : TypescriptLang.TypescriptStructureMember {break;break;break;return new TypescriptLang.TypescriptMethod( parameterizedHeader, methodNode.maybeChildren( ).map( JavaTypescriptParser.parseFunctionSegments));;}
	parseFunctionSegments(segments : List<JavaFunctionSegment>) : List<TypescriptLang.TypescriptFunctionSegment> {return segments.iter( ).map( JavaTypescriptParser.parseFunctionSegment).collect( new ListCollector<>( ));;}
	parseFunctionSegment(segment : JavaFunctionSegment) : TypescriptLang.TypescriptFunctionSegment {;;;}
	parseFunctionStatement(functionStatement : JavaFunctionStatement) : TypescriptLang.TypescriptFunctionSegment {return new TypescriptFunctionStatement( JavaTypescriptParser.parseFunctionStatementValue( functionStatement.child( )));;}
	parseFunctionStatementValue(child : JavaFunctionSegmentValue) : TypescriptFunctionSegmentValue {;;;}
	parseInvokable(invokable : JavaLang.Invokable) : TypescriptLang.Invokable {return new TypescriptLang.Invokable( JavaTypescriptParser.parseCaller( invokable.caller( )), JavaTypescriptParser.parseArguments( invokable.arguments( )));;}
	parseCaller(caller : JavaLang.JavaCaller) : TypescriptCaller {;;;}
	parseArguments(arguments : List<JavaLang.JavaArgument>) : List<TypescriptLang.TypescriptArgument> {return arguments.iter( ).map( JavaTypescriptParser.parseArgument).collect( new ListCollector<>( ));;}
	parseArgument(argument : JavaLang.JavaArgument) : TypescriptLang.TypescriptArgument {;;;}
	parseValue(child : JavaLang.JavaValue) : TypescriptLang.Value {;;;}
	parseAccess(access : JavaLang.Access) : TypescriptLang.Access {return new TypescriptLang.Access( JavaTypescriptParser.parseValue( access.receiver( )), access.property( ));;}
	parseIndex(index : JavaLang.Index) : TypescriptLang.Index {return new TypescriptLang.Index( JavaTypescriptParser.parseValue( index.parent( )), JavaTypescriptParser.parseValue( index.argument( )));;}
	parseOperation(operation : JavaLang.operation) : TypescriptLang.Operation {return new TypescriptLang.Operation( JavaTypescriptParser.parseValue( operation.left( )), operation.operator( ), JavaTypescriptParser.parseValue( operation.right( )));;}
	parseBlock(block : JavaBlock) : TypescriptLang.TypescriptBlock {return new TypescriptLang.TypescriptBlock( JavaTypescriptParser.parseHeader( block.header( )), JavaTypescriptParser.parseFunctionSegments( block.segments( )));;}
	parseHeader(header : JavaLang.JavaBlockHeader) : TypescriptLang.TypescriptBlockHeader {return new TypescriptLang.TypescriptConditional( ConditionalType.If, new Symbol( "true"));;}
	parseParameter(parameter : JavaParameter) : TypescriptLang.TypeScriptParameter {;;;}
	parseMethodHeader(header : JavaMethodHeader) : TypescriptLang.TypeScriptMethodHeader {;;;}
	parseDefinition(javaDefinition : JavaLang.JavaDefinition) : TypescriptLang.TypeScriptDefinition {break;;;;}
	parseSymbol(symbol : JavaLang.Symbol) : Symbol {return new Symbol( symbol.value( ));;}
	parseQualifiedType(qualified : JavaLang.JavaQualified) : Symbol {break;return new Symbol( joined);;}
	parseArrayType(type : JavaLang.JavaArrayType) : TypescriptLang.TypeScriptType {return new TypescriptLang.TypescriptArrayType( JavaTypescriptParser.parseType( type.inner));;}
	parseType(variadicType : JavaLang.JavaType) : TypescriptLang.TypeScriptType {;;;}
	parseTemplateType(type : JavaLang.JavaTemplateType) : TypescriptLang.TypeScriptTemplateType {break;break;return new TypescriptLang.TypeScriptTemplateType( base, new TypeArguments<>( collect));;}
	parseBaseType(base : JavaLang.JavaBase) : JavaLang.Symbol {;;;}
	parseNamespaced(location : Location, namespaced : JavaNamespacedNode) : TypescriptLang.TypeScriptRootSegment {;;;}
	parseImport(location : Location, segments : List<Segment>) : TypescriptLang.TypeScriptImport {break;break;break;return new TypescriptLang.TypeScriptImport( Lists.of( last), before.addAllLast( segments));;}
	apply(initial : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<TypescriptLang.TypescriptRoot>> {return initial.iter( ).map( JavaTypescriptParser.parseUnit).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));;}
}
