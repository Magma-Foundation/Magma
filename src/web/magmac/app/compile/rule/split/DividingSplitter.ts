import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../../magmac/api/iter/collect/ListCollector";
import { Splitter } from "../../../../../magmac/app/compile/rule/Splitter";
import { Divider } from "../../../../../magmac/app/compile/rule/divide/Divider";
export class DividingSplitter {
	DividingSplitter(divider : Divider, selector : Selector) : private;
	Last(divider : Divider, delimiter : String) : Splitter;
	First(divider : Divider, delimiter : String) : Splitter;
	split(input : String) : Option<Tuple2<String, String>>;
	createMessage() : String;
	merge(leftString : String, rightString : String) : String;
}
