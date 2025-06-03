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
	public addLast( element : T) : List<T> { let copy : var=new ArrayList<T>( this.elements);copy.add( element);return new JVMList<>( copy);;}
	public iter() : Iter<T> {return new HeadedIter<>( new RangeHead( this.elements.size( ))).map( this.get);;}
	public addAllLast( others : List<T>) : List<T> {return others.iter( ).fold( this.createInitial( ), List.addLast);;}
	private createInitial() : List<T> {return this;;}
	public removeAll( others : List<T>) : List<T> {return this.iter( ).filter( 0).collect( new ListCollector<>( ));;}
	public get( index : int) : T {return this.elements.get( index);;}
	public sort( sorter : BiFunction<T, T, Integer>) : List<T> { let copy : var=new ArrayList<T>( this.elements);copy.sort( sorter.apply);return new JVMList<>( copy);;}
	public contains( element : T) : boolean {return this.elements.contains( element);;}
	public size() : int {return this.elements.size( );;}
	public popLast() : Option<Tuple2<List<T>, T>> {if(true){ return new None<>( );;} let slice : var=this.elements.subList( 0, this.elements.size( )-1);return new Some<>( new Tuple2<>( new JVMList<>( slice), this.elements.getLast( )));;}
	public popFirst() : Option<Tuple2<T, List<T>>> {if(true){ return new None<>( );;} let slice : var=this.elements.subList( 1, this.elements.size( ));return new Some<>( new Tuple2<>( this.elements.getFirst( ), new JVMList<>( slice)));;}
	public addFirst( element : T) : List<T> { let copy : var=new ArrayList<T>( );copy.add( element);copy.addAll( this.elements);return new JVMList<>( copy);;}
	public isEmpty() : boolean {return this.elements.isEmpty( );;}
	public iterWithIndices() : Iter<Tuple2<Integer, T>> {return new HeadedIter<>( new RangeHead( this.elements.size( ))).map( 0);;}
	public findLast() : Option<T> {if(true){ return new None<>( );;}return new Some<>( this.elements.getLast( ));;}
}
