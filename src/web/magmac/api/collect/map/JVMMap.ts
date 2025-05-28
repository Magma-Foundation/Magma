import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { JVMList } from "../../../../magmac/api/collect/list/JVMList";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ArrayList } from "../../../../java/util/ArrayList";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class JVMMap {
	getOrDefault(key : K, other : V) : V;
	iterEntries() : Iter<Tuple2<K, V>>;
	put(key : K, value : V) : Map<K, V>;
	containsKey(key : K) : boolean;
	get(key : K) : V;
	isEmpty() : boolean;
	mapOrPut(key : K, mapper : Function<V, V>, supplier : Supplier<V>) : Map<K, V>;
}
