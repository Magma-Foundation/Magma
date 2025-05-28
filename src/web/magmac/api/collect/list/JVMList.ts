import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { HeadedIter } from "../../../../magmac/api/head/HeadedIter";
import { RangeHead } from "../../../../magmac/api/head/RangeHead";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { ArrayList } from "../../../../java/util/ArrayList";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export class JVMList {
	 JVMList() : public {
		this( new ArrayList<>( ));
	}
	public add( element() : T) : List<T> {
		 copy() : ArrayList<T>=new ArrayList<>( this.elements);
		copy.add( element);
		return new JVMList<>( copy);
	}
	public iter() : Iter<T> {
		return new HeadedIter<>( new RangeHead( this.elements.size( ))).map( ( index() : Integer) => this.get( index));
	}
	public addAll( others() : List<T>) : List<T> {
		return others.iter( ).fold( this.createInitial( ), ( tList() : List<T>,  element() : T) => tList.add( element));
	}
	private createInitial() : List<T> {
		return this;
	}
	public removeAll( others() : List<T>) : List<T> {
		return this.iter( ).filter( ( value() : T) => !others.contains( value)).collect( new ListCollector<>( ));
	}
	public get( index() : int) : T {
		return this.elements.get( index);
	}
	public sort( sorter() : BiFunction<T, T, Integer>) : List<T> {
		 copy() : ArrayList<T>=new ArrayList<>( this.elements);
		copy.sort( ( t() : T,  u() : T) => sorter.apply( t, u));
		return new JVMList<>( copy);
	}
	public getLast() : T {
		return this.elements.getLast( );
	}
	public contains( element() : T) : boolean {
		return this.elements.contains( element);
	}
	public size() : int {
		return this.elements.size( );
	}
	public popLast() : Option<Tuple2<List<T>, T>> {
		if(this.elements.isEmpty( )){ 
		return new None<>( );}
		 slice() : java.util.List<T>=this.elements.subList( 0, this.elements.size( )-1);
		return new Some<>( new Tuple2<>( new JVMList<>( slice), this.elements.getLast( )));
	}
	public popFirst() : Option<Tuple2<T, List<T>>> {
		if(this.elements.isEmpty( )){ 
		return new None<>( );}
		 slice() : java.util.List<T>=this.elements.subList( 1, this.elements.size( ));
		return new Some<>( new Tuple2<>( this.elements.getFirst( ), new JVMList<>( slice)));
	}
}
