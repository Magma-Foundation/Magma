import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { Option } from "../../../magmac/api/Option";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter {
	foldToResult : Result<R, X>{
	}
	map : Iter<R>{
	}
	fold : R{
	}
	collect : C{
	}
	filter : Iter<T>{
	}
	next : Option<T>{
	}
	flatMap : Iter<R>{
	}
	concat : Iter<T>{
	}
}
