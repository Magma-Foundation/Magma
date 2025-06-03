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
import { TypescriptBreak } from "../../../magmac/app/lang/web/TypescriptBreak";
import { TypescriptCaller } from "../../../magmac/app/lang/web/TypescriptCaller";
import { TypescriptContinue } from "../../../magmac/app/lang/web/TypescriptContinue";
import { TypescriptFunctionSegmentValue } from "../../../magmac/app/lang/web/TypescriptFunctionSegmentValue";
import { TypescriptFunctionStatement } from "../../../magmac/app/lang/web/TypescriptFunctionStatement";
import { TypescriptInvokable } from "../../../magmac/app/lang/web/TypescriptInvokable";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
import { TypescriptReturnNode } from "../../../magmac/app/lang/web/TypescriptReturnNode";
import { TypescriptSymbol } from "../../../magmac/app/lang/web/TypescriptSymbol";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaTypescriptParser {
	parseUnit(unit : Unit<JavaLang.JavaRoot>) : CompileResult<Unit<TypescriptLang.TypescriptRoot>> {return 0;;}
	parseRoot(location : Location, root : JavaLang.JavaRoot) : CompileResult<Unit<TypescriptLang.TypescriptRoot>> {break;return 0;;}
	parseRootSegment(location : Location, rootSegment : JavaRootSegment) : List<TypescriptLang.TypeScriptRootSegment> {;;;}
	getCollect(structureNode : JavaStructureNode) : List<TypescriptLang.TypeScriptRootSegment> {return 0;;}
	wrap(value : TypescriptLang.TypescriptStructureNode) : TypescriptLang.TypeScriptRootSegment {return 0;;}
	parseStructure(structureNode : JavaStructureNode) : List<TypescriptLang.TypescriptStructureNode> {;;;}
	parseStructureWithType(type : TypescriptLang.TypescriptStructureType, structureNode : JavaStructureNode) : List<TypescriptLang.TypescriptStructureNode> {break;break;break;break;break;return 0;;}
	parseTypeList(list : List<JavaLang.JavaType>) : List<TypescriptLang.TypeScriptType> {return 0;;}
	parseStructureMember(structureNode : JavaStructureMember) : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> {;;;}
	getListListTuple2(typescriptStructureMember : TypescriptLang.TypescriptStructureMember) : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> {return 0;;}
	getList() : Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> {return 0;;}
	parseMethod(methodNode : JavaMethod) : TypescriptLang.TypescriptStructureMember {break;break;break;return 0;;}
	parseFunctionSegments(segments : List<JavaFunctionSegment>) : List<TypescriptLang.TypescriptFunctionSegment> {return 0;;}
	parseFunctionSegment(segment : JavaFunctionSegment) : TypescriptLang.TypescriptFunctionSegment {;;;}
	parseFunctionStatement(functionStatement : JavaFunctionStatement) : TypescriptLang.TypescriptFunctionSegment {return 0;;}
	parseFunctionStatementValue(child : JavaFunctionSegmentValue) : TypescriptFunctionSegmentValue {;;;}
	parseCaller(caller : JavaLang.JavaCaller) : TypescriptCaller {;;;}
	parseArguments(arguments : List<JavaLang.JavaArgument>) : List<TypescriptLang.TypescriptArgument> {return 0;;}
	parseArgument(argument : JavaLang.JavaArgument) : TypescriptLang.TypescriptArgument {;;;}
	parseValue(child : JavaLang.JavaValue) : TypescriptLang.TypescriptValue {return 0;;}
	parseBlock(block : JavaBlock) : TypescriptLang.TypescriptBlock {return 0;;}
	parseHeader(header : JavaLang.JavaBlockHeader) : TypescriptLang.TypescriptBlockHeader {return 0;;}
	parseParameter(parameter : JavaParameter) : TypescriptLang.TypeScriptParameter {;;;}
	parseMethodHeader(header : JavaMethodHeader) : TypescriptLang.TypeScriptMethodHeader {;;;}
	parseDefinition(javaDefinition : JavaLang.JavaDefinition) : TypescriptLang.TypeScriptDefinition {break;;;;}
	parseSymbol(javaSymbol : JavaLang.JavaSymbol) : TypescriptSymbol {return 0;;}
	parseQualifiedType(qualified : JavaLang.JavaQualified) : TypescriptSymbol {break;return 0;;}
	parseArrayType(type : JavaLang.JavaArrayType) : TypescriptLang.TypeScriptType {return 0;;}
	parseType(variadicType : JavaLang.JavaType) : TypescriptLang.TypeScriptType {;;;}
	parseTemplateType(type : JavaLang.JavaTemplateType) : TypescriptLang.TypeScriptTemplateType {break;break;return 0;;}
	parseBaseType(base : JavaLang.JavaBase) : JavaLang.JavaSymbol {;;;}
	parseNamespaced(location : Location, namespaced : JavaNamespacedNode) : TypescriptLang.TypeScriptRootSegment {;;;}
	parseImport(location : Location, segments : List<Segment>) : TypescriptLang.TypeScriptImport {break;break;break;return 0;;}
	apply(initial : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<TypescriptLang.TypescriptRoot>> {return 0;;}
}
