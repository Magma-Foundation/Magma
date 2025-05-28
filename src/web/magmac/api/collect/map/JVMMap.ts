import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { JVMList } from "../../../../magmac/api/collect/list/JVMList";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { ArrayList } from "../../../../java/util/ArrayList";
import { Function } from "../../../../java/util/function/Function";
import { Supplier } from "../../../../java/util/function/Supplier";
export class JVMMap {
	public getOrDefault( key : K,  other : V) : V {
		return this.map.getOrDefault( key, other);
	}
	public iter() : Iter<Tuple2<K, V>> {
		return new JVMList<>( new ArrayList<>( this.map.entrySet( ))).iter( ).map( ( entry : java.util.Map.Entry<K, V>) => new Tuple2<>( entry.getKey( ), entry.getValue( )));
	}
	public put( key : K,  value : V) : Map<K, V> {
		this.map.put( key, value);
		return this;
	}
	public containsKey( key : K) : boolean {
		return this.map.containsKey( key);
	}
	public get( key : K) : V {
		return this.map.get( key);
	}
	public isEmpty() : boolean {
		return this.map.isEmpty( );
	}
	public mapOrPut( key : K,  mapper : Function<V, V>,  supplier : Supplier<V>) : Map<K, V> {
		if(this.map.containsKey( key)){ 
		this.map.put( key, mapper.apply( this.map.get( key)));}
		else{ 
		this.map.put( key, supplier.get( ));}
		return this;
	}
}
