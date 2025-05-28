import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
export interface Node {
	temp : ?;
	temp : ?;
	format(depth : int) : String;
	temp : ?;
	temp : ?;
	findString(key : String) : Option<String>;
	merge(other : Node) : Node;
	is(type : String) : boolean;
	retype(type : String) : Node;
	temp : ?;
	findNodeList(key : String) : Option<NodeList>;
	temp : ?;
	findNode(key : String) : Option<Node>;
	temp : ?;
	hasNodeList(key : String) : boolean;
}
