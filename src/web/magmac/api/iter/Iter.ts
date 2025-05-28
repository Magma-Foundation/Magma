import { Option } from "../../../magmac/api/Option";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export interface Iter {
	 foldToResult( initial : R,  folder : BiFunction<R, T, Result<R, X>>) : Result<R, X>;
	 map( mapper : Function<T, R>) : Iter<R>;
	 fold( initial : R,  folder : BiFunction<R, T, R>) : R;
	 collect( collector : Collector<T, C>) : C;
	 filter( predicate : Predicate<T>) : Iter<T>;
	 next() : Option<T>;
	 flatMap( mapper : Function<T, Iter<R>>) : Iter<R>;
	 concat( other : Iter<T>) : Iter<T>;
}
