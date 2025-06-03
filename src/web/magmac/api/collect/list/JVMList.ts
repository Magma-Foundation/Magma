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
	JVMList() : public {break;;}
	addLast(element : T) : List<T> {break;break;break;;}
	iter() : Iter<T> {break;;}
	addAllLast(others : List<T>) : List<T> {break;;}
	createInitial() : List<T> {break;;}
	removeAll(others : List<T>) : List<T> {break;;}
	get(index : int) : T {break;;}
	sort(sorter : BiFunction<T, T, Integer>) : List<T> {break;break;break;;}
	contains(element : T) : boolean {break;;}
	size() : int {break;;}
	popLast() : Option<Tuple2<List<T>, T>> {if(true){ break;;}break;break;;}
	popFirst() : Option<Tuple2<T, List<T>>> {if(true){ break;;}break;break;;}
	addFirst(element : T) : List<T> {break;break;break;break;;}
	findLast() : Option<T> {if(true){ break;;}break;;}
}
