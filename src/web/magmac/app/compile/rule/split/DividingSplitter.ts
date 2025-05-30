import { Option } from "../../../../../magmac/api/Option";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { ListCollector } from "../../../../../magmac/api/iter/collect/ListCollector";
import { Splitter } from "../../../../../magmac/app/compile/rule/Splitter";
import { Divider } from "../../../../../magmac/app/compile/rule/divide/Divider";
export class DividingSplitter {
	DividingSplitter : private;
	Last : Splitter;
	First : Splitter;
	split : Option<Tuple2<String, String>>;
	createMessage : String;
	merge : String;
}
