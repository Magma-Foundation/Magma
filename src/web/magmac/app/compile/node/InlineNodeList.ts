import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../magmac/api/iter/Iter";
export class InlineNodeList {
	temp : ?;
	InlineNodeList : public {
	}
	empty : NodeList {
	}
	iter : Iter<Node> {
	}
	last : Node {
	}
	add : NodeList {
	}
	addAll : NodeList {
	}
	findLast : Option<Node> {
	}
}
