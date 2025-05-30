import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Joiner } from "../../../../../magmac/api/iter/collect/Joiner";
export class LastSelector {
	LastSelector : public;
	select : Option<Tuple2<String, String>>;
	merge : Tuple2<String, String>;
}
