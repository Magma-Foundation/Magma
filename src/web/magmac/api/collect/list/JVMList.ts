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
	JVMList() : public {this( new ArrayList<>( ));}
	add(element : T) : List<T> { ArrayList<T> copy=new ArrayList<>( this.elements);copy.add( element);return new JVMList<>( copy);}
	iter() : Iter<T> {return new HeadedIter<>( new RangeHead( this.elements.size( ))).map( (Integer index) ->this.get( index));}
	addAll(others : List<T>) : List<T> {return others.iter( ).fold( this.createInitial( ),  (List<T> tList, T element) ->tList.add( element));}
	createInitial() : List<T> {return this;}
	removeAll(others : List<T>) : List<T> {return this.iter( ).filter( (T value) ->!others.contains( value)).collect( new ListCollector<>( ));}
	get(index : int) : T {return this.elements.get( index);}
	sort(sorter : BiFunction<T, T, Integer>) : List<T> { ArrayList<T> copy=new ArrayList<>( this.elements);copy.sort( (T t, T u) ->sorter.apply( t, u));return new JVMList<>( copy);}
	getLast() : T {return this.elements.getLast( );}
	contains(element : T) : boolean {return this.elements.contains( element);}
	size() : int {return this.elements.size( );}
	popLast() : Option<Tuple2<List<T>, T>> {if(this.elements.isEmpty( )){ return new None<>( );} java.util.List<T> slice=this.elements.subList( 0, this.elements.size( )-1);return new Some<>( new Tuple2<>( new JVMList<>( slice), this.elements.getLast( )));}
	popFirst() : Option<Tuple2<T, List<T>>> {if(this.elements.isEmpty( )){ return new None<>( );} java.util.List<T> slice=this.elements.subList( 1, this.elements.size( ));return new Some<>( new Tuple2<>( this.elements.getFirst( ), new JVMList<>( slice)));}
}
