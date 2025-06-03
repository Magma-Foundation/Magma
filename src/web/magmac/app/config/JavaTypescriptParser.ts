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
import { JavaBreak } from "../../../magmac/app/lang/java/JavaBreak";
import { JavaConstruction } from "../../../magmac/app/lang/java/JavaConstruction";
import { JavaConstructor } from "../../../magmac/app/lang/java/JavaConstructor";
import { JavaContinue } from "../../../magmac/app/lang/java/JavaContinue";
import { JavaEnumValues } from "../../../magmac/app/lang/java/JavaEnumValues";
import { JavaFunctionSegment } from "../../../magmac/app/lang/java/JavaFunctionSegment";
import { JavaFunctionSegmentValue } from "../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
import { JavaMethod } from "../../../magmac/app/lang/java/JavaMethod";
import { JavaMethodHeader } from "../../../magmac/app/lang/java/JavaMethodHeader";
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { JavaParameter } from "../../../magmac/app/lang/java/JavaParameter";
import { JavaPost } from "../../../magmac/app/lang/java/JavaPost";
import { JavaRootSegment } from "../../../magmac/app/lang/java/JavaRootSegment";
import { JavaStructureMember } from "../../../magmac/app/lang/java/JavaStructureMember";
import { JavaStructureStatement } from "../../../magmac/app/lang/java/JavaStructureStatement";
import { JavaYieldNode } from "../../../magmac/app/lang/java/JavaYieldNode";
import { ConditionalType } from "../../../magmac/app/lang/node/ConditionalType";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { ParameterizedMethodHeader } from "../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { StructureValue } from "../../../magmac/app/lang/node/StructureValue";
import { TypeArguments } from "../../../magmac/app/lang/node/TypeArguments";
import { Caller } from "../../../magmac/app/lang/web/Caller";
import { FunctionStatement } from "../../../magmac/app/lang/web/FunctionStatement";
import { Symbol } from "../../../magmac/app/lang/web/Symbol";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaTypescriptParser {
	parseUnit(unit : Unit<JavaLang.JavaRoot>) : CompileResult<Unit<TypescriptLang.TypescriptRoot>> {return unit.destruct( JavaTypescriptParser.parseRoot);;}
	parseRoot(location : Location, root : JavaLang.JavaRoot) : CompileResult<Unit<TypescriptLang.TypescriptRoot>> {rootSegments : var=root.children( ).iter( ).map( 0).flatMap( List.iter).collect( new ListCollector<>( ));return CompileResults.Ok( new SimpleUnit<>( location, new TypescriptLang.TypescriptRoot( rootSegments)));;}
	parseRootSegment(location : Location, rootSegment : JavaRootSegment) : List<TypescriptLang.TypeScriptRootSegment> {return 0;;}
	getCollect(structureNode : JavaLang.StructureNode) : List<TypescriptLang.TypeScriptRootSegment> {return JavaTypescriptParser.parseStructure( structureNode).iter( ).map( JavaTypescriptParser.wrap).collect( new ListCollector<>( ));;}
	wrap(value : TypescriptLang.StructureNode) : TypescriptLang.TypeScriptRootSegment {return value;;}
	parseStructure(structureNode : JavaLang.StructureNode) : List<TypescriptLang.StructureNode> {return 0;;}
	parseStructureWithType(type : TypescriptLang.StructureType, structureNode : JavaLang.StructureNode) : List<TypescriptLang.StructureNode> {value : var=structureNode.value;membersTuple : var=value.members( ).iter( ).map( JavaTypescriptParser.parseStructureMember).collect( new TupleCollector<>( new ListCollector<>( ), new ListCollector<>( )));members : var=membersTuple.left( ).iter( ).flatMap( List.iter).collect( new ListCollector<>( ));structures : var=membersTuple.right( ).iter( ).flatMap( List.iter).collect( new ListCollector<>( ));structureNode1 : var=new StructureValue<TypescriptLang.Type, TypescriptLang.TypescriptStructureMember>( value.name( ), value.modifiers( ), members, value.maybeTypeParams( ), value.maybeExtended( ).map( JavaTypescriptParser.parseTypeList), value.maybeImplemented( ).map( JavaTypescriptParser.parseTypeList));return structures.addLast( new TypescriptLang.StructureNode( type, structureNode1));;}
	parseTypeList(list : List<JavaLang.JavaType>) : List<TypescriptLang.Type> {return list.iter( ).map( JavaTypescriptParser.parseType).collect( new ListCollector<>( ));;}
	parseStructureMember(structureNode : JavaStructureMember) : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> {return 0;;}
	getListListTuple2(typescriptStructureMember : TypescriptLang.TypescriptStructureMember) : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> {return new Tuple2<>( Lists.of( typescriptStructureMember), Lists.empty( ));;}
	getList() : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> {return JavaTypescriptParser.getListListTuple2( new TypescriptLang.Whitespace( ));;}
	parseMethod(methodNode : JavaMethod) : TypescriptLang.TypescriptStructureMember {parameters : var=methodNode.parameters( ).iter( ).map( JavaTypescriptParser.parseParameter).collect( new ListCollector<>( ));header : var=JavaTypescriptParser.parseMethodHeader( methodNode.header( ));parameterizedHeader : var=new ParameterizedMethodHeader<TypescriptLang.TypeScriptParameter>( header, parameters);return new TypescriptLang.TypescriptMethod( parameterizedHeader, methodNode.maybeChildren( ).map( JavaTypescriptParser.parseFunctionSegments));;}
	parseFunctionSegments(segments : List<JavaFunctionSegment>) : List<TypescriptLang.FunctionSegment> {return segments.iter( ).map( JavaTypescriptParser.parseFunctionSegment).collect( new ListCollector<>( ));;}
	parseFunctionSegment(segment : JavaFunctionSegment) : TypescriptLang.FunctionSegment {return 0;;}
	parseFunctionStatement(functionStatement : JavaLang.FunctionStatement) : TypescriptLang.FunctionSegment {oldValue : var=JavaTypescriptParser.parseFunctionStatementValue( functionStatement.child( ));newValue : var=JavaTypescriptParser.getOldValue( oldValue);return new FunctionStatement( newValue);;}
	getOldValue(oldValue : TypescriptLang.FunctionSegment.Value) : TypescriptLang.FunctionSegment.Value {if(true){ if(true){ newDefinition : var=oldDefinition.withModifier( new Modifier( "let"));return new TypescriptLang.Assignment( newDefinition, value);;};}return oldValue;;}
	parseFunctionStatementValue(child : JavaFunctionSegmentValue) : TypescriptLang.FunctionSegment.Value {return 0;;}
	parseAssignable(assignable : JavaLang.Assignable) : TypescriptLang.Assignable {return 0;;}
	parseInvokable(invokable : JavaLang.Invokable) : TypescriptLang.Invokable {return new TypescriptLang.Invokable( JavaTypescriptParser.parseCaller( invokable.caller( )), JavaTypescriptParser.parseArguments( invokable.arguments( )));;}
	parseCaller(caller : JavaLang.JavaCaller) : Caller {return 0;;}
	parseArguments(arguments : List<JavaLang.JavaArgument>) : List<TypescriptLang.Argument> {return arguments.iter( ).map( JavaTypescriptParser.parseArgument).collect( new ListCollector<>( ));;}
	parseArgument(argument : JavaLang.JavaArgument) : TypescriptLang.Argument {return 0;;}
	parseValue(child : JavaLang.Value) : TypescriptLang.Value {return 0;;}
	parseAccess(access : JavaLang.Access) : TypescriptLang.Access {return new TypescriptLang.Access( JavaTypescriptParser.parseValue( access.receiver( )), access.property( ));;}
	parseIndex(index : JavaLang.Index) : TypescriptLang.Index {return new TypescriptLang.Index( JavaTypescriptParser.parseValue( index.parent( )), JavaTypescriptParser.parseValue( index.argument( )));;}
	parseOperation(operation : JavaLang.operation) : TypescriptLang.Operation {return new TypescriptLang.Operation( JavaTypescriptParser.parseValue( operation.left( )), operation.operator( ), JavaTypescriptParser.parseValue( operation.right( )));;}
	parseBlock(block : JavaLang.Block) : TypescriptLang.Block {return new TypescriptLang.Block( JavaTypescriptParser.parseHeader( block.header( )), JavaTypescriptParser.parseFunctionSegments( block.segments( )));;}
	parseHeader(header : JavaLang.BlockHeader) : TypescriptLang.TypescriptBlockHeader {return new TypescriptLang.TypescriptConditional( ConditionalType.If, new Symbol( "true"));;}
	parseParameter(parameter : JavaParameter) : TypescriptLang.TypeScriptParameter {return 0;;}
	parseMethodHeader(header : JavaMethodHeader) : TypescriptLang.TypeScriptMethodHeader {return 0;;}
	parseDefinition(definition : JavaLang.Definition) : TypescriptLang.Definition {maybeAnnotations : var=definition.maybeAnnotations( );maybeModifiers : var=definition.modifiers( );maybeTypeParameters : var=definition.maybeTypeParams( );type : var=definition.type( );oldName : var=definition.name( );if(true){ newType : var=new TypescriptLang.ArrayType( JavaTypescriptParser.parseType( child));name : var="..."+oldName;return new TypescriptLang.Definition( maybeAnnotations, maybeModifiers, name, maybeTypeParameters, newType);;}if(true){ return new TypescriptLang.Definition( maybeAnnotations, maybeModifiers, oldName, maybeTypeParameters, JavaTypescriptParser.parseType( definition.type( )));;};}
	parseSymbol(symbol : JavaLang.Symbol) : Symbol {return new Symbol( symbol.value( ));;}
	parseQualifiedType(qualified : JavaLang.Qualified) : Symbol {joined : var=qualified.segments( ).iter( ).map( Segment.value).collect( new Joiner( ".")).orElse( "");return new Symbol( joined);;}
	parseArrayType(type : JavaLang.JavaArrayType) : TypescriptLang.Type {return new TypescriptLang.ArrayType( JavaTypescriptParser.parseType( type.inner));;}
	parseType(variadicType : JavaLang.JavaType) : TypescriptLang.Type {return 0;;}
	parseTemplateType(type : JavaLang.JavaTemplateType) : TypescriptLang.TemplateType {base : var=JavaTypescriptParser.parseBaseType( type.base( ));collect : var=JavaTypescriptParser.parseTypeList( type.typeArguments( ).arguments( ));return new TypescriptLang.TemplateType( base, new TypeArguments<>( collect));;}
	parseBaseType(base : JavaLang.Base) : JavaLang.Symbol {return 0;;}
	parseNamespaced(location : Location, namespaced : JavaNamespacedNode) : TypescriptLang.TypeScriptRootSegment {return 0;;}
	parseImport(location : Location, segments : List<Segment>) : TypescriptLang.TypeScriptImport {segmentValues : var=segments.iter( ).map( Segment.value).collect( new ListCollector<>( ));before : var=location.namespace( ).iter( ).map( 0).map( Segment.new).collect( new ListCollector<>( ));last : var=new Segment( segmentValues.findLast( ).orElse( ""));return new TypescriptLang.TypeScriptImport( Lists.of( last), before.addAllLast( segments));;}
	apply(initial : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<TypescriptLang.TypescriptRoot>> {return initial.iter( ).map( JavaTypescriptParser.parseUnit).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));;}
}
