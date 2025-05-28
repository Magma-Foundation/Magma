import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { BiFunction } from "../../../../java/util/function/BiFunction";
export interface List {
	 add( element : T) : List<T>;
	 iter() : Iter<T>;
	 addAll( others : List<T>) : List<T>;
	 removeAll( others : List<T>) : List<T>;
	 get( index : int) : T;
	 sort( sorter : BiFunction<T, T, Integer>) : List<T>;
	 getLast() : T;
	 contains( element : T) : boolean;
	 size() : int;
	 popLast() : Option<Tuple2<List<T>, T>>;
	 popFirst() : Option<Tuple2<T, List<T>>>;
}
