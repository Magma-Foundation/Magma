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
	MapNode() : public {0( 0, 0, 0, 0);;}
	MapNode(maybeType : Option<String>, strings : Map<String, String>, nodes : Map<String, Node>, nodeLists : Map<String, NodeList>) : private {break;break;break;break;;}
	MapNode(type : String) : public {0( 0, 0, 0, 0);;}
	fold(node : Node, iter : Iter<Tuple2<String, T>>, mapper : Function<Node, BiFunction<String, T, Node>>) : Node {return 0;;}
	formatNodeList(depth : int, nodeList : NodeList) : String {return 0;;}
	createIndent(depth : int) : String {return 0;;}
	formatEntry(depth : int, key : String, value : String) : String {break;return 0;;}
	toStream(depth : int, map : Map<String, T>, mapper : Function<T, String>) : Iter<String> {if(true){ return 0;;}return 0;;}
	withNodeAndSerializer(key : String, element : T, serializer : Function<T, Node>) : Node {return 0;;}
	removeNode(key : String) : Option<Tuple2<Node, Node>> {return 0;;}
	iterNodes() : Iter<Tuple2<String, Node>> {return 0;;}
	display() : String {return 0;;}
	format(depth : int) : String {break;break;break;break;break;return 0;;}
	withString(key : String, value : String) : Node {break;return 0;;}
	findString(key : String) : Option<String> {if(true){ return 0;;}if(true){ return 0;;};}
	strings() : Map<String, String> {return 0.strings;;}
	is(type : String) : boolean {return 0;;}
	retype(type : String) : Node {return 0;;}
	withNode(key : String, value : Node) : Node {break;return 0;;}
	findNode(key : String) : Option<Node> {if(true){ return 0;;}if(true){ return 0;;};}
	merge(other : Node) : Node {break;break;return 0;;}
	iterStrings() : Iter<Tuple2<String, String>> {return 0;;}
	hasNodeList(key : String) : boolean {return 0;;}
	removeNodeListOrError(key : String) : CompileResult<Tuple2<Node, NodeList>> {return 0;;}
	removeNodeList(key : String) : Option<Tuple2<Node, NodeList>> {return 0;;}
	withNodeLists(nodeLists : Map<String, NodeList>) : Node {return 0;;}
	isEmpty() : boolean {return 0;;}
	withNodeListAndSerializer(key : String, list : List<T>, serializer : Function<T, Node>) : Node {break;return 0;;}
	removeString(key : String) : CompileResult<Tuple2<Node, String>> {return 0;;}
	removeNodeOrError(key : String) : CompileResult<Tuple2<Node, Node>> {return 0;;}
	createNotPresent(key : String) : CompileResult<Tuple2<Node, T>> {return 0;;}
	withNodes(nodes : Map<String, Node>) : Node {return 0;;}
	withStrings(strings : Map<String, String>) : Node {return 0;;}
	iterNodeLists() : Iter<Tuple2<String, NodeList>> {return 0;;}
	withNodeList(key : String, values : NodeList) : Node {break;return 0;;}
	toString() : String {return 0;;}
	findNodeList(key : String) : Option<NodeList> {if(true){ return 0;;}if(true){ return 0;;};}
	findNodeOrError(key : String) : CompileResult<Node> {return 0;;}
	findNodeListOrError(key : String) : CompileResult<NodeList> {return 0;;}
}
