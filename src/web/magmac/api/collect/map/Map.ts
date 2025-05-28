import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export interface Map {
	 getOrDefault( key : K,  other : V) : V;
	 iter() : Iter<Tuple2<K, V>>;
	 put( key : K,  value : V) : Map<K, V>;
	 containsKey( key : K) : boolean;
	 get( key : K) : V;
	 isEmpty() : boolean;
	 mapOrPut( key : K,  mapper : Function<V, V>,  supplier : Supplier<V>) : Map<K, V>;
}
