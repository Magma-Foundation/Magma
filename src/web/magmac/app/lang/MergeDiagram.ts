import { Tuple2 } from "../../../magmac/api/Tuple2";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Map } from "../../../magmac/api/collect/map/Map";
import { Maps } from "../../../magmac/api/collect/map/Maps";
import { Iters } from "../../../magmac/api/iter/Iters";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { Location } from "../../../magmac/app/io/Location";
import { AfterAll } from "../../../magmac/app/stage/after/AfterAll";
import { MapUnitSet } from "../../../magmac/app/stage/unit/MapUnitSet";
import { SimpleUnit } from "../../../magmac/app/stage/unit/SimpleUnit";
import { UnitSet } from "../../../magmac/app/stage/unit/UnitSet";
export class MergeDiagram {
	findParentDependencies(child : String, childToParents : Map<String, List<String>>, dependencyMap : Map<String, List<String>>) : List<String> {return 0.getOrDefault( 0, 0.empty( )).iter( ).map( 0).flatMap( 0.iter).collect( new ListCollector<>( ));;}
	findChildrenWithDependencies(rootSegments : NodeList) : Map<String, List<String>> {return 0.iter( ).fold( 0.empty( ), 0);;}
	findChildrenWithInheritedTypes(rootSegments : NodeList) : Map<String, List<String>> {return 0.iter( ).fold( 0.empty( ), 0);;}
	createInitial() : MapUnitSet<Node> {return new MapUnitSet<>( );;}
	afterAll(roots : UnitSet<Node>) : UnitSet<Node> {break;break;break;break;break;break;break;break;break;return 0.createInitial( ).add( new SimpleUnit<>( 0, 0));;}
}
