import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Joiner } from "../../../../../magmac/api/iter/collect/Joiner";
export class LastSelector {
	temp : ?;
	LastSelector(delimiter : String) : public {
		this.delimiter=delimiter;
	}
	select(list : List<String>) : Option<Tuple2<String, String>> {
		return list.popLast( ).map( (tuple : Tuple2<List<String>, String>) => this.merge( tuple));
	}
	merge(tuple : Tuple2<List<String>, String>) : Tuple2<String, String> {
		joined : String=tuple.left( ).iter( ).collect( new Joiner( this.delimiter)).orElse( "");
		return new Tuple2<String, String>( joined, tuple.right( ));
	}
}
