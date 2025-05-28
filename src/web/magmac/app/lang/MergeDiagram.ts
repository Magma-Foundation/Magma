import { Option } from "../../../magmac/api/Option";
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
import { AfterAll } from "../../../magmac/app/stage/AfterAll";
export class MergeDiagram {
	private static findParentDependencies( child : String,  childToParents : Map<String, List<String>>,  dependencyMap : Map<String, List<String>>) : List<String> {
		return childToParents.getOrDefault( child, Lists.empty( )).iter( ).map( ( parent : String) => dependencyMap.getOrDefault( parent, Lists.empty( ))).flatMap( ( stringList : List<String>) => stringList.iter( )).collect( new ListCollector<>( ));
	}
	private static findChildrenWithDependencies( rootSegments : NodeList) : Map<String, List<String>> {
		return rootSegments.iter( ).fold( Maps.empty( ), ( current : Map<String, List<String>>,  node : Node) => {if(node.is( "dependency")){  parent : String=node.findString( "parent").orElse( ""); child : String=node.findString( "child").orElse( "");return current.mapOrPut( parent, ( stringList : List<String>) => stringList.add( child), ( )->Lists.of( child));}return current;});
	}
	private static findChildrenWithInheritedTypes( rootSegments : NodeList) : Map<String, List<String>> {
		return rootSegments.iter( ).fold( Maps.empty( ), ( current : Map<String, List<String>>,  node1 : Node) => {if(!node1.is( "inherits")){ return current;} parent : String=node1.findString( "parent").orElse( ""); child : String=node1.findString( "child").orElse( "");return current.mapOrPut( child, ( stringList : List<String>) => stringList.add( parent), ( )->Lists.of( parent));});
	}
	public afterAll( roots : Map<Location, Node>) : Map<Location, Node> {
		 oldRootSegments : NodeList=new InlineNodeList( roots.iterEntries( ).map( ( locationNodeTuple2 : Tuple2<Location, Node>) => locationNodeTuple2.right( )).map( ( node : Node) => node.findNodeList( "children")).flatMap( ( option : Option<NodeList>) => Iters.fromOption( option)).flatMap( ( nodeList : NodeList) => nodeList.iter( )).collect( new ListCollector<>( )));
		 childrenWithInheritedTypes : Map<String, List<String>>=MergeDiagram.findChildrenWithInheritedTypes( oldRootSegments);
		 childrenWithDependencies : Map<String, List<String>>=MergeDiagram.findChildrenWithDependencies( oldRootSegments);
		 newDependencies : NodeList=childrenWithDependencies.iterEntries( ).fold( InlineNodeList.empty( ), ( current : NodeList,  entry : Tuple2<String, List<String>>) => { child : String=entry.left( ); currentDependencies : List<String>=entry.right( ); parentDependencies : List<String>=MergeDiagram.findParentDependencies( child, childrenWithInheritedTypes, childrenWithDependencies); childWithInheritedTypes : List<String>=childrenWithInheritedTypes.getOrDefault( child, Lists.empty( )); toRemove : List<String>=parentDependencies.addAll( childWithInheritedTypes); list : List<String>=currentDependencies.removeAll( toRemove); others : NodeList=new InlineNodeList( list.iter( ).map( ( parent : String) => new MapNode( "dependency").withString( "parent", parent).withString( "child", child)).collect( new ListCollector<>( )));return current.addAll( others);});
		 withoutDependencies : NodeList=new InlineNodeList( oldRootSegments.iter( ).filter( ( child : Node) => !child.is( "dependency")).collect( new ListCollector<>( )));
		 copy : NodeList=InlineNodeList.empty( ).add( new MapNode( "start")).addAll( withoutDependencies).addAll( newDependencies).add( new MapNode( "end"));
		 node : Node=new MapNode( );
		 root : Node=node.withNodeList( "children", copy);
		 location : Location=new Location( Lists.empty( ), "diagram");
		return MergeDiagram.createInitial( ).put( location, root);
	}
	private static createInitial() : Map<Location, Node> {
		return Maps.empty( );
	}
}
