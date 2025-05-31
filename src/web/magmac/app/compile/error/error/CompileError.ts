import { Error } from "../../../../../magmac/api/error/Error";
export interface CompileError {
	computeMaxDepth() : int;
	format(depth : int) : String;
}
