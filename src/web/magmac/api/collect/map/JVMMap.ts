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
	getOrDefault(key : K, other : V) : V {return 0.map.getOrDefault( 0, 0);;}
	iter() : Iter<Tuple2<K, V>> {return new JVMList<>( new ArrayList<>( 0.map.entrySet( ))).iter( ).map( 0);;}
	put(key : K, value : V) : Map<K, V> {0.map.put( 0, 0);return 0;;}
	containsKey(key : K) : boolean {return 0.map.containsKey( 0);;}
	get(key : K) : V {return 0.map.get( 0);;}
	isEmpty() : boolean {return 0.map.isEmpty( );;}
	mapOrPut(key : K, mapper : Function<V, V>, supplier : Supplier<V>) : Map<K, V> {if(true){ 0.map.put( 0, 0.apply( 0.map.get( 0)));;}if(true){ 0.map.put( 0, 0.get( ));;}return 0;;}
	removeByKey(key : K) : Option<Tuple2<Map<K, V>, V>> {if(true){ return new None<>( );;}break;break;return new Some<>( new Tuple2<>( new JVMMap<>( 0), 0));;}
}
