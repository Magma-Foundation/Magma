import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
export class ResultCollector<T,  C,  X> {
	createInitial() : Result<C, X> {return new Ok<>( this.collector.createInitial( ));;}
	fold(currentResult : Result<C, X>, element : Result<T, X>) : Result<C, X> {return currentResult.and( 0).mapValue( 0);;}
}
