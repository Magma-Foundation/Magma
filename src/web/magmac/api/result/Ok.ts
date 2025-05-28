import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Function } from "../../../java/util/function/Function";
import { Supplier } from "../../../java/util/function/Supplier";
export class Ok {
	mapValue : Result<R, X> {
	}
	and : Result<Tuple2<T, R>, X> {
	}
	match : R {
	}
	flatMapValue : Result<R, X> {
	}
	mapErr : Result<T, R> {
	}
}
