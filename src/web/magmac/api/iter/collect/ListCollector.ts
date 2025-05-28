import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
export class ListCollector {
	createInitial() : List<T> {
		return Lists.empty( );
	}
	fold(current : List<T>, element : T) : List<T> {
		return current.add( element);
	}
}
