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
import { JavaLang } from "../../../magmac/app/lang/java/JavaLang";
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
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { JavaStructureNode } from "../../../magmac/app/lang/java/JavaStructureNode";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { MapUnitSet } from "../../../magmac/app/stage/unit/MapUnitSet";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaPlantUMLParser {
	parseNamespaced(child : String, namespaced : JavaNamespacedNode) : Iter<PlantUMLRootSegment> {;;;}
	createSimpleName(base : JavaLang.JavaBase) : String {;;;}
	createSimpleNameFromQualifiedType(qualifiedType : JavaLang.JavaQualified) : String {return 0;;}
	createStructureSegment(structureNode : JavaStructureNode) : PlantUMLRootSegment {break;break;;;;}
	createSimpleNameFromType(type : JavaLang.JavaType) : String {;;;}
	apply(initial : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<PlantUMLRoot>> {break;break;break;return 0;;}
	parseRoot(unit : Unit<JavaLang.JavaRoot>) : Iter<PlantUMLRootSegment> {return 0;;}
	parseRootSegment(fileName : String, rootSegment : JavaRootSegment) : Iter<PlantUMLRootSegment> {;;;}
	parseStructure(structureNode : JavaStructureNode) : Iter<PlantUMLRootSegment> {break;break;return 0;;}
	toInherits(child : String, maybeOption : Option<List<JavaLang.JavaType>>) : List<PlantUMLRootSegment> {return 0;;}
}
