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
	MapNode() : public {break;;}
	MapNode(maybeType : Option<String>, strings : Map<String, String>, nodes : Map<String, Node>, nodeLists : Map<String, NodeList>) : private {break;break;break;break;;}
	MapNode(type : String) : public {break;;}
	fold(node : Node, iter : Iter<Tuple2<String, T>>, mapper : Function<Node, BiFunction<String, T, Node>>) : Node {break;;}
	formatNodeList(depth : int, nodeList : NodeList) : String {break;;}
	createIndent(depth : int) : String {break;;}
	formatEntry(depth : int, key : String, value : String) : String {break;break;;}
	toStream(depth : int, map : Map<String, T>, mapper : Function<T, String>) : Iter<String> {if(true){ break;;}break;;}
	withNodeAndSerializer(key : String, element : T, serializer : Function<T, Node>) : Node {break;;}
	removeNode(key : String) : Option<Tuple2<Node, Node>> {break;;}
	iterNodes() : Iter<Tuple2<String, Node>> {break;;}
	display() : String {break;;}
	format(depth : int) : String {break;break;break;break;break;break;;}
	withString(key : String, value : String) : Node {break;break;;}
	findString(key : String) : Option<String> {if(true){ break;;}if(true){ break;;};}
	strings() : Map<String, String> {break;;}
	is(type : String) : boolean {break;;}
	retype(type : String) : Node {break;;}
	withNode(key : String, value : Node) : Node {break;break;;}
	findNode(key : String) : Option<Node> {if(true){ break;;}if(true){ break;;};}
	merge(other : Node) : Node {break;break;break;;}
	iterStrings() : Iter<Tuple2<String, String>> {break;;}
	hasNodeList(key : String) : boolean {break;;}
	removeNodeListOrError(key : String) : CompileResult<Tuple2<Node, NodeList>> {break;;}
	removeNodeList(key : String) : Option<Tuple2<Node, NodeList>> {break;;}
	withNodeLists(nodeLists : Map<String, NodeList>) : Node {break;;}
	isEmpty() : boolean {break;;}
	withNodeListAndSerializer(key : String, list : List<T>, serializer : Function<T, Node>) : Node {break;break;;}
	removeString(key : String) : CompileResult<Tuple2<Node, String>> {break;;}
	removeNodeOrError(key : String) : CompileResult<Tuple2<Node, Node>> {break;;}
	createNotPresent(key : String) : CompileResult<Tuple2<Node, T>> {break;;}
	withNodes(nodes : Map<String, Node>) : Node {break;;}
	withStrings(strings : Map<String, String>) : Node {break;;}
	iterNodeLists() : Iter<Tuple2<String, NodeList>> {break;;}
	withNodeList(key : String, values : NodeList) : Node {break;break;;}
	toString() : String {break;;}
	findNodeList(key : String) : Option<NodeList> {if(true){ break;;}if(true){ break;;};}
	findNodeOrError(key : String) : CompileResult<Node> {break;;}
	findNodeListOrError(key : String) : CompileResult<NodeList> {break;;}
}
