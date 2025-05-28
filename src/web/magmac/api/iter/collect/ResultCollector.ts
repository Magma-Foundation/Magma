import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
export class ResultCollector<T,  C,  X> {
	public createInitial() : Result<C, X> {
		return new Ok<>( this.collector.createInitial( ));
	}
	public fold( currentResult : Result<C, X>,  element : Result<T, X>) : Result<C, X> {
		return currentResult.and( ( )->element).mapValue( ( tuple : Tuple2<C, T>) => this.collector.fold( tuple.left( ), tuple.right( )));
	}
}
