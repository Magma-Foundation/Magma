import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	foldSingleQuotes(current : DivideState, c : char) : Option<DivideState> {if(true){ ;;};;}
	foldEscape(current : DivideState, c : char) : Option<DivideState> {if(true){ ;;}if(true){ ;;};}
	foldDoubleQuotes(state : DivideState, c : char) : Option<DivideState> {if(true){ ;;};if(true){ ;if(true){ ;;};;if(true){ ;;}if(true){ ;;};};;}
	divide(input : String) : Iter<String> {;if(true){ ;if(true){ ;;};;;;;};;}
	createDelimiter() : String {;;}
}
