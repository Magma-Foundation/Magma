import { Option } from "../../../../magmac/api/Option";
import { Iter } from "../../../../magmac/api/iter/Iter";
export interface NodeList {iter() : Iter<Node>;findLast() : Option<Node>;
	add(element : Node) : NodeList;
	addAll(others : NodeList) : NodeList;
}
