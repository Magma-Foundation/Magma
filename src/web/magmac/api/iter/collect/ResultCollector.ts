import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
export class ResultCollector {
	createInitial() : Result<C, X> {return new Ok<>( this.collector.createInitial( ));}
	fold(currentResult : Result<C, X>, element : Result<T, X>) : Result<C, X> {return currentResult.and( () ->element).mapValue( (Tuple2<C, T> tuple) ->this.collector.fold( tuple.left( ), tuple.right( )));}
}
