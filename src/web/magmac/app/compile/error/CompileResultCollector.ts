import { Collector } from "../../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../../magmac/api/result/Ok";
export class CompileResultCollector<T,  C> {
	createInitial() : CompileResult<C> {;;}
	fold(maybeCurrent : CompileResult<C>, maybeElement : CompileResult<T>) : CompileResult<C> {;;}
}
