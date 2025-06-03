import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../../magmac/api/iter/collect/ListCollector";
import { Splitter } from "../../../../../magmac/app/compile/rule/Splitter";
import { Divider } from "../../../../../magmac/app/compile/rule/divide/Divider";
export class DividingSplitter {
	DividingSplitter(divider : Divider, selector : Selector) : private {break;break;;}
	Last(divider : Divider, delimiter : String) : Splitter {return new DividingSplitter( 0, new LastSelector( 0));;}
	First(divider : Divider, delimiter : String) : Splitter {return new DividingSplitter( 0, new FirstSelector( 0));;}
	split(input : String) : Option<Tuple2<String, String>> {break;return 0.selector.select( 0);;}
	createMessage() : String {return "Insufficient segments present";;}
	merge(leftString : String, rightString : String) : String {return 0;;}
}
