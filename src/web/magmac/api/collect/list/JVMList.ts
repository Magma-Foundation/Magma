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
export class JVMList<T> {
	JVMList() : public {this( new ArrayList<>( ));;}
	addLast(element : T) : List<T> {copy : var=new ArrayList<T>( this.elements);copy.add( element);return new JVMList<>( copy);;}
	iter() : Iter<T> {return new HeadedIter<>( new RangeHead( this.elements.size( ))).map( this.get);;}
	addAllLast(others : List<T>) : List<T> {return others.iter( ).fold( this.createInitial( ), List.addLast);;}
	createInitial() : List<T> {return this;;}
	removeAll(others : List<T>) : List<T> {return this.iter( ).filter( 0).collect( new ListCollector<>( ));;}
	get(index : int) : T {return this.elements.get( index);;}
	sort(sorter : BiFunction<T, T, Integer>) : List<T> {copy : var=new ArrayList<T>( this.elements);copy.sort( sorter.apply);return new JVMList<>( copy);;}
	contains(element : T) : boolean {return this.elements.contains( element);;}
	size() : int {return this.elements.size( );;}
	popLast() : Option<Tuple2<List<T>, T>> {if(true){ return new None<>( );;}slice : var=this.elements.subList( 0, this.elements.size( )-1);return new Some<>( new Tuple2<>( new JVMList<>( slice), this.elements.getLast( )));;}
	popFirst() : Option<Tuple2<T, List<T>>> {if(true){ return new None<>( );;}slice : var=this.elements.subList( 1, this.elements.size( ));return new Some<>( new Tuple2<>( this.elements.getFirst( ), new JVMList<>( slice)));;}
	addFirst(element : T) : List<T> {copy : var=new ArrayList<T>( );copy.add( element);copy.addAll( this.elements);return new JVMList<>( copy);;}
	findLast() : Option<T> {if(true){ return new None<>( );;}return new Some<>( this.elements.getLast( ));;}
}
