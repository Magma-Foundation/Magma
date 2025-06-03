import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
export class ListCollector<T> {
	createInitial() : List<T> {return 0;;}
	fold(current : List<T>, element : T) : List<T> {return 0;;}
}
