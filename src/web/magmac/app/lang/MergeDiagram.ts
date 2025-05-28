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
	findParentDependencies(child : String, childToParents : Map<String, List<String>>, dependencyMap : Map<String, List<String>>) : List<String> {
		return childToParents.getOrDefault( child, Lists.empty( )).iter( ).map( (String parent) ->dependencyMap.getOrDefault( parent, Lists.empty( ))).flatMap( (List<String> stringList) ->stringList.iter( )).collect( new ListCollector<>( ));
	}
	findChildrenWithDependencies(rootSegments : NodeList) : Map<String, List<String>> {
		return rootSegments.iter( ).fold( Maps.empty( ),  (Map<String, List<String>> current, Node node) ->{if(node.is( "dependency")){  String parent=node.findString( "parent").orElse( ""); String child=node.findString( "child").orElse( "");return current.mapOrPut( parent,  (List<String> stringList) ->stringList.add( child),  () ->Lists.of( child));}return current;});
	}
	findChildrenWithInheritedTypes(rootSegments : NodeList) : Map<String, List<String>> {
		return rootSegments.iter( ).fold( Maps.empty( ),  (Map<String, List<String>> current, Node node1) ->{if(!node1.is( "inherits")){ return current;} String parent=node1.findString( "parent").orElse( ""); String child=node1.findString( "child").orElse( "");return current.mapOrPut( child,  (List<String> stringList) ->stringList.add( parent),  () ->Lists.of( parent));});
	}
	afterAll(roots : Map<Location, Node>) : Map<Location, Node> {
		 NodeList oldRootSegments=new InlineNodeList( roots.iterEntries( ).map( (Tuple2<Location, Node> locationNodeTuple2) ->locationNodeTuple2.right( )).map( (Node node) ->node.findNodeList( "children")).flatMap( (Option<NodeList> option) ->Iters.fromOption( option)).flatMap( (NodeList nodeList) ->nodeList.iter( )).collect( new ListCollector<>( )));
		 Map<String, List<String>> childrenWithInheritedTypes=MergeDiagram.findChildrenWithInheritedTypes( oldRootSegments);
		 Map<String, List<String>> childrenWithDependencies=MergeDiagram.findChildrenWithDependencies( oldRootSegments);
		 NodeList newDependencies=childrenWithDependencies.iterEntries( ).fold( InlineNodeList.empty( ),  (NodeList current, Tuple2<String, List<String>> entry) ->{ String child=entry.left( ); List<String> currentDependencies=entry.right( ); List<String> parentDependencies=MergeDiagram.findParentDependencies( child, childrenWithInheritedTypes, childrenWithDependencies); List<String> childWithInheritedTypes=childrenWithInheritedTypes.getOrDefault( child, Lists.empty( )); List<String> toRemove=parentDependencies.addAll( childWithInheritedTypes); List<String> list=currentDependencies.removeAll( toRemove); NodeList others=new InlineNodeList( list.iter( ).map( (String parent) ->new MapNode( "dependency").withString( "parent", parent).withString( "child", child)).collect( new ListCollector<>( )));return current.addAll( others);});
		 NodeList withoutDependencies=new InlineNodeList( oldRootSegments.iter( ).filter( (Node child) ->!child.is( "dependency")).collect( new ListCollector<>( )));
		 NodeList copy=InlineNodeList.empty( ).add( new MapNode( "start")).addAll( withoutDependencies).addAll( newDependencies).add( new MapNode( "end"));
		 Node node=new MapNode( );
		 Node root=node.withNodeList( "children", copy);
		 Location location=new Location( Lists.empty( ), "diagram");
		return MergeDiagram.createInitial( ).put( location, root);
	}
	createInitial() : Map<Location, Node> {
		return Maps.empty( );
	}
}
