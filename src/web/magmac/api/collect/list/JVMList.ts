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
	JVMList() : public {0( new ArrayList<>( ));;}
	addLast(element : T) : List<T> {break;0.add( 0);return new JVMList<>( 0);;}
	iter() : Iter<T> {return new HeadedIter<>( new RangeHead( 0.elements.size( ))).map( 0.get);;}
	addAllLast(others : List<T>) : List<T> {return 0.iter( ).fold( 0.createInitial( ), 0.addLast);;}
	createInitial() : List<T> {return 0;;}
	removeAll(others : List<T>) : List<T> {return 0.iter( ).filter( 0).collect( new ListCollector<>( ));;}
	get(index : int) : T {return 0.elements.get( 0);;}
	sort(sorter : BiFunction<T, T, Integer>) : List<T> {break;0.sort( 0.apply);return new JVMList<>( 0);;}
	contains(element : T) : boolean {return 0.elements.contains( 0);;}
	size() : int {return 0.elements.size( );;}
	popLast() : Option<Tuple2<List<T>, T>> {if(true){ return new None<>( );;}break;return new Some<>( new Tuple2<>( new JVMList<>( 0), 0.elements.getLast( )));;}
	popFirst() : Option<Tuple2<T, List<T>>> {if(true){ return new None<>( );;}break;return new Some<>( new Tuple2<>( 0.elements.getFirst( ), new JVMList<>( 0)));;}
	addFirst(element : T) : List<T> {break;0.add( 0);0.addAll( 0.elements);return new JVMList<>( 0);;}
	findLast() : Option<T> {if(true){ return new None<>( );;}return new Some<>( 0.elements.getLast( ));;}
}
