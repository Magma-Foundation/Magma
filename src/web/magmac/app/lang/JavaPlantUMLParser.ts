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
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { JavaRootSegment } from "../../../magmac/app/lang/java/JavaRootSegment";
import { PlantUMLDependency } from "../../../magmac/app/lang/node/PlantUMLDependency";
import { PlantUMLFooter } from "../../../magmac/app/lang/node/PlantUMLFooter";
import { PlantUMLHeader } from "../../../magmac/app/lang/node/PlantUMLHeader";
import { PlantUMLInherits } from "../../../magmac/app/lang/node/PlantUMLInherits";
import { PlantUMLRoot } from "../../../magmac/app/lang/node/PlantUMLRoot";
import { PlantUMLRootSegment } from "../../../magmac/app/lang/node/PlantUMLRootSegment";
import { PlantUMLStructure } from "../../../magmac/app/lang/node/PlantUMLStructure";
import { PlantUMLStructureType } from "../../../magmac/app/lang/node/PlantUMLStructureType";
import { Segment } from "../../../magmac/app/lang/node/Segment";
import { Parser } from "../../../magmac/app/stage/parse/Parser";
import { MapUnitSet } from "../../../magmac/app/stage/unit/MapUnitSet";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { Unit } from "../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class JavaPlantUMLParser {
	private static parseNamespaced( child : String,  namespaced : JavaNamespacedNode) : Iter<PlantUMLRootSegment> {return 0;;}
	private static createSimpleName( base : JavaLang.Base) : String {return 0;;}
	private static createStructureSegment( structureNode : JavaLang.StructureNode) : PlantUMLRootSegment { let name : var=structureNode.name( ); let type : var=structureNode.type( );return 0;;}
	private static createSimpleNameFromType( type : JavaLang.JavaType) : String {return 0;;}
	private static createSimpleNameFromQualifiedType( qualified : JavaLang.Qualified) : String {return qualified.segments( ).iter( ).map( Segment.value).collect( new Joiner( ".")).orElse( "");;}
	private static parseRoot( unit : Unit<JavaLang.JavaRoot>) : Iter<PlantUMLRootSegment> {return unit.destruct( 0);;}
	private static parseRootSegment( fileName : String,  rootSegment : JavaRootSegment) : Iter<PlantUMLRootSegment> {return 0;;}
	private static parseStructure( structureNode : JavaLang.StructureNode) : Iter<PlantUMLRootSegment> { let segment : var=JavaPlantUMLParser.createStructureSegment( structureNode); let child : var=structureNode.name( );return Lists.of( segment).addAllLast( JavaPlantUMLParser.toInherits( child, structureNode.extended( ))).addAllLast( JavaPlantUMLParser.toInherits( child, structureNode.implemented( ))).iter( );;}
	private static toInherits( child : String,  maybeOption : Option<List<JavaLang.JavaType>>) : List<PlantUMLRootSegment> {return maybeOption.orElse( Lists.empty( )).iter( ).map( JavaPlantUMLParser.createSimpleNameFromType).map( 0).collect( new ListCollector<>( ));;}
	private static getPlantUMLInherits( child : String,  parent : String) : PlantUMLRootSegment {return new PlantUMLInherits( child, parent);;}
	public apply( initial : UnitSet<JavaLang.JavaRoot>) : CompileResult<UnitSet<PlantUMLRoot>> { let roots : var=initial.iter( ).flatMap( JavaPlantUMLParser.parseRoot).collect( new ListCollector<>( )).addFirst( new PlantUMLHeader( )).addLast( new PlantUMLFooter( )); let defaultLocation : var=new Location( Lists.empty( ), "diagram"); let mergedRoot : var=new PlantUMLRoot( roots);return CompileResults.Ok( new MapUnitSet<PlantUMLRoot>( ).add( new SimpleUnit<>( defaultLocation, mergedRoot)));;}
}
