import { ArrayList } from "../../../../java/util/ArrayList";
import { Arrays } from "../../../../java/util/Arrays";
export class Lists {
	public static of() : List<T> {
		return new JVMList<>( new ArrayList<>( Arrays.asList( elements)));
	}
	public static empty() : List<T> {
		return new JVMList<>( );
	}
	public static repeat( element() : T,  size() : int) : List<T> {
		 copy() : List<T>=Lists.empty( );
		 i() : int=0;
		if(i<size){ 
		copy=copy.add( element);
		i++;}
		return copy;
	}
}
