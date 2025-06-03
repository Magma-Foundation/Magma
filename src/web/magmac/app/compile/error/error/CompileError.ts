import { List } from "../../../../../magmac/api/collect/list/List";
import { Error } from "../../../../../magmac/api/error/Error";
export interface CompileError {
	 computeMaxDepth() : int;
	 format( depth : int,  indices : List<Integer>) : String;
}
