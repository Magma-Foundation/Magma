import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export interface Map {other) : V;iterEntries() : Iter<Tuple2<K, V>>;value) : V;
	containsKey(key : K) : boolean;
	get(key : K) : V;isEmpty() : boolean;supplier) : Supplier<V>;
}
