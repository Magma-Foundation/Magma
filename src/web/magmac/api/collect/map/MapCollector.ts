import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Collector } from "../../../../magmac/api/iter/collect/Collector";
export class MapCollector {
	createInitial() : Map<K, V>;
	fold(current : Map<K, V>, element : Tuple2<K, V>) : Map<K, V>;
}
