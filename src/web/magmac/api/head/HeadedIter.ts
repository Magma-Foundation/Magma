import { Option } from "../../../magmac/api/Option";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter<T> {
	foldToResult(initial : R, folder : BiFunction<R, T, Result<R, X>>) : Result<R, X> {break;;}
	createInitial(initial : R) : Result<R, X> {break;;}
	map(mapper : Function<T, R>) : Iter<R> {break;;}
	fold(initial : R, folder : BiFunction<R, T, R>) : R {break;if(true){ break;break;if(true){ break;;}if(true){ break;;};};}
	collect(collector : Collector<T, C>) : C {break;;}
	filter(predicate : Predicate<T>) : Iter<T> {break;;}
	next() : Option<T> {break;;}
	flatMap(mapper : Function<T, Iter<R>>) : Iter<R> {break;;}
	concat(other : Iter<T>) : Iter<T> {break;;}
}
