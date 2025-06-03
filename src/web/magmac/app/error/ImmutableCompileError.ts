import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { Max } from "../../../magmac/api/iter/collect/Max";
import { Context } from "../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
export class ImmutableCompileError {
	ImmutableCompileError(message : String, context : Context) : public {;;}
	formatEntry(depth : int, display : String) : String {;;;}
	display() : String {;;}
	format(depth : int) : String {;;;;}
	joinSorted(depth : int, copy : List<CompileError>) : String {;;}
	computeMaxDepth() : int {;;;}
}
