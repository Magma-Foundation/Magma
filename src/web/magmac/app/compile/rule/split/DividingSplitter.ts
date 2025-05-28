import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../../magmac/api/iter/collect/ListCollector";
import { Splitter } from "../../../../../magmac/app/compile/rule/Splitter";
import { Divider } from "../../../../../magmac/app/compile/rule/divide/Divider";
export class DividingSplitter {private final divider() : Divider;private final selector() : Selector;
	 DividingSplitter( divider() : Divider,  selector() : Selector) : private {
		this.divider=divider;
		this.selector=selector;
	}
	public static Last( divider() : Divider,  delimiter() : String) : Splitter {
		return new DividingSplitter( divider, new LastSelector( delimiter));
	}
	public static First( divider() : Divider,  delimiter() : String) : Splitter {
		return new DividingSplitter( divider, new FirstSelector( delimiter));
	}
	public split( input() : String) : Option<Tuple2<String, String>> {
		 list() : List<String>=this.divider.divide( input).collect( new ListCollector<>( ));
		return this.selector.select( list);
	}
	public createMessage() : String {
		return "Insufficient segments present";
	}
	public merge( leftString() : String,  rightString() : String) : String {
		return leftString+" "+rightString;
	}
}
