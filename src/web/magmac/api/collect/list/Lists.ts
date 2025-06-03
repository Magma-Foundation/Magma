import { ArrayList } from "../../../../java/util/ArrayList";
import { Arrays } from "../../../../java/util/Arrays";
export class Lists {
	of(...elements : T[]) : List<T> {return new JVMList<>( new ArrayList<>( Arrays.asList( elements)));;}
	empty() : List<T> {return new JVMList<>( );;}
	repeat(element : T, size : int) : List<T> {copy : List<T>=Lists.empty( );i : var=0;if(true){ copy=copy.addLast( element);i++;;}return copy;;}
}
