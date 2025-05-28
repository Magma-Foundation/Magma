import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../magmac/api/iter/Iter";
export class InlineNodeList {
	temp : ?;
	InlineNodeList(elements : List<Node>) : public {
	}
	empty() : NodeList {
	}
	iter() : Iter<Node> {
	}
	last() : Node {
	}
	add(element : Node) : NodeList {
	}
	addAll(others : NodeList) : NodeList {
	}
	findLast() : Option<Node> {
	}
}
