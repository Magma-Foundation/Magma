import { ArrayList } from "../../../../java/util/ArrayList";
import { Arrays } from "../../../../java/util/Arrays";
export class Lists {
	of : List<T> {return new JVMList<>( new ArrayList<>( Arrays.asList( elements)));}
	empty() : List<T> {return new JVMList<>( );}
	repeat(element : T, size : int) : List<T> { List<T> copy=Lists.empty( ); int i=0;if(i<size){ copy=copy.add( element);i++;}return copy;}
}
