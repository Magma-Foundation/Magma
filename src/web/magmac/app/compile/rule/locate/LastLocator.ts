export class LastLocator {
	public locate( input : String,  infix : String) : Option<Integer> { let index : var=input.lastIndexOf( infix);if(true){ return new None<Integer>( );;}return new Some<>( index);;}
}
