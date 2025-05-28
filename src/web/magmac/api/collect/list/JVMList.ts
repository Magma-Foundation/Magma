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
	JVMList() : public;
	add(element : T) : List<T>;
	iter() : Iter<T>;
	addAll(others : List<T>) : List<T>;
	createInitial() : List<T>;
	removeAll(others : List<T>) : List<T>;
	get(index : int) : T;
	sort(sorter : BiFunction<T, T, Integer>) : List<T>;
	getLast() : T;
	contains(element : T) : boolean;
	size() : int;
	popLast() : Option<Tuple2<List<T>, T>>;
	popFirst() : Option<Tuple2<T, List<T>>>;
}
