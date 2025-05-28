import { Collector } from "../../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../../magmac/api/result/Ok";
export class CompileResultCollector {
	createInitial() : CompileResult<C> {return InlineCompileResult.fromResult( new Ok<>( this.joiner.createInitial( )));}
	fold(maybeCurrent : CompileResult<C>, maybeElement : CompileResult<T>) : CompileResult<C> {return maybeCurrent.flatMapValue( (C currentValue) ->maybeElement.mapValue( (T element) ->this.joiner.fold( currentValue, element)));}
}
