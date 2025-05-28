import { Collector } from "../../../../magmac/api/iter/collect/Collector";
export class NodeListCollector {
	createInitial() : NodeList {
		return InlineNodeList.empty( );
	}
	fold(current : NodeList, element : Node) : NodeList {
		return current.add( element);
	}
}
