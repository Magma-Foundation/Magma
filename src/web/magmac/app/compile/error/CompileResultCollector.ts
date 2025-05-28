import { Collector } from "../../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../../magmac/api/result/Ok";
export class CompileResultCollector<T,  C> {
	public createInitial() : CompileResult<C> {
		return CompileResults.fromResult( new Ok<>( this.joiner.createInitial( )));
	}
	public fold( maybeCurrent : CompileResult<C>,  maybeElement : CompileResult<T>) : CompileResult<C> {
		return maybeCurrent.flatMapValue( ( currentValue : C) => maybeElement.mapValue( ( element : T) => this.joiner.fold( currentValue, element)));
	}
}
