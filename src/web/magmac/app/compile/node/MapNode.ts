import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { Maps } from "../../../../magmac/api/collect/map/Maps";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { Joiner } from "../../../../magmac/api/iter/collect/Joiner";
import { BiFunction } from "../../../../java/util/function/BiFunction";
import { Function } from "../../../../java/util/function/Function";
export class MapNode {maybeType : Option<String>;nodeLists : Map<String, NodeList>;nodes : Map<String, Node>;strings : Map<String, String>;
	MapNode() : public {
		this( new None<String>( ), Maps.empty( ), Maps.empty( ), Maps.empty( ));
	}
	MapNode(maybeType : Option<String>, strings : Map<String, String>, nodes : Map<String, Node>, nodeLists : Map<String, NodeList>) : private {
		this.strings=strings;
		this.maybeType=maybeType;
		this.nodes=nodes;
		this.nodeLists=nodeLists;
	}
	MapNode(type : String) : public {
		this( new Some<String>( type), Maps.empty( ), Maps.empty( ), Maps.empty( ));
	}
	fold(node : Node, iter : Iter<Tuple2<String, T>>, mapper : Function<Node, BiFunction<String, T, Node>>) : Node {
		return iter.fold( node, (current : Node, tuple : Tuple2<String, T>) => {left : String=tuple.left( );right : T=tuple.right( );return mapper.apply( current).apply( left, right);});
	}
	formatNodeList(depth : int, nodeList : NodeList) : String {
		return nodeList.iter( ).map( (child : Node) => child.format( depth+1)).collect( new Joiner( ", ")).orElse( "");
	}
	createIndent(depth : int) : String {
		return "\n" + "\t".repeat( depth);
	}
	formatEntry(depth : int, key : String, value : String) : String {
		indent : String=MapNode.createIndent( depth+1);
		return indent+key+": "+value;
	}
	iterNodes() : Iter<Tuple2<String, Node>> {
		return this.nodes.iterEntries( );
	}
	display() : String {
		return this.format( 0);
	}
	format(depth : int) : String {
		typeString : String=this.maybeType.map( (type : String) => type+" ").orElse( "");
		stringsStream : Iter<String>=this.toStream( depth, this.strings, (value : String) => "\"" + value + "\"");
		nodesStream : Iter<String>=this.toStream( depth, this.nodes, (value : Node) => value.format( depth+1));
		nodeListsStream : Iter<String>=this.toStream( depth, this.nodeLists, (values : NodeList) => "[" + MapNode.formatNodeList(depth, values) + "]");
		joined : String=stringsStream.concat( nodesStream).concat( nodeListsStream).collect( new Joiner( ", ")).orElse( "");
		return typeString+"{" + joined + MapNode.createIndent(depth) + "}";
	}
	toStream(depth : int, map : Map<String, T>, mapper : Function<T, String>) : Iter<String> {
		if(map.isEmpty( )){ 
		return Iters.empty( );}
		return map.iterEntries( ).map( (entry : Tuple2<String, T>) => {key : String=entry.left( );value : T=entry.right( );return MapNode.formatEntry( depth, key, mapper.apply( value));});
	}
	withString(key : String, value : String) : Node {
		this.strings=this.strings.put( key, value);
		return this;
	}
	findString(key : String) : Option<String> {
		if(this.strings.containsKey( key)){ 
		return new Some<>( this.strings.get( key));}
		else{ 
		return new None<>( );}
	}
	strings() : Map<String, String> {
		return this.strings;
	}
	is(type : String) : boolean {
		return this.maybeType.filter( (inner : String) => inner.equals( type)).isPresent( );
	}
	retype(type : String) : Node {
		return new MapNode( new Some<String>( type), this.strings, this.nodes, this.nodeLists);
	}
	withNode(key : String, value : Node) : Node {
		this.nodes=this.nodes.put( key, value);
		return this;
	}
	findNode(key : String) : Option<Node> {
		if(this.nodes.containsKey( key)){ 
		return new Some<>( this.nodes.get( key));}
		else{ 
		return new None<>( );}
	}
	merge(other : Node) : Node {
		withStrings : var=MapNode.fold( this, other.iterStrings( ), (current : Node) => (key2 : String, value1 : String) => current.withString( key2, value1));
		withNodes : var=MapNode.fold( withStrings, other.iterNodes( ), (current : Node) => (key1 : String, value : Node) => current.withNode( key1, value));
		return MapNode.fold( withNodes, other.iterNodeLists( ), (current : Node) => (key : String, values : NodeList) => current.withNodeList( key, values));
	}
	iterStrings() : Iter<Tuple2<String, String>> {
		return this.strings( ).iterEntries( );
	}
	hasNodeList(key : String) : boolean {
		return this.nodeLists.containsKey( key);
	}
	iterNodeLists() : Iter<Tuple2<String, NodeList>> {
		return this.nodeLists.iterEntries( );
	}
	withNodeList(key : String, values : NodeList) : Node {
		this.nodeLists=this.nodeLists.put( key, values);
		return this;
	}
	toString() : String {
		return this.display( );
	}
	findNodeList(key : String) : Option<NodeList> {
		if(this.nodeLists.containsKey( key)){ 
		return new Some<>( this.nodeLists.get( key));}
		else{ 
		return new None<>( );}
	}
}
