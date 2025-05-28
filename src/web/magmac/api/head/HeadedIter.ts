import { Option } from "../../../magmac/api/Option";
import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Ok } from "../../../magmac/api/result/Ok";
import { Result } from "../../../magmac/api/result/Result";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export class HeadedIter {
	foldToResult(initial : R, folder : BiFunction<R, T, Result<R, X>>) : Result<R, X> {
		return this.fold( HeadedIter.createInitial( initial), 
                (Result<R, X> rxResult, T t)  => rxResult.flatMapValue( 
                        (R r)  => folder.apply( r, t)));
	}
	createInitial(initial : R) : Result<R, X> {
		return new Ok<>( initial);
	}
	map(mapper : Function<T, R>) : Iter<R> {
		return new HeadedIter<>( ()  => this.head.next( ).map( mapper));
	}
	fold(initial : R, folder : BiFunction<R, T, R>) : R {
		 R current=initial;
		if(true){ 
		 R finalCurrent=current;
		 Option<R> option=this.head.next( ).map( (T next)  => folder.apply( finalCurrent, next));
		if(option.isPresent( )){ 
		current=option.orElse( null);}
		else{ 
		return current;}}
	}
	collect(collector : Collector<T, C>) : C {
		return this.fold( collector.createInitial( ),  (C current, T element)  => collector.fold( current, element));
	}
	filter(predicate : Predicate<T>) : Iter<T> {
		return this.flatMap( (T value)  => {if(predicate.test( value)){ return new HeadedIter<>( new SingleHead<>( value));}return new HeadedIter<>( new EmptyHead<>( ));});
	}
	next() : Option<T> {
		return this.head.next( );
	}
	flatMap(mapper : Function<T, Iter<R>>) : Iter<R> {
		return new HeadedIter<>( this.head.next( ).map( mapper).<Head<R>>map( (Iter<R> initial)  => new FlatMapHead<>( this.head, mapper, initial)).orElse( new EmptyHead<>( )));
	}
	concat(other : Iter<T>) : Iter<T> {
		return new HeadedIter<>( ()  => this.head.next( ).or( ()  => other.next( )));
	}
}
