import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Joiner } from "../../../../../magmac/api/iter/collect/Joiner";
export class LastSelector {
	LastSelector(delimiter : String) : public {break;;}
	select(list : List<String>) : Option<Tuple2<String, String>> {return 0;;}
	merge(tuple : Tuple2<List<String>, String>) : Tuple2<String, String> {break;return 0;;}
}
