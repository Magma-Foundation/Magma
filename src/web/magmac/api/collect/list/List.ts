import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export interface List {
	 add( element : T) : List<T>; iter() : Iter<T>;List others) : addAll(List<T>;List others) : removeAll(List<T>;
	 get( index : int) : T;List sorter) : sort(BiFunction<T, T, Integer>; getLast() : T;
	 contains( element : T) : boolean; size() : int; popLast() : Option<Tuple2<List<T>, T>>; popFirst() : Option<Tuple2<T, List<T>>>;
}
