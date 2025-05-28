import { Option } from "../../../magmac/api/Option";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter {
	public foldToResult( initial : R,  folder : BiFunction<R, T, Result<R, X>>) : Result<R, X> {
		return this.fold( HeadedIter.createInitial( initial), ( rxResult : Result<R, X>,  t : T) => rxResult.flatMapValue( ( r : R) => folder.apply( r, t)));
	}
	private static createInitial( initial : R) : Result<R, X> {
		return new Ok<>( initial);
	}
	public map( mapper : Function<T, R>) : Iter<R> {
		return new HeadedIter<>( ( )->this.head.next( ).map( mapper));
	}
	public fold( initial : R,  folder : BiFunction<R, T, R>) : R {
		 current : R=initial;
		if(true){ 
		 finalCurrent : R=current;
		 option : Option<R>=this.head.next( ).map( ( next : T) => folder.apply( finalCurrent, next));
		if(option.isPresent( )){ 
		current=option.orElse( null);}
		else{ 
		return current;}}
	}
	public collect( collector : Collector<T, C>) : C {
		return this.fold( collector.createInitial( ), ( current : C,  element : T) => collector.fold( current, element));
	}
	public filter( predicate : Predicate<T>) : Iter<T> {
		return this.flatMap( ( value : T) => {
		if(predicate.test( value)){ 
		return new HeadedIter<>( new SingleHead<>( value));}
		return new HeadedIter<>( new EmptyHead<>( ));});
	}
	public next() : Option<T> {
		return this.head.next( );
	}
	public flatMap( mapper : Function<T, Iter<R>>) : Iter<R> {
		return new HeadedIter<>( this.head.next( ).map( mapper).<Head<R>>map( ( initial : Iter<R>) => new FlatMapHead<>( this.head, mapper, initial)).orElse( new EmptyHead<>( )));
	}
	public concat( other : Iter<T>) : Iter<T> {
		return new HeadedIter<>( ( )->this.head.next( ).or( ( )->other.next( )));
	}
}
