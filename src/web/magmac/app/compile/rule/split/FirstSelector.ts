import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Joiner } from "../../../../../magmac/api/iter/collect/Joiner";
export class FirstSelector {
	temp : ?;
	FirstSelector(delimiter : String) : public {
		this.delimiter=delimiter;
	}
	select(list : List<String>) : Option<Tuple2<String, String>> {
		return list.popFirst( ).map( (tuple : Tuple2<String, List<String>>) => { String joined=tuple.right( ).iter( ).collect( new Joiner( this.delimiter)).orElse( "");return new Tuple2<String, String>( tuple.left( ), joined);});
	}
}
