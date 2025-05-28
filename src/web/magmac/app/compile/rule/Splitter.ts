import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
export interface Splitter {
	split(input : String) : Option<Tuple2<String, String>>;
	temp : ?;
	temp : ?;
}
