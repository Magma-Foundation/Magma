import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Iter } from "../../../../../magmac/api/iter/Iter";
import { Folder } from "../../../../../magmac/app/compile/rule/fold/Folder";
export class FoldingDivider {
	foldSingleQuotes : Option<DivideState> {
	}
	foldEscape : Option<DivideState> {
	}
	foldDoubleQuotes : Option<DivideState> {
	}
	divide : Iter<String> {
	}
}
