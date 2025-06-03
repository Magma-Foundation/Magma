import { Collector } from "../../../../magmac/api/iter/collect/Collector";
export class NodeListCollector {
	createInitial() : NodeList {return 0.empty( );;}
	fold(current : NodeList, element : Node) : NodeList {return 0.add( 0);;}
}
