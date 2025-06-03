export class Maps {
	public static empty() : Map<K, V> {return new JVMMap<>( new HashMap<>( ));;}
}
