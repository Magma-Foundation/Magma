import { HashMap } from "../../../../java/util/HashMap";
export class Maps {
	public static empty() : Map<K, V> {return new JVMMap<>( new HashMap<>( ));;}
}
