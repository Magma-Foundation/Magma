import { Collector } from "../../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../../magmac/api/result/Ok";
export class CompileResultCollector<T,  C> {
	createInitial() : CompileResult<C> {return 0.fromResult( new Ok<>( 0.joiner.createInitial( )));;}
	fold(maybeCurrent : CompileResult<C>, maybeElement : CompileResult<T>) : CompileResult<C> {return 0.flatMapValue( 0);;}
}
