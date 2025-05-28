import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Collector } from "../../../../magmac/api/iter/collect/Collector";
export class MapCollector<K,  V> {
	public createInitial() : Map<K, V> {
		return Maps.empty( );
	}
	public fold( current : Map<K, V>,  element : Tuple2<K, V>) : Map<K, V> {
		return current.put( element.left( ), element.right( ));
	}
}
