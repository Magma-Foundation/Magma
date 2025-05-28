import { Collector } from "../../../../magmac/api/iter/collect/Collector";
export class NodeListCollector {
	public createInitial() : NodeList {
		return InlineNodeList.empty( );
	}
	public fold( current : NodeList,  element : Node) : NodeList {
		return current.add( element);
	}
}
