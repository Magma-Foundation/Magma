import { Node } from "../../../magmac/app/compile/node/Node";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
import { ParseResult } from "../../../magmac/app/stage/result/ParseResult";
export interface Passer {
	 pass( state : ParseState,  node : Node) : ParseResult;
}
