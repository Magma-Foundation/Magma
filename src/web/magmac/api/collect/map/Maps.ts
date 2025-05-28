import { HashMap } from "../../../../java/util/HashMap";
export class Maps {
	empty() : Map<K, V> {
		return new JVMMap<>( new HashMap<>( ));
	}
}
