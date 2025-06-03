export class Lists {
	public static of( ...elements : T[]) : List<T> {return new JVMList<>( new ArrayList<>( Arrays.asList( elements)));;}
	public static empty() : List<T> {return new JVMList<>( );;}
	public static repeat( element : T,  size : int) : List<T> { let copy : List<T>=Lists.empty( ); let i : var=0;if(true){ copy=copy.addLast( element);i++;;}return copy;;}
}
