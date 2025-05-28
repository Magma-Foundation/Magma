import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Joiner } from "../../../../../magmac/api/iter/collect/Joiner";
export class LastSelector {private final delimiter() : String;
	 LastSelector( delimiter() : String) : public {
		this.delimiter=delimiter;
	}
	public select( list() : List<String>) : Option<Tuple2<String, String>> {
		return list.popLast( ).map( ( tuple() : Tuple2<List<String>, String>) => this.merge( tuple));
	}
	private merge( tuple() : Tuple2<List<String>, String>) : Tuple2<String, String> {
		 joined() : String=tuple.left( ).iter( ).collect( new Joiner( this.delimiter)).orElse( "");
		return new Tuple2<String, String>( joined, tuple.right( ));
	}
}
