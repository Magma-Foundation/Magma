import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export interface Map {V getOrDefault(K key, other) : V; iter() : Iter<Tuple2<K, V>>;Map<K, V> put(K key, value) : V;
	 containsKey( key : K) : boolean;
	 get( key : K) : V; isEmpty() : boolean;Map<K, V> mapOrPut(K key, Function<V, V> mapper, supplier) : Supplier<V>;
}
