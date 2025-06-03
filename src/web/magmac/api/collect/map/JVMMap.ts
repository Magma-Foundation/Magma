import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { JVMList } from "../../../../magmac/api/collect/list/JVMList";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ArrayList } from "../../../../java/util/ArrayList";
import { HashMap } from "../../../../java/util/HashMap";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class JVMMap<K,  V> {
	getOrDefault(key : K, other : V) : V {break;;}
	iter() : Iter<Tuple2<K, V>> {break;;}
	put(key : K, value : V) : Map<K, V> {break;break;;}
	containsKey(key : K) : boolean {break;;}
	get(key : K) : V {break;;}
	isEmpty() : boolean {break;;}
	mapOrPut(key : K, mapper : Function<V, V>, supplier : Supplier<V>) : Map<K, V> {if(true){ break;;}if(true){ break;;}break;;}
	removeByKey(key : K) : Option<Tuple2<Map<K, V>, V>> {if(true){ break;;}break;break;break;;}
}
