import { Option } from "../../../magmac/api/Option";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter<T> {
	foldToResult(initial : R, folder : BiFunction<R, T, Result<R, X>>) : Result<R, X> {return 0.fold( 0.createInitial( 0), 0);;}
	createInitial(initial : R) : Result<R, X> {return new Ok<>( 0);;}
	map(mapper : Function<T, R>) : Iter<R> {return new HeadedIter<>( 0);;}
	fold(initial : R, folder : BiFunction<R, T, R>) : R {break;if(true){ break;break;if(true){ break;;}if(true){ return 0;;};};}
	collect(collector : Collector<T, C>) : C {return 0.fold( 0.createInitial( ), 0.fold);;}
	filter(predicate : Predicate<T>) : Iter<T> {return 0.flatMap( 0);;}
	next() : Option<T> {return 0.head.next( );;}
	flatMap(mapper : Function<T, Iter<R>>) : Iter<R> {return new HeadedIter<>( 0( 0).orElse( new EmptyHead<>( )));;}
	concat(other : Iter<T>) : Iter<T> {return new HeadedIter<>( 0);;}
}
