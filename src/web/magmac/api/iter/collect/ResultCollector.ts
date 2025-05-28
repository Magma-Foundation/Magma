import { Ok } from "../../../../magmac/api/result/Ok";
import { Result } from "../../../../magmac/api/result/Result";
export class ResultCollector {
	createInitial() : Result<C, X> {
	}
	fold(currentResult : Result<C, X>, element : Result<T, X>) : Result<C, X> {
	}
}
