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
import { JavaAssignmentNode } from "../../../magmac/app/lang/java/JavaAssignmentNode";
import { JavaBlock } from "../../../magmac/app/lang/java/JavaBlock";
import { JavaBreak } from "../../../magmac/app/lang/java/JavaBreak";
import { JavaCaseNode } from "../../../magmac/app/lang/java/JavaCaseNode";
import { JavaConstructor } from "../../../magmac/app/lang/java/JavaConstructor";
import { JavaContinue } from "../../../magmac/app/lang/java/JavaContinue";
import { JavaDefinition } from "../../../magmac/app/lang/java/JavaDefinition";
import { JavaEnumValues } from "../../../magmac/app/lang/java/JavaEnumValues";
import { JavaFunctionSegment } from "../../../magmac/app/lang/java/JavaFunctionSegment";
import { JavaFunctionSegmentValue } from "../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaFunctionStatement } from "../../../magmac/app/lang/java/JavaFunctionStatement";
import { JavaInvokable } from "../../../magmac/app/lang/java/JavaInvokable";
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
import { JavaWhitespace } from "../../../magmac/app/lang/java/JavaWhitespace";
import { JavaYieldNode } from "../../../magmac/app/lang/java/JavaYieldNode";
import { ConditionalType } from "../../../magmac/app/lang/node/ConditionalType";
import { Definition } from "../../../magmac/app/lang/node/Definition";
import { JavaArrayType } from "../../../magmac/app/lang/node/JavaArrayType";
import { JavaBase } from "../../../magmac/app/lang/node/JavaBase";
import { JavaBlockHeader } from "../../../magmac/app/lang/node/JavaBlockHeader";
import { JavaRoot } from "../../../magmac/app/lang/node/JavaRoot";
import { JavaTemplateType } from "../../../magmac/app/lang/node/JavaTemplateType";
import { JavaType } from "../../../magmac/app/lang/node/JavaType";
import { ParameterizedMethodHeader } from "../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { Qualified } from "../../../magmac/app/lang/node/Qualified";
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { StructureValue } from "../../../magmac/app/lang/node/StructureValue";
import { Symbol } from "../../../magmac/app/lang/node/Symbol";
import { TypeArguments } from "../../../magmac/app/lang/node/TypeArguments";
import { TypeScriptDefinition } from "../../../magmac/app/lang/node/TypeScriptDefinition";
import { TypeScriptImport } from "../../../magmac/app/lang/node/TypeScriptImport";
import { TypeScriptMethodHeader } from "../../../magmac/app/lang/node/TypeScriptMethodHeader";
import { TypeScriptParameter } from "../../../magmac/app/lang/node/TypeScriptParameter";
import { TypeScriptRootSegment } from "../../../magmac/app/lang/node/TypeScriptRootSegment";
import { TypeScriptTemplateType } from "../../../magmac/app/lang/node/TypeScriptTemplateType";
import { TypeScriptType } from "../../../magmac/app/lang/node/TypeScriptType";
import { TypescriptArrayType } from "../../../magmac/app/lang/node/TypescriptArrayType";
import { TypescriptBlock } from "../../../magmac/app/lang/node/TypescriptBlock";
import { TypescriptBlockHeader } from "../../../magmac/app/lang/node/TypescriptBlockHeader";
import { TypescriptConditional } from "../../../magmac/app/lang/node/TypescriptConditional";
import { TypescriptConstructor } from "../../../magmac/app/lang/node/TypescriptConstructor";
import { TypescriptFunctionSegment } from "../../../magmac/app/lang/node/TypescriptFunctionSegment";
import { TypescriptMethod } from "../../../magmac/app/lang/node/TypescriptMethod";
import { TypescriptRoot } from "../../../magmac/app/lang/node/TypescriptRoot";
import { TypescriptStructureMember } from "../../../magmac/app/lang/node/TypescriptStructureMember";
import { TypescriptStructureNode } from "../../../magmac/app/lang/node/TypescriptStructureNode";
import { TypescriptStructureType } from "../../../magmac/app/lang/node/TypescriptStructureType";
import { VariadicType } from "../../../magmac/app/lang/node/VariadicType";
import { TypescriptBreak } from "../../../magmac/app/lang/web/TypescriptBreak";
import { TypescriptContinue } from "../../../magmac/app/lang/web/TypescriptContinue";
import { TypescriptFunctionSegmentValue } from "../../../magmac/app/lang/web/TypescriptFunctionSegmentValue";
import { TypescriptFunctionStatement } from "../../../magmac/app/lang/web/TypescriptFunctionStatement";
import { TypescriptWhitespace } from "../../../magmac/app/lang/web/TypescriptWhitespace";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaTypescriptParser {
	parseUnit(unit : Unit<JavaRoot>) : CompileResult<Unit<TypescriptRoot>> {break;;}
	parseRoot(location : Location, root : JavaRoot) : CompileResult<Unit<TypescriptRoot>> {break;break;;}
	parseRootSegment(location : Location, rootSegment : JavaRootSegment) : List<TypeScriptRootSegment> {;;;}
	getCollect(structureNode : JavaStructureNode) : List<TypeScriptRootSegment> {break;;}
	wrap(value : TypescriptStructureNode) : TypeScriptRootSegment {break;;}
	parseStructure(structureNode : JavaStructureNode) : List<TypescriptStructureNode> {;;;}
	parseStructureWithType(type : TypescriptStructureType, structureNode : JavaStructureNode) : List<TypescriptStructureNode> {break;break;break;break;break;break;;}
	parseTypeList(list : List<JavaType>) : List<TypeScriptType> {break;;}
	parseStructureMember(structureNode : JavaStructureMember) : Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> {;;;}
	getListListTuple2(typescriptStructureMember : TypescriptStructureMember) : Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> {break;;}
	getList() : Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> {break;;}
	parseMethod(methodNode : JavaMethod) : TypescriptStructureMember {break;break;break;break;;}
	parseFunctionSegments(segments : List<JavaFunctionSegment>) : List<TypescriptFunctionSegment> {break;;}
	parseFunctionSegment(segment : JavaFunctionSegment) : TypescriptFunctionSegment {;;;}
	parseFunctionStatement(functionStatement : JavaFunctionStatement) : TypescriptFunctionSegment {break;;}
	parseFunctionStatementValue(child : JavaFunctionSegmentValue) : TypescriptFunctionSegmentValue {;;;}
	parseBlock(block : JavaBlock) : TypescriptBlock {break;;}
	parseHeader(header : JavaBlockHeader) : TypescriptBlockHeader {break;;}
	parseParameter(parameter : JavaParameter) : TypeScriptParameter {;;;}
	parseMethodHeader(header : JavaMethodHeader) : TypeScriptMethodHeader {;;;}
	parseDefinition(javaDefinition : JavaDefinition) : TypeScriptDefinition {break;;;;}
	parseQualifiedType(qualified : Qualified) : Symbol {break;break;;}
	parseArrayType(type : JavaArrayType) : TypeScriptType {break;;}
	parseType(variadicType : JavaType) : TypeScriptType {;;;}
	parseTemplateType(type : JavaTemplateType) : TypeScriptTemplateType {break;break;break;;}
	parseBaseType(base : JavaBase) : Symbol {;;;}
	parseNamespaced(location : Location, namespaced : JavaNamespacedNode) : TypeScriptRootSegment {;;;}
	parseImport(location : Location, segments : List<Segment>) : TypeScriptImport {break;break;break;break;;}
	apply(initial : UnitSet<JavaRoot>) : CompileResult<UnitSet<TypescriptRoot>> {break;;}
}
