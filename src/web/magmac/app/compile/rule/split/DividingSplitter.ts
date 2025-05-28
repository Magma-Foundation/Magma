import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../../magmac/api/iter/collect/ListCollector";
import { Splitter } from "../../../../../magmac/app/compile/rule/Splitter";
import { Divider } from "../../../../../magmac/app/compile/rule/divide/Divider";
export class DividingSplitter {
	temp : ?;
	temp : ?;
	DividingSplitter(divider : Divider, selector : Selector) : private {
		this.divider=divider;
		this.selector=selector;
	}
	Last(divider : Divider, delimiter : String) : Splitter {
		return new DividingSplitter( divider, new LastSelector( delimiter));
	}
	First(divider : Divider, delimiter : String) : Splitter {
		return new DividingSplitter( divider, new FirstSelector( delimiter));
	}
	split(input : String) : Option<Tuple2<String, String>> {
		list : List<String>=this.divider.divide( input).collect( new ListCollector<>( ));
		return this.selector.select( list);
	}
	createMessage() : String {
		return "Insufficient segments present";
	}
	merge(leftString : String, rightString : String) : String {
		return leftString+" "+rightString;
	}
}
