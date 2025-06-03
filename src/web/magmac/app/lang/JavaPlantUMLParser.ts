import { Option } from "../../../magmac/api/Option";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Iters } from "../../../magmac/api/iter/Iters";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { Location } from "../../../magmac/app/io/Location";
import { JavaArrayType } from "../../../magmac/app/lang/node/JavaArrayType";
import { JavaBase } from "../../../magmac/app/lang/node/JavaBase";
import { JavaRoot } from "../../../magmac/app/lang/node/JavaRoot";
import { JavaRootSegment } from "../../../magmac/app/lang/java/JavaRootSegment";
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { PlantUMLDependency } from "../../../magmac/app/lang/node/PlantUMLDependency";
import { PlantUMLFooter } from "../../../magmac/app/lang/node/PlantUMLFooter";
import { PlantUMLHeader } from "../../../magmac/app/lang/node/PlantUMLHeader";
import { PlantUMLInherits } from "../../../magmac/app/lang/node/PlantUMLInherits";
import { PlantUMLRoot } from "../../../magmac/app/lang/node/PlantUMLRoot";
import { PlantUMLRootSegment } from "../../../magmac/app/lang/node/PlantUMLRootSegment";
import { PlantUMLStructure } from "../../../magmac/app/lang/node/PlantUMLStructure";
import { PlantUMLStructureType } from "../../../magmac/app/lang/node/PlantUMLStructureType";
import { Qualified } from "../../../magmac/app/lang/node/Qualified";
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { JavaStructureNode } from "../../../magmac/app/lang/java/JavaStructureNode";
import { JavaStructureType } from "../../../magmac/app/lang/node/JavaStructureType";
import { Symbol } from "../../../magmac/app/lang/node/Symbol";
import { JavaTemplateType } from "../../../magmac/app/lang/node/JavaTemplateType";
import { JavaType } from "../../../magmac/app/lang/node/JavaType";
import { VariadicType } from "../../../magmac/app/lang/node/VariadicType";
import { JavaWhitespace } from "../../../magmac/app/lang/java/JavaWhitespace";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { MapUnitSet } from "../../../magmac/app/stage/unit/MapUnitSet";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaPlantUMLParser {
	parseNamespaced(child : String, namespaced : JavaNamespacedNode) : Iter<PlantUMLRootSegment> {;;;}
	createSimpleName(base : JavaBase) : String {;;;}
	createSimpleNameFromQualifiedType(qualifiedType : Qualified) : String {break;;}
	createStructureSegment(structureNode : JavaStructureNode) : PlantUMLRootSegment {break;break;;;;}
	createSimpleNameFromType(type : JavaType) : String {;;;}
	apply(initial : UnitSet<JavaRoot>) : CompileResult<UnitSet<PlantUMLRoot>> {break;break;break;break;;}
	parseRoot(unit : Unit<JavaRoot>) : Iter<PlantUMLRootSegment> {break;;}
	parseRootSegment(fileName : String, rootSegment : JavaRootSegment) : Iter<PlantUMLRootSegment> {;;;}
	parseStructure(structureNode : JavaStructureNode) : Iter<PlantUMLRootSegment> {break;break;break;;}
	toInherits(child : String, maybeOption : Option<List<JavaType>>) : List<PlantUMLRootSegment> {break;;}
}
