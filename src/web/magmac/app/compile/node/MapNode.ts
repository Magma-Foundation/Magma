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
	MapNode() : public {;;}
	MapNode(maybeType : Option<String>, strings : Map<String, String>, nodes : Map<String, Node>, nodeLists : Map<String, NodeList>) : private {;;;;;}
	MapNode(type : String) : public {;;}
	fold(node : Node, iter : Iter<Tuple2<String, T>>, mapper : Function<Node, BiFunction<String, T, Node>>) : Node {;;}
	formatNodeList(depth : int, nodeList : NodeList) : String {;;}
	createIndent(depth : int) : String {;;}
	formatEntry(depth : int, key : String, value : String) : String {;;;}
	toStream(depth : int, map : Map<String, T>, mapper : Function<T, String>) : Iter<String> {;;;}
	withNodeAndSerializer(key : String, element : T, serializer : Function<T, Node>) : Node {;;}
	removeNode(key : String) : Option<Tuple2<Node, Node>> {;;}
	iterNodes() : Iter<Tuple2<String, Node>> {;;}
	display() : String {;;}
	format(depth : int) : String {;;;;;;;}
	withString(key : String, value : String) : Node {;;;}
	findString(key : String) : Option<String> {;;;}
	strings() : Map<String, String> {;;}
	is(type : String) : boolean {;;}
	retype(type : String) : Node {;;}
	withNode(key : String, value : Node) : Node {;;;}
	findNode(key : String) : Option<Node> {;;;}
	merge(other : Node) : Node {;;;;}
	iterStrings() : Iter<Tuple2<String, String>> {;;}
	hasNodeList(key : String) : boolean {;;}
	removeNodeListOrError(key : String) : CompileResult<Tuple2<Node, NodeList>> {;;}
	removeNodeList(key : String) : Option<Tuple2<Node, NodeList>> {;;}
	withNodeLists(nodeLists : Map<String, NodeList>) : Node {;;}
	isEmpty() : boolean {;;}
	withNodeListAndSerializer(key : String, list : List<T>, serializer : Function<T, Node>) : Node {;;;}
	removeString(key : String) : CompileResult<Tuple2<Node, String>> {;;}
	removeNodeOrError(key : String) : CompileResult<Tuple2<Node, Node>> {;;}
	createNotPresent(key : String) : CompileResult<Tuple2<Node, T>> {;;}
	withNodes(nodes : Map<String, Node>) : Node {;;}
	withStrings(strings : Map<String, String>) : Node {;;}
	iterNodeLists() : Iter<Tuple2<String, NodeList>> {;;}
	withNodeList(key : String, values : NodeList) : Node {;;;}
	toString() : String {;;}
	findNodeList(key : String) : Option<NodeList> {;;;}
	findNodeOrError(key : String) : CompileResult<Node> {;;}
	findNodeListOrError(key : String) : CompileResult<NodeList> {;;}
}
