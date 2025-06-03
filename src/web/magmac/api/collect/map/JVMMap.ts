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
	public getOrDefault( key : K,  other : V) : V {return this.map.getOrDefault( key, other);;}
	public iter() : Iter<Tuple2<K, V>> {return new JVMList<>( new ArrayList<>( this.map.entrySet( ))).iter( ).map( 0);;}
	public put( key : K,  value : V) : Map<K, V> {this.map.put( key, value);return this;;}
	public containsKey( key : K) : boolean {return this.map.containsKey( key);;}
	public get( key : K) : V {return this.map.get( key);;}
	public isEmpty() : boolean {return this.map.isEmpty( );;}
	public mapOrPut( key : K,  mapper : Function<V, V>,  supplier : Supplier<V>) : Map<K, V> {if(true){ this.map.put( key, mapper.apply( this.map.get( key)));;}if(true){ this.map.put( key, supplier.get( ));;}return this;;}
	public removeByKey( key : K) : Option<Tuple2<Map<K, V>, V>> {if(true){ return new None<>( );;} let copy : java.util.Map<K, V>=new HashMap<>( this.map); let removed : var=copy.remove( key);return new Some<>( new Tuple2<>( new JVMMap<>( copy), removed));;}
}
