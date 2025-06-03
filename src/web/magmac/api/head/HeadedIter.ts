import { Option } from "../../../magmac/api/Option";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter<T> {
	foldToResult(initial : R, folder : BiFunction<R, T, Result<R, X>>) : Result<R, X> {return this.fold( HeadedIter.createInitial( initial), 0);;}
	createInitial(initial : R) : Result<R, X> {return new Ok<>( initial);;}
	map(mapper : Function<T, R>) : Iter<R> {return new HeadedIter<>( 0);;}
	fold(initial : R, folder : BiFunction<R, T, R>) : R {break;if(true){ break;break;if(true){ break;;}if(true){ return current;;};};}
	collect(collector : Collector<T, C>) : C {return this.fold( collector.createInitial( ), collector.fold);;}
	filter(predicate : Predicate<T>) : Iter<T> {return this.flatMap( 0);;}
	next() : Option<T> {return this.head.next( );;}
	flatMap(mapper : Function<T, Iter<R>>) : Iter<R> {return new HeadedIter<>( 0( 0).orElse( new EmptyHead<>( )));;}
	concat(other : Iter<T>) : Iter<T> {return new HeadedIter<>( 0);;}
}
