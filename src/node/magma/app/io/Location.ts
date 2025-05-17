// []
import { List } from "../../../magma/api/collect/list/List";
export class Location {
	namespace: List<string>;
	name: string;
	constructor (namespace: List<string>, name: string) {
		this.namespace = namespace;
		this.name = name;
	}
}
