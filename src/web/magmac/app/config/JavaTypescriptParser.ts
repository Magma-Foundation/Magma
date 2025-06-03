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
import { Block } from "../../../magmac/app/lang/node/Block";
import { CaseNode } from "../../../magmac/app/lang/node/CaseNode";
import { FunctionStatement } from "../../../magmac/app/lang/node/FunctionStatement";
import { JavaFunctionSegment } from "../../../magmac/app/lang/node/JavaFunctionSegment";
import { Definition } from "../../../magmac/app/lang/node/Definition";
import { EnumValues } from "../../../magmac/app/lang/node/EnumValues";
import { JavaArrayType } from "../../../magmac/app/lang/node/JavaArrayType";
import { JavaBase } from "../../../magmac/app/lang/node/JavaBase";
import { JavaConstructor } from "../../../magmac/app/lang/node/JavaConstructor";
import { JavaDefinition } from "../../../magmac/app/lang/node/JavaDefinition";
import { JavaMethod } from "../../../magmac/app/lang/node/JavaMethod";
import { JavaMethodHeader } from "../../../magmac/app/lang/node/JavaMethodHeader";
import { JavaNamespacedNode } from "../../../magmac/app/lang/node/JavaNamespacedNode";
import { JavaParameter } from "../../../magmac/app/lang/node/JavaParameter";
import { JavaReturnNode } from "../../../magmac/app/lang/node/JavaReturnNode";
import { JavaRoot } from "../../../magmac/app/lang/node/JavaRoot";
import { JavaRootSegment } from "../../../magmac/app/lang/node/JavaRootSegment";
import { JavaStructureMember } from "../../../magmac/app/lang/node/JavaStructureMember";
import { JavaStructureNode } from "../../../magmac/app/lang/node/JavaStructureNode";
import { JavaTemplateType } from "../../../magmac/app/lang/node/JavaTemplateType";
import { JavaType } from "../../../magmac/app/lang/node/JavaType";
import { ParameterizedMethodHeader } from "../../../magmac/app/lang/node/ParameterizedMethodHeader";
import { Qualified } from "../../../magmac/app/lang/node/Qualified";
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { StructureStatement } from "../../../magmac/app/lang/node/StructureStatement";
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
import { TypescriptConstructor } from "../../../magmac/app/lang/node/TypescriptConstructor";
import { TypescriptFunctionSegment } from "../../../magmac/app/lang/node/TypescriptFunctionSegment";
import { TypescriptMethod } from "../../../magmac/app/lang/node/TypescriptMethod";
import { TypescriptRoot } from "../../../magmac/app/lang/node/TypescriptRoot";
import { TypescriptStructureMember } from "../../../magmac/app/lang/node/TypescriptStructureMember";
import { TypescriptStructureNode } from "../../../magmac/app/lang/node/TypescriptStructureNode";
import { TypescriptStructureType } from "../../../magmac/app/lang/node/TypescriptStructureType";
import { VariadicType } from "../../../magmac/app/lang/node/VariadicType";
import { Whitespace } from "../../../magmac/app/lang/node/Whitespace";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaTypescriptParser {
	parseUnit(unit : Unit<JavaRoot>) : CompileResult<Unit<TypescriptRoot>> {;;}
	parseRoot(location : Location, root : JavaRoot) : CompileResult<Unit<TypescriptRoot>> {;;;}
	parseRootSegment(location : Location, rootSegment : JavaRootSegment) : List<TypeScriptRootSegment> {;;;}
	getCollect(structureNode : JavaStructureNode) : List<TypeScriptRootSegment> {;;}
	wrap(value : TypescriptStructureNode) : TypeScriptRootSegment {;;}
	parseStructure(structureNode : JavaStructureNode) : List<TypescriptStructureNode> {;;;}
	parseStructureWithType(type : TypescriptStructureType, structureNode : JavaStructureNode) : List<TypescriptStructureNode> {;;;;;;;}
	parseTypeList(list : List<JavaType>) : List<TypeScriptType> {;;}
	parseStructureMember(structureNode : JavaStructureMember) : Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> {;;;}
	getListListTuple2(typescriptStructureMember : TypescriptStructureMember) : Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> {;;}
	getList() : Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> {;;}
	parseMethod(methodNode : JavaMethod) : TypescriptStructureMember {;;;;;}
	parseFunctionSegments(segments : List<JavaFunctionSegment>) : List<TypescriptFunctionSegment> {;;}
	parseFunctionSegment(segment : JavaFunctionSegment) : TypescriptFunctionSegment {;;;}
	parseParameter(parameter : JavaParameter) : TypeScriptParameter {;;;}
	parseMethodHeader(header : JavaMethodHeader) : TypeScriptMethodHeader {;;;}
	parseDefinition(javaDefinition : JavaDefinition) : TypeScriptDefinition {;;;;}
	parseQualifiedType(qualified : Qualified) : Symbol {;;;}
	parseArrayType(type : JavaArrayType) : TypeScriptType {;;}
	parseType(variadicType : JavaType) : TypeScriptType {;;;}
	parseTemplateType(type : JavaTemplateType) : TypeScriptTemplateType {;;;;}
	parseBaseType(base : JavaBase) : Symbol {;;;}
	parseNamespaced(location : Location, namespaced : JavaNamespacedNode) : TypeScriptRootSegment {;;;}
	parseImport(location : Location, segments : List<Segment>) : TypeScriptImport {;;;;;}
	apply(initial : UnitSet<JavaRoot>) : CompileResult<UnitSet<TypescriptRoot>> {;;}
}
