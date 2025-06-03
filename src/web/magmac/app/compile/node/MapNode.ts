import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { Maps } from "../../../../magmac/api/collect/map/Maps";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { Joiner } from "../../../../magmac/api/iter/collect/Joiner";
import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
export class MapNode {
	MapNode() : public {this( new None<String>( ), Maps.empty( ), Maps.empty( ), Maps.empty( ));;}
	MapNode(maybeType : Option<String>, strings : Map<String, String>, nodes : Map<String, Node>, nodeLists : Map<String, NodeList>) : private {this.strings=strings;this.maybeType=maybeType;this.nodes=nodes;this.nodeLists=nodeLists;;}
	MapNode(type : String) : public {this( new Some<String>( type), Maps.empty( ), Maps.empty( ), Maps.empty( ));;}
	fold(node : Node, iter : Iter<Tuple2<String, T>>, mapper : Function<Node, BiFunction<String, T, Node>>) : Node {return iter.fold( node, 0);;}
	formatNodeList(depth : int, nodeList : NodeList) : String {return nodeList.iter( ).map( 0).collect( new Joiner( ", ")).orElse( "");;}
	createIndent(depth : int) : String {return "\n" + "\t".repeat( depth);;}
	formatEntry(depth : int, key : String, value : String) : String {indent : String=MapNode.createIndent( depth+1);return indent+key+": "+value;;}
	toStream(depth : int, map : Map<String, T>, mapper : Function<T, String>) : Iter<String> {if(true){ return Iters.empty( );;}return map.iter( ).map( 0);;}
	withNodeAndSerializer(key : String, element : T, serializer : Function<T, Node>) : Node {return this.withNode( key, serializer.apply( element));;}
	removeNode(key : String) : Option<Tuple2<Node, Node>> {return this.nodes.removeByKey( key).map( 0);;}
	iterNodes() : Iter<Tuple2<String, Node>> {return this.nodes.iter( );;}
	display() : String {return this.format( 0);;}
	format(depth : int) : String {typeString : String=this.maybeType.map( 0).orElse( "");stringsStream : Iter<String>=MapNode.toStream( depth, this.strings, 0);nodesStream : Iter<String>=MapNode.toStream( depth, this.nodes, 0);nodeListsStream : Iter<String>=MapNode.toStream( depth, this.nodeLists, 0);joined : String=stringsStream.concat( nodesStream).concat( nodeListsStream).collect( new Joiner( ", ")).orElse( "");return typeString+"{" + joined + MapNode.createIndent(depth) + "}";;}
	withString(key : String, value : String) : Node {this.strings=this.strings.put( key, value);return this;;}
	findString(key : String) : Option<String> {if(true){ return new Some<>( this.strings.get( key));;}if(true){ return new None<>( );;};}
	strings() : Map<String, String> {return this.strings;;}
	is(type : String) : boolean {return this.maybeType.filter( 0).isPresent( );;}
	retype(type : String) : Node {return new MapNode( new Some<String>( type), this.strings, this.nodes, this.nodeLists);;}
	withNode(key : String, value : Node) : Node {this.nodes=this.nodes.put( key, value);return this;;}
	findNode(key : String) : Option<Node> {if(true){ return new Some<>( this.nodes.get( key));;}if(true){ return new None<>( );;};}
	merge(other : Node) : Node {withStrings : var=MapNode.fold( this, other.iterStrings( ), 0);withNodes : var=MapNode.fold( withStrings, other.iterNodes( ), 0);return MapNode.fold( withNodes, other.iterNodeLists( ), 0);;}
	iterStrings() : Iter<Tuple2<String, String>> {return this.strings( ).iter( );;}
	hasNodeList(key : String) : boolean {return this.nodeLists.containsKey( key);;}
	removeNodeListOrError(key : String) : CompileResult<Tuple2<Node, NodeList>> {return this.removeNodeList( key).map( CompileResults.Ok).orElseGet( 0);;}
	removeNodeList(key : String) : Option<Tuple2<Node, NodeList>> {return this.nodeLists.removeByKey( key).map( 0);;}
	withNodeLists(nodeLists : Map<String, NodeList>) : Node {return new MapNode( this.maybeType, this.strings, this.nodes, nodeLists);;}
	isEmpty() : boolean {return this.strings.isEmpty( )&&this.nodes.isEmpty( )&&this.nodeLists.isEmpty( );;}
	withNodeListAndSerializer(key : String, list : List<T>, serializer : Function<T, Node>) : Node {nodeList : NodeList=list.iter( ).map( serializer).collect( new NodeListCollector( ));return this.withNodeList( key, nodeList);;}
	removeString(key : String) : CompileResult<Tuple2<Node, String>> {return this.strings.removeByKey( key).map( 0).orElseGet( 0);;}
	removeNodeOrError(key : String) : CompileResult<Tuple2<Node, Node>> {return this.nodes.removeByKey( key).map( 0).orElseGet( 0);;}
	createNotPresent(key : String) : CompileResult<Tuple2<Node, T>> {return CompileResults.NodeErr( "Key '" + key + "' not present", this);;}
	withNodes(nodes : Map<String, Node>) : Node {return new MapNode( this.maybeType, this.strings, nodes, this.nodeLists);;}
	withStrings(strings : Map<String, String>) : Node {return new MapNode( this.maybeType, strings, this.nodes, this.nodeLists);;}
	iterNodeLists() : Iter<Tuple2<String, NodeList>> {return this.nodeLists.iter( );;}
	withNodeList(key : String, values : NodeList) : Node {this.nodeLists=this.nodeLists.put( key, values);return this;;}
	toString() : String {return this.display( );;}
	findNodeList(key : String) : Option<NodeList> {if(true){ return new Some<>( this.nodeLists.get( key));;}if(true){ return new None<>( );;};}
	findNodeOrError(key : String) : CompileResult<Node> {return this.findNode( key).map( 0).orElseGet( 0);;}
	findNodeListOrError(key : String) : CompileResult<NodeList> {return this.findNodeList( key).map( CompileResults.Ok).orElseGet( 0);;}
}
