import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { List } from "../../../../magmac/api/collect/list/List";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { Function } from "../../../../java/util/function/Function";
export interface Node {
	findNodeListOrError(key : String) : CompileResult<NodeList>;
	findNodeOrError(key : String) : CompileResult<Node>;
	iterNodes() : Iter<Tuple2<String, Node>>;
	display() : String;
	format(depth : int) : String;
	iterNodeLists() : Iter<Tuple2<String, NodeList>>;
	withString(key : String, value : String) : Node;
	findString(key : String) : Option<String>;
	merge(other : Node) : Node;
	is(type : String) : boolean;
	retype(type : String) : Node;
	withNodeList(key : String, values : NodeList) : Node;
	findNodeList(key : String) : Option<NodeList>;
	withNode(key : String, value : Node) : Node;
	findNode(key : String) : Option<Node>;
	iterStrings() : Iter<Tuple2<String, String>>;
	hasNodeList(key : String) : boolean;
	removeNodeListOrError(key : String) : CompileResult<Tuple2<Node, NodeList>>;
	removeNodeList(key : String) : Option<Tuple2<Node, NodeList>>;
	isEmpty() : boolean;
	removeString(key : String) : CompileResult<Tuple2<Node, String>>;
	removeNodeOrError(key : String) : CompileResult<Tuple2<Node, Node>>;
	withNodeListAndSerializer(key : String, list : List<T>, serializer : Function<T, Node>) : Node;
	withNodeAndSerializer(key : String, element : T, serializer : Function<T, Node>) : Node;
	withNodeListSerialized(key : String, list : List<T>) : Node {return 0.withNodeListAndSerializer( 0, 0, 0.serialize);;}
	withNodeSerialized(key : String, element : T) : Node {return 0.withNodeAndSerializer( 0, 0, 0.serialize);;}
	removeNode(key : String) : Option<Tuple2<Node, Node>>;
}
