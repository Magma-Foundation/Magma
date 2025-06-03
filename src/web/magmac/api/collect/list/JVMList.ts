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
	JVMList() : public {0( 0);;}
	addLast(element : T) : List<T> {break;0.add( 0);return 0;;}
	iter() : Iter<T> {return 0;;}
	addAllLast(others : List<T>) : List<T> {return 0;;}
	createInitial() : List<T> {return 0;;}
	removeAll(others : List<T>) : List<T> {return 0;;}
	get(index : int) : T {return 0;;}
	sort(sorter : BiFunction<T, T, Integer>) : List<T> {break;0.sort( 0.apply);return 0;;}
	contains(element : T) : boolean {return 0;;}
	size() : int {return 0;;}
	popLast() : Option<Tuple2<List<T>, T>> {if(true){ return 0;;}break;return 0;;}
	popFirst() : Option<Tuple2<T, List<T>>> {if(true){ return 0;;}break;return 0;;}
	addFirst(element : T) : List<T> {break;0.add( 0);0.addAll( 0.elements);return 0;;}
	findLast() : Option<T> {if(true){ return 0;;}return 0;;}
}
