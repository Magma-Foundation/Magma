import { Option } from "../../../magmac/api/Option";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter<T> {
	public foldToResult( initial : R,  folder : BiFunction<R, T, Result<R, X>>) : Result<R, X> {return this.fold( HeadedIter.createInitial( initial), 0);;}
	private static createInitial( initial : R) : Result<R, X> {return new Ok<>( initial);;}
	public map( mapper : Function<T, R>) : Iter<R> {return new HeadedIter<>( 0);;}
	public fold( initial : R,  folder : BiFunction<R, T, R>) : R { let current : var=initial;if(true){  let finalCurrent : var=current; let option : var=this.head.next( ).map( 0);if(true){ current=option.orElse( null);;}if(true){ return current;;};};}
	public collect( collector : Collector<T, C>) : C {return this.fold( collector.createInitial( ), collector.fold);;}
	public filter( predicate : Predicate<T>) : Iter<T> {return this.flatMap( 0);;}
	public next() : Option<T> {return this.head.next( );;}
	public flatMap( mapper : Function<T, Iter<R>>) : Iter<R> {return new HeadedIter<>( this.head.next( ).map( mapper).<Head<R>>map( 0).orElse( new EmptyHead<>( )));;}
	public concat( other : Iter<T>) : Iter<T> {return new HeadedIter<>( 0);;}
}
